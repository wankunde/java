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

package com.wankun.jit.inlining;

public class ConsecutiveNumbersSum {
  private long totalSum;
  private int totalNumbers;

  public ConsecutiveNumbersSum(int totalNumbers) {
    this.totalNumbers = totalNumbers;
  }

  public long getTotalSum() {
    totalSum = 0;
    for (int i = 0; i < totalNumbers; i++) {
      totalSum += i;
    }
    return totalSum;
  }

  private static long calculateSum(int n) {
    return new ConsecutiveNumbersSum(n).getTotalSum();
  }

  private static int NUMBER_OF_ITERATORS = 15000;

  public static void main(String[] args) {
    for (int i = 0; i < NUMBER_OF_ITERATORS; i++) {
//      new ConsecutiveNumbersSum(i).getTotalSum();
      calculateSum(i);
    }
  }
}
