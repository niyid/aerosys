package com.vasworks.airliner.struts.operator;

import java.util.Date;
import java.util.List;

import com.vasworks.airliner.model.Airplane;
import com.vasworks.airliner.model.Flight;
import com.vasworks.airliner.model.FlightId;
import com.vasworks.airliner.model.FlightSeat;
import com.vasworks.airliner.model.Seat;
import com.vasworks.airliner.model.SeatId;
import com.vasworks.airliner.model.SeatInterface;
import com.vasworks.airliner.struts.OperatorAction;

public class SeatSelectionAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private int rowSize;
	
	private String crossSection;
	
	private List<FlightSeat> seats;
	
	private Seat currentSeat;
	
	private Integer flightNumber;
	
	private Date flightDate;

	private String regNumber;
	
	private Long seatId;

	private Flight entity;
	
	private FlightId flightId;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		session.put("seat_id", null);
		return super.execute();
	}
	
	public String select() {
		LOG.info("SeatSelectionAction.list - seatId=" + seatId);
		if(seatId != null) {
			FlightSeat seat = (FlightSeat) operatorService.find(seatId, FlightSeat.class);
			
			session.put("seat_id", seatId);
		}
		return list();
	}
	
	public String list() {
		LOG.info("SeatSelectionAction.list - flightNumber=" + flightNumber + ",flightDate=" + flightDate + ",flightId" + flightId);
		if(flightId != null) {
			entity = (Flight) operatorService.find(flightId, Flight.class);

			Airplane airplane = entity.getAirplane();
			
			rowSize = airplane.getModel().getNumberOfRows();
			crossSection = airplane.getModel().getCrossSection();
			
			seats = entity.getFlightSeats();
		} else {
			if(flightNumber != null && flightDate != null) {			
				entity = (Flight) operatorService.find(new FlightId(flightNumber, getAirlineCode(), flightDate), Flight.class);

				Airplane airplane = entity.getAirplane();
				
				rowSize = airplane.getModel().getNumberOfRows();
				crossSection = airplane.getModel().getCrossSection();
				
				seats = entity.getFlightSeats();
			}
		}
		
		return SUCCESS;
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

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public List<FlightSeat> getSeats() {
		return seats;
	}
	
	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public FlightId getFlightId() {
		return flightId;
	}

	public void setFlightId(FlightId flightId) {
		this.flightId = flightId;
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

	public Flight getEntity() {
		return entity;
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
