package com.sapo.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataLimiteCuenta;
import com.sapo.datatypes.DataNotificacion;
import com.sapo.datatypes.DataResponse;
import com.sapo.entities.Av;
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
        	Notificaciones nlc = new Notificaciones();
        	nlc.setMensaje(dlc.getMensaje());
        	nlc.setUsuario(em.find(Usuario.class, dlc.getUsuarioid()));
        	nlc.setTipoNotificacione(em.find(TipoNotificacion.class, 1));
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
    
    @SuppressWarnings("unchecked")
	@GET
	@Path("/limitecuenta/{usuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificaciones(@PathParam(value="usuario")String usuario){	
		Query q= em.createQuery("select S from Notificaciones S "
				+ "where S.tipoNotificacion.id=:tn"
				+ " and S.usuario.id=:u"
				+ " order by S.fecha DESC");
		
		q.setParameter("tn", 1);
		q.setParameter("u", usuario);
		List<Notificaciones> nots = q.getResultList();
		
		List<DataNotificacion> datanots= new ArrayList<DataNotificacion>();
		for (Notificaciones n : nots){
			DataNotificacion dn = new DataNotificacion();
			dn.setId(n.getId());
			dn.setMensaje(n.getMensaje());
			dn.setTipo_notificacion(n.getTipoNotificacion().getId());
			datanots.add(dn);
		}
    	return Response.status(200).entity(datanots).build();
    }
    
    
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("{almacenID}/stock")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNotificacionesStock(@QueryParam("limit") Integer limit, @PathParam(value="almacenID")String almacenID){
		//para obtener usuario dueño
		Av a = em.find(Av.class, almacenID);
		
		Query q= em.createQuery("select S from Notificaciones S "
				+ "where S.tipoNotificacion.id=:tn"
				+ " and S.usuario.id=:u"
				+ " order by S.fecha DESC");
		
		q.setParameter("tn", 2);
		q.setParameter("u", a.getUsuario().getId());
		List<Notificaciones> nots = null;

		if(limit!=null)
			nots = q.setMaxResults(limit).getResultList();
		else
			nots = q.getResultList();
		
		
		List<DataNotificacion> datanots= new ArrayList<DataNotificacion>();
		for (Notificaciones n : nots){
			DataNotificacion dn = new DataNotificacion();
			dn.setId(n.getId());
			dn.setMensaje(n.getMensaje());
			dn.setTipo_notificacion(n.getTipoNotificacion().getId());
			datanots.add(dn);
		}
		return Response.status(200).entity(datanots).build();
		
	}
	
	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteNotificacion(@PathParam(value="id")Long notid){
		
		em.remove(em.find(Notificaciones.class, notid));
		em.flush();	
		return Response.status(200).build();
	}
	
   
}
