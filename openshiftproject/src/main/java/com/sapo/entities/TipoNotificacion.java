package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;

import com.sapo.entities.Notificaciones;

import java.util.List;


/**
 * The persistent class for the tipo_notificaciones database table.
 * 
 */
@Entity
@Table(name="tipo_notificaciones")
@NamedQuery(name="TipoNotificacion.findAll", query="SELECT t FROM TipoNotificacion t")
public class TipoNotificacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	private String mensaje;

	private String nombre;

	//bi-directional many-to-one association to Notificacion
	@OneToMany(mappedBy="tipoNotificacion")
	private List<Notificaciones> notificaciones;

	public TipoNotificacion() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Notificaciones> getNotificaciones() {
		return this.notificaciones;
	}

	public void setNotificaciones(List<Notificaciones> notificaciones) {
		this.notificaciones = notificaciones;
	}

	public Notificaciones addNotificacione(Notificaciones notificacione) {
		getNotificaciones().add(notificacione);
		notificacione.setTipoNotificacione(this);

		return notificacione;
	}

	public Notificaciones removeNotificacione(Notificaciones notificacione) {
		getNotificaciones().remove(notificacione);
		notificacione.setTipoNotificacione(null);

		return notificacione;
	}

}