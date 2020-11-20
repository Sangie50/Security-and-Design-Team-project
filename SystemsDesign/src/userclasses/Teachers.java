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
[D]Calculate the weighted mean grade of the module Might need to be done in the degree
[?]Decide whether student passes
[?]Based on the calculated grade, register student for next year ,Transition to next year, Resit a year, Graduate or Drop out
[?]Take weighted average of all years to give a final score
[D]Calculate classification from the module
[~]View status of student - Returns if they passed or failed the year. I really don't know what this promopt wanted from me.
[GUI]Show outcome what student has achieved each period and level of study 
[GUI]Show outcome of whole degree

Unique Attributes: 
Employee Number
Department ID
*/
public class Teachers extends Users{
    Integer employeeNo;
    String departmentID;
    
    public Teachers (String username, String title, String surname, String forename, String password, Integer employeeNo, String departmentID) throws SQLException {
    	super(username, title, surname, forename, password);
    	this.employeeNo = employeeNo;
    	this.departmentID = departmentID;
    	 Connection con = null;
       try {
           con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
           Statement stmt = null;
             String preparedStmt = "INSERT INTO teacher VALUES (?, ?, ?)";
           try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
           	con.setAutoCommit(false);
           	
               updateStmt.setString(1, username);
               updateStmt.setInt(2, employeeNo);
               updateStmt.setString(3, departmentID);
               
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
    
    public Integer getEmployeeNo() {
        return employeeNo;
    }
    public String getDepartmentID() {
        return departmentID;
    }
    
    public void addGrade(String moduleID, Integer registrationID, Integer overallMark) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO module grade VALUES ("+moduleID+","+registrationID+","+overallMark+","+null+")");
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
    
    public Integer weightedMean(String moduleID, String email) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
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
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                // Need to get level of student/ module and module credits to calculate this
                ResultSet passorfail = stmt.executeQuery("SELECT progress_to_next_level FROM student AS s, year grade AS yg USING (registrationID) WHERE s.registrationID = '"+registrationID+"'");
                // parse through set turn to object etc
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
        
        return status; // for now change later when get variable
    }
    
    public void updateGrades(String moduleID, Integer registrationID, Integer overallMark) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate("UPDATE module grade SET overallMark = '"+overallMark+"' WHERE moduleID = '"+moduleID+"' AND registrationID = '"+registrationID+"'");
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
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate("UPDATE module grade SET resitMark = '"+resitMark+"' WHERE moduleID = '"+moduleID+"' AND registrationID = '"+registrationID+"'");
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