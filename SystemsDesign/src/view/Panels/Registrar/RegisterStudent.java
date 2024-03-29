package view.Panels.Registrar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import userclasses.Admins;
import userclasses.Registrars;
import userclasses.Students;
import userclasses.Users;
import userclasses.Users.UserTypes;
import view.Frames.LoginFrame;
import view.Frames.RegistrarFrame;
import view.Panels.AbstractPanel;

import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterStudent extends AbstractPanel {

	private JTextField degreeId;
	private JTextField personalTutor;
	

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public RegisterStudent(JPanel panel, String name, JFrame mainFrame, Registrars registrar) throws SQLException {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		
		setLayout(null);
		setBounds(100, 100, 1035, 647);
		Users user = getUser(name);
	
		//DEFAULT
				UIManager.put("Label.font", LABEL_FONT);
				UIManager.put("Table.font", TABLE_FONT);
				UIManager.put("TableHeader.font", HEADER_FONT);
				UIManager.put("Button.font", TABLE_FONT);
				UIManager.put("ComboBox.font", LABEL_FONT);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(221,5,2,2);
		panel.add(scrollPane);

		JLabel infoLabel = new JLabel("User Information");
		infoLabel.setBounds(21, 21, 202, 26);
		panel.add(infoLabel);
		
		JLabel accountTypeLabel = new JLabel("Account Type:");
		accountTypeLabel.setBounds(21, 68, 202, 26);
		panel.add(accountTypeLabel);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(21, 115, 148, 26);
		panel.add(usernameLabel);
		
		JLabel accountType = new JLabel(user.getAccountType());
		System.out.println("Account type: " + user.getAccountType());
		accountType.setBounds(221, 68, 156, 26);
		panel.add(accountType);
		
		JLabel username = new JLabel(name);
		username.setBounds(221, 115, 156, 26);
		panel.add(username);
		
		String[] degreeIdList = getDegreeId();
		JComboBox<String> degreeId = new JComboBox<>(degreeIdList);
		degreeId.setBounds(221, 248, 186, 32);
		panel.add(degreeId);
		
		JLabel degreeLabel = new JLabel("Degree ID:");
		degreeLabel.setBounds(21, 251, 179, 26);
		panel.add(degreeLabel);
		
		JLabel creditsLabel = new JLabel("Total Credits:");
		creditsLabel.setBounds(21, 298, 179, 26);
		panel.add(creditsLabel);
		
		JLabel difficultyLabel = new JLabel("Difficulty:");
		difficultyLabel.setBounds(21, 345, 92, 26);
		panel.add(difficultyLabel);
		
		
		JLabel startDateLabel = new JLabel("Start Date:");
		startDateLabel.setBounds(21, 392, 179, 26);
		panel.add(startDateLabel);
		
		JLabel endDateLabel = new JLabel("End Date:");
		endDateLabel.setBounds(21, 439, 179, 26);
		panel.add(endDateLabel);
		
		JLabel personalTutorLabel = new JLabel("Personal Tutor:");
		personalTutorLabel.setBounds(21, 486, 179, 26);
		panel.add(personalTutorLabel);
		
		JRadioButton undergradCredits = new JRadioButton("120", true);
		undergradCredits.setActionCommand("120");
		undergradCredits.setBounds(221, 294, 186, 35);
		panel.add(undergradCredits);
		
		JRadioButton postgradCredits = new JRadioButton("180", false);
		postgradCredits.setActionCommand("180");
		postgradCredits.setBounds(410, 294, 201, 35);
		panel.add(postgradCredits);
		
		//making sure only either undergrad or postgrad is selected - not both
		ButtonGroup credits = new ButtonGroup();
		credits.add(undergradCredits);
		credits.add(postgradCredits);
		
		String[] difficultyLevels = {"MEng", "BEng", "MSc", "BSc", "MPsy", "BPsy","BA"};
		JComboBox<String> difficulty = new JComboBox<>(difficultyLevels);
		difficulty.setBounds(221, 342, 186, 32);
		panel.add(difficulty);

		String[] days = new String[31];
		String[] months = new String [12];
		String[] years = new String[10];
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		for (int i = 0; i < days.length; i++) {
			days[i] = Integer.toString(i + 1);
		}
		
		for (int i = 0; i < months.length; i ++) {
			months[i] = Integer.toString(i + 1);
		}
		
		for (int i = 0; i < years.length; i ++) {
			years[i] = Integer.toString(currentYear + i);
		}
		
		JLabel instruction = new JLabel("Fill in the form below for adding a student. Otherwise, click \"Remove Student\".");
		instruction.setBounds(21, 201, 854, 26);
		panel.add(instruction);
		JComboBox<String> startDay = new JComboBox<>(days);
		startDay.setBounds(221, 389, 67, 32);
		panel.add(startDay);
		
		JComboBox<String> startMonth = new JComboBox<>(months);
		startMonth.setBounds(321, 389, 109, 32);
		panel.add(startMonth);
		
		JComboBox<String> startYear = new JComboBox<>(years);
		startYear.setBounds(458, 389, 109, 32);
		panel.add(startYear);
		
		JComboBox<String> endDay = new JComboBox<>(days);
		endDay.setBounds(221, 436, 67, 32);
		panel.add(endDay);
		
		JComboBox<String> endMonth = new JComboBox<>(months);
		endMonth.setBounds(321, 436, 109, 32);
		panel.add(endMonth);
		
		JComboBox<String> endYear = new JComboBox<>(years);
		endYear.setBounds(458, 436, 109, 32);
		panel.add(endYear);
		
		personalTutor = new JTextField();
		personalTutor.setBounds(221, 483, 186, 32);
		panel.add(personalTutor);
		personalTutor.setColumns(10);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JPanel menu = new RegistrarMenu(panel, mainFrame, registrar.getUsername());
							panel.add(menu);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		backButton.setBounds(873, 17, 141, 35);
		panel.add(backButton);
		
		JButton addStudent = new JButton("Add Student");
		addStudent.setBounds(20, 576, 202, 35);
		panel.add(addStudent);
		addStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String un = username.getText();
				String deg = degreeId.getSelectedItem().toString();
				int creds = Integer.parseInt(credits.getSelection().getActionCommand());
				String diff = (String) difficulty.getSelectedItem();
				String sd = (String) startDay.getSelectedItem();
				String sm = (String) startMonth.getSelectedItem();
				String sy = (String) startYear.getSelectedItem();
				Date start = convertDate(sd, sm, sy);
				Date end = convertDate((String) endDay.getSelectedItem(), (String) endMonth.getSelectedItem(), (String) endYear.getSelectedItem());
				String pt = personalTutor.getText();
				try {
					registrar.addStudent(un, deg, creds, diff, start, end, pt);
					user.setAccountType(UserTypes.STUDENT.toString());
					accountType.setText(user.getAccountType());
					panel.revalidate();
					panel.repaint();
					JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Student Added.");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton removeStudent = new JButton("Remove Student");
		removeStudent.setBounds(409, 576, 202, 35);
		panel.add(removeStudent);
		removeStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Students student = getStudent((String) username.getText());
					Registrars.deleteStudent((String) username.getText(), student.getEmail());
					user.setAccountType(UserTypes.UNASSIGNED.toString());
					accountType.setText(user.getAccountType());
					panel.revalidate();
					panel.repaint();
					JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Student Removed.");
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		

	}
}
