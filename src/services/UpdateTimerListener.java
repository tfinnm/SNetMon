package services;

import java.awt.event.*;
import java.io.IOException;

public class UpdateTimerListener implements ActionListener, Runnable {
	public void actionPerformed(ActionEvent e) {
		Thread t = new Thread(this);
		t.start();
	}

	@Override
	public void run() {
		for(Service temp: ServiceManager.services) {
			try {
				temp.isUp();
			} catch (IOException e1) {
			}
		}
	}
}
