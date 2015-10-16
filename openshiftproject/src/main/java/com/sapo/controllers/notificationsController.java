package com.sapo.controllers;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.websockets.ChatClient;

@Singleton
@LocalBean
@Path("/notificaciones")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class notificationsController {

	ChatClient c = null;

    public notificationsController() throws URISyntaxException {
    	c = new ChatClient(new URI("ws://localhost:8080/openshiftproject/echo")); 	
    }

    @GET
	@Path("chat/{mensaje}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Chat(@PathParam(value="mensaje")String mensaje){	
		c.sendMessage(mensaje);
		return Response.ok().entity(mensaje).build();
	}
    
}
