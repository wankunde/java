package com.wankun.nio.netty.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-18.
 */
public class NettyClient {

  public static Logger logger = LoggerFactory.getLogger(NettyClient.class);

  public static void main(String[] args) {
    // 客户端事件循环组
    EventLoopGroup group = new NioEventLoopGroup();

    // 客户端启动对象
    Bootstrap bootstrap = new Bootstrap();
    bootstrap.group(group) // 设置现场组
        .channel(NioSocketChannel.class) // 设置客户端通道实现类
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new NettyClientHandler());
          }
        });

    try {
      // 客户端连接服务器
      ChannelFuture channelFutue = bootstrap.connect("127.0.0.1", 7000).sync();

      //给关闭通道进行监听
      channelFutue.channel().closeFuture().sync();

      logger.info("客户端 is ok ....");

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      group.shutdownGracefully();
    }

  }
}
