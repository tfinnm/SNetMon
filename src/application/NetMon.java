package application;
import java.io.IOException;

import services.*;
import ui.UIManager;
import settings.SettingsManager;

public class NetMon {
	
	public static void main(String[] args) {
		UIManager.createFrame();
		try {
			ServiceManager.loadServices("Services.NetMon");
			SettingsManager.loadSettings("settings.NetMon");
		} catch (IOException e) {
		}
		TimerManager.startTimers();
	}


}
