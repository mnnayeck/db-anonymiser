/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.test;


import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.github.mnnayeck.dbanonymiser.bean.Configuration;
import com.github.mnnayeck.dbanonymiser.configuration.ConfigurationManager;

/**
 * @author Nadeem
 *
 */
public class ConfigurationTests {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ConfigurationTests.class);
	
	private Resource resource = new ClassPathResource("application-test.json");
	
	@Test
	public void loadConfigurationTest() throws Exception {
		LOGGER.info("Loading test configuration");
		Configuration configuration = ConfigurationManager.getInstance(resource);
		Assert.assertEquals(2, configuration.getDatabases().size());
		Assert.assertEquals(12, configuration.getAnonymisations().size());
	}

}
