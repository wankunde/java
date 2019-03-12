package com.wankun.calcite;

import com.google.common.collect.ImmutableList;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.plan.Contexts;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.hep.HepProgram;
import org.apache.calcite.rel.rules.FilterCorrelateRule;
import org.apache.calcite.rel.rules.FilterJoinRule;
import org.apache.calcite.rel.rules.FilterProjectTransposeRule;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.test.DiffRepository;
import org.apache.calcite.tools.RelConversionException;
import org.apache.calcite.tools.ValidationException;

import java.util.List;

public class CalciteDemo {

//  private final SqlOperatorTable operatorTable;
//  private final ImmutableList<Program> programs;
//  private final FrameworkConfig config;
//
//  /** Holds the trait definitions to be registered with planner. May be null. */
//  private final ImmutableList<RelTraitDef> traitDefs;
//
//  private final SqlParser.Config parserConfig;
//  private final SqlRexConvertletTable convertletTable;
//
//  private PlannerImpl.State state;
//
//  // set in STATE_1_RESET
//  private boolean open;
//
//  // set in STATE_2_READY
//  private SchemaPlus defaultSchema;
//  private JavaTypeFactory typeFactory;
//  private RelOptPlanner planner;
//  private RexExecutor executor;
//
//  // set in STATE_4_VALIDATE
//  private CalciteSqlValidator validator;
//  private SqlNode validatedSqlNode;
//
//  // set in STATE_5_CONVERT
//  private RelRoot root;

  private SchemaPlus rootSchema;
  private RelOptPlanner planner;
//  final RelOptCluster cluster;
  private JavaTypeFactory typeFactory;


  public CalciteDemo() {
//    final RexBuilder rexBuilder = createRexBuilder();
//    rootSchema = Frameworks.createRootSchema(true);

//    final FrameworkConfig config = Frameworks.newConfigBuilder()
//        .parserConfig(SqlParser.Config.DEFAULT)
//        .defaultSchema(
//            CalciteAssert.addSchema(rootSchema, CalciteAssert.SchemaSpec.HR)
//        )
//        .build();
    HepProgram program = HepProgram.builder()
        .addRuleInstance(FilterJoinRule.FILTER_ON_JOIN)
        .addRuleInstance(FilterProjectTransposeRule.INSTANCE)
        .addRuleInstance(FilterCorrelateRule.INSTANCE)
        .build();

//    planner = new HepPlanner(program);
//    cluster = RelOptCluster.create(planner, rexBuilder);
//    typeFactory = (JavaTypeFactory) cluster.getTypeFactory();

  }

//
//  /** Creates a planner. Not a public API; call
//   * {@link org.apache.calcite.tools.Frameworks#getFrameworkConfig} instead. */
//  public CalciteDemo(FrameworkConfig config) {
//    this.config = config;
//    this.defaultSchema = config.getDefaultSchema();
//    this.operatorTable = config.getOperatorTable();
//    this.programs = config.getPrograms();
//    this.parserConfig = config.getParserConfig();
//    this.state = PlannerImpl.State.STATE_0_CLOSED;
//    this.traitDefs = config.getTraitDefs();
//    this.convertletTable = config.getConvertletTable();
//    this.executor = config.getExecutor();
//  }

  protected static DiffRepository getDiffRepos() {
    return null;
  }

  private static PlannerUtil plannerUtil;

  public static void main(String[] args) throws SqlParseException, ValidationException, RelConversionException {
    CalciteDemo demo = new CalciteDemo();
    plannerUtil = new PlannerUtil(getDiffRepos(), false, false, true, false,
        null, null, SqlToRelConverter.Config.DEFAULT,
        SqlConformanceEnum.DEFAULT, Contexts.empty());
    demo.testPlanner();

  }

  public void testPlanner() throws SqlParseException {
    String sql = "SELECT u.id AS user_id, u.name AS user_name, o.id AS order_id " +
        "FROM users u INNER JOIN orders o ON u.id = o.user_id " +
        "WHERE u.id > 50";
    // Parse the query
    SqlParser parser = SqlParser.create(sql);
    SqlNode sqlNode = parser.parseStmt();
    System.out.println(sqlNode);

    boolean caseSensitive = true;
    final String DEFAULT_SCHEMA = "SALES";
    final List<String> PREFIX = ImmutableList.of(DEFAULT_SCHEMA);



    // Validate the query
    /*final RelDataTypeFactory typeFactory = new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT);
    final Prepare.CatalogReader catalogReader = new CalciteCatalogReader(CalciteSchema.createRootSchema(false, false, DEFAULT_SCHEMA),
        SqlNameMatchers.withCaseSensitive(caseSensitive),
        ImmutableList.of(PREFIX, ImmutableList.of()),
        typeFactory, null);

        plannerUtil.createCatalogReader(typeFactory);
    SqlValidator validator = SqlValidatorUtil.newValidator(
        SqlStdOperatorTable.instance(), catalogReader, typeFactory, SqlConformance.DEFAULT
    );
    SqlNode validatedSqlNode = validator.validate(sqlNode);*/

    // Convert SqlNode to RelNode
    RexBuilder rexBuilder = createRexBuilder();
    RelOptPlanner planner = null;
    RelOptCluster cluster = RelOptCluster.create(planner, rexBuilder);
    /*SqlRexConvertletTable convertletTable = null
        ;
    SqlToRelConverter sqlToRelConverter = new SqlToRelConverter(new PlannerImpl.ViewExpanderImpl()

        new RelOptTable.ViewExpander() {
      @Override
      public RelRoot expandView(RelDataType relDataType, String s, List<String> list, List<String> list1) {
        return null;
      }
    } , validator,createCatalogReader(),cluster,convertletTable);
    RelRoot root = sqlToRelConverter.convertQuery(validatedSqlNode,false,true);
*/


//    HepPlanner planner = createPlanner(program);
//
//    planner.setRoot(root);
//    root = planner.findBestExp();

  }


  /*// CalciteCatalogReader is stateless; no need to store one
  private CalciteCatalogReader createCatalogReader() {
    FrameworkConfig config = Frameworks.newConfigBuilder().build();
    SchemaPlus defaultSchema = config.getDefaultSchema();

    final SchemaPlus rootSchema = rootSchema(defaultSchema);
    final Context context = config.getContext();
    final CalciteConnectionConfig connectionConfig;

    if (context != null) {
      connectionConfig = context.unwrap(CalciteConnectionConfig.class);
    } else {
      Properties properties = new Properties();
//      properties.setProperty(CalciteConnectionProperty.CASE_SENSITIVE.camelName(),
//          String.valueOf(parserConfig.caseSensitive()));
      connectionConfig = new CalciteConnectionConfigImpl(properties);
    }

    return new CalciteCatalogReader(
        CalciteSchema.from(rootSchema),
        CalciteSchema.from(defaultSchema).path(null),
        typeFactory, connectionConfig);
  }
*/

  /*public void testPlanner() {
    String sql = "SELECT u.id AS user_id, u.name AS user_name, o.id AS order_id " +
        "FROM users u INNER JOIN orders o ON u.id = o.user_id " +
        "WHERE u.id > 50";
    // Parse the query
    SqlParser parser = SqlParser.create(sql);
    SqlNode sqlNode = parser.parseStmt();

    // Validate the query
    CalciteCatalogReader catalogReader = createCatalogReader();
    RelDataTypeFactory typeFactory = null;
    SqlValidator validator = SqlValidatorUtil.newValidator(
        SqlStdOperatorTable.instance(), catalogReader, typeFactory, SqlConformance.DEFAULT
    );
    SqlNode validatedSqlNode = validator.validate(sqlNode);

    // Convert SqlNode to RelNode
    RexBuilder rexBuilder = createRexBuilder();
    RelOptPlanner planner = null;
    RelOptCluster cluster = RelOptCluster.create(planner, rexBuilder);
    SqlRexConvertletTable convertletTable = null
        ;
    SqlToRelConverter sqlToRelConverter = new SqlToRelConverter(new RelOptTable.ViewExpander() {
      @Override
      public RelRoot expandView(RelDataType relDataType, String s, List<String> list, List<String> list1) {
        return null;
      }
    } , validator,createCatalogReader(),cluster,convertletTable);
    RelRoot root = sqlToRelConverter.convertQuery(validatedSqlNode,false,true);

    // Optimize the plan
    RelOptPlanner planner1 = new VolcanoPlanner();

    // create a set of rules to apply
    Program program = Programs.ofRules(
        FilterProjectTransposeRule.INSTANCE,
        ProjectMergeRule.INSTANCE,
        FilterMergeRule.INSTANCE,
        LoptOptimizeJoinRule.INSTANCE
    );

//    // Create a desired output trait set
//    RelTraitSet traitSet = planner.emptyTraitSet().replace(
////        SparkConvention.INSTANCE
//        SparkRel.CONVENTION
//    );
//
//    // Execute the program
//    RelNode optimized = program.run(planner,root.rel,traitSet);
  }*/


  // RexBuilder is stateless; no need to store one
  private RexBuilder createRexBuilder() {
    return new RexBuilder(typeFactory);
  }

//  // CalciteCatalogReader is stateless; no need to store one
//  private CalciteCatalogReader createCatalogReader() {
//    SchemaPlus rootSchema = rootSchema(defaultSchema);
//    return new CalciteCatalogReader(
//        CalciteSchema.from(rootSchema),
//        parserConfig.caseSensitive(),
//        CalciteSchema.from(defaultSchema).path(null),
//        typeFactory);
//  }

  private static SchemaPlus rootSchema(SchemaPlus schema) {
    for (; ; ) {
      if (schema.getParentSchema() == null) {
        return schema;
      }
      schema = schema.getParentSchema();
    }
  }

}
