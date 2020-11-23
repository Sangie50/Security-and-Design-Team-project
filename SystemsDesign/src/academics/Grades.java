package academics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import java.util.*;

import userclasses.Students;

/* Methods to be implemented
 * []Calculate the weighted (by credit) mean grade of all modules to give end of year grade
 * []Decide whether student passes this module?
 * []Take weighted average of all years to give a final score //deleted, seems like degreeGrade
 * []Calculate classification from the module
 * []cap re-sit grade at pass grade (1-3 40%, 4-50%) //update this in the database
 * []conceded pass (20 for 1-3, 15 for 4) with no more 10% below pass grade, met the overall average requirement
 * []final degree grade, 3rd:2nd 2:1 ratio
 * []special rules
 */

public class Grades { //create a constructor
	String email;
	HashMap<String, Integer> gradeForModule;
	
	enum LevelsOfStudy {
		CERTIFICATE("1"),
		DIPLOMA("2"), 
		BACHELORS("3"), 
		MASTERS("4"),
		PG;
		private String action; 
	  
    // getter method 
    public String getAction() { 
        return this.action; 
    } 
  
    private LevelsOfStudy(String action) { //constructor 
        this.action = action; 
    }
    private LevelsOfStudy() {
    	//constructor
    }
	}
	
	public static String getLevelOfStudy(String email) throws SQLException{
		String levelOfStudy = null;
		Connection con = null; 
		 try {
				 	con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getLevelOfStudy = String.format("SELECT level_of_study FROM year_grade WHERE email = %s" , email);    
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getLevelOfStudy)){
				      	rs = pstmt.executeQuery();        
				      	levelOfStudy = rs.getString(2);      
				      	
				      	rs.close();                       
				      	pstmt.close();                    
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
		 return levelOfStudy;
	}
	public static List<String> getModuleList(String email, String levelOfStudy) throws SQLException{
		List<String> moduleList = new ArrayList<>();
		Connection con = null; 
		 try {
				 	con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getModuleId = String.format("SELECT module_id FROM module_grade WHERE email = %s AND level_of_study = %s ", email, levelOfStudy);
		      String moduleId;
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getModuleId)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	while (rs.next()) {               // Position the cursor                  4 
					      	 moduleId = rs.getString(1);        // Retrieve the first column value
					      	 moduleList.add(moduleId); 
				      	}
				      	
				      	rs.close();                       
				      	pstmt.close();  
				      	
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

		 return moduleList; 
	}
	
	public HashMap<String, Integer> gradesForEachModule(String email, String levelOfStudy) throws SQLException{
		gradeForModule = new HashMap<String, Integer>();
		List<String> listOfModules = getModuleList(email, levelOfStudy);
		for(int i = 0; i < listOfModules.size(); i++) {
			String moduleCode = listOfModules.get(i);
			Connection con = null; 
			 try {
					 	con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
			      con.setAutoCommit(false);
			      Statement stmt = null;
			      String getInitialGrade = String.format("SELECT inital_grade FROM module_grade WHERE module_id = %s AND email = %s" , moduleCode, email);    
			      String getResitGrade = String.format("SELECT resit_grade FROM module_grade WHERE module_id = %s AND email = %s" , moduleCode, email);
			      String resitGradeString;
			      String initialGradeString;
			      PreparedStatement pstmt = con.prepareStatement(getInitialGrade);
			      PreparedStatement pstmt2 = con.prepareStatement(getResitGrade);
			      ResultSet rs;
			      ResultSet rs2;
			      try {
					      	rs = pstmt.executeQuery();        // Get the result table from the query  3   
					      	rs2 = pstmt2.executeQuery();
				      	  initialGradeString = rs.getString(3);        // Retrieve the first column value
				      	  resitGradeString = rs2.getString(4);
			      	    int initialGrade = Integer.parseInt(initialGradeString);
			      	    int resitGrade = Integer.parseInt(resitGradeString);
					      	if (initialGrade >= 40) {
					      		gradeForModule.put(moduleCode, initialGrade);
					      	}
					      	else {
					      		gradeForModule.put(moduleCode, resitGrade);
					      	}
					      	rs.close();                       // Close the ResultSet                  5 
					      	pstmt.close();                    
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
		 return gradeForModule;
	}
	
	/*public HashMap<String, Boolean> passModule(String email, String levelOfStudy) throws SQLException { 
		HashMap<String, Integer> grades_hmap = gradesForEachModule(email, levelOfStudy);
		HashMap<String, Boolean> passOrFail = new HashMap<>();
		for (int i = 0; i < grades_hmap.size(); i++) {
			//passOrFail.put(grade, remappingFunction)
		}
		
		if (grade >= capResitGrade(email)) {
			return true;
		}
		else {
			return false;
		}
	}
	*/
	
	/*public String checkForConcededPass(String email) {
		Double yearGrade = getWeightedYearGrade(email, getLevelOfStudy(email));
		if (yearGrade < 39.4) {
			
		}
	}
	*/
	
	public int capResitGrade(String email) throws SQLException { //update re-sit mark as 40/50 depending on the year
		String levelOfStudy = getLevelOfStudy(email);
		int cap = 100; //check, fails for passed modules
		if (levelOfStudy ==  String.valueOf(LevelsOfStudy.MASTERS)) {
  		cap = 50;
  	}
  	else {
  		cap = 40;
  	}
		return cap;
	}
	
	public Double getWeightedYearGrade(String email, String levelOfStudy) throws SQLException {
		List<String> moduleList = getModuleList(email, levelOfStudy);
		List<Integer> creditsList = Students.moduleCredits(email);
		HashMap<String, Integer> gradePerModule = gradesForEachModule(email, levelOfStudy);
		Double sum = 0.0;
		Double meanGrade = 0.0;
		if (moduleList.size() == creditsList.size()) {
			for(int i = 0; i < moduleList.size(); i++) {
				Integer grade = gradePerModule.get(moduleList.get(i));
				sum += grade * creditsList.get(i); //check if weightage is same as credit
			}
			meanGrade = sum/moduleList.size();
		}
		
		return meanGrade;
	}
	
	public Boolean yearPassed(String email, String levelOfStudy) throws SQLException {
		String currentLevelOfStudy = getLevelOfStudy(email);
		Double yearGrade = getWeightedYearGrade(email, levelOfStudy);
		if ((currentLevelOfStudy != (String.valueOf(LevelsOfStudy.MASTERS)) || currentLevelOfStudy != String.valueOf(LevelsOfStudy.PG)) 
				&& yearGrade >= 39.5) {
			return true;
		}
		else if((currentLevelOfStudy == (String.valueOf(LevelsOfStudy.MASTERS)) || currentLevelOfStudy == String.valueOf(LevelsOfStudy.PG)) 
				&& yearGrade >= 49.5) {
			return false;
		}
		else {
			return false;
		}
			
	}
	
	public String getEntryLevel(String email) throws SQLException {
		String degreeId = Students.getDegreeId(email);
		String entryLevel = null;
		Connection con = null; 
		 try {
				 	con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getEntry_level = String.format("SELECT entry_level FROM degree WHERE degree_id = %s ", degreeId);
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getEntry_level)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	while (rs.next()) {               // Position the cursor                  4 
					      	 entryLevel = rs.getString(3);        // Retrieve the first column value
				      	}
				      	
				      	rs.close();                       
				      	pstmt.close();  
				      	
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
		 return entryLevel;
	}
	
	public static String getLastLevelOfStudy(String email) throws SQLException{
		String lastLevel = null;
		String degreeId = Students.getDegreeId(email);
		Connection con = null; 
		 try {
				 	con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getLast_level = String.format("SELECT last_level FROM degree WHERE degree_id = %s ", degreeId);
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getLast_level)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	while (rs.next()) {               // Position the cursor                  4 
					      	 lastLevel = rs.getString(6);        // Retrieve the sixth column value
				      	}
				      	
				      	rs.close();                       
				      	pstmt.close();  
				      	
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
		 return lastLevel;
	}
	
	public Double getFinalDegreeGrade(String email) throws SQLException{
		String entryLevel = getEntryLevel(email);
		String lastLevel = getLastLevelOfStudy(email);
		String currentLevelOfStudy = getLevelOfStudy(email);
		Double year2Grade = getWeightedYearGrade(email, String.valueOf(LevelsOfStudy.DIPLOMA));
		Double year3Grade = getWeightedYearGrade(email, String.valueOf(LevelsOfStudy.BACHELORS));
		Double year4Grade = getWeightedYearGrade(email, String.valueOf(LevelsOfStudy.MASTERS));
		Double finalGrade = null;
		if (entryLevel == "undergraduate" && currentLevelOfStudy == lastLevel) {
			if(currentLevelOfStudy == String.valueOf(LevelsOfStudy.MASTERS)) {
				finalGrade = year2Grade * 0.2 + year3Grade * 0.4 * year4Grade * 0.4;
			}
			else if(currentLevelOfStudy == String.valueOf(LevelsOfStudy.BACHELORS)) {
				finalGrade = (year2Grade + year3Grade * 2)/3;
			}
		}
		else if ((entryLevel == "postgraduate" && currentLevelOfStudy == lastLevel)) {
			finalGrade = getWeightedYearGrade(email, currentLevelOfStudy);
		}
		return finalGrade;
	}
	
	public String degreeClassification(String email) throws SQLException{
		//check if they have completed their degree
		double degreeGrade = getFinalDegreeGrade(email);
		String classification = null;
		String lastLevel = getLastLevelOfStudy(email);
		if (lastLevel == String.valueOf(LevelsOfStudy.BACHELORS)) {
			if (degreeGrade >= 69.5) {
				classification = "first class";
			}
			else if (degreeGrade > 59.5 && degreeGrade <= 69.4) {
				classification = "upper second";
			} 
			else if (degreeGrade > 49.5 && degreeGrade <= 59.4) {
				classification = "lower second";
			}
			else if (degreeGrade > 44.5 && degreeGrade <= 49.4) {
				classification = "third class";
			}
			else if (degreeGrade > 39.5 && degreeGrade <= 44.4) {
				classification = "pass (non-honours)";
			}
			else if (degreeGrade < 39.5) {
				classification = "fail";
			}
		}
		else if (lastLevel == String.valueOf(LevelsOfStudy.MASTERS)) {
			if (degreeGrade >= 69.5) {
				classification = "first class";
			}
			else if (degreeGrade > 59.5 && degreeGrade <= 69.4) {
				classification = "upper second";
			}
			else if (degreeGrade > 49.5 && degreeGrade <= 59.4) {
				classification = "lower second";
			}
			else if (degreeGrade < 49.4) {
				classification = "fail";
			}
		}
		else if (lastLevel == String.valueOf(LevelsOfStudy.PG)) {
			if (degreeGrade >= 69.5) {
				classification = "distinction";
			}
			else if (degreeGrade > 59.5 && degreeGrade <= 69.4) {
				classification = "merit";
			}
			else if (degreeGrade > 49.5 && degreeGrade <= 59.4) {
				classification = "pass";
			}
			else if (degreeGrade < 49.4) {
				classification = "fail";
			}
		}
		return classification;
	}
	
	public void specialRule(String email) throws SQLException{
		//check for this
	}
	
	
}