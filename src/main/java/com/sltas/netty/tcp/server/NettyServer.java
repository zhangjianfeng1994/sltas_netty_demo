package com.sltas.netty.tcp.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * @author ZJF
 */
public class NettyServer {

	private int port;

	public NettyServer(int port){
		this.port = port;
	}
	public void start(){
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup();

		try {
			//创建ServerBootstrap实例
			ServerBootstrap b = new ServerBootstrap();
			//初始化ServerBootstrap的线程组
			b.group(bossGroup, workerGroup)
					//设置将要被实例化的ServerChannel类
					.channel(NioServerSocketChannel.class)
					//在ServerChannelInitializer中初始化ChannelPipeline责任链，并添加到serverBootstrap中
					.childHandler(new ServerChannelInitializer())
					//标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
					.option(ChannelOption.SO_BACKLOG, 128)
					//是否启用心跳保活机机制
					.childOption(ChannelOption.SO_KEEPALIVE, true);
			ChannelFuture future = b.bind(port).sync();
			System.out.println("NettyServer start listen at " + port );
			future.channel().closeFuture().sync();
		} catch (Exception e) {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}


	public static void main(String[] args) throws Exception {
		new NettyServer(8088).start();
	}

}
