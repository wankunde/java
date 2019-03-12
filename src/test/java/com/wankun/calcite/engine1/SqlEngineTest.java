package com.wankun.calcite.engine1;

import com.wankun.calcite.engine1.table.CalculateTable;
import com.wankun.calcite.engine1.table.SumTable;
import org.apache.calcite.schema.SchemaPlus;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlEngineTest {

  @Test
  public void testQuery() throws SQLException {
    CalculateTable table = new CalculateTable();
    SumTable sumTable = new SumTable();
    SchemaManage manage = new SchemaManage();
    SchemaPlus rootSchema = manage.getRootSchema();
    rootSchema.add("CT", table);
    rootSchema.add("ST", sumTable);
    ResultSet result = manage.executeSQL("SELECT CT.\"name\", \"age\", \"sex\", \"birthday\", \"job\" FROM CT JOIN ST ON CT.\"name\"=ST.\"name\"");
    while (result.next()) {
      System.out.println(result.getString("name") + "\t" +
          result.getString("age") + "\t" +
          result.getString("sex") + "\t" +
          result.getString("birthday") + "\t" +
          result.getString("job")
      );
    }
  }
}
