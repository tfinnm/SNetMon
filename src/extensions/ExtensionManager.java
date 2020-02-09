package extensions;

import java.io.File;
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
					loadExtension(files[i]);
				} catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				}
			}
		}
	}

	private static void loadExtension(File dir) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		URL loadPath = dir.toURI().toURL();
		URL[] classUrl = new URL[]{loadPath};

		ClassLoader cl = new URLClassLoader(classUrl);

		Class loadedClass = cl.loadClass(dir.getName());

		plugins.add((Extension)loadedClass.newInstance());
		plugins.get(plugins.size()-1).loadExtension();
	}

	public static void triggerEvent(services.Status e) {

		for (int j = 0; j < plugins.size(); j++) {
			plugins.get(j).onEvent(e);
		}

	}

}
