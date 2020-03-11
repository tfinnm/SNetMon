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
	
	public static void loadServices(String f) throws IOException {
		File file = new File(f); 
		BufferedReader br = new BufferedReader(new FileReader(file)); 

		String st; 
		while ((st = br.readLine()) != null) {
			String[] stDataSplit = st.split("\\|");
			services.add(new Service(stDataSplit[0],stDataSplit[1]));
		}
		br.close();
	}

	public static void addService(String f, String n, String a) throws IOException {
		String pass = n+"|"+a;
		write(f,pass,true);
		services.add(new Service(n,a));
	}

	public static void removeService(String f, Service s) throws IOException {
		String pass = s.name+"|"+s.adress;
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
