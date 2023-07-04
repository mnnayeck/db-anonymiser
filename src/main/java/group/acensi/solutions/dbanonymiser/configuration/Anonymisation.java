/**
 * 
 */
package group.acensi.solutions.dbanonymiser.configuration;

/**
 * @author Nadeem
 *
 */
public class Anonymisation {

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

}
