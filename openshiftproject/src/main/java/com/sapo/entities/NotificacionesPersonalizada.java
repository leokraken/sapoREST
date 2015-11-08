package com.sapo.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the notificaciones_personalizadas database table.
 * 
 */
@Entity
@Table(name="notificaciones_personalizadas")
@NamedQuery(name="NotificacionesPersonalizada.findAll", query="SELECT n FROM NotificacionesPersonalizada n")
public class NotificacionesPersonalizada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private Timestamp fecha = new Timestamp(new Date().getTime());

	private String mensaje;

	//bi-directional many-to-one association to Av
	@ManyToOne
	@JoinColumn(name="avid")
	private Av av;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="productoid")
	private Producto producto;

	//bi-directional many-to-one association to TipoNotificacione
	@ManyToOne
	@JoinColumn(name="tipo_notificacion")
	private TipoNotificacion tipoNotificacione;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioid")
	private Usuario usuario;

	public NotificacionesPersonalizada() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
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

	public TipoNotificacion getTipoNotificacione() {
		return this.tipoNotificacione;
	}

	public void setTipoNotificacione(TipoNotificacion tipoNotificacione) {
		this.tipoNotificacione = tipoNotificacione;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}