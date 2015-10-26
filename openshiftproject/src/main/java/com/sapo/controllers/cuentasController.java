package com.sapo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataCuenta;
import com.sapo.datatypes.DataResponse;
import com.sapo.entities.TipoCuenta;
import com.sapo.entities.Usuario;

@Stateless
@LocalBean
@Path("/cuentas")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class cuentasController {

	@PersistenceContext(unitName="SAPOLogica")
	private EntityManager em;
	
    public cuentasController() {
    }

    @GET
	@Path("datacuenta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DataCuenta getDataCuenta(){
    	return new DataCuenta();
    }
    
    @GET
	@Path("")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCuentas(){
    	TypedQuery<TipoCuenta> query = em.createNamedQuery("TipoCuenta.findAll",TipoCuenta.class);
    	List<TipoCuenta> cuentas = query.getResultList();
    	List<DataCuenta> ret = new ArrayList<DataCuenta>();
    	for(TipoCuenta c : cuentas){
    		DataCuenta dc = new DataCuenta();
    		dc.setCuentaID(c.getId());
    		dc.setDescripcion(c.getDescripcion());
    		dc.setNombre(c.getNombre());
    		dc.setPrecio(c.getPrecio());
    		ret.add(dc);
    	}
    	return Response.status(200).entity(ret).build();	
    }    

    @POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCuenta(DataCuenta dc){ 
		TipoCuenta tc = new TipoCuenta();

    	try{
    		tc.setDescripcion(dc.getDescripcion());
    		tc.setNombre(dc.getNombre());
    		tc.setPrecio(dc.getPrecio());   
    		em.persist(tc);
    		em.flush();
    		//return
        	dc.setCuentaID(tc.getId());
        	return Response.status(201).entity(dc).build();
    	}catch(Exception e){
    		
    		DataResponse dr = new DataResponse();
    		dr.setMensaje("Conflicto");
    		dr.setDescripcion("Error insertar tipo cuenta...");
    		return Response.status(409).build();
    	} 	
    } 

    @PUT
	@Path("{cuentaID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCuenta(@PathParam(value="cuentaID") int cuentaid, DataCuenta dc){ 

    	try{
    		TipoCuenta tc = em.find(TipoCuenta.class, dc.getCuentaID());
    		tc.setDescripcion(dc.getDescripcion());
    		tc.setNombre(dc.getNombre());
    		tc.setPrecio(dc.getPrecio());   
    		em.merge(tc);
    		em.flush();
    		//return
        	dc.setCuentaID(tc.getId());
        	return Response.status(200).entity(dc).build();
    	}catch(Exception e){
    		
    		DataResponse dr = new DataResponse();
    		dr.setMensaje("Conflicto");
    		dr.setDescripcion("Error al actualizar tipo cuenta...");
    		return Response.status(409).build();
    	} 	
    } 
    
    @PUT
	@Path("update/{usuarioID}/{cuentaID}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCuentaUsuario(@PathParam(value="usuarioID") String userID, @PathParam(value="cuentaID") int cuentaID){     
    	Usuario u = em.find(Usuario.class, userID);
    	u.setTipocuenta(em.find(TipoCuenta.class, cuentaID));
    	em.merge(u);
    	return Response.status(200).build();
    }
    
}