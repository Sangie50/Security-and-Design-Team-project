package view.Panels;

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
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.awt.event.ActionEvent;

public abstract class AbstractPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12345678945L;
	/**
	 * 
	 */
	

	public static String[] headers = { "Module ID", "Initial Grade", "Resit Grade", "Module Name", "Credits Worth", "Department ID" , "Pass Grade"};
	
	public static Dictionary<String, String> degreeCode = new Hashtable<String, String>();
	 
	 static {
		 degreeCode.put("BUS", "Business School");
	     degreeCode.put("COM", "Computer Science");
	     degreeCode.put("PSY", "Psychology");
	     degreeCode.put("LAN", "Modern Language");
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
		          String getInfo = "SELECT degree_id, difficulty, start_date, end_date, personal_tutor, user.title, "
		          		+ "user.forename, user.surname, user.password FROM student LEFT JOIN user ON user.username = student.username WHERE student.username = ?";
		          
		          try (PreparedStatement studentInfo = con.prepareStatement(getInfo)){
		        	  System.out.println("Executing prepared statements...");
		              studentInfo.setString(1, username);
		              ResultSet info = studentInfo.executeQuery();
		              con.commit();
		          
			          while (info.next()) {
					        degree = degreeCode.get(info.getString("degree_id").substring(0,2));
					        difficulty = info.getString("difficulty");
					        startDate = info.getDate("start_date");
					        endDate = info.getDate("end_date");
					        personalTutor = info.getString("personal_tutor"); 
					        title = info.getString("title");
					        forename = info.getString("forename");
					        surname = info.getString("surname");
					        password = info.getString("password");
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
	 
	public Registrars getRegistrar(String username) throws SQLException {
			System.out.println("Executing getting student...");
			Registrars registrar = null;
			Connection con = null; 
			String title = "";
			String surname = "";
			String forename = "";
			String password = "";

			try {
		          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		          System.out.println("Connected to database...");
		          con.setAutoCommit(false);
		          Statement stmt = null;
		          String getNames = "SELECT * FROM user WHERE username = ?";
		          
		          try (PreparedStatement registrarInfo = con.prepareStatement(getNames)){
		        	  System.out.println("Executing prepared statements...");
		              registrarInfo.setString(1, username);
		              ResultSet info = registrarInfo.executeQuery();
		              con.commit();
		          
			          while (info.next()) {
 
			        	  title = info.getString("title");
			        	  surname = info.getString("surname");
			        	  forename = info.getString("forename");
			        	  password = info.getString("password");
			          }
			          


			          registrar = new Registrars(username,title, surname, forename, password);

				      System.out.println("Registrar info retrieved!");
		                
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
			return registrar;
		}
	 
	public void insertStudentsTable(String username, String email, JTable modulesTable, DefaultTableModel model, JPanel contentPane, JScrollPane scroll) throws SQLException {
		modulesTable = new JTable(model);
		contentPane.add(modulesTable);
		modulesTable.setBackground(UIManager.getColor("Button.background"));
		modulesTable.setEnabled(false);
		modulesTable.getTableHeader().setReorderingAllowed(false);
		model.setColumnIdentifiers(headers); 
		   
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
	
	
}