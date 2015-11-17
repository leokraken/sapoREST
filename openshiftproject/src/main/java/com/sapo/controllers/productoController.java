package com.sapo.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
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

import com.sapo.datatypes.DataProducto;
import com.sapo.datatypes.DataResponse;
import com.sapo.datatypes.DataUnificar;
import com.sapo.entities.CarritoProducto;
import com.sapo.entities.Categoria;
import com.sapo.entities.Producto;
import com.sapo.utils.MongoController;

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
    	return p.getDataProducto();
    }

	@GET
	@Path("{id}/detalles")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getProductoDetails(@PathParam(value="id")Long id){
		MongoController mc = new MongoController();
		Document e = mc.getProducto(id);
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
			if(generico==null || generico.equals(p.getGenerico())){
		    	ret.add(p.getDataProducto());
    		}
		}
		return ret;
    }
   
	//User || admin
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addProducto(@QueryParam("mongo")Boolean mongo, DataProducto dp){
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
        	
        	//MONGO
        	if(mongo==null || mongo)
        		new MongoController().upsertProduct(dp);
        	
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
	@Produces(MediaType.APPLICATION_JSON)
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
		
		//mongo thread
		dp.setID(p.getId());
		new MongoController().upsertProduct(dp);
		
		return Response.status(200).build();
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response deleteProducto(@PathParam(value="id") Long id){
		Producto p = em.find(Producto.class, id);
		if(p!=null)
			em.remove(p);
		return Response.status(200).build();
	}
	
	/*
	 * MongoClient mongoClient = new MongoClient(new MongoClientURI(MongoURL));
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
		
	 * */

	@GET
	@Path("test")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response test(){
		HashSet<String> h = new HashSet<>();
		h.add("jajaj");
		h.add("lala");
		return Response.status(200).entity(h.toString()).build();
	}
	
	@POST
	@Path("{id}/tags/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addTag(@PathParam(value="id") Long id, List<String> tag){
		Producto p = em.find(Producto.class, id);
		List<String> tags_old = new ArrayList<>();
		if(p.getTags()!=null && !p.getTags().equals("")){
			tags_old= Arrays.asList(p.getTags().split(","));
		}
		HashSet<String> set = new HashSet<>(tags_old);
		set.addAll(tag);
		String joined = String.join(",", set);
		p.setTags(joined);

		//MONGO
		DataProducto dp = p.getDataProducto();
		new MongoController().upsertProduct(dp);;
		
		return Response.status(200).entity(tag).build();
	}

	@POST
	@Path("{id}/tags/remove")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response removeTag(@PathParam(value="id") Long id, List<String> tag){
		Producto p = em.find(Producto.class, id);
		List<String> tags_old = new ArrayList<>();
		if(p.getTags()!=null && !p.getTags().equals("")){
			tags_old= Arrays.asList(p.getTags().split(","));
		}
		HashSet<String> set = new HashSet<>(tags_old);
		set.removeAll(tag);
		String joined = String.join(",", set);
		p.setTags(joined);
		
		//Mongo 
		DataProducto dp = p.getDataProducto();
		new MongoController().upsertProduct(dp);
		
		return Response.status(200).entity(tag).build();
	}

	
	@POST
	@Path("{id}/imagenes/create")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addImages(@PathParam(value="id") Long id, List<String> images){
		try{
			Producto p = em.find(Producto.class, id);
			List<String> images_old = new ArrayList<>();
			if(p.getTags()!=null && !p.getTags().equals("")){
				images_old= Arrays.asList(p.getImagenes().split(","));
			}
			HashSet<String> set = new HashSet<>(images_old);
			set.addAll(images);
			String joined = String.join(",", set);
			p.setImagenes(joined);
			
			//MONGO
			DataProducto dp = p.getDataProducto();
			new MongoController().upsertProduct(dp);
			
			return Response.status(200).entity(dp).build();

		}catch(Exception e){
			e.printStackTrace();
			DataResponse dr = new DataResponse();
			dr.setMensaje("Error agregar imagenes");
			dr.setDescripcion("Problema en mongodb");
			return Response.status(500).entity(dr).build();
		}		
	}
	
	@POST
	@Path("{id}/imagenes/remove")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response removeImages(@PathParam(value="id") Long id, List<String> images){
		try{
			Producto p = em.find(Producto.class, id);
			List<String> images_old = new ArrayList<>();
			if(p.getImagenes()!=null && !p.getImagenes().equals("")){
				images_old= Arrays.asList(p.getImagenes().split(","));
			}
			HashSet<String> set = new HashSet<>(images_old);
			set.removeAll(images);
			String joined = String.join(",", set);
			p.setImagenes(joined);
			
			//update doc mongo
			DataProducto dp = p.getDataProducto();	
			new MongoController().upsertProduct(dp);
			
			return Response.status(200).entity(dp).build();

		}catch(Exception e){
			e.printStackTrace();
			DataResponse dr = new DataResponse();
			dr.setMensaje("Error remover imagenes");
			dr.setDescripcion("Problema en mongodb");
			return Response.status(500).entity(dr).build();
		}		
	}
	
	@POST
	@Path("/unificar")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doGenerico(DataUnificar du){
		Producto nuevo = new Producto();
		nuevo.setNombre(du.getNombre());
		nuevo.setCategoria(em.find(Categoria.class, du.getCategoria()));
		nuevo.setDescripcion(du.getDescripcion());
		nuevo.setGenerico(true);
		nuevo.setCarritoProductos(new ArrayList<CarritoProducto>());
		for(Long productoid : du.getProductos()){
			Producto p = em.find(Producto.class, productoid);
			nuevo.getCarritoProductos().addAll(p.getCarritoProductos());
			nuevo.getReportesMovimientoStocks().addAll(p.getReportesMovimientoStocks());
			nuevo.getStocks().addAll(p.getStocks());
			
			
		}
		return Response.status(200).build();
	}
	
}
