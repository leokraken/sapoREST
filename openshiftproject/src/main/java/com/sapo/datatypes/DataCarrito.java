package com.sapo.datatypes;

public class DataCarrito {
	private Integer cantidad_compras;
	private Integer total;
	private DataProducto producto;

	public Integer getCantidad_compras() {
		return cantidad_compras;
	}
	public void setCantidad_compras(Integer cantidad_compras) {
		this.cantidad_compras = cantidad_compras;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public DataProducto getProducto() {
		return producto;
	}
	public void setProducto(DataProducto producto) {
		this.producto = producto;
	}

	

	
}
