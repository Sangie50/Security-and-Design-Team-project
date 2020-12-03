package view.Panels.Registrar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import userclasses.Admins;
import userclasses.Registrars;
import userclasses.Students;
import userclasses.Users.UserTypes;
import view.Frames.LoginFrame;
import view.Panels.AbstractPanel;

public class RegistrarMenu extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 10L;
	private Registrars registrar;
	

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public RegistrarMenu(JPanel contentPane, JFrame mainFrame, String registrarUsername) throws SQLException {
		contentPane.removeAll();
		contentPane.revalidate();
		contentPane.repaint();
		
		registrar = getRegistrar(registrarUsername);
		
		//labels
		
		JLabel title = new JLabel("Registrar Page");
		title.setBounds(5, 5, 999, 26);
		title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(title);
		

		JLabel username = new JLabel("Username:");
		username.setBounds(81, 248, 277, 26);
		contentPane.add(username);
		
		JLabel descLabel = new JLabel("Select a user and click on the buttons to control their fate.");
		descLabel.setBounds(81, 68, 834, 26);
		contentPane.add(descLabel);
		
		JLabel error = new JLabel("");
		error.setBounds(81, 100, 277, 26);
		error.setForeground(Color.RED);
		contentPane.add(error);

		//---------------------------
		
		//combobox
			JComboBox<String> usernameBox = new JComboBox<>(getUsernames());
			usernameBox.setBounds(81, 291, 277, 45);
			contentPane.add(usernameBox);
			contentPane.add(usernameBox, BorderLayout.PAGE_START);
		//-------------------
		
		//buttons 

		
		JButton logout = new JButton("Logout");
		logout.setBounds(608, 525, 307, 35);
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							mainFrame.setVisible(false);
							LoginFrame frame = new LoginFrame();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		contentPane.add(logout);
		
		JButton registerPage = new JButton("Register/ remove student");
		registerPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel registerPanel = null;
				try {
					System.out.println("Selected User: " + (String) usernameBox.getSelectedItem());
					registerPanel = new RegisterStudent(contentPane, (String) usernameBox.getSelectedItem(), mainFrame, registrar);
					registerPanel.setVisible(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				contentPane.add(registerPanel);
			}
		});
		
		registerPage.setBounds(608, 163, 307, 35);
		contentPane.add(registerPage);
		
		JButton modulesPage = new JButton("Add/ remove modules");
		modulesPage.setBounds(608, 247, 307, 35);
		contentPane.add(modulesPage);
		modulesPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel modules = null;
				try {
					if (isType((String) usernameBox.getSelectedItem(), UserTypes.STUDENT.toString())) {
						
					try {
						modules = new ChangeModules(contentPane, (String) usernameBox.getSelectedItem(), mainFrame, registrar);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					modules.setVisible(true);
					contentPane.add(modules);
					}
				else {
					error.setText("Cannot add/ remove modules from a non-student account.");
				}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		JButton checkRegisterPage = new JButton("Check registration");
		checkRegisterPage.setBounds(608, 335, 307, 35);
		contentPane.add(checkRegisterPage);
		checkRegisterPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (isType((String) usernameBox.getSelectedItem(), UserTypes.STUDENT.toString())) {
						try {
							JPanel register = new CheckRegistered(contentPane, (String) usernameBox.getSelectedItem(), mainFrame, registrar);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						error.setText("*Cannot check registration of a non-student account.");
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		JButton teacherPage = new JButton("Add/remove teacher");
		teacherPage.setBounds(608, 425, 307, 35);
		contentPane.add(teacherPage);
		teacherPage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
				if (isType((String) usernameBox.getSelectedItem(), UserTypes.TEACHER.toString())) {
					try {
						JPanel addTeacher = new TeacherModule(contentPane, (String) usernameBox.getSelectedItem(), mainFrame, registrar);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {
					error.setText("*Cannot assign to a non-teacher account.");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
			}
		});
		
		
			
		//------------------
		
		
		
		
		
		
	}


}
