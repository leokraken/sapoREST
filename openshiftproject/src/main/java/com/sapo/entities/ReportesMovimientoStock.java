package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


import java.sql.Timestamp;


/**
 * The persistent class for the reportes_movimiento_stock database table.
 * 
 */
@Entity
@Table(name="reportes_movimiento_stock")
@NamedQuery(name="ReportesMovimientoStock.findAll", query="SELECT r FROM ReportesMovimientoStock r")
public class ReportesMovimientoStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Integer dif;

	private Timestamp fecha;

	private Integer stock;

	//bi-directional many-to-one association to Av
	@ManyToOne
	@JoinColumn(name="almacenid")
	private Av av;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="productoid")
	private Producto producto;

	public ReportesMovimientoStock() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getDif() {
		return this.dif;
	}

	public void setDif(Integer dif) {
		this.dif = dif;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Av getAv() {
		return this.av;
	}

	public void setAv(Av av) {
		this.av = av;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}