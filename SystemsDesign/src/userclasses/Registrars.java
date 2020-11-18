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
	[-]Add optional modules
	[-]Remove optional modules
	[-]Check registration is complete
	[-]Check student's modules
	[-]Check credits of modules sum to 120
	[-]Display results

	Attributes to add
	inherits from user class
	*/
	
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
	        //System.out.println(permission.get(newPermission));
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
	        //System.out.println(permission.get(newPermission));
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
	 
	 public void linkDegree(String email, String degree, int levelOfStudy, String undergradOrPostgrad, boolean yearInIndustry) throws SQLException {
		 Connection con = null;
		// BUS : business school, COM : computer science, PSY : psychology, LAN : modern languages
	        Dictionary<String, String> degreeCode = new Hashtable<String, String>();
	        degreeCode.put("Business School", "BUS");
	        degreeCode.put("Computer Science", "COM");
	        degreeCode.put("Psychology", "PSY");
	        degreeCode.put("Modern Language", "LAN");
	        String degreeId = "";
	        int serial = (int)(Math.random() * (99 - 10 + 1) + 10);
	        
	        if (yearInIndustry) degreeId = degreeCode.get(degree) + undergradOrPostgrad.toUpperCase() + serial;
	        else degreeId = degreeCode.get(degree) + undergradOrPostgrad.toUpperCase() + serial;
	      
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
}