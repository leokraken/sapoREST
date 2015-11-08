package com.sapo.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="producto_usuario_tienda_notificacion")
@NamedQuery(name="ProductoUsuarioTiendaNotificacion.findAll", query="SELECT p FROM ProductoUsuarioTiendaNotificacion p")
public class ProductoUsuarioTiendaNotificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProductoUsuarioTiendaNotificacionPK id;

	private Integer minimo;

	//bi-directional many-to-one association to Av
	@ManyToOne
	@MapsId("tiendaid")
	@JoinColumn(name="tiendaid")
	private Av av;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@MapsId("productoid")
	@JoinColumn(name="productoid")
	private Producto producto;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@MapsId("usuarioid")
	@JoinColumn(name="usuarioid")
	private Usuario usuario;

	public ProductoUsuarioTiendaNotificacion() {
	}

	public ProductoUsuarioTiendaNotificacionPK getId() {
		return this.id;
	}

	public void setId(ProductoUsuarioTiendaNotificacionPK id) {
		this.id = id;
	}

	public Integer getMinimo() {
		return this.minimo;
	}

	public void setMinimo(Integer minimo) {
		this.minimo = minimo;
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

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}