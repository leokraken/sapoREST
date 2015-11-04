package com.sapo.datatypes.reportes;

import java.sql.Timestamp;

public class DataReporteStock {
	private String producto_nombre;
	private Long producto_id;
	private String almacen_id;
	private String almacen_nombre;
	private Integer diferencia;
	private Timestamp fecha;
	private Integer stock;
	
	public String getProducto_nombre() {
		return producto_nombre;
	}
	public void setProducto_nombre(String producto_nombre) {
		this.producto_nombre = producto_nombre;
	}
	public Long getProducto_id() {
		return producto_id;
	}
	public void setProducto_id(Long producto_id) {
		this.producto_id = producto_id;
	}
	public Integer getDiferencia() {
		return diferencia;
	}
	public void setDiferencia(Integer diferencia) {
		this.diferencia = diferencia;
	}
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public String getAlmacen_id() {
		return almacen_id;
	}
	public void setAlmacen_id(String almacen_id) {
		this.almacen_id = almacen_id;
	}
	public String getAlmacen_nombre() {
		return almacen_nombre;
	}
	public void setAlmacen_nombre(String almacen_nombre) {
		this.almacen_nombre = almacen_nombre;
	}
	
}
