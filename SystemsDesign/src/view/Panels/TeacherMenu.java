package view.Panels;

import java.awt.BorderLayout;
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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 - View Students 
 - Add Grades to modules for students
 - 

*/
public class TeacherMenu extends JPanel {
    
    /**
     * Create the panel.
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


        JComboBox<String> usernameBox = new JComboBox<>(getUsernames());
        usernameBox.setBounds(81, 291, 277, 45);
        contentPane.add(usernameBox);
        contentPane.add(usernameBox, BorderLayout.PAGE_START);
        String selectedUser =  (String) usernameBox.getSelectedItem();

        JLabel user = new JLabel("Username:");
        user.setBounds(81, 248, 277, 26);
        contentPane.add(user);

        JButton registerPage = new JButton("Register/ remove student");
        registerPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JPanel registerPanel = null;
                try {
                    registerPanel = new RegisterStudent(contentPane, selectedUser);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                contentPane.add(registerPanel);

            }
        });
        registerPage.setBounds(608, 163, 307, 35);
        contentPane.add(registerPage);

        JButton modulesPage = new JButton("Add/ remove modules");
        modulesPage.setBounds(608, 247, 307, 35);
        contentPane.add(modulesPage);

        JButton checkRegisterPage = new JButton("Check registration");
        checkRegisterPage.setBounds(608, 335, 307, 35);
        contentPane.add(checkRegisterPage);

        JButton checkModulePage = new JButton("Check module sum");
        checkModulePage.setBounds(608, 425, 307, 35);
        contentPane.add(checkModulePage);

        JLabel descLabel = new JLabel("Select a user and click on the buttons to control their fate.");
        descLabel.setBounds(81, 68, 834, 26);
        contentPane.add(descLabel);
	
    }

	
	
    public String[] getUsernames() throws SQLException{
        ArrayList<String> list = new ArrayList<String>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT username FROM user";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
                ResultSet usernameList = getUsernames.executeQuery();
                con.commit();
                
                while (usernameList.next()) {
                	list.add(usernameList.getString("username"));	
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
