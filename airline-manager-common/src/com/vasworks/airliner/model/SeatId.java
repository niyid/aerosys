package com.vasworks.airliner.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class SeatId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2731296857997460192L;
	
	private String regNumber;

	private Integer rowNumber;
	
	private String columnId;
	
	public SeatId() {
		super();
	}

	public SeatId(Integer rowNumber, String columnId, String regNumber) {
		super();
		this.regNumber = regNumber;
		this.rowNumber = rowNumber;
		this.columnId = columnId;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnId == null) ? 0 : columnId.hashCode());
		result = prime * result
				+ ((regNumber == null) ? 0 : regNumber.hashCode());
		result = prime * result
				+ ((rowNumber == null) ? 0 : rowNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SeatId other = (SeatId) obj;
		if (columnId == null) {
			if (other.columnId != null)
				return false;
		} else if (!columnId.equals(other.columnId))
			return false;
		if (regNumber == null) {
			if (other.regNumber != null)
				return false;
		} else if (!regNumber.equals(other.regNumber))
			return false;
		if (rowNumber == null) {
			if (other.rowNumber != null)
				return false;
		} else if (!rowNumber.equals(other.rowNumber))
			return false;
		return true;
	}
}
