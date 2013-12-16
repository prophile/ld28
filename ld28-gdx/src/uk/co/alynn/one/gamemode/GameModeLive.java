package uk.co.alynn.one.gamemode;

import uk.co.alynn.one.ActionQueue;
import uk.co.alynn.one.Constants;
import uk.co.alynn.one.render.RenderRequest;
import uk.co.alynn.one.render.TextureManager;
import uk.co.alynn.one.render.WorldRenderer;
import uk.co.alynn.one.world.GameOverException;
import uk.co.alynn.one.world.ObstacleLoader;
import uk.co.alynn.one.world.World;
import uk.co.alynn.one.world.WorldUpdater;
import uk.co.alynn.one.world.level.Level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;

public class GameModeLive implements GameMode {
    private final World _world;
    private final Constants _constants;

    public GameModeLive(Constants ks, Level lvl) {
        _world = new World(lvl);
        ObstacleLoader.loadObstacles(_world, "numbers", false);
        _constants = ks;
    }

    @Override
    public GameMode step(ActionQueue aq) {
        WorldUpdater up = new WorldUpdater(_world, _constants, 1 / 30.0);
        emptyActionQueue(aq, up);
        if (runOneTick(up)) {
            return this;
        } else {
            return new GameModeDead(_world.getPlayer().getScore(), _constants);
        }
    }

    private boolean runOneTick(WorldUpdater up) {
        try {
            up.tick();
            return true;
        } catch (GameOverException go) {
            System.err.println("GAME OVER MAN");
            return false;
        }
    }

    private void emptyActionQueue(ActionQueue aq, WorldUpdater up) {
        if (aq.popFlip()) {
            up.doFlip();
        }
    }

    @Override
    public void render(TextureManager txman, SpriteBatch batch) {
        // TODO Auto-generated method stub
        batch.setTransformMatrix(new Matrix4());
        WorldRenderer renderer = new WorldRenderer(_world, new RenderRequest(
                _constants, batch, txman));
        renderer.renderWorld();
    }

}
