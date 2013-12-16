package uk.co.alynn.one.gamemode;

import uk.co.alynn.one.ActionQueue;
import uk.co.alynn.one.ColourScheme;
import uk.co.alynn.one.Constants;
import uk.co.alynn.one.LevelGenerator;
import uk.co.alynn.one.render.FXManager;
import uk.co.alynn.one.render.RenderRequest;
import uk.co.alynn.one.render.TextureManager;
import uk.co.alynn.one.render.WorldRenderer;
import uk.co.alynn.one.world.AdvanceLevelException;
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
    private final FXManager _fxManager;
    private int gameOverCounter = -1;
    private final int _thresholdScore;
    private final ColourScheme _colScheme;

    public GameModeLive(Constants ks, Level lvl, ColourScheme scheme) {
        this(ks, lvl, scheme, 0);
    }

    public GameModeLive(Constants ks, Level lvl, ColourScheme scheme,
            int initialScore) {
        _world = new World(lvl);
        _world.getPlayer().setScore(initialScore);
        ObstacleLoader.loadObstacles(_world, "numbers", false);
        _constants = ks;
        _fxManager = new FXManager();
        _colScheme = scheme;
        _thresholdScore = initialScore + 10;
    }

    @Override
    public GameMode step(ActionQueue aq) {
        WorldUpdater up = new WorldUpdater(_world, _constants, 1 / 30.0);
        emptyActionQueue(aq, up);
        _fxManager.update();
        return runOneTick(up);
    }

    private GameMode runOneTick(WorldUpdater up) {
        _fxManager.setTrailEnabled(gameOverCounter < 0);
        if (gameOverCounter == -1) {
            try {
                up.tick(_fxManager, _thresholdScore);
            } catch (GameOverException go) {
                System.err.println("GAME OVER MAN");
                gameOverCounter = _constants.getInt("game-over-hold-time", 35,
                        "Hold time, in frames, after game over.");
            } catch (AdvanceLevelException adv) {
                return new GameModeLive(_constants, adv.getLevel(),
                        LevelGenerator.nextColourScheme(), _world.getPlayer()
                                .getScore());
            }
            return this;
        } else {
            return --gameOverCounter > 0 ? this : new GameModeDead(_world
                    .getPlayer().getScore(), _constants);
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
                _constants, batch, txman, _colScheme), gameOverCounter < 0);
        renderer.renderWorld(_fxManager);
    }

}
