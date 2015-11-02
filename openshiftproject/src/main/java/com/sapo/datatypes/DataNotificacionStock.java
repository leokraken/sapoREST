package com.sapo.datatypes;

public class DataNotificacionStock {

	private Long productoID;
	private int minimo;
	private Boolean notifica;
	private String mensaje;
	
	public Long getProductoID() {
		return productoID;
	}
	public void setProductoID(Long productoID) {
		this.productoID = productoID;
	}
	public int getMinimo() {
		return minimo;
	}
	public void setMinimo(int minimo) {
		this.minimo = minimo;
	}
	public Boolean getNotifica() {
		return notifica;
	}
	public void setNotifica(Boolean notifica) {
		this.notifica = notifica;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
}
