package com.wankun.nio.netty.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


/**
 * @author kun.wan, <kun.wan@leyantech.com>
 * @date 2020-02-18.
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {


  public static Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

  /**
   *
   * @param ctx 上下文对象，含有 管道pipeline, 通道 channel, 地址
   * @param msg 客户度发送的数据
   * @throws Exception
   */
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    logger.info("服务器响应线程名:" + Thread.currentThread().getName());
    logger.info("server ctx = " + ctx); // ctx 上下文
    Channel channel = ctx.channel(); // 网络通信相关的属性在这里
    ChannelPipeline pipeline = ctx.pipeline(); // DefaultChannelPipeline 双向链表
    ByteBuf buf = (ByteBuf) msg;
    logger.info("客户端发送的消息是:" + buf.toString(CharsetUtil.UTF_8));
    logger.info("客户端地址:" + channel.remoteAddress());

//    // 用户自定义普通任务
//    ctx.channel().eventLoop().execute(() -> {
//      try {
//        Thread.sleep(10 * 1000);
//        ctx.writeAndFlush(
//            Unpooled.copiedBuffer("hello, client, this is aync message~~", CharsetUtil.UTF_8));
//        logger.info("async job1 finished.");
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    });
//    // 用户自定义普通任务
//    ctx.channel().eventLoop().execute(() -> {
//      try {
//        Thread.sleep(20 * 1000);
//        ctx.writeAndFlush(
//            Unpooled.copiedBuffer("hello, client, this is aync message2~~", CharsetUtil.UTF_8));
//        logger.info("async job2 finished.");
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//    });

    // 用户自定义普通任务
    ctx.channel().eventLoop().schedule(() -> {
      try {
        Thread.sleep(7 * 1000);
        ctx.writeAndFlush(
            Unpooled.copiedBuffer("hello, client, this is aync message3~~", CharsetUtil.UTF_8));
        logger.info("async schedule job finished.");
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }, 3, TimeUnit.SECONDS);
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.writeAndFlush(Unpooled.copiedBuffer("hello, client~~", CharsetUtil.UTF_8));
  }

  /**
   * 异常处理，一般要关闭通道
   *
   * @param ctx
   * @param cause
   * @throws Exception
   */
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    ctx.close();
  }
}
