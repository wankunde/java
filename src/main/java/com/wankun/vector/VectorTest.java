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

package com.wankun.vector;

import jdk.incubator.vector.VectorSpecies;
import jdk.incubator.vector.FloatVector;


import java.util.Arrays;

/**
 * java --add-modules=jdk.incubator.vector src/main/java/com/wankun/vector/VectorTest.java
 * java -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly --add-modules=jdk.incubator.vector src/main/java/com/wankun/vector/VectorTest.java > /tmp/a.log
 */
public class VectorTest {

    public static void main(String[] args) {
        final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

        float[] arr = new float[1_200_000];
        Arrays.fill(arr, 23.74F);

        var v8sum = FloatVector.zero(SPECIES);

        for (int i = 0; i < arr.length; i += SPECIES.length()) {
            var v8temp = FloatVector.fromArray(SPECIES, arr, i);
            v8sum = v8sum.add(v8temp);
        }
        System.out.println(v8sum);
    }
}
