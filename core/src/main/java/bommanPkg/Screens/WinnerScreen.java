package bommanPkg.Screens;

import bommanPkg.Entities.Base.GUIEntity;
import bommanPkg.Game.BommanProject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;

public class WinnerScreen extends MyScreen {
    private SingletonMusicClass victory;
    private boolean musicPlayed;

    @Override
    public void initialize() {
        victory = SingletonMusicClass.getInstance("music/victory.mp3");
        GUIEntity background = new GUIEntity(0, 0, mainStage, 1200, 960);
        background.loadTexture("winner.png");

        victory.play();
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            // Exit
            BommanProject.exit();
            SingletonMusicClass.shutAllSounds();
        }
    }
}
