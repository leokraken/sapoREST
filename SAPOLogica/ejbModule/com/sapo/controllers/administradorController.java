package com.sapo.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.entities.Administrador;

@Stateless
@LocalBean
@Path("/administradores")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class administradorController {

	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
    public administradorController() {

    }

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Administrador getAdministrador(String id){
    	return em.find(Administrador.class, id);
    }
    
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
    public List<Administrador> getAdministradores(){
    	TypedQuery<Administrador> query = em.createNamedQuery("Administrador.findAll",Administrador.class);
    	return query.getResultList();
    
    }
    
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAdministrador(Administrador admin){
		try{
        	em.persist(admin); 
        	em.flush();		
			return Response.ok().build();

		}catch(Exception e){
			return Response.serverError().entity("Administrador ya existe").build();
		}

    }
	
	@PUT
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public Response updateAdministrador(Administrador admin){
    	em.merge(admin);
		return Response.ok().build();
    }
     
}
