/**
 * 
 */
package com.github.mnnayeck.dbanonymiser;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.github.mnnayeck.dbanonymiser.bean.Anonymisation;
import com.github.mnnayeck.dbanonymiser.bean.Configuration;
import com.github.mnnayeck.dbanonymiser.bean.Database;
import com.github.mnnayeck.dbanonymiser.configuration.ConfigurationManager;
import com.github.mnnayeck.dbanonymiser.service.AnonymisationService;
import com.github.mnnayeck.dbanonymiser.service.AnonymizationServiceImpl;

/**
 * @author Nadeem
 *
 */
@SpringBootApplication
public class DBAnonynmizerApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DBAnonynmizerApplication.class);
	
	public static void main(String[] args) throws IOException {
		
		ApplicationContext context = SpringApplication.run(DBAnonynmizerApplication.class, args);
		DBAnonynmizerApplication application = context.getBean(DBAnonynmizerApplication.class);
		application.run();
	}


	/**
	 * @param args
	 * @throws IOException 
	 */
	int run() throws IOException {
		
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
				LOGGER.info("Treating entry {}", anonymisation);
			}
			anonymisationService.anonymize(anonymisation);
			LOGGER.info("Generation of anonymised SQL for table '{}' and column '{}' in {} milliseconds", anonymisation.getTableName(), anonymisation.getColumnName(),  (System.currentTimeMillis() - start));
		}
		
		anonymisationService.updateDb();
		LOGGER.info("Total execution time: {} seconds", ((System.currentTimeMillis() - totalStart)/1000));
		return 0;
	}

}
