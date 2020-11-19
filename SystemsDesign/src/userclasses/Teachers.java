package userclasses;
import java.sql.*;
import java.util.*;
//Teacher class

/*
Methods:
[X]Add Grades - May need to add where clause on for teachers to stop lots of changes
[X]Update grades 
[X]Resit grades
[-]Calculate the weighted mean grade of the module
[?]Decide whether student passes
[?]Based on the calculated grade, register student for next year ,Transition to next year, Resit a year, Graduate or Drop out
[?]Take weighted average of all years to give a final score
[-]Calculate classification from the module
[-]View status of student
[GUI]Show outcome what student has achieved each period and level of study 
[GUI]Show outcome of whole degree

Unique Attributes: 
Employee Number
Department ID
*/
public class Teachers extends Users{
    Integer employeeNo;
    String departmentID;
    
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