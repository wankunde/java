package com.wankun.nio.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-03-01.
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

  public static Logger logger = LoggerFactory.getLogger(HttpServerHandler.class);

  /**
   * 读取客户端数据
   * @param ctx
   * @param msg
   * @throws Exception
   */
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

    // 客户端类型
    if (msg instanceof HttpRequest) {
      HttpRequest request = (HttpRequest) msg;
      if (request.uri().equals("/favicon.ico")) {
        logger.info("请求了/favicon.ico, 直接返回～～");
        return;
      }
      logger.info("msg type:" + msg.getClass());
      logger.info("client address: " + ctx.channel().remoteAddress());

      ByteBuf buf = Unpooled.copiedBuffer("hello, I am server", CharsetUtil.UTF_8);
      DefaultFullHttpResponse response = new DefaultFullHttpResponse(
          HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
      response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
      response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
      ctx.writeAndFlush(response);
    }
  }
}
