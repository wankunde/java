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

package com.wankun.jvm;

public class OutOfMemoryErrorTest {

  public static void main(String[] args) throws InterruptedException {
    byte[][] bs = new byte[10][];
    for (int i = 0; i < bs.length; i++) {
      System.out.println("Allocate memory for buffer " + i);
      if (i > 0) {
        bs[i - 1] = null;
      }
      bs[i] = new byte[1024 * 1024 * 20];
      Thread.sleep(2000L);
    }
  }
}
