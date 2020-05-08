package com.wankun.nio.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-18.
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

  public static Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

  // 通道就绪
  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    logger.info("client " + ctx);
    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,Server ...", CharsetUtil.UTF_8));
  }

  //通道读取消息
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf buf = (ByteBuf) msg;
    logger.info("服务器回复的消息:" + buf.toString(CharsetUtil.UTF_8));
    logger.info("服务器回复的地址:" + ctx.channel().remoteAddress());
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
