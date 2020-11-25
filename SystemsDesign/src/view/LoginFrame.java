package view;
import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public LoginFrame() {
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenDimensions = toolkit.getScreenSize();
//		
//		setSize(screenSize.width/2, screenSize.height/2);
//		setLocation(screenSize.width/4, screenSize.height/4);
				
		setVisible(true);

		setTitle("Login Page");
		setPreferredSize(screenDimensions);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		pack();
		
	}
	
}