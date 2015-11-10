package com.sapo.datatypes;

import java.util.List;

public class DataUnificar {
private String nombre;
private String descripcion;
private Long categoria;
private List<Long> productos;
private List<String> tags;


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
public List<Long> getProductos() {
	return productos;
}
public void setProductos(List<Long> productos) {
	this.productos = productos;
}
public List<String> getTags() {
	return tags;
}
public void setTags(List<String> tags) {
	this.tags = tags;
}
public Long getCategoria() {
	return categoria;
}
public void setCategoria(Long categoria) {
	this.categoria = categoria;
}

}
