package bommanPkg.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class SingletonMusicClass {
    private static SingletonMusicClass instance = null;
    private static Music music;

    private SingletonMusicClass(String path) {
        music = Gdx.audio.newMusic(Gdx.files.internal(path));
    }

    public static SingletonMusicClass getInstance(String path) {
        if (instance == null) {
            instance = new SingletonMusicClass(path);
        }
        return instance;
    }

    public static void shutAllSounds() {
        music.dispose();
        instance = null;
    }

    public void setLooping(boolean b) {
        music.setLooping(b);
    }

    public void play() {
        music.play();
    }

    public void pause() {
        music.pause();
    }
}
