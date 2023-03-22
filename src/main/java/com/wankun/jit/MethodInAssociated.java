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

public class MethodInAssociated {

  public static void main(String[] args) {
    long start1=System.currentTimeMillis();
    for (int i=0;i<10000000;i++){
      int e=alSum(1,2,3,4);//调用嵌套方法多次计算（JIT自动内联进行方法体替换）
    }
    long end1=System.currentTimeMillis();
    System.out.println("JIT内置方法内联时间花费="+(end1-start1));
    long start2=System.currentTimeMillis();
    for (int i=0;i<10000000;i++){
      int e=alSum2(1,2,3,4);//多次计算（方法体替换成内联后的方法体，模拟JIT方法内联）
    }
    long end2=System.currentTimeMillis();
    System.out.println("模拟JIT内置方法内联时间花费="+(end2-start2));
  }
  public static int alSum(int a,int b,int c,int d){
    return sum(a,b)+sum(c,d);//在alSum方法中嵌套调用sum方法
  }
  public static int  sum(int a,int b){
    return a+b;
  }
  public static int alSum2(int a,int b,int c,int d) {
    return a + b + c + d;
  }
}
