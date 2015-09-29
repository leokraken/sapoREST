package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the productos_genericos database table.
 * 
 */
@Entity
@Table(name="productos_genericos")
@NamedQuery(name="ProductosGenerico.findAll", query="SELECT p FROM ProductosGenerico p")
public class ProductosGenerico extends Producto implements Serializable {
	private static final long serialVersionUID = 1L;


	public ProductosGenerico() {
	}

}