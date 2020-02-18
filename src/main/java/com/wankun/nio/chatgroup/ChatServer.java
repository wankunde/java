package com.wankun.nio.chatgroup;

import javafx.scene.control.SelectionMode;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-15.
 */
public class ChatServer {

  private Selector selector;

  public void init() {
    try {
      selector = Selector.open();
      ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
      serverSocketChannel.bind(new InetSocketAddress(7000));
      serverSocketChannel.configureBlocking(false);
      serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
      System.out.println("server has been started...");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void listen() {
    while (true) {
      try {
        int count = selector.select(2000);
        if (count == 0) {
          continue;
        }

        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext()) {
          SelectionKey key = iterator.next();
          if (key.isAcceptable()) {
            ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
            SocketChannel channel = ssChannel.accept();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
            channel.write(ByteBuffer.wrap("欢迎登录".getBytes()));
            System.out.println("新的客户端上线, name=" + channel.getRemoteAddress() +
                " channel hashCode:" + channel.hashCode());
          } else if (key.isReadable()) {
            receiveMessage(key);
          }

          iterator.remove();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void receiveMessage(SelectionKey key) {
    SocketChannel channel = (SocketChannel) key.channel();
    ByteBuffer buffer = (ByteBuffer) key.attachment();
    try {
      int readCount = channel.read(buffer);
      if (readCount <= 0) {
        channel.close();
        return;
      }

      buffer.flip();
      byte[] fromData = (channel.getRemoteAddress().toString() + " says:").getBytes();
      byte[] data = new byte[fromData.length + buffer.remaining()];
      System.arraycopy(fromData, 0, data, 0, fromData.length);
      buffer.get(data, fromData.length, buffer.remaining());
      // 取出数据，为了后面再次使用buffer，需要将buffer复位
      buffer.clear();

      Iterator<SelectionKey> iterator = selector.keys().iterator();
      while (iterator.hasNext()) {
        Channel otherChannel = iterator.next().channel();
        if (otherChannel instanceof SocketChannel && otherChannel != channel) {
          ((SocketChannel) otherChannel).write(ByteBuffer.wrap(data));
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
      try {
        channel.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

  }

  public static void main(String[] args) {
    ChatServer chatServer = new ChatServer();
    chatServer.init();
    chatServer.listen();
  }
}
