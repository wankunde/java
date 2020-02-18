package com.wankun.nio.chatgroup;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-15.
 */
public class ChatClient {

  private Selector selector;
  private SocketChannel channel;

  public void connectServer() {
    try {
      channel = SocketChannel.open();
      channel.configureBlocking(false);
      if (!channel.connect(new InetSocketAddress("127.0.0.1", 7000))) {
        while (!channel.finishConnect()) {
          Thread.currentThread().sleep(100);
        }
      }

      selector = Selector.open();
      channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
      System.out.println("client connect to server successful ...");
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void sendMessage() {
    Scanner scanner = new Scanner(System.in);
    String line;
    while ((line = scanner.nextLine()) != null) {
      try {
        System.out.println("发送消息: " + line);
        channel.write(ByteBuffer.wrap(line.getBytes()));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void receiveMessage() {
    new Thread(() -> {
      while (true) {
        try {
          int count = selector.select(2000);
          if (count == 0) {
            continue;
          }

          Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
          while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if (key.isReadable()) {
              SocketChannel channel = (SocketChannel) key.channel();
              ByteBuffer buffer = (ByteBuffer) key.attachment();
              int readCount = channel.read(buffer);
              if (readCount <= 0) {
                channel.close();
                return;
              }

              buffer.flip();
              byte[] data = new byte[buffer.remaining()];
              buffer.get(data);
              System.out.println("接收到消息:" + new String(data));
              buffer.clear();
            }

            iterator.remove();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  public static void main(String[] args) {
    ChatClient chatClient = new ChatClient();
    chatClient.connectServer();
    chatClient.receiveMessage();
    chatClient.sendMessage();
  }

}
