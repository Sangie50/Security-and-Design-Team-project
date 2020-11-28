import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import userclasses.Admins;

import javax.swing.JLabel;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AdminFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel listOf = new JLabel("List of: ");
		listOf.setBounds(15, 51, 54, 20);
		contentPane.add(listOf);
				
		String[] options = {"Departments", "Degrees", "Modules", "Users"};
		JComboBox comboBox = new JComboBox(options);
		comboBox.setSelectedIndex(1);
		
		comboBox.setBounds(88, 50, 121, 23);

		comboBox.addActionListener(new ActionListener() {
			 
		    @Override
		    public void actionPerformed(ActionEvent event) {
		        JComboBox<String> combo = (JComboBox<String>) event.getSource();
		        String selectedOption = (String) combo.getSelectedItem();
		 
		        if (selectedOption.equals("Departments")) {
		            try {
						Admins.viewDepartment().toString();
						System.out.println("shouldn't be here");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        } 
		        else if (selectedOption.equals("Degrees")) {
		            System.out.println("idk what's going on");
		        }
		    }
		});
		
		contentPane.add(comboBox);
		
		JButton btnNewButton = new JButton("New");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton.setBounds(248, 47, 68, 24);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Welcome Admin!");
		lblNewLabel.setBounds(15, 15, 146, 20);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_1 = new JButton("Logout");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton_1.setBounds(332, 14, 81, 23);
		contentPane.add(btnNewButton_1);
	}
}
