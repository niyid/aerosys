package com.vasworks.airliner.struts.setup;

import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Airplane;
import com.vasworks.airliner.model.Seat;
import com.vasworks.airliner.model.SeatId;
import com.vasworks.airliner.model.SeatInterface;
import com.vasworks.airliner.struts.OperatorAction;

public class SeatAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private String regNumber;

	private Integer rowNumber;
	
	private String columnId;
	
	private Seat entity;
	
	private int rowSize;
	
	private String crossSection;
	
	private List<Seat> seats;
	
	private Seat currentSeat;
	
	private Integer flightNumber;
	
	private Date flightDate;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		session.put("seat_id", null);
		return super.execute();
	}
	
	public String load() {
		if(rowNumber != null && columnId != null) {
			if(entity == null) {
				entity = (Seat) operatorService.find(new SeatId(rowNumber, columnId, regNumber), Seat.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "regNumber", message = "'Airplane Registration' is required."),
				@RequiredStringValidator(fieldName = "columnId", message = "'Seat Column' is required.")
			},
			requiredFields = {
				@RequiredFieldValidator(fieldName = "rowNumber", message = "'Seat Row' is required."),
				@RequiredFieldValidator(fieldName = "entity.seatStatus", message = "'Status' is required."),
				@RequiredFieldValidator(fieldName = "entity.seatClass", message = "'Class' is required."),
				@RequiredFieldValidator(fieldName = "entity.seatLocation", message = "'Location' is required.")
			}
		)
	public String save() {
		operatorService.saveSeat(entity, (rowNumber != null && columnId != null ? new SeatId(rowNumber, columnId, regNumber) : null), getUserId());
		
		entity = new Seat();
		rowNumber = null;
		columnId = null;
		
		session.put("seat_id", null);
		
		addActionMessage("Seat successfully saved.");
		
		return SUCCESS;
	}
	
	public String initNew() {
		entity = new Seat();
		
		session.put("seat_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(rowNumber != null && columnId != null) {
			entity = (Seat) operatorService.find(new SeatId(rowNumber, columnId, regNumber), Seat.class);
			
			session.put("seat_id", new SeatId(rowNumber, columnId, regNumber));
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(rowNumber != null && columnId != null) {
			operatorService.remove(new SeatId(rowNumber, columnId, regNumber), Seat.class);
		}
		
		return list();
	}
	
	public String list() {
		LOG.info("SeatAction.list - flightNumber=" + flightNumber + ",flightDate=" + flightDate);
		if(regNumber != null && !regNumber.isEmpty()) {			
			Airplane airplane = (Airplane) operatorService.find(regNumber, Airplane.class);
			
			rowSize = airplane.getModel().getNumberOfRows();
			crossSection = airplane.getModel().getCrossSection();
			
			seats = operatorService.listSeats(airplane.getRegNumber());
		}
		
		return SUCCESS;
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

	public Seat getEntity() {
		return entity;
	}

	public void setEntity(Seat entity) {
		this.entity = entity;
	}

	public Integer getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(Integer flightNumber) {
		this.flightNumber = flightNumber;
	}

	public Date getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public List<Seat> getSeats() {
		return seats;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	
	public int getRowSize() {
		return rowSize;
	}
	
	public String getCrossSection() {
		return crossSection;
	}
	
	public Seat findSeat(Integer rowNumber, String columnId, String regNumber) {
		return currentSeat = (rowNumber != null && columnId != null && regNumber != null && !"|".equals(columnId) ? (Seat) operatorService.find(new SeatId(rowNumber, columnId, regNumber), Seat.class) : null);
	}
	
	public Seat getCurrentSeat() {
		return currentSeat;
	}

	public List<Airplane> getAirplaneLov() {
		return operatorService.listAirplanes(getAirlineId());
	}
	
	public SeatInterface.SeatStatus[] getSeatStatusLov() {
		return SeatInterface.SeatStatus.values();
	}	
	
	public SeatInterface.SeatClass[] getSeatClassLov() {
		return SeatInterface.SeatClass.values();
	}
}
