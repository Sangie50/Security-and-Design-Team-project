package view.Panels.Student;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import academics.Grades;
import userclasses.Admins;
import userclasses.Registrars;
import userclasses.Students;
import userclasses.Teachers;
import userclasses.Users.UserTypes;
import view.Frames.LoginFrame;
import view.Panels.AbstractPanel;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class StudentMenu extends AbstractPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1234536L;
	
	
	private JTable modulesTable;
	private DefaultTableModel model = new DefaultTableModel();
	private JScrollPane scroll;
	private static final Font LABEL_FONT = new Font("Yu Gothic", Font.PLAIN, 18);

	private static final Font TITLE_FONT = new Font("Yu Gothic", Font.BOLD, 21);


	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public StudentMenu(JPanel contentPane, String username, JFrame mainFrame) throws SQLException {
		Students student = getStudent(username);
	
		JLabel title = new JLabel("Student Page");
		title.setFont(TITLE_FONT);
		title.setBounds(413, 21, 224, 71);
		contentPane.add(title);
		
		JLabel registrationLabel = new JLabel("Registration ID:");
		registrationLabel.setBounds(21, 184, 147, 26);
		contentPane.add(registrationLabel);
		
		JLabel surnameLabel = new JLabel("Surname: ");
		surnameLabel.setBounds(21, 149, 98, 26);
		contentPane.add(surnameLabel);
		
		JLabel forenameLabel = new JLabel("Forename: ");
		forenameLabel.setBounds(21, 114, 107, 26);
		contentPane.add(forenameLabel);
		
		JLabel degreeLabel = new JLabel("Degree:");
		degreeLabel.setBounds(21, 218, 92, 26);
		contentPane.add(degreeLabel);
		
		System.out.println("Getting forename");
		JLabel forename = new JLabel(student.getForename());

		forename.setBounds(249, 116, 92, 26);
		contentPane.add(forename);
		
		JLabel surname = new JLabel(student.getSurname());
		surname.setBounds(249, 149, 206, 26);
		contentPane.add(surname);
		
		JLabel registrationId = new JLabel(Integer.toString(student.getRegistrationId()));
		registrationId.setBounds(249, 184, 206, 26);
		contentPane.add(registrationId);
		
		JLabel degree = new JLabel(student.getDegreeId());
		degree.setBounds(249, 218, 206, 26);
		contentPane.add(degree);

		forename.setBounds(249, 116, 206, 26);
		contentPane.add(forename);

	    
	    JButton logoutButton = new JButton("Logout");
	    logoutButton.addActionListener(new ActionListener() {
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
	    logoutButton.setBounds(795, 110, 141, 35);
	    contentPane.add(logoutButton);
	    
	
	    modulesTable = new JTable(model);
	    modulesTable.setBounds(21, 119, 643, 507);
		modulesTable.setBackground(UIManager.getColor("Button.background"));
		modulesTable.setEnabled(false);
		modulesTable.getTableHeader().setReorderingAllowed(false);
		contentPane.add(modulesTable);

	   
	    scroll = new JScrollPane(modulesTable);
		scroll.setBounds(75,265,861,290);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(scroll);

	    contentPane.setVisible(true);
	    
	    //combobox
	    JComboBox<String> levelOfStudy = new JComboBox<>(
				student.getAllLevelsOfStudy(student.getEmail()));
		levelOfStudy.setBounds(795, 225, 141, 32);
		contentPane.add(levelOfStudy);
		levelOfStudy.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
	            JComboBox<String> combo = (JComboBox<String>) e.getSource();
	            String selection = (String) levelOfStudy.getSelectedItem();
				
	            try {
					insert(student.getUsername(), selection, model);
	            } catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		 insert(username, (String) levelOfStudy.getSelectedItem(), model);
	
	}
	
	public void insert(String username, String levelOfStudy, DefaultTableModel model) throws SQLException {
		model.setRowCount(0);
    	String[] headers = {"Module ID", "Module Name", "Initial Grade", "Resit Grade", "Credits Worth", "Department ID" , "Pass Grade"};
		Students student = getStudent(username);
		model.setColumnIdentifiers(headers);
		
		ArrayList<ArrayList<String>> ar = student.displayByLevelOfStudy(student.getEmail(), levelOfStudy);

		for (int i = 0; i < (ar.size()); i++) {
		    	String moduleid = ar.get(i).get(0); //module id
		    	String initGrade = ar.get(i).get(1); //initial grade
		    	String reGrade = ar.get(i).get(2); //resit grade
		    	String modName = ar.get(i).get(3); //module name
		    	String creds = ar.get(i).get(4); //credits worth
		    	String depId = ar.get(i).get(5); //department id
		    	String pass = ar.get(i).get(6); //pass grade
		    	String[] arr = {moduleid, modName, initGrade, reGrade, creds, depId, pass};
		    	model.addRow(arr);
	    }
	}	
}
