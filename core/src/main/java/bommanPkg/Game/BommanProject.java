package bommanPkg.Game;

import bommanPkg.Screens.MainGameScreen;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class BommanProject extends MyGame {
    public static final String VERSION = getVersion();

    private static String getVersion() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = new Date();
        return (dateFormatter.format(date));
    }

    public void create() {
        setActiveScreen(new MainGameScreen());
    }

}