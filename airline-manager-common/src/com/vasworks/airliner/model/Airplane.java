package com.vasworks.airliner.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.vasworks.entity.Address;

@Entity
public class Airplane implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8010742639694164216L;
	
	public static enum OperationType {MCC, FOCC, PNRO};

	private String regNumber;
	
	private String serialNumber;
	
	private String ownerName;
	
	private Address ownerAddress;

	private AirplaneModel model;
	
	private Date manufactureDate;
	
	private Date arrivalDate;
	
	private OperationType operationType;
	
	private Airline airline;
	
	private DocumentFile cofaDoc;
	
	private DocumentFile cofmDoc;
	
	private DocumentFile insuranceDoc;
	
	private DocumentFile radioLicenseDoc;
	
	private LicensingAuthority licensingAuthority;
	
	private AircraftMaintenanceOrg maintenanceOrg;
	
	private List<Seat> seats;
	
	private List<AircraftMaintenanceCheck> maintenanceChecks;
	
	private boolean hasEconomy = true;
	
	private boolean hasBusiness;
	
	private boolean hasFirst;
	
	private String economyTravelName = "Business Travel";
	
	private String businessTravelName = "Business Class";
	
	private String firstTravelName = "First Class";

	@Id
	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Address getOwnerAddress() {
		return ownerAddress;
	}

	public void setOwnerAddress(Address ownerAddress) {
		this.ownerAddress = ownerAddress;
	}

	@ManyToOne
	public AirplaneModel getModel() {
		return model;
	}

	public void setModel(AirplaneModel model) {
		this.model = model;
	}

	@Temporal(TemporalType.DATE)
	public Date getManufactureDate() {
		return manufactureDate;
	}

	public void setManufactureDate(Date manufactureDate) {
		this.manufactureDate = manufactureDate;
	}

	@Temporal(TemporalType.DATE)
	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public OperationType getOperationType() {
		return operationType;
	}

	public void setOperationType(OperationType operationType) {
		this.operationType = operationType;
	}

	@ManyToOne
	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	@OneToOne
	public DocumentFile getCofaDoc() {
		return cofaDoc;
	}

	public void setCofaDoc(DocumentFile cofaDoc) {
		this.cofaDoc = cofaDoc;
	}

	@OneToOne
	public DocumentFile getCofmDoc() {
		return cofmDoc;
	}

	public void setCofmDoc(DocumentFile cofmDoc) {
		this.cofmDoc = cofmDoc;
	}

	@OneToOne
	public DocumentFile getInsuranceDoc() {
		return insuranceDoc;
	}

	public void setInsuranceDoc(DocumentFile insuranceDoc) {
		this.insuranceDoc = insuranceDoc;
	}

	@OneToOne
	public DocumentFile getRadioLicenseDoc() {
		return radioLicenseDoc;
	}

	public void setRadioLicenseDoc(DocumentFile radioLicenseDoc) {
		this.radioLicenseDoc = radioLicenseDoc;
	}

	@OneToOne
	public LicensingAuthority getLicensingAuthority() {
		return licensingAuthority;
	}

	public void setLicensingAuthority(LicensingAuthority licensingAuthority) {
		this.licensingAuthority = licensingAuthority;
	}

	@ManyToOne
	public AircraftMaintenanceOrg getMaintenanceOrg() {
		return maintenanceOrg;
	}

	public void setMaintenanceOrg(AircraftMaintenanceOrg maintenanceOrg) {
		this.maintenanceOrg = maintenanceOrg;
	}

	@OneToMany(mappedBy = "airplane")
	@OrderBy("id.rowNumber ASC, id.columnId ASC")
	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	@OneToMany(mappedBy = "airplane")
	public List<AircraftMaintenanceCheck> getMaintenanceChecks() {
		return maintenanceChecks;
	}

	public void setMaintenanceChecks(List<AircraftMaintenanceCheck> maintenanceChecks) {
		this.maintenanceChecks = maintenanceChecks;
	}

	public boolean isHasEconomy() {
		return hasEconomy;
	}

	public void setHasEconomy(boolean hasEconomy) {
		this.hasEconomy = hasEconomy;
	}

	public boolean isHasBusiness() {
		return hasBusiness;
	}

	public void setHasBusiness(boolean hasBusiness) {
		this.hasBusiness = hasBusiness;
	}

	public boolean isHasFirst() {
		return hasFirst;
	}

	public void setHasFirst(boolean hasFirst) {
		this.hasFirst = hasFirst;
	}

	public String getEconomyTravelName() {
		return economyTravelName;
	}

	public void setEconomyTravelName(String economyTravelName) {
		this.economyTravelName = economyTravelName;
	}

	public String getBusinessTravelName() {
		return businessTravelName;
	}

	public void setBusinessTravelName(String businessTravelName) {
		this.businessTravelName = businessTravelName;
	}

	public String getFirstTravelName() {
		return firstTravelName;
	}

	public void setFirstTravelName(String firstTravelName) {
		this.firstTravelName = firstTravelName;
	}
	
}
