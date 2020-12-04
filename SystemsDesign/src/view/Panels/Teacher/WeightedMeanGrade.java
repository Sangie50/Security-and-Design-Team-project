package view.Panels.Teacher;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import view.Panels.AbstractPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import academics.Grades;
import userclasses.Students;
import userclasses.Teachers;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class WeightedMeanGrade extends AbstractPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1423435135L;
	
	private JTable table;
	private DefaultTableModel model = new DefaultTableModel();
	private static JScrollPane scroll;
	private Students student;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public WeightedMeanGrade(JPanel contentPane, String studentUsername, JFrame mainFrame, Teachers teacher) throws SQLException {
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
		
		
		//settings
		student = getStudent(studentUsername);
		
		
		//combo box		
		JComboBox<String> levelOfStudy = new JComboBox<>(
				student.getAllLevelsOfStudy(student.getEmail()));
		levelOfStudy.setBounds(885, 65, 119, 32);
		contentPane.add(levelOfStudy);
		//--------------------------------

				
		//labels
		JLabel studentProgressLabel = new JLabel("Student Progress");
		studentProgressLabel.setBounds(21, 21, 257, 26);
		contentPane.add(studentProgressLabel);
	
		JLabel weightedMeanGradeLabel = new JLabel("Weighted mean grade:");
		weightedMeanGradeLabel.setBounds(21, 361, 233, 26);
		contentPane.add(weightedMeanGradeLabel);
		
		JLabel weightedMeanGrade = new JLabel(String.valueOf(
				Grades.getWeightedYearGrade(student.getEmail(), 
						(String) levelOfStudy.getSelectedItem())));
		weightedMeanGrade.setBounds(252, 361, 92, 26);
		contentPane.add(weightedMeanGrade);
		
		JLabel studentModulesTable = new JLabel("Student Modules:");
		studentModulesTable.setBounds(21, 68, 181, 26);
		contentPane.add(studentModulesTable);
		
		JLabel periodOfStudyLabel = new JLabel("Level of study:");
		periodOfStudyLabel.setBounds(687, 65, 174, 26);
		contentPane.add(periodOfStudyLabel);

		JLabel nextLevelLabel = new JLabel("Next level:");
		nextLevelLabel.setBounds(21, 408, 215, 26);
		contentPane.add(nextLevelLabel);
		
		JLabel nextLevel = new JLabel(teacher.displayNextLevel(
				student.getEmail(), (String) levelOfStudy.getSelectedItem()));
		nextLevel.setBounds(252, 408, 92, 26);
		contentPane.add(nextLevel);
		
		JLabel degreeClassLabel = new JLabel("Degree class:");
		degreeClassLabel.setBounds(21, 455, 152, 26);
		contentPane.add(degreeClassLabel);
		
		JLabel degreeClass = new JLabel(Grades.degreeClassification(student.getEmail()));
		degreeClass.setBounds(252, 455, 92, 26);
		contentPane.add(degreeClass);	
		
		JLabel lblNewLabel = new JLabel("Pass Year:");
		lblNewLabel.setBounds(504, 408, 141, 26);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(648, 408, 92, 26);
		contentPane.add(lblNewLabel_1);
		//---------------------------------
		
		
		
		
		
		
		//Buttons
		JButton progressStudent = new JButton("Progress Student");
		progressStudent.setBounds(21, 554, 215, 35);
		contentPane.add(progressStudent);
		
		JButton backButton = new JButton("Back");
		backButton.setBounds(873, 554, 141, 35);
		contentPane.add(backButton);

		//----------------------------------
		
		
		//table
		table = new JTable(model);
		table.setBounds(21, 112, 983, 228);
		table.setBackground(UIManager.getColor("Button.background"));
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		contentPane.add(table);
		insert(student.getUsername(), (String) levelOfStudy.getSelectedItem(), teacher, model);
		scroll = new JScrollPane(table);
		scroll.setBounds(21, 112, 983, 228);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(scroll);
	}
	
	public void insert(String username, String levelOfStudy, Teachers teacher, DefaultTableModel model) throws SQLException {
		String[] headers = { "Module ID", "Module Name", "Initial Grade", "Resit Grade", "Pass Grade"};

		Students student = getStudent(username);
		model.setColumnIdentifiers(headers); 
		ArrayList<ArrayList<String>> ar = teacher.displayByLevelOfStudy(student.getEmail(), levelOfStudy);
	    for (int i = 0; i < (ar.size()); i++) {
	    	String moduleid = ar.get(i).get(0); //module id
	    	String modName = ar.get(i).get(1); //module name
	    	String initGrade = ar.get(i).get(2); //initial grade
	    	String reGrade = ar.get(i).get(3); //resit grade
	    	String pass = ar.get(i).get(4); //pass grade
	    	String[] arr = {moduleid, modName, initGrade, reGrade, pass};
	    	model.addRow(arr);

	    }
	}
}