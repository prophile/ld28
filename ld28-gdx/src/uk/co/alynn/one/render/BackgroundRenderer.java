package uk.co.alynn.one.render;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;

public final class BackgroundRenderer {
    private static List<BackgroundElement> elements = null;

    private static void initElements() {
        Random rng = new Random();
        elements = new ArrayList<BackgroundElement>();
        elements.add(new BackgroundElement("Flower", rng));
        elements.add(new BackgroundElement("Hexagon", rng));
        elements.add(new BackgroundElement("Pentagon", rng));
        elements.add(new BackgroundElement("Circle", rng));
        elements.add(new BackgroundElement("Triangle", rng));
        elements.add(new BackgroundElement("Square", rng));
    }

    public static void renderBackground(WorldRenderer worldRenderer) {
        if (elements == null) {
            initElements();
        }
        Gdx.gl.glClearColor(3 / 16.0f, 0.0f, 6 / 16.0f, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        float ww = Gdx.graphics.getWidth(), wh = Gdx.graphics.getHeight();

        for (BackgroundElement elt : elements) {
            elt.update(1.0f / 30.0f);
            String type = "Background_Shapes_" + elt.getType();
            worldRenderer.drawSprite(type, ww * 0.5f + elt.getX(), wh * 0.5f
                    + elt.getY(), 192.0f, Color.PINK);
        }
    }
}
