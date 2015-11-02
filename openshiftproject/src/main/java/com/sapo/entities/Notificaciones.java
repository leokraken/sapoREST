package com.sapo.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

import com.sapo.entities.Usuario;

@Entity
@Table(name="notificaciones")
@NamedQuery(name="Notificaciones.findAll", query="SELECT n FROM Notificaciones n")
public class Notificaciones implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String mensaje;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuarioid")
	private Usuario usuario;

	//bi-directional many-to-one association to TipoNotificacione
	@ManyToOne
	@JoinColumn(name="tipo_notificacion")
	private TipoNotificacion tipoNotificacion;
	
	private Timestamp fecha = new Timestamp(new Date().getTime()); 
	
	public Notificaciones() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMensaje() {
		return this.mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public TipoNotificacion getTipoNotificacion() {
		return this.tipoNotificacion;
	}

	public void setTipoNotificacione(TipoNotificacion tipoNotificacion) {
		this.tipoNotificacion = tipoNotificacion;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

}