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
	[-]Check registration is complete
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
	 public void addAccountType(String type, String email) throws SQLException {
	        Connection con = null;
	        //System.out.println(permission.get(newPermission));
	        try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
	            Statement stmt = null;
	            try {
	                stmt = con.createStatement();
	                if (type == "student") stmt.executeUpdate("UPDATE user SET accountType = 4 WHERE email = '"+email+"')");
	                else if (type == "teacher") stmt.executeUpdate("UPDATE user SET accountType = 3 WHERE email = '"+email+"')");
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
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
	            Statement stmt = null;
	            try {
	                stmt = con.createStatement();
	                ResultSet checkStudent = stmt.executeQuery("SELECT accountType FROM user WHERE email = '" + email + "')");
	                if (checkStudent.getInt(1) == 4) {
	                	stmt.executeUpdate("DELETE FROM user WHERE email = '"+email+"')");
	                }
	                else System.err.println("Cannot delete a non-student user.");
	                
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
	 
	 public void registerInitialPeriodOfStudy(String email, int date) throws SQLException {
		 Connection con = null;
	        try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
	            Statement stmt = null;
	            try {
	                stmt = con.createStatement();
	                ResultSet checkStudent = stmt.executeQuery("SELECT accountType FROM user WHERE email = '" + email + "')");
	                if (checkStudent.getInt(1) == 4) {
	                	stmt.executeQuery("UPDATE student SET startDate = '" + date + "' WHERE email = '" + email + "')"); 
	                }
	                else System.err.println("Cannot add start date to a non-student user.");
	                
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
	 
	 public void linkDegree(String email, String degree, int levelOfStudy, String undergradOrPostgrad, int serial) throws SQLException {
		 Connection con = null;
	        String degreeId = "";
	        
	        degreeId = degreeCode.get(degree) + undergradOrPostgrad.toUpperCase() + serial;
	      
	        try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
	            Statement stmt = null;
	            try {
	                stmt = con.createStatement();
	                ResultSet checkStudent = stmt.executeQuery("SELECT accountType FROM user WHERE email = '" + email + "')");
	                if (checkStudent.getInt(1) == 4) {
	                	stmt.executeQuery("UPDATE student SET degreeId = '" + degreeId + "' WHERE email = '" + email + "')"); 
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
	 
	 public void addModule(int serial, String name, int levelOfStudy, int credits, String department, int passMark, String undergradOrPostgrad) throws SQLException {
		 Connection con = null; 
		 String moduleId = degreeCode.get(department) + undergradOrPostgrad + serial;
		 try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
	            Statement stmt = null;
	            try {
	                stmt = con.createStatement();
	                	stmt.executeQuery("INSERT INTO Modules VALUES moduleId = '" + moduleId + "' departmentId = '" + degreeCode.get(department) + "' moduleName = '" + name + "' levelOfStudy = '" + levelOfStudy + "' creditsWorth = '" + credits + "' passMark = " + passMark + "')"); 
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
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
	            Statement stmt = null;
	            try {
	                stmt = con.createStatement();
                	stmt.executeUpdate("DELETE FROM module WHERE moduleId = '"+ moduleId+"')");
   
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
	 
	 public void checkRegistration (String email) throws SQLException {
		 Connection con = null; 
		 try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
	            Statement stmt = null;
	            try {
	                stmt = con.createStatement();
                	stmt.executeUpdate("");
   
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
	 
	 public void checkModuleSum (int moduleId) throws SQLException{
		 Connection con = null; 
		 try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
	            Statement stmt = null;
	            try {
	            	int totalCredits = 0;
	                stmt = con.createStatement();
                	ResultSet creditsList = stmt.executeQuery("SELECT creditsWorth FROM Module WHERE moduleId = '" + moduleId + "')");
                	while (creditsList.next()) totalCredits += creditsList.getInt(1);
                	if (totalCredits != 120) {
                		System.err.println("Credits do not sum to 120. Invalid module selections.");
                	}
                	else System.out.println("Credits sum to 120.");
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