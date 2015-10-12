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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataCategoria;
import com.sapo.datatypes.DataTemplate;
import com.sapo.entities.Categoria;
import com.sapo.entities.Template;

@Stateless
@LocalBean
@Path("/templates")
@Produces({ "application/xml", "application/json" })
@Consumes({ "application/xml", "application/json" })
public class templateController {

	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
	@GET
	@Path("datatemplate")
	@Produces(MediaType.APPLICATION_JSON)
	public DataTemplate getDataTemplate(){
		return new DataTemplate();
	}

	@GET
	@Path("datacategoria")
	@Produces(MediaType.APPLICATION_JSON)
	public DataCategoria getDataCategoria(){
		return new DataCategoria();
	}
	
	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DataTemplate> getTemplates(){
    	TypedQuery<Template> query = em.createNamedQuery("Template.findAll",Template.class);
    	List<Template>  templates = query.getResultList();
    	List<DataTemplate> ret = new ArrayList<DataTemplate>();
		for(Template t : templates){
	    	 
			List<DataCategoria> categorias = new ArrayList<DataCategoria>();
			for(Categoria c : t.getCategorias()){
				DataCategoria dc = new DataCategoria();
				dc.setIsgenerico(c.getGenerica());
				dc.setID(c.getId());
				dc.setNombre(c.getNombre());
				dc.setDescripcion(c.getDescripcion());		
				categorias.add(dc);
			}
	    	DataTemplate dt = new DataTemplate();
	    	dt.setID(t.getId());
	    	dt.setDescripcion(t.getDescripcion());
	    	dt.setNombre(t.getNombre());
	    	dt.setCategorias(categorias);
	    	ret.add(dt);
		}
		return ret;
	}
	
	//admin
	@POST
	@Path("create")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response createTemplate(DataTemplate datetemplate){
		Template t = new Template();
		t.setDescripcion(datetemplate.getDescripcion());
		t.setNombre(datetemplate.getNombre());
		t.setCategorias(new ArrayList<Categoria>());

		for(DataCategoria dc : datetemplate.getCategorias()){
			System.out.println("Print data id");
			System.out.println(dc.getID());
			t.getCategorias().add(em.find(Categoria.class, dc.getID()));
		}
		try{
			em.persist(t);
			em.flush();
		}catch(Exception e){
			e.printStackTrace();
			//conflicto
			return Response.status(409).build();
		}
		//creado
		return Response.status(201).build();
	}
}
