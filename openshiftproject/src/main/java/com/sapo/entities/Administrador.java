package com.sapo.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="administradores")
@NamedQuery(name="Administrador.findAll", query="SELECT a FROM Administrador a")
public class Administrador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String apellido;

	private String nombre;
	
	private String token;
	
	private String password;

	private Timestamp expires;
	
	public Administrador() {
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getExpires() {
		return expires;
	}

	public void setExpires(Timestamp expires) {
		this.expires = expires;
	}

	

}