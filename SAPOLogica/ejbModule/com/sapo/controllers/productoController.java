package com.sapo.controllers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.sapo.datatypes.DataProducto;
import com.sapo.entities.*;

@Stateless
@LocalBean
public class productoController {

	@PersistenceContext(unitName="SAPOLogica")
	EntityManager em;
	
    public productoController() {
    }
    //CRUD
    
    public Producto getProducto(long id){
    	return em.find(Producto.class, id);   	
    }

    
    public List<Producto> getProductos(){
    	TypedQuery<Producto> query = em.createNamedQuery("Producto.findAll",Producto.class);
    	return query.getResultList();
    	
    }
   
    public void addProducto(DataProducto dp){
    	Producto p = new Producto();
   	
    	p.setNombre(dp.getNombre());
    	p.setDescripcion(dp.getDescripcion());
    	p.setCategoria(em.find(Categoria.class, dp.getCategoria()));
    	p.setGenerico(dp.getIsgenerico());
    	
    	em.persist(p); 
    	em.flush();
	}

    
	public void updateProducto(DataProducto dp){
		Producto p = new Producto();
    	p.setNombre(dp.getNombre());
    	p.setDescripcion(dp.getDescripcion());
    	p.setGenerico(dp.getIsgenerico());
    	p.setCategoria(em.find(Categoria.class, dp.getCategoria()));
 
		em.merge(p);    	
	}
    

}
