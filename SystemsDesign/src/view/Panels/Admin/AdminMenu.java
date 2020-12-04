package view.Panels.Admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import academics.CoreModules;
import academics.Degrees;
import academics.Departments;
import academics.Modules;
import userclasses.Admins;
import view.Frames.LoginFrame;
import view.Panels.AbstractPanel;
import view.Panels.Admin.AddDeptPanel;

public class AdminMenu extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1234L;

	private static JTable departTable;
	private static JTable degreeTable;
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
		
		
		//DEFAULT
		UIManager.put("Label.font", LABEL_FONT);
		UIManager.put("Table.font", TABLE_FONT);
		UIManager.put("TableHeader.font", HEADER_FONT);
		UIManager.put("Button.font", TABLE_FONT);
		UIManager.put("ComboBox.font", LABEL_FONT);
		
		JLabel listOf = new JLabel("List of: ");
		listOf.setBounds(35, 73, 54, 20);
		contentPane.add(listOf);
				
		String[] options = {"Departments", "Degrees", "Modules", "Users"};
		
		JLabel adminLabel = new JLabel("Admin Page");
		adminLabel.setBounds(5, 5, 999, 26);
		adminLabel.setFont(adminLabel.getFont().deriveFont(adminLabel.getFont().getStyle() | Font.BOLD));
		adminLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(adminLabel);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(1000, 69, 81, 29);
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
	    
	    JScrollPane allModules_sp = new JScrollPane(allModulesTable());
	    allModules_sp.setBounds(35, 134, 1100, 500); 
	    allModules_sp.setBorder(BorderFactory.createEmptyBorder());
	    
	    JScrollPane coreModules_sp = new JScrollPane(coreModulesTable());
	    coreModules_sp.setBounds(35, 134, 500, 500); 
	    coreModules_sp.setBorder(BorderFactory.createEmptyBorder());

	    
	    JScrollPane user_sp = new JScrollPane(userTable());
	    user_sp.setBounds(35, 134, 700, 500); 
	    user_sp.setBorder(BorderFactory.createEmptyBorder());
	    
	    JButton addOtherDeptButton = new JButton("Add Secondary Department");
		addOtherDeptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddOtherDeptPanel otherDept = new AddOtherDeptPanel(contentPane, mainFrame);
			}
		});
		addOtherDeptButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		addOtherDeptButton.setBounds(730, 69, 220, 29);
		contentPane.add(addOtherDeptButton, BorderLayout.SOUTH);
		
		JButton setCoreModuleButton = new JButton("Set Core Module");
		setCoreModuleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SetCoreModulePanel coreMod = new SetCoreModulePanel(contentPane, mainFrame);
			}
		});
		setCoreModuleButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		setCoreModuleButton.setBounds(730, 69, 200, 29);
		contentPane.add(setCoreModuleButton, BorderLayout.SOUTH);
		
		JButton updatePermButton = new JButton("Change Account Type");
		updatePermButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdatePermissionPanel upPerm = new UpdatePermissionPanel(contentPane, mainFrame);
			}
		});
		updatePermButton.setLayout(new FlowLayout(FlowLayout.LEFT));
		updatePermButton.setBounds(730, 69, 220, 29);
		contentPane.add(updatePermButton, BorderLayout.SOUTH);
	    
		JRadioButton allButton = new JRadioButton("All");
		allButton.setBounds(270, 69, 50, 29);
		contentPane.add(allButton);
		allButton.setVisible(false);
		
		JRadioButton coreButton = new JRadioButton("Core");
		coreButton.setBounds(320, 69, 60, 29);
		contentPane.add(coreButton);
		coreButton.setVisible(false);
		
		ButtonGroup typeGroup = new ButtonGroup();
		typeGroup.add(allButton);
		typeGroup.add(coreButton);
	    
			
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
	            	allModules_sp.setVisible(false);
	            	dept_sp.setVisible(true);
	            	dept_sp.setBorder(BorderFactory.createEmptyBorder());
					contentPane.add(dept_sp);
					setCoreModuleButton.setVisible(false);
	            	updatePermButton.setVisible(false);
	            	addOtherDeptButton.setVisible(false);
	            	allButton.setVisible(false);
	            	coreButton.setVisible(false);
	            	coreModules_sp.setVisible(false);
	            	allModules_sp.setVisible(false);
	            	user_sp.setVisible(false);
					
	            } 
	            else if (selectedOption.equals("Degrees")) {
	            	dept_sp.setVisible(false);
	            	allModules_sp.setVisible(false);
	            	user_sp.setVisible(false);
	            	contentPane.add(degree_sp);
	            	degree_sp.setVisible(true);
	            	setCoreModuleButton.setVisible(false);
	            	updatePermButton.setVisible(false);
	            	addOtherDeptButton.setVisible(true);
	            	allButton.setVisible(false);
	            	coreButton.setVisible(false);
	            	coreModules_sp.setVisible(false);
	            	allModules_sp.setVisible(false);
	            	
	        	}
	            else if (selectedOption.equals("Modules")) {
	            	dept_sp.setVisible(false);
	            	degree_sp.setVisible(false);
	            	user_sp.setVisible(false);
	            	
	            	addOtherDeptButton.setVisible(false);
	            	updatePermButton.setVisible(false);
	            	allButton.setVisible(true);
	            	coreButton.setVisible(true);
	            	setCoreModuleButton.setVisible(true);
	            	
				}
	            else if (selectedOption.equals("Users")) {
	            	dept_sp.setVisible(false);
	            	degree_sp.setVisible(false);
	            	allModules_sp.setVisible(false);
	            	contentPane.add(user_sp);
	            	user_sp.setVisible(true);
	            	addOtherDeptButton.setVisible(false);
	            	setCoreModuleButton.setVisible(false);
	            	updatePermButton.setVisible(true);
	            	allButton.setVisible(false);
	            	coreButton.setVisible(false);
	            	coreModules_sp.setVisible(false);
	            	allModules_sp.setVisible(false);
	            	
	            }
	        }
	    });
	    
	    contentPane.add(mainComboBox);
	    
	    allButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.add(allModules_sp);
				allModules_sp.setVisible(true);
				coreModules_sp.setVisible(false);
			}
		});
			
		coreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.add(coreModules_sp);
				allModules_sp.setVisible(false);
				coreModules_sp.setVisible(true);
			}
		});
	    
	    JButton newButton = new JButton("New");
		newButton.setBounds(450, 69, 63, 29);
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
		removeButton.setBounds(580, 69, 90, 29);
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
			String difficulty = degree.substring(10, degree.indexOf(" "));
			String name = degree.substring(degree.indexOf(" ")+1, degree.indexOf(";"));
			String lastLevel = degree.substring(degree.indexOf(";")+1, degree.indexOf(","));
			String otherDeptId = degree.substring( degree.indexOf(",")+1, degree.length());
			
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
		List<CoreModules> moduleList;
		moduleList = Admins.viewCoreModule();
		int arraySize = moduleList.size();
		String[][] content = new String[arraySize][3];
		for (int i = 0; i<moduleList.size(); i++) {
			String module = moduleList.get(i).toString();
			String[] coreMod = module.split(";");
			for (int j = 0; j < coreMod.length; j++) {
				content[i][j] = coreMod[j];
			}
		}	
		String[] header = {"Module ID", "Degree ID", "Level of Study"};
	    
	    coreModulesTable = new JTable(content, header);
	    coreModulesTable.setEnabled(false);
	    
	    coreModulesTable.getColumnModel().getColumn(0).setPreferredWidth(30);
	    coreModulesTable.getColumnModel().getColumn(1).setPreferredWidth(30);
	    coreModulesTable.getColumnModel().getColumn(2).setPreferredWidth(30);
	    
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
