package com.sapo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

import com.sapo.datatypes.DataAlmacen;
import com.sapo.datatypes.DataPersona;
import com.sapo.datatypes.DataResponse;
import com.sapo.entities.Av;
import com.sapo.entities.Usuario;

@Stateless
@LocalBean
@Path("/usuarios")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class usuariosController {


	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
    public usuariosController() {
    }

    @GET
	@Path("datapersona")
	@Produces(MediaType.APPLICATION_JSON)
    public DataPersona getDp(){
    	return new DataPersona();
    }
    
    //user || admin
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public DataPersona getUsuario(@PathParam(value="id") String id){
    	Usuario u = em.find(Usuario.class, id);
    	DataPersona dp = new DataPersona();
		dp.setId(u.getId());
		dp.setApellido(u.getApellido());
		dp.setNombre(u.getNombre());
    	return dp;
    }
    
	//user || admin
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
    public List<DataPersona> getUsuarios(){
    	TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findAll",Usuario.class);
    	List<Usuario> usuarios = query.getResultList();
    	List<DataPersona> ret = new ArrayList<DataPersona>();
    	for(Usuario u : usuarios){
    		DataPersona dp = new DataPersona();
    		dp.setId(u.getId());
    		dp.setApellido(u.getApellido());
    		dp.setNombre(u.getNombre());
    		ret.add(dp);
    	}
    	return ret;
    }

	@GET
	@Path("search/{name}")
	@Produces(MediaType.APPLICATION_JSON)
    public List<DataPersona> searchUsuarios(@PathParam(value="name")String name){
    	Query query = em.createQuery("select u from Usuario u where u.id like :id");
    	query.setParameter("id", "%"+name+"%");
    	@SuppressWarnings("unchecked")
		List<Usuario> usuarios = query.getResultList();
    	List<DataPersona> ret = new ArrayList<DataPersona>();
    	for(Usuario u : usuarios){
    		DataPersona dp = new DataPersona();
    		dp.setId(u.getId());
    		dp.setApellido(u.getApellido());
    		dp.setNombre(u.getNombre());
    		ret.add(dp);
    	}
    	return ret;
    }
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUsuario(DataPersona dp){
		try{
			Usuario login = em.find(Usuario.class, dp.getId());
			if(login==null){ 
				//creo usuario
				Usuario u = new Usuario();
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
			DataResponse dr = new DataResponse();
			dr.setMensaje("Error inesperado");
			dr.setDescripcion(e.getMessage());
			return Response.status(500).entity(dr).build();
		}

    }
    
	@PUT
	@Path("{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUsuario(@PathParam(value="usuario") String id,DataPersona user){
		Usuario u = em.find(Usuario.class, id);
		u.setApellido(user.getApellido());
		u.setNombre(user.getNombre());
    	em.merge(u);    	
    }
    
	@POST
	@Path("{usuario}/almacenes/create")
	@Produces(MediaType.APPLICATION_JSON)
    public Response crearAlmacenUsuario(@PathParam(value = "usuario") String usuario, DataAlmacen da){
    	Av almacen = new Av();
    	almacen.setId(da.getId());
    	almacen.setNombre(da.getNombre());
    	almacen.setDescripcion(da.getDescripcion());
    	almacen.setUsuario(em.find(Usuario.class,usuario));
    	//almacen.setPrivada(da.getPrivado());
    	try{
        	em.persist(almacen);
        	em.flush();	
    	}catch(Exception e){
    		//conflict
    		DataResponse dr = new DataResponse();
    		dr.setMensaje("Error inesperado");
    		dr.setDescripcion(e.getMessage());
    		return Response.status(409).entity(dr).build();
    	}
    	da.setId(almacen.getId());
    	return Response.status(201).entity(da).build();
	}
	
	@GET
	@Path("{usuario}/almacenes/list")
	@Produces(MediaType.APPLICATION_JSON)
    public List<DataAlmacen> listAlmacenesUsuario(@PathParam(value = "usuario") String usuario){
		Usuario u = em.find(Usuario.class, usuario);
		List<DataAlmacen> ret = new ArrayList<DataAlmacen>();
		for(Av a : u.getAvs1()){
			DataAlmacen da = new DataAlmacen();
			da.setId(a.getId());
			da.setDescripcion(a.getDescripcion());
			da.setNombre(a.getNombre());
			da.setUsuario(u.getId());
			da.setPrivado(a.getPrivada());
			ret.add(da);
		}	
		return ret;
	}
	
	@DELETE
	@Path("{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(@PathParam(value = "usuario") String usuario){
		Usuario u = em.find(Usuario.class, usuario);
		if(u!=null){
			em.remove(u);
		}
		return Response.status(200).build();
	}
	
	
	
	   
}
