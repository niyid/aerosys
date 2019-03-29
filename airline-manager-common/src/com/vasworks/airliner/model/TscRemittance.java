package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vasworks.entity.Currency;

@Entity
public class TscRemittance implements Serializable {
	
	public static enum RemittanceStatus {PENDING, SENT};

	/**
	 * 
	 */
	private static final long serialVersionUID = -4250298807626963255L;

	private Long id;
	
	private Ticket ticket;
	
	private RemittanceStatus remittanceStatus;
	
	private Date ticketDate;

	private Date remittanceDate;
	
	private Double remitAmount;
	
	private Currency remitCurrency;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OneToOne
	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public RemittanceStatus getRemittanceStatus() {
		return remittanceStatus;
	}

	public void setRemittanceStatus(RemittanceStatus remittanceStatus) {
		this.remittanceStatus = remittanceStatus;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getTicketDate() {
		return ticketDate;
	}

	public void setTicketDate(Date ticketDate) {
		this.ticketDate = ticketDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getRemittanceDate() {
		return remittanceDate;
	}

	public void setRemittanceDate(Date remittanceDate) {
		this.remittanceDate = remittanceDate;
	}

	public Double getRemitAmount() {
		return remitAmount;
	}

	public void setRemitAmount(Double remitAmount) {
		this.remitAmount = remitAmount;
	}

	@ManyToOne
	public Currency getRemitCurrency() {
		return remitCurrency;
	}

	public void setRemitCurrency(Currency remitCurrency) {
		this.remitCurrency = remitCurrency;
	}
	
}
