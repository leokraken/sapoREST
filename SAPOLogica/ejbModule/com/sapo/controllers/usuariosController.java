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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataAlmacen;
import com.sapo.datatypes.DataPersona;
import com.sapo.entities.Av;
import com.sapo.entities.ExternalLoginAccount;
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
    //remover para ver json
	@GET
	@Path("datapersona")
	@Produces(MediaType.APPLICATION_JSON)
    public DataPersona getDp(){
    	return new DataPersona();
    }
    
    
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Usuario getUsuario(@PathParam(value="id") String id){
    	Usuario u = em.find(Usuario.class, id);
    	u.setAvs1(null);
    	u.setAvs2(null);
    	u.getExternalLoginAccount().setAdministradores(null);
    	u.getExternalLoginAccount().setUsuarios(null);
    	return u;
    }
    
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getUsuarios(){
    	TypedQuery<Usuario> query = em.createNamedQuery("Usuario.findAll",Usuario.class);
    	List<Usuario> usuarios = query.getResultList();
    	for(Usuario u : usuarios){
    		u.setAvs1(null);
    		u.setAvs2(null);
    		u.getExternalLoginAccount().setAdministradores(null);//.setAdministradores(null);
    		u.getExternalLoginAccount().setUsuarios(null);
    	}
    	return usuarios;
    }
    
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUsuario(DataPersona dp){
		try{
			Usuario u = new Usuario();
			u.setApellido(dp.getApellido());
			u.setId(dp.getId());
			u.setNombre(dp.getNombre());
			u.setExternalLoginAccount(em.find(ExternalLoginAccount.class, dp.getAccount_id()));
			
        	em.persist(u); 
        	em.flush();	
        	return Response.ok().build();
		}catch(Exception e){
			return Response.serverError().entity("Administrador ya existe").build();
		}

    }
    
	@PUT
	@Path("{usuario}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateUsuario(@PathParam(value="usuario") String id,DataPersona user){
		Usuario u = em.find(Usuario.class, id);
		u.setApellido(user.getApellido());
		u.setExternalLoginAccount(em.find(ExternalLoginAccount.class, user.getAccount_id()));
		u.setNombre(user.getNombre());
    	em.merge(u);    	
    }
    
	@POST
	@Path("{usuario}/almacenes/create")
	@Produces(MediaType.APPLICATION_JSON)
    public void crearAlmacenUsuario(@PathParam(value = "usuario") String usuario, DataAlmacen da){
    	Av almacen = new Av();
    	almacen.setNombre(da.getNombre());
    	almacen.setDescripcion(da.getDescripcion());
    	almacen.setUsuario(em.find(Usuario.class,usuario));
    	em.persist(almacen);
	}
    
}
