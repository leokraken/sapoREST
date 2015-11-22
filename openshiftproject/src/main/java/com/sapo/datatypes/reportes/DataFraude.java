package com.sapo.datatypes.reportes;

import java.math.BigDecimal;
import java.util.Comparator;

public class DataFraude implements Comparable<DataFraude> {

	private Integer x;
	private Long low;
	private BigDecimal median;
	private BigDecimal q1;
	private BigDecimal q3;
	private Long high;
	private String almacen;
	
	public String getAlmacen() {
		return almacen;
	}
	public void setAlmacen(String almacen) {
		this.almacen = almacen;
	}
	public Long getLow() {
		return low;
	}
	public void setLow(Long low) {
		this.low = low;
	}
	public BigDecimal getMedian() {
		return median;
	}
	public void setMedian(BigDecimal median) {
		this.median = median;
	}
	public BigDecimal getQ1() {
		return q1;
	}
	public void setQ1(BigDecimal q1) {
		this.q1 = q1;
	}
	public BigDecimal getQ3() {
		return q3;
	}
	public void setQ3(BigDecimal q3) {
		this.q3 = q3;
	}
	public Long getHigh() {
		return high;
	}
	public void setHigh(Long high) {
		this.high = high;
	}
	public Integer getX() {
		return x;
	}
	public void setX(Integer x) {
		this.x = x;
	}
	@Override
	public int compareTo(DataFraude o) {
		Double o1 = this.getMedian().doubleValue();
		return o1.compareTo(o.getMedian().doubleValue());
	}
	
	public static Comparator<DataFraude> comparador = new Comparator<DataFraude>() {
	
	@Override
	public int compare(DataFraude o1, DataFraude o2) {
		return o2.compareTo(o1);
	}
	};
	
	
}
