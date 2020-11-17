package userclasses;
//Admin class

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
What needs doing (methods):
Add users
Remove users
Change access permissions
Create departments
Add degree courses (degree)
Remove degree courses
Assign courses to a department (options for multiple departments)
Indicate lead department
Add modules.
Remove Modules
Link modules to degrees and level of study (setting core or not)
Update the system after changes
Display results

Attributes to add
inherits from user class
*/
public class Admins extends Users{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException{
        // TODO code application logic here
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
            Statement stmt = null;
            try {
                stmt = con.createStatement();
                stmt.executeUpdate("");
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

