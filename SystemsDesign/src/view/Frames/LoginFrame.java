package view.Frames;
import java.awt.*;
import javax.swing.*;

import view.Panels.LoginPanel;

public class LoginFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
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
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
	          public void run() {
	      		JFrame loginPage = new LoginFrame();
	               loginPage.setVisible(true);
	          }
	    });
	}
	
	public void buildLoginPanel(JPanel pane) {
		JTextField loginBox = new JTextField(20);
		JPasswordField passwordBox = new JPasswordField(20);
		JButton loginButton = new JButton("Login");
		JButton registerButton = new JButton("Register");
		JLabel username = new JLabel("Username");
		JLabel password = new JLabel("Password");
		
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
	}
	
}