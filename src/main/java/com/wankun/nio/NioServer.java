package com.wankun.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-14.
 */
public class NioServer {

  public static void main(String[] args) throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.configureBlocking(false);

    serverSocketChannel.bind(new InetSocketAddress(7000));
    Selector selector = Selector.open();

    // 如果需要使用selector模型，前面必须要先设置为非阻塞模式
    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    while (true) {
      // selector 等待1秒
      if (selector.select(2000) == 0) {
        System.out.println("server wait for connection...");
        continue;
      }

      Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        if (key.isAcceptable()) {
          // 也可以通过 key.channel() 来获取serverSocketChannel 并操作
          SocketChannel socketChannel = serverSocketChannel.accept();
          socketChannel.configureBlocking(false);
          socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
          System.out.println("创建新的socketChannel, hashCode:" + socketChannel.hashCode());
        } else if (key.isReadable()) {
          SocketChannel channel = (SocketChannel) key.channel();
          ByteBuffer buffer = (ByteBuffer) key.attachment();
          // 客户端连接断开时，会发送数据到服务器端，所以会接收到 OP_READ 事件，导致死循环
          // 如果读取的结果为0, 判断为断开连接，关闭对应的channel
          if (channel.read(buffer) == 0) {
            channel.close();
          } else {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            System.out.println("Server receive data:" + new String(bytes));
          }
        }
        // 这里需要使用迭代器的remove方法来删除key
        // 在处理 selectedKeys set的过程中，可能又有了新的事件加入，
        // 那在外层循环中如果和来确定每次处理完后，剩余的增量key呢？
        // 通过迭代器可以轻松实现
        iterator.remove();
      }
    }
  }
}
