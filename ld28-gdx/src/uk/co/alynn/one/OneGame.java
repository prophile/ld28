package uk.co.alynn.one;

import java.util.ArrayList;
import java.util.List;

import uk.co.alynn.one.world.Segment;
import uk.co.alynn.one.world.World;
import uk.co.alynn.one.world.WorldUpdater;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class OneGame implements ApplicationListener {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private Constants constants;

    @Override
    public void create() {
        ConstantsLoader ldr = new ConstantsLoader(Gdx.files.internal(
                "data/constants.txt").reader());
        constants = ldr.load();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(1, h / w);
        batch = new SpriteBatch();

        texture = new Texture(Gdx.files.internal("data/libgdx.png"));
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        TextureRegion region = new TextureRegion(texture, 0, 0, 512, 275);

        sprite = new Sprite(region);
        sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

        System.out.println(constants.getString("hello-message",
                "Hello, world!", "Message displayed on program start."));

        testWorld();
    }

    private void testWorld() {
        List<Segment> segs = new ArrayList<Segment>();
        segs.add(new Segment(1.0, 0.0));
        segs.add(new Segment(1.0, 0.0));
        segs.add(new Segment(1.0, 0.0));
        World w = new World(segs);
        for (int i = 0; i < 60; ++i) {
            WorldUpdater up = new WorldUpdater(w, constants, 1.0 / 60.0);
            up.tick();
            System.out.println(w.getPlayer().getPosition());
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }
}
