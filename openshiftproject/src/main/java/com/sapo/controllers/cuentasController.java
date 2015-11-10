package com.sapo.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataCuenta;
import com.sapo.datatypes.DataNotificacionCuentas;
import com.sapo.datatypes.DataResponse;
import com.sapo.entities.Notificaciones;
import com.sapo.entities.TipoCuenta;
import com.sapo.entities.Usuario;

@Stateless
@LocalBean
@Path("/cuentas")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class cuentasController {

	public static final long DAYS = 3600*1000*24;
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
    		dc.setTiempo(c.getTiempo());
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
    		tc.setTiempo(dc.getTiempo());
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
    		tc.setTiempo(dc.getTiempo());
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
    	TipoCuenta tipocuenta = em.find(TipoCuenta.class, cuentaID);
    	u.setTipocuenta(tipocuenta);
    	u.setExpires(new Timestamp(new Date().getTime()+(DAYS*tipocuenta.getTiempo())));
    	em.merge(u);
    	return Response.status(200).build();
    }
    
	@GET
	@Path("/time")
	@Produces(MediaType.APPLICATION_JSON)
	public Response gettime(){
		return Response.status(200).entity(new Date()).build();
	}
    
	@SuppressWarnings("unchecked")
	@GET
	@Path("/vencimiento")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCuentasValidez(@QueryParam("fecha")Long fecha){
		Query q= null;
		if(fecha!=null){
			q=em.createQuery("select u from Usuario u where u.expires<:fecha");		
			q.setParameter("fecha", new Timestamp(fecha));
		}else{
			q=em.createQuery("select u from Usuario u");		
		}
		
		List<Usuario> users = q.getResultList();
		List<DataNotificacionCuentas> nots = new ArrayList<>();
		for(Usuario u : users){
			Boolean not=true;
			for(Notificaciones n : u.getNotificacionesLimiteCuentas()){
				if(n.getUsuario().getId()==u.getId() && n.getTipoNotificacion().getId()==1){
					not=false;
					break;
				}
			}
			if(not){
				DataNotificacionCuentas n = new DataNotificacionCuentas();
				n.setExpira(u.getExpires());
				n.setUsuario(u.getId());
				n.setTipo_cuenta(u.getTipocuenta().getId());
				n.setAlias_cuenta(u.getTipocuenta().getNombre());
				nots.add(n);			
			}
		}
		return Response.status(200).entity(nots).build();
	}
    
    @GET
	@Path("{usuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsuarioCuenta(@PathParam("usuario") String usuario){ 
    	Usuario u = em.find(Usuario.class, usuario);
    	DataCuenta dc = new DataCuenta();
    	dc.setCuentaID(u.getTipocuenta().getId());
    	dc.setDescripcion(u.getTipocuenta().getDescripcion());
    	dc.setNombre(u.getTipocuenta().getNombre());
    	dc.setPrecio(u.getTipocuenta().getPrecio());
    	dc.setTiempo(u.getTipocuenta().getTiempo());
    	return Response.status(200).entity(dc).build();
    }
}
