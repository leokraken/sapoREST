package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tokens_administrador database table.
 * 
 */
@Embeddable
public class TokensAdministradorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String usuarioid;

	private String token;

	public TokensAdministradorPK() {
	}
	public String getUsuarioid() {
		return this.usuarioid;
	}
	public void setUsuarioid(String usuarioid) {
		this.usuarioid = usuarioid;
	}
	public String getToken() {
		return this.token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TokensAdministradorPK)) {
			return false;
		}
		TokensAdministradorPK castOther = (TokensAdministradorPK)other;
		return 
			this.usuarioid.equals(castOther.usuarioid)
			&& this.token.equals(castOther.token);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.usuarioid.hashCode();
		hash = hash * prime + this.token.hashCode();
		
		return hash;
	}
}