package academics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Degrees{
    String departmentID;
    String departmentName;
    String entryLevel;
    String difficulty;
    String degreeName;
    String lastLevel;
    
    public String getDepartmentID() {
        return departmentID;
    }
    public String getDepartmentName() {
        return departmentName;
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
    
    public Degrees(String departmentID, String departmentName, String entryLevel, String difficulty, String degreeName, String lastLevel) throws SQLException {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.entryLevel = entryLevel;
        this.difficulty = difficulty;
        this.degreeName = degreeName;
        this.lastLevel = lastLevel;
    }
    
}