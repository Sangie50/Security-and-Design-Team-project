package view.Panels.Registrar;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class temp extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public temp() {

		setLayout(null);
		setBounds(100, 100, 1035, 647);
		
		JLabel title = new JLabel("Add or remove optional modules");
		title.setFont(title.getFont().deriveFont(title.getFont().getStyle() | Font.BOLD));
		title.setBounds(21, 21, 425, 26);
		add(title);
		
		JLabel modulesLabel = new JLabel("Student Modules:");
		modulesLabel.setBounds(21, 80, 187, 26);
		add(modulesLabel);
		
		JLabel optionalModulesLabel = new JLabel("Optional modules:");
		optionalModulesLabel.setBounds(719, 201, 196, 26);
		add(optionalModulesLabel);
		
		JComboBox optionalModulesList = new JComboBox();
		optionalModulesList.setBounds(719, 248, 247, 32);
		add(optionalModulesList);
		
		JButton addModuleButton = new JButton("Add module");
		addModuleButton.setBounds(719, 301, 247, 35);
		add(addModuleButton);
		
		JLabel creditsAvailable = new JLabel("Credits available:");
		creditsAvailable.setBounds(719, 119, 206, 26);
		add(creditsAvailable);
		
		JLabel credits = new JLabel("");
		credits.setBounds(719, 154, 92, 26);
		add(credits);
		
		table = new JTable();
		table.setBounds(21, 127, 677, 499);
		add(table);
		
	}

}
