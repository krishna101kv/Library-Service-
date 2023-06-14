package controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.*;
import javax.swing.*;

import application.Booklist;

public class AdministrationWindowController implements Initializable {
	
	 // Book Table
    @FXML
    private ObservableList <Booklist> libraryBooklist;
    @FXML
    private TableView <Booklist> libraryAvailableBooksTable;
    @FXML
    private TextField libraryBookID;
    @FXML
    private TextField libraryBookName;
    @FXML
    private TextField libraryBookPrice;
    @FXML
    private TextField bookAuthor;
    @FXML
    private Label bookAdditionStatus;
    
    
    //initialization
	    public void initialize(URL location, ResourceBundle resources) {
		 try {
			 	getAVailableBooks();
			 	updateAvailableBooks();
		} catch (SQLException sqlException) {
			
			sqlException.printStackTrace();
		}
		 
	 }
	 
	 //function to get the availability of Books from database
	    private void getAVailableBooks() throws SQLException{
		 	libraryBooklist = FXCollections.observableArrayList();
	        DataBaseOperationBook bookOp = new DataBaseOperationBookImplementation();
	        libraryBooklist = bookOp.getAllBookList();
	    }
	        
	    

	 //function to update the table when a new Book is added
	    private void updateAvailableBooks(){
		 	TableColumn<Booklist, Integer> libraryBookID = new TableColumn<>("BOOKID");
			libraryBookID.setCellValueFactory(new PropertyValueFactory<Booklist, Integer>("BOOKID"));
	        
			TableColumn<Booklist, String> libraryBookName = new TableColumn<>("BOOKNAME");
	        libraryBookName.setCellValueFactory(new PropertyValueFactory<Booklist, String>("BOOKNAME"));

	        TableColumn<Booklist, Integer> libraryBookPrice = new TableColumn<>("BOOKEDITION");
	        libraryBookPrice.setCellValueFactory(new PropertyValueFactory<Booklist, Integer>("BOOKEDITION"));
	        
	        TableColumn<Booklist, String> bookAuthor = new TableColumn<>("AUTHOR");
	        bookAuthor.setCellValueFactory(new PropertyValueFactory<Booklist, String>("AUTHOR"));
    
	        libraryAvailableBooksTable.setItems(libraryBooklist); 
	        
	        libraryAvailableBooksTable.getColumns().addAll(libraryBookID, libraryBookName, libraryBookPrice, bookAuthor);
	        libraryAvailableBooksTable.setVisible(true);
	    }  
	 
	 	//function to add new books
	 	@FXML
	 	private void addNewBook(ActionEvent action) throws SQLException {
	 			 	 		
	 		//check if all the fields are filled 		
	 		if(!libraryBookID.getText().isEmpty() && !libraryBookName.getText().isEmpty() && !libraryBookPrice.getText().isEmpty() && !bookAuthor.getText().isEmpty()) {
	 			int BookCode = Integer.parseInt(libraryBookID.getText());
		 		String BookTitle = libraryBookName.getText();
		 		int BookDuration = Integer.parseInt(libraryBookPrice.getText());
		 		String BookProfessor = bookAuthor.getText();
		 		Booklist newBook = new Booklist(BookCode,BookTitle,BookDuration,BookProfessor);
		 		DataBaseOperationBook addBookDBOps = new DataBaseOperationBookImplementation();
		 		
		 		//condition to check if the Book is already inserted
		 		if(addBookDBOps.insertBook(newBook))
		 		{
		 			
		 			bookAdditionStatus.setText(BookCode + " Book inserted successfully");
		 		}
		 		else
		 		{
		 			bookAdditionStatus.setText(BookCode+ " Book already inserted/there was an error while inserting");
		 		}
		 		
		 		libraryBookID.clear();
		 		libraryBookName.clear();
		 		libraryBookPrice.clear();
		 		bookAuthor.clear();
		 		
		 		getAVailableBooks();
		 		updateAvailableBooks();
		 				
	 		}
	 		else
	 			bookAdditionStatus.setText("Please entery all the fields!!");
	 			
}
	 	
	 	@FXML
	 	public void adminWindowSignOut(ActionEvent action) throws IOException {
	 		  Parent loginWindow = FXMLLoader.load(getClass().getResource("/views/LoginWindow.fxml"));
	 	      Scene loginWindowScene = new Scene(loginWindow);
	 	      Stage loginMainWindow = (Stage)((Node)action.getSource()).getScene().getWindow();
	 	      loginMainWindow.setScene(loginWindowScene);
	 	      loginMainWindow.show();
	 	}
	 		    
}