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
}
	

