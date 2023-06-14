package models;

import javafx.collections.ObservableList;
import application.Booklist;
import application.BookRegistration;
import application.Members;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataBaseOperationBook {
    
	//function to get book details
	ObservableList <Booklist> getAllBookList() throws SQLException;
    Booklist getBookDetail(int library_bookNumber) throws SQLException;
    
    //function to register to book
    boolean registerTobook(BookRegistration registration);
	boolean bookExists(Booklist librarybook, Members libraryStudent) throws SQLException;
	
	//function to remove the book from the registered books
	boolean removeRegsiteredbook(Booklist librarybook, Members libraryStudent);
	ObservableList <Booklist> getRegisteredbooks(Members libraryStudent) throws SQLException;
	ObservableList <Members> getRegisteredMembers(Booklist librarybook) throws SQLException;
	
	//function to insert a new book
	boolean insertBook(Booklist newbook);
     
}
