package com.sapo.websockets;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{av}")
public class Chat {
	
    @OnOpen
    public void onConnectionOpen(Session session, @PathParam(value="av")String av) {
    	session.getUserProperties().put("av", av);
    	System.out.println("Socket open...");
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException, EncodeException {
    	String av = (String)session.getUserProperties().get("av");
    	for(Session s : session.getOpenSessions()){   	
    		if(s.getUserProperties().get("av").equals(av)){
    			s.getBasicRemote().sendObject(message);
    		}
    	}   	
    }
 
    @OnClose
    public void onConnectionClose(Session session) {
    	System.out.println("Socket closed...");
    }
    
}
