package com.ss.sf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.BookLoans;

/**
 * @author Jacob
 *
 */
public class BookLoansDAO extends BaseDAO<BookLoans> {

	public void addLoan(BookLoans loan) throws SQLException {
		save("insert into tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values (?, ?, ?, ?, ?)",
			new Object[] {loan.getBookId(), loan.getBranchId(), loan.getCardNo(), loan.getDateOut(), loan.getDueDate()});
	}
	
	public void updateLoan(BookLoans loan) throws SQLException {
		save("update tbl_book_loans set dateOut = ?, dueDate = ? where bookId = ? and branchId = ? and cardNo = ?",
			new Object[] {loan.getDateOut(), loan.getDueDate(), loan.getBookId(), loan.getBranchId(), loan.getCardNo()});
	}
	
	public void deleteLoan(BookLoans loan) throws SQLException {
		save("delete from tbl_book_loans where bookId = ? and branchId = ? and cardNo = ?",
			new Object[] {loan.getBookId(), loan.getBranchId(), loan.getCardNo()});
	}
	
	public List<BookLoans> readLoans() throws SQLException {
		return read("select * from tbl_book_loans", null);
	}
	
	public List<BookLoans> readLoansByCardNo(int cardNo) throws SQLException {
		return read("select * from tbl_book_loans where cardNo = ?", new Object[] {cardNo});
	}
	
	public List<BookLoans> readLoansByCardNoAndBranch(int cardNo, int branchId) throws SQLException {
		return read("select * from tbl_book_loans where cardNo = ? and branchId = ?",
			new Object[] {cardNo, branchId});
	}
	
	@Override
	List<BookLoans> extractData(ResultSet rs) throws SQLException {
		List<BookLoans> loans = new ArrayList<>();
		while (rs.next()) {
			BookLoans loan = new BookLoans();
			loan.setBookId(rs.getInt("bookId"));
			loan.setBranchId(rs.getInt("branchId"));
			loan.setCardNo(rs.getInt("cardNo"));
			loan.setDateOut(LocalDateTime.parse(rs.getString("dateOut").replace(" ", "T")));
			loan.setDueDate(LocalDateTime.parse(rs.getString("dueDate").replace(" ", "T")));
			loans.add(loan);
		}
		
		return loans;
	}
}
