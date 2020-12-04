package view.Panels.Teacher;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import userclasses.Teachers;
import userclasses.Users.UserTypes;
import view.Frames.LoginFrame;
import view.Panels.AbstractPanel;

/*
 - View Students 
 - Add Grades to modules for students
 - 

*/
public class TeacherMenu extends AbstractPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 412341325L;


	/**
     * Create the panel.
     * @param contentPane
     * @param teacherUsername
     * @throws SQLException 
     */
    public TeacherMenu(JPanel contentPane, String teacherUsername, JFrame mainFrame) throws SQLException {
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
        
        Teachers teacher = getTeacher(teacherUsername);

        //DEFAULT
		UIManager.put("Label.font", LABEL_FONT);
		UIManager.put("Table.font", TABLE_FONT);
		UIManager.put("TableHeader.font", HEADER_FONT);
		UIManager.put("Button.font", TABLE_FONT);
		UIManager.put("ComboBox.font", LABEL_FONT);
      		
        //labels 
        JLabel title = new JLabel("Teacher Page");
        title.setBounds(5, 5, 999, 26);
        title.setFont(TITLE_FONT);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(title);

        JLabel user = new JLabel("Emails of students:");
        user.setBounds(81, 248, 277, 26);
        contentPane.add(user);
        //---------------------------------
        
        
        //Combo boxes
        JComboBox<String> usernameBox = new JComboBox<>(getUsernames(UserTypes.STUDENT.toString()));
        usernameBox.setBounds(81, 291, 277, 45);
        contentPane.add(usernameBox);
        contentPane.add(usernameBox, BorderLayout.PAGE_START);
        //---------------------------------
        
        
        //buttons      
        JButton changeGradesPage = new JButton("Edit module grades");
        changeGradesPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel menu = null;
                try {
                    String selectedUsername =  (String) usernameBox.getSelectedItem();
                    menu = new changeGradesTeachers(contentPane, teacherUsername, selectedUsername, mainFrame, teacher);
                    contentPane.add(menu);
                } catch (SQLException ex) {
                    Logger.getLogger(TeacherMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        changeGradesPage.setBounds(608, 277, 307, 35);
        contentPane.add(changeGradesPage);
        
        JButton check = new JButton("Check Student Degree");
        check.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel menu = null;
                try {
                    String selectedUsername =  (String) usernameBox.getSelectedItem();
                    menu = new teacherCheck(contentPane, teacherUsername, selectedUsername, mainFrame, teacher);
                    contentPane.add(menu);
                } catch (SQLException ex) {
                    Logger.getLogger(TeacherMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        check.setBounds(608, 320, 307, 35);
        contentPane.add(check);

	
        JButton logoutButton = new JButton("Logout");
        contentPane.add(logoutButton);
        logoutButton.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		setVisible(false);
	    		EventQueue.invokeLater(new Runnable() {
					public void run() {
						try {
							mainFrame.setVisible(false);
							LoginFrame frame = new LoginFrame();
							frame.setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	    	}
	    });
	    		
        JButton progressStudent = new JButton("Progress Student");
        progressStudent.setBounds(608, 363, 307, 35);
        contentPane.add(progressStudent);
        progressStudent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                    	JPanel progress = null;
                        try {
                            String selectedUsername =  (String) usernameBox.getSelectedItem();
                            progress = new WeightedMeanGrade(contentPane, selectedUsername, mainFrame, teacher);
                            contentPane.add(progress);
                        } catch (SQLException ex) {
                            Logger.getLogger(TeacherMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
            }
        });
        
    }
    //-----------------------------------------


}
