package com.sapo.datatypes;

public class DataStock {
	private Long AvID;
	private Long ProductoID;
	private int cantidad;
	
	
	public Long getAvID() {
		return AvID;
	}
	public void setAvID(Long avID) {
		AvID = avID;
	}
	public Long getProductoID() {
		return ProductoID;
	}
	public void setProductoID(Long productoID) {
		ProductoID = productoID;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	

}
