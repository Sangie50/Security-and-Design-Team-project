package view.Panels.Admin;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;

import userclasses.Admins;
import view.Panels.AbstractPanel;

import javax.swing.JComboBox;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.swing.JButton;

public class AddUserPanel extends AbstractPanel {
	private JTextField usernameText;
	private JTextField forenameText;
	private JTextField surnameText;
	private JTextField passwordText;

	/**
	 * Create the panel.
	 */
	public AddUserPanel(JPanel panel, JFrame mainFrame) {
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
		
		JLabel heading = new JLabel("New User");
		heading.setBounds(51, 60, 129, 20);
		heading.setFont(TITLE_FONT);
		add(heading);
		panel.add(heading);
		
		JLabel username = new JLabel("Username");
		username.setBounds(51, 113, 92, 20);
		add(username);
		panel.add(username);
		
		usernameText = new JTextField();
		usernameText.setBounds(170, 110, 146, 26);
		add(usernameText);
		panel.add(usernameText);
		usernameText.setColumns(10);
		
		JLabel title = new JLabel("Title");
		title.setBounds(51, 164, 69, 20);
		add(title);
		panel.add(title);
		
		String[] titleOptions = {"Mr", "Ms", "Mrs", "Miss", "Dr."};
		JComboBox titleComboBox = new JComboBox(titleOptions);
		titleComboBox.setBounds(170, 161, 109, 23);
		add(titleComboBox);
		panel.add(titleComboBox);
		
		
		JLabel forename = new JLabel("Forename");
		forename.setBounds(51, 213, 109, 20);
		add(forename);
		panel.add(forename);
		
		forenameText = new JTextField();
		forenameText.setBounds(170, 210, 146, 26);
		add(forenameText);
		panel.add(forenameText);
		forenameText.setColumns(10);
		
		JLabel surname = new JLabel("Surname");
		surname.setBounds(51, 267, 69, 20);
		add(surname);
		panel.add(surname);
		
		surnameText = new JTextField();
		surnameText.setBounds(170, 264, 146, 26);
		add(surnameText);
		panel.add(surnameText);
		surnameText.setColumns(10);
		
		JLabel accountType = new JLabel("Account Type");
		accountType.setBounds(51, 323, 109, 20);
		add(accountType);
		panel.add(accountType);
		
		String[] typeOptions = {"Admin", "Registrar", "Teacher", "Student"};
		JComboBox typeComboBox = new JComboBox(typeOptions);
		typeComboBox.setBounds(170, 320, 109, 23);
		add(typeComboBox);
		panel.add(typeComboBox);
		
		JLabel password = new JLabel("Password");
		password.setBounds(51, 383, 92, 20);
		add(password);
		panel.add(password);
		
		passwordText = new JTextField();
		passwordText.setBounds(170, 377, 146, 26);
		add(passwordText);
		panel.add(passwordText);
		passwordText.setColumns(10);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(51, 519, 115, 29);
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
		
		JButton addButton = new JButton("Add");
		addButton.setBounds(511, 519, 115, 29);
		add(addButton);
		panel.add(addButton);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setVisible(false);
				String titleText = titleComboBox.getSelectedItem().toString();
				String type = typeComboBox.getSelectedItem().toString();
	    		EventQueue.invokeLater(new Runnable() {
					public void run()  {
						try {
							Admins.addUser(usernameText.getText(), titleText, forenameText.getText(), surnameText.getText(), 
									type, passwordText.getText());
							if (Admins.duplicateUser) {
								System.out.println("user exists");
								JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Username already exists");
								JPanel backNewUser = new AddUserPanel(panel, mainFrame);
							}
							else if (!Admins.duplicateUser) {
								JOptionPane.showMessageDialog(mainFrame.getComponent(0), "User Added");
								JPanel menu = new AdminMenu(panel, mainFrame);
								AdminMenu.mainComboBox.setSelectedIndex(3);
								panel.add(menu);
							}
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
