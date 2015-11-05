package com.sapo.datatypes;

public class DataComentario {
	private Long comentarioid;
	private String usuario;
	private String comentario;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public Long getComentarioid() {
		return comentarioid;
	}
	public void setComentarioid(Long comentarioid) {
		this.comentarioid = comentarioid;
	}
		
}
