/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.service;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.github.mnnayeck.dbanonymiser.bean.Anonymisation;
import com.github.mnnayeck.dbanonymiser.bean.DataAnonymiserFactory;
import com.github.mnnayeck.dbanonymiser.dao.FileSqlStatementDaoImpl;
import com.github.mnnayeck.dbanonymiser.dao.SqlStatementDao;

/**
 * 
 */
public class AnonymizationServiceImpl implements AnonymisationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnonymizationServiceImpl.class);
	private Map<String, JdbcTemplate> databaseJdbcTemplatesList = new HashMap<>();	
	private SqlStatementDao sqlStatementDao = new FileSqlStatementDaoImpl();
	
	
	/**
	 * @param databaseConnectionMap
	 * @throws IOException 
	 */
	public AnonymizationServiceImpl(Map<String, DriverManagerDataSource> databaseConnectionMap) throws IOException {
	
		for (Entry<String, DriverManagerDataSource> dataSourceEntry : databaseConnectionMap.entrySet()) {
			JdbcTemplate template = new JdbcTemplate(dataSourceEntry.getValue());
			template.afterPropertiesSet();
			this.databaseJdbcTemplatesList.put(dataSourceEntry.getKey(), template);
			sqlStatementDao.addStore(dataSourceEntry.getKey());
		}
	}


	@Override
	public void anonymize(Anonymisation anonymisation) {
		
		String select = "SELECT "+anonymisation.getPrimaryKey()+"," + anonymisation.getColumnName() + " from " + anonymisation.getTableName();
		
		this.databaseJdbcTemplatesList.get(anonymisation.getDatabaseRefId()).query(select, new RowCallbackHandler() {
			
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				
				Object value = rs.getObject(anonymisation.getColumnName());
				Object primaryKey = rs.getObject(anonymisation.getPrimaryKey());
				if (value == null
						|| value instanceof String && StringUtils.isBlank((String)value)){
					return;
				}

				doAnonymize(anonymisation, primaryKey, value);
				
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
		DataAnonymiserService<Serializable> anonymizer = DataAnonymiserFactory.create(anonymisation.getAnonymiser());
		if (anonymizer == null) {
			LOGGER.error("No anonymizer of type {}", anonymisation.getAnonymiser());
			return;
		}
		Serializable anonymized = anonymizer.anonymize((Serializable) value);
		String updateString = "UPDATE " +anonymisation.getTableName() 
			+ " set " + anonymisation.getColumnName() + " = '" + anonymized + "'"
		    + " where "+ anonymisation.getPrimaryKey() + " = '" + primaryKey + "';" 
			+ System.lineSeparator();
		
		this.sqlStatementDao.persist(anonymisation.getDatabaseRefId(), updateString);
	}

	@Override
	public void updateDb() throws IOException {
		
		Map<String, Stream<String>> fileMap = this.sqlStatementDao.getFileMap();
		
		for(Entry<String, Stream<String>> entry: fileMap.entrySet()) {
			String fileName = entry.getKey();
			Stream<String> linesStream = entry.getValue();
			JdbcTemplate template = this.databaseJdbcTemplatesList.get(fileName);

			LOGGER.info("Executing SQLs in file {}", fileName);
			
			int[] index = {0}; // final not neccessary here if no other array is assigned
			int[] count = {0}; // final not neccessary here if no other array is assigned
			int totalCount = this.sqlStatementDao.countLines(fileName);
			
			final int batchSize = 5000;
			
			String[] statements = new String[batchSize];
			
			linesStream.forEach(line -> {
//				template.execute(line);
				
				statements[index[0]++] = line;
				count[0]++;
				if (index[0] % batchSize == 0 || count[0] >= totalCount) { //log log after 100 lines
					template.batchUpdate(Arrays.stream(statements).filter(Objects::nonNull).toArray(String[]::new));
					LOGGER.info("Executed {} out of {}",(count[0]) , (totalCount));
					for(int i=0; i<statements.length; i++) {
						statements[i] = null;
					}
					index[0] = 0;
				}
            });
			linesStream.close();
		}
		
	}

}
