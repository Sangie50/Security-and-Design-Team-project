package userclasses;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import java.sql.Date;

import academics.Grades;
//Students class
import userclasses.Users;

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
	Date startDate;
	Date endDate;
        String personalTutor;
  
  public Students (String username, String title, String surname, String forename, String password, String degreeId, int totalCredits, String difficulty, Date startDate, Date endDate, String personalTutor ) throws SQLException {
  	super(username, title, surname, forename, password);
  	
  	email = emailGen(surname, forename, username);
  	this.degreeId = degreeId;
  	this.totalCredits = totalCredits;
  	this.difficulty = difficulty;
  	this.startDate = startDate;
  	this.endDate = endDate;
  	this.personalTutor = personalTutor;
  	
        //java.sql.Date stDate = new java.sql.Date(startDate.getTime()); 
        //java.sql.Date enDate = new java.sql.Date(endDate.getTime());

  	 Connection con = null;
     try {
         con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
         con.setAutoCommit(false);
         Statement stmt = null;
         String preparedStmt = "INSERT INTO student(email, username, resit_year, degree_id, total_credits, difficulty, start_date, end_date, personal_tutor) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
         try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
             updateStmt.setString(1, email);
             updateStmt.setString(2, username);
             updateStmt.setBoolean(3, false);
             updateStmt.setString(4, degreeId);
             updateStmt.setInt(5, totalCredits);
             updateStmt.setString(6, difficulty);
             updateStmt.setDate(7, startDate);
             updateStmt.setDate(8, endDate);
             updateStmt.setString(9, personalTutor);
             
             updateStmt.executeUpdate();
             con.commit();
             
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
  
  public Date getstartDate() {
	  return startDate;
  }
	  
  public Date getEndDate() {
	  return endDate;
  }
  
  public int getRegistrationId() {
	  return registrationId;
  }
  
  public String getDegreeId() {
	  return degreeId;
  }
	  
  public String emailGen(String surname, String forename, String username) throws SQLException{
	  String[] name = forename.split(" ");
	  String initials = "";
	  String email = "";
	  int a = 1;
	  DecimalFormat formatter = new DecimalFormat("00");
	  for (int i = 0; i < name.length; i++) {
	      initials = name[i];
	    }
	  
	  email = initials + surname + formatter.format(a); //create email from forename initials and surname and unique 2 digit number
	  Connection con = null;
	  
	  try {
          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
          con.setAutoCommit(false);
          Statement stmt = null;
          String allEmails = "SELECT email FROM student INNER JOIN user WHERE student.username = user.username AND forename = ? AND surname = ?";
          try (PreparedStatement preparedStmt = con.prepareStatement(allEmails)){
        	  preparedStmt.setString(1, forename);
                  preparedStmt.setString(2, surname);
        	  ResultSet rs = preparedStmt.executeQuery();
        	  con.commit();
        	  while(rs.getString(1).equals(email)) {
        		  a += 1;
        		  email = initials + surname + formatter.format(a);
        	  }
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
	  return email;
  }
  
  public String getEmail() {
  	return email;
  	
  }
  
  public List<String> displayAllModules(String email, String levelOfStudy) throws SQLException {
		 List<String> moduleList = Grades.getModuleList(email, levelOfStudy);
		 for (int i = 0; i < moduleList.size(); i++) {
			 System.out.println("Module Id = "+ moduleList.get(i));
		 }
		 return moduleList;
	 }
  
  public ArrayList<ArrayList<String>> displayStudentView(String email) throws SQLException {
	  ArrayList<ArrayList<String>> list= new ArrayList<ArrayList<String>>();
	  ArrayList<String> row = new ArrayList<String>();
	  System.out.println("DISPLAY STUDENT STUFF...");
	  Connection con = null; 
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String studentViewTable ="SELECT module_grade.module_id, initial_grade, resit_grade, module.module_name, "
		      		+ "module.credit_worth, module.department_id, module.pass_grade FROM module_grade LEFT JOIN module "
		      		+ "ON module_grade.module_id = module.module_id WHERE email = ?";
		      
		      ResultSet rs;

		      try (PreparedStatement pstmt = con.prepareStatement(studentViewTable)){
		    	  	pstmt.setString(1, email);
				    rs = pstmt.executeQuery();  
				    // Get the result table from the query  3 
					System.out.println("DASDFADLSFJAS;DLFKJADF;ALKJSDF");
		      	 	while (rs.next()) {
		      	 		row.clear();
		      	 		System.out.println("MODULE ID" + rs.getString(1));
		      	 		row.add(rs.getString(1));					//module id
		      	 		row.add(Integer.toString(rs.getInt(2)));	//initial grade		      	 		
		      	 		row.add(Integer.toString(rs.getInt(3)));	//resit grade
		      	 		row.add(rs.getString(4));					//module name
		      	 		row.add(Integer.toString(rs.getInt(5)));	//credits worth
		      	 		row.add(rs.getString(6));					//department id
		      	 		row.add(Integer.toString(rs.getInt(7)));	//pass grade
		      	 		list.add(row);
		      	 		System.out.println("11111111 Size of row of list: " + list.get(0).size()); // 7

		      	 		System.out.println("Size of row of list: " + list.get(0).size()); // 0
				    }
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
	  return list;
  }
  
  public static List<Integer> moduleCredits(String email, String levelOfStudy, List<String> moduleList) throws SQLException{
  	List<Integer> creditsList = new ArrayList<>();
  	for(int i =0; i < moduleList.size(); i++) {
  		 Connection con = null; 
	 		 try {
		          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
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
	 
  public void displayModuleCredits(String email, String levelOfStudy) throws SQLException {
  	List<Integer> creditsList = moduleCredits(email, levelOfStudy, Grades.getModuleList(email, levelOfStudy));
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
  public static String getDegreeName(String email) throws SQLException {
	  String degreeName = null;
	  String degreeId = getDegreeId(email);
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
	 return degreeName;
  }
  public void displayDegree(String email) throws SQLException {
  	String degreeName = getDegreeName(email);
    String degreeId = getDegreeId(email);
  	System.out.println("Degree = " + degreeName + "(" + degreeId + ")");
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
  public static Boolean getProgressToNextLevel(String email) throws SQLException {
	  Boolean progress_to_next_level = null;
	  Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getProgress = "SELECT progress_to_next_level FROM year_grade WHERE email = ? " ;
		      String totalGrade;
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getProgress)){
				      	rs = pstmt.executeQuery();       
				      	progress_to_next_level = rs.getBoolean(6);        
				      	
				      	rs.close();                       
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
		 return progress_to_next_level;
  }
  
  public static String getPeriodOfStudy(String email) throws SQLException {
	  String periodOfStudy = null;
	  Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getPeriodOfStudy = String.format("SELECT period_of_study FROM year_grade WHERE email = %s" , email);    
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getPeriodOfStudy)){
				      	rs = pstmt.executeQuery();        
				      	periodOfStudy = rs.getString(4);      
				      	
				      	rs.close();                       
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
		 return periodOfStudy;
  }
   
  public static String nextPeriodOfStudy(String email) throws SQLException {
	  Boolean progress_to_next_level = getProgressToNextLevel(email);
	  String period_of_study = getPeriodOfStudy(email);
	  String label = period_of_study.substring(0,1);
	  String updateLabel = "";
	  String rest_pos = period_of_study.substring(1);
	  String update_pos = "";
	  
	  if (progress_to_next_level == true) {
		 if(label == "A") {
			 updateLabel = "B";
			 update_pos = updateLabel + rest_pos;
		 }
		 else if(label == "B") {
			 updateLabel = "C";
			 update_pos = updateLabel + rest_pos;
		 }
		 else if(label == "C") {
			 updateLabel = "D";
			 update_pos = updateLabel + rest_pos;
		 }
		 else if(label == "C") {
			 updateLabel = "D";
			 update_pos = updateLabel + rest_pos;
		 }
		 else {
			 update_pos = period_of_study;
		 }
	  }
	  return update_pos;
  }
  
  public void updatePeriodOfStudy(String email) throws SQLException {
	  String update_pos = nextPeriodOfStudy(email);
	  Connection con = null;
      //System.out.println(permission.get(newPermission));
      try {
          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
          con.setAutoCommit(false);
          Statement stmt = null;
          String preparedStmt = "UPDATE year_grade SET period_of_study = ? WHERE email = ?";
          try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
              stmt = con.createStatement();
              updateStmt.setString(1, update_pos);
              updateStmt.setString(2, email);
              updateStmt.executeUpdate();
              con.commit();
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