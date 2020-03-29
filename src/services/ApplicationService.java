package services;

import java.io.IOException;
import java.net.*;

import ui.UIManager;
public class ApplicationService extends Service {
		
	public boolean exists = true;
	public ApplicationService currserv = this;

	public ApplicationService(String n, String a) {
		super(n, a);
		savedStatus = Status.UP;
		new Thread(new Runnable() {

			public void run() {
				try {
					MulticastSocket socket = new MulticastSocket(0223);
			        byte[] buf = new byte[256];
					InetAddress group = InetAddress.getByName("230.2.2.3");
			        socket.joinGroup(group);
			        while (exists) {
			            DatagramPacket packet = new DatagramPacket(buf, buf.length);
			            socket.receive(packet);
			            String received = new String(packet.getData(), 0, packet.getLength());
			            appMonMsg += received;
			            if (settings.SettingsManager.highlightWarn) {
			            	UIManager.featured = currserv;
			            }
			            if (!appMonMsg.equals("")) {
			            	savedStatus = Status.WARN;
			            } else {
			            	savedStatus = Status.UP;
			            }
			        }
			        socket.leaveGroup(group);
			        socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();
	}
	
	public Status isUp() throws IOException {
		return this.savedStatus;
	}

}
