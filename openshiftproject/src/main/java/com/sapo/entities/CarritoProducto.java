package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the carrito_producto database table.
 * 
 */
@Entity
@Table(name="carrito_producto")
@NamedQuery(name="CarritoProducto.findAll", query="SELECT c FROM CarritoProducto c")
public class CarritoProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CarritoProductoPK id;

	@Column(name="cant_comprado")
	private Integer cantComprado;

	@Column(name="cant_total")
	private Integer cantTotal;

	@ManyToOne
	@MapsId("idAv")
	@JoinColumn(name="id_av")
	private Av av;

	@ManyToOne
	@MapsId("idProducto")
	@JoinColumn(name="id_producto")
	private Producto producto;

	public CarritoProducto() {
	}

	public CarritoProductoPK getId() {
		return this.id;
	}

	public void setId(CarritoProductoPK id) {
		this.id = id;
	}

	public Integer getCantComprado() {
		return this.cantComprado;
	}

	public void setCantComprado(Integer cantComprado) {
		this.cantComprado = cantComprado;
	}

	public Integer getCantTotal() {
		return this.cantTotal;
	}

	public void setCantTotal(Integer cantTotal) {
		this.cantTotal = cantTotal;
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