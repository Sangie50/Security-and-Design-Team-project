package userclasses;
//Admin class

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Dictionary;
import java.util.Hashtable;

/*
What needs doing (methods):
[E]Add users
[-]Remove users
[O]Change access permissions
[X]Create departments 
[X]Add degree courses (degree)
[-]Remove degree courses
[-]Assign courses to a department (options for multiple departments)
[-]Indicate lead department (part of degree)
[X]Add modules.
[-]Remove Modules
[-]Link modules to degrees and level of study (setting core or not)
[-]Update the system after changes
[-]Display results

Attributes to add
inherits from user class
*/
public class Admins extends Users{
    
    // For in class testing
    public static void main(String[] args) throws Exception {
        //Admins.UpdatePermissions("generic email", 2);
    }
    
    //i think these belongs here 
    public void UpdatePermissions(String email, Integer newPermission) throws SQLException {
        // 1: Admin | 2: Registrar | 3: Teacher | 4: Student 
        Dictionary permission = new Hashtable();
        permission.put(1 ,"Admin");
        permission.put(2 ,"Registrar");
        permission.put(3 ,"Teacher");
        permission.put(4 ,"Student");
        Connection con = null;
        //System.out.println(permission.get(newPermission));
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate("UPDATE user SET accountType = '"+permission.get(newPermission)+"' WHERE email = '"+email+"')");
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
    
    public void AddModule(String moduleName, Integer levelOfStudy, Integer creditWorth, String departmentID, Integer passMark) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO Module VALUES ('"+moduleName+"',"+levelOfStudy+","+creditWorth+",'"+departmentID+"',"+passMark+")");
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
    
    public void AddDepartment(String departmentID, String departmentName, String entryLevel) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                // need to check if it already exists
                ResultSet r = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM department WHERE departmentID = '"+departmentID+"'");
                r.next();
                int count = r.getInt("rowcount");
                if (count < 1){
                    stmt.executeUpdate("INSERT INTO department VALUES ('"+departmentID+"','"+departmentName+"','"+entryLevel+"')");
                }
                else{
                    System.out.println("DepartmentID already exists");
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
    
    public void AddDegree(String degreeID, String departmentID, String entryLevel, String difficulty, String degreeName) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                // need to check if it already exists
                ResultSet r = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM degree WHERE degreeID = '"+degreeID+"'");
                r.next();
                int count = r.getInt("rowcount");
                if (count < 1){
                    stmt.executeUpdate("INSERT INTO degree VALUES ('"+degreeID+"','"+departmentID+"','"+entryLevel+"','"+difficulty+"','"+degreeName+"')");
                }
                else{
                    System.out.println("DegreeID already exists");
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
    
}

