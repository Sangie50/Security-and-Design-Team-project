package userclasses;
//Admin class

import academics.Degrees;
import academics.Departments;
import academics.Modules;
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
    
    public static void updatePermissions(String username, String accountType) throws SQLException {
        // Admin | Registrar | Teacher | Student | Null 
        Connection con = null;
        //System.out.println(permission.get(newPermission));
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "UPDATE user SET account_type = ? WHERE username = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setString(1, accountType);
                updateStmt.setString(2, username);
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
                count.next();
                if (count.getInt("rowcount") > 0){
                    delStmt.setString(1, username);
                    // Other connected rows should be deleted via cascades
                    delStmt.executeUpdate();
                    con.commit();
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
    
    public static void addModule(String moduleID, String moduleName, Integer creditWorth, String departmentID, Integer passMark, Boolean isTaught) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String selectStmt = "SELECT COUNT(*) AS rowcount FROM module WHERE module_id = ?";
            String preparedStmt = "INSERT INTO module VALUES (?,?,?,?,?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt);PreparedStatement selStmt = con.prepareStatement(selectStmt)){
                selStmt.setString(1, moduleID);
                ResultSet count = selStmt.executeQuery();
                con.commit();
                count.next();
                if (count.getInt("rowcount") == 0){
                    stmt = con.createStatement();
                    updateStmt.setString(1, moduleID);
                    updateStmt.setString(2, moduleName);
                    updateStmt.setInt(3, creditWorth);
                    updateStmt.setString(4, departmentID);
                    updateStmt.setInt(5, passMark);
                    updateStmt.setBoolean(6, isTaught);
                    updateStmt.executeUpdate();
                    con.commit();
                }
                else{
                    System.err.println("ModuleID already exists.");
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
    
    public static void setCoreModule(String moduleID, String departmentID, String levelOfStudy) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "INSERT INTO core_modules VALUES (?,?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setString(1, moduleID);
                updateStmt.setString(2, departmentID);
                updateStmt.setString(3, levelOfStudy);
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
    
    public static List<Modules> viewCoreModule() throws SQLException {
        List<Modules> mod = new ArrayList<Modules>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT * FROM module INNER JOIN core_modules ON module.module_id = core_modules.module_id";
            //System.out.println(preparedStmt);
            try (PreparedStatement selectStmt = con.prepareStatement(preparedStmt)){
                ResultSet module = selectStmt.executeQuery();
                while (module.next()) {
                    String moduleID = module.getString("module_id");
                    String moduleName = module.getString("module_name");
                    Integer creditWorth = module.getInt("credit_worth");
                    String departmentID = module.getString("department_id");
                    Integer passGrade = module.getInt("pass_grade");
                    Boolean isTaught = module.getBoolean("pass_grade");
                    String degreeID = module.getString("degree_id");
                    Boolean level_of_study = module.getBoolean("level_of_study");
                    
                    con.commit();
                    Modules modules = new Modules(moduleID, moduleName, isTaught, creditWorth, departmentID, passGrade);
                    mod.add(modules);
                    modules.getModuleName();
                    System.out.println(moduleID+":"+moduleName+":"+isTaught+":"+creditWorth+":"+departmentID+":"+passGrade+":"+degreeID+":"+level_of_study);
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
        return mod;
    }
    
    public static List<Modules> viewAllModule() throws SQLException {
        List<Modules> mod = new ArrayList<Modules>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT * FROM module";
            //System.out.println(preparedStmt);
            try (PreparedStatement selectStmt = con.prepareStatement(preparedStmt)){
                ResultSet module = selectStmt.executeQuery();
                while (module.next()) {
                    String moduleID = module.getString("module_id");
                    String moduleName = module.getString("module_name");
                    Integer creditWorth = module.getInt("credit_worth");
                    String departmentID = module.getString("department_id");
                    Integer passGrade = module.getInt("pass_grade");
                    Boolean isTaught = module.getBoolean("pass_grade");
                    
                    con.commit();
                    Modules modules = new Modules(moduleID, moduleName, isTaught, creditWorth, departmentID, passGrade);
                    mod.add(modules);
                    System.out.println(moduleID+":"+moduleName+":"+isTaught+":"+creditWorth+":"+departmentID+":"+passGrade);
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
        return mod;
    }
    
    public static void removeModule(String moduleID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT COUNT(*) AS rowcount FROM module WHERE module_id = ?";
            String deleteStmt = "DELETE FROM module WHERE module_id = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt); PreparedStatement delStmt = con.prepareStatement(deleteStmt)){
                updateStmt.setString(1, moduleID);
                ResultSet count = updateStmt.executeQuery();
                count.next();
                if (count.getInt(1) > 0){
                    delStmt.setString(1, moduleID);
                    delStmt.executeUpdate();
                    con.commit();
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
                con.commit();
                count.next();
                if (count.getInt("rowcount") == 0){
                    insStmt.setString(1, departmentID);
                    insStmt.setString(2, departmentName);
                    insStmt.setString(3, entryLevel);
                    insStmt.executeUpdate();
                    con.commit();
                }
                else{ 
                    System.err.println("DepartmentID or department name already exists.");
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
    
    public static List<Departments> viewDepartment() throws SQLException {
        List<Departments> departs = new ArrayList<Departments>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT * FROM department";
            //System.out.println(preparedStmt);
            try (PreparedStatement selectStmt = con.prepareStatement(preparedStmt)){
                ResultSet department = selectStmt.executeQuery();
                while (department.next()) {
                    String departmentID = department.getString("department_id");
                    String departmentName = department.getString("department_name");
                    String entryLevel = department.getString("entry_level");
                    con.commit();
                    Departments depart = new Departments(departmentID, departmentName, entryLevel);
                    departs.add(depart);
                    System.out.println(departmentID+"; "+departmentName+", Entry level; "+entryLevel);
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
        return departs;
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
                con.commit();
                count.next();
                if (count.getInt(1) > 0){
                    delStmt.setString(1, departmentID);
                    delStmt.executeUpdate();
                    con.commit();
                    // Other connected rows should be deleted via cascades
                }
                else{ 
                    System.err.println("No departments exist with that departmentID.");
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
    
    public static void addDegree(String degreeID, String departmentID, String entryLevel, String difficulty, String degreeName, String lastLevel) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT COUNT(*) AS rowcount FROM degree WHERE degree_id = ?";
            String insertStmt = "INSERT INTO degree(degree_id, department_id, entry_level, difficulty, degree_name, last_level) VALUES (?,?,?,?,?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt); PreparedStatement insStmt = con.prepareStatement(insertStmt)){
                updateStmt.setString(1, degreeID);
                con.commit();
                ResultSet count = updateStmt.executeQuery();
                count.next();
                if (count.getInt(1) == 0){
                    insStmt.setString(1, degreeID);
                    insStmt.setString(2, departmentID);
                    insStmt.setString(3, entryLevel);
                    insStmt.setString(4, difficulty);
                    insStmt.setString(5, degreeName);
                    insStmt.setString(6, lastLevel);
                    insStmt.executeUpdate();
                    con.commit();
                }
                else{ 
                    System.err.println("Degree ID already exists already exists.");
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
    
    public static void addDegreePartner(String degreeID, String otherDepartmentID) throws SQLException {
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT COUNT(*) AS rowcount FROM degree WHERE degree_id = ?";
            String insertStmt = "INSERT INTO interdisciplinary_degree(degree_id, other_department) VALUES (?,?)";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt); PreparedStatement insStmt = con.prepareStatement(insertStmt)){
                updateStmt.setString(1, degreeID);
                con.commit();
                ResultSet count = updateStmt.executeQuery();
                count.next();
                if (count.getInt(1) > 0){
                    insStmt.setString(1, degreeID);
                    insStmt.setString(2, otherDepartmentID);
                    insStmt.executeUpdate();
                    con.commit();
                }
                else{ 
                    System.err.println("Degree ID doesn't exist.");
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
    
    public static List<Degrees> viewDegree() throws SQLException {
        List<Degrees> deg = new ArrayList<Degrees>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "SELECT * FROM degree Left JOIN interdisciplinary_degree ON degree.degree_id = interdisciplinary_degree.degree_id";
            //System.out.println(preparedStmt);
            try (PreparedStatement selectStmt = con.prepareStatement(preparedStmt)){
                ResultSet department = selectStmt.executeQuery();
                while (department.next()) {
                    String degreeID = department.getString("degree_id");
                    String departmentID = department.getString("department_id");
                    String entryLevel = department.getString("entry_level");
                    String difficulty = department.getString("difficulty");
                    String degreeName = department.getString("degree_name");
                    String lastLevel = department.getString("last_level");
                    String partner = department.getString("other_department");
                    
                    con.commit();
                    Degrees degree = new Degrees(degreeID, departmentID, entryLevel, difficulty, degreeName, lastLevel);
                    deg.add(degree); 
                    System.out.println(degreeID+":"+departmentID+":"+entryLevel+":"+difficulty+":"+degreeName+":"+lastLevel+":"+partner);
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
        return deg;
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
                con.commit();
                ResultSet count = updateStmt.executeQuery();
                count.next();
                if (count.getInt(1) > 0){
                    delStmt.setString(1, degreeID);
                    delStmt.executeUpdate();
                    con.commit();
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