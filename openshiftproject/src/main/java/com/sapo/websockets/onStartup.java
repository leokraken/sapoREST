package com.sapo.websockets;
import java.net.URISyntaxException;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sapo.interfaces.IstockController;

@WebListener
public class onStartup implements ServletContextListener {

	
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	
        System.out.println("Starting up! Initialize all websockets...");      
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Shutting down!");       
    }
}