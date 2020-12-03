package view.Panels.Registrar;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
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
import javax.swing.DefaultComboBoxModel;
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
	private DefaultTableModel model = new DefaultTableModel();
	private static JScrollPane scroll;





	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public ChangeModules(JPanel contentPane, String studentUsername, JFrame mainFrame, Registrars registrar) throws SQLException {
		
		contentPane.removeAll();
		contentPane.revalidate();
		contentPane.repaint();
		Students student = getStudent(studentUsername);
		
		setLayout(null);
		setBounds(PANEL_SIZE);
		
		//DEFAULT
		UIManager.put("Label.font", LABEL_FONT);
		UIManager.put("Table.font", TABLE_FONT);
		UIManager.put("TableHeader.font", HEADER_FONT);
		UIManager.put("Button.font", TABLE_FONT);
		UIManager.put("ComboBox.font", LABEL_FONT);

		//labels

		JLabel title = new JLabel("Add or remove optional modules");
		title.setFont(TITLE_FONT);
		title.setBounds(21, 21, 425, 26);
		contentPane.add(title);
		
		
		JLabel modulesLabel = new JLabel("Student Modules:");
		modulesLabel.setBounds(21, 80, 187, 26);
		contentPane.add(modulesLabel);
		
		JLabel optionalModulesLabel = new JLabel("Optional modules:");
		optionalModulesLabel.setBounds(719, 201, 196, 26);
		contentPane.add(optionalModulesLabel);

		JLabel creditsAvailable = new JLabel("Credits available:");
		creditsAvailable.setBounds(719, 119, 206, 26);
		contentPane.add(creditsAvailable);

		int remainingCredits = student.getTotalCredits() - registrar.checkCredits(student.getEmail()); 
		System.out.println(remainingCredits);
		JLabel credits = new JLabel(Integer.toString(remainingCredits));
		if (remainingCredits < 0) credits.setForeground(Color.red);
		credits.setBounds(719, 154, 92, 26);
		contentPane.add(credits);
		
		JLabel removeModuleLabel = new JLabel("Remove module:");
		removeModuleLabel.setBounds(719, 424, 176, 26);
		contentPane.add(removeModuleLabel);
		//-----------------------------
		
			
		//Combo box
		JComboBox<String> optionalModulesList = new JComboBox<>(registrar.getOptionalModulesList(student.getEmail()));
		optionalModulesList.setBounds(719, 248, 247, 32);
		contentPane.add(optionalModulesList);
		
		System.out.println("Student email: " + student.getEmail());
		System.out.println(studentUsername);
		JComboBox<String> existingModulesList = new JComboBox<>(registrar.getModulesList(student.getEmail()));
		existingModulesList.setBounds(719, 471, 247, 32);
		contentPane.add(existingModulesList);
		
		//--------------
		
		//table	
	

		modulesTable = new JTable(model);
//		modulesTable.setBounds(21, 127, 677, 499);
		modulesTable.setBackground(UIManager.getColor("Button.background"));
		modulesTable.setEnabled(false);
		modulesTable.getTableHeader().setReorderingAllowed(false);
		contentPane.add(modulesTable);
		insertStudentsTable(studentUsername, student.getEmail(), model);
		scroll = new JScrollPane(modulesTable);
		scroll.setBounds(10, 120, 700, 600);
//		scroll.setBounds(-20, 200, 800, 600);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(scroll);
		
	    modulesTable.getColumnModel().getColumn(0).setPreferredWidth(1);
	    modulesTable.getColumnModel().getColumn(1).setPreferredWidth(1);
	    modulesTable.getColumnModel().getColumn(2).setPreferredWidth(1);
	    modulesTable.getColumnModel().getColumn(4).setPreferredWidth(2);
	    modulesTable.getColumnModel().getColumn(5).setPreferredWidth(2);
	    modulesTable.getColumnModel().getColumn(6).setPreferredWidth(1);
		//--------------
	    
	  //buttons


	  		JButton addModuleButton = new JButton("Add module");
	  		addModuleButton.setBounds(719, 301, 247, 35);
	  		addModuleButton.addActionListener(new ActionListener() {
	  			public void actionPerformed(ActionEvent e) {
	  				try {
	  					int moduleCredit = registrar.getModuleCredits((String) optionalModulesList.getSelectedItem());
	  				    int calculateCredit = student.getTotalCredits() - registrar.getModuleCredits((String) optionalModulesList.getSelectedItem()) - moduleCredit;
	  				    if (calculateCredit >=  0) {
	  				    	registrar.linkModuleToStudent(student.getEmail(), (String) optionalModulesList.getSelectedItem());
							model.setRowCount(0);
							insertStudentsTable(studentUsername, student.getEmail(), model);
							credits.setText(Integer.toString(student.getTotalCredits() - registrar.getModuleCredits((String) optionalModulesList.getSelectedItem()) - moduleCredit));
							existingModulesList.removeAllItems();
							existingModulesList.setModel(new DefaultComboBoxModel<>(registrar.getModulesList(student.getEmail())));
							contentPane.revalidate();
							contentPane.getIgnoreRepaint();
							JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Module Added.");
	  				    }
	  				    else {
	  				    	JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Student does not have enough available credits.");
	  				    }

						

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	  				
	  			}
	  		});
	  		contentPane.add(addModuleButton);

	  		

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
	  		
	  		JButton removeModuleButton = new JButton("Remove module");
	  		removeModuleButton.setBounds(719, 524, 247, 35);
	  		removeModuleButton.addActionListener(new ActionListener() {
	  			public void actionPerformed(ActionEvent e) {
	  				try {
						int moduleCredit = registrar.getModuleCredits((String) optionalModulesList.getSelectedItem());
  		  				int calculateCredit = registrar.getModuleCredits((String) optionalModulesList.getSelectedItem()) + moduleCredit;
						registrar.deleteModule(student.getEmail(), (String) existingModulesList.getSelectedItem());
						model.setRowCount(0);
						insertStudentsTable(studentUsername, student.getEmail(), model);
						credits.setText(Integer.toString(calculateCredit));
						existingModulesList.removeAllItems();
						existingModulesList.setModel(new DefaultComboBoxModel(registrar.getModulesList(student.getEmail())));
						JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Module Deleted.");
						System.out.println("Deleting " + (String) existingModulesList.getSelectedItem());
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	  				
	  			}
	  		});
	  		contentPane.add(removeModuleButton);

	  		
	  		//-----------
		
	}
	
	
}
