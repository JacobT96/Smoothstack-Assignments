package com.ss.lms.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Jacob
 *
 */
public class BookLoans implements Serializable {

	private static final long serialVersionUID = 2913798169139895712L;
	private int bookId;
	private int branchId;
	private int cardNo;
	private LocalDateTime dateOut;
	private LocalDateTime dueDate;
	
	/**
	 * @return the bookId
	 */
	public int getBookId() {
		return bookId;
	}
	
	/**
	 * @param bookId the bookId to set
	 */
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
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
	 * @return the cardNo
	 */
	public int getCardNo() {
		return cardNo;
	}
	
	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(int cardNo) {
		this.cardNo = cardNo;
	}
	
	/**
	 * @return the dateOut
	 */
	public LocalDateTime getDateOut() {
		return dateOut;
	}
	
	/**
	 * @param dateOut the dateOut to set
	 */
	public void setDateOut(LocalDateTime dateOut) {
		this.dateOut = dateOut;
	}
	
	/**
	 * @return the dueDate
	 */
	public LocalDateTime getDueDate() {
		return dueDate;
	}
	
	/**
	 * @param dueDate the dueDate to set
	 */
	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return "BookLoans [bookId=" + bookId + ", branchId=" + branchId + ", cardNo=" + cardNo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bookId;
		result = prime * result + branchId;
		result = prime * result + cardNo;
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
		BookLoans other = (BookLoans) obj;
		if (bookId != other.bookId)
			return false;
		if (branchId != other.branchId)
			return false;
		if (cardNo != other.cardNo)
			return false;
		return true;
	}
}
