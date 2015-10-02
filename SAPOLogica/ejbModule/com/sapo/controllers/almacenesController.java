package com.sapo.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sapo.entities.Administrador;
import com.sapo.entities.Av;


@Stateless
@LocalBean
@Path("/almacenes")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class almacenesController {

	@PersistenceContext(unitName="SAPOLogica")
	private EntityManager em;
	
    public almacenesController() {
    }

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Av> getAlmacenes(){
    	TypedQuery<Av> query = em.createNamedQuery("Av.findAll",Av.class);
    	List<Av> avs = query.getResultList();
    	for(Av av : avs){
    		av.setUsuarios(null);
    		av.setCarritoProductos(null);
    		av.getUsuario().setAvs1(null);
    		av.getUsuario().setAvs2(null);
    		av.getUsuario().setExternalLoginAccount(null);
    		av.setStocks(null);
    	}
    	return avs;
	}    
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Av getAlmacen(@PathParam("id") Long id){
		
		Av ret = em.find(Av.class,id);
		ret.setCarritoProductos(null);
		ret.setStocks(null);
		ret.setUsuarios(null);
		ret.setUsuario(null);
		return ret;
	}
	
	
}
