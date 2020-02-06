import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

		startTimers();
		try {
			loadServices("Services.NetMon");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void startTimers() {
		startTimer(rendertimer, 1000, new RenderTimerListener());
		startTimer(clockstimer, 1000, new ClocksTimerListener());
		startTimer(updatetimer, 1000, new UpdateTimerListener());
	}
	
	private void startTimer(Timer timer, int milliseconds, ActionListener timerlistener) {
		timer = new Timer(milliseconds, timerlistener);
		timer.setRepeats(true);
		timer.start();
		timer.setRepeats(true);
	}

	private void loadServices(String f) throws IOException {
		File file = new File(f); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 

		String st; 
		while ((st = br.readLine()) != null) {
			String[] stDataSplit = st.split("\\|");
			services.add(new Service(stDataSplit[0],stDataSplit[1]));
		}
		br.close();
	}

	private class RenderTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);

			int size = 250;
			int gap = 50;
			String statusMsg = "";
			if (optimized) {
				gap = (WIDTH%size)/(WIDTH/size);
			}
			int xpos = gap;
			int ypos = gap/4;
			for(Service temp: services) {
				Service.status t = temp.savedStatus;
				if (t == Service.status.UP) {
					g.setColor(Color.green);
					statusMsg = "O.K.";
				} else if (t == Service.status.SLOW) {
					g.setColor(Color.yellow);
					statusMsg = "Responded Late";
				} else if (t == Service.status.CRITICAL) {
					temp.switchPhase();
					statusMsg = "Critically Slow";
					if (temp.phase) {
						g.setColor(Color.yellow);
					} else {
						g.setColor(Color.red);
					}
				} else if (t == Service.status.MISSED) {
					g.setColor(Color.orange);
					statusMsg = "Missed Ping";
				}else if (t == Service.status.DOWN) {
					temp.switchPhase();
					statusMsg = "Offline";
					if (temp.phase) {
						g.setColor(Color.red);
					} else {
						g.setColor(Color.black);
					}
				} else if (t == Service.status.ERROR) {
					g.setColor(Color.blue);
					statusMsg = "ERROR";
				} else {
					statusMsg = "ERROR";
					g.setColor(Color.blue);
				}
				g.drawRect(xpos, ypos, size, size);
				g.setFont(new Font("Ubuntu", Font.BOLD, 30));
				g.drawString(statusMsg, (xpos+size/2)-(g.getFontMetrics(g.getFont()).stringWidth(statusMsg)/2), ypos+(size/3)*2);
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
