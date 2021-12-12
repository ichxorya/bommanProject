package bommanPkg.Game;

import bommanPkg.Entities.Derived.LivingEntities.Players.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class _ {

    private static Music HolyHolyMusic;

    public static void theMostImportantMethodIGuess(Player player) {
        if (HolyHolyMusic == null) {
            HolyHolyMusic = Gdx.audio.newMusic(Gdx.files.internal("music/rickmode.mp3"));
            HolyHolyMusic.setLooping(true);
        }

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.Q)
                && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.U)
                && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)
                && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.M)
                && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.O)
                && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.N)) {
            HolyHolyMusic.play();
            player.i_am_god_ok();
        }
    }

    public static void shutAllSounds() {
        if (HolyHolyMusic != null) {
            HolyHolyMusic.dispose();
        }
    }
}
