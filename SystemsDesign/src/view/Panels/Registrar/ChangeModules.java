package view.Panels.Registrar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import userclasses.Admins;
import userclasses.Registrars;
import userclasses.Students;
import userclasses.Users.UserTypes;
import view.Panels.AbstractPanel;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class ChangeModules extends AbstractPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12345L;
	
	private JTable modulesTable;
	private DefaultTableModel model;
	private static JScrollPane scroll;



	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public ChangeModules(JPanel contentPane, String studentUsername, JFrame mainFrame, Registrars registrar) throws SQLException {
		
		contentPane.removeAll();
		contentPane.revalidate();
		contentPane.repaint();
		String email = "";
		Students student = getStudent(studentUsername);
		
		setLayout(null);
		setBounds(100, 100, 1035, 647);
		
		JLabel title = new JLabel("Add or remove optional modules");
		title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD));
		title.setBounds(21, 21, 425, 26);
		contentPane.add(title);
		

	
		
		JLabel modulesLabel = new JLabel("Student Modules:");
		modulesLabel.setBounds(21, 80, 187, 26);
		contentPane.add(modulesLabel);
		
		JLabel optionalModulesLabel = new JLabel("Optional modules:");
		optionalModulesLabel.setBounds(719, 201, 196, 26);
		contentPane.add(optionalModulesLabel);
		
		JComboBox<String> optionalModulesList = new JComboBox<>(registrar.getOptionalModulesList(student.getEmail()));
		optionalModulesList.setBounds(719, 248, 247, 32);
		contentPane.add(optionalModulesList);
		
		JButton addModuleButton = new JButton("Add module");
		addModuleButton.setBounds(719, 301, 247, 35);
		contentPane.add(addModuleButton);
		
		JLabel creditsAvailable = new JLabel("Credits available:");
		creditsAvailable.setBounds(719, 119, 206, 26);
		contentPane.add(creditsAvailable);
		
		JLabel credits = new JLabel("");
		credits.setBounds(719, 154, 92, 26);
		contentPane.add(credits);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							JPanel menu = new RegistrarMenu(contentPane, mainFrame, registrar.getUsername());
							contentPane.add(menu);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		});
		
		backButton.setBounds(873, 17, 141, 35);
		contentPane.add(backButton);
		
		JLabel removeModuleLabel = new JLabel("Remove module:");
		removeModuleLabel.setBounds(719, 424, 176, 26);
		contentPane.add(removeModuleLabel);
		
		System.out.println("Student email: " + student.getEmail());
		JComboBox<String> existingModulesList = new JComboBox<>(registrar.getModulesList(student.getEmail()));
		existingModulesList.setBounds(719, 471, 247, 32);
		contentPane.add(existingModulesList);
		
		JButton removeModuleButton = new JButton("Remove module");
		removeModuleButton.setBounds(719, 524, 247, 35);
		contentPane.add(removeModuleButton);
	
		modulesTable = new JTable(model);
		scroll = new JScrollPane(modulesTable);
		scroll.setBounds(21, 127, 800, 600);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(scroll);
		insertStudentsTable(studentUsername, student.getEmail(), modulesTable, model, contentPane, scroll);
		modulesTable.setBounds(21, 127, 677, 499);
		
		contentPane.setVisible(true);
	}
}