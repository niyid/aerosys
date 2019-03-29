package com.vasworks.airliner.model;

public interface SeatInterface {
	
	static enum SeatLocation {WINDOW, MIDDLE, ISLE};

	static enum SeatStatus {VACANT, RESERVED, TAKEN, UNAVAILABLE};
	
	static enum SeatClass {ECONOMY, BUSINESS, FIRST};
	
	SeatStatus getSeatStatus();

	void setSeatStatus(SeatStatus seatStatus);

	SeatClass getSeatClass();

	void setSeatClass(SeatClass seatClass);

	SeatLocation getSeatLocation();

	void setSeatLocation(SeatLocation seatLocation);
}
