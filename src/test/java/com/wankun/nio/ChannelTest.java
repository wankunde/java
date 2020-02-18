package com.wankun.nio;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-11.
 */
public class ChannelTest {

  //FileChannel   ServerSocketChannel   SocketChannel

  public static final String TEST_FILE_NAME = "out.txt";

  @Test
  public void testFileChannel() {
    String content = "hello FileChannel";
    ByteBuffer buffer = ByteBuffer.allocate(1024);

    try (FileOutputStream out = new FileOutputStream("out.txt");
        FileChannel channel = out.getChannel()) {
      for (byte b : content.getBytes()) {
        buffer.put(b);
      }

      buffer.flip();

      channel.write(buffer);

      //channel.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // 再次使用Buffer前必须要清空buffer
    buffer.clear();

    try (FileInputStream in = new FileInputStream(TEST_FILE_NAME);
        FileChannel channel = in.getChannel()) {
      int len = channel.read(buffer);
      // 从channel中读取出数据后，想使用buffer，必须要先切换读写模式
      buffer.flip();

      // 这里我们是知道需要从buffer读取多少数据，如果是参数传入的buffer，可以通过remaining方法查询
      // remaining = limit - position
      int bytesNeedRead = buffer.remaining();
      byte[] bytes = new byte[len];
      buffer.get(bytes);
      System.out.println(new String(bytes));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testFileCopy() throws IOException {
    ByteBuffer buffer = ByteBuffer.allocate(5);

    String inputFilePath = getClass().getResource("/School.json").getPath();
    try (
        FileInputStream fileInputStream = new FileInputStream(new File(inputFilePath));
        FileChannel inputFileChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(TEST_FILE_NAME));
        FileChannel outputFileChannel = fileOutputStream.getChannel()
    ) {
      while (true) {
        buffer.clear();
        int size = inputFileChannel.read(buffer);
        if (size == -1) {
          break;
        }
        buffer.flip();
        outputFileChannel.write(buffer);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    List<String> sourceContent = Files.readAllLines(Paths.get(inputFilePath));
    List<String> targetContent = Files.readAllLines(Paths.get(TEST_FILE_NAME));
    Assert.assertArrayEquals(sourceContent.toArray(), targetContent.toArray());
  }

  @Test
  public void testFileCopy2() throws IOException {
    String inputFilePath = getClass().getResource("/School.json").getPath();
    try (
        FileInputStream fileInputStream = new FileInputStream(new File(inputFilePath));
        FileChannel inputFileChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(TEST_FILE_NAME));
        FileChannel outputFileChannel = fileOutputStream.getChannel()
    ) {
      inputFileChannel.transferTo(0, inputFileChannel.size(), outputFileChannel);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    List<String> sourceContent = Files.readAllLines(Paths.get(inputFilePath));
    List<String> targetContent = Files.readAllLines(Paths.get(TEST_FILE_NAME));
    Assert.assertArrayEquals(sourceContent.toArray(), targetContent.toArray());
  }

  @Test
  public void testByteBuffer() {
    ByteBuffer buffer = ByteBuffer.allocate(64);
    buffer.putInt(100);
    buffer.putLong(9);
    buffer.putChar('万');
    buffer.putShort((short) 2);

    buffer.flip();
    Assert.assertEquals(100, buffer.getInt());
    Assert.assertEquals(9, buffer.getLong());
    Assert.assertEquals('万', buffer.getChar());
    Assert.assertEquals((short) 2, buffer.getShort());
  }

  @Test
  public void testMappedChannel() throws IOException {
    Files.createFile(Paths.get(TEST_FILE_NAME));
    Files.write(Paths.get(TEST_FILE_NAME), Collections.singleton("hello file channel"),
        new OpenOption[]{StandardOpenOption.WRITE});
    RandomAccessFile randomAccessFile = new RandomAccessFile(TEST_FILE_NAME, "rw");
    FileChannel randomAccessFileChannel = randomAccessFile.getChannel();
    MappedByteBuffer mappedBuffer = randomAccessFileChannel
        .map(MapMode.READ_WRITE, 0, randomAccessFileChannel.size());
    mappedBuffer.put(0, (byte) 'H');
    mappedBuffer.put(6, (byte) 'F');
    randomAccessFileChannel.close();

    String result = Files.readAllLines(Paths.get(TEST_FILE_NAME)).get(0);
    Assert.assertEquals("Hello File channel", result);
  }

  @After
  public void cleanTestFiles() throws IOException {
    Files.deleteIfExists(Paths.get(TEST_FILE_NAME));
  }
}
