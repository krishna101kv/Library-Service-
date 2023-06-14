package application;
import java.io.*;

public class Booklist {
	   
	    private int BOOKID;
	    private String BOOKNAME;
	    private int BOOKEDITION;
	    private String AUTHOR;

	    
	    public Booklist(int BOOKID, String BOOKNAME, int BOOKEDITION, String AUTHOR) {
	        this.BOOKID = BOOKID;
	        this.BOOKNAME = BOOKNAME;
	        this.BOOKEDITION = BOOKEDITION;
	        this.AUTHOR = AUTHOR;
	            
	    }
	    
	    //getters and setters methods

	    public int getBOOKID() {
	        return BOOKID;
	    }

	    public void setBOOKID(int BOOKID) {
	        this.BOOKID = BOOKID;
	    }

	    public String getBOOKNAME() {
	        return BOOKNAME;
	    }

	    public void setBOOKNAME(String BOOKNAME) {
	        this.BOOKNAME = BOOKNAME;
	    }

		public int getBOOKEDITION() {
			return BOOKEDITION;
		}

		public void setBOOKEDITION(int BOOKEDITION) {
			this.BOOKEDITION = BOOKEDITION;
		}
	    

		public String getAUTHOR() {
			return AUTHOR;
		}

		public void setAUTHOR(String AUTHOR) {
			this.AUTHOR = AUTHOR;
		}
	    public String toString() {
	        return "Booklist{" +
	                ", BOOKID='" + BOOKID + '\'' +
	                ", BOOKNAME='" + BOOKNAME + '\'' +
	                ", BOOKEDITION='" + BOOKEDITION + '\'' +
	                ", AUTHOR='" + AUTHOR +
	                '}';
	    }
	
}
