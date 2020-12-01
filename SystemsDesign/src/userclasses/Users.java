package userclasses;
import java.sql.*;
import java.util.Dictionary;
import java.util.Hashtable;

import features.PasswordGen;

public class Users {
	String username;
	String title;
	String surname;
	String forename;
	String accountType;
	String password;
	String salt = PasswordGen.getSalt(30);
	
	public enum UserTypes {
		ADMIN ("Admin"),
		TEACHER ("Teacher"),
		STUDENT ("Student"),
		REGISTRAR ("Registrar"),
		UNASSIGNED ("Unassigned");
		
		private final String name;
		
		private UserTypes (String s) {
			name = s;
		}
		
		public String toString() {
			return this.name;
		}
	}
	
	public enum DegreeTypes {
		BUS ("Business School"),
		COM ("Computer Science"),
		PSY ("Psychology"),
		LAN("Modern Language");
		
		private final String name;
		
		private DegreeTypes (String s) {
			name = s;
		}
		
		public String toString() {
			return this.name;
		}
	}
	
	 // BUS : business school, COM : computer science, PSY : psychology, LAN : modern languages
	 public static Dictionary<String, String> degreeCode = new Hashtable<String, String>();
	 
	 static {
		 degreeCode.put("Business School", "BUS");
	     degreeCode.put("Computer Science", "COM");
	     degreeCode.put("Psychology", "PSY");
	     degreeCode.put("Modern Language", "LAN");
	 }
	
	public Users (String username, String title, String surname, String forename, String password) throws SQLException {
		this.username = username;
		this.title = title;
		this.surname = surname;
		this.forename = forename;
		this.accountType = UserTypes.UNASSIGNED.toString();
		
		this.password = PasswordGen.generateSecurePassword(password, salt);
		
    	Connection con = null;
	        try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String checkExisting = "SELECT username FROM student WHERE username = ?";

                String preparedStmt = "INSERT INTO user VALUES (?, ?, ?, ?, ?, ?, ?)";
	            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt);
	            		PreparedStatement checkStmt = con.prepareStatement(checkExisting)){
	            	checkStmt.setString(1, username);
	            	ResultSet existingUsers = checkStmt.executeQuery();
	            	con.commit();
	            	while (existingUsers.next()) {
	           		 if (username.equals(existingUsers.getString("username"))) {
	        			 System.err.println("User/username already exists.");
	        		 }
	        		 else {
	        			updateStmt.setString(1, username);
	 	                updateStmt.setString(2, title);
	 	                updateStmt.setString(3, surname);
	 	                updateStmt.setString(4, forename);
	 	                updateStmt.setString(5, accountType);
	 	                updateStmt.setString(6, this.password);
	 	                updateStmt.setString(7, salt);
	 	                updateStmt.executeUpdate();
	 	                con.commit();
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
	}
	
	public String getSalt() {
		return salt;
	}

	public String toString() {
		return "new user created. username = " + username + " name = " + forename;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getForename() {
		return forename;
	}
	
	public String getPassword() {
		return password;
	}
	
}
	

