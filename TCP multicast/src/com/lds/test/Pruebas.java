package com.lds.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

import javax.imageio.spi.ServiceRegistry;

import org.roblybarger.ServiceDescription;
import org.roblybarger.ServiceResponder;


public class Pruebas {

	public static final String SERVICE_NAME = "discoveryDemo";
	public static final String SERVICE_INSTANCE_NAME_1 = "Demo_Server_1";
	public static final String SERVICE_INSTANCE_NAME_2 = "Demo_Server_2";
	public static final String SERVICE_INSTANCE_NAME_3 = "Demo_Server_3";
	
	private static ServiceResponder responder1;
	private static ServiceResponder responder2;
	private static ServiceResponder responder3;
	
	public static void main(String[] args) throws IOException, InterruptedException 
	{
		 ServerSocket server1 = new ServerSocket();
		 server1.bind(new InetSocketAddress(InetAddress.getLocalHost(), 0));
		 ServerSocket server2 = new ServerSocket();
		 server2.bind(new InetSocketAddress(InetAddress.getLocalHost(), 0));
		 ServerSocket server3 = new ServerSocket();
		 server3.bind(new InetSocketAddress(InetAddress.getLocalHost(), 0));
		 
		 ServiceDescription descriptor1 = new ServiceDescription();
		 ServiceDescription descriptor2 = new ServiceDescription();
		 ServiceDescription descriptor3 = new ServiceDescription();
		 
		 descriptor1.setAddress(server1.getInetAddress());
		 descriptor1.setPort(server1.getLocalPort());
		 descriptor1.setInstanceName(SERVICE_INSTANCE_NAME_1);
		 
		 descriptor2.setAddress(server2.getInetAddress());
		 descriptor2.setPort(server2.getLocalPort());
		 descriptor2.setInstanceName(SERVICE_INSTANCE_NAME_2);
		 
		 descriptor3.setAddress(server3.getInetAddress());
		 descriptor3.setPort(server3.getLocalPort());
		 descriptor3.setInstanceName(SERVICE_INSTANCE_NAME_3);
		 
		 responder1 = new ServiceResponder(SERVICE_NAME);
		 responder1.setDescriptor(descriptor1);
		 responder1.addShutdownHandler();
		 responder1.startResponder();
		 
		 responder2 = new ServiceResponder(SERVICE_NAME);
		 responder2.setDescriptor(descriptor2);
		 responder2.addShutdownHandler();
		 responder2.startResponder();
		 
		 responder3 = new ServiceResponder(SERVICE_NAME);
		 responder3.setDescriptor(descriptor3);
		 responder3.addShutdownHandler();
		 responder3.startResponder();
				
		 System.out.println("started!!!");
		 Thread.currentThread().join();
	}
}
