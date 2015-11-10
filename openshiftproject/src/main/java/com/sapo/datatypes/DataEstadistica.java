package com.sapo.datatypes;

import java.util.List;

import com.sapo.datatypes.reportes.DataReporteProducto;

public class DataEstadistica {
	
	private List<DataReporteProducto> productos;
	private Long categorias_genericas;
	private Long productos_genericos;
	private Long usuarios_registrados;
	private Long usuarios_premium;
	
	
	public Long getCategorias_genericas() {
		return categorias_genericas;
	}
	public void setCategorias_genericas(Long categorias_genericas) {
		this.categorias_genericas = categorias_genericas;
	}
	public Long getProductos_genericos() {
		return productos_genericos;
	}
	public void setProductos_genericos(Long productos_genericos) {
		this.productos_genericos = productos_genericos;
	}
	public List<DataReporteProducto> getProductos() {
		return productos;
	}
	public void setProductos(List<DataReporteProducto> productos) {
		this.productos = productos;
	}
	public Long getUsuarios_registrados() {
		return usuarios_registrados;
	}
	public void setUsuarios_registrados(Long usuarios_registrados) {
		this.usuarios_registrados = usuarios_registrados;
	}

	public Long getUsuarios_premium() {
		return usuarios_premium;
	}
	public void setUsuarios_premium(Long usuarios_premium) {
		this.usuarios_premium = usuarios_premium;
	}

}
