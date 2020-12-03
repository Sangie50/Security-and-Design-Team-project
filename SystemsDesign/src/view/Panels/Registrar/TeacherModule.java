package view.Panels.Registrar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import userclasses.Registrars;
import userclasses.Students;
import userclasses.Teachers;
import view.Panels.AbstractPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionListener;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

public class TeacherModule extends AbstractPanel {
	private JTable table;
	private Teachers teacher;
	private DefaultTableModel model = new DefaultTableModel();
	private JScrollPane scroll;

	/**
	 * Create the panel.
	 * @throws SQLException 
	 */
	public TeacherModule(JPanel contentPane, String teacherUsername, JFrame mainFrame, Registrars registrar) throws SQLException {
		contentPane.removeAll();
		contentPane.revalidate();
		contentPane.repaint();
		
		teacher = getTeacher(teacherUsername);
		
		setLayout(null);
		setBounds(100, 100, 1035, 647);
		
		//label
		JLabel title = new JLabel("Assign teacher to a module");
		title.setBounds(21, 21, 315, 26);
		contentPane.add(title);
		
		JLabel subheading = new JLabel("Teacher Info");
		subheading.setBounds(21, 97, 210, 26);
		contentPane.add(subheading);
		
		JLabel forenameLabel = new JLabel("Forename:");
		forenameLabel.setBounds(21, 147, 142, 26);
		contentPane.add(forenameLabel);
		
		JLabel forename = new JLabel(teacher.getForename());
		forename.setBounds(165, 147, 150, 26);
		contentPane.add(forename);
		
		JLabel surnameLabel = new JLabel("Surname:");
		surnameLabel.setBounds(21, 194, 123, 26);
		contentPane.add(surnameLabel);
		
		JLabel surname = new JLabel(teacher.getSurname());
		surname.setBounds(165, 194, 150, 26);
		contentPane.add(surname);
		
		JLabel employeeLabel = new JLabel("Employee No:");
		employeeLabel.setBounds(21, 241, 142, 26);
		contentPane.add(employeeLabel);
		
		JLabel employeeNo = new JLabel(Integer.toString(teacher.getEmployeeNo()));
		employeeNo.setBounds(165, 241, 92, 26);
		contentPane.add(employeeNo);
		
		JLabel modulesLabel = new JLabel("Modules:");
		modulesLabel.setBounds(21, 376, 92, 26);
		contentPane.add(modulesLabel);
		
		JLabel departmentLabel = new JLabel("Department:");
		departmentLabel.setBounds(21, 288, 123, 26);
		contentPane.add(departmentLabel);
		
		JLabel department = new JLabel(degreeCode.get(teacher.getDepartmentID()));
		department.setBounds(165, 288, 92, 26);
		contentPane.add(department);
		
		//-------------------------------------
		
		//combobox 
		
		JComboBox<String> modules = new JComboBox<>(teacher.getTaughtModules(teacher.getEmployeeNo()));
		modules.setBounds(275, 423, 236, 32);
		contentPane.add(modules);
		
		JComboBox<String> untaughtModules = new JComboBox<>(teacher.getUntaughtModules(teacher.getDepartmentID()));
		untaughtModules.setBounds(21, 423, 236, 32);
		contentPane.add(untaughtModules);
		//-------------------------------------
		
		//buttons
		
		JButton assignModuleButton = new JButton("Assign Module");
		assignModuleButton.setBounds(21, 476, 236, 35);
		contentPane.add(assignModuleButton);
		assignModuleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					registrar.linkModuleToTeacher(teacher.getEmployeeNo(), teacher.getDepartmentID(), (String) untaughtModules.getSelectedItem());
					model.setRowCount(0);
					insert(teacher.getUsername(), model);
					JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Module assigned.");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton unassignModuleButton = new JButton("Un-assign Module");
		unassignModuleButton.setBounds(275, 476, 236, 35);
		contentPane.add(unassignModuleButton);
		unassignModuleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					registrar.unlinkModuleToTeacher(teacher.getEmployeeNo(), (String) modules.getSelectedItem());
					model.setRowCount(0);
					insert(teacher.getUsername(), model);
					modules.removeAllItems();
					modules.setModel(new DefaultComboBoxModel(teacher.getUntaughtModules(teacher.getDepartmentID())));
					
					JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Module removed.");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});		
		
		JButton backButton = new JButton("Back");
		backButton.setBounds(873, 17, 141, 35);
		contentPane.add(backButton);
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
		//---------------------------------------
		
		table = new JTable(model);
		table.setBounds(336, 97, 678, 300);
		table.setBackground(UIManager.getColor("Button.background"));
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);
		contentPane.add(table);
		insert(teacher.getUsername(), model);
		
		scroll = new JScrollPane(table);
		scroll.setBounds(336, 97, 678, 300);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		contentPane.add(scroll);

	}
	
	public void insert(String username, DefaultTableModel model) throws SQLException {
		String[] headers = { "Module ID", "Module name", "Department ID"};

		model.setColumnIdentifiers(headers); 
		ArrayList<ArrayList<String>> ar = teacher.displayAllModules(username);
	    for (int i = 0; i < (ar.size()); i++) {
	    	String moduleid = ar.get(i).get(0); //module id
	    	String modName = ar.get(i).get(1); //initial grade
	    	String depId = ar.get(i).get(2); 
	    	
	    	String[] arr = {moduleid, modName, depId};
	    	model.addRow(arr);

	    }
	}
}
