package view.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5L;
	private JPanel contentPane;
	private JTextField loginBox;
	private JPasswordField passwordBox;
	/**
	 * Create the panel.
	 */
	public LoginPane() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(53, 66, 117, 47);
		contentPane.add(lblNewLabel);
		
		passwordBox = new JPasswordField(20);
		passwordBox.setBounds(175, 129, 207, 38);
		contentPane.add(passwordBox);
		passwordBox.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(53, 111, 101, 74);
		contentPane.add(lblNewLabel_1);
		
		loginBox = new JTextField();
		loginBox.setBounds(175, 70, 207, 38);
		contentPane.add(loginBox);
		loginBox.setColumns(5);
		
		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Logging in sequence...");
			  	try {
			  		String isUser = loginValidation(loginBox, passwordBox);
			  		setVisible(false);
			  		launchStudentFrame(isUser, loginBox.getText());
			  		System.out.println("TYPE: " + isUser);
				} 
			   	catch (SQLException e1) {
					e1.printStackTrace();
			   	}        
			}
			
		});
		loginButton.setBounds(76, 188, 117, 38);
		contentPane.add(loginButton);
		
		JButton registerButton = new JButton("Register");
		registerButton.setBounds(241, 188, 117, 38);
		contentPane.add(registerButton);
	}

}
