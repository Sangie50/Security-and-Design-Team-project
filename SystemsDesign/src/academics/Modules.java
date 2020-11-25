/*
[X] Get module ID
[-] Teachers module link?

*/

package academics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Modules{
    String moduleID;
    String moduleName;
    Integer levelOfStudy;
    Integer creditWorth;
    String departmentID;
    Integer pass_grade;
    
    public String getModuleID() {
        return moduleID;
    }
    public String getModuleName() {
        return moduleName;
    }
    public Integer getLevelOfStudy() {
        return levelOfStudy;
    }
    public Integer getCreditWorth() {
        return creditWorth;
    }
    public String getDepartmentID() {
        return departmentID;
    }
    public Integer getPassGrade() {
        return pass_grade;
    }
    
    public Modules() throws SQLException {
    
    }
    
    public void addTeacherModule (Integer employeeNo, String departmentID, String moduleID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String addTeacherModuleStmt = "INSERT INTO module_teacher VALUES (?, ?, ?)";
            // Assumes checking that username doesn't exist beforehand
            try (PreparedStatement addTMStmt = con.prepareStatement(addTeacherModuleStmt)){
                
                //Set variables for adding to teacher to module
                addTMStmt.setInt(1, employeeNo);
                addTMStmt.setString(2, departmentID);
                addTMStmt.setString(3, moduleID);
                
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