package view.Frames;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import features.PasswordGen;
import userclasses.Users.UserTypes;

public class LoginFrame extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	public JTextField loginBox = new JTextField(20);
	public JPasswordField passwordBox = new JPasswordField(20);
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Register");
	JLabel username = new JLabel("Username");
	JLabel password = new JLabel("Password");
	JTextArea textArea = new JTextArea("");
//	static JFrame loginPage = new LoginFrame();
//	static JFrame studentPage = new StudentFrame();
		
	public LoginFrame() {
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();

		setTitle("Login Page");
		setPreferredSize(screenDimensions);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JPanel pane = new JPanel();
		add(pane);
		
		buildLoginPanel(pane);
		pack();
		
	}
	
	public void actionPerformed(ActionEvent e) {
        String s = (String)e.getActionCommand();
        // calculator.performCommand(s);
        textArea.append(s + " ");      // <<--- THIS IS LINE 106
    } 
	
	public static void main(String[] args) {
		System.out.println("running...");
		java.awt.EventQueue.invokeLater(new Runnable() {
	          public void run() {	
	            (new LoginFrame()).setVisible(true);

	          }
	    });
		System.out.println("end");
	}
	
	public void buildLoginPanel(JPanel pane) {
		pane.setLayout(null);
		username.setBounds(10,20,80,25);
		pane.add(username);
		loginBox.setBounds(100,20,165,25);
		pane.add(loginBox);
		password.setBounds(10,50,80,25);
		pane.add(password);
		passwordBox.setBounds(100,50,165,25);
		pane.add(passwordBox);
		loginButton.setBounds(10, 80, 80, 25);
		pane.add(loginButton);
		registerButton.setBounds(100, 80, 100, 25);
		pane.add(registerButton);
		
		loginButton.setActionCommand("login");
		registerButton.setActionCommand("register");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    System.out.println("Logging in sequence...");
			  	try {
			  		String isUser = loginValidation(loginBox, passwordBox);
			  		System.out.println(isUser);
			  		setVisible(false);
			  		new StudentFrame().setVisible(true);
				} 
			   	catch (SQLException e1) {
					e1.printStackTrace();
			   	}        
			}	
		});
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Registering sequence...");				
			}
		});
	}
	
	public String loginValidation(JTextField username, JPasswordField password) throws SQLException {
		String un = username.getText();
		String pw = String.valueOf(password.getPassword());
		String rightpw = "";
		String salt = "";
		String type = "";
		
		Connection con = null; 
		try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	          con.setAutoCommit(false);
	          Statement stmt = null;
	          String getusername = "SELECT * FROM user WHERE username = ?";
	          try (PreparedStatement updateStmt = con.prepareStatement(getusername)){
	              updateStmt.setString(1, un);
	              ResultSet checkpw = updateStmt.executeQuery();
	              con.commit();
//	              System.out.println("Result set size: " + checkpw.getFetchSize());
//	              System.out.println("username: " + un + " password: " + pw);
	          
		          while (checkpw.next()) {
		        	    rightpw = checkpw.getString(6);
				        salt = checkpw.getString(7);
				        type = checkpw.getString(5);
				        
		          }
//	              System.out.println(rightpw + " " + salt);

	              boolean passwordMatch = PasswordGen.verifyUserPassword(pw, rightpw, salt);
	              if (passwordMatch) {
	            	  System.out.println("Correct password. Access Granted.");
	            	 
	              }
	              
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
		return type;
	}
}