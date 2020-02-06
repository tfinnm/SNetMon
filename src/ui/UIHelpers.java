package ui;

import java.awt.Color;
import java.awt.Graphics;

import services.Service;

public class UIHelpers {

	public static String setColor(Graphics g, Service temp) {
		String statusMsg = "";
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
		return statusMsg;
	}
	
}
