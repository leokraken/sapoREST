package com.sapo.controllers;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataAuth;
import com.sapo.entities.Administrador;
import com.sapo.entities.TokensAdministrador;
import com.sapo.entities.TokensAdministradorPK;
import com.sapo.entities.TokensUsuario;
import com.sapo.entities.TokensUsuarioPK;
import com.sapo.entities.Usuario;


@Stateless
@LocalBean
@Path("/auth")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class authController {

	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
    public authController() {
    }

	@GET
	@Path("dataauth")
	@Produces(MediaType.APPLICATION_JSON)
    public DataAuth getDataAuth(){
		return new DataAuth();
	}
	
	
	@POST
	@Path("admin/addtoken")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addTokenAdmin(DataAuth dataauth){
		TokensAdministrador ta = new TokensAdministrador();
		ta.setAdministradore(em.find(Administrador.class,dataauth.getUserid()));
		TokensAdministradorPK tapk = new TokensAdministradorPK();
		tapk.setToken(dataauth.getToken());
		tapk.setUsuarioid(dataauth.getUserid());
		ta.setId(tapk);
		
		em.persist(ta);
		em.flush();
		return Response.status(200).build();
	}
	
	@POST
	@Path("usuarios/addtoken")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addTokenUser(DataAuth dataauth){
		TokensUsuario tu = new TokensUsuario();
		tu.setUsuario(em.find(Usuario.class, dataauth.getUserid()));
		TokensUsuarioPK tupk = new TokensUsuarioPK();
		tupk.setToken(dataauth.getToken());
		tupk.setUsuarioid(dataauth.getUserid());
		tu.setId(tupk);
		
		em.persist(tu);
		em.flush();
		return Response.status(200).build();
	}
     
}
