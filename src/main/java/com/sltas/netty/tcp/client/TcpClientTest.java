package com.sltas.netty.tcp.client;

public class TcpClientTest {

	public static void main(String[] args) {
		try{
			String msg = "hello,我是张三";
			byte[] bytes = msg.getBytes("UTF-8");
			TcpClient.sendMsg(bytes);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
