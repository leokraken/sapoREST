package com.sapo.websockets;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echo")
public class Chat {
  
	
    @OnOpen
    public void onConnectionOpen(Session session) {
    	
    	System.out.println("Socket open...");
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException, EncodeException {
    	for(Session s : session.getOpenSessions()){   	
    		s.getBasicRemote().sendObject("broadcast fucker");
    		//"Echo: "+message;    	
    	}   	
    }
 
    @OnClose
    public void onConnectionClose(Session session) {
    	System.out.println("Socket closed...");
    }
    
}
