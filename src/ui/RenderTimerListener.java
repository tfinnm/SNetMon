package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import services.Service;
import services.ServiceManager;
import settings.SettingsManager;

public class RenderTimerListener implements java.awt.event.ActionListener {

	private Graphics g;

	public void actionPerformed(ActionEvent e) {
		g = UIManager.ui.g;
		g.setColor(Color.black);
		g.fillRect(0, 0, UIManager.WIDTH, UIManager.HEIGHT);

		int size = SettingsManager.size;
		int gap = size/5;
		int barWidth = 25;
		String statusMsg = "";
		if (SettingsManager.compact) {
			gap = (UIManager.WIDTH%size)/(UIManager.WIDTH/size);
		}
		int ypos = (gap/4);
		if (SettingsManager.topbar) {
			g.setColor(Color.gray);
			g.fillRect(0, 0, UIManager.WIDTH, barWidth);
			g.setColor(Color.white);
			g.setFont(new Font("Impact", Font.BOLD, 15));
			g.drawString("B L I P", 10, barWidth/2+7);
			g.setFont(new Font("Impact", Font.ITALIC, 10));
			g.drawString("v1.0", 60, barWidth/2+7);
			g.setFont(new Font("Impact", Font.PLAIN, 15));
			String clock = LocalDateTime.now().format(DateTimeFormatter.ofPattern("d/M/uuuu | k:m:s")).toString();
			g.drawString(clock,UIManager.WIDTH/2-g.getFontMetrics(g.getFont()).stringWidth(clock)/2, barWidth/2+7);
			String servicesMsg = ServiceManager.services.size()+" Services";
			g.drawString(servicesMsg,UIManager.WIDTH-g.getFontMetrics(g.getFont()).stringWidth(servicesMsg)-10, barWidth/2+7);
			ypos += barWidth;
			
		}
		int xpos = gap;
		for(Service temp: ServiceManager.services) {
			statusMsg = UIHelpers.setColor(g, temp);
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
		
		if(UIManager.featured != null) {
			g.setColor(Color.black);
			g.fillRect(UIManager.WIDTH/4, UIManager.HEIGHT/4, UIManager.WIDTH/2, UIManager.HEIGHT/2);
			statusMsg = UIHelpers.setColor(g, UIManager.featured);
			if (g.getColor().equals(Color.black)) g.setColor(Color.white);
			g.drawRect(UIManager.WIDTH/4, UIManager.HEIGHT/4, UIManager.WIDTH/2, UIManager.HEIGHT/2);
			g.setFont(new Font("Ubuntu", Font.BOLD, 70));
			g.drawString(statusMsg.toUpperCase(), (UIManager.WIDTH/2)-(g.getFontMetrics(g.getFont()).stringWidth(statusMsg.toUpperCase())/2), UIManager.HEIGHT/2-35);
			g.setColor(Color.white);
			g.setFont(new Font("Ubuntu", Font.BOLD, 50));
			g.drawString(UIManager.featured.name, (UIManager.WIDTH/2)-(g.getFontMetrics(g.getFont()).stringWidth(UIManager.featured.name)/2), UIManager.HEIGHT/4+60);
			g.setFont(new Font("Ubuntu", Font.PLAIN, 30));
			g.drawString(UIManager.featured.adress, (UIManager.WIDTH*3/8)+5, UIManager.HEIGHT*3/4-35);
			g.drawString(UIManager.featured.getUp(), (UIManager.WIDTH*5/8)-(g.getFontMetrics(g.getFont()).stringWidth(UIManager.featured.getUp()))-5, UIManager.HEIGHT*3/4-35);
		}

		// This is the last line of actionPerformed
		UIManager.ui.repaint();
	}

}
