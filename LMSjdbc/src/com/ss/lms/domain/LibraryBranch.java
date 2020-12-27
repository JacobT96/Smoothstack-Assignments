package com.ss.lms.domain;

import java.io.Serializable;

/**
 * @author Jacob
 *
 */
public class LibraryBranch implements Serializable {

	private static final long serialVersionUID = -8232174189998714890L;
	private int branchId;
	private String branchName;
	private String branchAddress;
	
	/**
	 * @return the branchId
	 */
	public int getBranchId() {
		return branchId;
	}
	
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	
	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	/**
	 * @return the branchAddress
	 */
	public String getBranchAddress() {
		return branchAddress;
	}
	
	/**
	 * @param branchAddress the branchAddress to set
	 */
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	@Override
	public String toString() {
		return branchName + " Library, " + branchAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + branchId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LibraryBranch other = (LibraryBranch) obj;
		if (branchId != other.branchId)
			return false;
		return true;
	}
}
