package com.sapo.datatypes;

import java.sql.Timestamp;

public class DataNotificacionCuentas {

	private String usuario;
	private int tipo_cuenta;
	private String alias_cuenta;
	private Timestamp expira;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getTipo_cuenta() {
		return tipo_cuenta;
	}
	public void setTipo_cuenta(int tipo_cuenta) {
		this.tipo_cuenta = tipo_cuenta;
	}
	public String getAlias_cuenta() {
		return alias_cuenta;
	}
	public void setAlias_cuenta(String alias_cuenta) {
		this.alias_cuenta = alias_cuenta;
	}
	public Timestamp getExpira() {
		return expira;
	}
	public void setExpira(Timestamp expira) {
		this.expira = expira;
	}
	
}
