package com.vasworks.airliner.struts.operator;

import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Passenger;
import com.vasworks.airliner.struts.OperatorAction;

public class PassengerAction extends OperatorAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3044965041941202832L;
	
	private Long passengerId;
	
	private Long bookingId;
	
	private boolean coPassenger;
	
	private Passenger entity;
	
	private List<Passenger> passengers;
	
	@Override
	public void prepare() {
	}

	@Override
	public String execute() {
		session.put("passenger_id", null);
		return super.execute();
	}
	
	public String load() {
		if(passengerId != null) {
			if(entity == null) {
				entity = (Passenger) operatorService.find(passengerId, Passenger.class);
			}
		}
		return SUCCESS;
	}
	
	@Validations(
			requiredStrings = {
				@RequiredStringValidator(fieldName = "entity.description", message = "'Description' is required.")
			}
		)
	public String save() {
		operatorService.addPassenger(bookingId, entity, getUser().getId());
		
		entity = new Passenger();
		passengerId = null;
		
		session.put("passenger_id", null);
		
		addActionMessage("Passenger successfully saved.");
		
		return SUCCESS;
	}
	
	@Override
	public void validate() {
	}

	public String initNew() {
		entity = new Passenger();
		
		session.put("passenger_id", null);
		
		return SUCCESS;
	}
	
	public String select() {
		if(passengerId != null) {
			entity = (Passenger) operatorService.find(passengerId, Passenger.class);
			
			session.put("passenger_id", passengerId);
		}
		return SUCCESS;
	}
	
	public String remove() {
		if(passengerId != null) {
			operatorService.remove(passengerId, Passenger.class);
		}
		
		return list();
	}
	
	public String list() {
		passengers = operatorService.listPassengers(bookingId);
		
		return SUCCESS;
	}

	public Long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public boolean isCoPassenger() {
		return coPassenger;
	}

	public void setCoPassenger(boolean coPassenger) {
		this.coPassenger = coPassenger;
	}

	public Passenger getEntity() {
		return entity;
	}

	public void setEntity(Passenger entity) {
		this.entity = entity;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}
	
}
