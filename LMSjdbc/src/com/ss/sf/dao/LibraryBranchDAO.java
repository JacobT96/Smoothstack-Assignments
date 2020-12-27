package com.ss.sf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.LibraryBranch;

/**
 * @author Jacob
 *
 */
public class LibraryBranchDAO extends BaseDAO<LibraryBranch> {

	public void addBranch(LibraryBranch branch) throws SQLException {
		save("insert into tbl_library_branch (branchName, branchAddress, branchId) values (?, ?, ?)",
			new Object[] {branch.getBranchName(), branch.getBranchAddress(), readBranches().size()});
	}
	
	public void updateBranch(LibraryBranch branch) throws SQLException {
		save("update tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?",
			new Object[] {branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId()});
	}
	
	public void deleteBranch(LibraryBranch branch) throws SQLException {
		save("delete from tbl_library_branch where branchId = ?", new Object[] {branch.getBranchId()});
	}
	
	public List<LibraryBranch> readBranches() throws SQLException {
		return read("select * from tbl_library_branch", null);
	}
	
	public List<LibraryBranch> readBranchesById(int branchId) throws SQLException {
		return read("select * from tbl_library_branch where branchId = ?", new Object[] {branchId});
	}
	
	@Override
	List<LibraryBranch> extractData(ResultSet rs) throws SQLException {
		List<LibraryBranch> branches = new ArrayList<>();
		while (rs.next()) {
			LibraryBranch branch = new LibraryBranch();
			branch.setBranchId(rs.getInt("branchId"));
			branch.setBranchName(rs.getString("branchName"));
			branch.setBranchAddress(rs.getString("branchAddress"));
			branches.add(branch);
		}
		
		return branches;
	}
}
