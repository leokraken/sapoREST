package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the producto_usuario_tienda_notificacion database table.
 * 
 */
@Embeddable
public class ProductoUsuarioTiendaNotificacionPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private Long productoid;

	@Column(insertable=false, updatable=false)
	private String usuarioid;

	@Column(insertable=false, updatable=false)
	private String tiendaid;

	public ProductoUsuarioTiendaNotificacionPK() {
	}
	public Long getProductoid() {
		return this.productoid;
	}
	public void setProductoid(Long productoid) {
		this.productoid = productoid;
	}
	public String getUsuarioid() {
		return this.usuarioid;
	}
	public void setUsuarioid(String usuarioid) {
		this.usuarioid = usuarioid;
	}
	public String getTiendaid() {
		return this.tiendaid;
	}
	public void setTiendaid(String tiendaid) {
		this.tiendaid = tiendaid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProductoUsuarioTiendaNotificacionPK)) {
			return false;
		}
		ProductoUsuarioTiendaNotificacionPK castOther = (ProductoUsuarioTiendaNotificacionPK)other;
		return 
			this.productoid.equals(castOther.productoid)
			&& this.usuarioid.equals(castOther.usuarioid)
			&& this.tiendaid.equals(castOther.tiendaid);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.productoid.hashCode();
		hash = hash * prime + this.usuarioid.hashCode();
		hash = hash * prime + this.tiendaid.hashCode();
		
		return hash;
	}
}