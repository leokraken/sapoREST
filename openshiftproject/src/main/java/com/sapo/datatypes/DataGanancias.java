package com.sapo.datatypes;

import java.math.BigDecimal;
import java.util.List;

public class DataGanancias {

	private List<String> fechas;
	private List<BigDecimal> monto;
	
	public List<String> getFechas() {
		return fechas;
	}
	public void setFechas(List<String> fechas) {
		this.fechas = fechas;
	}
	public List<BigDecimal> getMonto() {
		return monto;
	}
	public void setMonto(List<BigDecimal> monto) {
		this.monto = monto;
	}
	
}
