package userclasses;
import java.sql.*;
//Abstract class for the various user classes
public abstract class Users {
	String email;
	String surname;
	String password;
	String forename;
	
	public String getEmail() {
		return email;
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
	
	public void addUser(String email, String name, String password) throws Exception {
		Connection con = null;
		try {
			con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
			Statement stmt = null;
			try {
				stmt = con.createStatement();
				stmt.executeUpdate( "INSERT email = '" + email + "' surname = '" + surname + "' forename = '" + forename + "'");
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
	