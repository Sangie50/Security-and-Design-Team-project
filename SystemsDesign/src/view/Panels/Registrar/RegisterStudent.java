package view.Panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JTextField;

import view.Frames.LoginFrame;
import view.Frames.RegistrarFrame;

import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RegisterStudent extends JPanel {
	private JTable table;
	private JTextField degreeId;
	private JTextField personalTutor;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public RegisterStudent(JPanel panel, String name) throws SQLException {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		
		setLayout(null);
		setBounds(100, 100, 1035, 647);
		String type = "";
		
		Connection con = null;
	    Statement stmt = null;

		     try {
		         con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		         con.setAutoCommit(false);

		         String accType = "SELECT account_type FROM user WHERE username = ?";
		         try (PreparedStatement checkAccType = con.prepareStatement(accType)){
		        	 checkAccType.setString(1, name);
		             ResultSet rs = checkAccType.executeQuery();
		             con.commit();
		              
		             while (rs.next()) {
			             type = rs.getString(1);

		             }
		             
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
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(221,5,2,2);
		panel.add(scrollPane);

		JLabel infoLabel = new JLabel("User Information");
		infoLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
		infoLabel.setBounds(21, 21, 202, 26);
		panel.add(infoLabel);
		
		JLabel accountTypeLabel = new JLabel("Account Type:");
		accountTypeLabel.setBounds(21, 68, 202, 26);
		panel.add(accountTypeLabel);
		
		JLabel usernameLabel = new JLabel("Username:");
		usernameLabel.setBounds(21, 115, 148, 26);
		panel.add(usernameLabel);
		
		JLabel accountType = new JLabel(type);
		accountType.setBounds(221, 68, 156, 26);
		panel.add(accountType);
		
		JLabel username = new JLabel(name);
		username.setBounds(221, 115, 156, 26);
		panel.add(username);
		
		JButton addStudent = new JButton("Add Student");
		addStudent.setBounds(20, 576, 202, 35);
		panel.add(addStudent);
		
		JButton removeStudent = new JButton("Remove Student");
		removeStudent.setBounds(409, 576, 202, 35);
		panel.add(removeStudent);
		
		degreeId = new JTextField();
		degreeId.setBounds(221, 248, 186, 32);
		panel.add(degreeId);
		degreeId.setColumns(10);
		
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
		
		JRadioButton undergradCredits = new JRadioButton("120");
		undergradCredits.setBounds(221, 294, 186, 35);
		panel.add(undergradCredits);
		
		JRadioButton postgradCredits = new JRadioButton("180");
		postgradCredits.setBounds(410, 294, 201, 35);
		panel.add(postgradCredits);
		
		
		String[] difficultyLevels = {"MEng", "BEng", "MSc", "BSc", "MPsy", "BPsy"};
		JComboBox<String> difficulty = new JComboBox<>(difficultyLevels);
		difficulty.setBounds(221, 342, 186, 32);
		panel.add(difficulty);

		
		JLabel instruction = new JLabel("Fill in the form below for adding a student. Otherwise, click \"Remove Student\".");
		instruction.setBounds(21, 201, 854, 26);
		panel.add(instruction);
		JComboBox startDay = new JComboBox();
		startDay.setBounds(221, 389, 67, 32);
		panel.add(startDay);
		
		JComboBox startMonth = new JComboBox();
		startMonth.setBounds(321, 389, 109, 32);
		panel.add(startMonth);
		
		JComboBox startYear = new JComboBox();
		startYear.setBounds(458, 389, 109, 32);
		panel.add(startYear);
		
		JComboBox endDay = new JComboBox();
		endDay.setBounds(221, 436, 67, 32);
		panel.add(endDay);
		
		JComboBox endMonth = new JComboBox();
		endMonth.setBounds(321, 436, 109, 32);
		panel.add(endMonth);
		
		JComboBox endYear = new JComboBox();
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
							JPanel menu = new RegistrarMenu(panel);
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
		
//		panel.removeAll();
//		panel.revalidate();
//		panel.repaint();
//		setLayout(null);
//		setBounds(100, 100, 1035, 647);
//		String type = "";
//		
//		Connection con = null;
//	    Statement stmt = null;
//
//		     try {
//		         con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
//		         con.setAutoCommit(false);
//
//		         String accType = "SELECT account_type FROM user WHERE username = ?";
//		         try (PreparedStatement checkAccType = con.prepareStatement(accType)){
//		        	 checkAccType.setString(1, name);
//		             ResultSet rs = checkAccType.executeQuery();
//		             con.commit();
//		              
//		             while (rs.next()) {
//			             type = rs.getString(1);
//
//		             }
//		             
//		         }
//		         catch (SQLException ex) {
//		             ex.printStackTrace();
//		         }
//		         finally {
//		             if (stmt != null) stmt.close();
//		         }
//		     }
//		     catch (Exception ex) {
//		         ex.printStackTrace();
//		     }
//		     finally {
//		         if (con != null) con.close();
//		     }
//		
//		
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBounds(221,5,2,2);
//		add(scrollPane);
//
//		JLabel infoLabel = new JLabel("User Information");
//		infoLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
//		infoLabel.setBounds(21, 21, 202, 26);
//		add(infoLabel);
//		
//		JLabel accountTypeLabel = new JLabel("Account Type:");
//		accountTypeLabel.setBounds(21, 68, 202, 26);
//		add(accountTypeLabel);
//		
//		JLabel usernameLabel = new JLabel("Username:");
//		usernameLabel.setBounds(21, 115, 148, 26);
//		add(usernameLabel);
//		
//		JLabel accountType = new JLabel(type);
//		accountType.setBounds(221, 68, 156, 26);
//		add(accountType);
//		
//		JLabel username = new JLabel(name);
//		username.setBounds(221, 115, 156, 26);
//		add(username);
//		
//		JButton addStudent = new JButton("Add Student");
//		addStudent.setBounds(20, 576, 202, 35);
//		add(addStudent);
//		
//		JButton removeStudent = new JButton("Remove Student");
//		removeStudent.setBounds(409, 576, 202, 35);
//		add(removeStudent);
//		
//		degreeId = new JTextField();
//		degreeId.setBounds(221, 248, 186, 32);
//		add(degreeId);
//		degreeId.setColumns(10);
//		
//		JLabel degreeLabel = new JLabel("Degree ID:");
//		degreeLabel.setBounds(21, 251, 179, 26);
//		add(degreeLabel);
//		
//		JLabel creditsLabel = new JLabel("Total Credits:");
//		creditsLabel.setBounds(21, 298, 179, 26);
//		add(creditsLabel);
//		
//		JLabel difficultyLabel = new JLabel("Difficulty:");
//		difficultyLabel.setBounds(21, 345, 92, 26);
//		add(difficultyLabel);
//		
//		JLabel startDateLabel = new JLabel("Start Date:");
//		startDateLabel.setBounds(21, 392, 179, 26);
//		add(startDateLabel);
//		
//		JLabel endDateLabel = new JLabel("End Date:");
//		endDateLabel.setBounds(21, 439, 179, 26);
//		add(endDateLabel);
//		
//		JLabel personalTutorLabel = new JLabel("Personal Tutor:");
//		personalTutorLabel.setBounds(21, 486, 179, 26);
//		add(personalTutorLabel);
//		
//		JRadioButton undergradCredits = new JRadioButton("120");
//		undergradCredits.setBounds(221, 294, 186, 35);
//		add(undergradCredits);
//		
//		JRadioButton postgradCredits = new JRadioButton("180");
//		postgradCredits.setBounds(410, 294, 201, 35);
//		add(postgradCredits);
//		
////		
////		String[] difficultyLevels = {"MEng", "BEng", "MSc", "BSc", "MPsy", "BPsy"};
////		JComboBox<String> difficulty = new JComboBox<>(difficultyLevels);
////		difficulty.setBounds(221, 342, 186, 32);
////		add(difficulty);
//
//		
//		JLabel instruction = new JLabel("Fill in the form below for adding a student. Otherwise, click \"Remove Student\".");
//		instruction.setBounds(21, 201, 854, 26);
//		add(instruction);
//		
//		JComboBox startDay = new JComboBox();
//		startDay.setBounds(221, 389, 67, 32);
//		add(startDay);
//		
//		JComboBox startMonth = new JComboBox();
//		startMonth.setBounds(321, 389, 109, 32);
//		add(startMonth);
//		
//		JComboBox startYear = new JComboBox();
//		startYear.setBounds(458, 389, 109, 32);
//		add(startYear);
//		
//		JComboBox endDay = new JComboBox();
//		endDay.setBounds(221, 436, 67, 32);
//		add(endDay);
//		
//		JComboBox endMonth = new JComboBox();
//		endMonth.setBounds(321, 436, 109, 32);
//		add(endMonth);
//		
//		JComboBox endYear = new JComboBox();
//		endYear.setBounds(458, 436, 109, 32);
//		add(endYear);
//		
//		personalTutor = new JTextField();
//		personalTutor.setBounds(221, 483, 186, 32);
//		add(personalTutor);
//		personalTutor.setColumns(10);
//		
//		JButton backButton = new JButton("Back");
//		backButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				setVisible(false);
//	    		EventQueue.invokeLater(new Runnable() {
//					public void run() {
//						try {
//							RegistrarFrame frame = new RegistrarFrame();
//							frame.setVisible(true);
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				});
//			}
//		});
//		backButton.setBounds(873, 17, 141, 35);
//		add(backButton);



	}
}
