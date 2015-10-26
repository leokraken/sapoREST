package com.sapo.datatypes;

public class DataCuenta {
	private int cuentaID;
	private String nombre;
	private String descripcion;
	private double precio;
	
	public int getCuentaID() {
		return cuentaID;
	}
	public void setCuentaID(int cuentaID) {
		this.cuentaID = cuentaID;
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
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	
}
