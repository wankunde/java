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

package com.wankun.bytes;

import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;
import java.util.Arrays;

public class ByteBufferTest {

  public static void main(String[] args) {
    // Declaring the capacity of the ByteBuffer
    int capacity = 5;

    // Creating the ByteBuffer
    try {
      // creating object of ByteBuffer
      // and allocating size capacity
      ByteBuffer bb1 = ByteBuffer.allocate(capacity);

      // putting the value in ByteBuffer
      bb1.put((byte) 10);
      bb1.put((byte) 20);

      // print the ByteBuffer
      System.out.println("Original ByteBuffer: " + Arrays.toString(bb1.array()));
      System.out.println("position:  " + bb1.position());
      System.out.println("capacity:  " + bb1.capacity());
      System.out.println("limit:  " + bb1.limit());


      // Creating a shared subsequence buffer
      // of given ByteBuffer
      // using slice() method
      ByteBuffer bb2 = bb1.slice();
      // print the shared subsequence buffer
      System.out.println("shared subsequence ByteBuffer: " + Arrays.toString(bb2.array()));
      System.out.println("position:  " + bb2.position());
      System.out.println("capacity:  " + bb2.capacity());
      System.out.println("limit:  " + bb1.limit());

      bb2.put((byte) 30);
      System.out.println("Add new byte: " + Arrays.toString(bb2.array()));
      System.out.println("position:  " + bb2.position());
      System.out.println("capacity:  " + bb2.capacity());
      System.out.println("limit:  " + bb1.limit());
    } catch (IllegalArgumentException e) {
      System.out.println("IllegalArgumentException catched");
    } catch (ReadOnlyBufferException e) {
      System.out.println("ReadOnlyBufferException catched");
    }
  }
}
