package application;
public class LoginInformation {
	private String libraryLoginUserName;
    private String libraryLoginPassword;
    public LoginInformation(String libraryLoginUserName, String libraryLoginPassword) {
        this.libraryLoginUserName = libraryLoginUserName;
        this.libraryLoginPassword = libraryLoginPassword;
    }
    public String getLibraryUserName() {
        return libraryLoginUserName;
    }
    public String getLibraryPassword() {
        return libraryLoginPassword;
    }
}
