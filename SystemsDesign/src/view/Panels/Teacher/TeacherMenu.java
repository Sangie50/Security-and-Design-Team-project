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
import view.Frames.LoginFrame;

/*
 - View Students 
 - Add Grades to modules for students
 - 

*/
public class TeacherMenu extends JPanel {
    
    /**
     * Create the panel.
     * @param contentPane
     * @param username
     * @throws SQLException 
     */
    public TeacherMenu(JPanel contentPane, String username, JFrame mainFrame) throws SQLException {
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        JLabel title = new JLabel("Teacher Page");
        title.setBounds(5, 5, 999, 26);
        title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(title);


        JComboBox<String> emailBox = new JComboBox<>(getEmails(username));
        emailBox.setBounds(81, 291, 277, 45);
        contentPane.add(emailBox);
        contentPane.add(emailBox, BorderLayout.PAGE_START);
        

        JLabel user = new JLabel("Emails of students:");
        user.setBounds(81, 248, 277, 26);
        contentPane.add(user);

        JButton changeGradesPage = new JButton("Edit module grades");
        changeGradesPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel menu = null;
                try {
                    String selectedEmail =  (String) emailBox.getSelectedItem();
                    menu = new changeGradesTeachers(contentPane, username, selectedEmail, mainFrame);
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
                    String selectedEmail =  (String) emailBox.getSelectedItem();
                    menu = new teacherCheck(contentPane, username, selectedEmail, mainFrame);
                    contentPane.add(menu);
                } catch (SQLException ex) {
                    Logger.getLogger(TeacherMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        check.setBounds(608, 320, 307, 35);
        contentPane.add(check);

	
        JButton logoutButton = new JButton("Logout");
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
        logoutButton.setBounds(620, 576, 141, 35);
        contentPane.add(logoutButton); 
        
    }

	
	// Gets emails of students that this teacher teaches only
    public String[] getEmails(String username) throws SQLException{
        ArrayList<String> list = new ArrayList<String>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT student.email FROM student INNER JOIN "
                    + "module_grade ON student.email = module_grade.email INNER"
                    + " JOIN module ON module_grade.module_id = module.module_id"
                    + " INNER JOIN module_teacher ON module.module_id = "
                    + "module_teacher.module_id INNER JOIN teacher ON "
                    + "module_teacher.employee_no = teacher.employee_no WHERE"
                    + " teacher.username = ?";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
                getUsernames.setString(1, username);
                ResultSet usernameList = getUsernames.executeQuery();
                con.commit();
                
                while (usernameList.next()) {
                	list.add(usernameList.getString("email"));	
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
        String[] arr = new String[list.size()];
        arr = list.toArray(arr);
        return arr;
	
    }

}
