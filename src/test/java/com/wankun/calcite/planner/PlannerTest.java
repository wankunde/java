package com.wankun.calcite.planner;

import com.google.common.collect.Lists;
import com.wankun.calcite.PlannerUtil;
import org.apache.calcite.interpreter.BindableConvention;
import org.apache.calcite.interpreter.Interpreter;
import org.apache.calcite.plan.ConventionTraitDef;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.RelTraitDef;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.plan.hep.HepPlanner;
import org.apache.calcite.plan.hep.HepProgramBuilder;
import org.apache.calcite.plan.volcano.VolcanoPlanner;
import org.apache.calcite.rel.RelCollationTraitDef;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.metadata.DefaultRelMetadataProvider;
import org.apache.calcite.rel.rules.FilterJoinRule;
import org.apache.calcite.rel.rules.FilterMergeRule;
import org.apache.calcite.rel.rules.FilterProjectTransposeRule;
import org.apache.calcite.rel.rules.LoptOptimizeJoinRule;
import org.apache.calcite.rel.rules.ProjectMergeRule;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.impl.AbstractTable;
import org.apache.calcite.sql.SqlExplainLevel;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.type.SqlTypeFactoryImpl;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.test.CalciteAssert;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.calcite.tools.Program;
import org.apache.calcite.tools.Programs;
import org.apache.calcite.tools.RelConversionException;
import org.apache.calcite.tools.ValidationException;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class PlannerTest {


  @Test
  public void testPlanner() throws SqlParseException, ValidationException, RelConversionException {
    FrameworkConfig config = PlannerUtil.getFrameworkConfig(CalciteAssert.SchemaSpec.HR);
    Planner planner = Frameworks.getPlanner(config);

    SqlNode parse = planner.parse("SELECT e.name, e.salary, d.name as deptname\n" +
        "FROM emps e INNER JOIN depts d ON e.deptno = d.deptno\n" +
        "WHERE e.empid > 10 ");

    SqlNode validate = planner.validate(parse);
    // Converts a SQL parse tree into a tree of relational expressions
    RelNode converted = planner.rel(validate).rel;
    System.out.println(RelOptUtil.toString(converted));

    System.out.println(converted.getCluster().getPlanner());


//    RelTraitSet traitSet = converted.getTraitSet();
//    traitSet = traitSet.replace(BindableConvention.INSTANCE).simplify();
//    planner.transform(0, traitSet, converted);

    /*HepProgramBuilder builder = new HepProgramBuilder();
    builder.addRuleInstance(FilterJoinRule.FilterIntoJoinRule.FILTER_ON_JOIN);
    builder.addRuleInstance(FilterJoinRule.JOIN);
    builder.addRuleCollection(Programs.CALC_RULES);
    builder.addRuleCollection(Programs.RULE_SET);
    builder.addRuleInstance(FilterProjectTransposeRule.INSTANCE);
    HepPlanner hepPlanner = new HepPlanner(builder.build());
    hepPlanner.setRoot(converted);

    RelOptUtil.findTables(converted).forEach(a -> System.out.println(a.getQualifiedName()));;
    System.out.println(RelOptUtil.getVariablesSet(converted));*/

//    RelVisitTestOne testOne = new RelVisitTestOne();
//    testOne.go(root.rel);


//    RelNode node = hepPlanner.findBestExp();
//    System.out.println(RelOptUtil.toString(node));

    //Use VolcanoPlanner
    // cannot new planner instance, rel expression already belong to a planner
//    VolcanoPlanner volcanoPlanner = new VolcanoPlanner();
    //volcanoPlanner.addRelTraitDef(ConventionTraitDef.INSTANCE);
    VolcanoPlanner volcanoPlanner = (VolcanoPlanner) converted.getCluster().getPlanner();
//    volcanoPlanner.addRelTraitDef(ConventionTraitDef.INSTANCE);


    //volcanoPlanner.setRoot(root.rel);

    RelOptCluster relOptCluster = RelOptCluster.create(volcanoPlanner, new RexBuilder(new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT)));

    relOptCluster.setMetadataProvider(DefaultRelMetadataProvider.INSTANCE);
//    RelTraitSet desiredTraits = relOptCluster.traitSetOf(DogRel.CONVENTION);


//    RelNode relNode = root.rel;
//    if (!relNode.getTraitSet().equals(desiredTraits)) {
//      relNode = volcanoPlanner.changeTraits(relNode, desiredTraits);
//    }

    //RelTraitSet relTraits = relNode.getTraitSet().replace(desiredTraits).simplify();
    volcanoPlanner.setRoot(converted);

    RelNode finalNode = volcanoPlanner.findBestExp();


    System.out.println(RelOptUtil.toString(finalNode, SqlExplainLevel.ALL_ATTRIBUTES));
  }

  @Test
  public void testPlanner3() throws SqlParseException, ValidationException, RelConversionException {
    FrameworkConfig config = PlannerUtil.getFrameworkConfig(CalciteAssert.SchemaSpec.HR);
    Planner planner = Frameworks.getPlanner(config);

    SqlNode parse = planner.parse("SELECT e.name, e.salary, d.name as deptname\n" +
        "FROM emps e INNER JOIN depts d ON e.deptno = d.deptno\n" +
        "WHERE e.empid > 10 ");

    SqlNode validate = planner.validate(parse);
    // Converts a SQL parse tree into a tree of relational expressions
    RelNode converted = planner.rel(validate).rel;
    System.out.println(RelOptUtil.toString(converted));

    System.out.println(converted.getCluster().getPlanner());


    // create a set of rules to apply
    Program program = Programs.ofRules(
        FilterProjectTransposeRule.INSTANCE,
        ProjectMergeRule.INSTANCE,
        FilterMergeRule.INSTANCE,
        LoptOptimizeJoinRule.INSTANCE
    );

//    // Create a desired output trait set
    RelTraitSet traitSet = converted.getCluster().getPlanner().emptyTraitSet();
//    .emptyTraitSet();
//    .replace(
//        SparkConvention.INSTANCE
//        SparkRel.CONVENTION
//    );

//
//    // Execute the program
    RelNode optimized = program.run(converted.getCluster().getPlanner()
        , converted, traitSet, null, null);


  }


  @Test
  public void testParseAndExecutor() throws SqlParseException, ValidationException, RelConversionException {
    FrameworkConfig config = PlannerUtil.getDefaultFrameworkConfig();
    Planner planner = Frameworks.getPlanner(config);
    MyDataContext dataContext = new MyDataContext(planner, config.getDefaultSchema());

    SqlNode parse = planner.parse("select y, x\n"
        + "from (values (1, 'a'), (2, 'b'), (3, 'c')) as t(x, y)\n"
        + "where x > 1");

    SqlNode validate = planner.validate(parse);
    RelNode convert = planner.rel(validate).rel;
    System.out.println(RelOptUtil.toString(convert));

    final Interpreter interpreter = new Interpreter(dataContext, convert);
    for (Object[] row : interpreter) {
      System.out.println(Arrays.toString(row));
    }
  }

  @Test
  public void testPlanner2() throws SqlParseException, ValidationException, RelConversionException {
    SchemaPlus rootSchema = MockSchemaPlus();

    final FrameworkConfig config = Frameworks.newConfigBuilder()
        .parserConfig(SqlParser.Config.DEFAULT)
        .defaultSchema(rootSchema)
        .build();
    Planner planner = Frameworks.getPlanner(config);


    String sql = "select id, if(id > 0, time_test, date_test) from table_result";
    String sql1 = "select id, case when id > 0 then time_test else date_test end from table_result";
    String sql2 = "select * from table_result a natural left join table_result_copy b";

    String sql3 = "select case when 1 = 1 then sum(id) when 1 = 2 then first_value(int_test) else 0 end from table_result";


    SqlNode parse = planner.parse(sql3);
    SqlNode validate = planner.validate(parse);
    RelRoot root = planner.rel(validate);

    System.out.println(RelOptUtil.toString(root.rel));
  }

  public SchemaPlus MockSchemaPlus() {
    SchemaPlus rootSchema = Frameworks.createRootSchema(true);

    rootSchema.add("TABLE_RESULT", new AbstractTable() {
      public RelDataType getRowType(final RelDataTypeFactory typeFactory) {
        RelDataTypeFactory.FieldInfoBuilder builder = typeFactory.builder();

        RelDataType t0 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.FLOAT), true);
        RelDataType t1 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.TINYINT), true);
        RelDataType t2 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.SMALLINT), true);
        RelDataType t3 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.INTEGER), true);
        RelDataType t4 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.FLOAT), true);
        RelDataType t5 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.DOUBLE), true);
        RelDataType t6 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.BIGINT), true);
        RelDataType t7 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.BOOLEAN), true);
        RelDataType t8 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.DATE), true);
        RelDataType t9 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.TIME), true);
        RelDataType t10 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.TIMESTAMP), true);
        RelDataType t11 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.VARCHAR), true);


        builder.add("ID", t0);
        builder.add("byte_test".toUpperCase(), t1);
        builder.add("short_test".toUpperCase(), t2);
        builder.add("int_test".toUpperCase(), t3);
        builder.add("float_test".toUpperCase(), t4);
        builder.add("double_test".toUpperCase(), t5);
        builder.add("long_test".toUpperCase(), t6);
        builder.add("boolean_test".toUpperCase(), t7);
        builder.add("date_test".toUpperCase(), t8);
        builder.add("time_test".toUpperCase(), t9);
        builder.add("timestamp_test".toUpperCase(), t10);
        builder.add("string_test".toUpperCase(), t11);

        return builder.build();
      }
    });


    rootSchema.add("TABLE_RESULT_COPY", new AbstractTable() {
      public RelDataType getRowType(final RelDataTypeFactory typeFactory) {
        RelDataTypeFactory.FieldInfoBuilder builder = typeFactory.builder();

        RelDataType t0 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.DOUBLE), true);
        RelDataType t1 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.TINYINT), true);
        RelDataType t2 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.SMALLINT), true);
        RelDataType t3 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.INTEGER), true);
        RelDataType t4 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.FLOAT), true);
        RelDataType t5 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.DOUBLE), true);
        RelDataType t6 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.BIGINT), true);
        RelDataType t7 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.BOOLEAN), true);
        RelDataType t8 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.DATE), true);
        RelDataType t9 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.TIME), true);
        RelDataType t10 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.TIMESTAMP), true);
        RelDataType t11 = typeFactory.createTypeWithNullability(typeFactory.createSqlType(SqlTypeName.VARCHAR), true);


        builder.add("ID", t0);
        builder.add("byte_test1".toUpperCase(), t1);
        builder.add("short_test1".toUpperCase(), t2);
        builder.add("int_test1".toUpperCase(), t3);
        builder.add("float_test1".toUpperCase(), t4);
        builder.add("double_test1".toUpperCase(), t5);
        builder.add("long_test1".toUpperCase(), t6);
        builder.add("boolean_test1".toUpperCase(), t7);
        builder.add("date_test1".toUpperCase(), t8);
        builder.add("time_test1".toUpperCase(), t9);
        builder.add("timestamp_test1".toUpperCase(), t10);
        builder.add("string_test1".toUpperCase(), t11);

        return builder.build();
      }
    });
    return rootSchema;
  }


}
