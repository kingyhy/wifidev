package com.wifi.control;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.dom4j.DocumentException;

import com.wifi.util.msg.AppMessage;
import com.wifi.util.msg.MessageBuilder;

public class AppServer implements Runnable {

	final static int APP_UDP_PORT = 61000;
	final static int UDP_BUFFER_LEN = 200;
	final static int UDP_BING_RETRY_TIMES = 10;
	@Override
	public void run() {
		udpServer4App();
	}

	private void udpServer4App(){
		System.out.println("Starting UDP server for app.");
		DatagramSocket  server;
		int retry = 0;
		while (true) {
			try {
				server = new DatagramSocket(APP_UDP_PORT);
				break;
			} catch (Exception e) {
				retry++;
				System.err.println("Fail to bing udp port: " + APP_UDP_PORT + " retry [" + retry + "/10]");
				System.err.println(e.getMessage());
				if (retry >= 10) {
					server = null;
					break;
				}
			}
		}
		
		if (server == null) {
			System.err.println("Server error!");
			return;
		}
		
        byte[] recvBuf = new byte[UDP_BUFFER_LEN];
        while(true)
        {
        	try {
        		System.out.println("wait something...");
        		DatagramPacket recvPacket 
                	= new DatagramPacket(recvBuf , recvBuf.length);
	            server.receive(recvPacket);
	            System.out.println("rcv something...");
	            String recvStr = new String(recvPacket.getData() , 0 , recvPacket.getLength());
	            
	            MessageBuilder msgBuilder = new MessageBuilder();
	            AppMessage msg = msgBuilder.setXMLText(recvStr).buildAppMessage();
	            
	            int port = recvPacket.getPort();
	            InetAddress addr = recvPacket.getAddress();
	            msg.getHeader().setSrcIp("10.10.101.101");
	            String sendStr = msg.toXmlText();
	            byte[] sendBuf;
	            sendBuf = sendStr.getBytes();
	            DatagramPacket sendPacket 
                	= new DatagramPacket(sendBuf , sendBuf.length , addr , port );
	            server.send(sendPacket);
			} catch (Exception e) {
				System.err.println("   ");
			}
            
        }
    }
}
