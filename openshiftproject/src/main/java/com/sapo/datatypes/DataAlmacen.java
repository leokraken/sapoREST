package com.sapo.datatypes;

import java.util.List;

public class DataAlmacen {

	private Long id;
	private String nombre;
	private String descripcion;
	private String url;
	private String usuario;
	
	List<DataPersona> colaboradores;
	List<DataCategoria> categorias;
	List<DataStock> stockproductos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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

	public List<DataCategoria> getCategorias() {
		return categorias;
	}
	public void setCategorias(List<DataCategoria> categorias) {
		this.categorias = categorias;
	}
	public List<DataStock> getStockproductos() {
		return stockproductos;
	}
	public void setStockproductos(List<DataStock> stockproductos) {
		this.stockproductos = stockproductos;
	}
	public List<DataPersona> getColaboradores() {
		return colaboradores;
	}
	public void setColaboradores(List<DataPersona> colaboradores) {
		this.colaboradores = colaboradores;
	}
	
	
}
