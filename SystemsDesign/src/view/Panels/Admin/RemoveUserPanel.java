package view.Panels.Admin;

import javax.swing.JPanel;

import userclasses.Admins;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.event.ActionEvent;
import view.Panels.AbstractPanel;

public class RemoveUserPanel extends JPanel {
	private String[] options;
	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public RemoveUserPanel(JPanel panel, JFrame mainFrame) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		setLayout(null);
		
		JLabel heading = new JLabel("Remove User");
		heading.setBounds(52, 58, 127, 20);
		heading.setFont(new Font("Tahoma", Font.BOLD, 21));
		add(heading);
		panel.add(heading);
		
		JLabel username = new JLabel("Username");
		username.setBounds(52, 150, 100, 20);
		add(username);
		panel.add(username);
		
		JLabel instruction = new JLabel("Select the user you wish to remove");
		instruction.setBounds(52, 105, 326, 20);
		add(instruction);
		panel.add(instruction);
		
		try {
			options = AbstractPanel.getUsernames();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JComboBox<String> usernameComboBox = new JComboBox<>(options);
		usernameComboBox.setBounds(196, 147, 160, 26);
		add(usernameComboBox);
		panel.add(usernameComboBox);
		
		JButton removeButton = new JButton("Remove");
		removeButton.setBounds(516, 377, 115, 29);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					int dialogButton = JOptionPane.YES_NO_OPTION;
					int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to remove?","Warning",dialogButton);
					if(dialogResult == JOptionPane.YES_OPTION){
					    Admins.removeUser(usernameComboBox.getSelectedItem().toString());
					    JPanel menu = new AdminMenu(panel, mainFrame);
						AdminMenu.mainComboBox.setSelectedIndex(3);
						System.out.println("user removed");
						panel.add(menu);
					}
					else if (dialogResult == JOptionPane.NO_OPTION){
						JPanel backRemoveUser = new RemoveUserPanel(panel, mainFrame);
						usernameComboBox.setSelectedIndex(usernameComboBox.getSelectedIndex());
						System.out.println("user not removed");
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
