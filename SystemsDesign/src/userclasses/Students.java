package userclasses;
import java.sql.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
 * [X] display total rgrade
 * */

public class Students extends Users{
	String email;
	int registrationId;
	int resit_year;
	String degreeId;
	int totalCredits;
	String difficulty;
	Date startDate;
	Date endDate;
    String personalTutor;

  
  public Students (String username, String title, String surname, String forename, String password, String degreeId, int totalCredits, String difficulty, Date startDate, Date endDate, String personalTutor) throws SQLException {
  	super(username, title, surname, forename, password);
  	addStudent(username, degreeId, totalCredits, difficulty, startDate, endDate, personalTutor);

    this.password = password;
  	this.degreeId = degreeId;
  	this.totalCredits = totalCredits;
  	this.difficulty = difficulty;
  	this.startDate = startDate;
  	this.endDate = endDate;
  	this.personalTutor = personalTutor;
  	

  }
  public void addStudent(String username, String degreeId, int totalCredits, String difficulty, Date startDate, Date endDate, String personalTutor ) throws SQLException {
	     System.out.println("This is add STUDENT");
	  	 Connection con = null;
	  	 int registrationId = 0;
	  	 boolean preExisting = false;
	     try {
	         con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	         con.setAutoCommit(false);
	         Statement stmt = null;
	         String gradeTable = "INSERT INTO year_grade(email, level_of_study,"
	         		+ " period_of_study, current_level_of_study) VALUES (?,?,?,?)";
	         String checkExisting = "SELECT username, email FROM student WHERE username = ?";
	         String preparedStmt = "INSERT INTO student(email, username, resit_year, degree_id, "
	         		+ "total_credits, difficulty, start_date, end_date, personal_tutor)"
	         		+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	         String regId = "SELECT registration_id, start_date, end_date, "
	         		+ "year_grade.period_of_study FROM student LEFT JOIN year_grade ON "
	         		+ "student.email = year_grade.email WHERE username = ?";
	         try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt);
	        		 PreparedStatement checkStmt = con.prepareStatement(checkExisting);){
	        	 checkStmt.setString(1, username);
	        	 ResultSet existingUsers = checkStmt.executeQuery();
	        	 con.commit();
	        	 
	        	 while (existingUsers.next()) {
	        		 if (username.equals(existingUsers.getString("username"))) {
	        			 this.email = existingUsers.getString("email");
	        			 System.out.println("email" + this.email);
	        			 preExisting = true;
	        			 System.err.println("Student/username already exists.");
	        		 }
	        		 
	        	 }
	        	 if (!preExisting) {
	        		 System.out.println("inside not preexisting");
	        		 email = emailGen(surname, forename);
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
	        	 
	             
	         }
	         catch (SQLException ex) {
	             ex.printStackTrace();
	         }
	         try (PreparedStatement update = con.prepareStatement(gradeTable);
	             PreparedStatement getRegId = con.prepareStatement(regId)) {
	        	 String periodStudy = "";
                 
                 getRegId.setString(1, username);
                 ResultSet regIdSet = getRegId.executeQuery();
                 while(regIdSet.next()) {
                	 registrationId = regIdSet.getInt("registration_id");
                	 startDate = regIdSet.getDate("start_date");
                	 endDate = regIdSet.getDate("end_date");
                	 periodStudy = regIdSet.getString("period_of_study");
                	 this.registrationId = registrationId;
                	 this.startDate = startDate;
                	 this.endDate = endDate;
                 }
                 
            	
                 if (!preExisting) {
                	 update.setString(1, email);
                     update.setString(2, "1");
                     update.setString(3, generatePeriodOfStudy(registrationId));
                     update.setString(4, "1");
                     update.executeUpdate();
                     con.commit();
                 }

                 System.out.println("Hello");
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

public Date getstartDate() {
	  return startDate;
}
	  
public Date getEndDate() {
	  return endDate;
}

public int getTotalCredits() {
	  return totalCredits;
}

public String getDegreeId() {
	  return degreeId;
}

public String getPersonalTutor() {
	return personalTutor;
}

public String getDifficulty() throws SQLException {
	Connection con = null;
	String difficulty = "";
    try {
        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
        con.setAutoCommit(false);
        Statement stmt = null;
        String diff = "SELECT difficulty FROM student WHERE email = ?";
        try (PreparedStatement preparedStmt = con.prepareStatement(diff)){
            preparedStmt.setString(1, email);
            ResultSet rs = preparedStmt.executeQuery();
            while (rs.next()) {
            	difficulty = rs.getString("difficulty");
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
	return difficulty;
}


public int getRegistrationId() throws SQLException {
	  Connection con = null;
    Statement stmt = null;

	     try {
	         con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	         con.setAutoCommit(false);

	         String regId = "SELECT registration_id FROM student WHERE username = ?";

	         try (PreparedStatement checkRegId = con.prepareStatement(regId)){
	        	 checkRegId.setString(1, username);
	             ResultSet rs = checkRegId.executeQuery();
	             con.commit();
	              
	             while (rs.next()) {
		             registrationId = rs.getInt(1);
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
	  return registrationId;
}

public int getInitialGrade(String module) throws SQLException {
	Connection con = null;
    Statement stmt = null;
    int initialGrade = 0;

	     try {
	         con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	         con.setAutoCommit(false);

	         String initGrade = "SELECT initial_grade FROM module_grade WHERE email = ? AND module_id = ?";
	         try (PreparedStatement getInit = con.prepareStatement(initGrade)){
	        	 getInit.setString(1, email);
	        	 getInit.setString(2, module);
	             ResultSet rs = getInit.executeQuery();
	             con.commit();
	              
	             while (rs.next()) {
		             initialGrade = rs.getInt(1);
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
	  return initialGrade;
}

public int getResitGrade(String module) throws SQLException {
	Connection con = null;
    Statement stmt = null;
    int resitGrade = 0;

	     try {
	         con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	         con.setAutoCommit(false);

	         String reGrade = "SELECT resit_grade FROM module_grade WHERE email = ? AND module_id = ?";
	         try (PreparedStatement getRe = con.prepareStatement(reGrade)){
	        	 getRe.setString(1, email);
	        	 getRe.setString(2, module);
	             ResultSet rs = getRe.executeQuery();
	             con.commit();
	              
	             while (rs.next()) {
		             resitGrade = rs.getInt(1);
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
	  return resitGrade;
}

public boolean getResitYear() throws SQLException {
	Connection con = null;
    Statement stmt = null;
    boolean resitYear = false;

	     try {
	         con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	         con.setAutoCommit(false);

	         String reGrade = "SELECT resit_year FROM student WHERE email = ?";
	         try (PreparedStatement getRe = con.prepareStatement(reGrade)){
	        	 getRe.setString(1, email);
	             ResultSet rs = getRe.executeQuery();
	             con.commit();
	              
	             while (rs.next()) {
	            	 resitYear = rs.getBoolean(1);
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
	  return resitYear;
}


public String generatePeriodOfStudy(int registrationId) throws SQLException{
    String periodOfStudy = "";
    char label = 'A';
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String checkExisting = "SELECT period_of_study FROM year_grade WHERE email = ?";
            try (PreparedStatement getExisting = con.prepareStatement(checkExisting)){
                getExisting.setString(1, email);
                ResultSet existing = getExisting.executeQuery();
                con.commit();
                
                SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
                
                periodOfStudy = label + format.format(startDate) + format.format(endDate) + registrationId;
                while (existing.next()) {
                	while(periodOfStudy.equals(existing.getString("period_of_study"))) {
                		label++;
                		periodOfStudy = label + format.format(startDate) + format.format(endDate) + registrationId;
                	}
                }
            }
            catch (SQLException ex) {
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
        System.out.println("period of study: " + periodOfStudy);
    return periodOfStudy;
}

  

	  

  public String emailGen(String surname, String forename) throws SQLException{
    String initials = forename.substring(0,1).toUpperCase();
    int a = 1;
    String rest = "@sheff.ac.uk";
    DecimalFormat formatter = new DecimalFormat("00");
    for (int i = 0; i < forename.length(); i++) {
    	if (forename.charAt(i) == ' ') {
    		initials += Character.toUpperCase(forename.charAt(i + 1)); 
    	}
    }
    
    String email = initials + surname + formatter.format(a) + rest; //create email from forename initials and surname and unique 2 digit number
    Connection con = null;

    try {
        con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
        con.setAutoCommit(false);
        Statement stmt = null;
        String allEmails = "SELECT email FROM student INNER JOIN user WHERE student.username = user.username AND forename = ? AND surname = ?";
        String setEmail = "UPDATE student SET email = ? WHERE username = ?";
        try (PreparedStatement preparedStmt = con.prepareStatement(allEmails);
        		PreparedStatement setStmt = con.prepareStatement(setEmail)){
            preparedStmt.setString(1, forename);
            preparedStmt.setString(2, surname);
            ResultSet rs = preparedStmt.executeQuery();
            System.out.println(email);
            System.out.println(a);
            while (rs.next()) {
                String retrievedEmail = rs.getString("email");
                while (retrievedEmail.toLowerCase().equals(email.toLowerCase())){
                    a++;
                
                setStmt.setString(1, email);
                setStmt.setString(2, username);
                setStmt.executeUpdate();
                con.commit();
                System.out.println(a);
                System.out.println(retrievedEmail);
                email = initials + surname + formatter.format(a);
                System.out.println(email);
                }
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
  
  
  public ArrayList<ArrayList<String>> displayStudentView(String email) throws SQLException {
	  ArrayList<ArrayList<String>> list= new ArrayList<ArrayList<String>>();

	  Connection con = null; 
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String studentViewTable ="SELECT module_grade.module_id, initial_grade, resit_grade, module.module_name, "
		      		+ "module.credit_worth, module.department_id, module.pass_grade FROM module_grade JOIN "
		      		+ "module ON module_grade.module_id = module.module_id WHERE module_grade.level_of_study = ? AND "
		      		+ "module_grade.email = ?";
		      
		      ResultSet rs;

		      try (PreparedStatement pstmt = con.prepareStatement(studentViewTable)){
		    	  	pstmt.setString(1, Grades.getCurrentLevelOfStudy(email));
		    	  	pstmt.setString(2, email);
				    rs = pstmt.executeQuery();  
				    // Get the result table from the query  3 
					
		      	 	while (rs.next()) {
		      		    ArrayList<String> row = new ArrayList<String>();
		      	 		row.add(rs.getString(1));					//module id
		      	 		row.add(Integer.toString(rs.getInt(2)));	//initial grade		      	 		
		      	 		row.add(Integer.toString(rs.getInt(3)));	//resit grade
		      	 		row.add(rs.getString(4));					//module name
		      	 		row.add(Integer.toString(rs.getInt(5)));	//credits worth
		      	 		row.add(rs.getString(6));					//department id
		      	 		row.add(Integer.toString(rs.getInt(7)));	//pass grade
		      	 		list.add(row);
		      	 		System.out.println("row: " + row);
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
		 System.out.println(list);
	  return list;
  }
  
  public ArrayList<ArrayList<String>> displayStudentDetails(String email) throws SQLException {
	  ArrayList<ArrayList<String>> list= new ArrayList<ArrayList<String>>();
	  DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	  Connection con = null; 
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String studentViewTable ="SELECT user.username, user.forename, user.surname, user.account_type,"
		      		+ "student.difficulty, student.total_credits, student.start_date, student.end_date, degree.degree_id, "
		      		+ "degree.degree_name FROM user LEFT JOIN(student, degree) ON (user.username = student.username AND "
		      		+ " student.degree_id = degree.degree_id) WHERE email = ?";
		      
		      ResultSet rs;

		      try (PreparedStatement pstmt = con.prepareStatement(studentViewTable)){
		    	  	pstmt.setString(1, email);
				    rs = pstmt.executeQuery();  					
		      	 	while (rs.next()) {
		      		    ArrayList<String> row = new ArrayList<String>();
		      	 		row.add(rs.getString("username"));
		      	 		row.add(rs.getString("forename"));     	 		
		      	 		row.add(rs.getString("surname"));	
		      	 		row.add(rs.getString("account_type"));	
		      	 		row.add(rs.getString("difficulty"));
		      	 		row.add(Integer.toString(rs.getInt("total_credits")));
		      	 		row.add(df.format(rs.getDate("start_date")));	
		      	 		row.add(df.format(rs.getDate("end_date")));
		      	 		row.add(rs.getString("degree_id"));
		      	 		row.add(rs.getString("degree_name"));
		      	 		list.add(row);
		      	 		System.out.println(row);
		 
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
		 System.out.println(list);
	  return list;
  }
  
  
  public String[] getAllLevelsOfStudy(String email) throws SQLException {
	  LinkedHashSet<String> list = new LinkedHashSet<String>();
	  Connection con = null; 
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String levels ="SELECT level_of_study FROM module_grade WHERE email = ?";
		      
		      ResultSet rs;

		      try (PreparedStatement pstmt = con.prepareStatement(levels)){
		    	  	pstmt.setString(1, email);
				    rs = pstmt.executeQuery();  					
		      	 	while (rs.next()) {
		      		    list.add(rs.getString("level_of_study"));
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
		 String[] arr = list.toArray(new String[list.size()]);

	  return arr;
  }
  
  public static List<Integer> moduleCredits(String email, String levelOfStudy, List<String> moduleList) throws SQLException{
  	List<Integer> creditsList = new ArrayList<>();
  	for(int i =0; i < moduleList.size(); i++) {
  		 Connection con = null; 
	      Statement stmt = null;

	 		 try {
		          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	 		      con.setAutoCommit(false);
	 		      String getCredits = "SELECT credit_worth FROM module WHERE module_id = ? ";
	 		      String creditsString;
	 		      int credits;
	 		      ResultSet rs;
	 		      try (PreparedStatement pstmt = con.prepareStatement(getCredits)){
	 		    	      pstmt.setString(1, moduleList.get(i));
	 				      rs = pstmt.executeQuery(); 
	 				      con.commit();
	 				      
	 				      while(rs.next()) {
	 				    	 creditsString = rs.getString("credit_worth");        
		 		      	 	 credits = Integer.parseInt(creditsString);
		 			      	 creditsList.add(credits);
	 				      }
	 		      	 	 
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
  		}
  	return creditsList;
  	
  }
	 
  public void displayModuleCredits(String email, String levelOfStudy) throws SQLException {
  	List<Integer> creditsList = moduleCredits(email, levelOfStudy, Grades.getModuleList(email, levelOfStudy));
  	for (int i = 0; i < creditsList.size(); i++) {
  		System.out.println("Credit worth = " + creditsList.get(i));
  	}
	 }
  
 public String getDepartmentId() throws SQLException {
	 String deptId = getDegreeId(email).substring(0,3);
	 return deptId;
 }
	 
  
   public static String getDegreeId(String email) throws SQLException {
  	 String degreeId = null;
		 Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getdegree = "SELECT degree_id FROM student WHERE email = ? " ;
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getdegree)){
				  pstmt.setString(1, email);    	
		    	  rs = pstmt.executeQuery();        // Get the result table from the query  3 
				  
		    	  while (rs.next()) {
			    	  degreeId = rs.getString("degree_id");        // Retrieve the fourth column value
		    	  }
					      
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
		        catch (SQLException ex) {
		            System.err.println("Error code: " + ex.getErrorCode() 
		            + "// Not enough data entered for student to display info.");
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
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getdegree = "SELECT degree_name FROM degree WHERE degree_id = ? ";
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getdegree)){
				  pstmt.setString(1, degreeId);    	
		    	  rs = pstmt.executeQuery();   
		    	  // Get the result table from the query  3 
				  while (rs.next()) {
			    	  degreeName = rs.getString("degree_name");        // Retrieve the fifth column value

				  }
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
			con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
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
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
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
  
  public void updatePeriodOfStudy() throws SQLException {
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
      

  public Double getYearResitGrade(String levelOfStudy) throws SQLException {
	  Connection con = null;
      Double resitGrade = 0.0;
      try {
          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
          con.setAutoCommit(false);
          Statement stmt = null;
          String preparedStmt = "SELECT resit_grade FROM year_grade WHERE email = ?";
          try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
              stmt = con.createStatement();
              updateStmt.setString(1, email);
              ResultSet rs = updateStmt.executeQuery();
              con.commit();
              while(rs.next()) {
            	  resitGrade = rs.getDouble("resit_grade");
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
      return resitGrade;
  }
  public void addYearGrade(Double yearGrade, String levelOfStudy, boolean progress, Double resitGrade) throws SQLException {
	  Connection con = null;
	  int currLev = Integer.parseInt(Grades.getCurrentLevelOfStudy(email));
	  int levStudy = Integer.parseInt(levelOfStudy);
      //System.out.println(permission.get(newPermission));
	  if (progress) {
		  currLev += 1;
		  levStudy += 1;
	  }
      try {
          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
          con.setAutoCommit(false);
          Statement stmt = null;
          String preparedStmt = "INSERT INTO year_grade(email, level_of_study, current_level_of_study, "
          		+ "period_of_study, overall_grade, progress_to_next_level, resit_grade) VALUES(?,?,?,?,?,?,?)";
          String curr = "UPDATE year_grade SET current_level_of_study = ? WHERE email = ?";
          try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt);
        		  PreparedStatement currStmt = con.prepareStatement(curr)){
              stmt = con.createStatement();
              updateStmt.setString(1, email);
              updateStmt.setString(2, Integer.toString(levStudy));
              updateStmt.setString(3,  Integer.toString(currLev));
              updateStmt.setString(4, generatePeriodOfStudy(registrationId));
              updateStmt.setDouble(5, yearGrade);
              updateStmt.setBoolean(6, progress);
              if (resitGrade != null) {
                  updateStmt.setDouble(7, resitGrade);
              }
              else {
            	  updateStmt.setDouble(7, java.sql.Types.NULL);
              }
              updateStmt.executeUpdate();
              currStmt.setString(1,Integer.toString(currLev));
              currStmt.setString(2, email);
              currStmt.executeUpdate();
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
  
  public ArrayList<ArrayList<String>> displayByLevelOfStudy(String email, String levelOfStudy) throws SQLException {
	   	 ArrayList<ArrayList<String>> list= new ArrayList<ArrayList<String>>();

	  	  Connection con = null; 
	  		 try {
	  	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	  		      con.setAutoCommit(false);
	  		      Statement stmt = null;
	  		      String studentViewTable ="SELECT module.credit_worth, module.department_id, module.module_id, module_name, initial_grade, resit_grade,"
	  		      		+ " module.pass_grade FROM module_grade LEFT JOIN module "
	  		      		+ "ON module_grade.module_id = module.module_id WHERE email = ? AND level_of_study = ? ";
	  		      
	  		      ResultSet rs;

	  		      try (PreparedStatement pstmt = con.prepareStatement(studentViewTable)){
	  		    	  	pstmt.setString(1, email);
	  		    	  	pstmt.setString(2, levelOfStudy);
	  				    rs = pstmt.executeQuery();  
	  				    // Get the result table from the query  3 
	  					
	  		      	 	while (rs.next()) {
	  		      		    ArrayList<String> row = new ArrayList<String>();
	  		      	 		row.add(rs.getString("module_id"));				
	  		      	 		row.add(rs.getString("initial_grade"));	
	  		      	 		row.add(rs.getString("resit_grade"));
	  		      	 		row.add(rs.getString("module_name"));
	  		      	 		row.add(rs.getString("credit_worth"));	   		
	  		      	 		row.add(rs.getString("department_id"));	   		
	  		      	 		row.add(rs.getString("pass_grade"));
	  		      	 		list.add(row);
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
	  		 System.out.println(list);
	  	  return list;
	   }
}