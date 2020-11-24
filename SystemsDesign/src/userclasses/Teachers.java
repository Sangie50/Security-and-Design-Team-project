package userclasses;
import java.sql.*;
import java.util.*;
//Teacher class

import userclasses.Users.UserTypes;

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
    String departmentID;

    public Teachers(String username, String title, String surname, String forename, String password) throws SQLException {
        super(username, title, surname, forename, password);
    }
    
    public Integer getEmployeeNo() {
        return employeeNo;
    }
    public String getDepartmentID() {
        return departmentID;
    }
    
    public void addTeachers (String username, String title, String surname, String forename, String password, Integer employeeNo, String departmentID) throws SQLException {
    	this.employeeNo = employeeNo;
    	this.departmentID = departmentID;
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String addUserStmt = "INSERT INTO User VALUES (?,?,?,?,?,?)";
            String addTeacherStmt = "INSERT INTO teacher VALUES (?, ?, ?)";
            // Assumes checking that username doesn't exist beforehand
            try (PreparedStatement addUStmt = con.prepareStatement(addUserStmt); PreparedStatement addTStmt = con.prepareStatement(addTeacherStmt)){
                addUStmt.setString(1, username);
                addUStmt.setString(2, title);
                addUStmt.setString(3, surname);
                addUStmt.setString(4, forename);
                addUStmt.setString(5, password);
                
                //Set variables for adding to teacher
                addTStmt.setString(1, username);
                addTStmt.setInt(2, employeeNo);
                addTStmt.setString(3, departmentID);
                
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
    
    public void addGrade(String moduleID, Integer registrationID, Integer overallMark) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "INSERT INTO module grade VALUES (?,?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setString(1, moduleID);
                updateStmt.setInt(2, registrationID);
                updateStmt.setInt(3, overallMark);
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
            try {
                stmt = con.createStatement();
                // Need to get level of student/ module and module credits to calculate this
                /*stmt.executeQuery("SELECT creditWorth, initialGrade, resistGrade FROM module AS m, "
                        + "module grade AS mg USING (registrationID) WHERE "
                        + "mg.email = '"+email+"' AND mg.moduleID = '"+moduleID+"'");
                */
                
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
    
    public Boolean viewStudentStatus(Integer registrationID) throws SQLException {
        Boolean status = null;
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT progress_to_next_level FROM student AS s, year grade AS yg USING (registrationID) WHERE s.registrationID = ?";
            try (PreparedStatement selectStmt = con.prepareStatement(preparedStmt)){
                selectStmt.setInt(1, registrationID);
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
    
    public void updateGrades(String moduleID, Integer registrationID, Integer overallMark) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "UPDATE module grade SET overallMark = ? WHERE moduleID = ? AND registrationID = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setInt(1, overallMark);
                updateStmt.setString(2, moduleID);
                updateStmt.setInt(3, registrationID);
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
    
    public void addResitGrades(String moduleID, Integer registrationID, Integer resitMark) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "UPDATE module grade SET resitMark = ? WHERE moduleID = ? AND registrationID = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setInt(1, resitMark);
                updateStmt.setString(2, moduleID);
                updateStmt.setInt(3, registrationID);
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