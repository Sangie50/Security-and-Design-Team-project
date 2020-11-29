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

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;



public class StudentFrame extends JFrame {
	private static final long serialVersionUID = 2L;

	private JPanel contentPane;
	private JTable modulesTable;
	DefaultTableModel model = new DefaultTableModel();
	JScrollPane scroll;
	String headers[] = { "Module ID", "Initial Grade", "Resit Grade", "Module Name", "Credits Worth", "Department ID" , "Pass Grade"};

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
		surname.setBounds(249, 149, 92, 26);
		contentPane.add(surname);
		
		JLabel registrationId = new JLabel(Integer.toString(student.getRegistrationId()));
		registrationId.setBounds(249, 184, 92, 26);
		contentPane.add(registrationId);
		
		JLabel degree = new JLabel(student.getDegreeId());
		degree.setBounds(249, 218, 92, 26);
		contentPane.add(degree);
		
		JPanel panel = new JPanel();
		panel.setBounds(21, 248, 967, 307);
		contentPane.add(panel);
		
		modulesTable = new JTable();
		panel.add(modulesTable);
		modulesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		model.setColumnIdentifiers(headers);   
		modulesTable.setModel(model);
	    scroll = new JScrollPane(modulesTable);
	    
	    insert(username, student.getEmail());
	    
	    panel.add(scroll, BorderLayout.CENTER);
	    
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
	    panel.setVisible(true);
	}

	
	public void insert(String username, String email) throws SQLException {
		Students student = getStudent(username);
		ArrayList<ArrayList<String>> ar = student.displayStudentView(email);
	    System.out.println(ar.size());
	    for (int i = 0; i < (ar.size()); i++) {
		        model.addRow(new Object[] { String.valueOf(ar.get(i).get(0)),String.valueOf(ar.get(i).get(1)),String.valueOf(ar.get(i).get(2)),
		        		String.valueOf(ar.get(i).get(3)),String.valueOf(ar.get(i).get(4)),String.valueOf(ar.get(i).get(5)),String.valueOf(ar.get(i).get(6))});
	    }	    
	}
	
	public Students getStudent(String username) throws SQLException {
		System.out.println("Executing getting student...");
		Students student = null;
		Connection con = null; 
		int registrationId = 0;
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
				        registrationId = info.getInt(3);
				        degree = info.getString(5);
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
		          
		          student = new Students(username,title, surname, forename, password, registrationId, degree, credits,difficulty, startDate, endDate, personalTutor);
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
