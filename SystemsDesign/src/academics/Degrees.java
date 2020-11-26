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
    
    public Degrees(String degreeID, String departmentID, String entryLevel, String difficulty, String degreeName, String lastLevel) throws SQLException {
        this.degreeID = degreeID;
        this.departmentID = departmentID;
        this.entryLevel = entryLevel;
        this.difficulty = difficulty;
        this.degreeName = degreeName;
        this.lastLevel = lastLevel;
    }
    
}