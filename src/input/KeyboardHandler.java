package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import services.ServiceManager;
import ui.UIManager;

/**
 * 
 * @author Toby McDonald <toby_mcdonald@email.com>
 *
 */
public class KeyboardHandler implements KeyListener {

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (ServiceManager.add) {
			if ((arg0.getKeyCode() == KeyEvent.VK_SHIFT) || (arg0.getKeyCode() == KeyEvent.VK_CAPS_LOCK)) {
				
			} else if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (ServiceManager.line == 0) {
					ServiceManager.nameLine = ServiceManager.nameLine.substring(0, ServiceManager.nameLine.length() - 1);
				} else {
					ServiceManager.addrLine = ServiceManager.addrLine.substring(0, ServiceManager.addrLine.length() - 1);
				}
			} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
				if (ServiceManager.line == 0) {
					ServiceManager.line = 1;
				} else {
					String selected = "s";
					if (services.ServiceManager.SelectedType.equals(services.ServiceManager.ServiceType.application)) {
						selected = "a";
					}
					try {
						ServiceManager.addService("Services.NetMon", ServiceManager.nameLine, ServiceManager.addrLine, selected);
					} catch (IOException e) {
					}
					ServiceManager.add = false;
					ServiceManager.line = 0;
					ServiceManager.nameLine = "";
					ServiceManager.addrLine = "";
					ServiceManager.SelectedType = ServiceManager.ServiceType.server;
				}
			} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
				ServiceManager.add = false;
				ServiceManager.line = 0;
				ServiceManager.nameLine = "";
				ServiceManager.addrLine = "";
				ServiceManager.SelectedType = ServiceManager.ServiceType.server;
			} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
				ServiceManager.SelectedType = ServiceManager.ServiceType.server;
			} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
				ServiceManager.SelectedType = ServiceManager.ServiceType.application;
			} else {
				if (ServiceManager.line == 0) {
					ServiceManager.nameLine += arg0.getKeyChar();
				} else {
					ServiceManager.addrLine += arg0.getKeyChar();
				}
			}
		} else {
			if (arg0.getKeyCode() == KeyEvent.VK_UP) {
				ServiceManager.services.add(ServiceManager.services.get(0));
				ServiceManager.services.remove(0);
			} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE && UIManager.featured != null) {
				UIManager.featured = null;
				UIManager.featured.remove = false;
			} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
				ServiceManager.services.add(0,ServiceManager.services.get(ServiceManager.services.size()-1));
				ServiceManager.services.remove(ServiceManager.services.size()-1);
			} else if (arg0.getKeyCode() == KeyEvent.VK_SPACE || arg0.getKeyCode() == KeyEvent.VK_0) {
				UIManager.featured = ServiceManager.services.get(0);
			} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT && UIManager.featured != null) {
				int ind = ServiceManager.services.indexOf(UIManager.featured);
				if (ind < ServiceManager.services.size()-1) {
					ind++;
				} else {
					ind = 0;
				}
				UIManager.featured = ServiceManager.services.get(ind);
				UIManager.featured.remove = false;
			} else if (arg0.getKeyCode() == KeyEvent.VK_A && UIManager.featured != null) {
				UIManager.featured.appMonMsg = "";
			} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT && UIManager.featured != null) {
				int ind = ServiceManager.services.indexOf(UIManager.featured);
				if (ind > 0) {
					ind--;
				} else {
					ind = ServiceManager.services.size()-1;
				}
				UIManager.featured = ServiceManager.services.get(ind);
				UIManager.featured.remove = false;
			} else if ((arg0.getKeyCode() == KeyEvent.VK_DELETE || arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) && UIManager.featured != null) {
				UIManager.featured.remove = true;
			} else if ((arg0.getKeyCode() == KeyEvent.VK_Y) && UIManager.featured.remove) {
				try {
					ServiceManager.removeService("Services.NetMon", UIManager.featured);
				} catch (IOException e) {
				}
				ServiceManager.services.remove(UIManager.featured);
				UIManager.featured = null;
			} else if ((arg0.getKeyCode() == KeyEvent.VK_N) && UIManager.featured.remove) {
				UIManager.featured.remove = false;
			} else if ((arg0.getKeyCode() == KeyEvent.VK_ENTER)) {
				ServiceManager.add = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
