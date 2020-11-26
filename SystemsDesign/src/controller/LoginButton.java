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
	
	
}