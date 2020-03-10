package com.sltas.netty.tcp.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

import java.net.InetAddress;

public class TCPServerHandler extends ChannelInboundHandlerAdapter {

	//收到数据时调用
	@Override
	public  void channelRead(ChannelHandlerContext ctx, Object  msg) throws Exception {
		String msgrec = new String((byte[])msg,"UTF-8");
		System.out.println("客户端地址:"+ctx.channel().remoteAddress()+"; 消息:"+msgrec);
		String msgString = "你很好啊";
		byte[] bytes = msgString.getBytes("UTF-8");
		ctx.write(bytes);
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 当出现异常就关闭连接
		cause.printStackTrace();
		ctx.close();
	}


}
