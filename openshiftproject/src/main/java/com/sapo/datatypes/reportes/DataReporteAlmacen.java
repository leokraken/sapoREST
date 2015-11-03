package com.sapo.datatypes.reportes;

import java.util.List;

public class DataReporteAlmacen {
	private List<String> labels;
	private List<String> series;
	private List<List<Long>> data;
	
	public List<String> getLabels() {
		return labels;
	}
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}
	public List<String> getSeries() {
		return series;
	}
	public void setSeries(List<String> series) {
		this.series = series;
	}
	public List<List<Long>> getData() {
		return data;
	}
	public void setData(List<List<Long>> data) {
		this.data = data;
	}
	
}
