package com.sapo.controllers;

import java.net.URISyntaxException;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataCategoria;
import com.sapo.datatypes.DataProducto;
import com.sapo.entities.Categoria;
import com.sapo.entities.Producto;

@Stateless
@LocalBean
@Path("/categorias")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class categoriaController {

	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
		
    public categoriaController() throws URISyntaxException {
    }

	
	@GET
	@Path("datacategoria")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDataCategoria(){
		return Response.ok().entity(new DataCategoria()).build();
	}
	
	//all
    @GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DataCategoria> getCategorias(@QueryParam("generico")Boolean generico){
    	TypedQuery<Categoria> query = em.createNamedQuery("Categoria.findAll",Categoria.class);
    	List<DataCategoria> ret = new ArrayList<DataCategoria>();
    	List<Categoria> categorias = query.getResultList();
    	for(Categoria cat : categorias){
        	DataCategoria dc = new DataCategoria();
        	dc.setID(cat.getId());
        	dc.setDescripcion(cat.getDescripcion());
        	dc.setIsgenerico(cat.getGenerica());
        	dc.setNombre(cat.getNombre());
    		if(generico==null || generico.equals(cat.getGenerica())){
        		ret.add(dc);    			
    		}
    	}
    	return ret;
	}
	
    //all
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getCategoria(@PathParam(value="id")Long id){
    	Categoria cat = em.find(Categoria.class, id);
    	if(cat==null){
    		return Response.status(204).build();
    	}
    	DataCategoria dc = new DataCategoria();
    	dc.setID(cat.getId());
    	dc.setDescripcion(cat.getDescripcion());
    	dc.setIsgenerico(cat.getGenerica());
    	dc.setNombre(cat.getNombre());
    	dc.setProductos(new ArrayList<DataProducto>());
    	for(Producto p : cat.getProductos()){
    		DataProducto dp = new DataProducto();
    		dp.setCategoria(p.getCategoria().getId());
    		dp.setDescripcion(p.getDescripcion());
    		dp.setID(p.getId());
    		dp.setIsgenerico(p.getGenerico());
    		dp.setNombre(p.getNombre());
    		dc.getProductos().add(dp);
    	}
    	return Response.status(200).entity(dc).build();
    }
	
	//user || admin
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategoria(DataCategoria datacat){
		try{
	    	Categoria cat = new Categoria();
	    	cat.setDescripcion(datacat.getDescripcion());
	    	cat.setGenerica(datacat.getIsgenerico());
	    	cat.setNombre(datacat.getNombre());	    	
        	em.persist(cat); 
        	em.flush();		
			return Response.status(201).entity(cat).build();

		}catch(Exception e){
			return Response.status(409).build();
		}

    }
	//depende...
	@PUT
	@Path("{categoriaID}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCategoria(@PathParam(value="categoriaID")Long catid, DataCategoria datacat){
		try{
	    	Categoria cat = new Categoria();
    	
	    	cat.setDescripcion(datacat.getDescripcion());
	    	cat.setGenerica(datacat.getIsgenerico());
	    	cat.setNombre(datacat.getNombre());	    
	    	cat.setId(catid);
        	em.merge(cat); 
			return Response.status(200).build();

		}catch(Exception e){
			return Response.status(304).entity("Error actualizar categoria").build();
		}

    }
   
	//all
	@GET
	@Path("{id}/productos")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getProductosCategoria(@PathParam(value="id")Long id){
    	Categoria cat = em.find(Categoria.class, id);
    	if(cat==null){
    		return Response.status(500).entity("Categoria no existe").build();
    	}
    	List<DataProducto> ret = new ArrayList<DataProducto>();
    	for(Producto p : cat.getProductos()){
    		DataProducto dp = p.getDataProducto();
    		ret.add(dp);
    	}
    	return Response.status(200).entity(ret).build();
    }
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response deleteCategoria(@PathParam(value="id")Long id){
		Categoria c = em.find(Categoria.class, id);
		if(c!=null)
			em.remove(c);
		return Response.status(200).build();
	}
	
}
