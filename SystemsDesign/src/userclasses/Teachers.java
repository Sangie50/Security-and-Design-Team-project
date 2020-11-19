package userclasses;
import java.sql.*;
import java.util.*;
//Teacher class

/*
Methods:
[~]Add Grades
[-]Update grades
[-]Add options for resit grades
[-]calculate the weighted mean grade
[-]Decide whether student passes
[?]Based on the calculated grade, register student for next year
[?]Transition to next year
[?]Resit a year
[?]Graduate or Drop out
[?]Take weighted average of all years to give a final score
[?]Calculate classification from final score
[-]View status of student
[-]Show outcome what student has achieved each period and level of study 
[-]Show outcome of whole degree

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
    
    public void AddTeacher(String teacherEmail, String departmentID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO teacher (email, departmentID) VALUES('"+teacherEmail+"','"+departmentID+"')");
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
    
    public void AddGrades(String studentEmail, Integer grade) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate("UPDATE module_grade WHERE email = '" + studentEmail + "'SET grade? = '"+ grade +"'");
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