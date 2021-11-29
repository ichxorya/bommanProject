package bommanPkg;

import bommanPkg.screens.MainGameScreen;
import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class BommanMain extends Game {
	/* Variables */
	public static final MainGameScreen mainGameScreen = new MainGameScreen();

	/* Methods */
	@Override
	public void create() {
		setScreen(mainGameScreen);
	}


	@Override
	public void dispose() {
		mainGameScreen.dispose();
	}
}