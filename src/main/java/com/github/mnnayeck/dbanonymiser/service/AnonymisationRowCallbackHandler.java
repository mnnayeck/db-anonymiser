/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.service;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.github.mnnayeck.dbanonymiser.bean.Anonymisation;
import com.github.mnnayeck.dbanonymiser.bean.DataAnonymiserFactory;
import com.github.mnnayeck.dbanonymiser.dao.SqlStatementDao;

/**
 * 
 */
public class AnonymisationRowCallbackHandler implements RowCallbackHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AnonymisationRowCallbackHandler.class);

	private Anonymisation anonymisation;
	private SqlStatementDao sqlStatementDao;

	public AnonymisationRowCallbackHandler(Anonymisation anonymisation, SqlStatementDao sqlStatementDao) {
		this.anonymisation = anonymisation;
		this.sqlStatementDao = sqlStatementDao;
	}

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
		    + " where "+ anonymisation.getPrimaryKey() + " = " + this.getPrimaryKey(primaryKey) + ";" 
			+ System.lineSeparator();
		
		this.sqlStatementDao.persist(anonymisation.getDatabaseRefId(), updateString);
	}

	private String getPrimaryKey(Object primaryKey) {
		return "'"+primaryKey+"'";
	}

}
