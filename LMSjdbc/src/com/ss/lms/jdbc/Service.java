package com.ss.lms.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;

import com.ss.lms.domain.*;
import com.ss.sf.dao.*;

/**
 * @author Jacob
 *
 */
public class Service {
	
	private AuthorDAO authDAO;
	private BookDAO bookDAO;
	private BorrowerDAO borrowerDAO;
	private PublisherDAO publisherDAO;
	private LibraryBranchDAO libraryDAO;
	private BookCopiesDAO bcDAO;
	private BookLoansDAO loansDAO;
	
	public Service() {
		authDAO = new AuthorDAO();
		bookDAO = new BookDAO();
		borrowerDAO = new BorrowerDAO();
		publisherDAO = new PublisherDAO();
		libraryDAO = new LibraryBranchDAO();
		bcDAO = new BookCopiesDAO();
		loansDAO = new BookLoansDAO();
	}
	
	// Handle exceptions when user input needs to be an int.
	private int getIntInput(Scanner sc) {
		int input = 0;
		try {
			input = Integer.parseInt(sc.nextLine());
		}
		catch (NumberFormatException e) {
			input = 0;
		}
		return input;
	}

	public int mainmenu(Scanner sc) {
		while (true) {
			int input = 0;
			System.out.println("Welcome to the SS Library Management System. Which category of user are you");
			do {
				System.out.println("1) Librarian");
				System.out.println("2) Administrator");
				System.out.println("3) Borrower");
				input = getIntInput(sc);
			} while (input != 1 && input != 2 && input != 3);
			switch (input) {
			case 1:
				//librarian
				lib1(sc);
				break;
			case 2:
				//administrator
				admin1(sc);
				break;
			case 3:
				//borrower
				takeCardNo(sc);
				break;
			default:
				return 0;
			}
		}
	}
	
	public int lib1(Scanner sc) {
		int input = 0;
		do {
			System.out.println("1) Enter Branch you manage");
			System.out.println("2) Quit to previous");
			input = getIntInput(sc);
		} while (input != 1 && input != 2);
		
		switch (input) {
		case 1:
			return lib2(sc);
		case 2:
			return 0; //main menu
		default:
			return 0;
		}
	}
	
	public int lib2(Scanner sc) {
		try {
			List<LibraryBranch> branches = libraryDAO.readBranches();
			System.out.println("Enter the Branch you manage:");
			for (int i=0; i<branches.size(); i++) {
				System.out.println(i+1 + ") " + branches.get(i));
			}
			System.out.println(branches.size()+1 + ") Quit to previous");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > branches.size()+1);
			
			if (input == branches.size() + 1) {
				return lib1(sc);
			}
			else {
				LibraryBranch lb = branches.get(input-1);
				return lib3(lb, sc);
			}
			
		} catch (SQLException e) {
			System.err.println("We're sorry, we don't have any data for library branches.");
		}
		
		return 0;
	}
	
	public int lib3(LibraryBranch branch, Scanner sc) {
		int input = 0;
		do {
			System.out.println("1) Update the details of the Library");
			System.out.println("2) Add copies of Book to the Branch");
			System.out.println("3) Quit to previous");
			input = getIntInput(sc);
		} while (input != 1 && input != 2 && input != 3);
		
		switch (input) {
		case 1:
			return lib4(branch, sc);
		case 2:
			return lib5(branch, sc);
		case 3:
			return lib2(sc);
		default:
			return 0;
		}
	}
	
	public int lib4(LibraryBranch branch, Scanner sc) {
		System.out.println("You have chosen to update the Branch with Branch Id: " + branch.getBranchId() + " and Branch Name: " + branch.getBranchName() + ".");
		System.out.println("Enter 'quit' at any prompt to cancel operation.");
		boolean anyChanges = false;
		
		System.out.println("\nPlease enter new branch name or enter N/A for no change:");
		String name = sc.nextLine();
		if (name.toLowerCase().equals("quit")) {
			return lib3(branch, sc);
		}
		else if (!name.toLowerCase().equals("n/a")) {
			branch.setBranchName(name);
			anyChanges = true;
		}
		
		System.out.println("Please enter new branch address or enter N/A for no change:");
		String address = sc.nextLine();
		if (address.toLowerCase().equals("quit")) {
			return lib3(branch, sc);
		}
		else if (!address.toLowerCase().equals("n/a")) {
			branch.setBranchAddress(address);
			anyChanges = true;
		}
		
		try {
			libraryDAO.updateBranch(branch);
			if (anyChanges)
				System.out.println("Successfully updated.");
			else
				System.out.println("No changes made.");
		} catch (SQLException e) {
			System.err.println("Could not update branch.");
		}
		
		return lib3(branch, sc);
	}
	
	public int lib5(LibraryBranch branch, Scanner sc) {
		try {
			List<Book> books = bookDAO.readBooks();
			System.out.println("Pick the Book you want to add copies of to your branch:");
			
			for (int i=0; i<books.size(); i++) {
				System.out.println(i+1 + ") " + books.get(i).toString());
			}
			System.out.println(books.size()+1 + ") Quit to cancel operation");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > books.size()+1);
			
			if (input == books.size()+1) {
				return lib3(branch, sc);
			}
			
			Book book = books.get(input-1);
			List<BookCopies> copies = bcDAO.readCopiesByBookAndBranch(book.getBookId(), branch.getBranchId());
			BookCopies copy = null;
			
			int num = 0;
			if (!copies.isEmpty()) {
				copy = copies.get(0);
				num = copy.getNoOfCopies();
			}
			else {
				copy = new BookCopies();
				copy.setBookId(book.getBookId());
				copy.setBranchId(branch.getBranchId());
				copy.setNoOfCopies(0);
			}
			
			System.out.println("Existing number of copies: " + num);
			System.out.println("\nEnter new number of copies:");
			int newnum = getIntInput(sc);
			copy.setNoOfCopies(newnum);
			
			if (num == 0) {
				bcDAO.addCopies(copy);
			}
			else {
				bcDAO.updateCopies(copy);
			}
			
			System.out.println("book_copies tbl updated.");
			return lib3(branch, sc);
			
		} catch (SQLException e) {
			System.err.println("Could not retrieve book data.");
		}
		
		return 0;
	}
	
	public int takeCardNo(Scanner sc) {
		try {
			List<Borrower> borrs = borrowerDAO.readBorrowers();
			List<Integer> cards = new ArrayList<>();
			for (Borrower borr : borrs) {
				cards.add(borr.getCardNo());
			}
			
			int input = -1;
			do {
				System.out.println("Enter your Card Number:");
				input = getIntInput(sc);
			} while (!cards.contains(input));
			
			return borr1(input, sc);
			
		} catch (SQLException e) {
			System.err.println("Could not retrieve borrower data.");
		}
		
		return 0;
	}
	
	public int borr1(int cardNo, Scanner sc) {
		System.out.println("1) Check out a book");
		System.out.println("2) Return a book");
		System.out.println("3) Quit to previous");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input != 1 && input != 2 && input != 3);
		
		switch (input) {
		case 1:
			return borr2(cardNo, sc);
		case 2:
			return borr4(cardNo, sc);
		case 3:
			return 0; //mainmenu
		default:
			return 0;
		}
	}
	
	public int borr2(int cardNo, Scanner sc) {
		System.out.println("Pick the Branch you want to check out from:");
		try {
			List<LibraryBranch> branches = libraryDAO.readBranches();
			
			for (int i=0; i<branches.size(); i++) {
				System.out.println(i+1 + ") " + branches.get(i).toString());
			}
			System.out.println(branches.size()+1 + ") Quit to previous");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > branches.size()+1);
			
			if (input == branches.size()+1) {
				return borr1(cardNo, sc);
			}
			
			return borr3(cardNo, branches.get(input-1), sc);
			
		} catch (SQLException e) {
			System.err.println("Could not retrieve library data.");
		}
		return 0;
	}
	
	public int borr3(int cardNo, LibraryBranch branch, Scanner sc) {
		try {
			List<BookCopies> copies = bcDAO.readCopiesByBranch(branch.getBranchId());
			List<Book> availableBooks = new ArrayList<>();
			
			System.out.println("Pick the Book you want to check out:");
			for (int i=0; i<copies.size(); i++) {
				Book book = bookDAO.readBooksById(copies.get(i).getBookId()).get(0);
				availableBooks.add(book);
				System.out.println(i+1 + ") " + book.toString());
			}
			System.out.println(availableBooks.size()+1 + ") Quit to cancel operation");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > availableBooks.size()+1);
			
			if (input == availableBooks.size()+1) {
				return borr1(cardNo, sc);
			}
			
			Book chosenBook = availableBooks.get(input-1);
			BookLoans loan = new BookLoans();
			loan.setBookId(chosenBook.getBookId());
			loan.setBranchId(branch.getBranchId());
			loan.setCardNo(cardNo);
			LocalDateTime dateOut = LocalDateTime.now();
			loan.setDateOut(dateOut);
			loan.setDueDate(dateOut.plusWeeks(1));
			loansDAO.addLoan(loan);
			
			System.out.println("Loan added.");
			return borr1(cardNo, sc);
			
		} catch (SQLException e) {
			System.err.println("Could not retrieve data for this branch.");
		}
		
		return 0;
	}
	
	public int borr4(int cardNo, Scanner sc) {
		try {
			List<BookLoans> loans = loansDAO.readLoansByCardNo(cardNo);
			List<LibraryBranch> branches = new ArrayList<>();
			
			for (int i=0; i<loans.size(); i++) {
				int branchId = loans.get(i).getBranchId();
				LibraryBranch branch = libraryDAO.readBranchesById(branchId).get(0);
				if (!branches.contains(branch))
					branches.add(branch);
			}
			
			System.out.println("Pick the Branch you want to return to:");
			for (int i=0; i<branches.size(); i++) {
				System.out.println(i+1 + ") " + branches.get(i).toString());
			}
			System.out.println(branches.size()+1 + ") Quit to previous");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > branches.size()+1);
			
			if (input == branches.size()+1) {
				return borr1(cardNo, sc);
			}
			
			LibraryBranch branch = branches.get(input-1);
			return borr5(cardNo, branch, sc);
			
		} catch (SQLException e) {
			System.err.println("Error managing loans.");
		}
		
		return 0;
	}
	
	public int borr5(int cardNo, LibraryBranch branch, Scanner sc) {
		try {
			List<BookLoans> loans = loansDAO.readLoansByCardNoAndBranch(cardNo, branch.getBranchId());
			List<Book> books = new ArrayList<>();
			
			System.out.println("Pick the book you want to return:");
			for (int i=0; i<loans.size(); i++) {
				BookLoans loan = loans.get(i);
				Book book = bookDAO.readBooksById(loan.getBookId()).get(0);
				books.add(book);
				System.out.println(i+1 + ") " + book.toString() + ", due on " + loan.getDueDate());
			}
			System.out.println(books.size()+1 + ") Quit to cancel operation");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > books.size()+1);
			
			if (input == books.size()+1) {
				return borr1(cardNo, sc);
			}
			
			BookLoans chosenLoan = loans.get(input-1);
			loansDAO.deleteLoan(chosenLoan);
			System.out.println("Book returned successfully.");
			return borr1(cardNo, sc);
			
		} catch (SQLException e) {
			System.err.println("Error finding books.");
		}
		
		return 0;
	}
	
	public int admin1(Scanner sc) {
		System.out.println("1) Add/Update/Delete/Read Books");
		System.out.println("2) Add/Update/Delete/Read Authors");
		System.out.println("3) Add/Update/Delete/Read Publishers");
		System.out.println("4) Add/Update/Delete/Read Library Branches");
		System.out.println("5) Add/Update/Delete/Read Borrowers");
		System.out.println("6) Override Due Date for a Book Loan");
		System.out.println("7) Quit to previous");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > 7);
		
		switch (input) {
		case 1:
			return manageBooks(sc);
		case 2:
			return manageAuthors(sc);
		case 3:
			return managePublishers(sc);
		case 4:
			return manageBranches(sc);
		case 5:
			return manageBorrowers(sc);
		case 6:
			return overrideLoan(sc);
		case 7:
			return 0; //mainmenu
		default:
			return 0;
		}
	}
	
	public int manageBooks(Scanner sc) {
		while (true) {
			System.out.println("1) Add book");
			System.out.println("2) Update book");
			System.out.println("3) Delete book");
			System.out.println("4) Read books");
			System.out.println("5) Quit to previous");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > 5);
			
			try {
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
				case 4:
					displayBooks(sc);
					break;
				case 5:
					return admin1(sc);
				default:
					return 0;
				}
			} catch (SQLException e) {
				System.err.println("Operation could not be performed.");
				return 0;
			}
		}
	}
	
	private void addBook(Scanner sc) throws SQLException {
		System.out.println("Enter or select 'quit' at any prompt to cancel the operation.");
		System.out.println("Enter book title:");
		String title = sc.nextLine();
		if (title.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		List<Author> authors = authDAO.readAuthors();
		
		System.out.println("Pick an author:");
		for (int i=0; i<authors.size(); i++) {
			System.out.println(i+1 + ") " + authors.get(i).toString());
		}
		System.out.println(authors.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > authors.size()+1);
		
		if (input == authors.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Author chosenAuthor = authors.get(input-1);
		
		List<Publisher> pubs = publisherDAO.readPublishers();
		
		System.out.println("Pick a publisher:");
		for (int i=0; i<pubs.size(); i++) {
			System.out.println(i+1 + ") " + pubs.get(i).toString());
		}
		System.out.println(pubs.size()+1 + ") Quit");
		
		input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > pubs.size()+1);
		
		if (input == pubs.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Publisher chosenPub = pubs.get(input-1);
		
		Book book = new Book();
		book.setTitle(title);
		book.setAuthId(chosenAuthor.getAuthorId());
		book.setPubId(chosenPub.getPublisherId());
		bookDAO.addBook(book);
		System.out.println("Book " + book.toString() + " added.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void updateBook(Scanner sc) throws SQLException {
		List<Book> books = bookDAO.readBooks();
		System.out.println("Enter or select 'quit' at any prompt to cancel the operation.");
		System.out.println("Which book to update:");
		for (int i=0; i<books.size(); i++) {
			System.out.println(i+1 + ") " + books.get(i).toString());
		}
		System.out.println(books.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > books.size()+1);
		
		if (input == books.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Book chosenBook = books.get(input-1);
		
		String oldTitle = chosenBook.getTitle();
		System.out.println("Enter a new title for " + oldTitle + ", or enter N/A to skip:");
		String title = sc.nextLine();
		
		if (title.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (!title.toLowerCase().equals("n/a")) {
			chosenBook.setTitle(title);
		}
		
		List<Author> authors = authDAO.readAuthors();
		Author oldAuthor = authDAO.readAuthorsById(chosenBook.getAuthId()).get(0);
		System.out.println("Current author is " + oldAuthor.toString() + ".");
		int last = authors.size() + 1;
		System.out.println("Choose a new author or select " + last + " to skip:");
		for (int i=0; i<authors.size(); i++) {
			System.out.println(i+1 + ") " + authors.get(i).getAuthorName());
		}
		System.out.println(last + ") N/A");
		System.out.println(last+1 + ") Quit");
		
		input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > last+1);
		
		if (input == last+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (input != last) {
			Author chosenAuthor = authors.get(input-1);
			chosenBook.setAuthId(chosenAuthor.getAuthorId());
		}
		
		List<Publisher> pubs = publisherDAO.readPublishers();
		Publisher oldPub = publisherDAO.readPublishersById(chosenBook.getPubId()).get(0);
		System.out.println("Current publisher is " + oldPub.toString() + ".");
		last = pubs.size() + 1;
		System.out.println("Choose a new publisher or select " + last + " to skip:");
		for (int i=0; i<pubs.size(); i++) {
			System.out.println(i+1 + ") " + pubs.get(i).getPublisherName());
		}
		System.out.println(last + ") N/A");
		System.out.println(last+1 + ") Quit");
		
		input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > last+1);
		
		if (input == last+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (input != last) {
			Publisher chosenPub = pubs.get(input-1);
			chosenBook.setPubId(chosenPub.getPublisherId());
		}
		
		bookDAO.updateBook(chosenBook);
		System.out.println("Book updated.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void deleteBook(Scanner sc) throws SQLException {
		List<Book> books = bookDAO.readBooks();
		System.out.println("Select 'quit' to cancel the operation.");
		System.out.println("Which book to delete:");
		for (int i=0; i<books.size(); i++) {
			System.out.println(i+1 + ") " + books.get(i).toString());
		}
		System.out.println(books.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > books.size()+1);
		
		if (input == books.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Book chosenBook = books.get(input-1);
		bookDAO.deleteBook(chosenBook);
		System.out.println("Book " + chosenBook.getTitle() + " deleted.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void displayBooks(Scanner sc) throws SQLException {
		List<Book> books = bookDAO.readBooks();
		for (Book book : books) {
			System.out.println(book.getTitle());
			Author author = authDAO.readAuthorsById(book.getAuthId()).get(0);
			System.out.println("Written by " + author.getAuthorName());
			Publisher publisher = publisherDAO.readPublishersById(book.getPubId()).get(0);
			System.out.println("Published by " + publisher.getPublisherName());
			System.out.println("-------------");
		}
		
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	public int manageAuthors(Scanner sc) {
		while (true) {
			System.out.println("1) Add author");
			System.out.println("2) Update author");
			System.out.println("3) Delete author");
			System.out.println("4) Read authors");
			System.out.println("5) Quit to previous");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > 5);
			
			try {
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
				case 4:
					displayAuthors(sc);
					break;
				case 5:
					return admin1(sc);
				default:
					return 0;
				}
			} catch (SQLException e) {
				System.err.println("Operation could not be performed.");
				return 0;
			}
		}
	}

	private void addAuthor(Scanner sc) throws SQLException {
		System.out.println("Enter 'quit' to cancel the operation.");
		System.out.println("Enter author name:");
		String name = sc.nextLine();
		
		if (name.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Author author = new Author();
		author.setAuthorName(name);
		authDAO.addAuthor(author);
		System.out.println("Author " + name + " added.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void updateAuthor(Scanner sc) throws SQLException {
		List<Author> authors = authDAO.readAuthors();
		System.out.println("Enter or select 'quit' at any prompt to cancel the operation.");
		System.out.println("Which author to update:");
		for (int i=0; i<authors.size(); i++) {
			System.out.println(i+1 + ") " + authors.get(i).toString());
		}
		System.out.println(authors.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > authors.size()+1);
		
		if (input == authors.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Author chosenAuthor = authors.get(input-1);
		String oldName = chosenAuthor.toString();
		System.out.println("Enter a new name for " + oldName + ":");
		String name = sc.nextLine();
		
		if (name.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		chosenAuthor.setAuthorName(name);
		authDAO.updateAuthor(chosenAuthor);
		System.out.println("Author " + oldName + " changed to " + name + ".");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void deleteAuthor(Scanner sc) throws SQLException {
		List<Author> authors = authDAO.readAuthors();
		System.out.println("Select 'quit' to cancel the operation.");
		System.out.println("Which author to delete:");
		for (int i=0; i<authors.size(); i++) {
			System.out.println(i+1 + ") " + authors.get(i).toString());
		}
		System.out.println(authors.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > authors.size()+1);
		
		if (input == authors.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Author chosenAuthor = authors.get(input-1);
		authDAO.deleteAuthor(chosenAuthor);
		System.out.println("Author " + chosenAuthor.getAuthorName() + " deleted.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void displayAuthors(Scanner sc) throws SQLException {
		List<Author> authors = authDAO.readAuthors();
		for (Author auth : authors) {
			System.out.println(auth.toString());
			System.out.println("Books on record:");
			List<Book> books = bookDAO.readBooksByAuthor(auth.getAuthorId());
			for (Book b : books) {
				System.out.println(b.getTitle());
				System.out.println("-");
			}
			System.out.println("-------------");
		}
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	public int managePublishers(Scanner sc) {
		while (true) {
			System.out.println("1) Add publisher");
			System.out.println("2) Update publisher");
			System.out.println("3) Delete publisher");
			System.out.println("4) Read publishers");
			System.out.println("5) Quit to previous");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > 5);
			
			try {
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
				case 4:
					displayPublishers(sc);
					break;
				case 5:
					return admin1(sc);
				default:
					return 0;
				}
			} catch (SQLException e) {
				System.err.println("Operation could not be performed.");
				return 0;
			}
		}
	}
	
	private void addPublisher(Scanner sc) throws SQLException {
		System.out.println("Enter 'quit' at any prompt to cancel the operation.");
		System.out.println("Enter publisher name:");
		String name = sc.nextLine();
		
		if (name.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		System.out.println("Enter " + name + "'s address:");
		String address = sc.nextLine();
		
		if (address.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		System.out.println("Enter " + name + "'s phone number:");
		String phoneNumber = sc.nextLine();
		
		if (phoneNumber.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Publisher publisher = new Publisher();
		publisher.setPublisherName(name);
		publisher.setPublisherAddress(address);
		publisher.setPublisherPhone(phoneNumber);
		publisherDAO.addPublisher(publisher);
		System.out.println("Publisher " + name + " added.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void updatePublisher(Scanner sc) throws SQLException {
		List<Publisher> publishers = publisherDAO.readPublishers();
		System.out.println("Enter or select 'quit' at any prompt to cancel the operation.");
		System.out.println("Which publisher to update:");
		for (int i=0; i<publishers.size(); i++) {
			System.out.println(i+1 + ") " + publishers.get(i).toString());
		}
		System.out.println(publishers.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > publishers.size()+1);
		
		if (input == publishers.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Publisher p = publishers.get(input-1);
		
		String oldName = p.getPublisherName();
		System.out.println("Enter a new name for " + oldName + ", or enter N/A to skip:");
		String name = sc.nextLine();
		
		if (name.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (!name.toLowerCase().equals("n/a")) {
			p.setPublisherName(name);
		}
		
		System.out.println("Current address is " + p.getPublisherAddress());
		System.out.println("Enter a new address, or enter N/A to skip:");
		String address = sc.nextLine();
		
		if (address.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (!address.toLowerCase().equals("n/a")) {
			p.setPublisherAddress(address);
		}
		
		System.out.println("Current phone number is " + p.getPublisherPhone());
		System.out.println("Enter a new phone number, or enter N/A to skip:");
		String phone = sc.nextLine();
		
		if (phone.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (!phone.toLowerCase().equals("n/a")) {
			p.setPublisherPhone(phone);
		}
		
		publisherDAO.updatePublisher(p);
		System.out.println("Publisher updated.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void deletePublisher(Scanner sc) throws SQLException {
		List<Publisher> publishers = publisherDAO.readPublishers();
		System.out.println("Select 'quit' to cancel operation.");
		System.out.println("Which publisher to delete:");
		for (int i=0; i<publishers.size(); i++) {
			System.out.println(i+1 + ") " + publishers.get(i).toString());
		}
		System.out.println(publishers.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > publishers.size()+1);
		
		if (input == publishers.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Publisher chosenPub = publishers.get(input-1);
		publisherDAO.deletePublisher(chosenPub);
		System.out.println("Publisher " + chosenPub.toString() + " deleted.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void displayPublishers(Scanner sc) throws SQLException {
		List<Publisher> publishers = publisherDAO.readPublishers();
		for (Publisher p : publishers) {
			System.out.println(p.toString());
			System.out.println("Lives at " + p.getPublisherAddress());
			System.out.println("Phone number is " + p.getPublisherPhone());
			System.out.println("-------------");
		}
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	public int manageBranches(Scanner sc) {
		while (true) {
			System.out.println("1) Add branch");
			System.out.println("2) Update branch");
			System.out.println("3) Delete branch");
			System.out.println("4) Read branches");
			System.out.println("5) Quit to previous");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > 5);
			
			try {
				switch (input) {
				case 1:
					addBranch(sc);
					break;
				case 2:
					updateBranch(sc);
					break;
				case 3:
					deleteBranch(sc);
					break;
				case 4:
					displayBranches(sc);
					break;
				case 5:
					return admin1(sc);
				default:
					return 0;
				}
			} catch (SQLException e) {
				System.err.println("Operation could not be performed.");
				return 0;
			}
		}
	}
	
	private void addBranch(Scanner sc) throws SQLException {
		System.out.println("Enter 'quit' at any prompt to cancel the operation.");
		
		System.out.println("Enter branch name:");
		String name = sc.nextLine();
		if (name.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		System.out.println("Enter branch address:");
		String address = sc.nextLine();
		if (address.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		LibraryBranch branch = new LibraryBranch();
		branch.setBranchName(name);
		branch.setBranchAddress(address);
		libraryDAO.addBranch(branch);
		System.out.println("Library Branch " + name + " added.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void updateBranch(Scanner sc) throws SQLException {
		List<LibraryBranch> branches = libraryDAO.readBranches();
		System.out.println("Enter or select 'quit' at any prompt to cancel the operation.");
		System.out.println("Which branch to update:");
		for (int i=0; i<branches.size(); i++) {
			System.out.println(i+1 + ") " + branches.get(i).toString());
		}
		System.out.println(branches.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > branches.size()+1);
		
		if (input == branches.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		LibraryBranch chosenBranch = branches.get(input-1);
		
		String oldName = chosenBranch.getBranchName();
		System.out.println("Enter a new name for " + oldName + ", or enter N/A to skip:");
		String name = sc.nextLine();
		
		if (name.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (!name.toLowerCase().equals("n/a")) {
			chosenBranch.setBranchName(name);
		}
		
		String oldAddress = chosenBranch.getBranchAddress();
		System.out.println("Current address is " + oldAddress + ".");
		System.out.println("Enter a new address, or enter N/A to skip:");
		String address = sc.nextLine();
		
		if (address.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (!address.toLowerCase().equals("n/a")) {
			chosenBranch.setBranchAddress(address);
		}
		
		libraryDAO.updateBranch(chosenBranch);
		System.out.println("Library Branch updated.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void deleteBranch(Scanner sc) throws SQLException {
		List<LibraryBranch> branches = libraryDAO.readBranches();
		System.out.println("Select 'quit' to cancel the operation.");
		System.out.println("Which branch to delete:");
		for (int i=0; i<branches.size(); i++) {
			System.out.println(i+1 + ") " + branches.get(i).toString());
		}
		System.out.println(branches.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > branches.size()+1);
		
		if (input == branches.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		LibraryBranch chosenBranch = branches.get(input-1);
		libraryDAO.deleteBranch(chosenBranch);
		System.out.println("Library Branch " + chosenBranch.getBranchName() + " deleted.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
 	}
	
	private void displayBranches(Scanner sc) throws SQLException {
		List<LibraryBranch> branches = libraryDAO.readBranches();
		for (LibraryBranch b : branches) {
			System.out.println(b.getBranchName());
			System.out.println("Located at " + b.getBranchAddress());
			System.out.println("-------------");
		}
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	public int manageBorrowers(Scanner sc) {
		while (true) {
			System.out.println("1) Add borrower");
			System.out.println("2) Update borrower");
			System.out.println("3) Delete borrower");
			System.out.println("4) Read borrowers");
			System.out.println("5) Quit to previous");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > 5);
			
			try {
				switch (input) {
				case 1:
					addBorrower(sc);
					break;
				case 2:
					updateBorrower(sc);
					break;
				case 3:
					deleteBorrower(sc);
					break;
				case 4:
					displayBorrowers(sc);
					break;
				case 5:
					return admin1(sc);
				default:
					return 0;
				}
			} catch (SQLException e) {
				System.err.println("Operation could not be performed.");
				return 0;
			}
		}
	}
	
	private void addBorrower(Scanner sc) throws SQLException {
		System.out.println("Enter 'quit' at any prompt to cancel the operation.");
		
		System.out.println("Enter borrower name:");
		String name = sc.nextLine();
		if (name.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		System.out.println("Enter borrower address:");
		String address = sc.nextLine();
		if (address.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		System.out.println("Enter borrower phone number:");
		String number = sc.nextLine();
		if (number.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Borrower borrower = new Borrower();
		borrower.setName(name);
		borrower.setAddress(address);
		borrower.setPhone(number);
		borrowerDAO.addBorrower(borrower);
		System.out.println("Borrower " + name + " added.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void updateBorrower(Scanner sc) throws SQLException {
		List<Borrower> borrowers = borrowerDAO.readBorrowers();
		System.out.println("Enter or select 'quit' at any prompt to cancel the operation.");
		System.out.println("Which borrower to update:");
		for (int i=0; i<borrowers.size(); i++) {
			System.out.println(i+1 + ") " + borrowers.get(i).toString());
		}
		System.out.println(borrowers.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > borrowers.size()+1);
		
		if (input == borrowers.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Borrower chosenBorr = borrowers.get(input-1);
		
		String oldName = chosenBorr.getName();
		System.out.println("Enter a new name for " + oldName + ", or enter N/A to skip:");
		String name = sc.nextLine();
		
		if (name.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (!name.toLowerCase().equals("n/a")) {
			chosenBorr.setName(name);
		}
		
		String oldAddress = chosenBorr.getAddress();
		System.out.println("Current address is " + oldAddress + ".");
		System.out.println("Enter a new address, or enter N/A to skip:");
		String address = sc.nextLine();
		
		if (address.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (!address.toLowerCase().equals("n/a")) {
			chosenBorr.setAddress(address);
		}
		
		String oldPhone = chosenBorr.getPhone();
		System.out.println("Current phone number is " + oldPhone + ".");
		System.out.println("Enter a new phone number, or enter N/A to skip:");
		String number = sc.nextLine();
		
		if (number.toLowerCase().equals("quit")) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		if (!number.toLowerCase().equals("n/a")) {
			chosenBorr.setPhone(number);
		}
		
		borrowerDAO.updateBorrower(chosenBorr);
		System.out.println("Borrower updated.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void deleteBorrower(Scanner sc) throws SQLException {
		List<Borrower> borrowers = borrowerDAO.readBorrowers();
		System.out.println("Select 'quit' to cancel the operation.");
		System.out.println("Which borrower to delete:");
		for (int i=0; i<borrowers.size(); i++) {
			System.out.println(i+1 + ") " + borrowers.get(i).toString());
		}
		System.out.println(borrowers.size()+1 + ") Quit");
		
		int input = 0;
		do {
			input = getIntInput(sc);
		} while (input < 1 || input > borrowers.size()+1);
		
		if (input == borrowers.size()+1) {
			System.out.println("Operation cancelled.");
			return;
		}
		
		Borrower chosenBorr = borrowers.get(input-1);
		borrowerDAO.deleteBorrower(chosenBorr);
		System.out.println("Borrower " + chosenBorr.getName() + " deleted.");
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	private void displayBorrowers(Scanner sc) throws SQLException {
		List<Borrower> borrowers = borrowerDAO.readBorrowers();
		for (Borrower bor : borrowers) {
			System.out.println(bor.getName());
			System.out.println("Lives at " + bor.getAddress());
			System.out.println("Phone number is " + bor.getPhone());
			System.out.println("Card number " + bor.getCardNo());
			System.out.println("-------------");
		}
		System.out.println("Press enter to continue...");
		sc.nextLine();
	}
	
	public int overrideLoan(Scanner sc) {
		try {
			List<BookLoans> loans = loansDAO.readLoans();
			System.out.println("Select an active loan:");
			for (int i=0; i<loans.size(); i++) {
				BookLoans loan = loans.get(i);
				Borrower borrower = borrowerDAO.readBorrowersByCardNo(loan.getCardNo()).get(0);
				Book book = bookDAO.readBooksById(loan.getBookId()).get(0);
				LibraryBranch branch = libraryDAO.readBranchesById(loan.getBranchId()).get(0);
				LocalDateTime dateOut = loan.getDateOut();
				LocalDateTime dueDate = loan.getDueDate();
				System.out.println(i+1 + ")");
				System.out.println("Borrower: " + borrower.toString());
				System.out.println("Book: " + book.toString());
				System.out.println("Branch: " + branch.toString());
				System.out.println("Checked out: " + dateOut);
				System.out.println("Due: " + dueDate);
				System.out.println("-------------");
			}
			System.out.println(loans.size()+1 + ") Cancel");
			
			int input = 0;
			do {
				input = getIntInput(sc);
			} while (input < 1 || input > loans.size()+1);
			
			if (input != loans.size()+1) {
				BookLoans chosenLoan = loans.get(input-1);
				System.out.println("Enter the new year:");
				int year = getIntInput(sc);
				System.out.println("Enter the new month (1-12):");
				int month = getIntInput(sc);
				System.out.println("Enter the new day (1-31):");
				int day = getIntInput(sc);
				LocalDateTime newDueDate = LocalDateTime.of(year, month, day, 0, 0);
				chosenLoan.setDueDate(newDueDate);
				loansDAO.updateLoan(chosenLoan);
				System.out.println("Due date changed to " + newDueDate);
				System.out.println("Press enter to continue...");
				sc.nextLine();
				return admin1(sc);
			}
			
			System.out.println("Operation cancelled.");
			return admin1(sc);
			
		} catch (SQLException e) {
			System.err.println("Error accessing due date.");
		}
		
		return 0;
	}
	
	public static void main(String[] args) {
		Service service = new Service();
		Scanner sc = new Scanner(System.in);
		service.mainmenu(sc);
	}
}