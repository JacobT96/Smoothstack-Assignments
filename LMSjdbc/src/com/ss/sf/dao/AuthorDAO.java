package com.ss.sf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ss.lms.domain.Author;

/**
 * @author Jacob
 *
 */
public class AuthorDAO extends BaseDAO<Author> {
	
	public void addAuthor(Author author) throws SQLException {
		save("insert into tbl_author (authorName, authorId) values (?, ?)",
			new Object[] {author.getAuthorName(), readAuthors().size()});
	}
	
	public void updateAuthor(Author author) throws SQLException {
		save("update tbl_author set authorName = ? where authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
	}
	
	public void deleteAuthor(Author author) throws SQLException {
		save("delete from tbl_author where authorId = ?", new Object[] {author.getAuthorId()});
	}
	
	public List<Author> readAuthors() throws SQLException {
		return read("select * from tbl_author", null);
	}
	
	public List<Author> readAuthorsById(int authorId) throws SQLException {
		return read("select * from tbl_author where authorId = ?", new Object[] {authorId});
	}

	@Override
	List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		
		return authors;
	}
}
