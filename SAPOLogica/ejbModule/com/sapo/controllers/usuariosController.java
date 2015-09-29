package com.sapo.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sapo.entities.Usuario;

@Stateless
@LocalBean
public class usuariosController {


	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
    public usuariosController() {
    }
    
    public Usuario getUsuario(String id){
    	return em.find(Usuario.class, id);
    }
    
    public List<Usuario> getUsuarios(){
    	TypedQuery<Usuario> query = em.createNamedQuery("Usuarios.findAll",Usuario.class);
    	return query.getResultList();
    
    }
    
    public void addUsuario(Usuario user){
        	em.persist(user); 
        	em.flush();
    }
    
    public void updateAdministrador(Usuario user){
    	em.merge(user);    	
    }
    
    
    
}
