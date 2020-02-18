package application;
import java.io.IOException;

import extensions.ExtensionManager;
import services.*;
import ui.UIManager;
import settings.SettingsManager;

/**
 * 
 * @author Toby McDonald <toby_mcdonald@email.com>
 * @version 1.0.0
 */
public class BLIP {
	
	/**
	 * 
	 * main method to run program.
	 * 
	 * @param args unused...
	 */
	public static void main(String[] args) {
		UIManager.createFrame();
		try {
			ServiceManager.loadServices("Services.NetMon");
			SettingsManager.loadSettings("settings.NetMon");
			//ExtensionManager.loadExtensions();
		} catch (IOException e) {
		}
		TimerManager.startTimers();
	}


}
