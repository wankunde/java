package com.wankun.nio.netty.tcp;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-18.
 */
public class NettyServer {

  public static Logger logger = LoggerFactory.getLogger(NettyServer.class);

  public static void main(String[] args) {
    // boosGroup 只负责处理连接请求，真正的和客户端业务处理会交给workerGroup完成
    // 默认线程数 = 机器CPU个数 * 2
    NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    NioEventLoopGroup workerGroup = new NioEventLoopGroup(4);

    // 创建服务器端启动对象
    ServerBootstrap serverBootstrap = new ServerBootstrap();

    serverBootstrap.group(bossGroup, workerGroup) // 设置两个线程组
        .channel(NioServerSocketChannel.class)
        .option(ChannelOption.SO_BACKLOG, 128) // 设置线程队列得到的连接个数
        .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
        .childHandler(new ChannelInitializer<SocketChannel>() {

          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new NettyServerHandler());
          }
        });

    try {
      // 绑定端口，并同步
      ChannelFuture cf = serverBootstrap.bind(7000).sync();
      logger.info("server is ready...");

      // 服务器已经启动完毕，现在主现场开启对关闭通道的监听
      cf.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}
