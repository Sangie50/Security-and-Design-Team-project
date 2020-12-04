package view.Panels.Teacher;

import academics.Grades;
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
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import userclasses.Students;
import userclasses.Teachers;

public class teacherCheck extends AbstractPanel {
    private JTable table;
    private DefaultTableModel model = new DefaultTableModel();;
	private static JScrollPane scroll;

    /**
     * Create the panel.
 * @param username
 * @param studentEmail
     * @throws SQLException 
     */
    public teacherCheck(JPanel panel, String username, String studentEmail, JFrame mainFrame, Teachers teacher) throws SQLException {
        panel.removeAll();
        panel.revalidate();
        panel.repaint();

        setLayout(null);
        setBounds(100, 100, 1035, 700);
        Students student = getStudent(studentEmail);

      //DEFAULT
      		UIManager.put("Label.font", LABEL_FONT);
      		UIManager.put("Table.font", TABLE_FONT);
      		UIManager.put("TableHeader.font", HEADER_FONT);
      		UIManager.put("Button.font", TABLE_FONT);
      		UIManager.put("ComboBox.font", LABEL_FONT);
      		
      	//labels
        
        JLabel title = new JLabel("Teacher Page - " + student.getEmail());
        title.setBounds(5, 5, 999, 26);
        title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(title);
        
        JLabel creditLabel = new JLabel("Credits:");
        creditLabel.setBounds(81, 40, 277, 26);
        panel.add(creditLabel);
        JLabel creditText = new JLabel(Integer.toString(student.getTotalCredits()));
        creditText.setBounds(81, 70, 277, 26);
        panel.add(creditText);
        
        JLabel tutorLabel = new JLabel("Tutor:");
        tutorLabel.setBounds(81, 110, 277, 26);
        panel.add(tutorLabel);
        JLabel tutorText = new JLabel(student.getPersonalTutor());
        tutorText.setBounds(81, 140, 277, 26);
        panel.add(tutorText);
        
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
        
        JLabel resitLabel = new JLabel("Resit Year:");
        resitLabel.setBounds(81, 180, 277, 26);
        panel.add(resitLabel);
        
        JLabel resitText = new JLabel(Boolean.toString(student.getResitYear()));
        resitText.setBounds(81, 210, 277, 26);
        panel.add(resitText);
        
        JLabel passYearLabel = new JLabel("Pass Year:");
        passYearLabel.setBounds(81, 250, 277, 26);
        panel.add(passYearLabel);
        
        Boolean pass = teacher.getPassYear(student.getEmail());
        JLabel passYearText = new JLabel(String.valueOf(pass));
        passYearText.setBounds(81, 280, 277, 26);
        panel.add(passYearText);
        //----------------------------
        
        
        //buttons
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
        //--------------------------

        
        //table
        table = new JTable(model);
        table.setBounds(81, 285, 540, 200);
        table.setBackground(UIManager.getColor("Button.background"));
        table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        panel.add(table);
        insert(student.getUsername(), model, teacher);
        scroll = new JScrollPane(table);
        scroll.setBounds(81, 320, 700, 400);
        scroll.setBorder(BorderFactory.createEmptyBorder());
		panel.add(scroll);
    }
    

    
    public void insert(String username, DefaultTableModel model, Teachers teacher) throws SQLException {
		String[] headers = {"Level of Study", "Current Level of Study", "Period of Study", "Overall Grade", "Progress to Next Level", "Resit Grade"};

		Students student = getStudent(username);
		model.setColumnIdentifiers(headers); 
		ArrayList<ArrayList<String>> ar = teacher.displayYearGrades(student.getEmail());
	    for (int i = 0; i < (ar.size()); i++) {
	    	String los = ar.get(i).get(0); //module id
	    	String clos = ar.get(i).get(1); //initial grade
	    	String pos = ar.get(i).get(2); //resit grade
	    	String og = ar.get(i).get(3); //module name
	    	String ponl = ar.get(i).get(4); //credits worth
	    	String rg = ar.get(i).get(5); //department id
	    	String[] arr = {los, clos, pos, og, ponl, rg};
	    	model.addRow(arr);

	    }
	}
}
