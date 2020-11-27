package view.Frames;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import features.PasswordGen;

public class StudentFrame extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 4L;
//	
//	public JTextField loginBox = new JTextField(20);
//	public JPasswordField passwordBox = new JPasswordField(20);
//	JButton loginButton = new JButton("Login");
//	JButton registerButton = new JButton("Register");
//	JLabel username = new JLabel("Username");
//	JLabel password = new JLabel("Password");
	JTextArea textArea = new JTextArea("[Student page here.]");
	
	public StudentFrame() {
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();

		setTitle("Student Page");
		setPreferredSize(screenDimensions);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		JPanel pane = new JPanel();
		add(pane);
		
		buildStudentPanel(pane);
		pack();
		
	}
	
	public void actionPerformed(ActionEvent e) {
        String s = (String)e.getActionCommand();
        // calculator.performCommand(s);
        textArea.append(s + " ");     
    } 
	
	public static void main(String[] args) {
		System.out.println("running...");
		java.awt.EventQueue.invokeLater(new Runnable() {
	          public void run() {
	      		JFrame loginPage = new LoginFrame();
	               loginPage.setVisible(true);
	          }
	    });
		System.out.println("end");
	}
	
	public void buildStudentPanel(JPanel pane) {
		pane.setLayout(new BorderLayout());
		pane.add(textArea);
		
	
	}
}