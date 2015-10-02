package com.sapo.datatypes;

public class DataProducto {

	private String nombre;
	private String descripcion;
	private long categoria;
	private Boolean isgenerico;

	public Boolean getIsgenerico() {
		return isgenerico;
	}

	public void setIsgenerico(Boolean isgenerico) {
		this.isgenerico = isgenerico;
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
