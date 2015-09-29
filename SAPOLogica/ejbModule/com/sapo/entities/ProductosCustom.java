package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the productos_custom database table.
 * 
 */
@Entity
@Table(name="productos_custom")
@NamedQuery(name="ProductosCustom.findAll", query="SELECT p FROM ProductosCustom p")
public class ProductosCustom extends Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Av
	@ManyToOne
	@JoinColumn(name="id_av")
	private Av av;

	public ProductosCustom() {
	}

	public Av getAv() {
		return this.av;
	}

	public void setAv(Av av) {
		this.av = av;
	}

}