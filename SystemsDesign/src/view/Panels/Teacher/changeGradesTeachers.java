package view.Panels.Teacher;

import java.awt.BorderLayout;

import view.Panels.AbstractPanel;
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
import javax.swing.UIManager;

import view.Frames.LoginFrame;

import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

import userclasses.Students;
import userclasses.Teachers;

public class changeGradesTeachers extends AbstractPanel {
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
    public changeGradesTeachers(JPanel panel, String username, String studentEmail, JFrame mainFrame, Teachers teacher) throws SQLException {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();

        setLayout(null);
        setBounds(100, 100, 1035, 700);
        
        //DEFAULT
      	UIManager.put("Label.font", LABEL_FONT);
      	UIManager.put("Table.font", TABLE_FONT);
      	UIManager.put("TableHeader.font", HEADER_FONT);
      	UIManager.put("Button.font", TABLE_FONT);
      	UIManager.put("ComboBox.font", LABEL_FONT);
      		
      	//settings
        Students student = getStudent(studentEmail);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(221,5,2,2);
        panel.add(scrollPane);

        
        //label
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
        
        JLabel creditsLabel = new JLabel("Credits:");
        creditsLabel.setBounds(21, 160, 202, 26);
        panel.add(creditsLabel);

        JLabel tutorLabel = new JLabel("Tutor:");
        tutorLabel.setBounds(21, 200, 148, 26);
        panel.add(tutorLabel);
        
        JLabel initialGradeLabel = new JLabel("Initial Grade:");
        initialGradeLabel.setBounds(21, 240, 148, 26);
        panel.add(initialGradeLabel);
        
        JLabel resitGradeLabel = new JLabel("Resit Grade:");
        resitGradeLabel.setBounds(21, 280, 148, 26);
        panel.add(resitGradeLabel);
        
        JLabel moduleNameLabel = new JLabel("Module Name:");
        moduleNameLabel.setBounds(21, 320, 148, 26);
        panel.add(moduleNameLabel); 
        

        JLabel email = new JLabel(student.getEmail());
        email.setBounds(221, 80, 156, 26);
        panel.add(email);

        JLabel degreeId = new JLabel(student.getDegreeId());
        degreeId.setBounds(221, 120, 156, 26);
        panel.add(degreeId);
        
        JLabel creditsText = new JLabel(Integer.toString(student.getTotalCredits()));
        creditsText.setBounds(221, 160, 156, 26);
        panel.add(creditsText);

        JLabel tutorText = new JLabel(student.getPersonalTutor());
        tutorText.setBounds(221, 200, 156, 26);
        panel.add(tutorText);
        
        JLabel initialGradeText = new JLabel("");
        initialGradeText.setBounds(221, 240, 156, 26);
        panel.add(initialGradeText);
        
        JLabel resitGradeText = new JLabel("");
        resitGradeText.setBounds(221, 280, 156, 26);
        panel.add(resitGradeText);
        
        JLabel moduleNameText = new JLabel(degreeCode.get(student.getDegreeId()));
        moduleNameText.setBounds(221, 320, 450, 26);
        panel.add(moduleNameText);
        
        JLabel user = new JLabel("Student's Modules You Teach:");
        user.setBounds(81, 360, 277, 26);
        panel.add(user);
        //---------------------------------
        
        
        //text field
        JTextField inGrade = new JTextField();
        inGrade.setBounds(608, 200, 307, 35);
        panel.add(inGrade);
        inGrade.setColumns(10);
        
        JTextField reGrade = new JTextField();
        reGrade.setBounds(608, 320, 307, 35);
        panel.add(reGrade);
        reGrade.setColumns(10);
        //----------------------
        
        
        //comboBox
        JComboBox<String> gradesBox = new JComboBox<>(teacher.getModules(student.getEmail(), teacher.getUsername()));
        gradesBox.setBounds(81, 400, 277, 45);
        panel.add(gradesBox, BorderLayout.PAGE_START);
        //-----------------------
        
        
        //buttons
        JButton addInitial = new JButton("Modify Initial Grade");
        addInitial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        String selectedModule =  (String) gradesBox.getSelectedItem();
                        try {
                        	teacher.updateGrades(selectedModule, student.getEmail(), Integer.parseInt(inGrade.getText()));
                        	ArrayList<String> list = new ArrayList<String>();
                            list = teacher.getGrades(student.getEmail(), selectedModule);
                            initialGradeText.setText(list.get(0));
                            resitGradeText.setText(list.get(1));
                            moduleNameText.setText(list.get(2));
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        
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
                    	String selectedModule = (String) gradesBox.getSelectedItem();
                    	try {
							teacher.addResitGrades(selectedModule, student.getEmail(), Integer.parseInt(reGrade.getText()));
							ArrayList<String> list = new ArrayList<String>();
							list = teacher.getGrades(student.getEmail(), selectedModule);
							initialGradeText.setText(list.get(0));
							resitGradeText.setText(list.get(1));
							moduleNameText.setText(list.get(2));
                    	} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                });
            }
        });
        addResit.setBounds(608, 360, 307, 35);
        panel.add(addResit);
        

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
        //--------------------------------

	}
    
   

}
