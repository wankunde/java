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

public class EscapeAnalyze {
  public static void main(String[] args) {
    long start=System.currentTimeMillis();
    for (int i=0;i<1000000;i++){
      newObject();
    }
    long end =System.currentTimeMillis();
    System.out.println("expend:"+(end-start)+"ms");//未开启逃逸分析，expend=17ms，实例化一百万个Student对象。
    //开启逃逸分析，expend=5ms，实例化100541个对象，几乎少了十倍！
    try {
      Thread.sleep(100000);//为什么要进行线程的等待：等待状态使得方法运行未结束，对象还存在堆内存中
      //才可以进行命令行分析，否则方法执行结束，堆内存就销毁了，无法进行对比。
    }catch (Exception e){
      e.printStackTrace();
    }
  }
  public static void newObject(){
    Student student=new Student();//方法返回值为void，所以对象student没有逃逸，进行栈上内存分配优化。
  }
  static class Student {
  }
}
