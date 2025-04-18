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

public class InlineCommonMethod {

  public static InlineCommonMethod obj = new InlineCommonMethod();

  public static void main(String[] args) {
    for (int i = 0; i < 10; i++) {

      obj.fn1();
    }
  }

  private long fn1() {
    long sum = fn2(100);
    return sum > 0 ? sum : -sum;
  }


  public long fn2(int i) {
    long sum = 0;
    for (int j = 0; j < i; j++) {
      sum += j;
    }
    return sum;
  }
}
