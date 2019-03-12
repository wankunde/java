package com.wankun.calcite;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.calcite.tools.RelConversionException;
import org.apache.calcite.tools.ValidationException;
import org.junit.Test;

public class FrameWorksTest {

  public static class TestSchema {
    public static final TestTab[] tab = {new TestTab(1, "wankun", "ShangHai")};
    public final Triple[] rdf = {new Triple("s", "p", "o")};

  }

  public static class TestTab {
    public TestTab(int id, String name, String address) {
      this.id = id;
      this.name = name;
      this.address = address;
    }

    public int id;
    public String name;
    public String address;
  }

  public static class Triple {
    public String s;
    public String p;
    public String o;

    public Triple(String s, String p, String o) {
      super();
      this.s = s;
      this.p = p;
      this.o = o;
    }

  }

  @Test
  public void testFrameWorks() throws SqlParseException, ValidationException, RelConversionException {
    SchemaPlus schemaPlus = Frameworks.createRootSchema(true);

    schemaPlus.add("T", new ReflectiveSchema(new TestSchema()));
    Frameworks.ConfigBuilder configBuilder = Frameworks.newConfigBuilder();
    configBuilder.defaultSchema(schemaPlus);
    FrameworkConfig frameworkConfig = configBuilder.build();
    SqlParser.ConfigBuilder paresrConfig = SqlParser.configBuilder(frameworkConfig.getParserConfig());

    paresrConfig.setCaseSensitive(false).setConfig(paresrConfig.build());

    Planner planner = Frameworks.getPlanner(frameworkConfig);

    String[] sqls = {
        "select \"a\".\"s\", count(\"a\".\"s\") from \"T\".\"rdf\" \"a\" group by \"a\".\"s\""
    };


    SqlNode sqlNode = null;
    RelRoot relRoot = null;

    for (String sql : sqls) {
      sqlNode = planner.parse(sql);
      sqlNode = planner.validate(sqlNode);
      relRoot = planner.rel(sqlNode);

      RelNode relNode = relRoot.project();
      System.out.print(RelOptUtil.toString(relNode));
    }


  }
}
