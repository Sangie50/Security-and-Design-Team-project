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
	    
	    public String toString() {
	    	return this.action;
	    }
	}
	
	public static String getDegreeId(String email) throws SQLException {
		String degreeId = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getDegreeId = "SELECT degree_id FROM student WHERE email = ?";       
		      
		      try (PreparedStatement degreeInfo = con.prepareStatement(getDegreeId)){
		          degreeInfo.setString(1, email);
	   	  	      ResultSet rs = degreeInfo.executeQuery(); 
	   	  	      con.commit();
   	  	    
	   	  	      while(rs.next()) {
	   	  	      	degreeId = rs.getString("degree_id");  
	   	  	      }
		      	  rs.close();                       
		      	  degreeInfo.close();   
	      	  
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
	
	public static String getDeptId(String email) throws SQLException {
		String degreeId = getDegreeId(email);
		String deptId = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getDeptId = "SELECT department_id FROM degree WHERE degree_id = ?";    
		      
		      try (PreparedStatement deptInfo = con.prepareStatement(getDeptId)){
		             	  	      
    	  	      deptInfo.setString(1, degreeId);
    	  	      ResultSet deptRs = deptInfo.executeQuery();
    	  	      con.commit();
    	  	      
    	  	      while(deptRs.next()) {
    	  	    	  deptId = deptRs.getString("department_id");
    	  	      }
    	  	      
		      	  deptRs.close();                       
		      	  deptInfo.close();                    
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
		  return deptId;   
	}
	
	public static String getCurrentLevelOfStudy(String email) throws SQLException{
		String currentlevelOfStudy = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getCurrentLevelOfStudy = "SELECT current_level_of_study FROM year_grade WHERE email = ?";    
		      
		      try (PreparedStatement pstmt = con.prepareStatement(getCurrentLevelOfStudy)){
		          pstmt.setString(1, email);
    	  	      ResultSet rs = pstmt.executeQuery(); 
    	  	      con.commit();
    	  	    
    	  	      while(rs.next()) {
    	  	      	currentlevelOfStudy = rs.getString("current_level_of_study");  
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
		  return currentlevelOfStudy;   
				      	
	}	
	
	public static String getPeriodOfStudy(String email) throws SQLException{
		String periodOfStudy = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getCurrentLevelOfStudy = "SELECT period_of_study FROM year_grade WHERE email = ?";    
		      
		      try (PreparedStatement pstmt = con.prepareStatement(getCurrentLevelOfStudy)){
		          pstmt.setString(1, email);
    	  	      ResultSet rs = pstmt.executeQuery(); 
    	  	      con.commit();
    	  	    
    	  	      while(rs.next()) {
    	  	      	periodOfStudy = rs.getString("period_of_study");  
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
		  return periodOfStudy;   
				      	
	}	
	   
    //list of module id for a student for that level of study
	public static List<String> getModuleList(String email, String levelOfStudy) throws SQLException{
		List<String> moduleList = new ArrayList<>();
		Connection con = null; 
		try {
		    con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
   	        con.setAutoCommit(false);
	        Statement stmt = null;
	        String getModuleId = "SELECT module_id FROM module_grade WHERE email = ? AND level_of_study = ?";
	        String moduleId;
	        ResultSet rs;
	        try (PreparedStatement pstmt = con.prepareStatement(getModuleId)){
	        	pstmt.setString(1, email);
	        	pstmt.setString(2, levelOfStudy);
		        rs = pstmt.executeQuery();
		      	con.commit();
	      	
		      	while (rs.next()) {               
			      	 moduleId = rs.getString("module_id");        
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
	
	public static String getModuleIdFromName(String moduleName, String deptId) throws SQLException{
		String moduleId = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getModuleId = "SELECT module_id FROM module WHERE module_name = ? AND department_id = ?";
		      ResultSet rs;
		      try (PreparedStatement pstmt = con.prepareStatement(getModuleId)){
		    	        pstmt.setString(1, moduleName);
		    	        pstmt.setString(2, deptId);
				      	rs = pstmt.executeQuery();
				      	con.commit();
				      	while (rs.next()) {             
					      	 moduleId = rs.getString("module_id");       
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
	
	//takes the initial or resit grade for each module --resit if failed the first attempt
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
			      String getGrades = "SELECT initial_grade, resit_grade FROM module_grade WHERE module_id = ? AND email = ?";    
			      int initialGrade = 0;
			      int resitGrade = 0;
			      
			      try( PreparedStatement gradesInfo = con.prepareStatement(getGrades)){
		    	      gradesInfo.setString(1, moduleCode);
		    	      gradesInfo.setString(2, email);
			    	  ResultSet rs = gradesInfo.executeQuery(); 
			    	  con.commit();
			    	  
			    	  while (rs.next()) {
			    		  initialGrade = rs.getInt("initial_grade");        
					      resitGrade = rs.getInt("resit_grade");
			    	  }
			    	  
				      if (initialGrade >= 40) {
			      		gradeForModule.put(moduleCode, initialGrade);
				      }
				      else {
			      		gradeForModule.put(moduleCode, resitGrade);
				      }
				      rs.close();                       
				      gradesInfo.close();                    
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
	
	//returns true in the boolean value if that module is passed
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

	//checks if the students manages to pass the year with conceded pass
	public static String checkForConcededPass(String email, String levelOfStudy) throws SQLException {
		String result = "testing";
		HashMap<String, Integer> grades = gradesForEachModule(email, levelOfStudy);
		
		List<Integer> failedModules = new ArrayList<>();
		for(Map.Entry<String, Integer> entry : grades.entrySet()) {
		    String key = entry.getKey();
		    Integer val = entry.getValue();
			if (val <= capResitGrade(email)) {
				failedModules.add(val);
			}
		}
		
		if(failedModules.size() == 0) {
			return result = "all modules passed";
		}
		else if (failedModules.size() == 1) {
			if (failedModules.get(0) >= 0.9*capResitGrade(email)) {
				return result = "1 module granted conceded pass";	
			}
			else {
				return result = "cannot grant conceded pass";
			}
		}
		else {
			return result = "cannot grant conceded pass";
		}
	}
	
	//sets pass mark according to the level of study of the student
	public static Double capResitGrade(String email) throws SQLException { //update re-sit mark as 40/50 depending on the year
		String levelOfStudy = getCurrentLevelOfStudy(email);
		Double cap = 100.0; //to check, fails for passed modules
		
		if (levelOfStudy.equals(LevelsOfStudy.MASTERS.toString())  || levelOfStudy.equals(LevelsOfStudy.PG.toString())) {
			cap = 49.5;
	  	}
	  	else {
	  		cap = 39.5;
	  	}
		return cap;
	}
	
	//weighted year grade for a student
	public static Double getWeightedYearGrade(String email, String levelOfStudy) throws SQLException {
		List<Integer> creditsList = Students.moduleCredits(email, levelOfStudy, getModuleList(email, levelOfStudy));
		List<String> moduleList = getModuleList(email, levelOfStudy);
		HashMap<String, Integer> gradePerModule = gradesForEachModule(email, levelOfStudy);
		Double sum = 0.0;
		Double meanGrade = 0.0;
		Integer totalCredits = 0;
		for (int i = 0; i < creditsList.size(); i++) {
			totalCredits += creditsList.get(i);
		}
		if (moduleList.size() == creditsList.size()) {
			for(int i = 0; i < moduleList.size(); i++) {
				Integer grade = gradePerModule.get(moduleList.get(i));
				sum += grade * creditsList.get(i); 
			}
			meanGrade = sum/totalCredits;
			meanGrade = Math.floor(meanGrade * 10) / 10; //rounding to 1 d.p
		}
		
		return meanGrade;
	}
	
	//returns true if they passed overall year by passing every module
	public static Boolean yearPassed(String email, String levelOfStudy) throws SQLException {
		String currentLevelOfStudy = getCurrentLevelOfStudy(email);
		Double yearGrade = getWeightedYearGrade(email, levelOfStudy);
		String cpass = checkForConcededPass(email, levelOfStudy);
		
		//if they are bachelors and overall have a pass mark
		if ((currentLevelOfStudy != (String.valueOf(LevelsOfStudy.MASTERS)) || currentLevelOfStudy != String.valueOf(LevelsOfStudy.PG)) 
				 && yearGrade >= 39.5) {
			//to check if they passed each module, despite "passing" the overall year
			if (cpass == "cannot grant conceded pass") {
				return false;
			}
			else {
				return true;
			}
		}
		//if bachelors didn't pass the overall year
		else if ((currentLevelOfStudy != (String.valueOf(LevelsOfStudy.MASTERS)) || currentLevelOfStudy != String.valueOf(LevelsOfStudy.PG)) 
				 && yearGrade < 39.5) {
			return false;
		}
		//if masters passed the overall year
		else if((currentLevelOfStudy == (String.valueOf(LevelsOfStudy.MASTERS)) || currentLevelOfStudy == String.valueOf(LevelsOfStudy.PG)) 
				&& yearGrade >= 49.5) {
			//check if they passed each module
			if (cpass == "cannot grant conceded pass") {
				return false;
			}
			else {
				return true;
			}
		}
		//if masters failed overall year
		else if((currentLevelOfStudy == (String.valueOf(LevelsOfStudy.MASTERS)) || currentLevelOfStudy == String.valueOf(LevelsOfStudy.PG)) 
				&& yearGrade < 49.5) {
			return false;
		}
		else {
			return false;
		}
	}
	
	public static String getEntryLevel(String email) throws SQLException {
		String degreeId = getDegreeId(email);
		String entryLevel = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getEntryLevel = "SELECT entry_level FROM degree WHERE degree_id = ?";    
		      
		      try (PreparedStatement pstmt = con.prepareStatement(getEntryLevel)){
		          pstmt.setString(1, degreeId);
    	  	      ResultSet rs = pstmt.executeQuery(); 
    	  	      con.commit();
    	  	    
    	  	      while(rs.next()) {
    	  	      	entryLevel = rs.getString("entry_level");  
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
		String degreeId = getDegreeId(email);
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getLast_level = "SELECT last_level FROM degree WHERE degree_id = ?";
		      
		      try (PreparedStatement pstmt = con.prepareStatement(getLast_level)){
		    	  pstmt.setString(1, degreeId);
		    	  ResultSet rs = pstmt.executeQuery();  
		    	  con.commit();
		    	  
		          while (rs.next()) {               
			          lastLevel = rs.getString("last_level");
				      	
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
        
	public static int getResitYearGrade(String email, String levelOfStudy) throws SQLException {
		int resitYearGrade = 0;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getResitGrade = "SELECT resit_grade FROM year_grade WHERE email = ?";
		      
		      try (PreparedStatement pstmt = con.prepareStatement(getResitGrade)){
		    	  pstmt.setString(1, email);
		    	  ResultSet rs = pstmt.executeQuery(); 
		    	  con.commit();
		    	  while(rs.next()) {
		    		  resitYearGrade = rs.getInt("resit_grade");     
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
		Double finalGrade = 78.0;
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
				int resitGrade = getResitYearGrade(email, currentLevelOfStudy);
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
        
	//when a student fails in their last year and they are awarded the same degree with lower difficulty  --need enough data to test this!!!
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
		      String getdegree = "SELECT degree_id FROM degree WHERE degree_name like ? AND (last_level = ? OR last_level = PG)";
		      
		      try (PreparedStatement pstmt = con.prepareStatement(getdegree)){
		    	    pstmt.setString(1, degreeName);
		    	    pstmt.setString(2, email);
		    	    ResultSet rs = pstmt.executeQuery();   
		    	    con.commit();
		    	    
		    	    while(rs.next()) {
		    	    	degreeId = rs.getString("degree_id");  
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
		String classification = null;
		
		try {
			String degreeName = Students.getDegreeName(email);
			String degreeTitle = degreeName.substring(0, degreeName.indexOf(' '));
			double degreeGrade = getFinalDegreeGrade(email);
			Integer dissertationMarks = getDissertationMarks(email);
			
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
		}
		catch (SQLException e){
			System.err.println("Not enough student data to display info.");
		}
		
		return classification;
	}
	
	public static Integer getDissertationMarks(String email) throws SQLException {
		String dissertation_module_id = getModuleIdFromName("dissertation", getDeptId(email));
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
	
	//when a postgraduate fails and is awarded PGCert instead of MSc
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
				      	rs = pstmt.executeQuery();   
				      	con.commit();
				      	
				      	while(rs.next()) {
				      		passedTaughtModules.add(rs.getString("module_id")); 
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
					    	    pstmt.setString(1, degreeName);
						      	rs = pstmt.executeQuery();         
						      	updatedDegreeId = rs.getString("degree_id");
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
					 updateDegree(email, updatedDegreeId);
					 degreeClassification(email);
					 
				}
		}
		
	}
	
	public static String getStudentDifficulty(String email) throws SQLException {
		Connection con = null;
		String difficulty = "";
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String diff = "SELECT difficulty FROM student WHERE email = ?";
            try (PreparedStatement getDiff = con.prepareStatement(diff)){
                getDiff.setString(1, email);
                ResultSet difficult = getDiff.executeQuery();
                con.commit();
                
                while(difficult.next()) {
                    difficulty = difficult.getString("difficulty");
                }
          
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
        return difficulty.substring(0,1);
	}

	public static boolean checkRepeatYear(String email, String levelOfStudy) throws SQLException {
		Connection con = null;
		boolean hasRepeated = false;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String resit = "SELECT resit_grade FROM year_grade WHERE email =? AND"
            		+ " level_of_study = ?";
            try (PreparedStatement getResit = con.prepareStatement(resit)){
            	getResit.setString(1, email);
            	getResit.setString(2, levelOfStudy);
                ResultSet grade = getResit.executeQuery();
                con.commit();
                
                while(grade.next()) {
                	try {
                		grade.getString("resit_grade");
                		hasRepeated = true;
                	}
                	catch (SQLException e) {
                		System.err.println("No value in resit_grade of year_grade");
                	}
                    
                }
          
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
        return hasRepeated;
	}
}