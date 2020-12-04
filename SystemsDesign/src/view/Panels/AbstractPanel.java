package view.Panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import userclasses.Admins;
import userclasses.Registrars;
import userclasses.Students;
import userclasses.Teachers;
import userclasses.Users;
import userclasses.Users.UserTypes;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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
	
	protected static final Rectangle PANEL_SIZE = new Rectangle(100, 100, 1035, 647);
	protected static final Color BLUE = new java.awt.Color(102, 153, 255);

	protected static final Font BORDER_FONT = new Font("Yu Gothic", Font.BOLD, 13);
	protected static final Font LABEL_FONT = new Font("Yu Gothic", Font.PLAIN, 15);
	protected static final Font ERROR_FONT = new Font("Yu Gothic", Font.PLAIN, 13);
	protected static final Font COMBO_FONT = new Font("Yu Gothic", Font.PLAIN, 12);
	protected static final Font TITLE_FONT = new Font("Yu Gothic", Font.BOLD, 18);
	protected static final Font HEADER_FONT = new Font("Yu Gothic", Font.BOLD, 13);
	protected static final Font TABLE_FONT = new Font("Yu Gothic", Font.PLAIN, 12);
	protected static final Font BUTTON_FONT = new Font("Yu Gothic", Font.BOLD, 13);

//	private static final Font LABEL_FONT = new Font("Yu Gothic", Font.PLAIN, 18);
//	private static final Font ERROR_FONT = new Font("Yu Gothic", Font.PLAIN, 13);
	/**
	 * 
	 */
	

	
	public static Dictionary<String, String> degreeCode = new Hashtable<String, String>();
	 
	 static {
		 degreeCode.put("BUS", "Business School");
	     degreeCode.put("COM", "Computer Science");
	     degreeCode.put("PSY", "Psychology");
	     degreeCode.put("LAN", "Modern Language");
	 }
	

	public Users getUser(String username) throws SQLException {
		System.out.println("Executing getting user...");
		Users user = null;
		Connection con = null; 
		String title = "";
		String surname = "";
		String forename = "";
		String accountType = "";
		String password = "";
		

		try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	          System.out.println("Connected to database...");
	          con.setAutoCommit(false);
	          Statement stmt = null;
	          String getInfo = "SELECT * FROM user WHERE username = ?";
	          
	          try (PreparedStatement studentInfo = con.prepareStatement(getInfo)){
	              studentInfo.setString(1, username);
	              ResultSet info = studentInfo.executeQuery();
	              con.commit();
	          
		          while (info.next()) {
				        title = info.getString("title");
				        surname = info.getString("surname");
				        forename = info.getString("forename");
				        accountType = info.getString("account_type");
				        password = info.getString("password");
		          }
		          user = new Users(username,title, surname, forename, password);
		          String updateType = "";
		          if (accountType.equals(UserTypes.STUDENT.toString())) {
			          updateType = UserTypes.STUDENT.toString();
		          }
		          else if (accountType.equals(UserTypes.TEACHER.toString())) {
		        	  updateType = UserTypes.TEACHER.toString();
		          }
		          else if (accountType.equals(UserTypes.REGISTRAR.toString())) {
		        	  updateType = UserTypes.REGISTRAR.toString();
		          }
		          else if (accountType.equals(UserTypes.ADMIN.toString())){
		        	  updateType = UserTypes.ADMIN.toString();
		          }
		          else if (accountType.equals(UserTypes.UNASSIGNED.toString())){
		        	  updateType = UserTypes.UNASSIGNED.toString();
		          }
		          user.setAccountType(updateType);
			      System.out.println("User accountType: " + accountType);
	                
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
		return user;
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
		          String getInfo = "SELECT degree_id, difficulty, start_date, end_date, personal_tutor, total_credits, user.title, "
		          		+ "user.forename, user.surname, user.password FROM student LEFT JOIN user ON user.username = student.username WHERE student.username = ?";
		          
		          try (PreparedStatement studentInfo = con.prepareStatement(getInfo)){
		        	  System.out.println("Executing prepared statements...");
		              studentInfo.setString(1, username);
		              ResultSet info = studentInfo.executeQuery();
		              con.commit();
		          
			          while (info.next()) {
					        degree = info.getString("degree_id").substring(0,3);
					        credits = info.getInt("total_credits");
					        difficulty = info.getString("difficulty");
					        startDate = info.getDate("start_date");
					        endDate = info.getDate("end_date");
					        personalTutor = info.getString("personal_tutor"); 
					        title = info.getString("title");
					        forename = info.getString("forename");
					        surname = info.getString("surname");
					        password = info.getString("password");
			          }

			          student = new Students(username, title, surname, forename, password, degree, credits, difficulty, startDate, endDate, personalTutor);

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
	 
	public Teachers getTeacher(String username) throws SQLException {
		  System.out.println("Executing getting teacher...");
		  Teachers teacher = null;
		  Connection con = null; 

		  String title = "";
		  String surname = "";
		  String forename = "";
		  String password = "";
	      String departmentId = "";
	
			try {
		          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		          System.out.println("Connected to database...");
		          con.setAutoCommit(false);
		          Statement stmt = null;
		          String getInfo = "SELECT employee_no, department_id, user.title, user.surname, "
		          		+ "user.forename, user.password FROM teacher LEFT JOIN user ON "
		          		+ "user.username = teacher.username WHERE teacher.username = ?";
		          
		          try (PreparedStatement teacherInfo = con.prepareStatement(getInfo)){
		        	  System.out.println("Executing prepared statements...");
		              teacherInfo.setString(1, username);
		              ResultSet info = teacherInfo.executeQuery();
		              con.commit();
		          
			          while (info.next()) {
					        departmentId = info.getString("department_id");	
					        title = info.getString("title");
					        surname = info.getString("surname");
					        forename = info.getString("forename");
					        password = info.getString("password");
			          }
	
			          teacher = new Teachers(username, title, surname, forename, password, departmentId);
	
				      System.out.println("Teacher info retrieved!");
		                
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
			return teacher;
		}
	
	
	
	
	public String[] getUsernames(String type) throws SQLException{
		ArrayList<String> list = new ArrayList<String>();
		Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT username FROM user WHERE account_type = ?";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
            	getUsernames.setString(1, type);
                ResultSet usernameList = getUsernames.executeQuery();
                con.commit();
                
                while (usernameList.next()) {
                	list.add(usernameList.getString("username"));	
                }
                
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
        String[] arr = new String[list.size()];
        arr = list.toArray(arr);
        return arr;
	
	}

	public String[] getAllUsernames() throws SQLException{
		ArrayList<String> list = new ArrayList<String>();
		Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT username FROM user";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
                ResultSet usernameList = getUsernames.executeQuery();
                con.commit();
                
                while (usernameList.next()) {
                	list.add(usernameList.getString("username"));	
                }
                
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
        String[] arr = new String[list.size()];
        arr = list.toArray(arr);
        return arr;
	
	}
	
	public Date convertDate(String day, String month, String year) {
		DecimalFormat formatter = new DecimalFormat("00");
		String dd = year + "-" + formatter.format(Integer.parseInt(month)) + "-" + formatter.format(Integer.parseInt(day));
		System.out.println(dd);
		Date date = Date.valueOf(dd);

		return date;
	}
	

	public boolean isType(String username, String accType) throws SQLException {
		Connection con = null;
		String userType = "";
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String type = "SELECT account_type FROM user WHERE username = ?";
            try (PreparedStatement getType = con.prepareStatement(type)){
                getType.setString(1, username);
            	ResultSet rs = getType.executeQuery();
                con.commit();
                
                while (rs.next()) {
                	userType = rs.getString("account_type");
                }
                
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

		return accType.equals(userType);
	}
	
	public String[] getModuleId() throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT module_id FROM module";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
                ResultSet usernameList = getUsernames.executeQuery();
                con.commit();
                
                while (usernameList.next()) {
                	list.add(usernameList.getString("module_id"));	
                }
                
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
        String[] moduleId = new String[list.size()];
        moduleId = list.toArray(moduleId);
        return moduleId;
	
	}
	
	public String[] getDegreeId() throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		Connection con = null;
		
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            
            String degreeId = "SELECT degree_id FROM degree";
            try (PreparedStatement getDegreeId = con.prepareStatement(degreeId)){
                ResultSet degreeIdList = getDegreeId.executeQuery();
                con.commit();
                
                while (degreeIdList.next()) {
                	list.add(degreeIdList.getString("degree_id"));	
                }
                
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

        String[] degreeIdArray = new String[list.size()];
        degreeIdArray = list.toArray(degreeIdArray);
        return degreeIdArray;
	
	}
	
	public String[] getDeptId() throws SQLException {
		ArrayList<String> list = new ArrayList<String>();
		Connection con = null;
		
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            
            String deptId = "SELECT department_id FROM department";
            try (PreparedStatement getDeptId = con.prepareStatement(deptId)){
                ResultSet deptIdList = getDeptId.executeQuery();
                con.commit();
                
                while (deptIdList.next()) {
                	list.add(deptIdList.getString("department_id"));	
                }
                
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

        String[] deptIdArray = new String[list.size()];
        deptIdArray = list.toArray(deptIdArray);
        return deptIdArray;
	
	}
	
	public String getLevelOFStudyFromModuleId(String moduleId) throws SQLException {
		String levelOfStudy = null;
		Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String los = "SELECT level_of_study FROM module_grade WHERE module_id = ?";
            try (PreparedStatement getLos = con.prepareStatement(los)){
            	getLos.setString(1,  moduleId);
                ResultSet rs = getLos.executeQuery();
                con.commit();
                
                while (rs.next()) {
                	levelOfStudy = rs.getString("level_of_study");	
                }
                
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
        
        return levelOfStudy;
	
	}
	
}

