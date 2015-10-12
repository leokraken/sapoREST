package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tokens_administrador database table.
 * 
 */
@Entity
@Table(name="tokens_administrador")
@NamedQuery(name="TokensAdministrador.findAll", query="SELECT t FROM TokensAdministrador t")
public class TokensAdministrador implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TokensAdministradorPK id;

	@ManyToOne
	@MapsId("usuarioid")
	@JoinColumn(name="usuarioid")
	private Administrador administradore;

	public TokensAdministrador() {
	}

	public TokensAdministradorPK getId() {
		return this.id;
	}

	public void setId(TokensAdministradorPK id) {
		this.id = id;
	}

	public Administrador getAdministradore() {
		return this.administradore;
	}

	public void setAdministradore(Administrador administradore) {
		this.administradore = administradore;
	}

}