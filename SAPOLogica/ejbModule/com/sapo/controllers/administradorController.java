package com.sapo.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sapo.entities.Administrador;

@Stateless
@LocalBean
public class administradorController {

	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
    public administradorController() {

    }

    public Administrador getAdministrador(String id){
    	return em.find(Administrador.class, id);
    }
    
    public List<Administrador> getAdministradores(){
    	TypedQuery<Administrador> query = em.createNamedQuery("Administrador.findAll",Administrador.class);
    	return query.getResultList();
    
    }
    
    public void addAdministrador(Administrador admin){
        	em.persist(admin); 
        	em.flush();
    }
    
    public void updateAdministrador(Administrador admin){
    	em.merge(admin);    	
    }
     
}
