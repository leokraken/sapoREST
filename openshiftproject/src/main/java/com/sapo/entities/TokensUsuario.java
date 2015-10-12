package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tokens_usuario database table.
 * 
 */
@Entity
@Table(name="tokens_usuario")
@NamedQuery(name="TokensUsuario.findAll", query="SELECT t FROM TokensUsuario t")
public class TokensUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TokensUsuarioPK id;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@MapsId("usuarioid")
	@JoinColumn(name="usuarioid")
	private Usuario usuario;

	public TokensUsuario() {
	}

	public TokensUsuarioPK getId() {
		return this.id;
	}

	public void setId(TokensUsuarioPK id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}