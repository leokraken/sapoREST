package com.sapo.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.controllers.administradorController;
import com.sapo.entities.Administrador;

@Stateless
@Path("/administradores")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class administradores {
	@EJB
	private administradorController pc;
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Administrador> getAdministradors(){
		return pc.getAdministradores();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Administrador getAdministrador(@PathParam("id")String adminID){
		return pc.getAdministrador(adminID);
	}
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addAdministrador(Administrador admin){
	
		try{
			pc.addAdministrador(admin);
			return Response.ok().build();
			
		}catch(Exception E){
			return Response.serverError().entity("Administrador ya existe").build();		
		}
	
	}
	
	@PUT
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
	public Response updateAdministrador(@PathParam("id")String adminID, Administrador admin){
		pc.updateAdministrador(admin);
		return Response.ok().build();
	}

}
