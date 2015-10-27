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
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sapo.datatypes.DataCategoria;
import com.sapo.datatypes.DataResponse;
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
	public Response createTemplate(DataTemplate datatemplate){
		Template t = new Template();
		t.setDescripcion(datatemplate.getDescripcion());
		t.setNombre(datatemplate.getNombre());
		t.setCategorias(new ArrayList<Categoria>());

		for(DataCategoria dc : datatemplate.getCategorias()){
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
		datatemplate.setID(t.getId());
		return Response.status(201).entity(datatemplate).build();
	}
	
	@PUT
	@Path("{templateID}")
	@Consumes(MediaType.APPLICATION_JSON)	
	@Produces(MediaType.APPLICATION_JSON)	
	public Response createTemplate(@PathParam(value="templateID") Long templateid, DataTemplate datatemplate){
		Template t = em.find(Template.class, templateid);
		t.setDescripcion(datatemplate.getDescripcion());
		t.setNombre(datatemplate.getNombre());
		t.setCategorias(new ArrayList<Categoria>());
		try{
			
			for(DataCategoria dc : datatemplate.getCategorias()){
				t.getCategorias().add(em.find(Categoria.class, dc.getID()));
			}
		
			em.merge(t);
			em.flush();
		}catch(Exception e){
			e.printStackTrace();
			//conflicto
			DataResponse dr= new DataResponse();
			dr.setMensaje("Error: inesperado.");
			return Response.status(409).build();
		}
		//creado
		datatemplate.setID(t.getId());
		return Response.status(200).entity(datatemplate).build();
	}

	
	@GET
	@Path("{templateID}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response createTemplate(@PathParam(value="templateID")Long id){
		Template t = em.find(Template.class, id);
		if(t!=null){
			DataTemplate dt = new DataTemplate();
			dt.setDescripcion(t.getDescripcion());
			dt.setID(t.getId());
			dt.setNombre(t.getNombre());
			dt.setCategorias(new ArrayList<DataCategoria>());
			for(Categoria c : t.getCategorias()){
				DataCategoria dc = new DataCategoria();
				dc.setDescripcion(c.getDescripcion());
				dc.setID(c.getId());
				dc.setIsgenerico(c.getGenerica());
				dc.setNombre(c.getNombre());
				dt.getCategorias().add(dc);
			}
			return Response.status(200).entity(dt).build();

		}else{
			DataResponse dr = new DataResponse();
			dr.setMensaje("No existe template");
			return Response.status(500).entity(dr).build();
		}
	}
	
	
	@DELETE
	@Path("{templateID}")
	@Produces(MediaType.APPLICATION_JSON)	
	public Response deleteTemplate(@PathParam(value="templateID")Long id){
		Template t = em.find(Template.class, id);
		if(t!=null)
			em.remove(t);
		return Response.status(200).build();
	}
}
