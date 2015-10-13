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

import com.sapo.datatypes.DataProducto;
import com.sapo.entities.*;

@Stateless
@LocalBean
@Path("/productos")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class productoController {

	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
    public productoController() {
    }
    //CRUD

	@GET
	@Path("dataproducto")
	@Produces(MediaType.APPLICATION_JSON)
    public DataProducto getdp(){
		return new DataProducto();
	}
    
    
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public DataProducto getProducto(@PathParam(value="id")Long id){
    	Producto p = em.find(Producto.class, id);  
    	DataProducto dp = new  DataProducto();
    	dp.setIsgenerico(p.getGenerico());
    	dp.setDescripcion(p.getDescripcion());
    	dp.setNombre(p.getNombre());
    	dp.setID(p.getId());
    	dp.setCategoria(p.getCategoria().getId());
    	return dp;
    }

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
    public List<DataProducto> getProductos(){
    	TypedQuery<Producto> query = em.createNamedQuery("Producto.findAll",Producto.class);
    	List<Producto>  prods = query.getResultList();
    	List<DataProducto> ret = new ArrayList<DataProducto>();
		for(Producto p : prods){
	    	 
	    	DataProducto dp = new  DataProducto();
	    	dp.setIsgenerico(p.getGenerico());
	    	dp.setDescripcion(p.getDescripcion());
	    	dp.setNombre(p.getNombre());
	    	dp.setID(p.getId());
	    	dp.setCategoria(p.getCategoria().getId());
	    	ret.add(dp);
		}
		return ret;
    }
   
	//User || admin
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProducto(DataProducto dp){
    	Producto p = new Producto();
   	
    	p.setNombre(dp.getNombre());
    	p.setDescripcion(dp.getDescripcion());
    	p.setCategoria(em.find(Categoria.class, dp.getCategoria()));
    	p.setGenerico(dp.getIsgenerico());
    	try{
        	em.persist(p); 
        	em.flush();
			return Response.status(201).entity(p).build();

    	}catch(Exception e){
			return Response.status(409).build();		
    	}

	}

	//if producto generico admin
	//else user
	@PUT
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    @Consumes(MediaType.APPLICATION_JSON)
	public Response updateProducto(@PathParam(value="id") Long id,DataProducto dp){
		Producto p = em.find(Producto.class, id);
    	p.setNombre(dp.getNombre());
    	p.setDescripcion(dp.getDescripcion());
    	p.setGenerico(dp.getIsgenerico());
    	p.setCategoria(em.find(Categoria.class, dp.getCategoria()));

		em.merge(p);
		return Response.ok().build();
	}

}
