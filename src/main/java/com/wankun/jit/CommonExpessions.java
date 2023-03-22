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

package com.wankun.jit;

public class CommonExpessions {
  public static void main(String[] args) {
    for (int i = 0; i < 1000000; i++) {
      sum(1, 2, 3);
    }
  }

  public static void sum(int a, int b, int c) {
    /*计算d的值时，由于是循环执行，每次传入的参数又不变，那么JIT就会对该表达式中的公共子表达式进行优化*/
    int d = (b * c) * 12 + a + (a + b * c);//其中c*b为公共子表达式
    /*所以上式等同于：int d=E*12+a+(a+E)  -------消除
     *               int d=E*13+a*2      -------代码再优化*/
    System.out.println(d);
  }
}
