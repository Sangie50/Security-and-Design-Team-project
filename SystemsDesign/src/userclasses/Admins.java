package userclasses;
//Admin class

import java.sql.*;
import java.util.*;

/*
What needs doing (methods):
[X~]Add users (need to add password protection if its done on this end)
[X]Remove users
[X]Change access permissions
[X]Create departments 
[X]Remove departments
[X]Add degree courses (degree)
[X]Remove degree courses
[UI]Assign courses to a department (options for multiple departments)
[UI]Indicate lead department (part of degree) is Interdiscplinary?
[X]Add modules.
[X]Remove Modules
[X~]Set core modules - CHECK NAME OF TABLE
[UI]Link modules to degrees and level of study (setting core or not)
[UI]Update the system after changes
[NAH - UI]Display (return) results

Attributes to add
inherits from user class
*/
public class Admins extends Users{
    // For in class testing
    /*
    public static void main(String[] args) throws Exception {
        Admins.updatePermissions("generic email", 2);
    }
    */
    
    public void addUser(String username, String title, String forename, String lastname, String accountType, String password) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                // NEED TO CHANGE - password needs to be made more secure
                stmt.executeUpdate("INSERT INTO User VALUES ('"+username+"','"+title+"','"+forename+"','"+lastname+"','"+accountType+"','"+password+"')");
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
    
    public void updatePermissions(String username, String accountType) throws SQLException {
        // Admin | Registrar | Teacher | Student | Null 
        Connection con = null;
        //System.out.println(permission.get(newPermission));
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate("UPDATE user SET accountType = '"+accountType+"' WHERE username = '"+username+"')");
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
    
    public void removeUsers(String username) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                // Checks if the amil given belongs to a user, yeacher or student and deletes if does.
                
                // need to check if it exists
                ResultSet r = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM users WHERE username = '"+username+"'");
                r.next();
                int countUser = r.getInt("rowcount");
                if (countUser > 0){
                    // Deletes main user account
                    stmt.executeUpdate("DELETE FROM User WHERE username = '"+username+"')");
                }
                ResultSet s = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM users WHERE username = '"+username+"'");
                s.next();
                int countStudent = s.getInt("rowcount");
                if (countStudent > 0){
                    // Deletes student user account
                    stmt.executeUpdate("DELETE FROM Student WHERE username = '"+username+"')");
                }
                ResultSet t = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM users WHERE username = '"+username+"'");
                t.next();
                int countTeacher = t.getInt("rowcount");
                if (countTeacher > 0){
                    // Deletes Teacher user account
                    stmt.executeUpdate("DELETE FROM Teacher WHERE username = '"+username+"')");
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
    
    public void addModule(String moduleName, Integer levelOfStudy, Integer creditWorth, String departmentID, Integer passMark) throws SQLException {
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
    
    public void setCoreModule(Integer moduleID, String departmentID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO Core Module VALUES ("+moduleID+","+departmentID+"");
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
    
    public void removeModule(Integer moduleID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                // need to check if it exists
                ResultSet r = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM module WHERE moduleID = "+moduleID+"");
                r.next();
                int countDegree = r.getInt("rowcount");
                if (countDegree > 0){
                    // Deletes degree if the degree exists
                    stmt.executeUpdate("DELETE FROM module WHERE moduleID = "+moduleID+"");
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
    
    public void addDepartment(String departmentID, String departmentName, String entryLevel) throws SQLException {
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
    
    public void removeDepartment(String departmentID) throws SQLException {
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
                if (count > 0){
                    stmt.executeUpdate("DELETE FROM department WHERE departmentID = '"+departmentID+"'");
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
    
    public void addDegree(String degreeID, String departmentID, String entryLevel, String difficulty, String degreeName) throws SQLException {
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
    
    public void removeDegree(String degreeID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                // need to check if it exists
                ResultSet r = stmt.executeQuery("SELECT COUNT(*) AS rowcount FROM degree WHERE degreeID = '"+degreeID+"'");
                r.next();
                int countDegree = r.getInt("rowcount");
                if (countDegree > 0){
                    // Deletes degree if the degree exists
                    stmt.executeUpdate("DELETE FROM degree WHERE degreeID = '"+degreeID+"')");
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