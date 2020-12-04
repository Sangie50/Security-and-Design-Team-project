package view.Panels.Admin;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import userclasses.Admins;
import view.Panels.AbstractPanel;

public class RemoveDeptPanel extends AbstractPanel {
	String[] options;
	/**
	 * Create the panel.
	 */
	public RemoveDeptPanel(JPanel panel, JFrame mainFrame) {
		panel.removeAll();
		panel.revalidate();
		panel.repaint();
		setLayout(null);
		setBounds(100, 100, 1035, 647);
		
		//DEFAULT
		UIManager.put("Label.font", LABEL_FONT);
		UIManager.put("Table.font", TABLE_FONT);
		UIManager.put("TableHeader.font", HEADER_FONT);
		UIManager.put("Button.font", TABLE_FONT);
		UIManager.put("ComboBox.font", LABEL_FONT);
		
		JLabel heading = new JLabel("Remove Department");
		heading.setBounds(52, 58, 300, 20);
		add(heading);
		panel.add(heading);
		
		JLabel deptId = new JLabel("Department ID");
		deptId.setBounds(52, 150, 100, 20);
		add(deptId);
		panel.add(deptId);
		
		JLabel instruction = new JLabel("Select the department you wish to remove");
		instruction.setBounds(52, 105, 326, 20);
		add(instruction);
		panel.add(instruction);
		
		try {
			options = getDeptId();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JComboBox<String> deptIdComboBox = new JComboBox<>(options);
		deptIdComboBox.setBounds(196, 147, 160, 26);
		add(deptIdComboBox);
		panel.add(deptIdComboBox);
		
		JButton removeButton = new JButton("Remove");
		removeButton.setBounds(516, 377, 115, 29);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String deptIdSelected = deptIdComboBox.getSelectedItem().toString();
				
				try {
					Boolean isLinkedToDegree = checkDeptLinkedToDegree(deptIdSelected);
					Boolean isLinkedToModule = checkDeptLinkedToModule(deptIdSelected);
					if (!(isLinkedToDegree && isLinkedToModule)) {
						int dialogButton = JOptionPane.YES_NO_OPTION;
						int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to remove?","Warning",dialogButton);
						if(dialogResult == JOptionPane.YES_OPTION){
						    Admins.removeDepartment(deptIdSelected);
						    JPanel menu = new AdminMenu(panel, mainFrame);
							AdminMenu.mainComboBox.setSelectedIndex(0);
							System.out.println("department removed");
							panel.add(menu);
						}
						else if (dialogResult == JOptionPane.NO_OPTION){
							JPanel backRemoveDept = new RemoveDeptPanel(panel, mainFrame);
							deptIdComboBox.setSelectedIndex(deptIdComboBox.getSelectedIndex());
							System.out.println("department not removed");
						}
					}
					else if (isLinkedToDegree || isLinkedToModule) {
						JOptionPane.showMessageDialog(mainFrame.getComponent(0), "Department cannot be removed. It is linked to existing degrees or modules. "
								+ "Please delete linked degrees or modules before deleting this department");
						JPanel backRemoveDept = new RemoveDeptPanel(panel, mainFrame);
						deptIdComboBox.setSelectedIndex(deptIdComboBox.getSelectedIndex());
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		add(removeButton);
		panel.add(removeButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(52, 377, 115, 29);
		add(cancelButton);
		panel.add(cancelButton);
	}
	
	public static Boolean checkDeptLinkedToDegree(String deptId) throws SQLException {
		Boolean isLinked;
		String departmentId = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getDeptId = "SELECT department_id FROM degree WHERE department_id = ?";    
		      
		      try (PreparedStatement deptInfo = con.prepareStatement(getDeptId)){
		             	  	      
    	  	      deptInfo.setString(1, deptId);
    	  	      ResultSet deptRs = deptInfo.executeQuery();
    	  	      con.commit();
    	  	      
    	  	      while(deptRs.next()) {
    	  	    	  departmentId = deptRs.getString("department_id");
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
		 
		  if (departmentId != null) {
			  isLinked = true;
		  }
		  else {
			  isLinked = false;
		  }
		  
		  return isLinked;
	}
	
	public static Boolean checkDeptLinkedToModule(String deptId) throws SQLException {
		Boolean isLinked;
		String departmentId = null;
		Connection con = null; 
		 try {
			  con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
		      con.setAutoCommit(false);
		      Statement stmt = null;
		      String getDeptId = "SELECT department_id FROM module WHERE department_id = ?";    
		      
		      try (PreparedStatement deptInfo = con.prepareStatement(getDeptId)){
		             	  	      
    	  	      deptInfo.setString(1, deptId);
    	  	      ResultSet deptRs = deptInfo.executeQuery();
    	  	      con.commit();
    	  	      
    	  	      while(deptRs.next()) {
    	  	    	  departmentId = deptRs.getString("department_id");
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
		 
		  if (departmentId != null) {
			  isLinked = true;
		  }
		  else {
			  isLinked = false;
		  }
		  
		  return isLinked;
	}

}
