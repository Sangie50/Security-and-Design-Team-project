package userclasses;
import java.sql.*;
import java.util.*;
//Teacher class

/*
Methods:
Add Grades
Update grades
Options for resit grades
calculate the weighted mean grade
Decide whether student passes
Based on the calculated grade, register student for next year
Transition to next year
Resit a year
Graduate
Drop out
Take weighted average of all years to give a final score
Calculate classification from final score
View status of student
Show outcome what student has achieved each period and level of study 
Show outcome of whole degree
Unique Attributes: 
Employee Number
Department ID
*/
public class Teachers extends Users{
    protected Integer employeeNo;
    protected String departmentID;
    
    public static void main(String[] args)throws SQLException {
        // TODO code application logic here
        System.out.println("Testing this crap 2: Electric Boogaloo");
        Connection con = null;  // a Connection object
        try{
            con = DriverManager.getConnection("", "", "");
        } catch (Exception ex) {
            ex.printStackTrace();
        } 
        Statement stmt = null;
        try{
            stmt = con.createStatement();
            // Examples that work
            /*int count = stmt.executeUpdate("UPDATE borrower SET forename = 'Jenny'" 
                    +  "WHERE memberID = 54231234"); */
        }catch (SQLException ex) {
            ex.printStackTrace();
        }finally {
            if (stmt != null)
                stmt.close();
            
        }
    }
    
}