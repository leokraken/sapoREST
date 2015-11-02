package com.sapo.controllers;

import static com.mongodb.client.model.Filters.eq;

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

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import com.sapo.datatypes.DataProducto;
import com.sapo.datatypes.DataResponse;
import com.sapo.entities.Categoria;
import com.sapo.entities.Producto;

@Stateless
@LocalBean
@Path("/productos")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class productoController {

	private String MongoURL = "mongodb://tsi2:tsi2@ds043962.mongolab.com:43962/krakenmongo";

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
	@Path("{id}/detalles")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getProductoDetails(@PathParam(value="id")Long id){
		MongoClient mongoClient = new MongoClient(new MongoClientURI(MongoURL));
		MongoDatabase db = mongoClient.getDatabase("krakenmongo");
		FindIterable<Document> iterable= db.getCollection("test").find(eq("rdbms_id",id));
		iterable.forEach(new Block<Document>() {
		    @Override
		    public void apply(final Document document) {
		        System.out.println(document);
		    }
		});
		
		Document e = iterable.first();
		mongoClient.close();
		if(e==null){
			DataResponse dr = new DataResponse();
			dr.setMensaje("No encontrado");
			return Response.status(404).entity(dr).build();		
		}
    	return Response.status(200).entity(e).build();
    }
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
    public List<DataProducto> getProductos(@QueryParam("generico")Boolean generico){
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
			if(generico==null || generico.equals(p.getGenerico())){
		    	ret.add(dp);
    		}
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
    	Categoria cat = em.find(Categoria.class, dp.getCategoria());
    	if(cat==null){
    		DataResponse dr = new DataResponse();
    		dr.setMensaje("Categoria "+dp.getCategoria()+" no existe");  		
    		return Response.status(500).entity(dr).build();
    	}
    	p.setCategoria(cat);
    	p.setGenerico(dp.getIsgenerico());
    	try{
        	em.persist(p); 
        	em.flush();
        	dp.setID(p.getId());
			return Response.status(201).entity(dp).build();

    	}catch(Exception e){
    		DataResponse dr = new DataResponse();
    		dr.setMensaje("Error inesperado."); 
    		dr.setDescripcion(e.getMessage());
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
       	Categoria cat = em.find(Categoria.class, dp.getCategoria());
    	if(cat==null){
    		DataResponse dr = new DataResponse();
    		dr.setMensaje("Categoria "+dp.getCategoria()+" no existe");  		
    		return Response.status(500).entity(dr).build();
    	}
    	p.setCategoria(cat);

		em.merge(p);
		return Response.status(200).build();
	}

	@DELETE
	@Path("{id}")
	@Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    @Consumes(MediaType.APPLICATION_JSON)
	public Response deleteProducto(@PathParam(value="id") Long id){
		Producto p = em.find(Producto.class, id);
		if(p!=null)
			em.remove(p);
		return Response.status(200).build();
	}
	
}
