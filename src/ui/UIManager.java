package ui;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;

import input.KeyboardHandler;
import services.Service;

public class UIManager {
	//heights
	public static final int WIDTH = Integer.valueOf(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width);
	public static final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	static Panel ui = new Panel();
	
	public static Service featured;
	
	public static void createFrame() {
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setContentPane(ui);
		frame.setVisible(true);
	}

}
