package com.sapo.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import com.sapo.datatypes.DataAdministrador;
import com.sapo.datatypes.DataLoginAdmin;
import com.sapo.datatypes.DataResponse;
import com.sapo.datatypes.Token;
import com.sapo.entities.Administrador;

@Stateless
@LocalBean
@Path("/administradores")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class administradorController {

	public static final long HOUR = 3600*1000;
	
	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
    public administradorController() {

    }

	@GET
	@Path("dataadmin")
	@Produces(MediaType.APPLICATION_JSON)
    public DataAdministrador getDataAdministrador(){
		return new DataAdministrador();
	}
    
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public DataAdministrador getAdministrador(@PathParam(value="id")String id){
    	Administrador admin= em.find(Administrador.class, id);
    	DataAdministrador dp = new DataAdministrador();
		dp.setSurname(admin.getApellido());
    	dp.setName(admin.getNombre());
    	dp.setUsername(admin.getId());
    	return dp;
    }
    
	@POST
	@Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public Response loginAdministrador(DataLoginAdmin dl){
    	Administrador admin= em.find(Administrador.class, dl.getUser());
    	try{
        	if(dl.getPassword().equals(admin.getPassword())){
        		String uuid = UUID.randomUUID().toString();
        		Date d = new Date();
        		Timestamp ts = new Timestamp(d.getTime()+ 3 * HOUR);
        		
        		admin.setToken(uuid);
        		admin.setExpires(ts);
        		
        		Token t = new Token();
        		t.setToken(uuid);
            	return Response.status(200).entity(t).build();

        	}else{
        		DataResponse dr = new DataResponse();
        		dr.setMensaje("Login invalido");
            	return Response.status(401).entity(dr).build();

        	}    		
    	}catch(Exception e){
    		
    		DataResponse dr = new DataResponse();
    		dr.setMensaje("Excepcion");
    		dr.setDescripcion(e.getMessage());
        	return Response.status(401).entity(dr).build();
    	}

    }
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getAdministradores(){
    	TypedQuery<Administrador> query = em.createNamedQuery("Administrador.findAll",Administrador.class);
    	List<DataAdministrador> ret = new ArrayList<DataAdministrador>();
    	List<Administrador> admins = query.getResultList();
    	for(Administrador a : admins){
        	DataAdministrador dp = new DataAdministrador();
    		dp.setSurname(a.getApellido());
        	dp.setName(a.getNombre());
        	dp.setUsername(a.getId());
    		ret.add(dp);
    	}
    	return Response.status(200).entity(ret).build();
    }
    
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addAdministrador(DataAdministrador dp){
		try{
			Administrador login = em.find(Administrador.class, dp.getUsername());
			if(login==null){ 
				//creo usuario
				Administrador u = new Administrador();
				u.setApellido(dp.getSurname());
				u.setId(dp.getUsername());
				u.setNombre(dp.getName());
				u.setPassword(dp.getPassword());
	        	em.persist(u); 
	        	em.flush();	
	        	 //created					
			}
			return Response.status(201).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity("Administrador ya existe").build();
		}

    }
	
	@PUT
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public Response updateAdministrador(@PathParam(value="id")String id,DataAdministrador dataadmin){
		Administrador admin = new Administrador();
    	admin.setApellido(dataadmin.getSurname());
    	admin.setNombre(dataadmin.getName());
    	admin.setId(dataadmin.getUsername());	
		em.merge(admin);
		return Response.status(200).build();
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
