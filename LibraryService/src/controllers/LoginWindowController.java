package controllers;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import application.*;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginWindowController implements Initializable{
	
	//SignUp Window 
		@FXML
	    private TextField libraryFullNameSignUp;
	    @FXML
	    private PasswordField libraryPasswordSignUp;
	    @FXML
	    private TextField libraryEmailIdSignUp;
	    @FXML
	    private TextField libraryUserNameSignUp;
	    @FXML
	    private ComboBox <String> libraryUserTypesSignUp;
	    private ObservableList <String> libraryUserTypes;
	    @FXML
	    private Label libraryUserStatusSignUp;
	    
	    //Login Window
	    @FXML
	    private TextField libraryUserNameLogin;
	    @FXML
	    private PasswordField libraryPasswordLogin;
	    @FXML
	    private Label libraryStatusLogin;
	 
	    @FXML
	    private AnchorPane loginWindowPane;
	    
	    @FXML
	    private AnchorPane signUpWindowPane;
	    private static String getUserName;
	
		
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	libraryUserTypes = FXCollections.observableArrayList("LIBRARY_ADMIN","LIBRARY_MEMBER");
    	libraryUserTypesSignUp.setItems(libraryUserTypes);
    	libraryUserTypesSignUp.getSelectionModel().selectLast();
    	libraryUserStatusSignUp.setText(null);
    }
    
    //function for sign in button
    @FXML
    private void librarySignUpUserButton(ActionEvent actionEvent) throws SQLException {
    	
        //get all the details from fxml window
        String librarySignUpUserName = libraryUserNameSignUp.getText();
        String librarySignUpPassword = libraryPasswordSignUp.getText();
        String librarySignUpUserType = libraryUserTypesSignUp.getValue();
        String librarySignUpFullName = libraryFullNameSignUp.getText();
        String librarySignUpEmailID = libraryEmailIdSignUp.getText();

        //check if all the required fields are entered
        if (!librarySignUpFullName.isEmpty() && !librarySignUpUserName.isEmpty()  && !librarySignUpPassword.isEmpty() && librarySignUpUserType != null){
        	//clear the sign up status
        	libraryUserStatusSignUp.setText(null);
        	//insert new user into the database
        	NewUsers libraryUser = new NewUsers(librarySignUpFullName, librarySignUpEmailID, librarySignUpUserName, librarySignUpPassword, librarySignUpUserType);
            DataBaseOperationBookManagement libraryUserDBOps = new DataBaseOperationBookManagementImplementation();
           
            //check if the user trying insert is already available
            if (libraryUserDBOps.userExists(libraryUser)){
            	libraryUserStatusSignUp.setText("User "+librarySignUpFullName + " is already created, please login with user name and password");
                return;
            }
            //if user is not available the insert new user
            if (libraryUserDBOps.insertNewUser(libraryUser)){
            	libraryUserStatusSignUp.setText(librarySignUpFullName+" Sign Up successful!");
            	signUpClear();
            	
            }
            //if there are any other errors while signing up
            else{
            	libraryUserStatusSignUp.setText(librarySignUpFullName+" Failed to Sign Up");
            	signUpClear();
            }
        }
        //if the required fields are empty
        else{
            libraryUserStatusSignUp.setText("Please fill all the required details!");
            signUpClear(); 
            }
    }
    
    //enable the login window pane
    public void loginSignUpPaneWindow(ActionEvent actionEvent) {
    	loginWindowPane.setVisible(true);
    	signUpWindowPane.setVisible(false);//setting sign up window pane to null
    }
    
    //clear the sign up window pane fields
    public void signUpClear() {
    	libraryUserNameSignUp.clear();
        libraryPasswordSignUp.clear();
        libraryUserTypesSignUp.getSelectionModel().selectLast();
        libraryFullNameSignUp.clear();
        libraryEmailIdSignUp.clear();
    	
    }
    
    //handle user login button
    @FXML
    private void libraryUserLoginButton(ActionEvent actionEvent) throws SQLException {
    	//for getting the fxml feild values from login window pane    	
        String libraryUserLoginName = libraryUserNameLogin.getText();
        String libraryUserPassword = libraryPasswordLogin.getText();
        
        //check if any fields are empty in the login window pane
        if (!libraryUserLoginName.isEmpty() && !libraryUserPassword.isEmpty()){
            LoginInformation userloginInfo = new LoginInformation(libraryUserLoginName, libraryUserPassword);
            DataBaseOperationBookManagement libraryUserDBOps = new DataBaseOperationBookManagementImplementation();
            
            //verify the entered credentials
            if (libraryUserDBOps.verifyUserLoginCredential(userloginInfo)){
               
                getUserName = userloginInfo.getLibraryUserName();
                String userType = libraryUserDBOps.getLoginUserType(userloginInfo);
                try {
                    Parent LibraryServiceDashboard;
                    //if signed in user is library student enable the student window
                    if (userType.equals("LIBRARY_MEMBER")) {
                    	LibraryServiceDashboard = FXMLLoader.load(getClass().getResource("/views/MemberWindow.fxml"));
                    }	
                    //else enable the admin window
                    else {
                    	  LibraryServiceDashboard = FXMLLoader.load(getClass().getResource("/views/AdministrationWindow.fxml"));
                    }
                    Scene LibraryServiceDashboardScene = new Scene(LibraryServiceDashboard);

                    Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    window.setScene(LibraryServiceDashboardScene);
                    window.show();

                } catch (IOException sqlException) {
                	sqlException.printStackTrace();
                }
            }
            //invalid credentials
            else{
                
                libraryStatusLogin.setText("Entered Credentials are invalid");
                loginClear();
            }
        }
        //if the fields are empty
        else{
           
        	libraryStatusLogin.setText("Please enter all the required fields!");
        	loginClear();
        }
    }
    
    //enable the sign up window pane
    @FXML
    public void signUpLoginPageButton(ActionEvent action) {
    	loginWindowPane.setVisible(false);//disable the login window pane
    	signUpWindowPane.setVisible(true);
    }
    
    public void loginClear() {
    	libraryUserNameLogin.clear();
        libraryPasswordLogin.clear();
    	
    }
    
    //get the entered user name
    public static String getUsername(){
        return getUserName;
    }

   
}


