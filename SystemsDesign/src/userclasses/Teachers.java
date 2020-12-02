package userclasses;
import features.PasswordGen;
import java.sql.*;
import java.util.*;
//Teacher class

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
public class Teachers{
    
    Integer employeeNo;
    String departmentID;
    /*
    public Teachers(String username, String title, String surname, String forename, String password) throws SQLException {
        super(username, title, surname, forename, password); 
    }
    */
    public Integer getEmployeeNo() {
        return employeeNo;
    }
    public String getDepartmentID() {
        return departmentID;
    }
    
    public void addTeachers (String username, String title, String surname, String forename, String password, Integer employeeNo, String departmentID) throws SQLException {
    	this.employeeNo = employeeNo;
    	this.departmentID = departmentID;
        String salt = PasswordGen.getSalt(30);
        String accountType = UserTypes.UNASSIGNED.toString();
        password = PasswordGen.generateSecurePassword(password, salt);
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String addUserStmt = "INSERT INTO user VALUES (?,?,?,?,?,?)";
            String addTeacherStmt = "INSERT INTO teacher VALUES (?, ?, ?)";
            // Assumes checking that username doesn't exist beforehand
            try (PreparedStatement addUStmt = con.prepareStatement(addUserStmt); PreparedStatement addTStmt = con.prepareStatement(addTeacherStmt)){
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
    
    public static void updateGrades(String moduleID, String email, Integer overallMark) throws SQLException {
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
    
    public static void addResitGrades(String moduleID, String email, Integer resitMark) throws SQLException {
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
    
}