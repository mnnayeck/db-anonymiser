/**
 * 
 */
package com.github.mnnayeck.dbanonymiser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
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
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DBAnonynmizerApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DBAnonynmizerApplication.class);
	
	public static void main(String[] args) throws IOException {
		
		if (args == null || args.length == 0) {
			LOGGER.error("You must specify a json file as argument");
			System.exit(-1);
		}

		File file = new File(args[0]);
		if (!file.exists()) {
			LOGGER.error("Resource {} does not exist", file.getAbsolutePath());
			System.exit(-1);
		}
		
		ApplicationContext context = SpringApplication.run(DBAnonynmizerApplication.class, args);
		DBAnonynmizerApplication application = context.getBean(DBAnonynmizerApplication.class);
		application.run(file);
	}


	/**
	 * @param args
	 * @throws IOException 
	 */
	int run(File file) throws IOException {
		
		long totalStart = System.currentTimeMillis();
		
		Resource resource = new FileSystemResource(file);
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
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Generation of anonymised SQL for table '{}' and column '{}' in {}", anonymisation.getTableName(), anonymisation.getColumnName(),  DurationFormatUtils.formatDurationWords((System.currentTimeMillis() - start), true, true));
			}
		}
		
		anonymisationService.updateDb();
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("Total execution time: {} seconds", (DurationFormatUtils.formatDurationWords((System.currentTimeMillis() - totalStart), true, true)));
		}
		return 0;
	}

}
