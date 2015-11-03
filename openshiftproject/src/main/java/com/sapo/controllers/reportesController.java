package com.sapo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.reportes.DataReporteAlmacen;
import com.sapo.entities.Av;
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
    
}
