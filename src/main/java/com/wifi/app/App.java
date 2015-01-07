package com.wifi.app;

/**
 *UDPServer
 *@author Winty wintys@gmail.com
 *@version 2008-12-15
 */
import java.io.*;
import java.net.*;

import org.dom4j.DocumentException;
import org.hibernate.Session;

import com.wifi.control.AppServer;
import com.wifi.model.Stock;
import com.wifi.persistence.HibernateUtil;
import com.wifi.util.msg.AppMessage;
import com.wifi.util.msg.MessageBuilder;

class App{

	static String testXML = 
			  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<appmsg>\n"
			+ "<head>"
			+ "<msgid>2</msgid>"
			+ "<hwid>123456789</hwid>"
			+ "<devmac>fafafafafafa</devmac>"
			+ "<magic>FUCK</magic>"
			+ "<license>2</license>"
			+ "</head>"
			+ "<body>"
			+ "<name>ggg</name>"
			+ "<pwd>123456</pwd>"
			+ "</body>"
			+ "</appmsg>";
    
	public static void main(String[] args)throws IOException, DocumentException, InterruptedException{
        System.out.println("Maven + Hibernate + MySQL");
        
		messageTest();
		Thread appSerThread = new Thread(new AppServer());
		appSerThread.run();
		
        while(true) {
            Thread.sleep(1000);
        }
    }
    
    static void messageTest() throws DocumentException {
    	MessageBuilder builder = new MessageBuilder();
    	AppMessage appMessage = builder.setXMLText(testXML).buildAppMessage();
    }
    
    void hibernateTest() {
    	Session session = HibernateUtil.getSessionFactory().openSession();
        
        session.beginTransaction();
        Stock stock = new Stock();
        
        stock.setStockCode("4716");
        stock.setStockName("GENMY");
        
        session.save(stock);
        
        session.getTransaction().commit();
    }
}
