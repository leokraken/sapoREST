package com.sapo.datatypes;

import java.util.List;

public class DataTemplate {
	private int ID;
	private String descripcion;
	private String nombre;
	private List<DataCategoria> categorias;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
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
