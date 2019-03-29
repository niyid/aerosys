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
import javax.persistence.Transient;

import com.vasworks.airliner.model.SeatInterface.SeatClass;
import com.vasworks.entity.Address;
import com.vasworks.security.model.AppUser;

@Entity
public class Passenger implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7980165650710655630L;
	
	public static enum MinorPassenger {INFANT, CHILD};
	
	private Long id;
	
	private MinorPassenger minorPassenger;
	
	private boolean minor;
	
	private boolean impaired;
	
	private String passportNumber;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private Date dob;
	
	private String phoneNumber1;
	
	private String phoneNumber2;
	
	private String email1;
	
	private String email2;
	
	private UserTitle titlePrefix;
	
	private Gender gender;
	
	private AppUser user;
	
	private Address homeAddress;
	
	private Date registrationDate;
	
	private boolean hasCarryOn;
	
	private double passengerWeight;
	
	private double carryOnWeight = 6;
	
	private double totalWeight;
	
	private int luggageCount = 0;
	
	private double luggageWeight = 23;
	
	private List<Rebate> rebates;
	
	private SeatClass ticketClass = SeatClass.ECONOMY;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isMinor() {
		return minor;
	}

	public void setMinor(boolean minor) {
		this.minor = minor;
	}

	public MinorPassenger getMinorPassenger() {
		return minorPassenger;
	}

	public void setMinorPassenger(MinorPassenger minorPassenger) {
		this.minorPassenger = minorPassenger;
	}

	public boolean isImpaired() {
		return impaired;
	}

	public void setImpaired(boolean impaired) {
		this.impaired = impaired;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public UserTitle getTitlePrefix() {
		return titlePrefix;
	}

	public void setTitlePrefix(UserTitle titlePrefix) {
		this.titlePrefix = titlePrefix;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@ManyToOne
	public AppUser getUser() {
		return user;
	}

	public void setUser(AppUser user) {
		this.user = user;
	}

	@ManyToOne
	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	@Temporal(TemporalType.DATE)
	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public boolean isHasCarryOn() {
		return hasCarryOn;
	}

	public void setHasCarryOn(boolean hasCarryOn) {
		this.hasCarryOn = hasCarryOn;
	}

	public double getPassengerWeight() {
		return passengerWeight;
	}

	public void setPassengerWeight(double passengerWeight) {
		this.passengerWeight = passengerWeight;
	}

	public double getCarryOnWeight() {
		return carryOnWeight;
	}

	public void setCarryOnWeight(double carryOnWeight) {
		this.carryOnWeight = carryOnWeight;
	}

	public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	public int getLuggageCount() {
		return luggageCount;
	}

	public void setLuggageCount(int luggageCount) {
		this.luggageCount = luggageCount;
	}

	public double getLuggageWeight() {
		return luggageWeight;
	}

	public void setLuggageWeight(double luggageWeight) {
		this.luggageWeight = luggageWeight;
	}

	public SeatClass getTicketClass() {
		return ticketClass;
	}

	public void setTicketClass(SeatClass ticketClass) {
		this.ticketClass = ticketClass;
	}
	@OneToMany
	public List<Rebate> getRebates() {
		return rebates;
	}

	public void setRebates(List<Rebate> rebates) {
		this.rebates = rebates;
	}
	
	@Transient
	public String getFullName() {
		StringBuilder sb = new StringBuilder();
		if(getTitlePrefix() != null) {
			sb.append(getTitlePrefix().getDescription());
			sb.append(" ");
		}
		if(getFirstName() != null && !getFirstName().isEmpty()) {
			sb.append(getFirstName());
			sb.append(" ");
		}
		if(getMiddleName() != null && !getMiddleName().isEmpty()) {
			sb.append(getMiddleName());
			sb.append(" ");
		}
		if(getLastName() != null && !getLastName().isEmpty()) {
			sb.append(getLastName());
		}
		return sb.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Passenger [passportNumber=");
		builder.append(passportNumber);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", middleName=");
		builder.append(middleName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", gender=");
		builder.append(gender);
		builder.append("]");
		return builder.toString();
	}
}
