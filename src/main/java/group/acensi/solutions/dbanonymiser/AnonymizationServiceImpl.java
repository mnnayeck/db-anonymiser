/**
 * 
 */
package group.acensi.solutions.dbanonymiser;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.ctc.wstx.util.StringUtil;

import group.acensi.solutions.dbanonymiser.configuration.Anonymisation;

/**
 * 
 */
public class AnonymizationServiceImpl implements AnonymisationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnonymizationServiceImpl.class);
	private Map<String, JdbcTemplate> databaseJdbcTemplatesList = new HashMap<>();
	private Map<String, File> sqlFiles = new HashMap<>();
	
	
	/**
	 * @param databaseConnectionMap
	 * @throws IOException 
	 */
	public AnonymizationServiceImpl(Map<String, DriverManagerDataSource> databaseConnectionMap) throws IOException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
		File folder = new File(simpleDateFormat.format(new Date()));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		LOGGER.info("Using results folder: {}", folder.getAbsolutePath());
		for (Entry<String, DriverManagerDataSource> dataSourceEntry : databaseConnectionMap.entrySet()) {
			JdbcTemplate template = new JdbcTemplate(dataSourceEntry.getValue());
			template.afterPropertiesSet();
			this.databaseJdbcTemplatesList.put(dataSourceEntry.getKey(), template);
			sqlFiles.put(dataSourceEntry.getKey(), this.createFile(folder, dataSourceEntry.getKey()));
		}
	}

	/**
	 * @param folder 
	 * @param key
	 * @return
	 * @throws IOException 
	 */
	private File createFile(File folder, String key) throws IOException {
		File file = new File(folder, key + ".sql");
		if (file.exists()) {
			LOGGER.info("Deleting existing file: {}", file.getAbsolutePath());
		}
		file.createNewFile();
		return file;
	}

	@Override
	public void anonymize(Anonymisation anonymisation) {
		
		String select = "SELECT "+anonymisation.getPrimaryKey()+"," + anonymisation.getColumnName() + " from " + anonymisation.getTableName();
		
		this.databaseJdbcTemplatesList.get(anonymisation.getDatabaseRefId()).query(select, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				
				while (rs.next()) {
					Object value = rs.getObject(anonymisation.getColumnName());
					Object primaryKey = rs.getObject(anonymisation.getPrimaryKey());
					if (value == null) {
						continue;
					}
					if (value instanceof String && StringUtils.isBlank((String)value)) {
						continue;
					}
					doAnonymize(anonymisation, primaryKey, value);
				}
				
				
			}
		});
	}

	/**
	 * @param anonymisation
	 * @param value
	 * @param value2 
	 * @return
	 */
	protected void doAnonymize(Anonymisation anonymisation, Object primaryKey, Object value) {
		DataAnonymiser<Serializable> anonymizer = DataAnonymiserFactory.create(anonymisation.getAnonymiser());
		if (anonymizer == null) {
			LOGGER.error("No anonymizer of type {}", anonymisation.getAnonymiser());
			return;
		}
		Serializable anonymized = anonymizer.anonymize((Serializable) value);
		File sqlFile = this.sqlFiles.get(anonymisation.getDatabaseRefId());
		String updateString = "UPDATE "+anonymisation.getTableName()+" set "+anonymisation.getColumnName()+"='"+anonymized+"' where "+anonymisation.getPrimaryKey()+"="+primaryKey + ";" + System.lineSeparator();
//		LOGGER.info("Update String: {}", updateString);
		try {
			FileUtils.write(sqlFile, updateString, StandardCharsets.UTF_8, true);
		} catch (IOException ex) {
			LOGGER.error("Error appending to file", ex);
		}
	}

	@Override
	public void updateDb() throws IOException {
		for(Entry<String, File> entry: this.sqlFiles.entrySet()) {
			String fileName = entry.getKey();
			File file = entry.getValue();
			JdbcTemplate template = this.databaseJdbcTemplatesList.get(fileName);
			LOGGER.info("Executing SQLs in file {}", file.getAbsolutePath());
			List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
			int count = 0;
			for(String line: lines) {
				template.execute(line);
				LOGGER.info("Exeecuted {} out of {}", ++count, lines.size());
			}
		}
		
	}

}
