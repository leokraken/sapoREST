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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataAlmacen;
import com.sapo.entities.Av;
import com.sapo.entities.Categoria;


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
	public List<DataAlmacen> getAlmacenes(){
    	TypedQuery<Av> query = em.createNamedQuery("Av.findAll",Av.class);
    	List<Av> avs = query.getResultList();
    	List<DataAlmacen> ret = new ArrayList<>();
    	for(Av a : avs){
    		DataAlmacen da = new DataAlmacen();
    		da.setId(a.getId());
    		da.setDescripcion(a.getDescripcion());
    		da.setNombre(a.getNombre());
    		da.setUrl(a.getUrl());
    		ret.add(da);
    	}
    	return ret;
	}    
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public DataAlmacen getAlmacen(@PathParam("id") Long id){
		
		Av a = em.find(Av.class,id);
		DataAlmacen da = new DataAlmacen();
		da.setId(id);
		da.setDescripcion(a.getDescripcion());
		da.setNombre(a.getNombre());
		da.setUrl(a.getUrl());
		return da;
	}

	@POST
	@Path("{id}/agregarcategorias")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response agregarCategoriasAlmacen(@PathParam("id") Long id, List<Long> categorias){
		Av a = em.find(Av.class,id);			
		for(Long cat : categorias){
			System.out.println(cat);
			a.getCategorias().add(em.find(Categoria.class, cat));
		}

		return Response.ok().build();
	}

}
