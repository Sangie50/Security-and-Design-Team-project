package academics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Departments{
    String departmentID;
    String departmentName;
    String entryLevel;
    
    
    public String getDepartmentID() {
        return departmentID;
    }
    public String getDepartmentName() {
        return departmentName;
    }
    public String getEntryLevel() {
        return entryLevel;
    }
    
    public Departments(String departmentID, String departmentName, String entryLevel) throws SQLException {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.entryLevel = entryLevel;
    }
    
    public String toString(){
    	return String.format(this.departmentID + ";" + this.departmentName + ";" + this.entryLevel);
    }
    
}