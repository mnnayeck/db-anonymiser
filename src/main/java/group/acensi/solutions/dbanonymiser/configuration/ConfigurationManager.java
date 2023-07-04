/**
 * 
 */
package group.acensi.solutions.dbanonymiser.configuration;

import java.io.IOException;

import org.springframework.core.io.Resource;

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
