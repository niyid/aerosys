package com.vasworks.airliner.model;

public interface RateInterface {
	
	/**
	 * 
	 * @return
	 */
	String getDescription();
	
	/**
	 * 
	 * @param description
	 */
	void setDescription(String description);
	
	/**
	 * 
	 * @return
	 */
	Double getRatePercentage();
	
	/**
	 * 
	 * @param ratePercentage
	 */
	void setRatePercentage(Double ratePercentage);
	
	/**
	 * 
	 * @return
	 */
	Double getFixedRate();
	
	/**
	 * 
	 * @param fixedRate
	 */
	void setFixedRate(Double fixedRate);
	
	/**
	 * 
	 * @return
	 */
	boolean isAutoAdded();
	
	/**
	 * 
	 * @param autoAdded
	 */
	void setAutoAdded(boolean autoAdded);
	
	/**
	 * 
	 * @return
	 */
	String getRateCode();
	
	/**
	 * 
	 * @param rateCode
	 */
	void setRatecode(String rateCode);
}
