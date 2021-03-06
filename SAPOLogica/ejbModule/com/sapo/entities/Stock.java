package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the stock database table.
 * 
 */
@Entity
@NamedQuery(name="Stock.findAll", query="SELECT s FROM Stock s")
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StockPK id;

	private Integer cantidad;

	//bi-directional many-to-one association to Av
	@ManyToOne
	@MapsId("idAv")
	@JoinColumn(name="id_av")
	private Av av;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@MapsId("idProducto")
	@JoinColumn(name="id_producto")
	private Producto producto;

	public Stock() {
	}

	public StockPK getId() {
		return this.id;
	}

	public void setId(StockPK id) {
		this.id = id;
	}

	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
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