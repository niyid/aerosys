package com.vasworks.airliner.struts.operator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.ActivityReport;
import com.vasworks.airliner.model.Invoice;
import com.vasworks.airliner.struts.OperatorAction;

public class ActivityReportAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Long reportId;

	private Long clientId;
	
	private Long routeId;
	
	private List<Object[]> records;

	private Date startDate;

	private Date endDate;
	
	private boolean exportData;
	
	private List<ActivityReport> activityReports;
	
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
		session.put("actreport_id", null);
		return super.execute();
	}
	
	public String save() {
		addActionMessage("Report successfully saved.");
		
		return SUCCESS;
	}

	@SkipValidation
	public String select() {
		LOG.info("reportId=" + reportId);
		if(reportId != null) {
			session.put("actreport_id", reportId);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(reportId != null) {
			operatorService.remove(reportId, Invoice.class);
		}
		
		return list();
	}
	
	@Validations(
		requiredFields = {
			@RequiredFieldValidator(fieldName = "startDate", message = "'From' is required."),
			@RequiredFieldValidator(fieldName = "endDate", message = "'To' is required."),
			@RequiredFieldValidator(fieldName = "reportId", message = "'Report' is required.")
		}
	)
	public String search() {
		LOG.info("search - " + startDate + " " + endDate + " " + reportId);
		records = operatorService.generateReport(startDate, endDate, reportId, clientId, getUserId());
		
		ActivityReport report = (ActivityReport) operatorService.find(reportId, ActivityReport.class);
		headers = report.getHeaders().split(",");

		return xlsxDl();
	}
	
	@SkipValidation
	public String xlsxDl() {
		if(exportData) {
			try {
				Path path = Paths.get(operatorService.generateReport(reportId, records, getUserId()));
				
				StringBuilder b = new StringBuilder("attachment;filename=");		
				b.append("\"");
				b.append(path.getFileName());
				b.append("\"");
				
				contentDisposition = b.toString();
				contentType = "application/vnd.ms-excel";
				byte[] rawFileData = Files.readAllBytes(path);
				if(rawFileData != null) {
					inputStream = new ByteArrayInputStream(rawFileData);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "download";
		} else {
			return SUCCESS;
		}
	}
	
	public String list() {
		activityReports = operatorService.listReports(clientId, getUserId());

		return SUCCESS;
	}

	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long invoiceId) {
		this.reportId = invoiceId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
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

	public boolean isExportData() {
		return exportData;
	}

	public void setExportData(boolean exportData) {
		this.exportData = exportData;
	}

	public List<Object[]> getRecords() {
		return records;
	}

	public List<ActivityReport> getActivityReports() {
		return activityReports;
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
	
	public List<ActivityReport> getReportLov() {
		return (List<ActivityReport>) operatorService.list(ActivityReport.class);
	}
}
