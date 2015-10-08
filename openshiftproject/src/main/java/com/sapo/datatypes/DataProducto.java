package com.sapo.datatypes;

public class DataProducto {

	private Long ID;
	private String nombre;
	private String descripcion;
	private Long categoria;
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
	public Long getCategoria() {
		return categoria;
	}
	public void setCategoria(Long categoria) {
		this.categoria = categoria;
	}

	public Long getID() {
		return ID;
	}

	public void setID(Long iD) {
		ID = iD;
	}
	
	
}
