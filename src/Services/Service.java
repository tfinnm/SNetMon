package services;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import settings.SettingsManager;

public class Service {

	public String name;
	public String adress;
	public boolean phase = true;
	public boolean down = false;
	public boolean remove = false;
	public enum status {
		UP,
		SLOW,
		CRITICAL,
		MISSED,
		DOWN,
		ERROR
	}
	public status savedStatus = status.ERROR;
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

	private status miss() {
		if (misses < 3) {	
			misses++;
			savedStatus = status.MISSED;
			return status.MISSED;
		} else {
			if (!down) {
				uptime = 0;
				down = true;
				phase = true;
				Toolkit.getDefaultToolkit().beep();
			}
			savedStatus = status.DOWN;
			return status.DOWN;
		}
	}

	public void switchPhase() {
		phase = !phase;
	}

	public status isUp() throws IOException {
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
			}
			misses = 0;
			savedStatus = status.UP;
			return status.UP;
		} else if (net.isReachable(SettingsManager.critt)) {
			if (down) {
				uptime = 0;
				down = false;
			}
			misses = 0;
			savedStatus = status.SLOW;
			return status.SLOW;
		}
		if (misses < 1 && net.isReachable(SettingsManager.misst)) {
			savedStatus = status.CRITICAL;
			return status.CRITICAL;
		} else {
			return miss();
		}
	}

	public String getUp() {
		return (uptime/3600)+"h "+((uptime%3600)/60)+"m "+(uptime%60)+"s";
	}

}
