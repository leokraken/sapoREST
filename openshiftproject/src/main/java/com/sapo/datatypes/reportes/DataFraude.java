package com.sapo.datatypes.reportes;

import java.math.BigDecimal;

public class DataFraude {

	private String x;
	private Long low;
	private BigDecimal median;
	private BigDecimal q1;
	private BigDecimal q3;
	private Long high;
	
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
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	
}
