package application;
public class Members {
    private String MEMBERID;
    private String libraryMemberName;
    private String libraryMemberEmail;
    private String libraryMemberContactNumber;
    //to get member details
    public Members(String MEMBERID, String libraryMemberName, String libraryMemberEmail, String libraryMemberContactNumber) {
        this.setMEMBERID(MEMBERID);
        this.setlibraryMemberName(libraryMemberName);
        this.setlibraryMemberEmail(libraryMemberEmail);
        this.setlibraryMemberContactNumber(libraryMemberContactNumber);
    }
    //get and set methods
	public String getMEMBERID() {
		return MEMBERID;
	}

	public void setMEMBERID(String MEMBERID) {
		this.MEMBERID = MEMBERID;
	}

	public String getlibraryMemberName() {
		return libraryMemberName;
	}

	public void setlibraryMemberName(String libraryMemberName) {
		this.libraryMemberName = libraryMemberName;
	}

	public String getlibraryMemberEmail() {
		return libraryMemberEmail;
	}

	public void setlibraryMemberEmail(String libraryMemberEmail) {
		this.libraryMemberEmail = libraryMemberEmail;
	}

	public String getlibraryMemberContactNumber() {
		return libraryMemberContactNumber;
	}

	public void setlibraryMemberContactNumber(String libraryMemberContactNumber) {
		this.libraryMemberContactNumber = libraryMemberContactNumber;
	}

    public String toString() {
        return "Members{" +
                "memberName='" + libraryMemberName + '\'' +
                ", memberId='" + MEMBERID + '\'' +
                ", memberEmail='" + libraryMemberEmail + '\'' +
                ", memberContactNumber='" + libraryMemberContactNumber + '\'' +
                '}';
    }
}

