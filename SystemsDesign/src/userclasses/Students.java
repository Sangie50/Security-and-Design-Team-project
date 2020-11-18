package userclasses;
import java.sql.*;
import java.util.*;
//Students class

public class Students extends Users{
  Integer periodOfStudy;
  Integer startDate;
  Integer endDate;
  
  public Integer getPeriodOfStudy() {
  	return periodOfStudy;
  }
  
  public Integer getstartDate() {
  	return startDate;
  }
  
  public Integer getEndDate() {
  	return endDate;
  }
  
  public static void main(String[] args)throws SQLException {
      // TODO code application logic here
      Connection con = null;  // a Connection object
      try{
          con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
      } catch (Exception ex) {
          ex.printStackTrace();
      } 
      Statement stmt = null;
      try{
          stmt = con.createStatement();
      }catch (SQLException ex) {
          ex.printStackTrace();
      }finally {
          if (stmt != null)
              stmt.close();
      }
  }
}