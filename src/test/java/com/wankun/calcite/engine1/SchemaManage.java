package com.wankun.calcite.engine1;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaManage {

  public final static SchemaManage INSTANCE = new SchemaManage();

  CalciteConnection calciteConnection = null;

  public SchemaManage() {
    try {
      Class.forName("org.apache.calcite.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:calcite:");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      calciteConnection = connection.unwrap(CalciteConnection.class);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public SchemaPlus getRootSchema() {
    return this.calciteConnection.getRootSchema();
  }

  public void printTables() {
    System.out.println(this.calciteConnection.getRootSchema().getTableNames().toString());
  }

  public ResultSet executeSQL(String sql) {
    try {
      Statement statement = this.calciteConnection.createStatement();
      return statement.executeQuery(sql);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }


}
