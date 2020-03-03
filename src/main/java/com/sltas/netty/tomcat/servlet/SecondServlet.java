package com.sltas.netty.tomcat.servlet;


import com.sltas.netty.tomcat.http.GPRequest;
import com.sltas.netty.tomcat.http.GPResponse;
import com.sltas.netty.tomcat.http.GPServlet;

public class SecondServlet extends GPServlet {

	public void doGet(GPRequest request, GPResponse response) throws Exception {
		this.doPost(request, response);
	}

	public void doPost(GPRequest request, GPResponse response) throws Exception {
		response.write("This is Second Serlvet");
	}

}
