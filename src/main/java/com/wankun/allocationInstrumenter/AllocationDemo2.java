package com.wankun.allocationInstrumenter;

import com.google.monitoring.runtime.instrumentation.ConstructorCallback;
import com.google.monitoring.runtime.instrumentation.ConstructorInstrumenter;

import java.lang.instrument.UnmodifiableClassException;

/**
 * java -cp /Users/wankun/ws/wankun/java/target/java-1.0.jar -javaagent:/Users/wankun/.m2/repository/com/google/code/java-allocation-instrumenter/java-allocation-instrumenter/3.0/java-allocation-instrumenter-3.0.jar com.wankun.allocationInstrumenter.AllocationDemo2
 * Constructing an element of type AllocationDemo2 with x = 0
 * Constructing an element of type AllocationDemo2 with x = 1
 * Constructing an element of type AllocationDemo2 with x = 2
 * Constructing an element of type AllocationDemo2 with x = 3
 * Constructing an element of type AllocationDemo2 with x = 4
 * Constructing an element of type AllocationDemo2 with x = 5
 * Constructing an element of type AllocationDemo2 with x = 6
 * Constructing an element of type AllocationDemo2 with x = 7
 * Constructing an element of type AllocationDemo2 with x = 8
 * Constructing an element of type AllocationDemo2 with x = 9
 * Constructed 10 instances of Test
 */
public class AllocationDemo2 {
    static int count = 0;

    int x;

    AllocationDemo2() {
        x = count;
    }

    public static void main(String[] args) {
        try {
            ConstructorInstrumenter.instrumentClass(
                    AllocationDemo2.class, new ConstructorCallback<AllocationDemo2>() {
                        @Override
                        public void sample(AllocationDemo2 t) {
                            System.out.println(
                                    "Constructing an element of type AllocationDemo2 with x = " + t.x);
                            count++;
                        }
                    });
        } catch (UnmodifiableClassException e) {
            System.out.println("Class cannot be modified");
        }

        for (int i = 0; i < 10; i++) {
            new AllocationDemo2();
        }
        System.out.println("Constructed " + count + " instances of Test");
    }
}
