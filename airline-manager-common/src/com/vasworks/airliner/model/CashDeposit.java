package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CashDeposit implements Serializable {
	
	public static enum DepositStatus {PENDING, CLEARED, CANCELED};
	/**
	 * 
	 */
	private static final long serialVersionUID = -8805278614614603177L;

	private Long id;
	
	private Double amount;
	
	private Date checkDate;
	
	private String referenceNumber;
	
	private String checkSerialNumber;
	
	private Date depositDate = new Date();
	
	private Date approvalDate;
	
	private Date cancellationDate;
	
	private DepositStatus depositStatus = DepositStatus.PENDING;
	
	private Customer customer;

	@GeneratedValue
	@Id
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Temporal(TemporalType.DATE)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getCheckSerialNumber() {
		return checkSerialNumber;
	}

	public void setCheckSerialNumber(String checkSerialNumber) {
		this.checkSerialNumber = checkSerialNumber;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	@Column(nullable = false)
	public DepositStatus getDepositStatus() {
		return depositStatus;
	}

	public void setDepositStatus(DepositStatus depositStatus) {
		this.depositStatus = depositStatus;
	}

	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

}
