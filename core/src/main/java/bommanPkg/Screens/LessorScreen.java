package bommanPkg.Screens;

import bommanPkg.Entities.Base.GUIEntity;
import bommanPkg.Game.BommanProject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;

public class LessorScreen extends MyScreen {
    private Music victorynt;
    private boolean musicPlayed;

    @Override
    public void initialize() {
        victorynt = Gdx.audio.newMusic(Gdx.files.internal("music/victorynt.mp3"));
        GUIEntity background = new GUIEntity(0, 0, mainStage, 1200, 960);
        background.loadTexture("lessor.png");

        if (!musicPlayed) {
            victorynt.play();
            musicPlayed = true;
        }
    }

    @Override
    public void update(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            // Exit
            BommanProject.exit();
            victorynt.dispose();
        }
    }
}
