package com.vasworks.airliner.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vasworks.service.EmailException;
import com.vasworks.service.impl.JavaMail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class MailerTest {
	
	public static final Log LOG = LogFactory.getLog(MailerTest.class);
	
	@Autowired
	private JavaMail emailService;

	@Test
	public void send() {		
		StringBuilder b = new StringBuilder("http://localhost:8080/airliner-web/activate.jspx?sid=");
		b.append("neeyeed@gmail.com");
		b.append("&acd=");
		b.append("HvMAbhgssyLZ4j1T");
		
		String emailBody = "Please click on the following link for activation: " + b;
		try {
			emailService.sendEmail("neeyeed@gmail.com", "neeyeed@gmail.com", "SwappawS Registration Activation", emailBody);
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
