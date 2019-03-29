package com.vasworks.airliner.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vasworks.airliner.service.TravelerServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class TravelerServiceTest {

	@Autowired
	private TravelerServiceImpl travelerService;
	
	public static final Log LOG = LogFactory.getLog(TravelerServiceTest.class);
	
	@Test
	public void testConfirmBooking() {
		LOG.info("testConfirmBooking - " + travelerService);
		travelerService.confirmBooking(5L, 1L);
	}
}
