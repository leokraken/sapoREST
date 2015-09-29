package com.sapo.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.controllers.productoController;
import com.sapo.datatypes.DataProducto;
import com.sapo.entities.Producto;
import com.sapo.entities.ProductosGenerico;

@Stateless
@Path("/productos")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class productos {
	
	@EJB
	private productoController pc;
	
	@GET
	@Path("/genericos")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProductosGenerico> getProductos(){

		return pc.getProductosGenericos();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Producto getProducto(@PathParam("id")long prodID){
		return pc.getProducto(prodID);
	}
	
	@POST
	@Path("/generico/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addProductoGenerico(DataProducto producto){
	
		try{
			pc.addProductoGenerico(producto);
			return Response.ok().build();
			
		}catch(Exception E){
			return Response.serverError().entity("Producto ya existe").build();		
		}
	}
	
	@PUT
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
	public Response updateProducto(@PathParam("id")String productoID, DataProducto dp){
		pc.updateProductoGenerico(dp);
		return Response.ok().build();
	}

}
