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
 * []Calculate classification from the module
 * []cap re-sit grade at pass grade (1-3 40%, 4-50%) //update this in the database
 * []conceded pass (20 for 1-3, 15 for 4) with no more 10% below pass grade, met the overall average requirement
 * []final degree grade, 3rd:2nd 2:1 ratio
 * []special rules
 */

public class Grades { //create a constructor
	String email;
	static HashMap<String, Integer> gradeForModule;
	
	enum LevelsOfStudy {
		CERTIFICATE("1"),
		DIPLOMA("2"), 
		BACHELORS("3"), 
		MASTERS("4"),
		PG("PG");
		private String action; 
	  
        // getter method 
	    public String getAction() { 
	        return this.action; 
	    } 
	  
	    private LevelsOfStudy(String action) { //constructor 
	        this.action = action; 
	    }
	}
	
	public static String getCurrentLevelOfStudy(String email) throws SQLException{
		String currentlevelOfStudy = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getCurrentLevelOfStudy = String.format("SELECT current_level_of_study FROM year_grade WHERE email = %s" , email);    
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getCurrentLevelOfStudy)){
				      	rs = pstmt.executeQuery();        
				      	currentlevelOfStudy = rs.getString(3);      
				      	
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
		 return currentlevelOfStudy;
	}	
	   
        
	public static List<String> getModuleList(String email, String levelOfStudy) throws SQLException{
		List<String> moduleList = new ArrayList<>();
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getModuleId = String.format("SELECT module_id FROM module_grade INNER JOIN degree ON module_grade.degree_id = degree.degree_id WHERE email = '%s' AND entry_level = '%s' ", email, levelOfStudy);
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
	
	public static String getModuleIdFromName(String moduleName, String levelOfStudy) throws SQLException{
		String moduleId = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getModuleId = String.format("SELECT module_id FROM module WHERE module_name = %s AND level_of_study = %s ", moduleName, levelOfStudy);
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getModuleId)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	while (rs.next()) {               // Position the cursor                  4 
					      	 moduleId = rs.getString(1);        // Retrieve the first column value
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

		 return moduleId; 
	}
	
	public static HashMap<String, Integer> gradesForEachModule(String email, String levelOfStudy) throws SQLException{
		gradeForModule = new HashMap<String, Integer>();
		List<String> listOfModules = getModuleList(email, levelOfStudy);
		for(int i = 0; i < listOfModules.size(); i++) {
			String moduleCode = listOfModules.get(i);
			Connection con = null; 
			 try {
				  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
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
	
	public static HashMap<String, Boolean> passModule(String email, String levelOfStudy) throws SQLException { 
		HashMap<String, Integer> grades_hmap = gradesForEachModule(email, levelOfStudy);
		HashMap<String, Boolean> passOrFail = new HashMap<>();
		for (Map.Entry<String, Integer> entry : grades_hmap.entrySet()) {
	      String moduleId = entry.getKey();
	      Integer grade = entry.getValue();
	      if (grade >= capResitGrade(email)) {
	  			passOrFail.put(moduleId, true);
	  		}
	  		else {
	  			passOrFail.put(moduleId, false);;
	  		}
		}
		return passOrFail;
		
	}

	
	public static String checkForConcededPass(String email, String levelOfStudy) throws SQLException {
		String result = null;
		HashMap<String, Integer> grades = gradesForEachModule(email, levelOfStudy);
		List<Integer> failedModules = new ArrayList<>();
		for(int i = 0; i < grades.size(); i++) {
			if (grades.get(i) <= capResitGrade(email)) {
				failedModules.add(grades.get(i));
			}
		}
		if(failedModules.size() == 0) {
			result = "all modules passed";
		}
		else if (failedModules.size() == 1) {
			if (failedModules.get(0) >= 0.9*capResitGrade(email)) {
				result = "1 module granted conceded pass";	
			}
		}
		else {
			result = "cannot grant conceded pass";
		}
		return result;
	}
	
	
	public static Double capResitGrade(String email) throws SQLException { //update re-sit mark as 40/50 depending on the year
		String levelOfStudy = getCurrentLevelOfStudy(email);
		Double cap = 100.0; //check, fails for passed modules
		if (levelOfStudy ==  String.valueOf(LevelsOfStudy.MASTERS) || levelOfStudy ==  String.valueOf(LevelsOfStudy.PG)) {
  		cap = 49.5;
	  	}
	  	else {
	  		cap = 39.5;
	  	}
		return cap;
	}
	
	
	public static Double getWeightedYearGrade(String email, String levelOfStudy) throws SQLException {
		List<Integer> creditsList = Students.moduleCredits(email, levelOfStudy, getModuleList(email, levelOfStudy));
		List<String> moduleList = getModuleList(email, levelOfStudy);
		HashMap<String, Integer> gradePerModule = gradesForEachModule(email, levelOfStudy);
		Double sum = 0.0;
		Double meanGrade = 0.0;
		Double totalCredits = null;
		for (int i = 0; i < creditsList.size(); i++) {
			totalCredits += creditsList.get(i);
		}
		if (moduleList.size() == creditsList.size()) {
			for(int i = 0; i < moduleList.size(); i++) {
				Integer grade = gradePerModule.get(moduleList.get(i));
				sum += grade * creditsList.get(i); 
			}
			meanGrade = sum/totalCredits;
		}
		
		return meanGrade;
	}
	
	public static Boolean yearPassed(String email, String levelOfStudy) throws SQLException {
		String currentLevelOfStudy = getCurrentLevelOfStudy(email);
		Double yearGrade = getWeightedYearGrade(email, levelOfStudy);
		String cpass = checkForConcededPass(email, levelOfStudy);
		
		if ((currentLevelOfStudy != (String.valueOf(LevelsOfStudy.MASTERS)) || currentLevelOfStudy != String.valueOf(LevelsOfStudy.PG)) 
				 && yearGrade >= 39.5) {
			return true;
		}
		else if ((currentLevelOfStudy != (String.valueOf(LevelsOfStudy.MASTERS)) || currentLevelOfStudy != String.valueOf(LevelsOfStudy.PG)) 
				 && yearGrade < 39.5) {
			if (cpass == "cannot grant conceded pass") {
				return false;
			}
			else {
				return true;
			}
		}
		else if((currentLevelOfStudy == (String.valueOf(LevelsOfStudy.MASTERS)) || currentLevelOfStudy == String.valueOf(LevelsOfStudy.PG)) 
				&& yearGrade >= 49.5) {
			return true;
		}
		else if((currentLevelOfStudy == (String.valueOf(LevelsOfStudy.MASTERS)) || currentLevelOfStudy == String.valueOf(LevelsOfStudy.PG)) 
				&& yearGrade < 49.5) {
			if (cpass == "cannot grant conceded pass") {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return false;
		}
	}
	
	public static String getEntryLevel(String email) throws SQLException {
		String degreeId = Students.getDegreeId(email);
		String entryLevel = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
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
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getLast_level = String.format("SELECT last_level FROM year_grade WHERE email = %s ", email);
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getLast_level)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	while (rs.next()) {               // Position the cursor                  4 
					      	 lastLevel = rs.getString("current_level_of_study");        // Retrieve the sixth column value
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
        
	public static Double getResitYearGrade(String email, String levelOfStudy) throws SQLException {
		Double resitYearGrade = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getResitGrade = String.format("SELECT resit_grade FROM year_grade WHERE email = %s", email) ;
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getResitGrade)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	resitYearGrade = rs.getDouble(7);        // Retrieve the fourth column value
					      
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
		 return resitYearGrade;
	}
        
	public static Double getFinalDegreeGrade(String email) throws SQLException{
		String entryLevel = getEntryLevel(email);
		String lastLevel = getLastLevelOfStudy(email);
		String currentLevelOfStudy = getCurrentLevelOfStudy(email);
		Boolean lastYearPassed = yearPassed(email, currentLevelOfStudy);
		Double year2Grade = getWeightedYearGrade(email, String.valueOf(LevelsOfStudy.DIPLOMA));
		Double year3Grade = getWeightedYearGrade(email, String.valueOf(LevelsOfStudy.BACHELORS));
		Double year4Grade = getWeightedYearGrade(email, String.valueOf(LevelsOfStudy.MASTERS));
		Double finalGrade = null;
		if (entryLevel == "undergraduate" && currentLevelOfStudy == lastLevel) {
			if(currentLevelOfStudy == String.valueOf(LevelsOfStudy.MASTERS) && lastYearPassed) {
				finalGrade = year2Grade * 0.2 + year3Grade * 0.4 * year4Grade * 0.4;
			}
			else if(currentLevelOfStudy == String.valueOf(LevelsOfStudy.MASTERS) && lastYearPassed == false) {
				String degreeDegradedId = degradeDegreeId(email);
				updateDegree(email, degreeDegradedId);
				finalGrade = (year2Grade + year3Grade * 2)/3;
			}
			else if(currentLevelOfStudy == String.valueOf(LevelsOfStudy.BACHELORS) && lastYearPassed) {
				finalGrade = (year2Grade + year3Grade * 2)/3;
			}
			else if(currentLevelOfStudy == String.valueOf(LevelsOfStudy.BACHELORS) && lastYearPassed == false) {
				Double resitGrade = getResitYearGrade(email, currentLevelOfStudy);
				if (resitGrade >= capResitGrade(email)) {
					finalGrade = capResitGrade(email);
				}
			}
		}
		else if ((entryLevel == "postgraduate" && currentLevelOfStudy == lastLevel)) {
			finalGrade = getWeightedYearGrade(email, currentLevelOfStudy);
		}
		return finalGrade;
	}
        
	public static String degradeDegreeId(String email) throws SQLException {
		String currentDegree = Students.getDegreeName(email);
		String degreeName = currentDegree.substring(currentDegree.indexOf(' ') + 1);
		String lastLevel = LevelsOfStudy.BACHELORS.toString();
		String degreeId = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getdegree = String.format("SELECT degree_id FROM degree WHERE degree_name like '%s'AND (last_level = %s OR last_level = PG)", degreeName, lastLevel) ;
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getdegree)){
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	degreeId = rs.getString(1);        // Retrieve the first column value
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
		 return degreeId;
	}
	
	public static void updateDegree(String email, String degreeId) throws SQLException {
		Connection con = null;
        //System.out.println(permission.get(newPermission));
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String preparedStmt = "UPDATE student SET degreeId = ? WHERE email = ?";
            try (PreparedStatement updateStmt = con.prepareStatement(preparedStmt)){
                stmt = con.createStatement();
                updateStmt.setString(1, degreeId);
                updateStmt.setString(2, email);
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
	
	public static String degreeClassification(String email) throws SQLException{
		//check if they have completed their degree
		String degreeName = Students.getDegreeName(email);
		String degreeTitle = degreeName.substring(0, degreeName.indexOf(' '));
		double degreeGrade = getFinalDegreeGrade(email);
		Integer dissertationMarks = getDissertationMarks(email);
		String classification = null;
		String lastLevel = getLastLevelOfStudy(email);
		if (lastLevel == String.valueOf(LevelsOfStudy.BACHELORS)) {
			if (degreeGrade >= 69.5) {
				classification = "first class";
			}
			else if (degreeGrade >= 59.5 && degreeGrade <= 69.4) {
				classification = "upper second";
			} 
			else if (degreeGrade >= 49.5 && degreeGrade <= 59.4) {
				classification = "lower second";
			}
			else if (degreeGrade >= 44.5 && degreeGrade <= 49.4) {
				classification = "third class";
			}
			else if (degreeGrade >= 39.5 && degreeGrade <= 44.4) {
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
			else if (degreeGrade >= 59.5 && degreeGrade <= 69.4) {
				classification = "upper second";
			}
			else if (degreeGrade >= 49.5 && degreeGrade <= 59.4) {
				classification = "lower second";
			}
			else if (degreeGrade < 49.4) {
				classification = "fail";
			}
		}
		else if (lastLevel == String.valueOf(LevelsOfStudy.PG) && dissertationMarks >= 49.5) {
			if (degreeGrade >= 69.5) {
				classification = "distinction";
			}
			else if (degreeGrade >= 59.5 && degreeGrade <= 69.4) {
				classification = "merit";
			}
			else if (degreeGrade >= 49.5 && degreeGrade <= 59.4) {
				classification = "pass";
			}
			else if (degreeGrade < 49.4) {
				classification = "fail";
			}
		}
		else if (lastLevel == String.valueOf(LevelsOfStudy.PG) && dissertationMarks < 49.5) {
			if (degreeGrade >= 49.5) {
				String degradedDegreeId = degradeDegreeId(email);
				updateDegree(email, degradedDegreeId);
				classification = "pass";
			}
		}
		else if (degreeTitle == "PGCert") {
			if (degreeGrade >= 49.5) {
				classification = "pass";
			}
		}
		return classification;
	}
	
	public static Integer getDissertationMarks(String email) throws SQLException {
		String dissertation_module_id = getModuleIdFromName("dissertation", getCurrentLevelOfStudy(email));
		HashMap<String, Integer> gradeForDissertation = gradesForEachModule(email, getCurrentLevelOfStudy(email));
		Integer marks = null;
		for (Map.Entry<String, Integer> entry : gradeForDissertation.entrySet()) {
	      String moduleId = entry.getKey();
	      Integer grade = entry.getValue();
	      if (moduleId == dissertation_module_id) {
	  		marks = grade;
	      }
		}
		return marks;
	}
	
	public static void pgCert(String email) throws SQLException{
		String fullDegreeName = Students.getDegreeName(email);
		String degreeName = fullDegreeName.substring(fullDegreeName.indexOf(' ') + 1);
		String degreeTitle = fullDegreeName.substring(0, fullDegreeName.indexOf(' '));
		String updatedDegreeName = "PGCert " + degreeName;
		String updatedDegreeId = null;
		if (degreeTitle == "MSc") {
			HashMap<String, Boolean> passOrFail = passModule(email, getCurrentLevelOfStudy(email));
			List<String> passedModule = new ArrayList<>();
			List<String> passedTaughtModules = new ArrayList<>();
			
			for (Map.Entry<String, Boolean> entry : passOrFail.entrySet()) {
		      String moduleId = entry.getKey();
		      Boolean isPass = entry.getValue();
		      if (isPass == true) {
		  			passedModule.add(moduleId);
		  	  }
			}
			
			for(int i = 0; i < passedModule.size(); i++) {
				Connection con = null; 
				try {
					con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
				    con.setAutoCommit(false);
				    Statement stmt = null;
				    String getTaughtMod = "SELECT module_id FROM module WHERE module_id = ? AND isTaught = ? " ;
				    
				    ResultSet rs;
		            try (PreparedStatement pstmt = con.prepareStatement(getTaughtMod)){
		            	pstmt.setString(1, passedModule.get(i));
		            	pstmt.setBoolean(2, true);
				      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
				      	passedTaughtModules.add(rs.getString(1));        // Retrieve the fourth column value
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
			
				List<Integer> creditsList = Students.moduleCredits(email, getCurrentLevelOfStudy(email), passedTaughtModules);
				int totalCredits = 0;
				for (int i = 0; i < creditsList.size(); i++) {
					totalCredits += creditsList.get(i);
				}
				if (totalCredits == 60) {
					Connection con = null; 
					 try {
						  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "714e454e");
					      con.setAutoCommit(false);
					      Statement stmt = null;
					      String getdegreeId = "SELECT degree_id FROM degree WHERE degree_name = ?" ;
					      ResultSet rs;
					      try (PreparedStatement pstmt = con.prepareStatement(getdegreeId)){
							      	rs = pstmt.executeQuery();        // Get the result table from the query  3 
							      	updatedDegreeId = rs.getString(1);        // Retrieve the first column value
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
					 updateDegree(email, updatedDegreeId);
					 degreeClassification(email);
					 
				}
		}
		
	}
	
	
	
	
}