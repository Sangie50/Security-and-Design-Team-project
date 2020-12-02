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

import userclasses.Admins;
import view.Panels.AbstractPanel;

public class RemoveDegreePanel extends JPanel {
	private String[] options;
	/**
	 * Create the panel.
	 */
	public RemoveDegreePanel(JPanel panel, JFrame mainFrame) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		setLayout(null);
		
		JLabel heading = new JLabel("Remove Degree");
		heading.setBounds(52, 58, 300, 20);
		heading.setFont(new Font("Tahoma", Font.BOLD, 21));
		add(heading);
		panel.add(heading);
		
		JLabel degreeId = new JLabel("Degree ID");
		degreeId.setBounds(52, 150, 100, 20);
		add(degreeId);
		panel.add(degreeId);
		
		JLabel instruction = new JLabel("Select the degree you wish to remove");
		instruction.setBounds(52, 105, 326, 20);
		add(instruction);
		panel.add(instruction);
		
		try {
			options = AbstractPanel.getDegreeId();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JComboBox<String> degreeIdComboBox = new JComboBox<>(options);
		degreeIdComboBox.setBounds(196, 147, 160, 26);
		add(degreeIdComboBox);
		panel.add(degreeIdComboBox);
		
		JButton removeButton = new JButton("Remove");
		removeButton.setBounds(516, 377, 115, 29);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to remove?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					    Admins.removeDegree(degreeIdComboBox.getSelectedItem().toString());
					    JPanel menu = new AdminMenu(panel, mainFrame);
						AdminMenu.mainComboBox.setSelectedIndex(1);
						System.out.println("degree removed");
						panel.add(menu);
					}
					else if (dialogResult == JOptionPane.NO_OPTION){
						JPanel backRemoveDegree = new RemoveDegreePanel(panel, mainFrame);
						degreeIdComboBox.setSelectedIndex(degreeIdComboBox.getSelectedIndex());
						System.out.println("degree not removed");
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		add(removeButton);
		panel.add(removeButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(52, 377, 115, 29);
		add(cancelButton);
		panel.add(cancelButton);

	}
}
