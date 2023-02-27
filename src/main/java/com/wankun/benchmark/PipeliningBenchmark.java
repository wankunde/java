/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wankun.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;

/**
 *
 * https://hg.openjdk.org/code-tools/jmh/file/tip/jmh-samples/src/main/java/org/openjdk/jmh/samples/
 *
 */
public class PipeliningBenchmark {

  static int BATCH_SIZE = 1000000;
  static int V = 500;
  static int[] src = new int[BATCH_SIZE];
  static int[] out = new int[BATCH_SIZE];
  static {
    Random r = new Random();
    for (int i = 0; i < BATCH_SIZE; i++) {
      src[i] = r.nextInt(1000);
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(value = 1)
  @Warmup(iterations = 2)
  @Measurement(iterations = 1)
  public void branch_version() {
    for (int i = 0, j = 0; i < BATCH_SIZE; i++) {
      if(src[i] < V)
        out[j++] = i;
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @Fork(value = 1)
  @Warmup(iterations = 2)
  @Measurement(iterations = 1)
  public void predicate_version() {
    for (int i = 0, j = 0; i < BATCH_SIZE; i++) {
      int flag = src[i] < V ? 1 : 0;
      out[j] = i;
      j += flag;
    }
  }

  public static void main(String[] args) throws RunnerException {
    PipeliningBenchmark runner = new PipeliningBenchmark();
    runner.testByRunner();
  }

  void testByRunner() throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(PipeliningBenchmark.class.getSimpleName())
        .forks(1)
        .build();


    new Runner(opt).run();
  }

  void localTest() {
    long t1 = System.currentTimeMillis();
    for (int i = 0; i < 1000; i++) {
      branch_version();
    }
    long t2 = System.currentTimeMillis();
    for (int i = 0; i < 1000; i++) {
      predicate_version();
    }
    long t3 = System.currentTimeMillis();
    System.out.println(t2 - t1);
    System.out.println(t3 - t2);
  }
}
