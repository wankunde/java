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

package com.wankun.jvm.signal;

import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.Scanner;

public class RegisterTermSignal implements SignalHandler {

  public static void main(String[] args) {
    new RegisterTermSignal().run();
  }

  Signal sig = new Signal("TERM");
  private SignalHandler prevHandler;

  public void run() {
    Signal.handle(sig, (signal) -> {
      System.out.println("This is first handler");
    });
    prevHandler = Signal.handle(sig, this);
    new Scanner(System.in).nextLine();
  }

  @Override
  public void handle(Signal signal) {
    System.out.println("This is second handler");
    // first handler will deal with the second TERM signal
    Signal.handle(sig, prevHandler);
  }
}
