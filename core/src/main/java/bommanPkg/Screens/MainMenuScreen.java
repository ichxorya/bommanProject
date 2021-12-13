package bommanPkg.Screens;

import bommanPkg.Entities.Base.GUIEntity;
import bommanPkg.Game.BommanProject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;

public class MainMenuScreen extends MyScreen {
    private Sound selectSound;
    private SingletonMusicClass mainMenuMusic;
    private boolean pressed;

    @Override
    public void initialize() {
        GUIEntity background = new GUIEntity(0, 0, mainStage, 1200, 960);
        background.loadTexture("mainmenu.png");

        GUIEntity title = new GUIEntity(0, 400, mainStage, 400, 100);
        title.loadTexture("title.png");

        GUIEntity hdsd = new GUIEntity(300, 0, mainStage, 400, 100);
        hdsd.loadTexture("hdsd.png");

        GUIEntity start = new GUIEntity(300, 480, mainStage, 400, 100);
        start.loadTexture("startbutton.png");

        selectSound = Gdx.audio.newSound(Gdx.files.internal("sfxs/default_sfx.wav"));
        mainMenuMusic = SingletonMusicClass.getInstance("music/mainmenu.mp3");
        mainMenuMusic.setLooping(true);
        mainMenuMusic.play();
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {

            if (!pressed) {
                selectSound.play();
                pressed = true;
                SingletonMusicClass.shutAllSounds();
            }

            // 2 seconds timer
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    BommanProject.setActiveScreen(new MainGameScreen());
                }
            }, 2);
        }
    }
}
