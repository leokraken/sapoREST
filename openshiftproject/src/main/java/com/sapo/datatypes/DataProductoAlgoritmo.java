package com.sapo.datatypes;

import java.util.List;

public class DataProductoAlgoritmo {
	private Long ID;
	private String nombre;
	private String descripcion;
	private DataCategoria categoria;
	private Boolean isgenerico;
	private List<String> tags;
	private List<String> imagenes;
	
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
	public DataCategoria getCategoria() {
		return categoria;
	}
	public void setCategoria(DataCategoria categoria) {
		this.categoria = categoria;
	}
	public Boolean getIsgenerico() {
		return isgenerico;
	}
	public void setIsgenerico(Boolean isgenerico) {
		this.isgenerico = isgenerico;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<String> getImagenes() {
		return imagenes;
	}
	public void setImagenes(List<String> imagenes) {
		this.imagenes = imagenes;
	}
	
	
}
