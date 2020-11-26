package view.Panels;
import java.awt.*;

import javax.swing.*;

public class LoginPanel extends JPanel {
	
	private static final long serialVersionUID = 2L;
	
	JTextField loginBox = new JTextField(20);
	JPasswordField passwordBox = new JPasswordField(20);
	JButton loginButton = new JButton("Login");
	JButton registerButton = new JButton("Register");
	JLabel username = new JLabel("Username");
	JLabel password = new JLabel("Password");
	
	
	public LoginPanel(JPanel panel) {
//		JPanel panel = new JPanel();
//		panel.setLayout(new BorderLayout());
//		buildLoginPage(panel);	
		 panel.setLayout(null);

	        // Creating JLabel
	        JLabel userLabel = new JLabel("User");
	        /* This method specifies the location and size
	         * of component. setBounds(x, y, width, height)
	         * here (x,y) are cordinates from the top left 
	         * corner and remaining two arguments are the width
	         * and height of the component.
	         */
	        userLabel.setBounds(10,20,80,25);
	        panel.add(userLabel);

	        /* Creating text field where user is supposed to
	         * enter user name.
	         */
	        JTextField userText = new JTextField(20);
	        userText.setBounds(100,20,165,25);
	        panel.add(userText);

	        // Same process for password label and text field.
	        JLabel passwordLabel = new JLabel("Password");
	        passwordLabel.setBounds(10,50,80,25);
	        panel.add(passwordLabel);

	        /*This is similar to text field but it hides the user
	         * entered data and displays dots instead to protect
	         * the password like we normally see on login screens.
	         */
	        JPasswordField passwordText = new JPasswordField(20);
	        passwordText.setBounds(100,50,165,25);
	        panel.add(passwordText);

	        // Creating login button
	        JButton loginButton = new JButton("login");
	        loginButton.setBounds(10, 80, 80, 25);
	        panel.add(loginButton);
		
	}
	
	public void buildLoginPage(JPanel pane) {
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
		pane.add(new JButton("Login"));
		registerButton.setBounds(10, 80, 80, 25);
		pane.add(new JButton("Register"));
//		   /* We will discuss about layouts in the later sections
//         * of this tutorial. For now we are setting the layout 
//         * to null
//         */
//        panel.setLayout(null);
//
//        // Creating JLabel
//        JLabel userLabel = new JLabel("User");
//        /* This method specifies the location and size
//         * of component. setBounds(x, y, width, height)
//         * here (x,y) are cordinates from the top left 
//         * corner and remaining two arguments are the width
//         * and height of the component.
//         */
//        userLabel.setBounds(10,20,80,25);
//        panel.add(userLabel);
//
//        /* Creating text field where user is supposed to
//         * enter user name.
//         */
//        JTextField userText = new JTextField(20);
//        userText.setBounds(100,20,165,25);
//        panel.add(userText);
//
//        // Same process for password label and text field.
//        JLabel passwordLabel = new JLabel("Password");
//        passwordLabel.setBounds(10,50,80,25);
//        panel.add(passwordLabel);
//
//        /*This is similar to text field but it hides the user
//         * entered data and displays dots instead to protect
//         * the password like we normally see on login screens.
//         */
//        JPasswordField passwordText = new JPasswordField(20);
//        passwordText.setBounds(100,50,165,25);
//        panel.add(passwordText);
//
//        // Creating login button
//        JButton loginButton = new JButton("login");
//        loginButton.setBounds(10, 80, 80, 25);
//        panel.add(loginButton);
	}
}
