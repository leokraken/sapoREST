package com.sapo.datatypes;

public class DataCarritoReq {
	private Integer cantidad_compras;
	private Integer total;
	private Long producto;

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
	public Long getProducto() {
		return producto;
	}
	public void setProducto(Long producto) {
		this.producto = producto;
	}

}
