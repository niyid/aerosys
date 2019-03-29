package com.vasworks.airliner.test;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vasworks.airliner.service.TravelerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/applicationContext.xml"})
public class MatcherTest {
	
	public static final Log LOG = LogFactory.getLog(MatcherTest.class);
	
	@Autowired
	private TravelerService travelerService;

	@Test
	public void matchItem() {
		try {
	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

//	@Test
	public void matchProfile() {
	
	}
}
