package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.BookRegistration;
import application.Booklist;
import application.Members;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataBaseOperationBookImplementation implements DataBaseOperationBook {

	//initializing database connection
	DBConnect conn = null;
	Statement stmt = null;
	PreparedStatement prepareStmt = null;
	
	//constructor
	public DataBaseOperationBookImplementation() {
		conn = new DBConnect();
	}
	
	//calling function to call the list of books
    @Override
    public ObservableList <Booklist> getAllBookList() throws SQLException {
  
    	stmt = conn.connect().createStatement();
             
        String getbookDetail = String.format("SELECT * FROM BOOKREGISTRY");
        
        ResultSet outputResult = stmt.executeQuery(getbookDetail);
      
        //get all the available books
        ObservableList <Booklist> listOfbooks = FXCollections.observableArrayList();
        while(outputResult.next()){
        	
            int librarybookCode = outputResult.getInt("BOOKID");
            String BOOKNAME = outputResult.getString("BOOKNAME");
            int BOOKEDITION = outputResult.getInt("BOOKEDITION");
            String libraryAUTHOR = outputResult.getString("AUTHOR");
            Booklist newbook = new Booklist(librarybookCode, BOOKNAME, BOOKEDITION, libraryAUTHOR);
            listOfbooks.add(newbook);
        }
        return listOfbooks;
    }

    //calling function to get the book details
    @Override
    public Booklist getBookDetail(int BOOKID) throws SQLException {
   
    	//sql statement to get the book details
        String getbookDetail = String.format("SELECT * FROM BOOKREGISTRY WHERE BOOKID=%d",
        		BOOKID);
        stmt = conn.connect().createStatement(); 
        //execution of sql statement
        ResultSet outputResult = stmt.executeQuery(getbookDetail);
        //checking the result
        if(outputResult.next()){

            String BOOKNAME = outputResult.getString("BOOKNAME");
            int BOOKEDITION = outputResult.getInt("BOOKEDITION");
            String AUTHOR = outputResult.getString("AUTHOR");
            return new Booklist(BOOKID, BOOKNAME, BOOKEDITION, AUTHOR);
        }
        return null;
    }
    
    //calling function to register book
    @Override
    public boolean registerTobook(BookRegistration registration) {
       //insert books to the Member id
    	
    	
    	
    	
       String insertIntoMemberRegistration = String.format("INSERT INTO BORROWEDBOOKS VALUES('%s', %d)",
                registration.getMEMBERID(),
                registration.getBOOKID());
        try{
        	//executing sql statement
        	
        	stmt = conn.connect().createStatement();
            stmt.executeUpdate(insertIntoMemberRegistration);
        } catch (SQLException sqlException){
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }

    //function to check the existence of book
    @Override
    public boolean bookExists(Booklist LIBRARYBOOK, Members libraryMemberId) throws SQLException {
    	//sql statement to get the book details to check existence
    	String getBookList = String.format("SELECT BOOKREGISTRY.BOOKID\n" +
                "FROM BOOKREGISTRY, BORROWBOOKS\n" +
                "WHERE BORROWBOOKS.MEMBERID='%s' "
                + "AND BOOKREGISTRY.BOOKID='%s' "
                + "AND BOOKREGISTRY.BOOKID=BOOKREGISTRY.BOOKID",
                libraryMemberId.getMEMBERID(),
                LIBRARYBOOK.getBOOKID());

    	stmt = conn.connect().createStatement();
    	//execution of sql statement
        ResultSet outputResult = stmt.executeQuery(getBookList);
        return outputResult.next(); 
    }

    //function to remove the registered books
    @Override
    public boolean removeRegsiteredbook(Booklist registeredbook, Members registeredMember) {
    	//sql statement to remove the book
        String removeRegisteredbook = String.format("DELETE FROM BORROWEDBOOKS WHERE MEMBERID='%s' AND BOOKID=%d",
        		registeredMember.getMEMBERID(),
                registeredbook.getBOOKID());
     
        try{
        	stmt = conn.connect().createStatement();
        	stmt.executeUpdate(removeRegisteredbook);
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return false;
    }

    //calling function to get the list of registered books by Member
    @Override
    public ObservableList<Booklist> getRegisteredbooks(Members libraryMember) throws SQLException {
    	stmt = conn.connect().createStatement();
       
    	
    	//sql statement to get books registered by Member
        String getRegisteredbook = String.format("SELECT BOOKREGISTRY.BOOKID, "
        								+ "BOOKREGISTRY.BOOKNAME, "
        								+ "BOOKREGISTRY.BOOKEDITION, "
        								+ "BOOKREGISTRY.AUTHOR "
        								+ "FROM BOOKREGISTRY, BORROWEDBOOKS "
        								+ "WHERE BORROWEDBOOKS.MEMBERID='%s' "
        								+ "AND BORROWEDBOOKS.BOOKID=BOOKREGISTRY.BOOKID",
        								libraryMember.getMEMBERID());
       
        
        ResultSet outputResult = stmt.executeQuery(getRegisteredbook);

        ObservableList <Booklist> BookList = FXCollections.observableArrayList();
        while(outputResult.next()){
            int bookCode = outputResult.getInt("BOOKID");
            String bookTitle = outputResult.getString("BOOKNAME");
            int bookDuration = outputResult.getInt("BOOKEDITION");
            String AUTHOR = outputResult.getString("AUTHOR");
            Booklist book = new Booklist(bookCode, bookTitle, bookDuration, AUTHOR);
            BookList.add(book);
        }
        return BookList;
    }

    //function to get the list of Members registered to book
    @Override
    public ObservableList <Members> getRegisteredMembers(Booklist LIBRARYBOOK) throws SQLException {
        String query = String.format("SELECT LIBRARY_MEMBER.MEMBERID, "
        							+ "LIBRARY_MEMBER.MEMBERNAME, "
        							+ "LIBRARY_MEMBER.MEMBEREMAIL, "
        							+ "LIBRARY_MEMBER.MEMBERMOBILE "
        							+ "FROM BORROWEDBOOKS, LIBRARY_MEMBER "
        							+ "WHERE BORROWEDBOOKS.BOOKID=%d "
        							+ "AND LIBRARY_MEMBER.MEMBERID=BORROWEDBOOKS.MEMBERID",
        							LIBRARYBOOK.getBOOKID());

        stmt = conn.connect().createStatement();
        ResultSet outputResult = stmt.executeQuery(query);

        ObservableList <Members> MemberList = FXCollections.observableArrayList();

        while(outputResult.next()){
            String MemberId = outputResult.getString("MEMBERID");
            String MemberName = outputResult.getString("MEMBERNAME");
            String MemberEmail = outputResult.getString("MEMBEREMAIL");
            String MemberContactNumber = outputResult.getString("MEMBERMOBILE");
            MemberList.add(new Members(MemberId, MemberName, MemberEmail, MemberContactNumber));
        }
        return MemberList;
    }
    
    //function to insert new book
    @Override
	public boolean insertBook(Booklist newbook) {
		
		    
			boolean queryResult = false;
			try {
				
				//sql prepared statements to insert into new book
				String insertNewBook = "INSERT INTO BOOKREGISTRY(BOOKID,BOOKNAME,BOOKEDITION,AUTHOR) " + "VALUES (?,?,?,?)"; 
		        prepareStmt = conn.connect().prepareStatement(insertNewBook);
				prepareStmt.setInt(1,newbook.getBOOKID());
				prepareStmt.setString(2, newbook.getBOOKNAME());
				prepareStmt.setInt(3, newbook.getBOOKEDITION());
				prepareStmt.setString(4, newbook.getAUTHOR());
				
				String getbookDetails = String.format("SELECT BOOKID FROM BOOKREGISTRY WHERE BOOKID = '%s'", newbook.getBOOKID());
				
				stmt = conn.connect().createStatement();
	            ResultSet outputResult = stmt.executeQuery(getbookDetails);

		        if(outputResult.next()) {
		        	queryResult = false;
		        }
		        else {
		        	int insertRecordStatus = prepareStmt.executeUpdate();
		        	if(insertRecordStatus>0)		
		        		queryResult = true;
		        	else
		        		queryResult = false;
		        }
			
			}
			catch (SQLException sqlException) {
				sqlException.printStackTrace();
				return false;
			}
			if(queryResult) 
			{
				return true;
			}
			else
			{
				return false;
			}
		}
}
