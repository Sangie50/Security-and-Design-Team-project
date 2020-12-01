package view.Panels;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;

import userclasses.Admins;
import view.Frames.AdminFrame;
import view.Frames.LoginFrame;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class AddDeptPanel extends JPanel {
	private JTextField idText;
	private JTextField nameText;
	private JTextField entryLevelText;

	/**
	 * Create the panel.
	 */
	
	
	public AddDeptPanel(JPanel contentPane) {
		
		setLayout(null);
		
		JLabel heading = new JLabel("New Department");
		heading.setBounds(48, 37, 137, 20);
		add(heading);
		
		JLabel deptId = new JLabel("Department ID");
		deptId.setBounds(48, 93, 131, 20);
		add(deptId);
		
		
		idText = new JTextField();
		idText.setBounds(187, 90, 146, 26);
		add(idText);
		idText.setColumns(10);
		
		JLabel deptName = new JLabel("Department Name");
		deptName.setBounds(48, 157, 137, 20);
		add(deptName);
		
		nameText = new JTextField();
		nameText.setBounds(187, 154, 146, 26);
		add(nameText);
		nameText.setColumns(10);
		
		JLabel entryLevel = new JLabel("Entry Level");
		entryLevel.setBounds(48, 223, 108, 20);
		add(entryLevel);
		
		entryLevelText = new JTextField();
		entryLevelText.setBounds(187, 220, 146, 26);
		add(entryLevelText);
		entryLevelText.setColumns(10);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(48, 332, 115, 29);
		add(cancelButton);
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(482, 332, 115, 29);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							Admins.addDepartment(idText.getText(), nameText.getText(), entryLevelText.getText());
							AdminFrame frame = new AdminFrame();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	    	
			}
		});
		add(addButton);

	}
}
