package bommanPkg.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import bommanPkg.BommanMain;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	//// Main Launcher.
	public static void main(String[] args) {
		createApplication();
	}

	//// Application Initialization.
	private static Lwjgl3Application createApplication() {
		return new Lwjgl3Application(new BommanMain(), getDefaultConfiguration());
	}

	//// Default Configuration.
	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();

		configuration.setTitle("bommanProject");
		configuration.useVsync(true);
		configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);
		configuration.setWindowedMode(1280, 960);
		configuration.setWindowIcon(getGameIcons());

		return configuration;
	}

	//// Get Game Icons.
	private static String[] getGameIcons() {
		String[] icon = new String[4];
		int iconSize = 8;
		for (int i = 0; i < 4; i++) {
			iconSize *= 2;
			icon[i] = String.format("icon/bommanPjIcon%d.png", iconSize);
		}
		return icon;
	}
}