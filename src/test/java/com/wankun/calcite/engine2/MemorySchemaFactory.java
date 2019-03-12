package com.wankun.calcite.engine2;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.wankun.calcite.engine2.function.TimeOperator;
import org.apache.calcite.schema.Function;
import org.apache.calcite.schema.ScalarFunction;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaFactory;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Table;
import org.apache.calcite.schema.impl.AbstractSchema;
import org.apache.calcite.schema.impl.ScalarFunctionImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * SchemaFactory , 用于构建数据源的Schema
 *
 * @author hdfs
 */
public class MemorySchemaFactory implements SchemaFactory {
  /**
   * 建立Connection的时候，即通过读取定义的元数据文件，获取指定的SchemaFatory
   * 然后通过这个SchemaFactory来创建Schema。
   */
  public Schema create(SchemaPlus parentSchema, String name, Map<String, Object> operand) {
    System.out.println("param1 : " + operand.get("param1"));
    System.out.println("param2 : " + operand.get("param2"));
    System.out.println("Get database " + name);
    return new MemorySchema(name);
  }

  class MemorySchema extends AbstractSchema {
    private String dbName;

    public MemorySchema(String dbName) {
      this.dbName = dbName;
    }

    /**
     * 通过schema实现从数据源自己的概念（DataBase及Table）向Calcite的概念(MemoryTable)进行转换的过程。
     *
     * 这里执行效率真低，上层connection MetaData的每一次循环都会调用该方法
     */
    @Override
    public Map<String, Table> getTableMap() {
      Map<String, Table> tables = new HashMap<String, Table>();
      MemoryData.Database database = MemoryData.MAP.get(this.dbName);
      if (database == null)
        return tables;
      for (MemoryData.Table table : database.tables) {
        tables.put(table.tableName, new MemoryTable(table));
      }
      return tables;
    }

    /**
     * 获取操作函数中所有操作函数，将这些函数转换为Calcite中的概念。
     */
    protected Multimap<String, Function> getFunctionMultimap() {
      ImmutableMultimap<String, ScalarFunction> funcs = ScalarFunctionImpl.createAll(TimeOperator.class);
      Multimap<String, Function> functions = HashMultimap.create();
      for (String key : funcs.keySet()) {
        for (ScalarFunction func : funcs.get(key)) {
          functions.put(key, func);
        }
      }
      return functions;
    }
  }
}