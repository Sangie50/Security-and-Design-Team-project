package view.Panels.Registrar;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import userclasses.Registrars;
import userclasses.Students;
import view.Panels.AbstractPanel;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class CheckRegistered extends AbstractPanel {
	private JTable studentInfoTable;
	private JTextField degreeId;
	private JTextField difficulty;
	private JTable modulesTable;
	private DefaultTableModel model = new DefaultTableModel();
	private static JScrollPane scroll;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public CheckRegistered(JPanel contentPane, String studentUsername, JFrame mainFrame, Registrars registrar) throws SQLException {
		
		Students student = getStudent(studentUsername);
		
		contentPane.removeAll();
		contentPane.revalidate();
		contentPane.repaint();
		setBounds(PANEL_SIZE);
		setLayout(null);
		
		//DEFAULT
				UIManager.put("Label.font", LABEL_FONT);
				UIManager.put("Table.font", TABLE_FONT);
				UIManager.put("TableHeader.font", HEADER_FONT);
				UIManager.put("Button.font", TABLE_FONT);
				UIManager.put("ComboBox.font", LABEL_FONT);
				
		//labels
		JLabel title = new JLabel("Check Registration details of ");
		title.setBounds(364, 5, 307, 26);
		title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD));
		contentPane.add(title);
		
		JLabel degreeIdLabel = new JLabel("Edit Degree ID");
		degreeIdLabel.setBounds(786, 77, 153, 26);
		contentPane.add(degreeIdLabel);

		JLabel difficultyLabel = new JLabel("Edit Difficulty");
		difficultyLabel.setBounds(786, 210, 153, 26);
		contentPane.add(difficultyLabel);
		
		JLabel startdateLabel = new JLabel("Edit start date");
		startdateLabel.setBounds(786, 352, 169, 26);
		contentPane.add(startdateLabel);

		JLabel endDateLabel = new JLabel("Edit end date");
		endDateLabel.setBounds(786, 499, 169, 26);
		contentPane.add(endDateLabel);
		//------------------------------
		
		//combo box
		
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
		
		JComboBox<String> startDay = new JComboBox<>(days);
		startDay.setBounds(786, 386, 44, 32);
		contentPane.add(startDay);
		
		JComboBox<String> startMonth = new JComboBox<>(months);
		startMonth.setBounds(849, 386, 44, 32);
		contentPane.add(startMonth);
		
		JComboBox<String> startYear = new JComboBox<>(years);
		startYear.setBounds(914, 386, 100, 32);
		contentPane.add(startYear);
		
		
		
		JComboBox<String> endDay = new JComboBox<>(days);
		endDay.setBounds(786, 533, 44, 32);
		contentPane.add(endDay);
		
		JComboBox<String> endMonth = new JComboBox<>(months);
		endMonth.setBounds(849, 533, 44, 32);
		contentPane.add(endMonth);
		
		JComboBox<String> endYear = new JComboBox<>(years);
		endYear.setBounds(914, 533, 100, 32);
		contentPane.add(endYear);
		
		//--------------------------
		
		//text fields
		
		degreeId = new JTextField();
		degreeId.setBounds(786, 111, 186, 32);
		contentPane. add(degreeId);
		degreeId.setColumns(10);
		
		
		difficulty = new JTextField();
		difficulty.setBounds(786, 243, 186, 32);
		contentPane.add(difficulty);
		difficulty.setColumns(10);
		
		//---------------------------
		
		//buttons
		JButton backButton = new JButton("Back");
		backButton.setBounds(873, 21, 141, 35);
		contentPane.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JPanel menu = new RegistrarMenu(contentPane, mainFrame, registrar.getUsername());
							contentPane.add(menu);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		JButton updateDegreeId = new JButton("Update DegreeID");
		updateDegreeId.setBounds(786, 154, 141, 35);
		contentPane.add(updateDegreeId);
		updateDegreeId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					registrar.updateDegreeId(student.getEmail(), degreeId.getText());
					model.setRowCount(0);
					insert(studentUsername, model);
					JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Degree ID updated.");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton updateDifficulty = new JButton("Update Difficulty");
		updateDifficulty.setBounds(786, 296, 141, 35);
		contentPane.add(updateDifficulty);
		updateDifficulty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					registrar.updateDifficulty(student.getEmail(), difficulty.getText());
					model.setRowCount(0);
					insert(studentUsername, model);
					JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Difficulty updated.");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton updateStartDate = new JButton("Update start date");
		updateStartDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date = convertDate((String) startDay.getSelectedItem(), (String) startMonth.getSelectedItem(), (String) startYear.getSelectedItem());
				try {
					registrar.updateStartDate(student.getEmail(), date);
					model.setRowCount(0);
					insert(studentUsername, model);
					JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Date updated.");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		updateStartDate.setBounds(786, 443, 141, 35);
		contentPane.add(updateStartDate);
		
		JButton updateEndDate = new JButton("Update end date");
		updateEndDate.setBounds(786, 586, 141, 35);
		contentPane.add(updateEndDate);
		updateEndDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date = convertDate((String) startDay.getSelectedItem(), (String) startMonth.getSelectedItem(), (String) startYear.getSelectedItem());
				try {
					registrar.updateEndDate(student.getEmail(), date);
					model.setRowCount(0);
					insert(studentUsername, model);
					JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Date updated.");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		//----------------------------
		
		//table

		studentInfoTable = new JTable(model);
		studentInfoTable.setBounds(21, 52, 744, 574);
		studentInfoTable.setBackground(UIManager.getColor("Button.background"));
		studentInfoTable.setEnabled(false);
		studentInfoTable.getTableHeader().setReorderingAllowed(false);
		insert(studentUsername, model);
		contentPane.add(studentInfoTable);
		scroll = new JScrollPane(studentInfoTable);
		scroll.setBounds(20,55,744,574);
		contentPane.add(scroll);
		
		//--------------------------

	}
	
	public void insert(String username, DefaultTableModel model) throws SQLException {
		String[] headers = { "Username", "Forename", "Surname", "Account Type", 
				"Difficulty", "Total Credits" , "Start Date", "End Date", "Degree ID",
				"Degree Name"};

		Students student = getStudent(username);
		model.setColumnIdentifiers(headers); 
		ArrayList<ArrayList<String>> ar = student.displayStudentDetails(student.getEmail());
	    for (int i = 0; i < (ar.size()); i++) {
	    	String un = ar.get(i).get(0); 
	    	String forename = ar.get(i).get(1); 
	    	String surname = ar.get(i).get(2); 
	    	String accountType = ar.get(i).get(3); 
	    	String difficulty = ar.get(i).get(4); 
	    	String creds = ar.get(i).get(5); 
	    	String startDate = ar.get(i).get(6);
	    	String endDate = ar.get(i).get(7);
	    	String degreeId = ar.get(i).get(8);
	    	String degreeName = ar.get(i).get(9);
	    	String[] arr = {un, forename, surname, accountType, difficulty, creds,
	    			startDate, endDate, degreeId, degreeName};
	    	model.addRow(arr);

	    }
	}
}
