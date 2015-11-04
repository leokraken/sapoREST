package com.sapo.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="reportes_movimiento_stock")
@NamedQuery(name="ReportesMovimientoStock.findAll", query="SELECT r FROM ReportesMovimientoStock r")
public class ReportesMovimientoStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String almacenid;

	private Timestamp fecha= new Timestamp(new Date().getTime());

	private Long productoid;

	private Integer stock;

	private Integer dif;
	
	public ReportesMovimientoStock() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAlmacenid() {
		return this.almacenid;
	}

	public void setAlmacenid(String almacenid) {
		this.almacenid = almacenid;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public Long getProductoid() {
		return this.productoid;
	}

	public void setProductoid(Long productoid) {
		this.productoid = productoid;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getDif() {
		return dif;
	}

	public void setDif(Integer dif) {
		this.dif = dif;
	}

}