package com.vasworks.airliner.struts.operator;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.vasworks.airliner.model.ActivityReport;
import com.vasworks.airliner.model.Customer;
import com.vasworks.airliner.model.Invoice;
import com.vasworks.airliner.model.Invoice.InvoiceStatus;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.security.Authorize;

public class InvoiceAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Long invoiceId;

	private Long clientId;
	
	private Invoice entity;
	
	private List<Object[]> records;
	
	private Date startDate;

	private Date endDate;
	
	private InvoiceStatus invoiceStatus;
	
	private boolean exportData;
	
	private InputStream inputStream;
	
	private String contentType;
	
	private String contentDisposition;
	
	private String[] headers;
	
	@Override
	public void prepare() {
		if(startDate == null) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, -14);
			startDate = cal.getTime();
		}
		if(endDate == null) {
			endDate = new Date();
		}
	}

	@Override
	public String execute() {
		session.put("invoice_id", null);
		return super.execute();
	}

	@SkipValidation
	public String select() {
		LOG.info("invoiceId=" + invoiceId);
		if(invoiceId != null) {
			session.put("invoice_id", invoiceId);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(invoiceId != null) {
			operatorService.remove(invoiceId, Invoice.class);
		}
		
		return list();
	}
	
	public String list() {
		if (hasAny("PRE_ETL_POSTPAID")) {
			records = operatorService.listInvoices((Long) session.get("client_id"), startDate, endDate, invoiceStatus, exportData);
		} else
		if (Authorize.hasAny("ROLE_FINANCE,ROLE_OPERATOR,ROLE_ADMIN")) {
			records = operatorService.listInvoices(clientId, startDate, endDate, invoiceStatus, exportData);
		}

		return SUCCESS;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Invoice getEntity() {
		return entity;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public InvoiceStatus getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public boolean isExportData() {
		return exportData;
	}

	public void setExportData(boolean exportData) {
		this.exportData = exportData;
	}
	
	public InvoiceStatus[] getInvoiceStatusLov() {
		return InvoiceStatus.values();
	}
	
	public List<Customer> getClientLov() {
		return operatorService.listCustomers((Long) session.get("airline_id"));
	}

	public List<Object[]> getRecords() {
		return records;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentType() {
		return contentType;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public String[] getHeaders() {
		return headers;
	}

	public void setHeaders(String[] headers) {
		this.headers = headers;
	}
}
