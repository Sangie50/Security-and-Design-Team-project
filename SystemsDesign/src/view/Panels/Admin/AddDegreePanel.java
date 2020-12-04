package view.Panels.Admin;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import view.Panels.AbstractPanel;
import view.Panels.Admin.*;
import userclasses.Admins;

public class AddDegreePanel extends AbstractPanel {
	private JTextField deptIdText;
	private JTextField degreeIdText;
	private JTextField degreeNameText;
	private JTextField entryLevelText;
	private JTextField lastLevelText;
	private JTextField difficultyText;

	/**
	 * Create the panel.
	 */
	public AddDegreePanel(JPanel panel, JFrame mainFrame) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		
		setLayout(null);
		setBounds(100, 100, 1035, 647);
		
		//DEFAULT
		UIManager.put("Label.font", LABEL_FONT);
		UIManager.put("Table.font", TABLE_FONT);
		UIManager.put("TableHeader.font", HEADER_FONT);
		UIManager.put("Button.font", TABLE_FONT);
		UIManager.put("ComboBox.font", LABEL_FONT);
		
		JLabel heading = new JLabel("New Degree");
		heading.setBounds(31, 56, 146, 26);
		add(heading);
		panel.add(heading);
		
		JLabel deptId = new JLabel("Department ID");
		deptId.setBounds(31, 121, 123, 20);
		add(deptId);
		panel.add(deptId);
		
		deptIdText = new JTextField();
		deptIdText.setBounds(157, 118, 146, 26);
		add(deptIdText);
		panel.add(deptIdText);
		deptIdText.setColumns(10);
				
		JLabel degreeId = new JLabel("Degree ID");
		degreeId.setBounds(31, 169, 99, 20);
		add(degreeId);
		panel.add(degreeId);
		
		degreeIdText = new JTextField();
		degreeIdText.setBounds(157, 166, 146, 26);
		add(degreeIdText);
		panel.add(degreeIdText);
		degreeIdText.setColumns(10);
		
		JLabel degreeName = new JLabel("Degree Name");
		degreeName.setBounds(31, 221, 99, 20);
		add(degreeName);
		panel.add(degreeName);
		
		degreeNameText = new JTextField();
		degreeNameText.setBounds(157, 218, 146, 26);
		add(degreeNameText);
		panel.add(degreeNameText);
		degreeNameText.setColumns(10);
		
		JLabel difficulty = new JLabel("Difficulty");
		difficulty.setBounds(31, 272, 69, 20);
		add(difficulty);
		panel.add(difficulty);
		
		difficultyText = new JTextField();
		difficultyText.setBounds(157, 269, 146, 26);
		add(difficultyText);
		panel.add(difficultyText);
		difficultyText.setColumns(10);
		
		
		/*String[] options = {"BSc", "BEng", "MSc", "MEng"};
		
		JComboBox comboBox = new JComboBox(options);
		comboBox.setBounds(157, 269, 104, 23);
		add(comboBox);
		*/
		
		JLabel entryLevel = new JLabel("Entry Level");
		entryLevel.setBounds(31, 325, 99, 20);
		add(entryLevel);
		panel.add(entryLevel);
		
		entryLevelText = new JTextField();
		entryLevelText.setBounds(157, 322, 146, 26);
		add(entryLevelText);
		panel.add(entryLevelText);
		entryLevelText.setColumns(10);
		
		JLabel lastLevel = new JLabel("Last Level");
		lastLevel.setBounds(31, 382, 99, 20);
		add(lastLevel);
		panel.add(lastLevel);
		
		lastLevelText = new JTextField();
		lastLevelText.setBounds(157, 379, 146, 26);
		add(lastLevelText);
		panel.add(lastLevelText);
		lastLevelText.setColumns(10);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(31, 529, 115, 29);
		add(cancelButton);
		panel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JPanel menu = new AdminMenu(panel, mainFrame);
							panel.add(menu);
							AdminMenu.mainComboBox.setSelectedIndex(1);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(514, 529, 115, 29);
		add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Admins.addDegree(degreeIdText.getText(), deptIdText.getText(), entryLevelText.getText(), 
									difficultyText.getText(), degreeNameText.getText(), lastLevelText.getText());
							JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Degree Added");
							JPanel menu = new AdminMenu(panel, mainFrame);
							AdminMenu.mainComboBox.setSelectedIndex(1);
							panel.add(menu);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	    	
			}
		});
		panel.add(addButton);
		
		
	}
}
