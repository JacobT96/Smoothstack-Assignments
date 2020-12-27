package com.ss.sf.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.domain.Publisher;

/**
 * @author Jacob
 *
 */
public class PublisherDAO extends BaseDAO<Publisher> {

	public void addPublisher(Publisher publisher) throws SQLException {
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone, publisherId) values (?, ?, ?, ?)",
			new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), readPublishers().size()});
	}
	
	public void updatePublisher(Publisher publisher) throws SQLException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
			new Object[] {publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId()});
	}
	
	public void deletePublisher(Publisher publisher) throws SQLException {
		save("delete from tbl_publisher where publisherId = ?", new Object[] {publisher.getPublisherId()});
	}
	
	public List<Publisher> readPublishers() throws SQLException {
		return read("select * from tbl_publisher", null);
	}
	
	public List<Publisher> readPublishersById(int publisherId) throws SQLException {
		return read("select * from tbl_publisher where publisherId = ?", new Object[] {publisherId});
	}
	
	@Override
	List<Publisher> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPublisherName(rs.getString("publisherName"));
			publisher.setPublisherAddress(rs.getString("publisherAddress"));
			publisher.setPublisherPhone(rs.getString("publisherPhone"));
			publishers.add(publisher);
		}
		
		return publishers;
	}
	
}
