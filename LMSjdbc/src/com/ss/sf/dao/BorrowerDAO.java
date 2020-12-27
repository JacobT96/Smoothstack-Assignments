package com.ss.sf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.Borrower;

/**
 * @author Jacob
 *
 */
public class BorrowerDAO extends BaseDAO<Borrower> {

	public void addBorrower(Borrower borrower) throws SQLException {
		save("insert into tbl_borrower (name, address, phone, cardNo) values (?, ?, ?, ?)",
			new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone(), readBorrowers().size()});
	}
	
	public void updateBorrower(Borrower borrower) throws SQLException {
		save("update tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?",
			new Object[] {borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo()});
	}
	
	public void deleteBorrower(Borrower borrower) throws SQLException {
		save("delete from tbl_borrower where cardNo = ?", new Object[] {borrower.getCardNo()});
	}
	
	public List<Borrower> readBorrowers() throws SQLException {
		return read("select * from tbl_borrower", null);
	}
	
	@Override
	List<Borrower> extractData(ResultSet rs) throws SQLException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		
		return borrowers;
	}

}
