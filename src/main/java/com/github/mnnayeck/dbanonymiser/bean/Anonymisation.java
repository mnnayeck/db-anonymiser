/**
 * 
 */
package com.github.mnnayeck.dbanonymiser.bean;

import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Nadeem
 *
 */
public class Anonymisation implements Comparable<Anonymisation> {

	private String tableName;
	private String primaryKey;
	private String columnName;
	private String anonymiser;
	private String databaseRefId;

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * @return the anonymiser
	 */
	public String getAnonymiser() {
		return anonymiser;
	}

	/**
	 * @param anonymiser the anonymiser to set
	 */
	public void setAnonymiser(String anonymizer) {
		this.anonymiser = anonymizer;
	}

	/**
	 * @return the databaseRefId
	 */
	public String getDatabaseRefId() {
		return databaseRefId;
	}

	/**
	 * @param databaseRefId the databaseRefId to set
	 */
	public void setDatabaseRefId(String databaseRefId) {
		this.databaseRefId = databaseRefId;
	}

	/**
	 * @return the primaryKey
	 */
	public String getPrimaryKey() {
		return this.primaryKey;
	}

	/**
	 * @param primaryKey the primaryKey to set
	 */
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public String toString() {
		return "Anonymisation [tableName=" + this.tableName + ", primaryKey=" + this.primaryKey + ", columnName="
				+ this.columnName + ", anonymiser=" + this.anonymiser + ", databaseRefId=" + this.databaseRefId + "]";
	}

	@Override
	public int compareTo(Anonymisation o) {
		return this.tableName.compareTo(o.tableName);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Anonymisation object2 = (Anonymisation) obj;
		return this.tableName.equalsIgnoreCase(object2.getTableName())
				&& this.columnName.equalsIgnoreCase(object2.getColumnName())
				&& this.anonymiser.equalsIgnoreCase(object2.getAnonymiser());
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}

}
