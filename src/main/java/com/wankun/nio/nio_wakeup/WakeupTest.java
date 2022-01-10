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

package com.wankun.nio.nio_wakeup;

import java.io.IOException;
import java.nio.channels.Selector;

/**
 * Link: https://blog.csdn.net/qq_27384769/article/details/80650373
 * javac WakeupTest.java
 * strace -f -e write java WakeupTest
 */
public class WakeupTest {
  public static void main(String[] args) throws IOException {
    Selector selector = Selector.open();
    System.out.println("--before");
    selector.wakeup();
    selector.selectNow();  // 读取前一次写入的数据，保证多次wakeup的write 可以被正常调用
    selector.wakeup();
    selector.selectNow();
    selector.wakeup();
    System.out.println("--after");
  }
}
