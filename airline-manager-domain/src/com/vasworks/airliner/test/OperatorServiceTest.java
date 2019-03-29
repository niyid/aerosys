package com.vasworks.airliner.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vasworks.airliner.service.OperatorServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class OperatorServiceTest {

	@Autowired
	private OperatorServiceImpl operatorService;
	
	public static final Log LOG = LogFactory.getLog(OperatorServiceTest.class);
	
	@Test
	public void testGenerateInvoices() {
		LOG.info("testConfirmBooking - " + operatorService);
		operatorService.generateInvoices();
	}
}
