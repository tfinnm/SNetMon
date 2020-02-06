package Services;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ServiceManager {
	public static ArrayList<Service> services = new ArrayList<Service>();
	
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
}
