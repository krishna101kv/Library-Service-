package application;
public class BookRegistration {
    private String MEMBERID;
    private int BOOKID;
    
    public BookRegistration(String MEMBERID, int BOOKID) {
        this.MEMBERID = MEMBERID;
        this.BOOKID = BOOKID;
    }

   
    //get and set methods
    public String getMEMBERID() {
        return MEMBERID;
    }

    public void setMEMBERID(String MEMBERID) {
        this.MEMBERID = MEMBERID;
    }

    public int getBOOKID() {
        return BOOKID;
    }

    public void setBOOKID(int BOOKID) {
        this.BOOKID = BOOKID;
    }
    public String toString() {
        return "REGISTERLIBRARYBOOK{" +
                ", MEMBERID='" + MEMBERID + '\'' +
                ", BOOKID=" + BOOKID +
                '}';
    }
}
