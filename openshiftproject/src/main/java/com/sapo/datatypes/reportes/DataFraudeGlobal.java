package com.sapo.datatypes.reportes;

import java.math.BigDecimal;
import java.util.List;

public class DataFraudeGlobal {
	List<DataFraude> lista;
	BigDecimal mean;
	
	
	public List<DataFraude> getLista() {
		return lista;
	}
	public void setLista(List<DataFraude> lista) {
		this.lista = lista;
	}
	public BigDecimal getMean() {
		return mean;
	}
	public void setMean(BigDecimal mean) {
		this.mean = mean;
	}	
	
}
