package com.sapo.datatypes;

public class DataCategoria {
	private Long ID;
	private String nombre;
	private String descripcion;
	private Boolean isgenerico;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
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
	public Boolean getIsgenerico() {
		return isgenerico;
	}
	public void setIsgenerico(Boolean isgenerico) {
		this.isgenerico = isgenerico;
	}
	
	
}
