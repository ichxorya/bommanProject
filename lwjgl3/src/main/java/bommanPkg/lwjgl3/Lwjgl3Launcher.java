package bommanPkg.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import bommanPkg.Game.BommanProject;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
	public static void main(String[] args) {
		createApplication();
	}

	private static void createApplication() {
		new Lwjgl3Application(new BommanProject(), getDefaultConfiguration());
	}

	private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("BommanProject " + BommanProject.VERSION);
		configuration.useVsync(true);

		configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate);

		configuration.setWindowedMode(800, 600);
//		configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
		return configuration;
	}

	/*//// Get Game Icons.
	private static String[] getGameIcons() {
		String[] icon = new String[4];
		int iconSize = 8;
		for (int i = 0; i < 4; i++) {
			iconSize *= 2;
			icon[i] = String.format("icon/bommanPjIcon%d.png", iconSize);
		}
		return icon;
	}*/
}