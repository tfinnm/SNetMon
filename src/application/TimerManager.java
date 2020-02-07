package application;

import java.awt.event.ActionListener;

import javax.swing.Timer;

import services.UpdateTimerListener;
import services.UptimeTimerListener;
import settings.SettingsManager;
import ui.PhaseTimerListener;

public class TimerManager {

	public static Timer rendertimer;
	private static Timer clockstimer;
	private static Timer updatetimer;
	private static Timer phasetimer;

	public static void startTimers() {
		startTimer(clockstimer, 1000, new UptimeTimerListener(), true);
		startTimer(updatetimer, SettingsManager.pingDelay, new UpdateTimerListener(), true);
		startTimer(phasetimer, 1000, new PhaseTimerListener(), true);
	}
	
	public static void startTimer(Timer timer, int milliseconds, ActionListener timerlistener, boolean repeats) {
		timer = new Timer(milliseconds, timerlistener);
		timer.setRepeats(repeats);
		timer.start();
		timer.setRepeats(repeats);
	}

}
