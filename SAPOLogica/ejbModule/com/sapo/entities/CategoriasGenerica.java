package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the categorias_genericas database table.
 * 
 */
@Entity
@Table(name="categorias_genericas")
@NamedQuery(name="CategoriasGenerica.findAll", query="SELECT c FROM CategoriasGenerica c")
public class CategoriasGenerica extends Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	public CategoriasGenerica() {
	}
}