package com.sapo.controllers;

import java.util.ArrayList;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataPersona;
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
    public Response addAdministrador(DataPersona dataadmin){
		try{
	    	Administrador admin = new Administrador();
	    	admin.setApellido(dataadmin.getApellido());
	    	admin.setNombre(dataadmin.getNombre());
	    	admin.setId(dataadmin.getId());
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
    public Response updateAdministrador(@PathParam(value="id")String id,DataPersona dataadmin){
		Administrador admin = new Administrador();
    	admin.setApellido(dataadmin.getApellido());
    	admin.setNombre(dataadmin.getNombre());
    	admin.setId(dataadmin.getId());
    	
		em.merge(admin);
		return Response.ok().build();
    }

}
