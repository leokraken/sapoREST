package com.sapo.controllers;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataLimiteCuenta;
import com.sapo.datatypes.DataResponse;
import com.sapo.entities.Notificaciones;
import com.sapo.entities.TipoNotificacion;
import com.sapo.entities.Usuario;

@Stateless
@LocalBean
@Path("/notificaciones")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class notificationsController {

	
	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
    public notificationsController(){
    	//c = new ChatClient(new URI("ws://localhost:8080/openshiftproject/echo")); 	
    }

    
    @GET
	@Path("/datalimite")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    public DataLimiteCuenta getdatalimite(){
    	return new DataLimiteCuenta();
    }
    
    @POST
	@Path("/limitecuenta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response limiteCuenta(DataLimiteCuenta dlc){	
    	try{
    		System.out.println(dlc.getUsuarioid());
    		System.out.println(dlc.getMensaje());

        	Notificaciones nlc = new Notificaciones();
        	nlc.setMensaje(dlc.getMensaje());
        	nlc.setUsuario(em.find(Usuario.class, dlc.getUsuarioid()));
        	nlc.setTipoNotificacione(em.find(TipoNotificacion.class, dlc.getTipo_notificacion()));
        	em.persist(nlc);
        	em.flush();
        	return Response.status(200).build();    		
    	}catch(Exception e){
    		e.printStackTrace();
    		DataResponse dr = new DataResponse();
    		dr.setMensaje("Exception");
    		dr.setDescripcion(e.getMessage());
    		return Response.status(500).entity(dr).build();
    	}

	}
    
}
