package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="usuarios")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String apellido;

	private String nombre;

	private String token;
	
	@ManyToOne
	@JoinColumn(name="tipo_cuenta")
	private TipoCuenta tipocuenta;
	
	//bi-directional many-to-one association to NotificacionesLimiteCuenta
	@OneToMany(mappedBy="usuario")
	private List<Notificaciones> notificacionesLimiteCuentas;

	
	
	public TipoCuenta getTipocuenta() {
		return tipocuenta;
	}

	public void setTipocuenta(TipoCuenta tipocuenta) {
		this.tipocuenta = tipocuenta;
	}

	//bi-directional many-to-one association to Av
	@OneToMany(mappedBy="usuario",cascade=CascadeType.REMOVE)
	private List<Av> avs1;

	//bi-directional many-to-many association to Av
	@ManyToMany(cascade=CascadeType.REMOVE)
	@JoinTable(
		name="usuarios_invitados"
		, joinColumns={
			@JoinColumn(name="id_usuario")
			}
		, inverseJoinColumns={
			@JoinColumn(name="id_av")
			}
		)
	private List<Av> avs2;

	public Usuario() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Av> getAvs1() {
		return this.avs1;
	}

	public void setAvs1(List<Av> avs1) {
		this.avs1 = avs1;
	}

	public Av addAvs1(Av avs1) {
		getAvs1().add(avs1);
		avs1.setUsuario(this);

		return avs1;
	}

	public Av removeAvs1(Av avs1) {
		getAvs1().remove(avs1);
		avs1.setUsuario(null);

		return avs1;
	}

	public List<Av> getAvs2() {
		return this.avs2;
	}

	public void setAvs2(List<Av> avs2) {
		this.avs2 = avs2;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public List<Notificaciones> getNotificacionesLimiteCuentas() {
		return this.notificacionesLimiteCuentas;
	}

	public void setNotificacionesLimiteCuentas(List<Notificaciones> notificacionesLimiteCuentas) {
		this.notificacionesLimiteCuentas = notificacionesLimiteCuentas;
	}

	public Notificaciones addNotificacionesLimiteCuenta(Notificaciones notificacionesLimiteCuenta) {
		getNotificacionesLimiteCuentas().add(notificacionesLimiteCuenta);
		notificacionesLimiteCuenta.setUsuario(this);

		return notificacionesLimiteCuenta;
	}

	public Notificaciones removeNotificacionesLimiteCuenta(Notificaciones notificacionesLimiteCuenta) {
		getNotificacionesLimiteCuentas().remove(notificacionesLimiteCuenta);
		notificacionesLimiteCuenta.setUsuario(null);

		return notificacionesLimiteCuenta;
	}
	
}