/**
 * 
 */
package group.acensi.solutions.dbanonymiser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import group.acensi.solutions.dbanonymiser.configuration.Anonymisation;
import group.acensi.solutions.dbanonymiser.configuration.Configuration;
import group.acensi.solutions.dbanonymiser.configuration.ConfigurationManager;
import group.acensi.solutions.dbanonymiser.configuration.Database;

/**
 * @author Nadeem
 *
 */
@SpringBootApplication
public class DBAnonynmizerApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DBAnonynmizerApplication.class);
	
	public static void main(String[] args) {
		
		SpringApplication.run(DBAnonynmizerApplication.class, args);
	}


	/**
	 * @param args
	 * @throws IOException 
	 */
	@Bean
	public int run() throws IOException {
		
		long totalStart = System.currentTimeMillis();
		
		Resource resource = new ClassPathResource("application.json");
		Configuration configuration = ConfigurationManager.getInstance(resource);
		List<Database> databases = configuration.getDatabases();
		
		Map<String, DriverManagerDataSource> databaseConnectionMap = new HashMap<>();
		
		for(Database database: databases) {
			DriverManagerDataSource dataSource = new DriverManagerDataSource(database.getUrl(), database.getUsername(), database.getPassword());
			dataSource.setDriverClassName(database.getDriverClassName());
			databaseConnectionMap.put(database.getRefId(), dataSource);
		}
		
		AnonymisationService anonymisationService = new AnonymizationServiceImpl(databaseConnectionMap);
		
		for(Anonymisation anonymisation: configuration.getAnonymisations()) {
			long start = System.currentTimeMillis();
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Treating entry {}", anonymisation.toString());
			}
			anonymisationService.anonymize(anonymisation);
			LOGGER.info("Generation of anonymised SQL for table '{}' and column '{}' in {} milliseconds", anonymisation.getTableName(), anonymisation.getColumnName(),  (System.currentTimeMillis() - start));
		}
		
		anonymisationService.updateDb();
		LOGGER.info("Total execution time: {} seconds", ((System.currentTimeMillis() - totalStart)/1000));
		return 0;
	}

}
