package userclasses;
import java.sql.*;
import java.util.*;
import java.util.Date;

import academics.Grades;
//Students class

/*Methods
 * [X] display all modules
 * [X] display credits for each module
 * [X] display personal tutor
 * [X] display degree
 * [X] display total grade
 * */

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
  
  public void displayAllModules(String email) throws SQLException {
		 List<String> moduleList = Grades.getModuleList(email);
		 for (int i = 0; i < moduleList.size(); i++) {
			 System.out.println("Module Id = "+ moduleList.get(i));
		 }
	 }
  
  public static List<Integer> moduleCredits(String email) throws SQLException{
  	List<Integer> creditsList = new ArrayList<>();
  	List<String> moduleList = Grades.getModuleList(email);
  	for(int i =0; i < moduleList.size(); i++) {
  		 Connection con = null; 
	 		 try {
	 				 	con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
	 		      con.setAutoCommit(false);
	 		      Statement stmt = null;
	 		      String getCredits = String.format("SELECT credit_worth FROM module WHERE module_id = %s ", moduleList.get(i)) ;
	 		      String creditsString;
	 		      int credits;
	 		      ResultSet rs;
	 		      try (PreparedStatement pstmt = con.prepareStatement(getCredits)){
	 				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
	 		      	 	  creditsString = rs.getString(4);        // Retrieve the fourth column value
	 		      	 	  credits = Integer.parseInt(creditsString);
	 			      	  creditsList.add(credits);
	 				      	
	 				      	rs.close();                       // Close the ResultSet                  5 
	 				      	pstmt.close();                    
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
  	return creditsList;
  	
  }
	 
  public void displayModuleCredits(String email) throws SQLException {
  	List<Integer> creditsList = moduleCredits(email);
  	for (int i = 0; i < creditsList.size(); i++) {
  		System.out.println("Credit worth = " + creditsList.get(i));
  	}
	 }
  
  public void displayPersonalTutor(String email) throws SQLException {
		 Connection con = null; 
		 try {
				 	con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getpt = "SELECT personal_tutor FROM student WHERE email = ? " ;
		      String personalTutor;
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getpt)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	personalTutor = rs.getString(9);        // Retrieve the ninth column value
					      System.out.println("Personal tutor = " + personalTutor); // Print the column values
				      	
				      	rs.close();                       // Close the ResultSet                  5 
				      	pstmt.close();                    
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
	 
  
  public static String getDegreeId(String email) throws SQLException {
  	 String degreeId = null;
		 Connection con = null; 
		 try {
				 	con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getdegree = "SELECT degree_id FROM student WHERE email = ? " ;
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getdegree)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	degreeId = rs.getString(4);        // Retrieve the fourth column value
					      
				      	rs.close();                       // Close the ResultSet                  5 
				      	pstmt.close();                    
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
		 return degreeId;
	 }
  
  public void displayDegree(String email) throws SQLException {
  	String degreeId = getDegreeId(email);
  	String degreeName = null;
  	Connection con = null; 
		 try {
				 	con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getdegree = String.format("SELECT degree_name FROM degree WHERE degree_id = %s ", degreeId) ;
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getdegree)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	degreeName = rs.getString(5);        // Retrieve the fifth column value
					      System.out.println("Degree = " + degreeName + "(" + degreeId + ")");
				      	rs.close();                       // Close the ResultSet                  5 
				      	pstmt.close();                    
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
  
  public void displayTotalGrade(String email) throws SQLException {
		 Connection con = null; 
		 try {
				 	con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getTg = "SELECT overall_grade FROM year_grade WHERE email = ? " ;
		      String totalGrade;
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getTg)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	totalGrade = rs.getString(4);        // Retrieve the fourth column value
					      System.out.println("Degree = " + totalGrade); // Print the column values
				      	
				      	rs.close();                       // Close the ResultSet                  5 
				      	pstmt.close();                    
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
}