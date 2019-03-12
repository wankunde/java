package com.wankun.calcite;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.calcite.adapter.java.JavaTypeFactory;
import org.apache.calcite.config.CalciteConnectionConfig;
import org.apache.calcite.interpreter.Bindables;
import org.apache.calcite.jdbc.JavaTypeFactoryImpl;
import org.apache.calcite.plan.Context;
import org.apache.calcite.plan.Contexts;
import org.apache.calcite.plan.ConventionTraitDef;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelOptPlanner;
import org.apache.calcite.plan.RelOptRule;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.plan.RelTraitDef;
import org.apache.calcite.prepare.Prepare;
import org.apache.calcite.rel.RelCollationTraitDef;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.core.RelFactories;
import org.apache.calcite.rel.stream.StreamRules;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rel.type.RelDataTypeFactory;
import org.apache.calcite.rel.type.RelDataTypeField;
import org.apache.calcite.rel.type.RelDataTypeSystem;
import org.apache.calcite.rex.RexBuilder;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Table;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlOperatorTable;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.test.SqlTestFactory;
import org.apache.calcite.sql.type.SqlTypeFactoryImpl;
import org.apache.calcite.sql.validate.SqlConformance;
import org.apache.calcite.sql.validate.SqlConformanceEnum;
import org.apache.calcite.sql.validate.SqlValidator;
import org.apache.calcite.sql.validate.SqlValidatorCatalogReader;
import org.apache.calcite.sql.validate.SqlValidatorImpl;
import org.apache.calcite.sql2rel.RelFieldTrimmer;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.sql2rel.StandardConvertletTable;
import org.apache.calcite.test.CalciteAssert;
import org.apache.calcite.test.DiffRepository;
import org.apache.calcite.test.MockRelOptPlanner;
import org.apache.calcite.test.MockSqlOperatorTable;
import org.apache.calcite.test.catalog.MockCatalogReader;
import org.apache.calcite.test.catalog.MockCatalogReaderSimple;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.RelBuilder;
import org.apache.calcite.tools.RuleSet;
import org.apache.calcite.tools.RuleSets;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import static org.apache.calcite.test.SqlToRelTestBase.assertValid;

public class PlannerUtil {

  public static FrameworkConfig getDefaultFrameworkConfig() {
    return getFrameworkConfig(null);
  }

  public static FrameworkConfig getFrameworkConfig(CalciteAssert.SchemaSpec SchemaSpec) {
    SchemaPlus rootSchema = Frameworks.createRootSchema(true);
    if (SchemaSpec != null)
      rootSchema = CalciteAssert.addSchema(rootSchema, SchemaSpec);

    // SQL 取消大小写检查
    SqlParser.ConfigBuilder parserConfig = SqlParser.configBuilder();
    parserConfig.setCaseSensitive(false);

    final List<RelTraitDef> traitDefs = Lists.newArrayList(
        ConventionTraitDef.INSTANCE,
        RelCollationTraitDef.INSTANCE);

    final FrameworkConfig config = Frameworks.newConfigBuilder()
        .parserConfig(parserConfig.build())
        .defaultSchema(rootSchema)
        .traitDefs(traitDefs)
        .ruleSets(getRuleSets())
        .build();
    return config;
  }

  public static final RuleSet[] getRuleSets() {
    ImmutableList<RelOptRule> rules = ImmutableList.<RelOptRule>builder()
        .addAll(StreamRules.RULES)
        .addAll(Bindables.RULES)
        .build();

    return new RuleSet[]{RuleSets.ofList(rules)};
  }

  public static void printSchema(Schema schema) {
    JavaTypeFactory javaTypeFactory = new JavaTypeFactoryImpl();
    schema.getTableNames().forEach(tabName -> {
      Table tab = schema.getTable(tabName);
      RelDataType dataType = tab.getRowType(javaTypeFactory);

      System.out.println("---------------- \n" +
          tabName + " : " + tabName + "\n" +
          "  dataType : ");
      for (RelDataTypeField field : dataType.getFieldList()) {
        System.out.println("  " + field);
      }

    });
  }

  // ==================================================================================================

  protected static final String NL = System.getProperty("line.separator");

  private RelOptPlanner planner;
  private SqlOperatorTable opTab;
  private final DiffRepository diffRepos;
  private final boolean enableDecorrelate;
  private final boolean enableLateDecorrelate;
  private final boolean enableTrim;
  private final boolean enableExpand;
  private final SqlConformance conformance;
  private final SqlTestFactory.MockCatalogReaderFactory catalogReaderFactory;
  private final java.util.function.Function<RelOptCluster, RelOptCluster> clusterFactory;
  private RelDataTypeFactory typeFactory;
  public final SqlToRelConverter.Config config;
  private final Context context;

  /**
   * Creates a TesterImpl.
   *
   * @param diffRepos            Diff repository
   * @param enableDecorrelate    Whether to decorrelate
   * @param enableTrim           Whether to trim unused fields
   * @param enableExpand         Whether to expand sub-queries
   * @param catalogReaderFactory Function to create catalog reader, or null
   * @param clusterFactory       Called after a cluster has been created
   */
  protected PlannerUtil(DiffRepository diffRepos, boolean enableDecorrelate,
                        boolean enableTrim, boolean enableExpand,
                        boolean enableLateDecorrelate,
                        SqlTestFactory.MockCatalogReaderFactory
                            catalogReaderFactory,
                        java.util.function.Function<RelOptCluster, RelOptCluster> clusterFactory) {
    this(diffRepos, enableDecorrelate, enableTrim, enableExpand,
        enableLateDecorrelate,
        catalogReaderFactory,
        clusterFactory,
        SqlToRelConverter.Config.DEFAULT,
        SqlConformanceEnum.DEFAULT,
        Contexts.empty());
  }

  protected PlannerUtil(DiffRepository diffRepos, boolean enableDecorrelate,
                        boolean enableTrim, boolean enableExpand, boolean enableLateDecorrelate,
                        SqlTestFactory.MockCatalogReaderFactory catalogReaderFactory,
                        java.util.function.Function<RelOptCluster, RelOptCluster> clusterFactory,
                        SqlToRelConverter.Config config, SqlConformance conformance,
                        Context context) {
    this.diffRepos = diffRepos;
    this.enableDecorrelate = enableDecorrelate;
    this.enableTrim = enableTrim;
    this.enableExpand = enableExpand;
    this.enableLateDecorrelate = enableLateDecorrelate;
    this.catalogReaderFactory = catalogReaderFactory;
    this.clusterFactory = clusterFactory;
    this.config = config;
    this.conformance = conformance;
    this.context = context;
  }

  public RelRoot convertSqlToRel(String sql) {
    Objects.requireNonNull(sql);
    final SqlNode sqlQuery;
    final SqlToRelConverter.Config localConfig;
    try {
      sqlQuery = parseQuery(sql);
    } catch (RuntimeException | Error e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    final RelDataTypeFactory typeFactory = getTypeFactory();
    final Prepare.CatalogReader catalogReader =
        createCatalogReader(typeFactory);
    final SqlValidator validator =
        createValidator(
            catalogReader, typeFactory);
    final CalciteConnectionConfig calciteConfig = context.unwrap(CalciteConnectionConfig.class);
    if (calciteConfig != null) {
      validator.setDefaultNullCollation(calciteConfig.defaultNullCollation());
    }
    if (config == SqlToRelConverter.Config.DEFAULT) {
      localConfig = SqlToRelConverter.configBuilder()
          .withTrimUnusedFields(true).withExpand(enableExpand).build();
    } else {
      localConfig = config;
    }

    final SqlToRelConverter converter =
        createSqlToRelConverter(
            validator,
            catalogReader,
            typeFactory,
            localConfig);

    final SqlNode validatedQuery = validator.validate(sqlQuery);
    RelRoot root =
        converter.convertQuery(validatedQuery, false, true);
    assert root != null;
    if (enableDecorrelate || enableTrim) {
      root = root.withRel(converter.flattenTypes(root.rel, true));
    }
    if (enableDecorrelate) {
      root = root.withRel(converter.decorrelate(sqlQuery, root.rel));
    }
    if (enableTrim) {
      root = root.withRel(converter.trimUnusedFields(true, root.rel));
    }
    return root;
  }

  protected SqlToRelConverter createSqlToRelConverter(
      final SqlValidator validator,
      final Prepare.CatalogReader catalogReader,
      final RelDataTypeFactory typeFactory,
      final SqlToRelConverter.Config config) {
    final RexBuilder rexBuilder = new RexBuilder(typeFactory);
    RelOptCluster cluster =
        RelOptCluster.create(getFrameworkConfig(), rexBuilder);
    if (clusterFactory != null) {
      cluster = clusterFactory.apply(cluster);
    }
    return new SqlToRelConverter(null, validator, catalogReader, cluster,
        StandardConvertletTable.INSTANCE, config);
  }

  protected final RelDataTypeFactory getTypeFactory() {
    if (typeFactory == null) {
      typeFactory = createTypeFactory();
    }
    return typeFactory;
  }

  protected RelDataTypeFactory createTypeFactory() {
    return new SqlTypeFactoryImpl(RelDataTypeSystem.DEFAULT);
  }

  protected final RelOptPlanner getFrameworkConfig() {
    if (planner == null) {
      planner = createPlanner();
    }
    return planner;
  }

  public SqlNode parseQuery(String sql) throws Exception {
    final SqlParser.Config config =
        SqlParser.configBuilder().setConformance(getConformance()).build();
    SqlParser parser = SqlParser.create(sql, config);
    return parser.parseQuery();
  }

  public SqlConformance getConformance() {
    return conformance;
  }

  public SqlValidator createValidator(
      SqlValidatorCatalogReader catalogReader,
      RelDataTypeFactory typeFactory) {
    return new FarragoTestValidator(
        getOperatorTable(),
        catalogReader,
        typeFactory,
        getConformance());
  }

  public final SqlOperatorTable getOperatorTable() {
    if (opTab == null) {
      opTab = createOperatorTable();
    }
    return opTab;
  }

  /**
   * Creates an operator table.
   *
   * @return New operator table
   */
  protected SqlOperatorTable createOperatorTable() {
    final MockSqlOperatorTable opTab =
        new MockSqlOperatorTable(SqlStdOperatorTable.instance());
    MockSqlOperatorTable.addRamp(opTab);
    return opTab;
  }

  public Prepare.CatalogReader createCatalogReader(
      RelDataTypeFactory typeFactory) {
    MockCatalogReader catalogReader;
    if (this.catalogReaderFactory != null) {
      catalogReader = catalogReaderFactory.create(typeFactory, true);
    } else {
      catalogReader = new MockCatalogReaderSimple(typeFactory, true);
    }
    return catalogReader.init();
  }

  public RelOptPlanner createPlanner() {
    return new MockRelOptPlanner(context);
  }

  public void assertConvertsTo(
      String sql,
      String plan) {
    assertConvertsTo(sql, plan, false);
  }

  public void assertConvertsTo(
      String sql,
      String plan,
      boolean trim) {
    String sql2 = getDiffRepos().expand("sql", sql);
    RelNode rel = convertSqlToRel(sql2).project();

    assert rel != null;
    assertValid(rel);

    if (trim) {
      final RelBuilder relBuilder =
          RelFactories.LOGICAL_BUILDER.create(rel.getCluster(), null);
      final RelFieldTrimmer trimmer = createFieldTrimmer(relBuilder);
      rel = trimmer.trim(rel);
      assert rel != null;
      assertValid(rel);
    }

    // NOTE jvs 28-Mar-2006:  insert leading newline so
    // that plans come out nicely stacked instead of first
    // line immediately after CDATA start
    String actual = NL + RelOptUtil.toString(rel);
    diffRepos.assertEquals("plan", plan, actual);
  }

  /**
   * Creates a RelFieldTrimmer.
   *
   * @param relBuilder Builder
   * @return Field trimmer
   */
  public RelFieldTrimmer createFieldTrimmer(RelBuilder relBuilder) {
    return new RelFieldTrimmer(getValidator(), relBuilder);
  }

  public DiffRepository getDiffRepos() {
    return diffRepos;
  }

  public SqlValidator getValidator() {
    final RelDataTypeFactory typeFactory = getTypeFactory();
    final SqlValidatorCatalogReader catalogReader =
        createCatalogReader(typeFactory);
    return createValidator(catalogReader, typeFactory);
  }

  public PlannerUtil withDecorrelation(boolean enableDecorrelate) {
    return this.enableDecorrelate == enableDecorrelate
        ? this
        : new PlannerUtil(diffRepos, enableDecorrelate, enableTrim,
        enableExpand, enableLateDecorrelate, catalogReaderFactory,
        clusterFactory, config, conformance, context);
  }

  public PlannerUtil withLateDecorrelation(boolean enableLateDecorrelate) {
    return this.enableLateDecorrelate == enableLateDecorrelate
        ? this
        : new PlannerUtil(diffRepos, enableDecorrelate, enableTrim,
        enableExpand, enableLateDecorrelate, catalogReaderFactory,
        clusterFactory, config, conformance, context);
  }

  public PlannerUtil withConfig(SqlToRelConverter.Config config) {
    return this.config == config
        ? this
        : new PlannerUtil(diffRepos, enableDecorrelate, enableTrim,
        enableExpand, enableLateDecorrelate, catalogReaderFactory,
        clusterFactory, config, conformance, context);
  }

  public PlannerUtil withTrim(boolean enableTrim) {
    return this.enableTrim == enableTrim
        ? this
        : new PlannerUtil(diffRepos, enableDecorrelate, enableTrim,
        enableExpand, enableLateDecorrelate, catalogReaderFactory,
        clusterFactory, config, conformance, context);
  }

  public PlannerUtil withExpand(boolean enableExpand) {
    return this.enableExpand == enableExpand
        ? this
        : new PlannerUtil(diffRepos, enableDecorrelate, enableTrim,
        enableExpand, enableLateDecorrelate, catalogReaderFactory,
        clusterFactory, config, conformance, context);
  }

  public PlannerUtil withConformance(SqlConformance conformance) {
    return new PlannerUtil(diffRepos, enableDecorrelate, false,
        enableExpand, enableLateDecorrelate, catalogReaderFactory,
        clusterFactory, config, conformance, context);
  }

  public PlannerUtil withCatalogReaderFactory(
      SqlTestFactory.MockCatalogReaderFactory factory) {
    return new PlannerUtil(diffRepos, enableDecorrelate, false,
        enableExpand, enableLateDecorrelate, factory,
        clusterFactory, config, conformance, context);
  }

  public PlannerUtil withClusterFactory(
      Function<RelOptCluster, RelOptCluster> clusterFactory) {
    return new PlannerUtil(diffRepos, enableDecorrelate, false,
        enableExpand, enableLateDecorrelate, catalogReaderFactory,
        clusterFactory, config, conformance, context);
  }

  public PlannerUtil withContext(Context context) {
    return new PlannerUtil(diffRepos, enableDecorrelate, false,
        enableExpand, enableLateDecorrelate, catalogReaderFactory,
        clusterFactory, config, conformance, context);
  }

  public boolean isLateDecorrelate() {
    return enableLateDecorrelate;
  }

  /**
   * Validator for testing.
   */
  private static class FarragoTestValidator extends SqlValidatorImpl {
    FarragoTestValidator(
        SqlOperatorTable opTab,
        SqlValidatorCatalogReader catalogReader,
        RelDataTypeFactory typeFactory,
        SqlConformance conformance) {
      super(opTab, catalogReader, typeFactory, conformance);
    }

    // override SqlValidator
    public boolean shouldExpandIdentifiers() {
      return true;
    }
  }
}
