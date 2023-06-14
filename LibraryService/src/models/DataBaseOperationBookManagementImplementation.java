package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.LoginInformation;
import application.Members;
import java.sql.PreparedStatement;
import application.NewUsers;
import models.DataBaseOperationBookManagement;

public class DataBaseOperationBookManagementImplementation implements DataBaseOperationBookManagement {
	
	//initializing the database connection
	DBConnect conn = null;
	Statement stmt = null;
	PreparedStatement prepareStmt = null;
	
	//constructor
	public DataBaseOperationBookManagementImplementation() {
		conn = new DBConnect();
	}
	
	//calling the method to insert new user
	@Override
    public boolean insertNewUser(NewUsers newUser) {
		boolean queryResult = false;
        try {
        	
        	//prepared statement to insert new users
        	String insertUserTypeQuery = "INSERT INTO LIBRARYUSERLOGIN(USERNAME,PASSWORD,USERTYPE) "+ "VALUES (?,?,?)";
	        prepareStmt = conn.connect().prepareStatement(insertUserTypeQuery);
			prepareStmt.setString(1,newUser.getUserName());
			prepareStmt.setString(2, newUser.getPassword());
			prepareStmt.setString(3, newUser.getUserType());
			String roleName = newUser.getUserType();
			
			int insertUserTypeStatus = prepareStmt.executeUpdate();
			if(insertUserTypeStatus > 0) {
				 queryResult = true;
			 }
			 else {
				 	queryResult = false;
			 }
	        
			stmt = conn.connect().createStatement();
				
			//prepared statements
	       String insertQuery = "INSERT INTO " +roleName+"(MEMBERID,MEMBERNAME,MEMBEREMAIL) " + "VALUES (?, ?, ?)"; 
	       prepareStmt = conn.connect().prepareStatement(insertQuery);
		   prepareStmt.setString(1,newUser.getUserName());
			prepareStmt.setString(2, newUser.getFullName());
			prepareStmt.setString(3, newUser.getUserEmailID());
			int insertRecordStatus = prepareStmt.executeUpdate();
				 if(insertRecordStatus > 0) {
					 queryResult = true;
				 }
				 else {
					 	queryResult = false;
				 }
		}
        catch (SQLException sqlException) {
        	sqlException.printStackTrace();
        	return false;
        }
        return queryResult;
	}
	
	//calling method to verify user credentials
    @Override
    public boolean verifyUserLoginCredential(LoginInformation loginInfo) {
    	//sql statment to get user name and password from the database
        String veryUserLogin = String.format("SELECT * FROM LIBRARYUSERLOGIN WHERE USERNAME='%s' AND PASSWORD='%s'",
        		loginInfo.getLibraryUserName(),
        		loginInfo.getLibraryPassword());
       
        try {
            //verify the user login        
        	stmt = conn.connect().createStatement();
            ResultSet outputResult = stmt.executeQuery(veryUserLogin);
            if (outputResult.next()){
                return true; 
            }
        } catch (SQLException sqlException) {
        	sqlException.printStackTrace();
        }
        return false; 
    }

    //method to get the type of user who has logged in
    @Override
    public String getLoginUserType(LoginInformation loginInfo) throws SQLException {
    	//sql statement to get the user name
        String getUserType = String.format("SELECT USERTYPE FROM LIBRARYUSERLOGIN WHERE USERNAME='%s'",
        		loginInfo.getLibraryUserName());
               
        stmt = conn.connect().createStatement();     
        ResultSet outputResult = stmt.executeQuery(getUserType);
        if (outputResult.next()){
            return outputResult.getString("USERTYPE");
        }
        return null;
    }

    //method to check the user existence
    @Override
    public boolean userExists(NewUsers newUser) throws SQLException {
    	//sql query to get the user login details
        String query = String.format("SELECT USERNAME FROM LIBRARYUSERLOGIN WHERE USERNAME='%s' AND USERTYPE='%s'",newUser.getUserName(),newUser.getUserType());
        stmt = conn.connect().createStatement();
        
        ResultSet outputResult = stmt.executeQuery(query);
        return (outputResult.next());
    }
    
    //method to update Member email
    @Override
    public boolean updateLibraryMemberEmail(String LibraryMemberId, String LibraryMemberEmailId) {
    	//sql query to update the email id
        String updateEmailId = String.format("UPDATE LIBRARY_MEMBER SET MEMBEREMAIL='%s' WHERE MEMBERID='%s'",
        		LibraryMemberEmailId,
                LibraryMemberId);
        try{
        	//execution of sql statement
            stmt = conn.connect().createStatement();
        	stmt.executeUpdate(updateEmailId);
            return true;

        } catch (SQLException sqlException){
        	sqlException.printStackTrace();
        }
        return false;
    }

    //method to update contact information
    @Override
    public boolean updateLibraryMemberContactNumber(String LibraryMemberId, String LibraryMemberContactNumber) {
    	//sql statement to update contact information
        String updateContactNumber = String.format("UPDATE LIBRARY_MEMBER SET MEMBERMOBILE='%s' WHERE MEMBERID='%s'",
        		LibraryMemberContactNumber,
                LibraryMemberId);
        try{
        	//command to execute sql statements
        	stmt = conn.connect().createStatement();
            stmt.executeUpdate(updateContactNumber);
            return true;

        } catch (SQLException sqlException){
        	sqlException.printStackTrace();
        }
        return false;
    }
   
    //method to get Member details
    @Override
    public Members getLibraryMember(String LibraryMemberId) throws SQLException {
    	//sql query to get Member details
    	  stmt = conn.connect().createStatement();
    	 
    	 //stmt.executeUpdate(sql);
        String getMemberInfo = String.format("SELECT * FROM LIBRARY_MEMBER WHERE MEMBERID='%s'", LibraryMemberId);
        //command to execute sql command
      

        ResultSet outputResult = stmt.executeQuery(getMemberInfo);

        if (!outputResult.next())
            return null;

        String LibraryMemberName = outputResult.getString("MEMBERNAME");
        String LibraryMemberEmail = outputResult.getString("MEMBEREMAIL");
        String LibraryMemberContactNumber = outputResult.getString("MEMBERMOBILE");

        return new Members(LibraryMemberId, LibraryMemberName, LibraryMemberEmail, LibraryMemberContactNumber);
    }
}
