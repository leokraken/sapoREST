package com.sapo.controllers;

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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataAlmacen;
import com.sapo.datatypes.DataCategoria;
import com.sapo.datatypes.DataPersona;
import com.sapo.datatypes.DataStock;
import com.sapo.entities.Av;
import com.sapo.entities.Categoria;
import com.sapo.entities.Producto;
import com.sapo.entities.Stock;
import com.sapo.entities.StockPK;
import com.sapo.entities.Usuario;
import com.sapo.security.SecurityUtils;


@Stateless
@LocalBean
@Path("/almacenes")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class almacenesController {

	@PersistenceContext(unitName="SAPOLogica")
	private EntityManager em;
	
	private SecurityUtils su = new SecurityUtils();
	
    public almacenesController() {
    }

    @GET
	@Path("dataalmacen")
	@Produces(MediaType.APPLICATION_JSON)
	public DataAlmacen getDataAlmacen(){
    	return new DataAlmacen();
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
	public Response getAlmacen(@PathParam("id") Long id){
		
		Av a = em.find(Av.class,id);
		if(a==null)
			return Response.status(204).build();
		
		DataAlmacen da = new DataAlmacen();
		da.setId(id);
		da.setDescripcion(a.getDescripcion());
		da.setNombre(a.getNombre());
		da.setUrl(a.getUrl());
		da.setUsuario(a.getUsuario().getId());
		
		List<DataStock> stock = new ArrayList<DataStock>();
		List<DataCategoria> categorias = new ArrayList<DataCategoria>();
		List<DataPersona> colaboradores = new ArrayList<DataPersona>();
		
		for(Usuario u : a.getUsuarios()){
			DataPersona dp = new DataPersona();
			dp.setId(u.getId());
			dp.setNombre(u.getNombre());
			dp.setApellido(u.getApellido());
			colaboradores.add(dp);
		}
		for(Stock s : a.getStocks()){
			DataStock ds = new DataStock();
			ds.setProductoID(s.getProducto().getId());
			ds.setNombre(s.getProducto().getNombre());
			ds.setCantidad(s.getCantidad());
			ds.setDescripcion(s.getProducto().getDescripcion());
			stock.add(ds);
		}
		for(Categoria c : a.getCategorias()){
			DataCategoria dc = new DataCategoria();
			dc.setDescripcion(c.getDescripcion());
			dc.setID(c.getId());
			dc.setIsgenerico(c.getGenerica());
			dc.setNombre(c.getNombre());
			dc.setProductos(null);
			categorias.add(dc);
		}
		da.setCategorias(categorias);
		da.setColaboradores(colaboradores);
		da.setStockproductos(stock);
		
		return Response.status(200).entity(da).build();
	}

	@POST
	@Path("{id}/agregarcategorias")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response agregarCategoriasAlmacen(@PathParam("id") Long id, List<Long> categorias){
		Av a = em.find(Av.class,id);
		try{
			for(Long cat : categorias){
				System.out.println(cat);
				a.getCategorias().add(em.find(Categoria.class, cat));
			}		
		}catch(Exception E){
			System.out.println("Alguna categoria no existe...");
		}

		return Response.status(200).build();
	}

	@POST
	@Path("{id}/agregarproductos")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response agregarProductosAlmacen(@PathParam("id") Long id, List<DataStock> productos){
		Av a = em.find(Av.class,id);			
		for(DataStock prod : productos){
			System.out.println(prod);
			Stock stock = new Stock();
			stock.setCantidad(prod.getCantidad());
			stock.setAv(a);
			stock.setProducto(em.find(Producto.class, prod.getProductoID()));
			
			StockPK stockid = new StockPK();
			stockid.setIdAv(id);
			stockid.setIdProducto(prod.getProductoID());
			stock.setId(stockid);
			em.persist(stock);
			em.flush();
		}

		return Response.status(200).build();
	}
	
	/*datatypes*/
	@GET
	@Path("datastock")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDataStock(@HeaderParam("user-token") String userToken,
								  @HeaderParam("user-login") String user)
	{
		if(su.authorizeUser(userToken, user,new Long(2), em))
		
			return Response.status(200).entity(new DataStock()).build();
		else
			return Response.status(401).build();
		
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response deleteAlmacen(@PathParam("id") Long id){
		Av av = em.find(Av.class, id);
		if(av!=null)
			em.remove(av);
		return Response.status(200).build();
	}
	
	
}
