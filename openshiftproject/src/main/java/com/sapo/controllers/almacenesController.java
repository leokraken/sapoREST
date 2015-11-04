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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sapo.datatypes.DataAlmacen;
import com.sapo.datatypes.DataCategoria;
import com.sapo.datatypes.DataNotificacionStock;
import com.sapo.datatypes.DataPersona;
import com.sapo.datatypes.DataProducto;
import com.sapo.datatypes.DataResponse;
import com.sapo.datatypes.DataStock;
import com.sapo.datatypes.DataStockLite;
import com.sapo.datatypes.DataStockProducto;
import com.sapo.entities.Av;
import com.sapo.entities.Categoria;
import com.sapo.entities.Notificaciones;
import com.sapo.entities.Producto;
import com.sapo.entities.ReportesMovimientoStock;
import com.sapo.entities.Stock;
import com.sapo.entities.StockPK;
import com.sapo.entities.TipoNotificacion;
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
	
	@Context
    UriInfo uriInfo;
	
	
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
    		List<DataCategoria> categorias = new ArrayList<DataCategoria>();
    		for(Categoria c : a.getCategorias()){
    			DataCategoria dc = new DataCategoria();
    			dc.setID(c.getId());
    			dc.setDescripcion(c.getDescripcion());
    			dc.setIsgenerico(c.getGenerica());
    			dc.setNombre(c.getNombre());
    			categorias.add(dc);
    		}
    		DataAlmacen da = new DataAlmacen();
    		da.setId(a.getId());
    		da.setDescripcion(a.getDescripcion());
    		da.setNombre(a.getNombre());
    		da.setCategorias(categorias);
    		ret.add(da);
    	}
    	return ret;
	}    
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response getAlmacen(@PathParam("id") String id){
		
		Av a = em.find(Av.class,id);
		
		
		if(a==null)
			return Response.status(204).build();
		
		//actualizo visitas
		a.setVisitas(a.getVisitas()+1);
	
		DataAlmacen da = new DataAlmacen();
		da.setId(id);
		da.setDescripcion(a.getDescripcion());
		da.setNombre(a.getNombre());
		da.setUsuario(a.getUsuario().getId());
		da.setPrivado(a.getPrivada());
		
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

	@PUT
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response updateAlmacen(@PathParam("id") String id, DataAlmacen da){
		
		Av a = em.find(Av.class,id);
		if(a==null){
			DataResponse dr = new DataResponse();
			dr.setMensaje("No existe almacen...");
			return Response.status(400).build();
		
		}else{			
			a.setId(id);
			a.setDescripcion(da.getDescripcion());
			a.setNombre(da.getNombre());
			
			if(!a.getUsuario().getTipocuenta().getNombre().equals("FREE")){
				a.setPrivada(da.getPrivado());
			}else{
				a.setPrivada(false);
			}			
		}			
		DataResponse dr = new DataResponse();
		dr.setMensaje("Almacen actualizado con exito!");
		return Response.status(200).entity(dr).build();
	}

	
	@POST
	@Path("{id}/agregarcategorias")
    @Consumes(MediaType.APPLICATION_JSON)
	public Response agregarCategoriasAlmacen(@PathParam("id") String id, List<Long> categorias){
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
	public Response agregarProductosAlmacen(@PathParam("id") String id, List<DataStockLite> productos){
		Av a = em.find(Av.class,id);			
		for(DataStockLite prod : productos){
			System.out.println(prod.getProductoID());
			System.out.println(prod.getCantidad());

			Stock stock = new Stock();
			stock.setCantidad(prod.getCantidad());
			stock.setAv(a);
			stock.setProducto(em.find(Producto.class, prod.getProductoID()));
			
			StockPK stockid = new StockPK();
			stockid.setIdAv(id);
			stockid.setIdProducto(prod.getProductoID());
			stock.setId(stockid);
			
			//movimiento stock
			ReportesMovimientoStock rm = new ReportesMovimientoStock();
			rm.setAlmacenid(id);
			rm.setProductoid(prod.getProductoID());
			rm.setStock(prod.getCantidad());
			
			try{
				em.merge(stock);
				em.flush();		
				
				//movimientos stock
				em.persist(rm);
				em.flush();
			}catch(Exception e){
				e.printStackTrace();
			}
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
	public Response deleteAlmacen(@PathParam("id") String id){
		Av av = em.find(Av.class, id);
		if(av!=null)
			em.remove(av);
		return Response.status(200).build();
	}
	
	
	@GET
	@Path("{id}/productos/categoria/{categoriaID}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response getProductosByCategoria(@PathParam("id") String id, @PathParam("categoriaID") Long idcat){
		
		Av a = em.find(Av.class,id);
		if(a==null)
			return Response.status(204).build();
		List<DataStockProducto> productos = new ArrayList<DataStockProducto>();
		for(Stock s : a.getStocks()){
			if(s.getProducto().getCategoria().getId().equals(idcat)){
				DataProducto dp = new DataProducto();
				dp.setID(s.getProducto().getId());
				dp.setDescripcion(s.getProducto().getDescripcion());
				dp.setIsgenerico(s.getProducto().getGenerico());
				dp.setNombre(s.getProducto().getNombre());
				dp.setCategoria(s.getProducto().getCategoria().getId());
				
				DataStockProducto dsp = new DataStockProducto();
				dsp.setProducto(dp);
				dsp.setCantidad(s.getCantidad());
				productos.add(dsp);			
			}
		}
		
		return Response.status(200).entity(productos).build();
	}

	@GET
	@Path("datastocklite")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getDataStockLite(){
		return Response.status(200).entity(new DataStockLite()).build();
	}

	@GET
	@Path("datastockproducto")
    @Produces(MediaType.APPLICATION_JSON)
	public Response getDataStockProducto(){
		return Response.status(200).entity(new DataStockProducto()).build();
	}
	
	@PUT
	@Path("{id}/stock/{productoID}/{cantidad}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response setStockAlmacen(@PathParam(value="id")String idAV, @PathParam(value="productoID") Long productoID, @PathParam(value="cantidad")int cantidad){

		int stockold=cantidad;
		//si no existe lo creo
		try{
			StockPK key = new StockPK();
			key.setIdAv(idAV);;
			key.setIdProducto(productoID);;

			//busco
			Stock s= em.find(Stock.class, key);
			if(s==null){		
				s = new Stock();
				s.setCantidad(cantidad);
				s.setId(key);
				s.setAv(em.find(Av.class, idAV));
				s.setProducto(em.find(Producto.class, productoID));
				em.merge(s);		
				
			}else{
				stockold = cantidad-s.getCantidad();
				s.setCantidad(cantidad);
				String mensaje= null;
				/*Notifico caso sea pertinente*/
				if(s.getNotifica() && s.getMinimo() > s.getCantidad()){		
					Notificaciones np = new Notificaciones();
					//np.setAv(em.find(Av.class, idAV));
					//np.setProducto(em.find(Producto.class, productoID));
					np.setUsuario(s.getAv().getUsuario());
					mensaje = "Únicamente cuentas con "+s.getCantidad()+" unidades de "+s.getProducto().getNombre()+"!";
					np.setMensaje(mensaje);
					np.setTipoNotificacione(em.find(TipoNotificacion.class, 2));
					em.persist(np);
					em.flush();
					
					//TODO make send message
				}
			}
			
			//Movimiento de stock
			ReportesMovimientoStock rm = new ReportesMovimientoStock();
			rm.setAlmacenid(idAV);
			rm.setProductoid(productoID);
			rm.setStock(cantidad);
			rm.setDif(stockold);
			em.persist(rm);
			em.flush();

		}catch(Exception e){
			e.printStackTrace();
			DataResponse dr = new DataResponse();
			dr.setMensaje("Producto o av no existe.");			
			return Response.status(500).entity(dr).build();			
		}
		return Response.status(200).build();
	}
	
	@GET
	@Path("{id}/colaboradores")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response getColaboradores(@PathParam(value="id")String idAV){

		Av a = em.find(Av.class, idAV);
		List<Usuario> colaboradores = a.getUsuarios();
		List<DataPersona> ret = new ArrayList<DataPersona>();
		
		for(Usuario u : colaboradores){
			DataPersona dp = new DataPersona();
			dp.setId(u.getId());
			dp.setApellido(u.getApellido());
			dp.setNombre(u.getNombre());			
			ret.add(dp);
		}
		return Response.status(200).entity(ret).build();
	}	
	
	
	@POST
	@Path("{id}/agregarcolaboradores")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response setColaboradores(@PathParam(value="id")String idAV, List<String> colaboradores){
		try{
			Av a = em.find(Av.class, idAV);

			for(String c : colaboradores){
				System.out.println(c);
				Usuario u = em.find(Usuario.class, c);
				a.addColaborador(u);
			}
			
		}catch(Exception E){
			E.printStackTrace();
			DataResponse dt = new DataResponse();
			dt.setMensaje("Error agregar colaboradores");
			return Response.status(500).entity(dt).build();
		}
		return Response.status(200).build();
	}	

	@POST
	@Path("{id}/colaboradores/{idColaborador}")
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	public Response addColaborador(@PathParam(value="id")String idAV,@PathParam(value="idColaborador") String colaborador){

		try{
			Av a = em.find(Av.class, idAV);
			Usuario u = em.find(Usuario.class, colaborador);
			a.addColaborador(u);
			
		}catch(Exception E){
			DataResponse dt = new DataResponse();
			dt.setMensaje("Error agregar colaborador");
			return Response.status(500).build();
		}
		return Response.status(200).build();
	}
	
	@GET
	@Path("datanotificacion")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getDataNotificacion(){		
		return Response.status(200).entity(new DataNotificacionStock()).build();
	}
		
	//eliminar notificaciones parametro
	@POST
	@Path("{almacenID}/notificaciones/stock")
	@Produces(MediaType.APPLICATION_JSON)
	public Response agregarNotificacionProducto(
			@PathParam(value="almacenID")String almacenID,
			DataNotificacionStock dn
			){
		StockPK sfk= new StockPK();
		sfk.setIdAv(almacenID);
		sfk.setIdProducto(dn.getProductoID());
		Stock s = em.find(Stock.class, sfk);
		s.setMinimo(dn.getMinimo());
		s.setNotifica(dn.getNotifica());
		return Response.status(200).build();
		
	}

}
