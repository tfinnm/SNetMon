package extensions;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Scanner;

import services.Service;

public class ExtensionManager {

	static ArrayList<Extension> plugins = new ArrayList<Extension>();

	public static void loadExtensions() {
		findExtensions("Extensions/");
	}

	private static void findExtensions(String DIR) {
		File dir = new File(DIR);
		if (dir.exists()) {
			File[] files = dir.listFiles((d, name) -> name.endsWith(".class"));
			for (int i = 0; i < files.length; i++) {
				try {
					loadExtension(new File(DIR+"/"+files[i].getName()));
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static void loadExtension(File dir) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
		URL loadPath = dir.toURI().toURL();
		URL[] classUrl = new URL[]{loadPath};

		ClassLoader cl = new URLClassLoader(classUrl);

		//Class loadedClass = new URLClassLoader().loadClass(dir.getCanonicalPath());
		//Class loadedClass = cl.loadClass(dir.getName().substring(0,dir.getName().length()-6));
		Class loadedClass = cl.loadClass(dir.getCanonicalPath());

		plugins.add((Extension)loadedClass.newInstance());
		plugins.get(plugins.size()-1).loadExtension();
	}

	public static void triggerEvent(services.Status e) {

		for (int j = 0; j < plugins.size(); j++) {
			plugins.get(j).onEvent(e);
		}

	}

}
