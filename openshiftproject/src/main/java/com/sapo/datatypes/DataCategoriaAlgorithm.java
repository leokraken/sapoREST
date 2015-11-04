package com.sapo.datatypes;

import java.util.List;

public class DataCategoriaAlgorithm {
	private Long categoriaid;
	private String nombre;
	private String descripcion;
	private List<DataProducto> productos;
	
	public Long getCategoriaid() {
		return categoriaid;
	}
	public void setCategoriaid(Long categoriaid) {
		this.categoriaid = categoriaid;
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
	public List<DataProducto> getProductos() {
		return productos;
	}
	public void setProductos(List<DataProducto> productos) {
		this.productos = productos;
	}	
}
