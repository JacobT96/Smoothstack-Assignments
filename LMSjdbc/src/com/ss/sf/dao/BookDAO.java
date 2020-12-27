package com.ss.sf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.Book;

/**
 * @author Jacob
 *
 */
public class BookDAO extends BaseDAO<Book> {

	public void addBook(Book book) throws SQLException {
		save("insert into tbl_book (title, authId, pubId, bookId) values (?, ?, ?, ?)",
			new Object[] {book.getTitle(), book.getAuthId(), book.getPubId(), readBooks().size()});
	}
	
	public void updateBook(Book book) throws SQLException {
		save("update tbl_book set title = ?, authId = ?, pubId = ? where bookId = ?",
			new Object[] {book.getTitle(), book.getAuthId(), book.getPubId(), book.getBookId()});
	}
	
	public void deleteBook(Book book) throws SQLException {
		save("delete from tbl_book where bookId = ?", new Object[] {book.getBookId()});
	}
	
	public List<Book> readBooks() throws SQLException {
		return read("select * from tbl_book", null);
	}
	
	public List<Book> readBooksById(int bookId) throws SQLException {
		return read("select * from tbl_book where bookId = ?", new Object[] {bookId});
	}
	
	public List<Book> readBooksByAuthor(int authorId) throws SQLException {
		return read("select b.* from tbl_book b, tbl_author a where b.authId = a.authorId and b.authId = ?",
			new Object[] {authorId});
	}
	
	@Override
	List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setAuthId(rs.getInt("authId"));
			book.setPubId(rs.getInt("pubId"));
			books.add(book);
		}
		
		return books;
	}

}
