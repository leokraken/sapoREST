package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the categorias_especificas database table.
 * 
 */
@Entity
@Table(name="categorias_especificas")
@NamedQuery(name="CategoriasEspecifica.findAll", query="SELECT c FROM CategoriasEspecifica c")
public class CategoriasEspecifica extends Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Av
	@ManyToOne
	@JoinColumn(name="id_av")
	private Av av;

	public CategoriasEspecifica() {
	}

	public Av getAv() {
		return this.av;
	}

	public void setAv(Av av) {
		this.av = av;
	}

}