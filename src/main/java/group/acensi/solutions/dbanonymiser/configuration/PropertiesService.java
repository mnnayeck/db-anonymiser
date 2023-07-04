/**
 * 
 */
package group.acensi.solutions.dbanonymiser.configuration;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * @author nmnayeck
 *
 */
public class PropertiesService {
	
	private static PropertiesService propertiesService = null;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesService.class);
	
	private java.util.Properties properties;
	
	private PropertiesService() throws IOException {
		this.properties = new java.util.Properties();
		Resource resource = new ClassPathResource(Properties.PROP_FILE);
		LOGGER.info("Loading properties file {} from classpath", Properties.PROP_FILE);
		properties.load(resource.getInputStream());
	}
	
	
	public static PropertiesService getInstance() {
		if (propertiesService == null) {
			try {
				propertiesService = new PropertiesService();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return propertiesService;
	}
	


	public int obtainBatchProcessingSize() {
		return Integer.parseInt((String) this.properties.get(Properties.FILE_BATCH_SIZE));
	}
	

	public int obtainLoadingBatchSize() {
		return Integer.parseInt((String) this.properties.get(Properties.LOADING_BATCH_SIZE));
	}

}
