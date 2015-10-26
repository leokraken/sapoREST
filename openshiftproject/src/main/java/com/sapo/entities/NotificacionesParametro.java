package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


@Entity
@Table(name="notificaciones_parametros")
@NamedQuery(name="NotificacionesParametro.findAll", query="SELECT n FROM NotificacionesParametro n")
public class NotificacionesParametro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String mensaje;

	@ManyToOne
	@JoinColumn(name="avid")
	private Av av;

	@ManyToOne
	@JoinColumn(name="productoid")
	private Producto producto;

	public NotificacionesParametro() {
	}


	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
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