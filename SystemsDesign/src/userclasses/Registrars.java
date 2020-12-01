package userclasses;

import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.List;

import userclasses.Users.UserTypes;

public class Registrars extends Users {
	/*
	What needs doing (methods):
	[x]Add students
	[x]Add teachers
	[x]Remove students
	[x]Register initial period of study
	[?]Link to degree
	[x]Add optional modules
	[x]Remove optional modules
	[x]Check registration is complete
	[GUI]Check student's modules
	[x]Check credits of modules sum to 120
	[GUI]Display results

	Attributes to add
	inherits from user class
	*/
	
	/**
	 * Assigns student or teacher to a user account
	 * @param type				- accountType
	 * @param email				- unique ID of the user
	 * @throws SQLException
	 */
	 
	 public Registrars (String username, String title, String surname, String forename, String password)throws SQLException {
			super(username, title, surname, forename, password);
			this.password = password;
			Admins.updatePermission(username, UserTypes.REGISTRAR.toString());
		}
	     
	 
	 public static void deleteStudent(String username) throws SQLException {
		 Connection con = null;
	        try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String preparedStmt = "SELECT account_type FROM user WHERE username = ?";
	            String deleteStmt = "DELETE FROM student WHERE username = ?";
	            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt);
	            		PreparedStatement delStmt = con.prepareStatement(deleteStmt)){
	                updateStmt.setString(1, username);
	                ResultSet type = updateStmt.executeQuery();
	                while (type.next()) {
	                	 if (type.getString(1).equals(UserTypes.STUDENT.toString())) {
	 	                	delStmt.setString(1, username);
	 	                	delStmt.executeUpdate();
	 	                	Admins.updatePermission(username, UserTypes.UNASSIGNED.toString());
	 						System.out.println("Student deleted");
	 	                }
	 	                else System.err.println("Cannot delete a non-student user.");
	 	                con.commit();
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
	 }
	 
	 public static void addStudent(String username, String degreeId, int totalCredits, String difficulty, Date startDate, Date endDate, String personalTutor) throws SQLException {
			Connection con = null;
		    Statement stmt = null;
		    String title = "";
		    String surname = "";
		    String forename = "";
		    String password = "";
		    

			     try {
			         con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
			         con.setAutoCommit(false);

			         String userInfo = "SELECT title, surname, forename, password FROM user WHERE username = ?";
			         try (PreparedStatement getUserInfo = con.prepareStatement(userInfo)){
			        	 getUserInfo.setString(1, username);
			             ResultSet rs = getUserInfo.executeQuery();
			             con.commit();
			              
			             while (rs.next()) {
				             title = rs.getString("title");
				             surname = rs.getString("surname");
				             forename = rs.getString("forename");
				             password = rs.getString("password");

			             }
			             new Students(username, title, surname, forename, password,
			            		degreeId, totalCredits, difficulty, startDate, endDate, personalTutor);
			             Admins.updatePermission(username, UserTypes.STUDENT.toString());
			             System.out.println("new student created!");
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
	 
	 public void registerInitialPeriodOfStudy(String email, java.sql.Date date) throws SQLException {
		 Connection con = null;
	        try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String preparedStmt = "SELECT accountType FROM user WHERE email = ?";
	            String dateStmt = "UPDATE student SET startDate = ? WHERE email = ?";
	            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt);
	            		PreparedStatement datStmt = con.prepareStatement(dateStmt)){
	                updateStmt.setString(1, email);
	                ResultSet type = updateStmt.executeQuery();
	                if (type.getString(1).equals(UserTypes.STUDENT.toString())) datStmt.setDate(1, date);
	                else System.err.println("Cannot add start date to a non-student user.");
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
	 
	 public void linkDegree(String email, String degreeId) throws SQLException {
		 Connection con = null;
	        try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String preparedStmt = "SELECT accountType FROM user WHERE email = ?";
	            String setDegree = "UPDATE student SET degreeId = ? WHERE email = ?";
	            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt);
	            		PreparedStatement degreeStmt = con.prepareStatement(setDegree)){
	            	updateStmt.setString(1, email);
		            ResultSet type = updateStmt.executeQuery();
		            if (type.getString(1).equals(UserTypes.STUDENT.toString())) {
		            	degreeStmt.setString(1, degreeId);
		            	degreeStmt.setString(2, email);
		            }
	                else System.err.println("Cannot assign degrees to a non-student user.");
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
	 
	 //Add for module grade a new row linking a new module and student email
	 public void linkModuleToStudent(String email, String moduleId) throws SQLException {
		 Connection con = null; 
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String getModuleId = "INSERT INTO module_grade (module_id, email) VALUES (?,?)";
	            try (PreparedStatement updateStmt = con.prepareStatement(getModuleId)){
	                updateStmt.setString(1, moduleId);
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
	 
	 public void linkModuleToTeacher(String employeeNo, String moduleId) throws SQLException {
		 Connection con = null; 
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String getModuleId = "INSERT INTO module_teacher (employeeNo, moduleId) VALUES (?,?)";
	            try (PreparedStatement updateStmt = con.prepareStatement(getModuleId)){
	                updateStmt.setString(1, employeeNo);
	                updateStmt.setString(2, moduleId);
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
	 
	 public void deleteModule(String moduleId) throws SQLException {
		 Connection con = null; 
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String preparedStmt = "DELETE FROM module_grade WHERE module_id = ?";
	            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
	                updateStmt.setString(1, moduleId);
	                updateStmt.execute();
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
	 
	 public void checkRegistration (String username) throws SQLException {
		 Connection con = null; 
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String checkStudent = "SELECT accountType FROM user WHERE username = ?";
	            String hasRegNo = "SELECT registrationId, email FROM student WHERE username = ?";
	            try (PreparedStatement updateStudent = con.prepareStatement(checkStudent);
	            		PreparedStatement updateRegNo = con.prepareStatement(hasRegNo)){
	            	updateStudent.setString(1, username);
	            	ResultSet isStudent = updateStudent.executeQuery();
	            	if (isStudent.getString(1).equals(UserTypes.STUDENT.toString())) {
	            		updateRegNo.setString(1, username);
	            		ResultSet registered = updateRegNo.executeQuery();
	            		if(registered.getString(1) != null && registered.getString(2) != null) System.out.println("Student is successfully registered.");
	            		else System.err.println("Student is missing a registration number or email");
	            	}
	            	else System.err.print("A non-student cannot be registered");
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
	 
	 public void checkCredits(String moduleId, String email, String entryLevel) throws SQLException{
		 Connection con = null; 
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String credit = "SELECT creditWorth FROM module WHERE moduleId = ?";
	            try (PreparedStatement updateCredit = con.prepareStatement(credit)){
	            	int totalCredits = 0;
	                updateCredit.setString(1, moduleId);
	                //check whether undergrad or postgrad
	                ResultSet maxCredits = updateCredit.executeQuery();
                	while (maxCredits.next()) totalCredits += maxCredits.getInt(1);
                	if (entryLevel.equals("U")) {
	                	if (totalCredits != 120) System.err.println("Credits do not sum to 120. Invalid module selections.");
	                	else System.out.println("Credits sum to 120.");
                	}
                	else {
                		if(totalCredits != 180) System.err.println("Credits do not sum to 180. Invalid module selections");
                		else System.out.println("Credits sum to 180");
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
		 
	 }
	 

	 public String[] getModulesList(String email) throws SQLException {
		 Connection con = null; 
		 ArrayList<String> m = new ArrayList<String>();
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String modules = "SELECT module_id FROM module_grade WHERE email = ?";
	            try (PreparedStatement modulesList = con.prepareStatement(modules)){
	                modulesList.setString(1, email);
	                ResultSet modulesSet = modulesList.executeQuery();
                	while (modulesSet.next()) {
                		m.add(modulesSet.getString("module_id"));
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
		 String[] ar = new String[m.size()];
		 for (int i = 0; i < m.size(); i++) {
			 ar[i] = m.get(i);
			 System.out.println(m.get(i));
		 }
		 return ar;
	 }
	 
	 public static String[] getOptionalModulesList(String email) throws SQLException {
		 Connection con = null; 
		 LinkedHashSet<String> m = new LinkedHashSet<String>();
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String modules = "SELECT core_modules.module_id FROM core_modules INNER JOIN module_grade ON core_modules.module_id != module_grade.module_id WHERE email = ?";
	            try (PreparedStatement modulesList = con.prepareStatement(modules)){
	                modulesList.setString(1, email);
	                ResultSet modulesSet = modulesList.executeQuery();
                	while (modulesSet.next()) {
                		m.add(modulesSet.getString("core_modules.module_id"));
                	}
           		 System.out.println(m);

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
		 String[] ar = m.toArray(new String[m.size()]);
//		 }
		 return ar;
	 }
}