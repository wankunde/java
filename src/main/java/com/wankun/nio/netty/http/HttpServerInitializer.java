package com.wankun.nio.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-03-01.
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    // 向管道加入处理器
    ChannelPipeline pipeline = ch.pipeline();
    pipeline.addLast("httpCodec", new HttpServerCodec());
    pipeline.addLast("myHttpServerHandler", new HttpServerHandler());
  }
}
