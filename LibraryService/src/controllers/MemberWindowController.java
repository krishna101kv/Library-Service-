package controllers;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.swing.text.TabableView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.*;
import application.*;
import javafx.fxml.Initializable;
import controllers.*;

public class MemberWindowController implements Initializable {
	
	//Member fxml variables
	@FXML
	private Label libraryWelcomeMemberName;
	
	@FXML
	private Label libraryWelcomeMemberId;
	
	@FXML
	private AnchorPane libraryMemberWelcomeWindowPane;
	
	@FXML
	private AnchorPane libraryMemberInformationPane;
	
	@FXML
	private AnchorPane libraryMemberBookRegistrationPane;
	
	@FXML
	private Label libraryMemberName;
	
	@FXML
	private Label libraryMemberId;
	
	@FXML 
	private Label libraryMemberBookInformation;
	
	@FXML
	private TextField libraryMemberEmailId;
	
	@FXML
	private TextField libraryMemberContactNumber;
	
	@FXML
	private Label libraryMemberUpdate;
	
	//Book table
	@FXML
	private TableView<Booklist> libraryAvailableBooksTable;
	private ObservableList <Booklist> availableBooklist;
	
	@FXML
	private Label libraryBookRegistrationStatus;
	
	@FXML
	private TableView<Booklist> libraryBookRegisteredTable;
	private ObservableList <Booklist> registeredBooklist;
	
	@FXML
	private Label libraryBookRemovalStatus;
	
	
	private Members libraryMember;
	
	private String libraryBookMemberId;
	
	//initializing the database connection
	DataBaseOperationBookManagement MemberOp = null;
	DataBaseOperationBook BookRegistrationOps = null;
	DataBaseOperationBook BookOp = null;
	
	//constructor
	public MemberWindowController() {
		
		 MemberOp = new DataBaseOperationBookManagementImplementation();
		 BookRegistrationOps = new DataBaseOperationBookImplementation();
		 BookOp = new DataBaseOperationBookImplementation();
	}
			
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		//set the Member window pane
		
		libraryMemberWelcomeWindowPane.setVisible(true);
		libraryMemberUpdate.setText(null);
		libraryBookRegistrationStatus.setText(null);
		libraryBookRemovalStatus.setText(null);
	
        try{
        	//functions to perform Book registration operations
             updateTheMemberInformationDetails();
             getlibraryMemberInformationDetails();
             getlibraryAvailableBookDetails();
             updatelibraryAvaialableBookTable();
             getlibraryMemberRegisteredBooks();
             updatelibraryMemberRegisteredBooks();
            
            
      } catch (SQLException sqlException){
    	  sqlException.printStackTrace();
        }
    }

	//method to get the Books registered by Member
	private void getlibraryMemberRegisteredBooks() throws SQLException {
		
	     registeredBooklist = BookRegistrationOps.getRegisteredbooks(libraryMember);
		
	}
	
	//method to update the registered Books in Member table
		private void updatelibraryMemberRegisteredBooks() {
		//set the column values and populate values
		TableColumn<Booklist, Integer> libraryBookCode = new TableColumn<>("Code");
		libraryBookCode.setCellValueFactory(new PropertyValueFactory<Booklist, Integer>("BOOKID"));
        
		TableColumn<Booklist, String> libraryBookTitle = new TableColumn<>("Title");
        libraryBookTitle.setCellValueFactory(new PropertyValueFactory<Booklist, String>("BOOKNAME"));

        TableColumn<Booklist, Integer> libraryBookDuration = new TableColumn<>("EDITION");
        libraryBookDuration.setCellValueFactory(new PropertyValueFactory<Booklist, Integer>("BOOKEDITION"));
        
        TableColumn<Booklist, String> libraryProfessor = new TableColumn<>("AUTHOR");
        libraryProfessor.setCellValueFactory(new PropertyValueFactory<Booklist, String>("AUTHOR"));
        
        
        //set the registered Book table
        libraryBookRegisteredTable.setItems(registeredBooklist);
        libraryBookRegisteredTable.getColumns().addAll(libraryBookCode, libraryBookTitle, libraryBookDuration, libraryProfessor);
        libraryBookRegisteredTable.setVisible(true);

		
	}

	//function to get the available Books
	void getlibraryAvailableBookDetails() throws SQLException{
		 availableBooklist = FXCollections.observableArrayList();
	     availableBooklist = BookOp.getAllBookList();
	}
	
	//function to update the available Book table
	void updatelibraryAvaialableBookTable() {
		//set the column names and populate values
		TableColumn<Booklist, Integer> libraryBookCode = new TableColumn<>("Code");
		libraryBookCode.setCellValueFactory(new PropertyValueFactory<Booklist, Integer>("BOOKID"));
        
		TableColumn<Booklist, String> libraryBookTitle = new TableColumn<>("Title");
        libraryBookTitle.setCellValueFactory(new PropertyValueFactory<Booklist, String>("BOOKNAME"));

        TableColumn<Booklist, Integer> libraryBookDuration = new TableColumn<>("EDITION");
        libraryBookDuration.setCellValueFactory(new PropertyValueFactory<Booklist, Integer>("BOOKEDITION"));
        
        TableColumn<Booklist, String> libraryProfessor = new TableColumn<>("AUTHOR");
        libraryProfessor.setCellValueFactory(new PropertyValueFactory<Booklist, String>("AUTHOR"));
        
        //set the available Book table
        libraryAvailableBooksTable.setItems(availableBooklist);   
        libraryAvailableBooksTable.getColumns().addAll(libraryBookCode, libraryBookTitle, libraryBookDuration, libraryProfessor);
        libraryAvailableBooksTable.setVisible(true);

	}

	//method to get the Member details
	private void updateTheMemberInformationDetails() throws SQLException {
		libraryBookMemberId = LoginWindowController.getUsername();
       
        libraryMember = MemberOp.getLibraryMember(libraryBookMemberId);       
		libraryWelcomeMemberName.setText(libraryMember.getlibraryMemberName());
		libraryWelcomeMemberId.setText(libraryMember.getMEMBERID());
		
	}
	//method to update the Member information
	private void getlibraryMemberInformationDetails() {
		

        libraryMemberName.setText(libraryMember.getlibraryMemberName());
        libraryMemberId.setText(libraryMember.getMEMBERID());
        libraryMemberBookInformation.setText("Management Information System");

        libraryMemberEmailId.setText(libraryMember.getlibraryMemberEmail());
        libraryMemberContactNumber.setText(libraryMember.getlibraryMemberContactNumber());
	}

	//handle Member information button to enable Member information table
	@FXML
	private void libraryMemberInformationButton(ActionEvent action) {
		libraryMemberBookRegistrationPane.setVisible(false);//disable the Member registered Book pane
		libraryMemberWelcomeWindowPane.setVisible(false);//disable the Member welcome window pane
		libraryMemberInformationPane.setVisible(true);
	}
	
	//handle Member information button to enable Book registration window table
	@FXML
	private void libraryMemberBookRegistrationButton(ActionEvent action) {
		libraryMemberInformationPane.setVisible(false);//disable the Member information window pane
		libraryMemberWelcomeWindowPane.setVisible(false);//disable the Member welcome window pane
		libraryMemberBookRegistrationPane.setVisible(true);
	}
	
	//handle Member update button
	@FXML
	private void libraryMemberUpdateButton(ActionEvent action) {
		String newEmailId = libraryMemberEmailId.getText();
        String newContactNumber = libraryMemberContactNumber.getText();

        boolean emailStatus ;
        boolean contactNumberStatus;
        //update Member information
        emailStatus = MemberOp.updateLibraryMemberEmail(libraryBookMemberId, newEmailId);
        if (emailStatus)
            libraryMember.setlibraryMemberEmail(newEmailId);

        contactNumberStatus = MemberOp.updateLibraryMemberContactNumber(libraryBookMemberId, newContactNumber);
        if (contactNumberStatus)
            libraryMember.setlibraryMemberContactNumber(newContactNumber);

        if (emailStatus && contactNumberStatus) {
            libraryMemberUpdate.setText("Information updated successfully.");
        } else {
        	libraryMemberUpdate.setText("Error updating information. Please recheck!");
        }

	}
	
	//method to register the Books from the available Books
	@FXML
	private void libraryBookRegisterButton(ActionEvent action) throws SQLException {
		
        Booklist choosenBook = libraryAvailableBooksTable.getSelectionModel().getSelectedItem();
        if (choosenBook == null){
        	libraryBookRegistrationStatus.setText("Select a Book first!");
            return;
        }
        BookRegistration BookRegistration = new BookRegistration(libraryBookMemberId, choosenBook.getBOOKID());
        //check if Book is already registered
        boolean exists = BookRegistrationOps.bookExists(choosenBook, libraryMember);
        if (exists){
        	libraryBookRegistrationStatus.setText("Already registered for " + choosenBook.getBOOKID());
            return;
        }

        boolean regStatus = BookRegistrationOps.registerTobook(BookRegistration);
        if (regStatus){
            registeredBooklist.add(choosenBook);
            libraryBookRegistrationStatus.setText("Registration for " + choosenBook.getBOOKID() + " successful.");
        } else{
        	libraryBookRegistrationStatus.setText("Failed to add " + choosenBook.getBOOKID() + "!");
        }

		
	}
	
	//function to remove the Books from the registered Books
	@FXML
	private void libraryBookRemovalButton(ActionEvent action) {
		libraryBookRemovalStatus.setText(null); 

        Booklist choosenBook = libraryBookRegisteredTable.getSelectionModel().getSelectedItem();
        if (choosenBook == null){
        	libraryBookRemovalStatus.setText("Select a Book first!");
            return;
        }

        
        boolean removeStatus = BookRegistrationOps.removeRegsiteredbook(choosenBook, libraryMember);
        if (removeStatus){
            registeredBooklist.remove(choosenBook);
            libraryBookRemovalStatus.setText("Removed " + choosenBook.getBOOKID() + " successfully.");
        } else{
        	libraryBookRemovalStatus.setText("Failed to remove " + choosenBook.getBOOKID() + "!");
        }

	}
	
	//handle sign out button
	@FXML
 	public void MemberWinodwSignOut(ActionEvent action) throws IOException {
 		  Parent loginWindow = FXMLLoader.load(getClass().getResource("/views/LoginWindow.fxml"));
 	      Scene loginWindowScene = new Scene(loginWindow);
 	      Stage loginMainWindow = (Stage)((Node)action.getSource()).getScene().getWindow();
 	      loginMainWindow.setScene(loginWindowScene);
 	      loginMainWindow.show();
	}
		
}
