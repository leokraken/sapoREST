package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the administradores database table.
 * 
 */
@Entity
@Table(name="administradores")
@NamedQuery(name="Administrador.findAll", query="SELECT a FROM Administrador a")
public class Administrador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String apellido;

	private String nombre;

	//bi-directional many-to-one association to TokensAdministrador
	@OneToMany(mappedBy="administradore")
	private List<TokensAdministrador> tokensAdministradors;

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

	public List<TokensAdministrador> getTokensAdministradors() {
		return this.tokensAdministradors;
	}

	public void setTokensAdministradors(List<TokensAdministrador> tokensAdministradors) {
		this.tokensAdministradors = tokensAdministradors;
	}

	public TokensAdministrador addTokensAdministrador(TokensAdministrador tokensAdministrador) {
		getTokensAdministradors().add(tokensAdministrador);
		tokensAdministrador.setAdministradore(this);

		return tokensAdministrador;
	}

	public TokensAdministrador removeTokensAdministrador(TokensAdministrador tokensAdministrador) {
		getTokensAdministradors().remove(tokensAdministrador);
		tokensAdministrador.setAdministradore(null);

		return tokensAdministrador;
	}

}