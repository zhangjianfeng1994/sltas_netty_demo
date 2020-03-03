package com.sltas.netty.tomcat.servlet;


import com.sltas.netty.tomcat.http.GPRequest;
import com.sltas.netty.tomcat.http.GPResponse;
import com.sltas.netty.tomcat.http.GPServlet;

public class FirstServlet extends GPServlet {

	@Override
	public void doGet(GPRequest request, GPResponse response) throws Exception {
		this.doPost(request, response);
	}

	@Override
	public void doPost(GPRequest request, GPResponse response) throws Exception {
		System.out.println("有访问请求 First Serlvet");
		response.write("This is First Serlvet");
	}

}
