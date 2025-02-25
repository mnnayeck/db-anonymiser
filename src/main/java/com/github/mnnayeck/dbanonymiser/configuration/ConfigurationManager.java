/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.configuration;

import java.io.IOException;

import org.springframework.core.io.Resource;

import com.github.mnnayeck.dbanonymiser.bean.Configuration;

/**
 * @author Nadeem
 *
 */
public abstract class ConfigurationManager {
	
	private static ConfigurationManager instance;

	protected abstract Configuration load();
	
	public static Configuration getInstance(Resource configFile) throws IOException {
        if (instance == null) {
        	instance = new JsonConfigurationManagerImpl(configFile);
        }
        return instance.load();
    }



}
