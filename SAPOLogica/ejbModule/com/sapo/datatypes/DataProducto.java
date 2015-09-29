package com.sapo.datatypes;

import com.sapo.entities.Producto;

public class DataProducto {

	private String nombre;
	private String descripcion;
	private long categoria;

	
	public DataProducto(Producto prod) {
		this.nombre= prod.getNombre();
		this.descripcion = prod.getDescripcion();
		this.categoria = prod.getCategoria().getId();
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
	public long getCategoria() {
		return categoria;
	}
	public void setCategoria(long categoria) {
		this.categoria = categoria;
	}
	
}
