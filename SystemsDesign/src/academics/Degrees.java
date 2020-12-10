package academics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Degrees{
    String degreeID;
    String departmentID;
    String entryLevel;
    String difficulty;
    String degreeName;
    String lastLevel;
    String otherDept;
    
    public String getDegreeID() {
        return degreeID;
    }
    public String getDepartmentID() {
        return departmentID;
    }
    public String getEntryLevel() {
        return entryLevel;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public String getDegreeName() {
        return degreeName;
    }
    public String getLastLevel() {
        return lastLevel;
    }
    
    public Degrees(String degreeID, String departmentID, String entryLevel, String difficulty, String degreeName, String lastLevel, String otherDept) throws SQLException {
        this.degreeID = degreeID;
        this.departmentID = departmentID;
        this.entryLevel = entryLevel;
        this.difficulty = difficulty;
        this.degreeName = degreeName;
        this.lastLevel = lastLevel;
        this.otherDept = otherDept;
    }
    
    public String toString() {
    	return String.format(this.degreeID + this.departmentID + this.entryLevel + "."+
    			this.difficulty+ " " + this.degreeName + ";" +this.lastLevel+","+ this.otherDept);
    }
    
}