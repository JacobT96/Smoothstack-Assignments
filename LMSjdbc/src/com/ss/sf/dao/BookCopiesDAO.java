package com.ss.sf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.BookCopies;

/**
 * @author Jacob
 *
 */
public class BookCopiesDAO extends BaseDAO<BookCopies> {

	public void addCopies(BookCopies copies) throws SQLException {
		save("insert into tbl_book_copies (bookId, branchId, noOfCopies) values (?, ?, ?)",
			new Object[] {copies.getBookId(), copies.getBranchId(), copies.getNoOfCopies()});
	}
	
	public void updateCopies(BookCopies copies) throws SQLException {
		save("update tbl_book_copies set noOfCopies = ? where bookId = ? and branchId = ?",
			new Object[] {copies.getNoOfCopies(), copies.getBookId(), copies.getBranchId()});
	}
	
	public void deleteCopies(BookCopies copies) throws SQLException {
		save("delete from tbl_book_copies where bookId = ? and branchId = ?",
			new Object[] {copies.getBookId(), copies.getBranchId()});
	}
	
	public List<BookCopies> readCopies() throws SQLException {
		return read("select * from tbl_book_copies", null);
	}
	
	public List<BookCopies> readCopiesByBookAndBranch(int bookId, int branchId) throws SQLException {
		return read("select * from tbl_book_copies where bookId = ? and branchId = ?",
			new Object[] {bookId, branchId});
	}
	
	public List<BookCopies> readCopiesByBranch(int branchId) throws SQLException {
		return read("select * from tbl_book_copies where branchId = ?", new Object[] {branchId});
	}
	
	@Override
	List<BookCopies> extractData(ResultSet rs) throws SQLException {
		List<BookCopies> copies = new ArrayList<>();
		while (rs.next()) {
			BookCopies copy = new BookCopies();
			copy.setBookId(rs.getInt("bookId"));
			copy.setBranchId(rs.getInt("branchId"));
			copy.setNoOfCopies(rs.getInt("noOfCopies"));
			copies.add(copy);
		}
		
		return copies;
	}

}
