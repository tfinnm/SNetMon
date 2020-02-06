package services;

import java.awt.event.*;

public class UptimeTimerListener implements ActionListener, Runnable {
	public void actionPerformed(ActionEvent e) {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		for(Service temp: ServiceManager.services) {
			temp.increaseUpTime();
		}
	}


}
