package userclasses;

import java.sql.*;
import java.util.Dictionary;
import java.util.Hashtable;

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
	
	 // BUS : business school, COM : computer science, PSY : psychology, LAN : modern languages
	 static Dictionary<String, String> degreeCode = new Hashtable<String, String>();
	 
	 static {
		 degreeCode.put("Business School", "BUS");
	     degreeCode.put("Computer Science", "COM");
	     degreeCode.put("Psychology", "PSY");
	     degreeCode.put("Modern Language", "LAN");
	 }
     
	
	/**
	 * Assigns student or teacher to a user account
	 * @param type				- accountType
	 * @param email				- unique ID of the user
	 * @throws SQLException
	 */
	 
	 public Registrars (String username, String title, String surname, String forename, String password)throws SQLException {
			super(username, title, surname, forename, password);
		}
	     
	 public void addAccountType(String type, String username) throws SQLException {
	        Connection con = null;
 	        try {
 	           con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String preparedStmt = "UPDATE user SET accoutType = ? WHERE username = ?";
	            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
	                stmt = con.createStatement();
	                if (type == "student") {
	                	updateStmt.setString(1, type);
	                	updateStmt.setString(2, username);
	                	con.commit();
	                }
	                else System.err.println("Not an accepted accountType.");
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
	 
	 public void deleteStudent(String email) throws SQLException {
		 Connection con = null;
	        try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String preparedStmt = "SELECT accountType FROM user WHERE email = ?";
	            String deleteStmt = "DELETE FROM user WHERE email = ?";
	            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt);
	            		PreparedStatement delStmt = con.prepareStatement(deleteStmt)){
	                updateStmt.setString(1, email);
	                ResultSet type = updateStmt.executeQuery();
	                if (type.getString(1) == UserTypes.STUDENT.toString()) delStmt.setString(1, email);
	                else System.err.println("Cannot delete a non-student user.");
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
	                if (type.getString(1) == UserTypes.STUDENT.toString()) datStmt.setDate(1, date);
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
		            if (type.getString(1) == UserTypes.STUDENT.toString()) {
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
	            	if (isStudent.getString(1) == UserTypes.STUDENT.toString()) {
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
	 
	 public void checkModuleSum(String moduleId, String email, String entryLevel) throws SQLException{
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
                	if (entryLevel == "U") {
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
}