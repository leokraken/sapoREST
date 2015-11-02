package com.sapo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataCarrito;
import com.sapo.datatypes.DataCarritoReq;
import com.sapo.datatypes.DataProducto;
import com.sapo.datatypes.DataResponse;
import com.sapo.entities.Av;
import com.sapo.entities.CarritoProducto;
import com.sapo.entities.CarritoProductoPK;
import com.sapo.entities.Producto;

@Stateless
@LocalBean
@Path("/carritos")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class carritoController {

	@PersistenceContext(unitName="SAPOLogica")
	private EntityManager em;
	
    public carritoController() {
    }
    
    @POST
	@Path("{almacen}/agregar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCarrito(@PathParam(value="almacen") String almacen, DataCarritoReq dc){	
    	try{
        	CarritoProductoPK cpfk = new CarritoProductoPK();
        	cpfk.setIdAv(almacen);
        	cpfk.setIdProducto(dc.getProducto());
        	
        	CarritoProducto cp = new CarritoProducto();
        	cp.setId(cpfk);
        	cp.setCantComprado(dc.getCantidad_compras());
        	cp.setCantTotal(dc.getTotal());
        	cp.setProducto(em.find(Producto.class, dc.getProducto()));
        	cp.setAv(em.find(Av.class, almacen));
        	
        	em.merge(cp);
        	em.flush();    		
    	}catch(Exception e){
    		e.printStackTrace();
    		DataResponse dr = new DataResponse();
    		dr.setMensaje("excepcion");
    		dr.setDescripcion(e.getMessage());
    		return Response.status(500).entity(dr).build();
    	}
	
    	return Response.status(200).build();
    }
    
    @DELETE
	@Path("{almacen}/delete/{producto}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeCarrito(@PathParam(value="almacen") String almacen, @PathParam(value="producto") Long producto){	
    	try{
        	CarritoProductoPK cpfk = new CarritoProductoPK();
        	cpfk.setIdAv(almacen);
        	cpfk.setIdProducto(producto);   	
        	em.remove(em.find(CarritoProducto.class, cpfk));   		
    	}catch(Exception e){
    		e.printStackTrace();
    		DataResponse dr = new DataResponse();
    		dr.setMensaje("excepcion");
    		dr.setDescripcion(e.getMessage());
    		return Response.status(500).entity(dr).build();
    	}
	
    	return Response.status(200).build();
    }
    
    
    @GET
	@Path("{almacen}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCarrito(@PathParam(value="almacen") String almacen){	
    	Query query = em.createQuery("select c from CarritoProducto c where c.av.id=:idav");
    	query.setParameter("idav", almacen);
    	@SuppressWarnings("unchecked")
		List<CarritoProducto> carrito = query.getResultList();
    	List<DataCarrito> datalist = new ArrayList<>();
    	for(CarritoProducto cp : carrito){
        	DataProducto dp = new  DataProducto();
        	dp.setIsgenerico(cp.getProducto().getGenerico());
        	dp.setDescripcion(cp.getProducto().getDescripcion());
        	dp.setNombre(cp.getProducto().getNombre());
        	dp.setID(cp.getProducto().getId());
        	dp.setCategoria(cp.getProducto().getCategoria().getId());
    		
    		DataCarrito dc = new DataCarrito();
    		dc.setProducto(dp);
    		dc.setCantidad_compras(cp.getCantComprado());
    		dc.setTotal(cp.getCantTotal());
    		datalist.add(dc);
    	}
    	return Response.status(200).entity(datalist).build();
    }

}
