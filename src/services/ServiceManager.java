package services;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 
 * @author Toby McDonald <toby_mcdonald@email.com>
 *
 */
public class ServiceManager {

	/**
	 * ArrayList of Service objects. This is the official location to store and interact with services.
	 */
	public static ArrayList<Service> services = new ArrayList<Service>();

	/**
	 * Used to activate the new service gui
	 */
	public static boolean add = false;
	/**
	 * This is indicates what line the new service gui is on
	 * @apiNote PLEASE DO NOT USE
	 */
	public static int line = 0;
	/**
	 * This is name entered in the new service gui
	 * @apiNote PLEASE DO NOT USE
	 */
	public static String nameLine = "";
	/**
	 * This is the address entered in the new service gui
	 * @apiNote PLEASE DO NOT USE
	 */
	public static String addrLine = "";

	public static enum ServiceType {
		server,
		application
	}

	public static ServiceType SelectedType = ServiceType.server;

	public static void loadServices(String f) throws IOException {
		File file = new File(f); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 

		String st; 
		while ((st = br.readLine()) != null) {
			String[] stDataSplit = st.split("\\|");
			if (stDataSplit[0].equals("s")) {
				services.add(new Service(stDataSplit[1],stDataSplit[2]));
			} else if (stDataSplit[0].equals("a")) {
				services.add(new ApplicationService(stDataSplit[1],stDataSplit[2]));
			} 
		}
		br.close();
	}

	public static void addService(String f, String n, String a, String t) throws IOException {
		String pass = t+"|"+n+"|"+a;
		write(f,pass,true);
		if (t.equals("s")) {
			services.add(new Service(n,a));
		} else if (t.equals("a")) {
			services.add(new ApplicationService(n,a));
		}
	}

	public static void removeService(String f, Service s) throws IOException {
		String pre = "s";
		if (s instanceof ApplicationService) {
			pre = "a";
			((ApplicationService) s).exists = false;
		}
		String pass = pre+"|"+s.name+"|"+s.adress;
		write(f,pass,false);
		services.remove(s);
	}

	private static void write(String f, String l, boolean write) {
		try {
			BufferedReader file = new BufferedReader(new FileReader(f));
			StringBuffer inputBuffer = new StringBuffer();
			String line;

			while ((line = file.readLine()) != null) {
				if (!line.equals(l)) {
					inputBuffer.append(line);
					inputBuffer.append('\n');
				}
			}
			if (write) {
				inputBuffer.append(l);
			}
			file.close();

			// write the new string with the replaced line OVER the same file
			FileOutputStream fileOut = new FileOutputStream(f);
			fileOut.write(inputBuffer.toString().getBytes());
			fileOut.close();

		} catch (Exception e) {
		}
	}
}
