package view.Frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import com.sun.xml.internal.ws.api.Component;

import userclasses.Students;

import javax.swing.JLabel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.UIManager;

public class StudentFrame extends JFrame {
	private static final long serialVersionUID = 2L;

	private JPanel contentPane;

	private JTable modulesTable;
	DefaultTableModel model = new DefaultTableModel();
	JScrollPane scroll;
	String headers[] = { "Module ID", "Initial Grade", "Resit Grade", "Module Name", "Credits Worth", "Department ID" , "Pass Grade"};

	public static Dictionary<String, String> degreeCode = new Hashtable<String, String>();
	 
	 static {
		 degreeCode.put("BUS", "Business School");
	     degreeCode.put("COM", "Computer Science");
	     degreeCode.put("PSY", "Psychology");
	     degreeCode.put("LAN", "Modern Language");
	 }
	 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentFrame frame = new StudentFrame("nameless");
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
	public StudentFrame(String username) throws SQLException {
		
		Students student = getStudent(username);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1035, 647);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("Student Page");
		title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD));
		title.setBounds(413, 21, 224, 71);
		contentPane.add(title);
		
		JLabel registrationLabel = new JLabel("Registration ID:");
		registrationLabel.setBounds(21, 184, 147, 26);
		contentPane.add(registrationLabel);
		
		JLabel surnameLabel = new JLabel("Surname: ");
		surnameLabel.setBounds(21, 149, 98, 26);
		contentPane.add(surnameLabel);
		
		JLabel forenameLabel = new JLabel("Forename: ");
		forenameLabel.setBounds(21, 114, 107, 26);
		contentPane.add(forenameLabel);
		
		JLabel degreeLabel = new JLabel("Degree:");
		degreeLabel.setBounds(21, 218, 92, 26);
		contentPane.add(degreeLabel);
		
		System.out.println("Getting forename");
		JLabel forename = new JLabel(student.getForename());

		forename.setBounds(249, 116, 92, 26);
		contentPane.add(forename);
		
		JLabel surname = new JLabel(student.getSurname());
		surname.setBounds(249, 149, 206, 26);
		contentPane.add(surname);
		
		JLabel registrationId = new JLabel(Integer.toString(student.getRegistrationId()));
		registrationId.setBounds(249, 184, 206, 26);
		contentPane.add(registrationId);
		
		JLabel degree = new JLabel(student.getDegreeId());
		degree.setBounds(249, 218, 206, 26);
		contentPane.add(degree);
		
		JButton modulesList = new JButton("Display Module Grades");
		modulesList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		modulesList.setBounds(743, 110, 245, 35);
		contentPane.add(modulesList);

		forename.setBounds(249, 116, 206, 26);
		contentPane.add(forename);


		
		
	    
	    JButton logoutButton = new JButton("Logout");
	    logoutButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
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
	    logoutButton.setBounds(795, 110, 141, 35);
	    contentPane.add(logoutButton);
	    
 
	    
	
	    
	    modulesTable = new JTable(model);
	    modulesTable.setBounds(75,265,861,290);
	    contentPane.add(modulesTable);
	    modulesTable.setBackground(UIManager.getColor("Button.background"));
	    modulesTable.setEnabled(false);
	    modulesTable.getTableHeader().setReorderingAllowed(false);

	    scroll = new JScrollPane(modulesTable);
	    scroll.setBounds(75,265,861,290);
	    scroll.setBorder(BorderFactory.createEmptyBorder());
	    contentPane.add(scroll);
	    
	    model.setColumnIdentifiers(headers); 
	    insert(username, student.getEmail());
	    
	    contentPane.setVisible(true);
	}

	
	public void insert(String username, String email) throws SQLException {
		Students student = getStudent(username);
		ArrayList<ArrayList<String>> ar = student.displayStudentView(email);

	    for (int i = 0; i < (ar.size()); i++) {
	    	String moduleid = ar.get(i).get(0); //module id
	    	String initGrade = ar.get(i).get(1); //initial grade
	    	String reGrade = ar.get(i).get(2); //resit grade
	    	String modName = ar.get(i).get(3); //module name
	    	String creds = ar.get(i).get(4); //credits worth
	    	String depId = ar.get(i).get(5); //department id
	    	String pass = ar.get(i).get(6); //pass grade
	    	String[] arr = {moduleid, initGrade, reGrade, modName, creds, depId, pass};
		    model.addRow(arr);
	    }	 
	    
	    
	}
	
	public Students getStudent(String username) throws SQLException {
		System.out.println("Executing getting student...");
		Students student = null;
		Connection con = null; 

		String degree = "";
        int credits = 0;
        String difficulty = "";
        java.sql.Date startDate = null;
        java.sql.Date endDate = null;
        String personalTutor = ""; 
  		String title = "";
        String forename = "";
        String surname = "";
        String password = "";

		try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	          System.out.println("Connected to database...");
	          con.setAutoCommit(false);
	          Statement stmt = null;
	          String getStudent = "SELECT * FROM student WHERE username = ?";
	          String getNames = "SELECT * FROM user WHERE username = ?";
	          
	          try (PreparedStatement studentInfo = con.prepareStatement(getStudent);
	        		  PreparedStatement nameInfo = con.prepareStatement(getNames)){
	        	  System.out.println("Executing prepared statements...");
	              studentInfo.setString(1, username);
	              nameInfo.setString(1, username);
	              ResultSet info = studentInfo.executeQuery();
	              ResultSet names = nameInfo.executeQuery();
	              con.commit();
	          
		          while (info.next()) {
				        degree = degreeCode.get(info.getString(5).substring(0,3));
				        difficulty = info.getString(7);
				        startDate = info.getDate(8);
				        endDate = info.getDate(9);
				        personalTutor = info.getString(10);   
		          }
		          
		          while (names.next()) {
		      		title = names.getString(2);
		            forename = names.getString(3);
		            surname = names.getString(4);
		            password = names.getString(6);  
		          }
		          

		          student = new Students(username,title, surname, forename, password, degree, credits, difficulty, startDate, endDate, personalTutor);

			      System.out.println("Student info retrieved!");
	                
	           }
	           catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	            finally {
	                if (stmt != null) stmt.close();
	            }
	        }
	        catch (Exception ex) {
	            ex.printStackTrace();
	        }
	        finally {
	            if (con != null) con.close();
	        }	
		return student;
	}
}


