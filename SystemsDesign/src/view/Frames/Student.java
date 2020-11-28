package view.Frames;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import userclasses.Students;

import javax.swing.JLabel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class Student extends JFrame {
	private static final long serialVersionUID = 2L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Student frame = new Student("nameless");
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
	public Student(String username) throws SQLException {
		
		Students student = getStudent(username);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("Student Page");
		title.setBounds(142, 21, 126, 26);
		contentPane.add(title);
		
		JLabel registrationLabel = new JLabel("Registration ID:");
		registrationLabel.setBounds(21, 136, 147, 26);
		contentPane.add(registrationLabel);
		
		JLabel surnameLabel = new JLabel("Surname: ");
		surnameLabel.setBounds(21, 101, 98, 26);
		contentPane.add(surnameLabel);
		
		JLabel forenameLabel = new JLabel("Forename: ");
		forenameLabel.setBounds(21, 66, 107, 26);
		contentPane.add(forenameLabel);
		
		JLabel degreeLabel = new JLabel("Degree:");
		degreeLabel.setBounds(21, 170, 92, 26);
		contentPane.add(degreeLabel);
		
		System.out.println("Getting forename");
		JLabel forename = new JLabel(student.getForename());
		forename.setBounds(249, 68, 92, 26);
		contentPane.add(forename);
		
		JLabel surname = new JLabel(student.getSurname());
		surname.setBounds(249, 101, 92, 26);
		contentPane.add(surname);
		
		JLabel registrationId = new JLabel(Integer.toString(student.getRegistrationId()));
		registrationId.setBounds(249, 136, 92, 26);
		contentPane.add(registrationId);
		
		JLabel degree = new JLabel(student.getDegreeId());
		degree.setBounds(249, 170, 92, 26);
		contentPane.add(degree);
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
