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

import academics.Grades;
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
		optionalModulesLabel.setBounds(10, 424, 196, 26);
		contentPane.add(optionalModulesLabel);

		JLabel creditsAvailable = new JLabel("Credits available:");
		creditsAvailable.setBounds(719, 119, 206, 26);
		contentPane.add(creditsAvailable);

		int remainingCredits = student.getTotalCredits() - registrar.checkCredits(student.getEmail()); 
		JLabel credits = new JLabel(Integer.toString(remainingCredits));
		if (remainingCredits < 0) credits.setForeground(Color.red);
		credits.setBounds(719, 154, 92, 26);
		contentPane.add(credits);
		
		JLabel removeModuleLabel = new JLabel("Remove module:");
		removeModuleLabel.setBounds(719, 424, 176, 26);
		contentPane.add(removeModuleLabel);
		
		JLabel addModuleLabel = new JLabel("Add module:");
		addModuleLabel.setBounds(365, 424, 176, 26);
		contentPane.add(addModuleLabel);
		//-----------------------------
		
			
		//Combo box
		JComboBox<String> addModulesList = new JComboBox<>(
				registrar.getAddModulesList(student.getDepartmentId()));
		addModulesList.setBounds(365, 471, 247, 32);
		contentPane.add(addModulesList);
		
		JComboBox<String> optionalModulesList = new JComboBox<>(
				registrar.getOptionalModulesList(student.getEmail()));
		optionalModulesList.setBounds(10, 471, 247, 32);
		contentPane.add(optionalModulesList);
		
		JComboBox<String> existingModulesList = new JComboBox<>(
				registrar.getModulesList(student.getEmail()));
		existingModulesList.setBounds(719, 471, 247, 32);
		contentPane.add(existingModulesList);
		//--------------
		
		//table	
	

		modulesTable = new JTable(model);
		modulesTable.setBackground(UIManager.getColor("Button.background"));
		modulesTable.setEnabled(false);
		modulesTable.getTableHeader().setReorderingAllowed(false);
		contentPane.add(modulesTable);
		insertStudentsTable(studentUsername, student.getEmail(), model);
		scroll = new JScrollPane(modulesTable);
		scroll.setBounds(10, 120, 700, 300);
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


	  		JButton addOptModuleButton = new JButton("Add module");
	  		addOptModuleButton.setBounds(10, 524, 247, 35);
	  		addOptModuleButton.addActionListener(new ActionListener() {
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
	  		contentPane.add(addOptModuleButton);

	  		
	  		JButton addModuleButton = new JButton("Add module");
	  		addModuleButton.setBounds(365, 524, 247, 35);
	  		addModuleButton.addActionListener(new ActionListener() {
	  			public void actionPerformed(ActionEvent e) {
	  				try {
	  					
	  					int moduleCredit = registrar.getModuleCredits((String) addModulesList.getSelectedItem());
	  				    int calculateCredit = student.getTotalCredits() - registrar.getModuleCredits((String) addModulesList.getSelectedItem()) - moduleCredit;
	  				    if (calculateCredit >=  0) {
	  				    	registrar.linkModuleToStudent(student.getEmail(), (String) addModulesList.getSelectedItem());
							model.setRowCount(0);
							insertStudentsTable(studentUsername, student.getEmail(), model);
							credits.setText(Integer.toString(student.getTotalCredits() - registrar.getModuleCredits((String) addModulesList.getSelectedItem()) - moduleCredit));
							addModulesList.removeAllItems();
							addModulesList.setModel(new DefaultComboBoxModel<>(registrar.getAddModulesList(student.getDepartmentId())));
							existingModulesList.removeAllItems();
							existingModulesList.setModel(new DefaultComboBoxModel<>(registrar.getModulesList(student.getEmail())));
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
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	  				
	  			}
	  		});
	  		contentPane.add(removeModuleButton);

	  		contentPane.setVisible(true);
	  		//-----------
		
	}
	
	public void insertStudentsTable(String username, String email, DefaultTableModel model) throws SQLException {
		String[] headers = { "Module ID", "Initial Grade", "Resit Grade", "Module Name", "Credits Worth", "Department ID" , "Pass Grade"};

		Students student = getStudent(username);
		model.setColumnIdentifiers(headers); 
		ArrayList<ArrayList<String>> ar = student.displayStudentView(student.getEmail());
	    for (int i = 0; i < (ar.size()); i++) {
	    	String moduleid = ar.get(i).get(0); //module id
	    	String initGrade = ar.get(i).get(1); //initial grade
	    	String reGrade = ar.get(i).get(2); //resit grade
	    	String modName = ar.get(i).get(3); //module name
	    	String creds = ar.get(i).get(4); //credits worth
	    	String depId = ar.get(i).get(5); //department id
	    	String pass = ar.get(i).get(6); //pass grade
	    	String[] arr = {moduleid, modName, initGrade, reGrade, creds, depId, pass};
	    	model.addRow(arr);

	    }
	}
	
	
}
