package ui;

import java.awt.event.*;

import services.*;

public class PhaseTimerListener implements ActionListener, Runnable {
	public void actionPerformed(ActionEvent e) {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		for(Service temp: ServiceManager.services) {
			temp.switchPhase();
		}
	}


}