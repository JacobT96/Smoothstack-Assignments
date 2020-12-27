package com.ss.sf.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Jacob
 *
 */
public abstract class BaseDAO<T> {
	public static String url = "jdbc:mysql://localhost:3306/library";
	public static String username = "root";
	public static String password = "root";
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
	
	public void save(String sql, Object[] vals) throws SQLException {
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		int count = 1;
		for (Object o : vals) {
			pstmt.setObject(count, o);
			count++;
		}
		pstmt.executeUpdate();
	}
	
	public List<T> read(String sql, Object[] vals) throws SQLException {
		PreparedStatement pstmt = getConnection().prepareStatement(sql);
		int count = 1;
		if (vals != null) {
			for (Object o : vals) {
				pstmt.setObject(count, o);
				count++;
			}
		}
		
		ResultSet rs = pstmt.executeQuery();
		return extractData(rs);
	}
	
	abstract List<T> extractData(ResultSet rs) throws SQLException;
}
