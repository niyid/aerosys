package com.vasworks.airliner.struts;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.vasworks.airliner.model.Booking;
import com.vasworks.airliner.model.Invoice;

// TODO: Auto-generated Javadoc
/**
 * The Class DashboardAction.
 */
public class PaymentAction extends OperatorAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -45564564536L;
	
	/** The Constant log. */
	private static final Log LOG = LogFactory.getLog(PaymentAction.class);
	
	private String cardType;
	
	private String cardName;
	
	private String cardNumber;
	
	private String cardPin;
	
	private String cardCvv;
	
	private String expirationDay;
	
	private String expirationYear;
	
	private Double amount;
	
	private Long bookingId;
	
	private Long invoiceId;
	
	/* (non-Javadoc)
	 * @see com.vasworks.struts.BaseAction#prepare()
	 */
	@Override
	@SkipValidation
	public void prepare() {
		LOG.debug("prepare()");
		if(bookingId != null) {
			Booking booking = (Booking) operatorService.find(bookingId, Booking.class);
			amount = booking.getTotalPrice();
		}
		if(invoiceId != null) {
			Invoice invoice = (Invoice) operatorService.find(invoiceId, Invoice.class);
			amount = invoice.getTotalAmount();
		}
	}

	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 */
	@SkipValidation
	public String execute() {
		return SUCCESS;
	}

	@Validations(
		requiredStrings = {
			@RequiredStringValidator(fieldName = "cardType", message = "'Card Type' is required."),
			@RequiredStringValidator(fieldName = "cardName", message = "'Card Type' is required."),
			@RequiredStringValidator(fieldName = "cardNumber", message = "'Card Type' is required."),
			@RequiredStringValidator(fieldName = "cardPin", message = "'Card Type' is required."),
			@RequiredStringValidator(fieldName = "cardCvv", message = "'Card Type' is required."),
			@RequiredStringValidator(fieldName = "expirationDay", message = "'Card Type' is required."),
			@RequiredStringValidator(fieldName = "expirationYear", message = "'Card Type' is required.")
		}
	)	
	public String save() {
		if(invoiceId != null) {
			String paymentStatus = operatorService.payInvoice(bookingId, getUserId());
			if(paymentStatus == null) {
				addActionMessage("Invoice successfully paid.");
				stepConfirm();
			} else {
				addActionError("Payment failed - " + paymentStatus);
			}
		}
		if(bookingId != null) {
			String paymentStatus = operatorService.payBooking(bookingId, getUserId());
			if(paymentStatus == null) {
				addActionMessage("Booking successfully paid and confirmed.");
				stepConfirm();
			} else {
				addActionError("Payment failed - " + paymentStatus);
			}
		}

		return SUCCESS;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardPin() {
		return cardPin;
	}

	public void setCardPin(String cardPin) {
		this.cardPin = cardPin;
	}

	public String getCardCvv() {
		return cardCvv;
	}

	public void setCardCvv(String cardCvv) {
		this.cardCvv = cardCvv;
	}

	public String getExpirationDay() {
		return expirationDay;
	}

	public void setExpirationDay(String expirationDay) {
		this.expirationDay = expirationDay;
	}

	public String getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
