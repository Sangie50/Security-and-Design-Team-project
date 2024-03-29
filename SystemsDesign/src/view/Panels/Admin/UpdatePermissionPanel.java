package view.Panels.Admin;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import userclasses.Admins;
import userclasses.Users;
import view.Panels.AbstractPanel;

public class UpdatePermissionPanel extends AbstractPanel {
	private String[] accTypeOptions = {"Admin", "Teacher", "Student", "Registrar", "Unassigned"};
	private String[] usernameOptions;
	/**
	 * Create the panel.
	 */
	public UpdatePermissionPanel(JPanel panel, JFrame mainFrame) {
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
		
		JLabel heading = new JLabel("Change Account Type");
		heading.setBounds(52, 58, 300, 20);
		heading.setFont(TITLE_FONT);
		add(heading);
		panel.add(heading);
		
		JLabel moduleId = new JLabel("Username");
		moduleId.setBounds(52, 150, 100, 20);
		add(moduleId);
		panel.add(moduleId);
		
		JLabel degreeId = new JLabel("Account Type");
		degreeId.setBounds(52, 200, 100, 20);
		add(degreeId);
		panel.add(degreeId);
		
		JLabel instruction = new JLabel("Select the username you wish to change");
		instruction.setBounds(52, 105, 326, 20);
		add(instruction);
		panel.add(instruction);
		
		try {
			usernameOptions = getAllUsernames();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JComboBox<String> usernameComboBox = new JComboBox<>(usernameOptions);
		usernameComboBox.setBounds(196, 147, 160, 26);
		add(usernameComboBox);
		panel.add(usernameComboBox);
		
		JComboBox<String> typeComboBox = new JComboBox<>(accTypeOptions);
		typeComboBox.setBounds(196, 200, 160, 26);
		add(typeComboBox);
		panel.add(typeComboBox);
		
		JButton applyButton = new JButton("Apply");
		applyButton.setBounds(516, 377, 115, 29);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String usernameSelected = usernameComboBox.getSelectedItem().toString();
					String typeSelected = typeComboBox.getSelectedItem().toString();
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					    Admins.updatePermission(usernameSelected, typeSelected);
					    JPanel menu = new AdminMenu(panel, mainFrame);
						AdminMenu.mainComboBox.setSelectedIndex(3);
						System.out.println("perm updated");
						panel.add(menu);
					}
					else if (dialogResult == JOptionPane.NO_OPTION){
						JPanel backAgain = new SetCoreModulePanel(panel, mainFrame);
						typeComboBox.setSelectedIndex(typeComboBox.getSelectedIndex());
						usernameComboBox.setSelectedIndex(usernameComboBox.getSelectedIndex());
						System.out.println("not updated");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		add(applyButton);
		panel.add(applyButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(52, 377, 115, 29);
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
							AdminMenu.mainComboBox.setSelectedIndex(3);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
	}


}
