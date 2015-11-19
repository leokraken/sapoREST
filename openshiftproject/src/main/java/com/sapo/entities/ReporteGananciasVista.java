package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the reporte_ganancias_vista database table.
 * 
 */
@Entity
@Table(name="reporte_ganancias_vista")
@NamedQuery(name="ReporteGananciasVista.findAll", query="SELECT r FROM ReporteGananciasVista r")
public class ReporteGananciasVista implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Temporal(TemporalType.DATE)
	private Date fecha;

	private BigDecimal sum;

	public ReporteGananciasVista() {
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getSum() {
		return this.sum;
	}

	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}

}