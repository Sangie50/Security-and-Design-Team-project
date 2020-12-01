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
    public TeacherMenu(JPanel contentPane, String username) throws SQLException {
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();

        JLabel title = new JLabel("Teacher Page");
        title.setBounds(5, 5, 999, 26);
        title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(title);


        JComboBox<String> emailBox = new JComboBox<>(getEmails());
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
                    menu = new changeGradesTeachers(contentPane, username, selectedEmail);
                    contentPane.add(menu);
                } catch (SQLException ex) {
                    Logger.getLogger(TeacherMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        changeGradesPage.setBounds(608, 163, 307, 35);
        contentPane.add(changeGradesPage);

        JButton modulesPage = new JButton("Add/ remove modules");
        modulesPage.setBounds(608, 247, 307, 35);
        contentPane.add(modulesPage);
	
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
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

	
	
    public String[] getEmails() throws SQLException{
        ArrayList<String> list = new ArrayList<String>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT email FROM student";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
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
