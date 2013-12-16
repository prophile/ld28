package uk.co.alynn.one.sound;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;

public final class SoundManager {
    private static final Map<String, Sound> _soundCache = new HashMap<String, Sound>();

    public static void playSound(String sound) {
        Sound snd = getSoundFromCache(sound);
        snd.play();
    }

    private static Sound getSoundFromCache(String sound) {
        Sound eyes = _soundCache.get(sound);
        if (eyes == null) {
            eyes = loadSound(sound);
            _soundCache.put(sound, eyes);
        }
        return eyes;
    }

    private static Sound loadSound(String sound) {
        String path = "data/" + sound + ".wav";
        FileHandle handle = Gdx.files.internal(path);
        return Gdx.audio.newSound(handle);
    }

    public static void startMusic() {
        String path = "data/soar.ogg";
        FileHandle handle = Gdx.files.internal(path);
        Music mus = Gdx.audio.newMusic(handle);
        mus.setLooping(true);
        mus.setVolume(0.4f);
        mus.play();
    }
}
