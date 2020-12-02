import java.awt.EventQueue;

import view.Frames.LoginFrame;

public class LaunchSoftware {
	public static void main(String[] args) {
		System.out.println("Launching software...");
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
}