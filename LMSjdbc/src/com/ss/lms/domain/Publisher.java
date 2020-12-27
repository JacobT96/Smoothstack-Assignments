package com.ss.lms.domain;

import java.io.Serializable;

/**
 * @author Jacob
 *
 */
public class Publisher implements Serializable {

	private static final long serialVersionUID = 1986696951403678655L;
	private int publisherId;
	private String publisherName;
	private String publisherAddress;
	private String publisherPhone;
	
	/**
	 * @return the publisherId
	 */
	public int getPublisherId() {
		return publisherId;
	}
	
	/**
	 * @param publisherId the publisherId to set
	 */
	public void setPublisherId(int publisherId) {
		this.publisherId = publisherId;
	}
	
	/**
	 * @return the publisherName
	 */
	public String getPublisherName() {
		return publisherName;
	}
	
	/**
	 * @param publisherName the publisherName to set
	 */
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	
	/**
	 * @return the publisherAddress
	 */
	public String getPublisherAddress() {
		return publisherAddress;
	}
	
	/**
	 * @param publisherAddress the publisherAddress to set
	 */
	public void setPublisherAddress(String publisherAddress) {
		this.publisherAddress = publisherAddress;
	}
	
	/**
	 * @return the publisherPhone
	 */
	public String getPublisherPhone() {
		return publisherPhone;
	}
	
	/**
	 * @param publisherPhone the publisherPhone to set
	 */
	public void setPublisherPhone(String publisherPhone) {
		this.publisherPhone = publisherPhone;
	}

	@Override
	public String toString() {
		return publisherName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + publisherId;
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
		Publisher other = (Publisher) obj;
		if (publisherId != other.publisherId)
			return false;
		return true;
	}
}
