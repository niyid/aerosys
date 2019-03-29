package com.vasworks.airliner.struts.operator;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.vasworks.airliner.model.TscRemittance;
import com.vasworks.airliner.model.TscRemittance.RemittanceStatus;
import com.vasworks.airliner.struts.OperatorAction;
import com.vasworks.security.Authorize;

public class RemittanceAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Long remittanceId;
	
	private TscRemittance entity;
	
	private List<TscRemittance> remittances;

	private Date startDate;

	private Date endDate;
	
	private RemittanceStatus remittanceStatus;
	
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

	@Override
	public String execute() {
		session.put("remittance_id", null);
		return super.execute();
	}

	@SkipValidation
	public String select() {
		LOG.info("remittanceId=" + remittanceId);
		if(remittanceId != null) {
			session.put("remittance_id", remittanceId);
		}
		return SUCCESS;
	}
	
	public String list() {
		if (Authorize.hasAny("ROLE_OPERATOR,ROLE_ADMIN")) {
			remittances = operatorService.listRemittances(startDate, endDate, remittanceStatus, exportData);
		}

		return SUCCESS;
	}

	public Long getRemittanceId() {
		return remittanceId;
	}

	public void setRemittanceId(Long remittanceId) {
		this.remittanceId = remittanceId;
	}

	public TscRemittance getEntity() {
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

	public RemittanceStatus getRemittanceStatus() {
		return remittanceStatus;
	}

	public void setRemittanceStatus(RemittanceStatus remittanceStatus) {
		this.remittanceStatus = remittanceStatus;
	}

	public boolean isExportData() {
		return exportData;
	}

	public void setExportData(boolean exportData) {
		this.exportData = exportData;
	}

	public List<TscRemittance> getRemittances() {
		return remittances;
	}
	
	public RemittanceStatus[] getRemittanceStatusLov() {
		return RemittanceStatus.values();
	}
}
