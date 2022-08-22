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

package com.wankun.bytecode;

public class TestLocalVariable {

  public static void main(String[] args) {
    TestLocalVariable obj = new TestLocalVariable();
    obj.testRelease(5);
  }

  private int state = 10;

  public int getState() {
    return this.state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public final boolean testRelease(int release) {
    int c = getState() - release;
    boolean free = false;
    if (c == 0) {
      free = true;
    }
    setState(c);
    return free;
  }

  /**
   *
   * javap -c TestLocalVariable.class
   *
   * Document:
   *  https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-6.html
   *
   * Tips:
   *   The <n> must be an index into the local variable array of the current frame
   *   Local variable array:
   *   0  release
   *   1  getState()
   *   2  c
   *   public final boolean testRelease(int);
   *     Code:
   *        0: aload_0             // Load reference from local variable
   *        1: invokevirtual #6                  // Method getState:()I
   *        4: iload_1             // Load int from local variable
   *        5: isub                // Subtract int
   *        6: istore_2            // Store int into local variable
   *        7: iconst_0            // Push int constant
   *        8: istore_3
   *        9: iload_2
   *       10: ifne          15   // Branch if int comparison with zero succeeds
   *       13: iconst_1
   *       14: istore_3
   *       15: aload_0
   *       16: iload_2
   *       17: invokevirtual #7                  // Method setState:(I)V
   *       20: iload_3
   *       21: ireturn
   */

}
