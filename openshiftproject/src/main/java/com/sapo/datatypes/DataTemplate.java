package com.sapo.datatypes;

import java.util.List;

public class DataTemplate {
	private Long ID;
	private String descripcion;
	private String nombre;
	private List<DataCategoria> categorias;
	
	public Long getID() {
		return ID;
	}
	public void setID(Long iD) {
		ID = iD;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<DataCategoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<DataCategoria> categorias) {
		this.categorias = categorias;
	}
	
	
}
