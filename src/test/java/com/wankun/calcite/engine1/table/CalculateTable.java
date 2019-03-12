package com.wankun.calcite.engine1.table;

import org.apache.calcite.DataContext;
import org.apache.calcite.linq4j.Enumerable;
import org.apache.calcite.linq4j.Linq4j;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.schema.ScannableTable;
import org.apache.calcite.schema.impl.AbstractTable;

import java.util.ArrayList;
import java.util.List;

public class CalculateTable extends AbstractTable implements ScannableTable {


  private static List<Object[]> dataList;

  private static List<Object> dataPeopleList;
  static {
    //data
    dataList = new ArrayList<Object[]>();
    Object[] rows = new Object[3];
    rows[0] = "ylh"; //name
    rows[1] = 29;   //age
    rows[2] = "male"; //sex
//        dataList.add(rows);

    rows = new Object[3];
    rows[0] = "xy"; //name
    rows[1] = 20;   //age
    rows[2] = 11; //sex
//        dataList.add(rows);

//        People p = new People();
//        p.setName("ylh");
//        p.setAge(29);
//        p.setSex("male");
//        rows = new Object[1];
//        rows[0] = p;
//        dataList.add(rows);
////        dataPeopleList.add(p);
//
//        p = new People();
//        p.setName("xy");
//        p.setAge(20);
//        p.setSex("male");
//        rows[0] = p;
//        dataList.add(rows);
////        dataPeopleList.add(p);
//        dataList.add(dataPeopleList);
  }

  public Enumerable<Object[]> scan(DataContext dataContext) {
    return Linq4j.asEnumerable(dataList);
  }

  public RelDataType getRowType(RelDataTypeFactory relDataTypeFactory) {
    java.util.List<RelDataType> typeList = new ArrayList<RelDataType>();
    typeList.add(relDataTypeFactory.createJavaType(String.class));
    typeList.add(relDataTypeFactory.createJavaType(String.class));
    typeList.add(relDataTypeFactory.createJavaType(Integer.class));

    java.util.List<java.lang.String> fieldNameList = new ArrayList<String>();
    fieldNameList.add("name");
    fieldNameList.add("age");
    fieldNameList.add("sex");

    RelDataType structType = relDataTypeFactory.createStructType(typeList, fieldNameList);
    return structType;
  }

}