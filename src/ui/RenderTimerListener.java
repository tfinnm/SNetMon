package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import services.Service;
import services.ServiceManager;
import settings.SettingsManager;

public class RenderTimerListener implements java.awt.event.ActionListener {

	private Graphics g;

	public void actionPerformed(ActionEvent e) {
		g = UIManager.ui.g;
		g.setColor(Color.black);
		g.fillRect(0, 0, UIManager.WIDTH, UIManager.HEIGHT);

		int size = 250;
		int gap = 50;
		int barWidth = 25;
		String statusMsg = "";
		if (SettingsManager.compact) {
			gap = (UIManager.WIDTH%size)/(UIManager.WIDTH/size);
		}
		int ypos = (gap/4);
		if (SettingsManager.topbar) {
			g.setColor(Color.gray);
			g.fillRect(0, 0, UIManager.WIDTH, barWidth);
			ypos += barWidth;
			
		}
		int xpos = gap;
		for(Service temp: ServiceManager.services) {
			Service.status t = temp.savedStatus;
			if (t == Service.status.UP) {
				g.setColor(Color.green);
				statusMsg = "O.K.";
			} else if (t == Service.status.SLOW) {
				g.setColor(Color.yellow);
				statusMsg = "Responded Late";
			} else if (t == Service.status.CRITICAL) {
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
			if (xpos+size > UIManager.WIDTH) {
				xpos = gap;
				ypos += size + gap;
			}
		}

		// This is the last line of actionPerformed
		UIManager.ui.repaint();
	}

}
