package com.wankun.jvm.jmx;

/**
 * Created by WANKUN603 on 2016-03-30.
 * <p/>
 * <p/>
 * This program must add $JAVA_HOME/lib/tools.jar in classpath.
 * <p/>
 * http://jiangnanguying.iteye.com/blog/539697
 */

import com.sun.tools.attach.VirtualMachine;

import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.lang.management.*;
import java.util.Properties;

public class JmxLocal {
    public static void main(String[] args) throws IOException {
        JmxLocal test = new JmxLocal();
        System.out.println("======================");
        test.oneJVMMBean();
        System.out.println("======================");
        test.remoteMBean();
//        test.attachJVMMBean();
    }

    public void oneJVMMBean() throws IOException {
        OperatingSystemMXBean osbean = ManagementFactory.getOperatingSystemMXBean();
        System.out.println(osbean.getArch());//操作系统体系结构
        System.out.println(osbean.getName());//操作系统名字
        System.out.println(osbean.getAvailableProcessors());//处理器数目
        System.out.println(osbean.getVersion());//操作系统版本
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.println(threadBean.getThreadCount());//总线程数

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(server, "java.lang:type=Runtime", RuntimeMXBean.class);

        // memory MX Bean
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        System.out.println(memoryMXBean.getHeapMemoryUsage());
        System.out.println(memoryMXBean.getNonHeapMemoryUsage());
    }

    public void remoteMBean() {
        try {
// connect to a separate VM's MBeanServer, using the JMX RMI functionality
            JMXServiceURL address = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9999/jmxrmi");
            JMXConnector connector = JMXConnectorFactory.connect(address);
            MBeanServerConnection mbs = connector.getMBeanServerConnection();
            ThreadMXBean threadBean = ManagementFactory.newPlatformMXBeanProxy
                    (mbs, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
            System.out.println(threadBean.getThreadCount());//线程数量
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 0. on one node but in different processes
     * 1. attach to a jvm and setup a jmx proxy.
     * 2. connect to jmx proxy by remote connect way.
     *
     * Note : Test Failed
     */
    public void attachJVMMBean() {
        try {
            //Attach 到5656的JVM进程上，后续Attach API再讲解
            VirtualMachine virtualmachine = VirtualMachine.attach("115912");

            //让JVM加载jmx Agent，后续讲到Java Instrutment再讲解
            String javaHome = virtualmachine.getSystemProperties().getProperty("java.home");
            String jmxAgent = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
            virtualmachine.loadAgent(jmxAgent, "com.sun.management.jmxremote");

            //获得连接地址
            Properties properties = virtualmachine.getAgentProperties();
            String address = (String) properties.get("com.sun.management.jmxremote.localConnectorAddress");

            //Detach
            virtualmachine.detach();

            JMXServiceURL url = new JMXServiceURL(address);
            JMXConnector connector = JMXConnectorFactory.connect(url);
            RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(connector
                    .getMBeanServerConnection(), "java.lang:type=Runtime", RuntimeMXBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
