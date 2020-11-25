package view;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginPanel extends JPanel {
	public LoginPanel() {
		JPanel pane = new JPanel();
		pane.setLayout(new FlowLayout());
		pane.add(new JTextField(20));
		pane.add(new JPasswordField(20));
		pane.add(new JButton("Login"));
		pane.add(new JButton("Register"));
	}
}
