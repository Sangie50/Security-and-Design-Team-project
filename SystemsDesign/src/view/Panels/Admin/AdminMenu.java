package view.Panels.Admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;

import academics.Degrees;
import academics.Departments;
import academics.Modules;
import userclasses.Admins;
import view.Frames.LoginFrame;
import view.Panels.Admin.AddDeptPanel;

public class AdminMenu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234L;

	private static JTable departTable;
	private static JTable degreeTable;
	private static JTable intDegreeTable;
	private static JTable userTable;
	private static JTable allModulesTable;
	private static JTable coreModulesTable;
	public static JComboBox<String> mainComboBox;
	public static String[][] userContent;
	
	/**
	 * Create the panel.
	 * @throws SQLException 
	 * 
	 */
	
	public AdminMenu(JPanel contentPane, JFrame mainFrame) throws SQLException {
		contentPane.removeAll();
		contentPane.revalidate();
		contentPane.repaint();
		
		JLabel listOf = new JLabel("List of: ");
		listOf.setBounds(35, 73, 54, 20);
		contentPane.add(listOf);
				
		String[] options = {"Departments", "Degrees", "Modules", "Users"};
		
		JLabel welcomeLabel = new JLabel("Welcome Admin!");
		welcomeLabel.setBounds(35, 17, 120, 20);
		contentPane.add(welcomeLabel);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(654, 13, 81, 29);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							mainFrame.setVisible(false);
							LoginFrame frame = new LoginFrame();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	    	
			}
		});
		contentPane.add(logoutButton);
		
	    
	    JScrollPane dept_sp = new JScrollPane(deptTable());
	    dept_sp.setBounds(35, 134, 400, 500);
	    dept_sp.setBorder(BorderFactory.createEmptyBorder());
	    contentPane.add(dept_sp);
	    
		
		JScrollPane degree_sp = new JScrollPane(allDegreesTable());
	    degree_sp.setBounds(35, 134, 1100, 500); 
	    degree_sp.setBorder(BorderFactory.createEmptyBorder());
	    
	    JScrollPane module_sp = new JScrollPane(allModulesTable());
	    module_sp.setBounds(35, 134, 900, 500); 
	    module_sp.setBorder(BorderFactory.createEmptyBorder());
	    
	    JScrollPane user_sp = new JScrollPane(userTable());
	    user_sp.setBounds(35, 134, 500, 500); 
	    user_sp.setBorder(BorderFactory.createEmptyBorder());
	    
	    JButton addOtherDeptButton = new JButton("Add Secondary Department");
		addOtherDeptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddOtherDeptPanel otherDept = new AddOtherDeptPanel(contentPane, mainFrame);
			}
		});
		addOtherDeptButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		addOtherDeptButton.setBounds(35, 495, 220, 29);
		contentPane.add(addOtherDeptButton, BorderLayout.SOUTH);
		
		JButton setCoreModuleButton = new JButton("Set Core Module");
		setCoreModuleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SetCoreModulePanel coreMod = new SetCoreModulePanel(contentPane, mainFrame);
			}
		});
		setCoreModuleButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		setCoreModuleButton.setBounds(35, 495, 200, 29);
		contentPane.add(setCoreModuleButton, BorderLayout.SOUTH);
		
		JButton updatePermButton = new JButton("Change Account Type");
		updatePermButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdatePermissionPanel upPerm = new UpdatePermissionPanel(contentPane, mainFrame);
			}
		});
		updatePermButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		updatePermButton.setBounds(35, 495, 220, 29);
		contentPane.add(updatePermButton, BorderLayout.SOUTH);
	    
	    mainComboBox = new JComboBox<>(options);
	    mainComboBox.setBounds(119, 70, 121, 26);
	    mainComboBox.setSelectedIndex(0);
	    
	    mainComboBox.addActionListener(new ActionListener() {
	    
	    	 
	        @Override
	        public void actionPerformed(ActionEvent event) {
	            JComboBox<String> combo = (JComboBox<String>) event.getSource();
	            String selectedOption = (String) combo.getSelectedItem();
	     
	            if (selectedOption.equals("Departments")) {
	            	degree_sp.setVisible(false);
	            	module_sp.setVisible(false);
	            	dept_sp.setVisible(true);
	            	dept_sp.setBorder(BorderFactory.createEmptyBorder());
					contentPane.add(dept_sp);
					
	            } 
	            else if (selectedOption.equals("Degrees")) {
	            	dept_sp.setVisible(false);
	            	module_sp.setVisible(false);
	            	user_sp.setVisible(false);
	            	contentPane.add(degree_sp);
	            	degree_sp.setVisible(true);
	            	setCoreModuleButton.setVisible(false);
	            	updatePermButton.setVisible(false);
	            	addOtherDeptButton.setVisible(true);
	            	
	        	}
	            else if (selectedOption.equals("Modules")) {
	            	dept_sp.setVisible(false);
	            	degree_sp.setVisible(false);
	            	user_sp.setVisible(false);
	            	contentPane.add(module_sp);
	            	module_sp.setVisible(true);
	            	addOtherDeptButton.setVisible(false);
	            	updatePermButton.setVisible(false);
	            	setCoreModuleButton.setVisible(true);
				}
	            else if (selectedOption.equals("Users")) {
	            	dept_sp.setVisible(false);
	            	degree_sp.setVisible(false);
	            	module_sp.setVisible(false);
	            	contentPane.add(user_sp);
	            	user_sp.setVisible(true);
	            	addOtherDeptButton.setVisible(false);
	            	setCoreModuleButton.setVisible(false);
	            	updatePermButton.setVisible(true);
	            	
	            }
	        }
	    });
	    
	    contentPane.add(mainComboBox);
	    
	    JButton newButton = new JButton("New");
		newButton.setBounds(309, 69, 63, 29);
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
		        String selectedOption = (String) mainComboBox.getSelectedItem();
		        
		        if (selectedOption.equals("Departments")) {
		        	AddDeptPanel addDept = new AddDeptPanel(contentPane, mainFrame);
		        }
		        else if (selectedOption.equals("Degrees")) {
		        	AddDegreePanel addDegree = new AddDegreePanel(contentPane, mainFrame);
		        }
		        else if (selectedOption.equals("Modules")) {
		        	AddModulePanel addModule = new AddModulePanel(contentPane, mainFrame);
		        }
		        else if (selectedOption.equals("Users")) {
		        	AddUserPanel addUser = new AddUserPanel(contentPane, mainFrame);
		        }
			}
		});
		contentPane.add(newButton);
		
		
		JButton removeButton = new JButton("Remove");
		removeButton.setBounds(500, 69, 80, 29);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String selectedOption = (String) mainComboBox.getSelectedItem();
				if (selectedOption.equals("Departments")) {
					RemoveDeptPanel removeDept = new RemoveDeptPanel(contentPane, mainFrame);					
		        }
				else if (selectedOption.equals("Degrees")) {
					RemoveDegreePanel removeDegree = new RemoveDegreePanel(contentPane, mainFrame);					
		        }
				else if (selectedOption.equals("Modules")) {
					RemoveModulePanel removeModule = new RemoveModulePanel(contentPane, mainFrame);					
		        }
				else if (selectedOption.equals("Users")) {
					RemoveUserPanel removeUser = new RemoveUserPanel(contentPane, mainFrame);					
		        }
		        
			    
			}
		});
		
		contentPane.add(removeButton);
	
	}
	
	

	public static JTable deptTable() throws SQLException{
		List<Departments> deptList = Admins.viewDepartment();
		int arraySize = deptList.size();
		String[][] content = new String[arraySize][3];
		for (int i = 0; i<deptList.size(); i++) {
			String dept = deptList.get(i).toString();
			String[] cells = dept.split(";");
			for (int j = 0; j < cells.length; j++) {
				content[i][j] = cells[j];
			}	
		}
		
		String[] header = {"Department Id", "Department Name", "Entry Level"};
	    
	    departTable = new JTable(content, header);
	    departTable.setEnabled(false);
	    departTable.getTableHeader().setReorderingAllowed(false);    	
	    departTable.setBackground(UIManager.getColor("Button.background"));
	    return departTable;
	    
	}
	
	public static JTable allDegreesTable() throws SQLException {
		List<Degrees> degreeList = Admins.viewAllDegrees();
		int array_size = degreeList.size();
		String[][] contentD = null; 
		contentD = new String[array_size][7];
		for (int i = 0; i<degreeList.size(); i++) {
			String degree = degreeList.get(i).toString();
			
			String degreeId = degree.substring(0,6);
			String departId = degree.substring(6,9);
			String entryLevel = degree.substring(9,10);
			String difficulty = degree.substring(10, degree.indexOf("name: "));
			String name = degree.substring(degree.indexOf("name: ")+1, degree.indexOf("level: "));
			String lastLevel = degree.substring(degree.indexOf("level: ")+1, degree.indexOf("otherDept") - 8 );
			String otherDeptId = degree.substring( degree.indexOf("otherDept: "), degree.length());
			
			System.out.println(name);
			System.out.println(lastLevel);
			System.out.println(otherDeptId);
			
			contentD[i][0] = degreeId;
			contentD[i][1] = departId;
			contentD[i][2] = name;
			contentD[i][3] = difficulty;
			contentD[i][4] = entryLevel;
			contentD[i][5] = lastLevel;
			contentD[i][6] = otherDeptId;
		}
		String[] header = {"Degree Id", "Lead Department Id", "Degree Name", "Difficuly", "Entry Level", "Last Level", "Secondary Department ID"};
			 
		degreeTable = new JTable(contentD, header);
	    degreeTable.setEnabled(false);
	    degreeTable.getColumnModel().getColumn(0).setPreferredWidth(25);
	    degreeTable.getColumnModel().getColumn(1).setPreferredWidth(70);
	    degreeTable.getColumnModel().getColumn(2).setPreferredWidth(230);
	    degreeTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	    degreeTable.getColumnModel().getColumn(4).setPreferredWidth(50);
	    degreeTable.getColumnModel().getColumn(5).setPreferredWidth(30);
	    degreeTable.getColumnModel().getColumn(6).setPreferredWidth(110);
	    
	    degreeTable.getTableHeader().setReorderingAllowed(false);    	
	    degreeTable.setBackground(UIManager.getColor("Button.background"));
		return degreeTable;
	}	
	
	public static JTable intDegreesTable() throws SQLException {
		List<Degrees> degreeList = Admins.viewAllDegrees();
		int array_size = degreeList.size();
		String[][] content = new String[array_size][3];
		for (int i = 0; i<degreeList.size(); i++) {
			String module = degreeList.get(i).toString();
			String[] idkk = module.split(";");
			for (int j = 0; j < idkk.length; j++) {
				content[i][j] = idkk[j];
			}
		}	
		String[] header = {"Index", "Degree Id", "Secondary Department Id"};
			 
		intDegreeTable = new JTable(content, header);
		intDegreeTable.setEnabled(false);
		intDegreeTable.getColumnModel().getColumn(0).setPreferredWidth(25);
		intDegreeTable.getColumnModel().getColumn(1).setPreferredWidth(30);
		intDegreeTable.getColumnModel().getColumn(2).setPreferredWidth(180);
	    
		intDegreeTable.getTableHeader().setReorderingAllowed(false);    	
		intDegreeTable.setBackground(UIManager.getColor("Button.background"));
		return intDegreeTable;
	}	
	
	public static JTable allModulesTable() throws SQLException{
		List<Modules> moduleList;
		moduleList = Admins.viewAllModule();
		int arraySize = moduleList.size();
		String[][] content = new String[arraySize][6];
		for (int i = 0; i<moduleList.size(); i++) {
			String module = moduleList.get(i).toString();
			String[] idkk = module.split(";");
			for (int j = 0; j < idkk.length; j++) {
				content[i][j] = idkk[j];
			}
		}	
		
		String[] header = {"Module ID", "Module Name", "isTaught", "Credits","Department Id", "Pass Grade"};
	    
	    allModulesTable = new JTable(content, header);
	    allModulesTable.setEnabled(false);
	    
	    allModulesTable.getColumnModel().getColumn(0).setPreferredWidth(20);
	    allModulesTable.getColumnModel().getColumn(1).setPreferredWidth(200);
	    allModulesTable.getColumnModel().getColumn(2).setPreferredWidth(20);
	    allModulesTable.getColumnModel().getColumn(3).setPreferredWidth(20);
	    allModulesTable.getColumnModel().getColumn(4).setPreferredWidth(20);
	    allModulesTable.getColumnModel().getColumn(5).setPreferredWidth(20);
	    
	    
	    
	    allModulesTable.getTableHeader().setReorderingAllowed(false);    	
	    allModulesTable.setBackground(UIManager.getColor("Button.background"));
	    return allModulesTable;
	}
	
	public static JTable coreModulesTable() throws SQLException{
		List<Modules> moduleList;
		moduleList = Admins.viewCoreModule();
		int arraySize = moduleList.size();
		String[][] content = new String[arraySize][4];
		for (int i = 0; i<moduleList.size(); i++) {
			String module = moduleList.get(i).toString();
			String[] idkk = module.split(";");
			for (int j = 0; j < idkk.length; j++) {
				content[i][j] = idkk[j];
			}
		}	
		
		String[] header = {"Index", "Module ID", "Degree ID", "Level of Study"};
	    
	    coreModulesTable = new JTable(content, header);
	    coreModulesTable.setEnabled(false);
	    
	    coreModulesTable.getColumnModel().getColumn(0).setPreferredWidth(30);
	    coreModulesTable.getColumnModel().getColumn(1).setPreferredWidth(30);
	    coreModulesTable.getColumnModel().getColumn(2).setPreferredWidth(30);
	    coreModulesTable.getColumnModel().getColumn(3).setPreferredWidth(30);
	    
	    
	    
	    coreModulesTable.getTableHeader().setReorderingAllowed(false);    	
	    coreModulesTable.setBackground(UIManager.getColor("Button.background"));
	    return coreModulesTable;
	}
	
	public static JTable userTable() throws SQLException{
		List<String> userList = Admins.viewAllUsers();
		int arraySize = userList.size();
		userContent = new String[arraySize][5];
		for (int i = 0; i<userList.size(); i++) {
			String user = userList.get(i);
			String[] cells = user.split(";");
			for (int j = 0; j < cells.length; j++) {
				userContent[i][j] = cells[j];
			}
		}	
		
		String[] header = {"Username", "Title", "Forename", "Surname", "Account Type"};
	    
	    userTable = new JTable(userContent, header);
	    userTable.setEnabled(false);
	    
	    userTable.getColumnModel().getColumn(0).setPreferredWidth(20);
	    userTable.getColumnModel().getColumn(1).setPreferredWidth(20);
	    userTable.getColumnModel().getColumn(2).setPreferredWidth(50);
	    userTable.getColumnModel().getColumn(3).setPreferredWidth(50);
	    userTable.getColumnModel().getColumn(4).setPreferredWidth(30);
	    
	    
	    userTable.setRowSelectionAllowed(true);
	    userTable.getTableHeader().setReorderingAllowed(false);    	
	    userTable.setBackground(UIManager.getColor("Button.background"));
	    return userTable;
	}
}
