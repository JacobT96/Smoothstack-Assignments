package com.ss.lms.jdbc;

import java.util.List;
import java.util.Scanner;

import org.bson.Document;

import com.ss.sf.dao.MongoBaseDAO;


/**
 * @author Jacob
 *
 */
public class MongoService {
	
	private MongoBaseDAO DAO;
	
	public MongoService() {
		DAO = new MongoBaseDAO();
	}
	
	public void mainmenu(Scanner sc) {
		while (true) {
			System.out.println("1) Add/Update/Delete Books");
			System.out.println("2) Add/Update/Delete Authors");
			System.out.println("3) Add/Update/Delete Publishers");
			int input = Integer.parseInt(sc.nextLine());
			switch (input) {
			case 1:
				manageBooks(sc);
				break;
			case 2:
				manageAuthors(sc);
				break;
			case 3:
				managePublishers(sc);
				break;
			}
		}
	}
	
	private void manageBooks(Scanner sc) {
		System.out.println("1) Add Book");
		System.out.println("2) Update Book");
		System.out.println("3) Delete Book");
		int input = Integer.parseInt(sc.nextLine());
		
		switch (input) {
		case 1:
			addBook(sc);
			break;
		case 2:
			updateBook(sc);
			break;
		case 3:
			deleteBook(sc);
			break;
		}
	}
	
	private void addBook(Scanner sc) {
		System.out.println("Enter book title:");
		String title = sc.nextLine();
		
		List<Document> authors = DAO.read("authors");
		System.out.println("Select an author:");
		for (int i=0; i<authors.size(); i++) {
			Document author = authors.get(i);
			System.out.println(i+1 + ") " + author.getString("authorName"));
		}
		int input = Integer.parseInt(sc.nextLine());
		Document chosenAuthor = authors.get(input-1);
		
		List<Document> publishers = DAO.read("publishers");
		System.out.println("Select a publisher:");
		for (int i=0; i<publishers.size(); i++) {
			Document publisher = publishers.get(i);
			System.out.println(i+1 + ") " + publisher.getString("publisherName"));
		}
		input = Integer.parseInt(sc.nextLine());
		Document chosenPub = publishers.get(input-1);
		
		Document book = new Document("title", title)
				.append("authId", chosenAuthor.getInteger("authorId"))
				.append("pubId", chosenPub.getInteger("publisherId"));
		DAO.add("books", book, "bookId");
		System.out.println("Book " + title + " added.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void updateBook(Scanner sc) {
		System.out.println("Which book to update:");
		List<Document> books = DAO.read("books");
		for (int i=0; i<books.size(); i++) {
			Document book = books.get(i);
			System.out.println(i+1 + ") " + book.getString("title"));
		}
		int input = Integer.parseInt(sc.nextLine());
		Document chosenBook = books.get(input-1);
		
		System.out.println("Enter a new title or press N/A to skip:");
		String title = sc.nextLine();
		if (!title.toLowerCase().equals("n/a"))
			chosenBook.replace("title", title);
		
		List<Document> authors = DAO.read("authors");
		System.out.println("Select a new author or N/A to skip:");
		for (int i=0; i<authors.size(); i++) {
			Document auth = authors.get(i);
			System.out.println(i+1 + ") " + auth.getString("authorName"));
		}
		System.out.println(authors.size()+1 + ") N/A");
		input = Integer.parseInt(sc.nextLine());
		
		if (input != authors.size()+1) {
			Document chosenAuth = authors.get(input-1);
			chosenBook.replace("authId", chosenAuth.getInteger("authorId"));
		}
		
		List<Document> pubs = DAO.read("publishers");
		System.out.println("Select a new publisher or N/A to skip:");
		for (int i=0; i<pubs.size(); i++) {
			Document pub = pubs.get(i);
			System.out.println(i+1 + ") " + pub.getString("publisherName"));
		}
		System.out.println(pubs.size()+1 + ") N/A");
		input = Integer.parseInt(sc.nextLine());
		
		if (input != pubs.size()+1) {
			Document chosenPub = pubs.get(input-1);
			chosenBook.replace("pubId", chosenPub.getInteger("publisherId"));
		}
		
		DAO.update("books", chosenBook, "bookId");
		System.out.println("Book updated.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void deleteBook(Scanner sc) {
		System.out.println("Which book to delete:");
		List<Document> books = DAO.read("books");
		for (int i=0; i<books.size(); i++) {
			Document book = books.get(i);
			System.out.println(i+1 + ") " + book.getString("title"));
		}
		int input = Integer.parseInt(sc.nextLine());
		Document chosenBook = books.get(input-1);
		
		DAO.delete("books", chosenBook, "bookId");
		System.out.println("Book " + chosenBook.getString("title") + " deleted.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void manageAuthors(Scanner sc) {
		System.out.println("1) Add Author");
		System.out.println("2) Update Author");
		System.out.println("3) Delete Author");
		int input = Integer.parseInt(sc.nextLine());
		
		switch (input) {
		case 1:
			addAuthor(sc);
			break;
		case 2:
			updateAuthor(sc);
			break;
		case 3:
			deleteAuthor(sc);
			break;
		}
	}
	
	private void addAuthor(Scanner sc) {
		System.out.println("Enter author name:");
		String name = sc.nextLine();
		Document author = new Document("authorName", name);
		DAO.add("authors", author, "authorId");
		System.out.println("Author " + name + " added.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void updateAuthor(Scanner sc) {
		System.out.println("Which author to update:");
		List<Document> authors = DAO.read("authors");
		for (int i=0; i<authors.size(); i++) {
			Document author = authors.get(i);
			System.out.println(i+1 + ") " + author.getString("authorName"));
		}
		int input = Integer.parseInt(sc.nextLine());
		Document chosenAuth = authors.get(input-1);
		
		System.out.println("Enter a new name for " + chosenAuth.getString("authorName") + ":");
		String name = sc.nextLine();
		chosenAuth.replace("authorName", name);
		
		DAO.update("authors", chosenAuth, "authorId");
		System.out.println("Author updated.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void deleteAuthor(Scanner sc) {
		System.out.println("Which author to delete:");
		List<Document> authors = DAO.read("authors");
		for (int i=0; i<authors.size(); i++) {
			Document author = authors.get(i);
			System.out.println(i+1 + ") " + author.getString("authorName"));
		}
		int input = Integer.parseInt(sc.nextLine());
		Document chosenAuth = authors.get(input-1);
		
		DAO.delete("authors", chosenAuth, "authorId");
		System.out.println("Author " + chosenAuth.getString("authorName") + " deleted.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void managePublishers(Scanner sc) {
		System.out.println("1) Add Publisher");
		System.out.println("2) Update Publisher");
		System.out.println("3) Delete Publisher");
		int input = Integer.parseInt(sc.nextLine());
		
		switch (input) {
		case 1:
			addPublisher(sc);
			break;
		case 2:
			updatePublisher(sc);
			break;
		case 3:
			deletePublisher(sc);
			break;
		}
	}
	
	private void addPublisher(Scanner sc) {
		System.out.println("Enter publisher name:");
		String name = sc.nextLine();
		System.out.println("Enter publisher address:");
		String address = sc.nextLine();
		System.out.println("Enter publisher phone number:");
		String phone = sc.nextLine();
		
		Document pub = new Document("publisherName", name)
				.append("publisherAddress", address)
				.append("publisherPhone", phone);
		
		DAO.add("publishers", pub, "publisherId");
		System.out.println("Publisher " + name + " added.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void updatePublisher(Scanner sc) {
		System.out.println("Which publisher to update:");
		List<Document> pubs = DAO.read("publishers");
		for (int i=0; i<pubs.size(); i++) {
			Document pub = pubs.get(i);
			System.out.println(i+1 + ") " + pub.getString("publisherName"));
		}
		int input = Integer.parseInt(sc.nextLine());
		Document chosenPub = pubs.get(input-1);
		
		System.out.println("Enter a new name for " + chosenPub.getString("publisherName") + " or N/A to skip:");
		String name = sc.nextLine();
		if (!name.toLowerCase().equals("n/a")) {
			chosenPub.replace("publisherName", name);
		}
		
		System.out.println("Current address is " + chosenPub.getString("publisherAddress") + ".");
		System.out.println("Enter a new address or N/A to skip:");
		String address = sc.nextLine();
		if (!address.toLowerCase().equals("n/a")) {
			chosenPub.replace("publisherAddress", address);
		}
		
		System.out.println("Current phone number is " + chosenPub.getString("publisherPhone") + ".");
		System.out.println("Enter a new number or N/A to skip:");
		String phone = sc.nextLine();
		if (!phone.toLowerCase().equals("n/a")) {
			chosenPub.replace("publisherPhone", phone);
		}
		
		DAO.update("publishers", chosenPub, "publisherId");
		System.out.println("Publisher updated.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void deletePublisher(Scanner sc) {
		System.out.println("Which publisher to delete:");
		List<Document> pubs = DAO.read("publishers");
		for (int i=0; i<pubs.size(); i++) {
			Document pub = pubs.get(i);
			System.out.println(i+1 + ") " + pub.getString("publisherName"));
		}
		
		int input = Integer.parseInt(sc.nextLine());
		Document chosenPub = pubs.get(input-1);
		DAO.delete("publishers", chosenPub, "publisherId");
		System.out.println("Publisher " + chosenPub.getString("publisherName") + " deleted.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		MongoService service = new MongoService();
		service.mainmenu(sc);
	}

}