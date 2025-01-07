//package com.wankun.calcite;
//
//import com.google.common.base.Preconditions;
//import com.google.common.collect.Lists;
//import org.apache.calcite.plan.Contexts;
//import org.apache.calcite.plan.RelOptPlanner;
//import org.apache.calcite.plan.RelOptUtil;
//import org.apache.calcite.plan.hep.HepPlanner;
//import org.apache.calcite.plan.hep.HepProgram;
//import org.apache.calcite.plan.hep.HepProgramBuilder;
//import org.apache.calcite.prepare.Prepare;
//import org.apache.calcite.rel.RelNode;
//import org.apache.calcite.rel.RelRoot;
//import org.apache.calcite.rel.logical.LogicalIntersect;
//import org.apache.calcite.rel.logical.LogicalUnion;
//import org.apache.calcite.rel.metadata.ChainedRelMetadataProvider;
//import org.apache.calcite.rel.metadata.DefaultRelMetadataProvider;
//import org.apache.calcite.rel.metadata.RelMetadataProvider;
//import org.apache.calcite.rel.rules.CoerceInputsRule;
//import org.apache.calcite.rel.type.RelDataTypeFactory;
//import org.apache.calcite.sql.SqlNode;
//import org.apache.calcite.sql.validate.SqlConformanceEnum;
//import org.apache.calcite.sql.validate.SqlValidator;
//import org.apache.calcite.sql2rel.RelDecorrelator;
//import org.apache.calcite.sql2rel.SqlToRelConverter;
//import org.apache.calcite.test.DiffRepository;
//import org.apache.calcite.test.SqlToRelTestBase;
//import org.apache.calcite.test.SqlToRelTestBase.TesterImpl;
//
//import java.util.List;
//
//public class PlannerDemo extends SqlToRelTestBase {
//
//  public static void main(String[] args) {
//    HepProgramBuilder programBuilder = HepProgram.builder();
//    programBuilder.addRuleClass(CoerceInputsRule.class);
//
//    HepPlanner planner =
//        new HepPlanner(
//            programBuilder.build());
//
//    planner.addRule(new CoerceInputsRule(LogicalUnion.class, false));
//    planner.addRule(new CoerceInputsRule(LogicalIntersect.class, false));
//    PlannerDemo plannerDemo = new PlannerDemo();
//    plannerDemo.checkPlanning(planner,
//        "(select name from dept union select ename from emp)"
//            + " intersect (select fname from customer.contact)");
//  }
//
//  /**
//   * Checks the plan for a SQL statement before/after executing a given
//   * program.
//   *
//   * @param program Planner program
//   * @param sql     SQL query
//   */
//  protected void checkPlanning(HepProgram program, String sql) {
//    checkPlanning(new HepPlanner(program), sql);
//  }
//
//  /**
//   * Checks the plan for a SQL statement before/after executing a given
//   * planner.
//   *
//   * @param planner Planner
//   * @param sql     SQL query
//   */
//  protected void checkPlanning(RelOptPlanner planner, String sql) {
//    checkPlanning(tester, null, planner, sql);
//  }
//
//  /**
//   * Checks that the plan is the same before and after executing a given
//   * planner. Useful for checking circumstances where rules should not fire.
//   *
//   * @param planner Planner
//   * @param sql     SQL query
//   */
//  protected void checkPlanUnchanged(RelOptPlanner planner, String sql) {
//    checkPlanning(tester, null, planner, sql, true);
//  }
//
//  /**
//   * Checks the plan for a SQL statement before/after executing a given rule,
//   * with a pre-program to prepare the tree.
//   *
//   * @param tester     Tester
//   * @param preProgram Program to execute before comparing before state
//   * @param planner    Planner
//   * @param sql        SQL query
//   */
//  protected void checkPlanning(Tester tester, HepProgram preProgram,
//                               RelOptPlanner planner, String sql) {
//    checkPlanning(tester, preProgram, planner, sql, false);
//  }
//
//  /**
//   * Checks the plan for a SQL statement before/after executing a given rule,
//   * with a pre-program to prepare the tree.
//   *
//   * @param tester     Tester
//   * @param preProgram Program to execute before comparing before state
//   * @param planner    Planner
//   * @param sql        SQL query
//   * @param unchanged  Whether the rule is to have no effect
//   */
//  protected void checkPlanning(SqlToRelTestBase.Tester tester, HepProgram preProgram,
//                               RelOptPlanner planner, String sql, boolean unchanged) {
//    final RelRoot root = tester.convertSqlToRel(sql);
//    final RelNode relInitial = root.rel;
//
//    List<RelMetadataProvider> list = Lists.newArrayList();
//    list.add(DefaultRelMetadataProvider.INSTANCE);
//    planner.registerMetadataProviders(list);
//    RelMetadataProvider plannerChain =
//        ChainedRelMetadataProvider.of(list);
//    relInitial.getCluster().setMetadataProvider(plannerChain);
//
//    RelNode relBefore;
//    if (preProgram == null) {
//      relBefore = relInitial;
//    } else {
//      HepPlanner prePlanner = new HepPlanner(preProgram);
//      prePlanner.setRoot(relInitial);
//      relBefore = prePlanner.findBestExp();
//    }
//
//    final String planBefore = NL + RelOptUtil.toString(relBefore);
//    System.out.println("planBefore");
//    System.out.println(planBefore);
////    SqlToRelTestBase.assertValid(relBefore);
//
//    planner.setRoot(relBefore);
//    RelNode r = planner.findBestExp();
//    if (tester.isLateDecorrelate()) {
//      final String planMid = NL + RelOptUtil.toString(r);
//      System.out.println("planMid");
//      System.out.println(planMid);
////      SqlToRelTestBase.assertValid(r);
//      r = RelDecorrelator.decorrelateQuery(r);
//    }
//    final String planAfter = NL + RelOptUtil.toString(r);
//    System.out.println("planAfter");
//    System.out.println(planAfter);
////    SqlToRelTestBase.assertValid(r);
//  }
//
//}
