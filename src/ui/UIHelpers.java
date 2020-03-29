package ui;

import java.awt.Color;
import java.awt.Graphics;

import services.Service;

public class UIHelpers {

	public static String setColor(Graphics g, Service temp) {
		String statusMsg = "";
		services.Status t = temp.savedStatus;
		if (t == services.Status.UP) {
			g.setColor(Color.green);
			statusMsg = "O.K.";
		} else if (t == services.Status.SLOW) {
			g.setColor(Color.yellow);
			statusMsg = "Responded Late";
		} else if (t == services.Status.WARN) {
			g.setColor(Color.orange);
			statusMsg = "ALERT!";
		} else if (t == services.Status.CRITICAL) {
			statusMsg = "Critically Slow";
			if (temp.phase) {
				g.setColor(Color.yellow);
			} else {
				g.setColor(Color.red);
			}
		} else if (t == services.Status.MISSED) {
			g.setColor(Color.orange);
			statusMsg = "Missed Ping";
		}else if (t == services.Status.DOWN) {
			statusMsg = "Offline";
			if (temp.phase) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.black);
			}
		} else if (t == services.Status.ERROR) {
			g.setColor(Color.blue);
			statusMsg = "ERROR";
		} else {
			statusMsg = "ERROR";
			g.setColor(Color.blue);
		}
		return statusMsg;
	}
	
}
