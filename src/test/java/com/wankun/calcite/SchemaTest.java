package com.wankun.calcite;

import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeField;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.schema.Table;
import org.apache.calcite.sql.type.SqlTypeFactoryImpl;
import org.junit.Test;

public class SchemaTest {

  /**
   * Schema : 类似数据库，通过 ReflectiveSchema 工具进行注册，在Schema中数组映射为表，数据元素映射为记录行
   * RelDataTypeFactory 和 JavaTypeFactory 是两个Type工具类
   */
  @Test
  public void testAddSchema() {
    Hr hr = new Hr();
    Address[] addresses = new Address[1];
    Address address = new Address(1, 1);
    addresses[0] = address;

    hr.emps[0] = new Employee(100, "Bill", addresses);

    address = new Address(2, 2);
    addresses[0] = address;
    hr.emps[1] = new Employee(200, "Eric", addresses);

    address = new Address(3, 3);
    addresses[0] = address;
    hr.emps[2] = new Employee(150, "Sebastian", addresses);

    ReflectiveSchema hrSchema = new ReflectiveSchema(hr);

//    RelDataTypeFactory dataTypeFactory = new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT);
    PlannerUtil.printSchema(hrSchema);
  }

  /**
   * Object that will be used via reflection to create the "hr" schema.
   */
  public static class Hr {
    public final Employee[] emps = new Employee[3];
  }

  /**
   * Object that will be used via reflection to create the "emps" table.
   */
  public static class Employee {
    public final int empid;
    public final String name;
    public Address[] address = new Address[1];

    public Employee(int empid, String name, Address[] address) {
      this.empid = empid;
      this.name = name;
      this.address = address;
    }
  }

  public static class Address {
    public final int street;
    public final int road;

    public Address(int street, int road) {
      this.street = street;
      this.road = road;
    }
  }

}
