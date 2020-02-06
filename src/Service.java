import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Service {

	String name;
	String adress;
	boolean phase = true;
	boolean down = false;
	enum status {
		UP,
		SLOW,
		MISSED,
		DOWN,
		ERROR
	}
	status savedStatus = status.UP;
	int misses = 0;
	int uptime = 0;


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
		if (net.isReachable(50)) {
			if (down) {
				uptime = 0;
				down = false;
			}
			misses = 0;
			savedStatus = status.UP;
			return status.UP;
		} else if (net.isReachable(500)) {
			if (down) {
				uptime = 0;
				down = false;
			}
			misses = 0;
			savedStatus = status.SLOW;
			return status.SLOW;
		}
		return miss();
	}

	public String getUp() {
		return (uptime/360)+"h "+((uptime%360)/60)+"m "+(uptime%60)+"s";
	}

}
