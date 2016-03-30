package com.wankun.jvm.jmx;

/**
 * Created by WANKUN603 on 2016-03-30.
 * <p>
 * start up parameter :
 * -Dcom.sun.management.jmxremote.port=9999 -- 指定端口
 * -Dcom.sun.management.jmxremote.authenticate=false – 指定是否需要密码验证
 * -Dcom.sun.management.jmxremote.ssl=false – 指定是否使用 SSL 通讯
 */
public class RemoteInstance {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            System.out.println("current time : " + System.currentTimeMillis());
            Thread.currentThread().sleep(1000);
        }
    }
}
