package com.sapo.websockets;

import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

@ClientEndpoint
public class StockWSClient {
Session userSession = null;

public StockWSClient(URI endpointURI) {
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, endpointURI);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
		 
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		// TODO Auto-generated method stub
	}
	@OnMessage
	public void onMessage(String message) {
		// TODO Auto-generated method stub
	}
	
	@OnError
	public void onError(Session session, Throwable throwable) {
		// TODO Auto-generated method stub
	}
	@OnOpen
	public void onOpen(Session session, EndpointConfig endpointConfig) {
		userSession=session;
	}
	
	public void sendMessage(String message) {
		if(userSession!=null)
			this.userSession.getAsyncRemote().sendText(message);
		else
			System.out.println("EL PUTO SESSION NULL...");
	}
	
	
	
}
