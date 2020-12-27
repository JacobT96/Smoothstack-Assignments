package com.ss.lms.domain;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import com.ss.sf.dao.AuthorDAO;

/**
 * @author Jacob
 *
 */
public class Book implements Serializable {

	private static final long serialVersionUID = -5785983860991023225L;
	private int bookId;
	private String title;
	private int authId;
	private int pubId;
	
	private AuthorDAO DAO;
	
	public Book() {
		DAO = new AuthorDAO();
	}
	
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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the authId
	 */
	public int getAuthId() {
		return authId;
	}
	/**
	 * @param authId the authId to set
	 */
	public void setAuthId(int authId) {
		this.authId = authId;
	}
	/**
	 * @return the pubId
	 */
	public int getPubId() {
		return pubId;
	}
	/**
	 * @param pubId the pubId to set
	 */
	public void setPubId(int pubId) {
		this.pubId = pubId;
	}
	
	@Override
	public String toString() {
		try {
			List<Author> authors = DAO.readAuthorsById(authId);
			Author auth = authors.get(0);
			return title + " by " + auth.toString();
		} catch (SQLException e) {
			return title;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bookId;
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
		Book other = (Book) obj;
		if (bookId != other.bookId)
			return false;
		return true;
	}
}
