package com.github.mnnayeck.dbanonymiser.bean;

public class Exclusion {

	private ExclusionType exclusionType = ExclusionType.EXACT_MATCH;
	private ExclusionApplicationType exclusionApplicationType = ExclusionApplicationType.VALUE_ONLY;
	private String value;
	private String[] targetTables;

	public ExclusionType getExclusionType() {
		return exclusionType;
	}

	public void setExclusionType(ExclusionType exclusionType) {
		this.exclusionType = exclusionType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String[] getTargetTables() {
		return targetTables;
	}

	public void setTargetTables(String[] targetTables) {
		this.targetTables = targetTables;
	}

	public ExclusionApplicationType getExclusionApplicationType() {
		return exclusionApplicationType;
	}

	public void setExclusionApplicationType(ExclusionApplicationType exclusionApplicationType) {
		this.exclusionApplicationType = exclusionApplicationType;
	}

}
