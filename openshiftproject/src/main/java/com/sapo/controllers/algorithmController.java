package com.sapo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataCategoria;
import com.sapo.datatypes.DataCategoriaAlgorithm;
import com.sapo.datatypes.DataProducto;
import com.sapo.datatypes.DataProductoAlgoritmo;
import com.sapo.entities.Categoria;
import com.sapo.entities.Producto;


@Stateless
@LocalBean
@Path("/algoritmos")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class algorithmController {

	@PersistenceContext(unitName="SAPOLogica")
	private EntityManager em;
	
    public algorithmController() {
    }

	@GET
	@Path("categorias")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getCategoriasGenericas(){
    	Query q = em.createQuery("select C from Categoria C where C.generica=true");
    	@SuppressWarnings("unchecked")
		List<Categoria> cats = q.getResultList();
    	List<DataCategoriaAlgorithm> dcats = new ArrayList<>();
    	for(Categoria c : cats){
    		DataCategoriaAlgorithm dc = new DataCategoriaAlgorithm();
    		dc.setCategoriaid(c.getId());
    		dc.setDescripcion(c.getDescripcion());
    		dc.setNombre(c.getNombre());
    		List<DataProducto> prods = new ArrayList<>();
    		for(Producto p : c.getProductos()){
				DataProducto dp = new DataProducto();
    			dp.setDescripcion(p.getDescripcion());
    			dp.setID(p.getId());
    			dp.setNombre(p.getNombre());
    			dp.setIsgenerico(p.getGenerico());
    			prods.add(dp);			
    		}
    		dc.setProductos(prods);    	
    		dcats.add(dc);
    	}
    	return Response.status(200).entity(dcats).build();
    }

	@GET
	@Path("dataproducto")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getData(){
		return Response.status(200).entity(new DataProductoAlgoritmo()).build();
	}
	
	
	@GET
	@Path("productos")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getProductosNoGenericos(){
    	Query q = em.createQuery("select p from Producto p");
    	@SuppressWarnings("unchecked")
		List<Producto> prods = q.getResultList();
    	List<DataProductoAlgoritmo> ret = new ArrayList<>();
    	for(Producto p : prods){
    		DataProducto dpt = p.getDataProducto();
    		DataProductoAlgoritmo dp = new DataProductoAlgoritmo();
    		dp.setID(p.getId());
    		dp.setDescripcion(p.getDescripcion());
    		dp.setImagenes(dpt.getImagenes());
    		dp.setIsgenerico(p.getGenerico());
    		dp.setNombre(p.getNombre());
    		dp.setTags(dpt.getTags());
    		DataCategoria dc = new DataCategoria();
    		dc.setDescripcion(p.getCategoria().getDescripcion());
    		dc.setID(p.getCategoria().getId());
    		dc.setIsgenerico(p.getCategoria().getGenerica());
    		dc.setNombre(p.getCategoria().getNombre());
    		dp.setCategoria(dc);
    		ret.add(dp);
    	}  	
    	return Response.status(200).entity(ret).build();
	}
	
	
    
}
