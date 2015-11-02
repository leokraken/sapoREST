package com.sapo.datatypes;

public class DataNotificacion {

	private Long id;
	private String mensaje;
	private Integer tipo_notificacion;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public Integer getTipo_notificacion() {
		return tipo_notificacion;
	}
	public void setTipo_notificacion(Integer tipo_notificacion) {
		this.tipo_notificacion = tipo_notificacion;
	}
		
}
