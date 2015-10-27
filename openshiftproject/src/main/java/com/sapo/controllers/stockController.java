package com.sapo.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import javax.ejb.LocalBean;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import com.sapo.entities.Av;
import com.sapo.interfaces.IstockController;
import com.sapo.websockets.StockWSClient;


@Singleton
@LocalBean
@Path("/stock")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class stockController implements IstockController {
	
	@PersistenceContext(unitName="SAPOLogica")
	private EntityManager em;
	
	@Context
    UriInfo uriInfo;
	
	private HashMap<String, StockWSClient> stockclients;

    public stockController() {
    }
    
    public void init() throws URISyntaxException{
    	stockclients= new HashMap<>();
		Query q = em.createQuery("select A from Av A");
		
		@SuppressWarnings("unchecked")
		List<Av> avs = q.getResultList();
		String PORT="8080";
		String baseurl= "ws://"+uriInfo.getBaseUri().getHost() +":"+PORT+"/openshiftproject/stock/";
		for (Av a : avs){
			String url= baseurl+a.getId();
			System.out.println(url);
			URI endpointURI = new URI(url);		
			StockWSClient client = new StockWSClient(endpointURI);
			stockclients.put(a.getId(), client);			
		}
    }

}
