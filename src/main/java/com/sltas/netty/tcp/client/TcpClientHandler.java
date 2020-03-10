package com.sltas.netty.tcp.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TcpClientHandler extends SimpleChannelInboundHandler<Object> {

	private Object response;

	public Object getResponse() {
		return response;
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		response = msg;
		String msgString = new String((byte[])msg);
		System.out.println("客户端收到消息:"+msgString);
		//ctx.write("你也好啊");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println("client exception is general");
	}

}
