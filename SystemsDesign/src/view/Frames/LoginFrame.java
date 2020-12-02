package view.Frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import features.PasswordGen;
import userclasses.Users.UserTypes;

import javax.swing.JTextField;
import javax.swing.BorderFactory;
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
	private JLabel error;
	
	private static final Color BLUE = new java.awt.Color(102, 153, 255);
	private static final Font BORDER_FONT = new Font("Yu Gothic", Font.BOLD, 13);
	private static final Font LABEL_FONT = new Font("Yu Gothic", Font.PLAIN, 18);
	private static final Font ERROR_FONT = new Font("Yu Gothic", Font.PLAIN, 13);

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
		
		TitledBorder title;
		Border line = BorderFactory.createLineBorder(BLUE);
		contentPane = new JPanel();
		title = BorderFactory.createTitledBorder(line, "Login");
		title.setTitleFont(BORDER_FONT);
		contentPane.setBorder(title);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//labels
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(21, 43, 117, 47);
		usernameLabel.setFont(LABEL_FONT);
		contentPane.add(usernameLabel);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(21, 88, 101, 74);
		passwordLabel.setFont(LABEL_FONT);
		contentPane.add(passwordLabel);
		
		error = new JLabel();
		error.setBounds(21,200, 401, 74);
		error.setFont(ERROR_FONT);
		error.setForeground(Color.RED);
		contentPane.add(error);
		//-----------------------------------
		
		
		//text fields
		
		passwordBox = new JPasswordField(20);
		passwordBox.setBounds(143, 106, 207, 38);
		contentPane.add(passwordBox);
		passwordBox.setColumns(10);

		loginBox = new JTextField();
		loginBox.setBounds(143, 47, 207, 38);
		contentPane.add(loginBox);
		loginBox.setColumns(5);
		
		//-----------------------------------
		
		
		
		//buttons
		
		JButton loginButton = new JButton("Login");
		loginButton.setFont(BORDER_FONT);
		loginButton.setBackground(BLUE);
		loginButton.setOpaque(true);
		loginButton.setBounds(160, 175, 117, 38);
		loginButton.setBorderPainted(false);
		loginButton.setFocusPainted(false);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			  	try {
			  		if (loginBox.getText().isEmpty() || passwordBox.getText().isEmpty()) {
						error.setText("*Username or password field is empty.");
						setVisible(true);
					}
			  		else {
			  			String isUser = loginValidation(loginBox, passwordBox);
				  		setVisible(false);
				  		launchFrames(isUser, loginBox.getText());                    
				  		System.out.println("TYPE: " + isUser);
			  		}
			  		
				} 
			   	catch (SQLException e1) {
					e1.printStackTrace();
			   	}        
			}
			
		});
		
		contentPane.add(loginButton);
		
		//-----------------------------------
	}

	/**
	 * Validates whether the user's login details are correct
	 * @param username
	 * @param password
	 * @return
	 * @throws SQLException
	 */
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

		          while (checkpw.next()) {
				        type = checkpw.getString(5);
		        	    rightpw = checkpw.getString(6);
				        salt = checkpw.getString(7);
		          }

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
