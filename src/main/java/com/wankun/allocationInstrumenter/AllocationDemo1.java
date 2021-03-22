package com.wankun.allocationInstrumenter;

import com.google.monitoring.runtime.instrumentation.AllocationRecorder;
import com.google.monitoring.runtime.instrumentation.Sampler;

/**
 * run command: java -cp /Users/wankun/ws/wankun/java/target/java-1.0.jar -javaagent:/Users/wankun/.m2/repository/com/google/code/java-allocation-instrumenter/java-allocation-instrumenter/3.0/java-allocation-instrumenter-3.0.jar com.wankun.allocationInstrumenter.AllocationDemo1
 * console output:
 *
 * allocate object = foo type = java/lang/String size = 24
 * allocate object = foo type = java/lang/String size = 24
 * allocate object = foo type = java/lang/String size = 24
 * allocate object = foo type = java/lang/String size = 24
 * allocate object = foo type = java/lang/String size = 24
 * allocate object = foo type = java/lang/String size = 24
 * allocate object = foo type = java/lang/String size = 24
 * allocate object = foo type = java/lang/String size = 24
 * allocate object = foo type = java/lang/String size = 24
 * allocate object = foo type = java/lang/String size = 24
 * ...
 */
public class AllocationDemo1 {
    public static void main(String [] args) throws Exception {
        AllocationRecorder.addSampler(new Sampler() {
            public void sampleAllocation(int count, String desc, Object newObj, long size) {
                System.out.println("allocate object = " + newObj + " type = " + desc + " size = " + size);
                if (count != -1) { System.out.println("It's an array of size " + count); }
            }
        });
        for (int i = 0 ; i < 10; i++) {
            new String("foo");
        }
        for (int i = 0 ; i < 10; i++) {
            new AllocationDemo1();
        }
    }
}
