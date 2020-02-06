import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class NetMon extends JPanel{
	//heights
	public static final int WIDTH = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGHT = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();


	// Declare objects to be used
	private BufferedImage image;
	private Timer rendertimer;
	private Timer clockstimer;
	private Timer updatetimer;
	private Graphics g;
	private static boolean optimized = false;
	public ArrayList<Service> services = new ArrayList<Service>();

	/**
	 * creates the loop
	 */
	public NetMon() {

		image =  new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = image.getGraphics();

		// create a Timer object and call the listener defined in the private class below
		// The listener can be called anything you chose
		rendertimer = new Timer(1000, new RenderTimerListener());
		rendertimer.setRepeats(true);
		rendertimer.start();
		rendertimer.setRepeats(true);

		clockstimer = new Timer(1000, new ClocksTimerListener());
		clockstimer.setRepeats(true);
		clockstimer.start();
		clockstimer.setRepeats(true);

		updatetimer = new Timer(1000, new UpdateTimerListener());
		updatetimer.setRepeats(true);
		updatetimer.start();
		updatetimer.setRepeats(true);

		services.add(new Service("test","localhost"));
		services.add(new Service("test2","192.168.1.1"));
		services.add(new Service("test3","tfinnm.tk"));
		services.add(new Service("test4","www.google.com"));
		services.add(new Service("test5","http://www.google.com"));
		services.add(new Service("test6","http://www.google.com/"));
		services.add(new Service("test7","example.com"));

	}

	private class RenderTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			int size = 250;
			int gap = 50; 
			if (optimized) {
				gap = (WIDTH%size)/(WIDTH/size);
			}
			int xpos = gap;
			int ypos = gap/4;
			for(Service temp: services) {
				Service.status t = temp.savedStatus;
				if (t == Service.status.UP) {
					g.setColor(Color.green);
				} else if (t == Service.status.SLOW) {
					g.setColor(Color.yellow);
				} else if (t == Service.status.MISSED) {
					g.setColor(Color.orange);
				}else if (t == Service.status.DOWN) {
					temp.switchPhase();
					if (temp.phase) {
						g.setColor(Color.red);
					} else {
						g.setColor(Color.black);
					}
				} else if (t == Service.status.ERROR) {
					g.setColor(Color.blue);
				} else {
					g.setColor(Color.blue);
				}
				g.drawRect(xpos, ypos, size, size);
				g.setColor(Color.white);
				g.setFont(new Font("Ubuntu", Font.BOLD, 25));
				g.drawString(temp.name, (xpos+size/2)-(g.getFontMetrics(g.getFont()).stringWidth(temp.name)/2), ypos+size/5);
				g.setFont(new Font("Ubuntu", Font.PLAIN, 17));
				g.drawString(temp.adress, (xpos+size/2)-(g.getFontMetrics(g.getFont()).stringWidth(temp.adress)/2), ypos+(size/3));
				g.setFont(new Font("Ubuntu", Font.PLAIN, 15));
				g.drawString(temp.getUp(), (xpos+size/2)-(g.getFontMetrics(g.getFont()).stringWidth(temp.getUp())/2), ypos+(size/5)*4);
				xpos += size+gap;
				if (xpos > WIDTH) {
					xpos = gap;
					ypos += size + gap;
				}
				System.out.print(xpos+","+ypos);
			}

			System.out.println();
			// This is the last line of actionPerformed
			repaint();
		}
	}

	private class ClocksTimerListener implements ActionListener, Runnable {
		public void actionPerformed(ActionEvent e) {
			Thread t = new Thread(this);
			t.start();
		}

		@Override
		public void run() {
			for(Service temp: services) {
				temp.increaseUpTime();
			}
		}
		
		
	}

	private class UpdateTimerListener implements ActionListener, Runnable {
		public void actionPerformed(ActionEvent e) {
			Thread t = new Thread(this);
			t.start();
		}

		@Override
		public void run() {
			for(Service temp: services) {
				try {
					temp.isUp();
				} catch (IOException e1) {
				}
			}
		}
	}



	/**
	 *  draws the image
	 */
	public void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
	}

	public static void main(String[] args) {
		if(args.length > 0) {
			optimized = Boolean.getBoolean(args[0]);
		}
		JFrame frame = new JFrame();
		frame.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setUndecorated(true);
		frame.setResizable(false);
		frame.setContentPane(new NetMon());
		frame.setVisible(true);
	}


}
