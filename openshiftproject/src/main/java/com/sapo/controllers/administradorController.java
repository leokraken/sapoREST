package com.sapo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataPersona;
import com.sapo.entities.Administrador;

/*
 * Solo administradores auth
 * */

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
    public DataPersona getAdministrador(@PathParam(value="id")String id){
    	Administrador admin= em.find(Administrador.class, id);
    	DataPersona dp = new DataPersona();
		dp.setApellido(admin.getApellido());
    	dp.setNombre(admin.getNombre());
    	dp.setId(admin.getId());
    	return dp;
    }
    
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
    public List<DataPersona> getAdministradores(){
    	TypedQuery<Administrador> query = em.createNamedQuery("Administrador.findAll",Administrador.class);
    	List<DataPersona> ret = new ArrayList<DataPersona>();
    	List<Administrador> admins = query.getResultList();
    	for(Administrador a : admins){
        	DataPersona dp = new DataPersona();
    		dp.setApellido(a.getApellido());
        	dp.setNombre(a.getNombre());
        	dp.setId(a.getId());
    		ret.add(dp);
    	}
    	return ret;
    }
    
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAdministrador(DataPersona dp){
		try{
			Administrador login = em.find(Administrador.class, dp.getId());
			if(login==null){ 
				//creo usuario
				Administrador u = new Administrador();
				u.setApellido(dp.getApellido());
				u.setId(dp.getId());
				u.setNombre(dp.getNombre());
				u.setToken(dp.getToken());
	        	em.persist(u); 
	        	em.flush();	
	        	return Response.status(201).build(); //created					
			}else{
				login.setToken(dp.getToken());
				return Response.status(200).build();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Administrador ya existe").build();
		}

    }
	
	@PUT
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public Response updateAdministrador(@PathParam(value="id")String id,DataPersona dataadmin){
		Administrador admin = new Administrador();
    	admin.setApellido(dataadmin.getApellido());
    	admin.setNombre(dataadmin.getNombre());
    	admin.setId(dataadmin.getId());
    	
		em.merge(admin);
		return Response.ok().build();
    }

	@DELETE
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public Response deleteAdministrador(@PathParam(value="id")String id){
		Administrador a = em.find(Administrador.class, id);
		if(a!=null)
			em.remove(a);
		return Response.status(200).build();
	}
	
}
