package bommanPkg.Game;

import com.badlogic.gdx.Gdx;

public class Secret {
    public static void theMostImportantMethodIGuess() {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.Q)
                && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.U)
                && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.A)
                && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.M)
                && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.O)
                && Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.N)) {
            Gdx.net.openURI("https://www.youtube.com/watch?v=dQw4w9WgXcQ");
        }
    }
}
