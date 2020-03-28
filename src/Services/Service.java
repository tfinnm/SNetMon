package services;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.*;

import extensions.ExtensionManager;
import settings.SettingsManager;

public class Service {

	public String name;
	public String adress;
	public boolean phase = true;
	public boolean down = false;
	public boolean remove = false;
	public Status savedStatus = Status.ERROR;
	public int misses = 0;
	public int criticals = 0;
	public int uptime = 0;


	public Service(String n, String a) {
		name = n;
		adress = a;
	}

	public void increaseUpTime() {
		uptime++;
	}

	private Status miss() {
		if (misses < 3) {	
			misses++;
			savedStatus = Status.MISSED;
			if (settings.SettingsManager.highlightMiss) {
				ui.UIManager.featured = this;
			}
			return Status.MISSED;
		} else {
			if (!down) {
				uptime = 0;
				down = true;
				phase = true;
				if (settings.SettingsManager.highlightOff) {
					ui.UIManager.featured = this;
				}
				Toolkit.getDefaultToolkit().beep();
				ExtensionManager.triggerEvent(Status.DOWN);
			}
			savedStatus = Status.DOWN;
			return Status.DOWN;
		}
	}

	public void switchPhase() {
		phase = !phase;
	}

	public Status isUp() throws IOException {
		InetAddress net;
		try {
			net = InetAddress.getByName(adress);
		} catch (UnknownHostException e) {
			return miss();
		}
		if (net.isReachable(SettingsManager.latet)) {
			if (down) {
				uptime = 0;
				down = false;
				ExtensionManager.triggerEvent(Status.UP);
			}
			misses = 0;
			savedStatus = Status.UP;
			return Status.UP;
		} else if (net.isReachable(SettingsManager.critt)) {
			if (down) {
				uptime = 0;
				down = false;
				ExtensionManager.triggerEvent(Status.UP);
			}
			misses = 0;
			savedStatus = Status.SLOW;
			return Status.SLOW;
		}
		if (misses < 1 && net.isReachable(SettingsManager.misst)) {
			if (savedStatus != Status.CRITICAL) {
				ExtensionManager.triggerEvent(Status.CRITICAL);
				if (settings.SettingsManager.highlightCrit) {
					ui.UIManager.featured = this;
				}
			}
			savedStatus = Status.CRITICAL;
			return Status.CRITICAL;
		} else {
			return miss();
		}
	}

	public String getUp() {
		return (uptime/3600)+"h "+((uptime%3600)/60)+"m "+(uptime%60)+"s";
	}

}
