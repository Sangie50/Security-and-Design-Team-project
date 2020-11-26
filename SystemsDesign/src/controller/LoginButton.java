package controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;

import features.PasswordGen;
import view.Frames.LoginFrame;

public class LoginButton extends LoginFrame implements ActionListener{
	
	private static final long serialVersionUID = 3L;

	public void actionPerformed(ActionEvent e) {
	      String command = e.getActionCommand();
	      if (command == "login") {
	    	  try {
				loginValidation(loginBox, passwordBox);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
	      }
	      else System.out.println("Im not ready yet");
	          
	}
	
	public void loginValidation(JTextField username, JPasswordField password) throws SQLException {
		String un = username.getText();
		String pw = String.valueOf(password.getPassword());
		
		Connection con = null; 
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String getusername = "SELECT password, salt FROM user WHERE username = ?";
	            try (PreparedStatement updateStmt = con.prepareStatement(getusername)){
	                updateStmt.setString(1, un);
	                ResultSet checkpw = updateStmt.executeQuery();
	                con.commit();
	                String rightpw = checkpw.getString(1);
	                String salt = checkpw.getString(2);
	                
	                boolean passwordMatch = PasswordGen.verifyUserPassword(pw, rightpw, salt);
	                if (passwordMatch) System.out.println("Correct password. Access Granted.");
	                else System.out.println("Access denied.");
	                
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