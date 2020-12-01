package view.Frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import features.PasswordGen;
import userclasses.Users.UserTypes;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import view.Frames.StudentFrame;

public class LoginFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField loginBox;
	private JPasswordField passwordBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(21, 33, 117, 47);
		contentPane.add(lblNewLabel);
		
		passwordBox = new JPasswordField(20);
		passwordBox.setBounds(143, 96, 207, 38);
		contentPane.add(passwordBox);
		passwordBox.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(21, 78, 101, 74);
		contentPane.add(lblNewLabel_1);
		
		loginBox = new JTextField();
		loginBox.setBounds(143, 37, 207, 38);
		contentPane.add(loginBox);
		loginBox.setColumns(5);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Logging in sequence...");
			  	try {
			  		String isUser = loginValidation(loginBox, passwordBox);
			  		setVisible(false);
			  		launchFrames(isUser, loginBox.getText());                    
			  		System.out.println("TYPE: " + isUser);
				} 
			   	catch (SQLException e1) {
					e1.printStackTrace();
			   	}        
			}
			
		});
		loginButton.setBounds(44, 155, 117, 38);
		contentPane.add(loginButton);
		
		JButton registerButton = new JButton("Register");
		registerButton.setBounds(209, 155, 117, 38);
		contentPane.add(registerButton);
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
				        type = checkpw.getString(5);
		        	    rightpw = checkpw.getString(6);
				        salt = checkpw.getString(7);
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
	
	public void launchFrames(String type, String username) throws SQLException {

		if (type.equals(UserTypes.STUDENT.toString())) {
			System.out.println("Hello, this is student frame");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						StudentFrame frame = new StudentFrame(username);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});	
		}
		
		else if (type.equals(UserTypes.TEACHER.toString())) {
			System.out.println("This is teacher frame");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						TeacherFrame frame = new TeacherFrame(username);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		else if (type.equals(UserTypes.REGISTRAR.toString())) {
			System.out.println("This is registrar frame");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						RegistrarFrame frame = new RegistrarFrame(username);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		else if (type.equals(UserTypes.ADMIN.toString())) {
			System.out.println("This is admin frame.");
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						AdminFrame frame = new AdminFrame();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}
        
}
