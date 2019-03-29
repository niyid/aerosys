package com.vasworks.airliner.model;

public interface YieldManagementRule {
	
	static enum YieldRuleType {RULE1, RULE2, RULE3, RULE4};
	
	/**
	 * 
	 * @return
	 */
	double getMaxPercent();
	
	/**
	 * 
	 * @param maxPercent
	 */
	void setMaxPercent(double maxPercent);
	
	/**
	 * 
	 * @return
	 */
	YieldRuleType getRuleType();
	
	/**
	 * 
	 * @param ruleType
	 */
	void setRuleType(YieldRuleType ruleType);
	
	/**
	 * 
	 * @return
	 */
	int getPrecedence();
	
	/**
	 * 
	 * @param precedence
	 */
	void setPrecedence(int precedence);
}
