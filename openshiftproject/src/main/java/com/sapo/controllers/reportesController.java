package com.sapo.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataEstadistica;
import com.sapo.datatypes.reportes.DataEstadisticaUsuario;
import com.sapo.datatypes.reportes.DataReporteAlmacen;
import com.sapo.datatypes.reportes.DataReporteProducto;
import com.sapo.datatypes.reportes.DataReporteStock;
import com.sapo.entities.Av;
import com.sapo.entities.ReportesMovimientoStock;
import com.sapo.entities.Stock;
import com.sapo.entities.Usuario;

@Stateless
@LocalBean
@Path("/reportes")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class reportesController {

	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
    public reportesController() {
    }
    /*
    @GET
	@Path("/{almacen}/valorizacion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstadisticasAlmacen(@PathParam("almacen")String almacen){	
    	Av a = em.find(Av.class,almacen);
    	DataReporteAlmacen dra = new DataReporteAlmacen();
    	dra.setProductos(a.getStocks().size());
    	
    	int stockcount = 0;
    	for(Stock s: a.getStocks()){
    		stockcount+= s.getCantidad();
    	}
    	dra.setStock(stockcount);
    	dra.setVisitas(a.getVisitas());
    	dra.setAlmacen(a.getNombre());
    	return Response.status(200).entity(dra).build();
    }*/

    @GET
	@Path("/{usuario}/valorizaciones")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstadisticasAlmacenes(@PathParam("usuario")String usuario){	
    	Usuario u = em.find(Usuario.class,usuario);
    	DataReporteAlmacen reportes= new DataReporteAlmacen();
    	ArrayList<String> labels = new ArrayList<>(); 
    	labels.add("Visitas");//.set(0, "Visitas");
    	labels.add("Productos");//.set(1, "Productos");
    	labels.add("Stocks");//set(2, "Stocks");

    	List<Av> almacenes = u.getAvs1(); 

    	
    	reportes.setLabels(labels);
    	reportes.setSeries(new ArrayList<String>());
    	reportes.setData(new ArrayList<List<Long>>(almacenes.size()));
    	
    	
    	for(int i=0;i<almacenes.size(); i++){
        	reportes.getSeries().add(almacenes.get(i).getId());//set(i, almacenes.get(i).getId());
        	
        	long stockcount = 0;
        	for(Stock s: almacenes.get(i).getStocks()){
        		stockcount+= s.getCantidad();
        	}
        	ArrayList<Long> data = new ArrayList<>();
        	
        	data.add(almacenes.get(i).getVisitas());//set(0, almacenes.get(i).getVisitas());
        	data.add((long)almacenes.get(i).getStocks().size());//set(1, (long)almacenes.get(i).getStocks().size());
        	data.add(stockcount);//set(2, stockcount);
        	reportes.getData().add(data);//getData().set(i, data);

    	}

    	return Response.status(200).entity(reportes).build();
    }
    
    @GET
   	@Path("/movimientos/{usuario}")
   	@Consumes(MediaType.APPLICATION_JSON)
   	@Produces(MediaType.APPLICATION_JSON)
   	public Response getMovimientosAlmacenes(@PathParam("usuario")String usuario, @QueryParam("dias") Integer dias){	
    	Usuario u = em.find(Usuario.class,usuario);
    	if(u==null)
    		return Response.status(404).build();
    	if(dias==null)
    		dias=4;
    	
    	List<Av> almacenesusuario = u.getAvs1();
    	Calendar c = Calendar.getInstance();
    	//c.add(Calendar.DATE, -dias);
    	//beginDay(c);
    	
    	
    	//create
    	DataReporteAlmacen reportes= new DataReporteAlmacen();
    	ArrayList<String> labels = new ArrayList<>();

    	reportes.setLabels(labels);
    	reportes.setSeries(new ArrayList<String>());
    	reportes.setData(new ArrayList<List<Long>>());
    	
    	for(int i=0; i<dias;i++){
    		Timestamp t = new Timestamp(c.getTimeInMillis());
        	reportes.getSeries().add(t.toString());
    		c.add(Calendar.DATE, -1);
    	}
    	
    	//datareporte end.
		//Timestamp t3= new Timestamp(c.getTimeInMillis());

    	//System.out.println("FECHA DE HOY :: "+t3.toString());
    	for(int i=0; i<almacenesusuario.size();i++){
    		//serie
    		reportes.getLabels().add(almacenesusuario.get(i).getId());
    		String almacen= almacenesusuario.get(i).getId();
    		
    		List<Long> stock_fecha = new ArrayList<>();
    		
    		for(int j=0; j<dias; j++){
    			Calendar cal = Calendar.getInstance();
    			beginDay(cal);
    			cal.add(Calendar.DATE, -j);
    			Timestamp t1= new Timestamp(cal.getTimeInMillis());

    			cal.add(Calendar.DATE, 1);
    			Timestamp t2= new Timestamp(cal.getTimeInMillis());
    			
    			System.out.println("INTERVALO:"+t1.toString()+t2.toString());
    			
    			Query q = em.createQuery("SELECT r from ReportesMovimientoStock R where R.av.id=:almacen and R.fecha>=:t1 and R.fecha<:t2 order by R.fecha");
            	q.setParameter("almacen", almacen);
            	q.setParameter("t1", t1);
            	q.setParameter("t2", t2);

            	
            	
            	HashMap<Long, Integer> productos = new HashMap<>();
            	Integer init_prod =0;
            	
            	@SuppressWarnings("unchecked")
        		List<ReportesMovimientoStock> rep = q.getResultList();

            	for(ReportesMovimientoStock r: rep){
            		Calendar tt = Calendar.getInstance();
            		tt.setTimeInMillis(r.getFecha().getTime());
            		beginDay(tt); //truncates
            		Long key= tt.getTimeInMillis();    		
            		Long prod = r.getProducto().getId();

            		if(!productos.containsKey(prod)){
            			productos.put(key, r.getStock());
            			init_prod+=r.getStock();
            		}

            	}
            	stock_fecha.add((long)init_prod);

            	
    		}
    		reportes.getData().add(stock_fecha);
	
    	}

       	return Response.status(200).entity(reportes).build();
    }
    
    
    @GET
	@Path("/movimientos/stock")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovimientosStock(@QueryParam("almacen") String almacen){
    	
    	Query q = null;
    	if(almacen==null)
    		q= em.createQuery("SELECT r from ReportesMovimientoStock R order by R.fecha DESC");
    	else{
    		q= em.createQuery("SELECT r from ReportesMovimientoStock R where R.av.id=:almacen order by R.fecha DESC");
    		q.setParameter("almacen", almacen);
    	}

    	@SuppressWarnings("unchecked")
		List<ReportesMovimientoStock> rep = q.getResultList();
    	List<DataReporteStock> ret = new ArrayList<>();
    	for(ReportesMovimientoStock r: rep){
    		DataReporteStock drs = new DataReporteStock();
    		drs.setDiferencia(r.getDif());
    		drs.setFecha(r.getFecha());
    		drs.setProducto_id(r.getProducto().getId());
    		drs.setProducto_nombre(r.getProducto().getNombre());
    		drs.setStock(r.getStock());
    		drs.setAlmacen_id(r.getAv().getId());
    		drs.setAlmacen_nombre(r.getAv().getNombre());
    		ret.add(drs);
    	}
    	return Response.status(200).entity(ret).build();
    }
    
    
    
    private static void beginDay(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0); 	
    }
    
    @GET
	@Path("/global")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
public Response getReportesGlobal(@QueryParam("productos") Integer productos){
    	Query q_users= em.createQuery("select count(*) from Usuario u");
    	Query q_prods = em.createQuery("select count(*) from Producto p");
    	Query q_premium = em.createQuery("select count(*) from Usuario u where u.tipocuenta.id>1");
    	Query q_categorias = em.createQuery("select count(*) from Categoria c where c.generica=true");
    	
    	if(productos==null)
    		productos= 10;
    	Query q_productos = em.createQuery("select s.producto.id, s.producto.nombre, s.producto.descripcion, count(*) from Stock s group by s.producto.id, s.producto.nombre, s.producto.descripcion order by count(s.producto.id) DESC");

    	Long cantidad_u = (Long) q_users.getSingleResult();
    	Long cantidad_p = (Long) q_prods.getSingleResult();
    	Long user_premium = (Long) q_premium.getSingleResult();
    	Long categorias = (Long) q_categorias.getSingleResult();
    	@SuppressWarnings("unchecked")
		List<Object[]> productosorder = q_productos.setMaxResults(productos).getResultList();
    	
    	List<DataReporteProducto> prods = new ArrayList<>();
    	for(Object[] o : productosorder){
    		Long p_id = (Long) o[0];
    		String p_nombre = (String) o[1];
    		String p_desc = (String) o[2];
    		Long count = (Long) o[3];
    		
    		DataReporteProducto drp = new DataReporteProducto();
    		drp.setId(p_id);
    		drp.setDescripcion(p_desc);
    		drp.setNombre(p_nombre);
    		drp.setCantidad_tiendas(count);
    		prods.add(drp);
    	}
    	
    	DataEstadistica de = new DataEstadistica();
    	de.setUsuarios_registrados(cantidad_u);
    	de.setProductos_genericos(cantidad_p);
    	de.setUsuarios_premium(user_premium);
    	de.setCategorias_genericas(categorias);
    	de.setProductos(prods);
    	return Response.status(200).entity(de).build();
    }
    
    @GET
	@Path("/usuario/{usuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getReportesTienda(@PathParam("usuario") String usuario){
    	Usuario u = em.find(Usuario.class, usuario);
    	Long cant_almacenes = (long) u.getAvs1().size();
    	
    	Query q = em.createQuery("select count(*) from NotificacionesPersonalizada n where n.usuario.id=:usuario");
    	q.setParameter("usuario", usuario);
    	long notificaciones = (long)q.getSingleResult();
    	List<Av> avs = u.getAvs1();
    	long colaboradores = 0;
    	for(Av a : avs){
    		colaboradores+= a.getUsuarios().size();
    	}
    	DataEstadisticaUsuario deu = new DataEstadisticaUsuario();
    	deu.setCantidad_almacenes(cant_almacenes);
    	deu.setCantidad_colaboradores(colaboradores);
    	deu.setCantidad_notificaciones(notificaciones);
    	
    	
    	return Response.status(200).entity(deu).build();
    }
    
}
