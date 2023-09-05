/**
 * 
 */
package group.acensi.solutions.dbanonymiser.bean;

import java.util.List;

/**
 * 
 */
public class Configuration {

	private List<Database> databases;
	private List<Exclusion> exclusions;
	private List<Anonymisation> anonymisations;

	/**
	 * @return the databases
	 */
	public List<Database> getDatabases() {
		return databases;
	}

	/**
	 * @param databases the databases to set
	 */
	public void setDatabases(List<Database> databases) {
		this.databases = databases;
	}

	/**
	 * @return the anonymisations
	 */
	public List<Anonymisation> getAnonymisations() {
		return anonymisations;
	}

	/**
	 * @param anonymisations the anonymisations to set
	 */
	public void setAnonymisations(List<Anonymisation> anonymisations) {
		this.anonymisations = anonymisations;
	}

	public List<Exclusion> getExclusions() {
		return exclusions;
	}

	public void setExclusions(List<Exclusion> exclusions) {
		this.exclusions = exclusions;
	}

}
