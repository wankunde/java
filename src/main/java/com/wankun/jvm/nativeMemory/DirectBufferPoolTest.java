package com.wankun.jvm.nativeMemory;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * 0. 程序测试工具 JDK Mission Control，可以分析本机 JVM 进程，也可以通过JMX连接服务器进程。
 *    服务器进程需要开启JMX: -Dcom.sun.management.jmxremote.port=7091 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false
 * 1. 如果我们使用ON_HEAP 分配，分配的内存空间受JVM管理，Old区直接显示已经使用1G
 * 2. 如果我们使用Direct方式分配，内存空间既不在ON_HEAP Pool，也不在 NON_HEAP Pool，空间分配在Direct Pool中，可以通过MBean java.nio.BufferPool.direct 的 MemoryUsed 查看
 * 3. 程序启动参数: -Xmx2048m -XX:NativeMemoryTracking=summary -XX:+UnlockDiagnosticVMOptions -XX:+PrintNMTStatistics,
 *    ON_HEAP方式启动时，如果是用 -Xmx1512m 参数，程序无法启动，因为程序启动时的committed内存要超过1512m
 */
public class DirectBufferPoolTest {

    public static void main(String[] args) throws InterruptedException {
//        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 1024);
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024 * 1024 * 1024);

        for (int i = 0; i < 1024; i++) {
            byte[] oneM = new byte[1024 * 1024];
            for (int j = 0; j < oneM.length; j++) {
                oneM[j] = 0xf;
            }
            buffer.put(oneM);
            Thread.sleep(1000);
            System.out.println(new Date() + ":" + buffer.position());
        }
    }
}

