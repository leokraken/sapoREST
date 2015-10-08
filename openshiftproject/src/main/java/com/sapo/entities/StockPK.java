package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the stock database table.
 * 
 */
@Embeddable
public class StockPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_av", insertable=false, updatable=false)
	private Long idAv;

	@Column(name="id_producto", insertable=false, updatable=false)
	private Long idProducto;

	public StockPK() {
	}
	public Long getIdAv() {
		return this.idAv;
	}
	public void setIdAv(Long idAv) {
		this.idAv = idAv;
	}
	public Long getIdProducto() {
		return this.idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StockPK)) {
			return false;
		}
		StockPK castOther = (StockPK)other;
		return 
			this.idAv.equals(castOther.idAv)
			&& this.idProducto.equals(castOther.idProducto);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idAv.hashCode();
		hash = hash * prime + this.idProducto.hashCode();
		
		return hash;
	}
}