package userclasses;
import java.sql.*;
import java.util.*;
import java.util.Date;
//Students class

public class Students extends Users{
	String email;
	Integer registrationId;
	Integer resit_year;
	String degreeId;
	Integer totalCredits;
	String difficulty;
	java.sql.Date startDate;
	java.sql.Date endDate;
  String personalTutor;
  
  public Students (String username, String title, String surname, String forename, String password, String email, 
  		Integer registrationId, Integer resit_year, String degreeId, Integer totalCredits, String difficulty, 
  		java.sql.Date startDate, java.sql.Date endDate, String personalTutor ) throws SQLException {
  	super(username, title, surname, forename, password);
  	this.email = email;
  	this.registrationId = registrationId;
  	this.resit_year = resit_year;
  	this.degreeId = degreeId;
  	this.totalCredits = totalCredits;
  	this.difficulty = difficulty;
  	this.startDate = startDate;
  	this.endDate = endDate;
  	this.personalTutor = personalTutor;
  	 Connection con = null;
     try {
         con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
         Statement stmt = null;
           String preparedStmt = "INSERT INTO student VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
         try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
         	con.setAutoCommit(false);
         	
             updateStmt.setString(1, email);
             updateStmt.setInt(2, registrationId);
             updateStmt.setInt(3, registrationId);
             updateStmt.setString(4, email);
             updateStmt.setInt(5, registrationId);
             updateStmt.setString(6, email);
             updateStmt.setDate(7, startDate);
             updateStmt.setDate(8, endDate);
             updateStmt.setString(9, personalTutor);
         }
         catch (SQLException ex) {
             ex.printStackTrace();
         }
         finally {
             if (stmt != null) stmt.close();
         }
     }
     catch (Exception ex) {
         ex.printStackTrace();
     }
     finally {
         if (con != null) con.close();
     }
  }
  
  public String getEmail() {
  	return email;
  }
  
  
  public java.sql.Date getstartDate() {
  	return startDate;
  }
  
  public java.sql.Date getEndDate() {
  	return endDate;
  }
  
  public static void main(String[] args)throws SQLException {
      // TODO code application logic here
      Connection con = null;  // a Connection object
      try{
          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
      } catch (Exception ex) {
          ex.printStackTrace();
      } 
      Statement stmt = null;
      try{
          stmt = con.createStatement();
      }catch (SQLException ex) {
          ex.printStackTrace();
      }finally {
          if (stmt != null)
              stmt.close();
      }
  }
}