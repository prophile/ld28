package uk.co.alynn.one;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "ld28-gdx";
        cfg.useGL20 = true;
        cfg.width = 800;
        cfg.height = 600;
        cfg.resizable = false;
        cfg.samples = 4;

        new LwjglApplication(new OneGame(), cfg);
    }
}
