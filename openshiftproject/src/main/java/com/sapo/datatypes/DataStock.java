package com.sapo.datatypes;

public class DataStock {
	private Long AvID;
	private Long ProductoID;
	private String nombre;
	private String descripcion;
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
	
}
