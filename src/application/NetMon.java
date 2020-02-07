package application;
import java.io.IOException;

import extensions.ExtensionManager;
import services.*;
import ui.UIManager;
import settings.SettingsManager;

public class NetMon {
	
	public static void main(String[] args) {
		UIManager.createFrame();
		try {
			ServiceManager.loadServices("Services.NetMon");
			SettingsManager.loadSettings("settings.NetMon");
			ExtensionManager.loadExtensions();
		} catch (IOException e) {
		}
		TimerManager.startTimers();
	}


}
