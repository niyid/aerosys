/**
 * 
 */
package com.vasworks.service;

import java.util.Collection;

/**
 * @author developer
 *
 */
public interface EmailService {
	void sendEmail(String sender, String recipient, String subject, String body) throws EmailException;

	/**
	 * @param sender
	 * @param mail
	 * @param emailSubject
	 * @param emailText
	 * @param string
	 * @param output
	 * @throws EmailException 
	 */
	void sendEmailWithAttachment(String sender, String recipient, Collection<String> cc, String emailSubject, String emailText, String string, byte[] output) throws EmailException;

	/**
	 * @param object
	 * @param addresses
	 * @param recipients
	 * @param subject
	 * @param body
	 * @throws EmailException 
	 */
	void sendEmail(String sender, String[] recipients, String[] ccRecipients, String subject, String body) throws EmailException;
}
