/**
 * 
 */
package group.acensi.solutions.dbanonymiser.configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Nadeem
 *
 */
public class JsonConfigurationManagerImpl extends ConfigurationManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonConfigurationManagerImpl.class);
	
	private Resource configFile;
	
	JsonConfigurationManagerImpl (Resource configFile) throws IOException {
		LOGGER.info("Using Json Config file: {}", configFile.getURI());
		this.configFile = configFile;
		this.load();
	}
	
	protected Configuration load()  {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			
			return objectMapper.readValue(this.configFile.getInputStream(), Configuration.class);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

}
