package view.Frames;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import academics.*;
import userclasses.Admins;
import userclasses.Users;
import view.Panels.addDeptPanel;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.UIManager;

public class AdminFrame extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private static JTable departTable;
	private static JTable degreeTable;
	private static JTable userTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public AdminFrame() throws SQLException {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 784, 617);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setLayout(null);
		
		JLabel listOf = new JLabel("List of: ");
		listOf.setBounds(35, 73, 54, 20);
		contentPane.add(listOf);
				
		String[] options = {"Departments", "Degrees", "Modules", "Users"};
		
		JButton newButton = new JButton("New");
		newButton.setBounds(309, 69, 63, 29);
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDeptPanel addDept = new addDeptPanel();
				
			}
		});
		contentPane.add(newButton);
		
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
        dept_sp.setBounds(15, 134, 400, 500);
        dept_sp.setBorder(BorderFactory.createEmptyBorder());
        contentPane.add(dept_sp);
        
    	
    	JScrollPane degree_sp = new JScrollPane(degreeTable());
        degree_sp.setBounds(15, 134, 700, 500); 
        degree_sp.setBorder(BorderFactory.createEmptyBorder());
        
        JScrollPane module_sp = new JScrollPane(moduleTable());
        module_sp.setBounds(15, 134, 900, 500); 
        module_sp.setBorder(BorderFactory.createEmptyBorder());
        
        JScrollPane user_sp = new JScrollPane(userTable());
        user_sp.setBounds(15, 134, 500, 500); 
        user_sp.setBorder(BorderFactory.createEmptyBorder());
        
        
        JComboBox comboBox = new JComboBox(options);
        comboBox.setBounds(119, 70, 121, 26);
        comboBox.setSelectedIndex(0);
        
        comboBox.addActionListener(new ActionListener() {
        
        	 
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
            	}
                else if (selectedOption.equals("Modules")) {
                	dept_sp.setVisible(false);
                	degree_sp.setVisible(false);
                	user_sp.setVisible(false);
                	contentPane.add(module_sp);
                	module_sp.setVisible(true);
				}
                else if (selectedOption.equals("Users")) {
                	dept_sp.setVisible(false);
                	degree_sp.setVisible(false);
                	module_sp.setVisible(false);
                	contentPane.add(user_sp);
                	user_sp.setVisible(true);
                }
            }
        });
        
        contentPane.add(comboBox);
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
	
	public static JTable degreeTable() throws SQLException {
		List<Degrees> degreeList = Admins.viewDegree();
		int array_size = degreeList.size();
		String[][] contentD = null; 
		contentD = new String[array_size][6];
		for (int i = 0; i<degreeList.size(); i++) {
			String degree = degreeList.get(i).toString();
			
			String degreeId = degree.substring(0,6);
			String departId = degree.substring(6,9);
			String entryLevel = degree.substring(9,10);
			String difficulty = degree.substring(10, degree.indexOf(" "));
			String name = degree.substring(degree.indexOf(" ")+1, degree.length()-1);
			String lastLevel = degree.substring(degree.length()-1);
			
			contentD[i][0] = degreeId;
			contentD[i][1] = departId;
			contentD[i][2] = name;
			contentD[i][3] = difficulty;
			contentD[i][4] = entryLevel;
			contentD[i][5] = lastLevel;
		}
		String[] header = {"Degree Id", "Department Id", "Degree Name", "Difficuly", "Entry Level", "Last Level"};
 		 
		degreeTable = new JTable(contentD, header);
        degreeTable.setEnabled(false);
        degreeTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        degreeTable.getColumnModel().getColumn(1).setPreferredWidth(30);
        degreeTable.getColumnModel().getColumn(2).setPreferredWidth(180);
        degreeTable.getColumnModel().getColumn(3).setPreferredWidth(20);
        degreeTable.getColumnModel().getColumn(4).setPreferredWidth(15);
        degreeTable.getColumnModel().getColumn(5).setPreferredWidth(12);
        
        degreeTable.getTableHeader().setReorderingAllowed(false);    	
        degreeTable.setBackground(UIManager.getColor("Button.background"));
		return degreeTable;
	}	
	
	public static JTable moduleTable() throws SQLException{
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
        
        userTable = new JTable(content, header);
        userTable.setEnabled(false);
        
        userTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        userTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        userTable.getColumnModel().getColumn(2).setPreferredWidth(20);
        userTable.getColumnModel().getColumn(3).setPreferredWidth(20);
        userTable.getColumnModel().getColumn(4).setPreferredWidth(20);
        userTable.getColumnModel().getColumn(5).setPreferredWidth(20);
        
        
        
        userTable.getTableHeader().setReorderingAllowed(false);    	
        userTable.setBackground(UIManager.getColor("Button.background"));
        return userTable;
	}
	
	public static JTable userTable() throws SQLException{
		List<String> userList = Admins.viewAllUsers();
		int arraySize = userList.size();
		String[][] content = new String[arraySize][5];
		for (int i = 0; i<userList.size(); i++) {
			String user = userList.get(i);
			String[] cells = user.split(";");
			for (int j = 0; j < cells.length; j++) {
				content[i][j] = cells[j];
			}
		}	
		
		String[] header = {"Username", "Title", "Forename", "Surname", "Account Type"};
        
        userTable = new JTable(content, header);
        userTable.setEnabled(false);
        
        userTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        userTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        userTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        userTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        userTable.getColumnModel().getColumn(4).setPreferredWidth(30);
        
        
        
        userTable.getTableHeader().setReorderingAllowed(false);    	
        userTable.setBackground(UIManager.getColor("Button.background"));
        return userTable;
	}
	
}
