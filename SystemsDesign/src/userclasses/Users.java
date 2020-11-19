package userclasses;
import java.sql.*; 
import features.PasswordGen;

public class Users {
	String username;
	String title;
	String surname;
	String forename;
	String accountType;
	String password;
	
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
	
	public Users (String username, String title, String surname, String forename, String password) throws SQLException {
		this.username = username;
		this.title = title;
		this.surname = surname;
		this.forename = forename;
		this.accountType = UserTypes.UNASSIGNED.toString();
		String salt = PasswordGen.getSalt(30);
		this.password = PasswordGen.generateSecurePassword(password, salt);
		
		 Connection con = null;
	        try {
	            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
	            Statement stmt = null;
                String preparedStmt = "INSERT INTO users VALUES (?, ?, ?, ?, ?, ?)";
	            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
	            	con.setAutoCommit(false);
	            	
	                updateStmt.setString(1, username);
	                updateStmt.setString(2, title);
	                updateStmt.setString(3, surname);
	                updateStmt.setString(4, forename);
	                updateStmt.setString(5, accountType);
	                updateStmt.setString(6, password);
	                updateStmt.setString(7, salt);
	                
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

	public String toString() {
		return "new user created.";
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
	

