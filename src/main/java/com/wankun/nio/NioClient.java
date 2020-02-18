package com.wankun.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-14.
 */
public class NioClient {

  public static void main(String[] args) {

    try (SocketChannel socketChannel = SocketChannel.open();) {
      socketChannel.configureBlocking(false);
      InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 7000);

      // connect方法: 当连接是非阻塞的，且连接正在进行中，返回false;
      // 连接建立成功，返回true，
      // 需要针对正在建立的连接进行等待
      if (!socketChannel.connect(inetSocketAddress)) {
        while (!socketChannel.finishConnect()) {
          System.out.println("连接建立过程中，请稍等...");
        }
      }

      // 连接建立成功，开始发送数据
      String data = "Hello Selector Server";
      ByteBuffer dataBuffer = ByteBuffer.wrap(data.getBytes());
      socketChannel.write(dataBuffer);
      // 客户端如果直接断开会发送空数据到服务器端，如果处理不好的话，会导致服务器端死循环
//      System.in.read();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
