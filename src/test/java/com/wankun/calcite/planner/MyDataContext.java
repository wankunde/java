package com.wankun.calcite.planner;

import org.apache.calcite.DataContext;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.linq4j.QueryProvider;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.tools.Planner;

public class MyDataContext implements DataContext {
  private final Planner planner;
  private final SchemaPlus rootSchema;

  public MyDataContext(Planner planner, SchemaPlus rootSchema) {
    this.planner = planner;
    this.rootSchema = rootSchema;
  }

  public SchemaPlus getRootSchema() {
    return rootSchema;
  }

  public JavaTypeFactory getTypeFactory() {
    return (JavaTypeFactory) planner.getTypeFactory();
  }

  public QueryProvider getQueryProvider() {
    return null;
  }

  public Object get(String name) {
    return null;
  }
}

