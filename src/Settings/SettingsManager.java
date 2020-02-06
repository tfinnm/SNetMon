package settings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SettingsManager {

	public static int latet;
	public static int critt;
	public static int misst;
	public static boolean compact;

	public static void loadSettings(String f) throws IOException {
		File file = new File(f);
		BufferedReader br = new BufferedReader(new FileReader(file)); 

		String st; 
		while ((st = br.readLine()) != null) {
			if (!st.startsWith("#")) {
				String[] stDataSplit = st.split("=");
				if (stDataSplit[0].equals("compact")) {
					compact = Boolean.valueOf(stDataSplit[1]);
				} else if (stDataSplit[0].equals("critical")) {
					critt = Integer.valueOf(stDataSplit[1]);
				} else if (stDataSplit[0].equals("late")) {
					latet = Integer.valueOf(stDataSplit[1]);
				} else if (stDataSplit[0].equals("miss")) {
					misst = Integer.valueOf(stDataSplit[1]);
				}
			}
		}
		br.close();
	}

}
