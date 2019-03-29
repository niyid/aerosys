package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Invoice implements Serializable {
	
	public static enum InvoiceStatus {PENDING, PAID, CANCELED};

	/**
	 * 
	 */
	private static final long serialVersionUID = 6064351262003898648L;
	
	private Long id;
	
	private Date invoiceDate;
	
	private Date overdueDate;
	
	private Date paidDate;
	
	private Date cancellationDate;
	
	private Double totalAmount;
	
	private InvoiceStatus invoiceStatus = InvoiceStatus.PENDING;
	
	private Customer customer;
	
	private List<Ticket> tickets;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getOverdueDate() {
		return overdueDate;
	}

	public void setOverdueDate(Date overdueDate) {
		this.overdueDate = overdueDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCancellationDate() {
		return cancellationDate;
	}

	public void setCancellationDate(Date cancellationDate) {
		this.cancellationDate = cancellationDate;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	
	@ManyToOne
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@OneToMany(mappedBy="invoice")
	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}
}
