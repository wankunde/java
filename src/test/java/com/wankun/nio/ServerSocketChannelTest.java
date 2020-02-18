package com.wankun.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-12.
 *
 * * 数据可以通过循环读写到buffer, 也可以一次性读写到buffer数组，这样好像更方便
 * * 通过Channel操作过buffer之后一定要flip，才能进行再次读写
 */
public class ServerSocketChannelTest {

  @Test
  public void testScatteringAndGathering() throws IOException {
    InetSocketAddress socketAddress = new InetSocketAddress(7000);
    ServerSocketChannel serverSocketChannel =
        ServerSocketChannel.open()
            .bind(socketAddress);
    SocketChannel socketChannel = serverSocketChannel.accept();

    ByteBuffer[] buffers = new ByteBuffer[2];
    buffers[0] = ByteBuffer.allocate(3);
    buffers[1] = ByteBuffer.allocate(5);

    // 读取数据
    int messageLength = 8;
    int byteRead = 0;
    while (byteRead < messageLength) {
      long r = socketChannel.read(buffers);
      byteRead += r;
      Arrays.stream(buffers).forEach(buffer -> {
        System.out.println("buffer position=" + buffer.position() + ", limit=" + buffer.limit());
      });
    }

    //
    Arrays.stream(buffers).forEach(buffer -> buffer.flip());

    // 数据回显到客户端
    socketChannel.write(buffers);

    Arrays.stream(buffers).forEach(buffer -> buffer.clear());

  }

  @Test
  public void testSocketChannel() {

    InetSocketAddress socketAddress = new InetSocketAddress(7000);

    try (SocketChannel socketChannel = SocketChannel.open(socketAddress)) {
      ByteBuffer writeBuffer = ByteBuffer.allocate(8);
      writeBuffer.put("Hello AB".getBytes());
      // 上面是向buffer填充数据，发送数据前需要先flip一下，否则读取不到
      writeBuffer.flip();
      socketChannel.write(writeBuffer);

      // 读取服务器端返回结果
      writeBuffer.clear();
      socketChannel.read(writeBuffer);
      writeBuffer.flip();
      System.out.println(new String(writeBuffer.array()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
