package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import application.TimerManager;
import input.KeyboardHandler;
import settings.SettingsManager;


@SuppressWarnings("serial")
public class Panel extends JPanel{

	private BufferedImage image =  new BufferedImage(UIManager.WIDTH, UIManager.HEIGHT, BufferedImage.TYPE_INT_RGB);
	Graphics g = image.getGraphics();
	
	public Panel() {

		TimerManager.startTimer(TimerManager.rendertimer, SettingsManager.renderDelay, new RenderTimerListener(), true);
		setFocusable(true);
		addKeyListener(new KeyboardHandler());


	}
	
	public  void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, UIManager.WIDTH, UIManager.HEIGHT, null);
	}

	
}
