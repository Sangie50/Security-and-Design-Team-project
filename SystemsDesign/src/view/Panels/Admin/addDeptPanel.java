package view.Panels.Admin;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import userclasses.Admins;
import view.Frames.AdminFrame;
import view.Frames.LoginFrame;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class AddDeptPanel extends JPanel {
	private JTextField idText;
	private JTextField nameText;
	private JTextField entryLevelText;

	/**
	 * Create the panel.
	 */
	
	
	public AddDeptPanel(JPanel panel, JFrame mainFrame) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		
		setLayout(null);
		setBounds(100, 100, 1035, 647);
		
		JLabel heading = new JLabel("New Department");
		heading.setBounds(48, 37, 300, 20);
		heading.setFont(new Font("Tahoma", Font.BOLD, 21));
		panel.add(heading);
		
		JLabel deptId = new JLabel("Department ID");
		deptId.setBounds(48, 93, 131, 20);
		panel.add(deptId);
		
		
		idText = new JTextField();
		idText.setBounds(187, 90, 146, 26);
		panel.add(idText);
		idText.setColumns(10);
		
		JLabel deptName = new JLabel("Department Name");
		deptName.setBounds(48, 157, 137, 20);
		panel.add(deptName);
		
		nameText = new JTextField();
		nameText.setBounds(187, 154, 146, 26);
		panel.add(nameText);
		nameText.setColumns(10);
		
		JLabel entryLevel = new JLabel("Entry Level");
		entryLevel.setBounds(48, 223, 108, 20);
		panel.add(entryLevel);
		
		entryLevelText = new JTextField();
		entryLevelText.setBounds(187, 220, 146, 26);
		panel.add(entryLevelText);
		entryLevelText.setColumns(10);
		
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(482, 332, 115, 29);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Admins.addDepartment(idText.getText(), nameText.getText(), entryLevelText.getText());
							JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Department Added.");
							JPanel menu = new AdminMenu(panel, mainFrame);
							panel.add(menu);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	    	
			}
		});
		panel.add(addButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(48, 332, 115, 29);
		panel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JPanel menu = new AdminMenu(panel, mainFrame);
							panel.add(menu);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
	}
}



