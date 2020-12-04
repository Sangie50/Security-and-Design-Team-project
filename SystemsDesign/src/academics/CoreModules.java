package academics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CoreModules{
    String moduleId;
    String degreeId;
    String levelOfStudy;
    
    public String getModuleId() {
        return moduleId;
    }
    public String getDegreeId() {
        return degreeId;
    }
    public String getLevelOfStudy() {
        return levelOfStudy;
    }
    
    public CoreModules(String moduleId,String degreeId, String levelOfStudy) throws SQLException {
        this.moduleId = moduleId;
        this.degreeId = degreeId;
        this.levelOfStudy = levelOfStudy;
    }
 
    public String toString() {
    	return String.format(this.moduleId + ";"+ this.degreeId + ";" + this.levelOfStudy);
    }
}

