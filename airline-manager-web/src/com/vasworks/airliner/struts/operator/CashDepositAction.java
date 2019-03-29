package com.vasworks.airliner.struts.operator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.CashDeposit;
import com.vasworks.airliner.model.CashDeposit.DepositStatus;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.security.Authorize;

public class CashDepositAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Long depositId;
	
	private CashDeposit entity;
	
	private List<CashDeposit> deposits;

	private Date startDate;

	private Date endDate;
	
	private DepositStatus depositStatus;
	
	private Long clientId;
	
	private boolean exportData;
	
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
	
	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "entity.referenceNumber", message = "'Reference' is required.")	
		},
		requiredFields = {
			@RequiredFieldValidator(fieldName = "clientId", message = "'Customer' is required."),
			@RequiredFieldValidator(fieldName = "entity.amount", message = "'Amount' is required."),
			@RequiredFieldValidator(fieldName = "entity.depositStatus", message = "'Status' is required."),
			@RequiredFieldValidator(fieldName = "entity.depositDate", message = "'Date' is required.")
		}
	)
	public String save() {
		
		entity = operatorService.saveCashDeposit(entity, clientId, getUserId());

		entity = new CashDeposit();
		clientId = null;
		session.remove("deposit_id");
		
		addActionMessage("Cash deposit successfully saved.");
		
		return SUCCESS;
	}
	
	@Override
	public String execute() {
		session.remove("deposit_id");
		return SUCCESS;
	}

	@SkipValidation
	public String select() {
		LOG.info("depositId=" + depositId);
		if(depositId != null) {
			entity = (CashDeposit) operatorService.find(depositId, CashDeposit.class);
			clientId = entity.getCustomer().getId();
			session.put("deposit_id", depositId);
		}
		return SUCCESS;
	}
	
	public String list() {
		if (hasAny("POST_ETL_POSTPAID")) {
			deposits = operatorService.listDeposits(startDate, endDate, (Long) session.get("client_id"), depositStatus, exportData);
		} else
		if (Authorize.hasAny("ROLE_FINANCE,ROLE_ADMIN")) {
			deposits = operatorService.listDeposits(startDate, endDate, clientId, depositStatus, exportData);
		}

		return SUCCESS;
	}

	@Override
	public void validate() {
		if(entity != null && entity.getAmount() < 200000.0) {
			addFieldError("entity.amount", "Minimum deposit is 200,000.00");
		}
		if(entity != null && StringUtils.isNotEmpty(entity.getCheckSerialNumber()) && entity.getCheckDate() == null) {
			addFieldError("entity.checkDate", "Check date is required with check serial #.");
		}
	}

	public Long getDepositId() {
		return depositId;
	}

	public void setDepositId(Long remittanceId) {
		this.depositId = remittanceId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public CashDeposit getEntity() {
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

	public DepositStatus getDepositStatus() {
		return depositStatus;
	}

	public void setDepositStatus(DepositStatus depositStatus) {
		this.depositStatus = depositStatus;
	}

	public boolean isExportData() {
		return exportData;
	}

	public void setExportData(boolean exportData) {
		this.exportData = exportData;
	}

	public List<CashDeposit> getDeposits() {
		return deposits;
	}
	
	public DepositStatus[] getDepositStatusLov() {
		return DepositStatus.values();
	}
}
