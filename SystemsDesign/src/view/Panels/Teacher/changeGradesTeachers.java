package view.Panels.Teacher;

import java.awt.BorderLayout;
import view.Panels.Teacher.TeacherMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JTextField;

import view.Frames.LoginFrame;

import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import userclasses.Teachers;

public class changeGradesTeachers extends JPanel {
//    private JTable table;
//    private JTextField degreeId;
//    private JTextField personalTutor;
//    private String username;

    /**
     * Create the panel.
 * @param username
 * @param studentEmail
     * @throws SQLException 
     */
    public changeGradesTeachers(JPanel panel, String username, String studentEmail, JFrame mainFrame) throws SQLException {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();

        setLayout(null);
        setBounds(100, 100, 1035, 700);
        String type = "";

        Connection con = null;
        Statement stmt = null;
        
        String degreeID ="";
        Integer credits = 0;
        String tutor = "";
        Integer initialGrade = 0;
        Integer resitGrade = 0;
        String moduleName = "";
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);

            String accType = "SELECT * FROM student INNER JOIN module_grade ON student.email = module_grade.email INNER JOIN module ON module_grade.module_id = module.module_id WHERE student.email = ?";
            try (PreparedStatement checkAccType = con.prepareStatement(accType)){
                checkAccType.setString(1, studentEmail);
                ResultSet rs = checkAccType.executeQuery();
                con.commit();
                while (rs.next()) {
                    degreeID = rs.getString("degree_id");
                    credits = rs.getInt("total_credits");
                    tutor = rs.getString("personal_tutor");
                    //moduleName = rs.getString("module_name");
                    //initialGrade = rs.getInt("initial_grade");
                    //resitGrade = rs.getInt("resit_grade");
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


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(221,5,2,2);
        panel.add(scrollPane);

        JLabel infoLabel = new JLabel("Student Information");
        infoLabel.setFont(new Font("Tahoma", Font.BOLD, 21));
        infoLabel.setBounds(30, 40, 300, 26);
        panel.add(infoLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(21, 80, 202, 26);
        panel.add(emailLabel);

        JLabel idLabel = new JLabel("Degree ID:");
        idLabel.setBounds(21, 120, 148, 26);
        panel.add(idLabel);
        
        JLabel cred = new JLabel("Credits:");
        cred.setBounds(21, 160, 202, 26);
        panel.add(cred);

        JLabel tut = new JLabel("Tutor:");
        tut.setBounds(21, 200, 148, 26);
        panel.add(tut);
        
        JLabel ini = new JLabel("Initial Grade:");
        ini.setBounds(21, 240, 148, 26);
        panel.add(ini);
        
        JLabel res = new JLabel("Resit Grade:");
        res.setBounds(21, 280, 148, 26);
        panel.add(res);
        
        JLabel na = new JLabel("Module Name:");
        na.setBounds(21, 320, 148, 26);
        panel.add(na); 
        

        JLabel email = new JLabel(studentEmail);
        email.setBounds(221, 80, 156, 26);
        panel.add(email);

        JLabel degreeid = new JLabel(degreeID);
        degreeid.setBounds(221, 120, 156, 26);
        panel.add(degreeid);
        
        JLabel credi = new JLabel("'"+credits+"'");
        credi.setBounds(221, 160, 156, 26);
        panel.add(credi);

        JLabel tuto = new JLabel(tutor);
        tuto.setBounds(221, 200, 156, 26);
        panel.add(tuto);
        
        JLabel in = new JLabel(""+initialGrade+"");
        in.setBounds(221, 240, 156, 26);
        panel.add(in);
        
        JLabel re = new JLabel(""+resitGrade+"");
        re.setBounds(221, 280, 156, 26);
        panel.add(re);
        
        JLabel nam = new JLabel(moduleName);
        nam.setBounds(221, 320, 450, 26);
        panel.add(nam);
        
        
        JTextField inGrade = new JTextField();
        inGrade.setBounds(608, 200, 307, 35);
        add(inGrade);
        panel.add(inGrade);
        inGrade.setColumns(10);
        
        JTextField reGrade = new JTextField();
        reGrade.setBounds(608, 320, 307, 35);
        add(reGrade);
        panel.add(reGrade);
        reGrade.setColumns(10);

        JLabel user = new JLabel("Student's Modules You Teach:");
        user.setBounds(81, 360, 277, 26);
        panel.add(user);
        
        JComboBox<String> gradesBox = new JComboBox<>(getModules(studentEmail,username));
        gradesBox.setBounds(81, 400, 277, 45);
        panel.add(gradesBox);
        panel.add(gradesBox, BorderLayout.PAGE_START);
        
        JButton addInitial = new JButton("Modify Initial Grade");
        addInitial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        String selectedModule =  (String) gradesBox.getSelectedItem();
                        String i = inGrade.getText().trim();
 
                        Integer inGrade = Integer.parseInt(i);
                        try {
                            System.out.println(inGrade);
                            Teachers.updateGrades(selectedModule, studentEmail, inGrade);
                        } catch (SQLException ex) {
                            Logger.getLogger(changeGradesTeachers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //View the changes
                        ArrayList list = null;
                        try {
                            list = getGrades(studentEmail, selectedModule);
                        } catch (SQLException ex) {
                            Logger.getLogger(changeGradesTeachers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Integer initialGrade = (int)list.get(0);
                        Integer resitGrade = (int)list.get(1);
                        String moduleName = (String)list.get(2);
                        in.setText(""+initialGrade+"");
                        re.setText(""+resitGrade+"");
                        nam.setText(moduleName);
                    }
                });
            }
        });
        addInitial.setBounds(608, 240, 307, 35);
        panel.add(addInitial);
        
        JButton addResit = new JButton("Modify Resit Grades");
        addResit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        String selectedModule =  (String) gradesBox.getSelectedItem();
                        String r = reGrade.getText().trim();
                        if("".equals(r)){
                            r = "0";
                        }
                        Integer reGrade = Integer.parseInt(r);

                        try {
                            Teachers.addResitGrades(selectedModule, studentEmail, reGrade);
                        } catch (SQLException ex) {
                            Logger.getLogger(changeGradesTeachers.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        //View the changes
                        ArrayList list = null;
                        try {
                            list = getGrades(studentEmail, selectedModule);
                        } catch (SQLException ex) {
                            Logger.getLogger(changeGradesTeachers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        Integer initialGrade = (int)list.get(0);
                        Integer resitGrade = (int)list.get(1);
                        String moduleName = (String)list.get(2);
                        in.setText(""+initialGrade+"");
                        re.setText(""+resitGrade+"");
                        nam.setText(moduleName);
                    }
                });
            }
        });
        addResit.setBounds(608, 360, 307, 35);
        panel.add(addResit);
        
        JButton viewGrades = new JButton("View Grades");
        viewGrades.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String selectedModule =  (String) gradesBox.getSelectedItem();
                    ArrayList list = new ArrayList();
                    list = getGrades(studentEmail, selectedModule);
                    Integer initialGrade = (int)list.get(0);
                    Integer resitGrade = (int)list.get(1);
                    String moduleName = (String)list.get(2);
                    in.setText(""+initialGrade+"");
                    re.setText(""+resitGrade+"");
                    nam.setText(moduleName);
                } catch (SQLException ex) {
                    Logger.getLogger(changeGradesTeachers.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        viewGrades.setBounds(100, 480, 150, 26);
        panel.add(viewGrades);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            
                            JPanel menu = new TeacherMenu(panel, username, mainFrame);
                            panel.add(menu);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        backButton.setBounds(620, 50, 141, 35);
        panel.add(backButton);
        
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
        panel.add(logoutButton); 
        

	}
    public String[] getModules(String studentEmail, String username) throws SQLException{
        ArrayList<String> list = new ArrayList<String>();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT * FROM student INNER JOIN "
                    + "module_grade ON student.email = module_grade.email INNER"
                    + " JOIN module ON module_grade.module_id = module.module_id"
                    + " INNER JOIN module_teacher ON module.module_id = "
                    + "module_teacher.module_id INNER JOIN teacher ON "
                    + "module_teacher.employee_no = teacher.employee_no WHERE"
                    + " teacher.username = ? AND student.email = ?";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
                getUsernames.setString(1, username);
                getUsernames.setString(2, studentEmail);
                ResultSet usernameList = getUsernames.executeQuery();
                con.commit();
                
                while (usernameList.next()) {
                	list.add(usernameList.getString("module_id"));	
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
    public ArrayList getGrades(String studentEmail, String moduleID) throws SQLException{
        ArrayList list = new ArrayList();
        Connection con = null;
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team028", "team028", "7f4e454e");
            con.setAutoCommit(false);
            Statement stmt = null;
            String usernames = "SELECT * FROM student INNER JOIN "
                    + "module_grade ON student.email = module_grade.email INNER"
                    + " JOIN module ON module_grade.module_id = module.module_id"
                    + " WHERE module.module_id = ? AND student.email = ?";
            try (PreparedStatement getUsernames = con.prepareStatement(usernames)){
                getUsernames.setString(1, moduleID);
                getUsernames.setString(2, studentEmail);
                ResultSet usernameList = getUsernames.executeQuery();
                con.commit();
                
                usernameList.next(); 
                list.add(usernameList.getInt("initial_grade"));
                list.add(usernameList.getInt("resit_grade"));
                list.add(usernameList.getString("module_name"));
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
        return list;
	
    }

}
