package com.ss.sf.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

/**
 * @author Jacob
 *
 */
public class MongoBaseDAO {
	
	private MongoClient client;
	
	public MongoBaseDAO() {
		client = new MongoClient("localhost", 27017);
	}
	
	private MongoDatabase getDatabase() {
		return client.getDatabase("library");
	}
	
	public void add(String tbl, Document doc, String primaryKey) {
		List<Document> docs = read(tbl);
		int maxId = 0;
		for (Document document : docs) {
			int id = document.getInteger(primaryKey);
			if (id > maxId)
				maxId = id; 
		}
		doc.append(primaryKey, maxId+1);
		
		getDatabase().getCollection(tbl).insertOne(doc);
	}
	
	public void update(String tbl, Document doc, String primaryKey) {
		getDatabase().getCollection(tbl).replaceOne(Filters.eq(primaryKey, doc.getInteger(primaryKey)), doc);
	}
	
	public void delete(String tbl, Document doc, String primaryKey) {
		getDatabase().getCollection(tbl).deleteOne(Filters.eq(primaryKey, doc.getInteger(primaryKey)));
	}
	
	public List<Document> read(String tbl) {
		Iterator it = getDatabase().getCollection(tbl).find().iterator();
		List<Document> list = new ArrayList<>();
		while (it.hasNext()) {
			list.add((Document) it.next());
		}
		return list;
	}
}
