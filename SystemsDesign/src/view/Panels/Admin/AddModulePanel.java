package view.Panels.Admin;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import userclasses.Admins;
import view.Panels.AbstractPanel;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;

public class AddModulePanel extends AbstractPanel {
	private JTextField moduleIdText;
	private JTextField moduleNameText;
	private JTextField deptIdText;
	private JTextField creditsWorthText;
	private Boolean isTaught;
	private int passMark;

	/**
	 * Create the panel.
	 */
	public AddModulePanel(JPanel panel, JFrame mainFrame) {
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
		
		JLabel heading = new JLabel("New Module");
		heading.setBounds(59, 44, 183, 20);
		heading.setFont(TITLE_FONT);
		add(heading);
		panel.add(heading);
		
		JLabel moduleId = new JLabel("Module Id");
		moduleId.setBounds(59, 114, 95, 20);
		add(moduleId);
		panel.add(moduleId);
		
		moduleIdText = new JTextField();
		moduleIdText.setBounds(202, 111, 146, 26);
		add(moduleIdText);
		panel.add(moduleIdText);
		moduleIdText.setColumns(10);
		
		JLabel moduleName = new JLabel("Module Name");
		moduleName.setBounds(59, 169, 107, 20);
		add(moduleName);
		panel.add(moduleName);
		
		moduleNameText = new JTextField();
		moduleNameText.setBounds(202, 166, 146, 26);
		add(moduleNameText);
		panel.add(moduleNameText);
		moduleNameText.setColumns(10);
		
		JLabel deptId = new JLabel("Department ID");
		deptId.setBounds(59, 220, 124, 20);
		add(deptId);
		panel.add(deptId);
		
		deptIdText = new JTextField();
		deptIdText.setBounds(202, 217, 146, 26);
		add(deptIdText);
		panel.add(deptIdText);
		deptIdText.setColumns(10);
		
		JLabel creditsWorth = new JLabel("Credits Worth");
		creditsWorth.setBounds(59, 268, 107, 20);
		add(creditsWorth);
		panel.add(creditsWorth);
		
		creditsWorthText = new JTextField();
		creditsWorthText.setBounds(202, 265, 146, 26);
		add(creditsWorthText);
		panel.add(creditsWorthText);
		creditsWorthText.setColumns(10);
		
		JLabel passMarkLabel = new JLabel("Pass Mark");
		passMarkLabel.setBounds(59, 315, 95, 20);
		panel.add(passMarkLabel);
		
		JRadioButton ugRadioButton = new JRadioButton("40 (for level 1-3)");
		ugRadioButton.setBounds(202, 311, 155, 29);
		panel.add(ugRadioButton);
		
		JRadioButton pgRadioButton = new JRadioButton("50 (for level 4)");
		pgRadioButton.setBounds(411, 311, 155, 29);
		panel.add(pgRadioButton);
		
		ButtonGroup passGroup = new ButtonGroup();
		passGroup.add(ugRadioButton);
		passGroup.add(pgRadioButton);
		
		JLabel type = new JLabel("Type");
		type.setBounds(59, 364, 69, 20);
		panel.add(type);
		
		JRadioButton taughtRadioButton = new JRadioButton("Taught");
		taughtRadioButton.setBounds(202, 360, 155, 29);
		panel.add(taughtRadioButton);
		
		JRadioButton researchRadioButton = new JRadioButton("Research");
		researchRadioButton.setBounds(411, 360, 155, 29);
		panel.add(researchRadioButton);
		
		ButtonGroup typeGroup = new ButtonGroup();
		typeGroup.add(taughtRadioButton);
		typeGroup.add(researchRadioButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(59, 447, 115, 29);
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
							AdminMenu.mainComboBox.setSelectedIndex(2);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(672, 447, 115, 29);
		add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							if (taughtRadioButton.isSelected()) {
								isTaught = true;
							}
							else if (researchRadioButton.isSelected()) {
								isTaught = false;
							}
							System.out.println(isTaught);
							
							if(ugRadioButton.isSelected()) {
								passMark = 40;
							}
							else if (pgRadioButton.isSelected()){
								passMark = 50;
							}
							
							System.out.println(passMark);
							Admins.addModule(moduleIdText.getText(), moduleNameText.getText(), Integer.parseInt(creditsWorthText.getText()),
									deptIdText.getText(), passMark, isTaught);
							JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Module Added");
							JPanel menu = new AdminMenu(panel, mainFrame);
							AdminMenu.mainComboBox.setSelectedIndex(2);
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
