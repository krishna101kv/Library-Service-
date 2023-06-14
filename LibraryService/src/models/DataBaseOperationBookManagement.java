package models;
import java.sql.SQLException;
import application.LoginInformation;
import application.Members;
import application.NewUsers;

public interface DataBaseOperationBookManagement {
	
	//new user verification and sign up
	boolean insertNewUser(NewUsers newUser);
    boolean verifyUserLoginCredential(LoginInformation loginInfo);
    String getLoginUserType(LoginInformation loginInfo) throws SQLException;
    boolean userExists(NewUsers newUser) throws SQLException;
    
    //Member database update
    boolean updateLibraryMemberEmail(String libraryMemberId, String libraryMemberEmailId);
    boolean updateLibraryMemberContactNumber(String libraryMemberId, String libraryMemberContactNumber);
    Members getLibraryMember(String libraryMemberId) throws SQLException;
	
    
}
