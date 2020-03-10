package com.sltas.netty.tcp.server;

import com.sltas.netty.tcp.codec.MessagePacketDecoder;
import com.sltas.netty.tcp.codec.MessagePacketEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		ChannelPipeline pipeline = socketChannel.pipeline();
		//IdleStateHandler心跳机制,如果超时触发Handle中userEventTrigger()方法
		pipeline.addLast("idleStateHandler",
				new IdleStateHandler(15, 0, 0, TimeUnit.MINUTES));
		// netty基于分割符的自带解码器，根据提供的分隔符解析报文，这里是0x7e;1024表示单条消息的最大长度，解码器在查找分隔符的时候，达到该长度还没找到的话会抛异常
//        pipeline.addLast(
//                new DelimiterBasedFrameDecoder(1024, Unpooled.copiedBuffer(new byte[] { 0x7e }),
//                        Unpooled.copiedBuffer(new byte[] { 0x7e })));
		//自定义编解码器
		pipeline.addLast(
				new MessagePacketDecoder(),
				new MessagePacketEncoder()
		);
		//自定义Hadler
		pipeline.addLast("handler",new TCPServerHandler());
		//自定义Hander,可用于处理耗时操作，不阻塞IO处理线程
		//pipeline.addLast(group,"BussinessHandler",new BussinessHandler());
	}

}
