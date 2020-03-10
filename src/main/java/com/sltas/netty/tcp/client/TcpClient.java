package com.sltas.netty.tcp.client;

import com.sltas.netty.tcp.codec.MessagePacketDecoder;
import com.sltas.netty.tcp.codec.MessagePacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class TcpClient {

	public static String HOST = "127.0.0.1";

	public static int PORT = 8088;

	public static Bootstrap bootstrap = getBootstrap();

	public static Channel channel = getChannel(HOST,PORT);

	public static final Bootstrap getBootstrap(){
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group).channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception{

				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("idleStateHandler",
						new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));
				//自定义编解码器
				pipeline.addLast(
						new MessagePacketDecoder(),
						new MessagePacketEncoder()
				);
				pipeline.addLast("handler",new TcpClientHandler());
			}
		});
		bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
		return bootstrap;
	}

	public static final Channel getChannel(String HOST, int PORT){
		Channel channel = null;
		try{
			channel = bootstrap.connect(HOST,PORT).sync().channel();
			System.out.println("连接成功");
		}catch (Exception e){
			System.out.println("连接server(IP{},PROT{})失败");
			return null;
		}
		return channel;
	}

	public static void sendMsg(Object msg) throws Exception{
		if(channel!=null){
			channel.writeAndFlush(msg).sync();
		}else{
			System.out.println("消息发送失败，连接尚未建立");
		}
	}


}
