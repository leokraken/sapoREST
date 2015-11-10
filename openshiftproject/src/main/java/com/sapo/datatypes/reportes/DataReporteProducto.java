package com.sapo.datatypes.reportes;

public class DataReporteProducto {
	private Long id;
	private Long cantidad_tiendas;
	private String nombre;
	private String descripcion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCantidad_tiendas() {
		return cantidad_tiendas;
	}
	public void setCantidad_tiendas(Long cantidad_tiendas) {
		this.cantidad_tiendas = cantidad_tiendas;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
