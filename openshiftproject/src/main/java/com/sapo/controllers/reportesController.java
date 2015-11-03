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
    }

    @GET
	@Path("/{usuario}/valorizaciones")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getEstadisticasAlmacenes(@PathParam("usuario")String usuario){	
    	Usuario u = em.find(Usuario.class,usuario);
    	List<DataReporteAlmacen> reportes= new ArrayList<>();
    	for(Av a : u.getAvs1()){
        	DataReporteAlmacen dra = new DataReporteAlmacen();
        	dra.setProductos(a.getStocks().size());
        	
        	int stockcount = 0;
        	for(Stock s: a.getStocks()){
        		stockcount+= s.getCantidad();
        	}
        	dra.setStock(stockcount);
        	dra.setVisitas(a.getVisitas());
        	dra.setAlmacen(a.getNombre());    		
    		reportes.add(dra);
    	}

    	return Response.status(200).entity(reportes).build();
    }
    
}
