package com.wankun.calcite.engine2;

import com.wankun.calcite.engine2.function.TimeOperator;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlEngineTest {

  @Test
  public void testEngine() throws NoSuchMethodException, ClassNotFoundException, SQLException {
    // 1. 加载Calcite jdbc Driver。可以代码指定jvm加载的驱动类，也可以不写，由系统自动查找加载
    Class.forName("org.apache.calcite.jdbc.Driver");

    /*
     2. jdbc方式调用calcite，calcite会根据指定的Model配置文件进行初始化Schema信息
     2.1 通过 MemorySchemaFactory 生成一个Schema子类,dbName=school的 MemorySchema 对象
     2.2 MemoryData 进行Schema 初始化
     2.2.1 初始化 SQLTYPE_MAPPING 和 JAVATYPE_MAPPING 两种数据类型
     2.2.2 构建 Database 对象，包括所有tables和tables中的数据
     2.2.3 后面无论是 getMetaData().getTables , getMetaData().getColumns 还是 executeQuery 都会调入
          MemorySchema.getTableMap 方法，并进行必要的初始化
     */
    String schemaJson = Thread.currentThread().getContextClassLoader().getResource("School.json").getFile();
    Connection connection = DriverManager.getConnection("jdbc:calcite:model=" + schemaJson, null);
    CalciteConnection calciteConn = connection.unwrap(CalciteConnection.class);

    calciteConn.getRootSchema().add("YEAR", ScalarFunctionImpl.create(TimeOperator.class.getMethod("YEAR", Date.class)));
    calciteConn.getRootSchema().add("COM", ScalarFunctionImpl.create(TimeOperator.class.getMethod("COM", String.class, String.class)));
    ResultSet result;
    /**
     * getTables 操作会将数据源原始的table概念转换为calcite中的Table概念，还要将操作函数类中的所有操作函数
     * 读取出来。
     */
    result = connection.getMetaData().getTables(null, null, null, null);
    while (result.next()) {
      System.out.println("Catalog : " + result.getString(1) + ",Database : " + result.getString(2) + ",Table : " + result.getString(3));
    }
    result.close();
    /**
     * getColumns 操作会将原始数据源中的Column概念(列数据类型等)转换为Calcite中的Column概念(RelDataType)
     */
    result = connection.getMetaData().getColumns(null, null, "Student", null);
    while (result.next()) {
      System.out.println("name : " + result.getString(4) + ", type : " + result.getString(5) + ", typename : " + result.getString(6));
    }
    result.close();

    Statement st = connection.createStatement();
    /**
     * 调用scan函数读取数据库，在执行query的过程中，会调用Calcite的MemoryTable概念的scan来获取表的迭代器，这个迭代器是我们自己定义的
     * 用于对表数据进行迭代处理。这个处理过程就是实现从原始数据源的数据到我们所需要的数据之间的转换过程，要调用迭代器的功能来完成实现。
     */
    result = st.executeQuery("select S.\"id\", SUM(S.\"classId\") from \"Student\" as S group by S.\"id\"");
    while (result.next()) {
      System.out.println(result.getString(1) + "\t" + result.getString(2));
    }
    result.close();
    connection.close();

  }
}
