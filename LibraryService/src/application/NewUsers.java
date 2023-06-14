package application;
public class NewUsers {
    private String fullName;
    private String userEmailID;
    private String userName;
    private String userPassword;
    private String userType;
    //method for getting user info
    public NewUsers(String fullName, String userEmailID, String userName, String userPassword, String userType) {
        this.fullName = fullName;
        this.userEmailID = userEmailID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userType = userType;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserEmailID() {
        return userEmailID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return userPassword;
    }

    public String getUserType() {
        return userType;
    }
}

