package view.Panels.Admin;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import userclasses.Admins;
import view.Panels.AbstractPanel;

public class AddOtherDeptPanel extends AbstractPanel {
	private String[] degreeOptions;
	private String[] deptOptions;
	/**
	 * Create the panel.
	 */
	public AddOtherDeptPanel(JPanel panel, JFrame mainFrame) {
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
		
		JLabel heading = new JLabel("Add Secondary Department");
		heading.setBounds(52, 58, 300, 20);
		heading.setFont(TITLE_FONT);
		add(heading);
		panel.add(heading);
		
		JLabel degreeId = new JLabel("Degree ID");
		degreeId.setBounds(52, 150, 100, 20);
		add(degreeId);
		panel.add(degreeId);
		
		JLabel deptId = new JLabel("Department ID");
		deptId.setBounds(52, 200, 100, 20);
		add(deptId);
		panel.add(deptId);
		
		JLabel instruction = new JLabel("Select the department and degree you wish to link");
		instruction.setBounds(52, 105, 500, 20);
		add(instruction);
		panel.add(instruction);
		
		try {
			degreeOptions = getDegreeId();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JComboBox<String> degreeIdComboBox = new JComboBox<>(degreeOptions);
		degreeIdComboBox.setBounds(196, 147, 160, 26);
		add(degreeIdComboBox);
		panel.add(degreeIdComboBox);
		
		try {
			deptOptions = getDeptId();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JComboBox<String> deptIdComboBox = new JComboBox<>(deptOptions);
		deptIdComboBox.setBounds(196, 200, 160, 26);
		add(deptIdComboBox);
		panel.add(deptIdComboBox);
		
		JButton linkButton = new JButton("Link");
		linkButton.setBounds(516, 377, 115, 29);
		linkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to link?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					    Admins.addDegreePartner(degreeIdComboBox.getSelectedItem().toString(), deptIdComboBox.getSelectedItem().toString());
					    JPanel menu = new AdminMenu(panel, mainFrame);
						AdminMenu.mainComboBox.setSelectedIndex(1);
						System.out.println("degree and dept linked");
						panel.add(menu);
					}
					else if (dialogResult == JOptionPane.NO_OPTION){
						JPanel backAgain = new AddOtherDeptPanel(panel, mainFrame);
						degreeIdComboBox.setSelectedIndex(degreeIdComboBox.getSelectedIndex());
						deptIdComboBox.setSelectedIndex(deptIdComboBox.getSelectedIndex());
						System.out.println("degree and dept not linked");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		add(linkButton);
		panel.add(linkButton);
		
		JButton cancelButton = new JButton("Back");
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
							AdminMenu.mainComboBox.setSelectedIndex(1);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});

	}
	

}
