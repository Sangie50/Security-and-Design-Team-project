package view.Panels.Admin;

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

public class SetCoreModulePanel extends AbstractPanel{
	private String[] degreeOptions;
	private String[] moduleOptions;
	/**
	 * Create the panel.
	 */
	public SetCoreModulePanel(JPanel panel, JFrame mainFrame) {
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
		
		JLabel heading = new JLabel("Set Core Module");
		heading.setBounds(52, 58, 300, 20);
		add(heading);
		panel.add(heading);
		
		JLabel moduleId = new JLabel("Module ID");
		moduleId.setBounds(52, 150, 100, 20);
		add(moduleId);
		panel.add(moduleId);
		
		JLabel degreeId = new JLabel("Degree ID");
		degreeId.setBounds(52, 200, 100, 20);
		add(degreeId);
		panel.add(degreeId);
		
		JLabel instruction = new JLabel("Select the module and degree you wish to set as core");
		instruction.setBounds(52, 105, 326, 20);
		add(instruction);
		panel.add(instruction);
		
		try {
			moduleOptions = getModuleId();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JComboBox<String> moduleIdComboBox = new JComboBox<>(moduleOptions);
		moduleIdComboBox.setBounds(196, 147, 160, 26);
		add(moduleIdComboBox);
		panel.add(moduleIdComboBox);
		
		try {
			degreeOptions = getDegreeId();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		JComboBox<String> degreeIdComboBox = new JComboBox<>(degreeOptions);
		degreeIdComboBox.setBounds(196, 200, 160, 26);
		add(degreeIdComboBox);
		panel.add(degreeIdComboBox);
		
		JButton applyButton = new JButton("Apply");
		applyButton.setBounds(516, 377, 115, 29);
		applyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					String moduleIdSelected = moduleIdComboBox.getSelectedItem().toString();
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					    Admins.setCoreModule(moduleIdSelected, degreeIdComboBox.getSelectedItem().toString(), getLevelOFStudyFromModuleId(moduleIdSelected));
					    JPanel menu = new AdminMenu(panel, mainFrame);
						AdminMenu.mainComboBox.setSelectedIndex(2);
						System.out.println("applied");
						panel.add(menu);
					}
					else if (dialogResult == JOptionPane.NO_OPTION){
						JPanel backAgain = new SetCoreModulePanel(panel, mainFrame);
						degreeIdComboBox.setSelectedIndex(degreeIdComboBox.getSelectedIndex());
						moduleIdComboBox.setSelectedIndex(moduleIdComboBox.getSelectedIndex());
						System.out.println("not applied");
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

	}

}
