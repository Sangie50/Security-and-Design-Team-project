package userclasses;
//Admin class

import java.sql.*;
import java.util.*;

/*
What needs doing (methods):
[X]Add users 
[X]Remove users
[X]Change access permissions
[X]Create departments 
[X]Remove departments
[X]Add degree courses (degree)
[X]Remove degree courses
[GUI]Assign courses to a department (options for multiple departments)
[GUI]Indicate lead department (part of degree) is Interdiscplinary?
[X]Add modules.
[X]Add core module data to link to degree - Set core modules - CHECK NAME OF TABLE
[O]Remove Modules
[GUI]Link modules to degrees and level of study (setting core or not)
[GUI]Update the system after changes
[GUI]Display (return) results

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
    public Admins (String username, String title, String surname, String forename, String password)throws SQLException {
        super(username, title, surname, forename, password);
    }
    
    public static void addUser(String username, String title, String forename, String lastname, String accountType, String password) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "INSERT INTO user VALUES (?,?,?,?,?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setString(1, username);
                updateStmt.setString(2, title);
                updateStmt.setString(3, forename);
                updateStmt.setString(4, lastname);
                updateStmt.setString(5, accountType);
                updateStmt.setString(6, password);
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
    
    public static void updatePermissions(String username, String accountType) throws SQLException {
        // Admin | Registrar | Teacher | Student | Null 
        Connection con = null;
        //System.out.println(permission.get(newPermission));
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "UPDATE user SET accountType = ? WHERE username = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setString(1, accountType);
                updateStmt.setString(2, username);
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
    
    public static void removeUsers(String username) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT COUNT(*) AS rowcount FROM user WHERE username = ?";
            String deleteStmt = "DELETE FROM user WHERE username = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt); PreparedStatement delStmt = con.prepareStatement(deleteStmt)){
                updateStmt.setString(1, username);
                ResultSet count = updateStmt.executeQuery();
                if (count.getInt("rowcount") > 0){
                    delStmt.setString(1, username);
                    // Other connected rows should be deleted via cascades
                }
                else{ 
                    System.err.println("No users exist with that username");
                } 
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
    
    public static void addModule(String moduleName, Integer levelOfStudy, Integer creditWorth, String departmentID, Integer passMark) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "INSERT INTO module VALUES (?,?,?,?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setString(1, moduleName);
                updateStmt.setInt(2, levelOfStudy);
                updateStmt.setInt(3, creditWorth);
                updateStmt.setString(4, departmentID);
                updateStmt.setInt(5, passMark);
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
    
    public static void setCoreModule(Integer moduleID, String departmentID, Boolean isCore) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "INSERT INTO core_module VALUES (?,?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setInt(1, moduleID);
                updateStmt.setString(2, departmentID);
                updateStmt.setBoolean(3, isCore);
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
    
    public static void removeModule(Integer moduleID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT COUNT(*) AS rowcount FROM module WHERE module_id = ?";
            String deleteStmt = "DELETE FROM module WHERE module_id = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt); PreparedStatement delStmt = con.prepareStatement(deleteStmt)){
                updateStmt.setInt(1, moduleID);
                ResultSet count = updateStmt.executeQuery();
                if (count.getInt(1) > 0){
                    delStmt.setInt(1, moduleID);
                    // Other connected rows should be deleted via cascades
                }
                else{ 
                    System.err.println("No modules exist with that moduleID.");
                } 
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
    
    public static void addDepartment(String departmentID, String departmentName, String entryLevel) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT COUNT(*) AS rowcount FROM department WHERE department_id = ? OR department_name = ?";
            String insertStmt = "INSERT INTO department VALUES (?,?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt); PreparedStatement insStmt = con.prepareStatement(insertStmt)){
                updateStmt.setString(1, departmentID);
                updateStmt.setString(2, departmentName);
                ResultSet count = updateStmt.executeQuery();
                if (count.getInt("rowcount") < 1){
                    insStmt.setString(1, departmentID);
                    insStmt.setString(2, departmentName);
                    insStmt.setString(3, entryLevel);
                    // Other connected rows should be deleted via cascades
                }
                else{ 
                    System.err.println("DepartmentID or department name already exists already exists.");
                } 
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
    
    public static void removeDepartment(String departmentID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT COUNT(*) AS rowcount FROM department WHERE department_id = ?";
            String deleteStmt = "DELETE FROM department WHERE department_id = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt); PreparedStatement delStmt = con.prepareStatement(deleteStmt)){
                updateStmt.setString(1, departmentID);
                ResultSet count = updateStmt.executeQuery();
                if (count.getInt(1) > 0){
                    delStmt.setString(1, departmentID);
                    // Other connected rows should be deleted via cascades
                }
                else{ 
                    System.err.println("No departments exist with that departmentID.");
                } 
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
    
    public static void addDegree(String degreeID, String departmentID, String entryLevel, String difficulty, String degreeName) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT COUNT(*) AS rowcount FROM degree WHERE degree_id = ?";
            String insertStmt = "INSERT INTO degree VALUES (?,?,?,?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt); PreparedStatement insStmt = con.prepareStatement(insertStmt)){
                updateStmt.setString(1, degreeID);
                ResultSet count = updateStmt.executeQuery();
                if (count.getInt("rowcount") < 1){
                    insStmt.setString(1, degreeID);
                    insStmt.setString(2, departmentID);
                    insStmt.setString(3, entryLevel);
                    insStmt.setString(4, difficulty);
                    insStmt.setString(5, degreeName);
                    // Other connected rows should be deleted via cascades
                }
                else{ 
                    System.err.println("Degree ID already exists already exists.");
                } 
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
    
    public static void removeDegree(String degreeID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT COUNT(*) AS rowcount FROM degree WHERE degree_id = ?";
            String deleteStmt = "DELETE FROM degree WHERE degree_id = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt); PreparedStatement delStmt = con.prepareStatement(deleteStmt)){
                updateStmt.setString(1, degreeID);
                ResultSet count = updateStmt.executeQuery();
                if (count.getInt(1) > 0){
                    delStmt.setString(1, degreeID);
                    // Other connected rows should be deleted via cascades
                }
                else{ 
                    System.err.println("No departments exist with that departmentID.");
                } 
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