package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import application.TimerManager;
import input.KeyboardHandler;
import settings.SettingsManager;


public class Panel extends JPanel{

	private static BufferedImage image =  new BufferedImage(UIManager.WIDTH, UIManager.HEIGHT, BufferedImage.TYPE_INT_RGB);
	static Graphics g = image.getGraphics();
	
	public Panel() {

		TimerManager.startTimer(TimerManager.rendertimer, SettingsManager.renderDelay, new RenderTimerListener(), true);
		setFocusable(true);
		addKeyListener(new KeyboardHandler());


	}
	
	public  void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, UIManager.WIDTH, UIManager.HEIGHT, null);
	}

	
}
