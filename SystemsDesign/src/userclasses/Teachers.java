package userclasses;
import features.PasswordGen;
import java.sql.*;
import java.util.*;
//Teacher class

import academics.Grades;
import userclasses.Users.*;

/*
Methods:
[X]Add Grades - May need to add where clause on for teachers to stop lots of changes
[X]Update grades 
[X]Resit grades
[G]Calculate the weighted mean grade of the module Might need to be done in the degree
[G]Decide whether student passes this module?
[G]Take weighted average of all years to give a final score
[G]Calculate classification from the module
[GUI]View status of student - Returns if they passed or failed the year. I really don't know what this promopt wanted from me.
[GUI]Show outcome what student has achieved each period and level of study 
[GUI]Show outcome of whole degree

Unique Attributes: 
Employee Number
Department ID
*/
public class Teachers extends Users{
    
    Integer employeeNo;
    String departmentId;
    
    public Teachers(String username, String title, String surname, String forename, String password, String departmentId) throws SQLException {
        super(username, title, surname, forename, password); 
        this.password = password;
        this.departmentId = departmentId;

        addTeachers(username, departmentId);
    }
    
    public int getEmployeeNo() throws SQLException {
    	 Connection con = null;
         try {
             con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
             con.setAutoCommit(false);
             Statement stmt = null;
             String empNo = "SELECT employee_no FROM teacher WHERE username = ?";
             // checking that doesn't exist beforehand
             try (PreparedStatement getEmpNo = con.prepareStatement(empNo)){
             	getEmpNo.setString(1, this.username);
             	ResultSet employeeNo = getEmpNo.executeQuery();
             	con.commit();
             	
             	while (employeeNo.next()) {
             		this.employeeNo = employeeNo.getInt("employee_no");
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
        return employeeNo;
    }
    
    public String getDepartmentID() {
        return departmentId;
    }
    
    public void addTeachers (String username,String departmentId) throws SQLException {
        String salt = PasswordGen.getSalt(30);
        String accountType = UserTypes.TEACHER.toString();
        password = PasswordGen.generateSecurePassword(password, salt);
        		
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String checkExisting = "SELECT username FROM teacher WHERE username = ?";

            String addUserStmt = "INSERT INTO user VALUES (?,?,?,?,?,?)";
            String addTeacherStmt = "INSERT INTO teacher(username, department_id) VALUES (?, ?, ?)";
            // checking that username doesn't exist beforehand
            try (PreparedStatement addUStmt = con.prepareStatement(addUserStmt); 
            		PreparedStatement addTStmt = con.prepareStatement(addTeacherStmt);
            		PreparedStatement checkStmt = con.prepareStatement(checkExisting)){
            	checkStmt.setString(1, username);
            	ResultSet existingUsers = checkStmt.executeQuery();
            	con.commit();
            	
            	while (existingUsers.next()) {
            		if (username.contentEquals(existingUsers.getString("username"))) {
            			System.err.println("Teacher/username already exists.");
            		}
            		else {
            			addUStmt.setString(1, username);
                        addUStmt.setString(2, title);
                        addUStmt.setString(3, surname);
                        addUStmt.setString(4, forename);
                        addUStmt.setString(5, accountType);
                        addUStmt.setString(6, password);
                        addUStmt.setString(7, salt);
                        addUStmt.executeUpdate();
                        con.commit();
                        //Set variables for adding to teacher
                        addTStmt.setString(1, username);
                        addTStmt.setString(2, departmentId);
                        addTStmt.executeUpdate();
                        con.commit();
            		}
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
    }
    
    public static void addJustTeachers (String username, Integer employeeNo, String departmentID) throws SQLException {
    	
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String addTeacherStmt = "INSERT INTO teacher VALUES (?, ?, ?)";
            // Assumes checking that username doesn't exist beforehand
            try (PreparedStatement addTStmt = con.prepareStatement(addTeacherStmt)){
                
                con.commit();
                //Set variables for adding to teacher
                addTStmt.setString(1, username);
                addTStmt.setInt(2, employeeNo);
                addTStmt.setString(3, departmentID);
                addTStmt.executeUpdate();
                con.commit();
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
    }
    
    
    public static void addGrade(String moduleID, String email, Integer overallMark) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "INSERT INTO module_grade (module_id, email, initial_grade) VALUES (?,?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                updateStmt.setString(1, moduleID);
                updateStmt.setString(2, email);
                updateStmt.setInt(3, overallMark);
                updateStmt.executeUpdate();
                con.commit();
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
    }
    
    //Nothing in for now
    public Integer weightedMean(String moduleID, String email) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            Statement stmt = null;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            if (con != null) con.close();
        }
        return null; //Change later
    }
    
    //Nothing in for now 
    public String classification(String moduleID, String email) throws Exception {
        try {
            Integer mean = weightedMean(moduleID, email);
            // Do the classification, return answer 
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null; // for now
    }
    
    public Boolean viewStudentStatus(String email) throws SQLException {
        Boolean status = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT progress_to_next_level FROM student AS s, year_grade AS yg USING (email) WHERE s.email = ?";
            try (PreparedStatement selectStmt = con.prepareStatement(preparedStmt)){
                selectStmt.setString(1, email);
                con.commit();
                ResultSet passorfail = selectStmt.executeQuery();
                status = passorfail.getBoolean(1);
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
        return status; 
    }
    
    public void updateGrades(String moduleID, String email, Integer overallMark) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "UPDATE module_grade SET initial_grade = ? WHERE module_id = ? AND email = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setInt(1, overallMark);
                updateStmt.setString(2, moduleID);
                updateStmt.setString(3, email);
                updateStmt.executeUpdate();
                con.commit();
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
    }
    
    public void addResitGrades(String moduleID, String email, Integer resitMark) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "UPDATE module_grade SET resit_grade = ? WHERE module_id = ? AND email = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setInt(1, resitMark);
                updateStmt.setString(2, moduleID);
                updateStmt.setString(3, email);
                updateStmt.executeUpdate();
                con.commit();
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
    }
    
    public static void deleteGrades(String moduleID, String email) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "DELETE FROM module_grade WHERE module_id = ? AND email = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setString(1, moduleID);
                updateStmt.setString(2, email);
                updateStmt.executeUpdate();
                con.commit();
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
    }

    
    public String[] getTaughtModules (int employeeNo) throws SQLException {
    	Connection con = null; 
		 ArrayList<String> m = new ArrayList<String>();
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String modules = "SELECT module_id FROM module_teacher WHERE employee_no = ?";
	            try (PreparedStatement modulesList = con.prepareStatement(modules)){
	                modulesList.setInt(1, employeeNo);
	                ResultSet modulesSet = modulesList.executeQuery();
               	while (modulesSet.next()) {
               		m.add(modulesSet.getString("module_id"));
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
		 String[] ar = new String[m.size()];
		 for (int i = 0; i < m.size(); i++) {
			 ar[i] = m.get(i);
			 System.out.println(m.get(i));
		 }
		 return ar;
    }
    
    public String[] getUntaughtModules (String depId) throws SQLException {
    	Connection con = null; 
		 ArrayList<String> m = new ArrayList<String>();
		 try {
	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
	            con.setAutoCommit(false);
	            Statement stmt = null;
	            String modules = "SELECT module_id FROM module WHERE department_id = ?";
	            try (PreparedStatement modulesList = con.prepareStatement(modules)){
	                modulesList.setString(1, depId);
	                ResultSet modulesSet = modulesList.executeQuery();
              	while (modulesSet.next()) {
              		m.add(modulesSet.getString("module_id"));
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
		 String[] ar = new String[m.size()];
		 for (int i = 0; i < m.size(); i++) {
			 ar[i] = m.get(i);
			 System.out.println(m.get(i));
		 }
		 return ar;
    }
    
    public ArrayList<ArrayList<String>> displayAllModules(String username) throws SQLException {
    	 ArrayList<ArrayList<String>> list= new ArrayList<ArrayList<String>>();

   	  Connection con = null; 
   		 try {
   	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
   		      con.setAutoCommit(false);
   		      Statement stmt = null;
   		      String studentViewTable ="SELECT module_teacher.module_id, module_teacher.department_id, module.module_name"
   		      		+ " FROM module_teacher LEFT JOIN (module, teacher) ON (module_teacher.module_id = module.module_id "
   		      		+ " AND  teacher.employee_no = module_teacher.employee_no) WHERE teacher.username = ?";
   		      
   		      ResultSet rs;

   		      try (PreparedStatement pstmt = con.prepareStatement(studentViewTable)){
   		    	  	pstmt.setString(1, username);
   				    rs = pstmt.executeQuery();  
   				    // Get the result table from the query  3 
   					
   		      	 	while (rs.next()) {
   		      		    ArrayList<String> row = new ArrayList<String>();
   		      	 		row.add(rs.getString("module_id"));				//module id
   		      	 		row.add(rs.getString("module_name"));	   		//module name
   		      	 		row.add(rs.getString("department_id"));			//department id	   
   		      	 		list.add(row);
   		 
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
   		 System.out.println(list);
   	  return list;
    }
    
    public ArrayList<ArrayList<String>> displayByLevelOfStudy(String email, String levelOfStudy) throws SQLException {
   	 ArrayList<ArrayList<String>> list= new ArrayList<ArrayList<String>>();

  	  Connection con = null; 
  		 try {
  	          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
  		      con.setAutoCommit(false);
  		      Statement stmt = null;
  		      String studentViewTable ="SELECT module.module_id, module_name, initial_grade, resit_grade"
  		      		+ " FROM module_grade LEFT JOIN module ON module_grade.module_id = module.module_id"
  		      		+ " WHERE email = ? AND level_of_study = ? ";
  		      
  		      ResultSet rs;

  		      try (PreparedStatement pstmt = con.prepareStatement(studentViewTable)){
  		    	  	pstmt.setString(1, email);
  		    	  	pstmt.setString(2, levelOfStudy);
  				    rs = pstmt.executeQuery();  
  				    // Get the result table from the query  3 
  					
  		      	 	while (rs.next()) {
  		      		    ArrayList<String> row = new ArrayList<String>();
  		      	 		row.add(rs.getString("module_id"));				//module id
  		      	 		row.add(rs.getString("module_name"));	   		//module name
  		      	 		row.add(rs.getString("department_id"));			//department id	   
  		      	 		list.add(row);
  		 
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
  		 System.out.println(list);
  	  return list;
   }

 // Gets emails of students that this teacher teaches only
    public String[] getEmails(String username) throws SQLException{
        ArrayList<String> list = new ArrayList<String>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT DISTINCT student.email FROM student INNER JOIN "
                    + "module_grade ON student.email = module_grade.email INNER"
                    + " JOIN module ON module_grade.module_id = module.module_id"
                    + " INNER JOIN module_teacher ON module.module_id = "
                    + "module_teacher.module_id INNER JOIN teacher ON "
                    + "module_teacher.employee_no = teacher.employee_no WHERE"
                    + " teacher.username = ?";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
                getUsernames.setString(1, username);
                ResultSet usernameList = getUsernames.executeQuery();
                con.commit();
                
                while (usernameList.next()) {
                	list.add(usernameList.getString("email"));
                        
                }
                usernameList.close();
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
    
    public String[] getModules(String studentEmail, String teacherUsername) throws SQLException{
        ArrayList<String> list = new ArrayList<String>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT module_grade.module_id FROM student LEFT JOIN "
                    + "(module_grade, module_teacher, teacher) ON (student.email = module_grade.email "
                    + "AND module_teacher.module_id = module_grade.module_id AND "
                    + "module_teacher.employee_no = teacher.employee_no) WHERE "
                    + "teacher.username = ? AND student.email = ?";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
                getUsernames.setString(1, teacherUsername);
                getUsernames.setString(2, studentEmail);
                ResultSet usernameList = getUsernames.executeQuery();
                con.commit();
                
                while (usernameList.next()) {
                	list.add(usernameList.getString("module_id"));	
                }
                System.out.println(list);
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
    
    public ArrayList<String> getGrades(String studentEmail, String moduleID) throws SQLException{
        ArrayList<String> list = new ArrayList<String>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT * FROM student INNER JOIN "
                    + "module_grade ON student.email = module_grade.email INNER"
                    + " JOIN module ON module_grade.module_id = module.module_id"
                    + " WHERE module.module_id = ? AND student.email = ?";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
                getUsernames.setString(1, moduleID);
                getUsernames.setString(2, studentEmail);
                ResultSet usernameList = getUsernames.executeQuery();
                con.commit();
                
                while(usernameList.next()) {
                    list.add(Integer.toString(usernameList.getInt("initial_grade")));
                    list.add(Integer.toString(usernameList.getInt("resit_grade")));
                    list.add(usernameList.getString("module_name"));
                }
                
                System.out.println(list);
          
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
        return list;
	
    }
    
    public Boolean getPassYear(String studentEmail) throws SQLException{
        String level = Grades.getLastLevelOfStudy(studentEmail);
        Boolean passYear = Grades.yearPassed(studentEmail, level);
        return passYear;
    }
}
