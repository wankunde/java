package com.wankun.jvm.nativeMemory;

import sun.misc.Unsafe;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * https://dzone.com/articles/native-memory-allocation-in-examples
 *
 * 1. Unsafe 分配的内存在JVM的各个Pool中都看不到
 * 2. allocateMemory()之后也不是立即可以观察到
 * 3. 当对该内存进行 touch 之后，内存的使用在操作系统中可以看到（1G内存空间使用）
 * 4. free() 后，系统内存空间被释放
 */
public class UnsafeAllocateTest {
    public static void main(String[] args) throws Exception {
        printNativeMemorySummary();

        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        // 1. Print PageSize
        System.out.println("PAGE SIZE: " + unsafe.pageSize());
        System.in.read();
        printNativeMemorySummary();


        // 2. Allocates 1024MB of native memory
        long memoryBlock = 1024 * 1024 * 1024;
        long address = unsafe.allocateMemory(memoryBlock);

        System.in.read();
        printNativeMemorySummary();

        /*
         * Touches the allocated pages:
         * writes one byte to every page to ensure that the page will be physically backed/mapped in memory
         */
        long currentAddress = address;
        while (currentAddress < (address + memoryBlock)) {
            unsafe.putByte(currentAddress, (byte) 0);
            currentAddress += unsafe.pageSize();
        }
        System.out.println("MEMORY TOUCHED");
        System.in.read();
        printNativeMemorySummary();


        /*
         * STAGE 4
         * Frees the allocated memory.
         */
        unsafe.freeMemory(address);
        System.out.println("DE-ALLOCATED");
        System.in.read();
        printNativeMemorySummary();
    }


    /**
     * Using
     *
     * invokeDiagnosticCommand("vmFlags")
     *
     * invokeDiagnosticCommand("vmNativeMemory");
     *
     * @param operationName
     * @return
     * @throws Exception
     */
    public static Object invokeDiagnosticCommand(String operationName) throws Exception {
        Thread.sleep(1000);
        ObjectName objectName = new ObjectName("com.sun.management:type=DiagnosticCommand");
        MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        String[] signature = new String[]{String[].class.getName()};
        Object[] params = new Object[1];
        return mbeanServer.invoke(objectName, operationName, params, signature);
    }

    public static void printNativeMemorySummary() throws Exception {
        String resp = (String) invokeDiagnosticCommand("vmNativeMemory");
        Pattern[] patterns = {
                Pattern.compile("^(Total):.*reserved=(\\d+)KB.*committed=(\\d+)KB"),
                Pattern.compile("^-\\s+(.*)\\s+\\(reserved=(\\d+)KB.*committed=(\\d+)KB\\)")
        };
        Arrays.stream(resp.split(("\\r?\\n"))).forEach(line -> {
            for (Pattern pattern : patterns) {
                Matcher m = pattern.matcher(line);
                if (m.find()) {
                    System.out.println(m.group(1));
                    System.out.println(m.group(3));
                }
            }

        });
        System.out.println(resp);
    }
}
