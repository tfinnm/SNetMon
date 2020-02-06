package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import services.ServiceManager;
import ui.UIManager;

public class KeyboardHandler implements KeyListener {

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			ServiceManager.services.add(ServiceManager.services.get(0));
			ServiceManager.services.remove(0);
		} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			UIManager.featured = null;
		} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			ServiceManager.services.add(0,ServiceManager.services.get(ServiceManager.services.size()-1));
			ServiceManager.services.remove(ServiceManager.services.size()-1);
		} else if (arg0.getKeyCode() == KeyEvent.VK_SPACE || arg0.getKeyCode() == KeyEvent.VK_0) {
			UIManager.featured = ServiceManager.services.get(0);
		} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			int ind = ServiceManager.services.indexOf(UIManager.featured);
			if (ind < ServiceManager.services.size()-1) {
			ind++;
			} else {
			ind = 0;
			}
			UIManager.featured = ServiceManager.services.get(ind);
		} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			int ind = ServiceManager.services.indexOf(UIManager.featured);
			if (ind > 0) {
			ind--;
			} else {
			ind = ServiceManager.services.size()-1;
			}
			UIManager.featured = ServiceManager.services.get(ind);
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
