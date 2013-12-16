package uk.co.alynn.one;

import java.util.ArrayList;
import java.util.List;

import uk.co.alynn.one.input.InputHandler;
import uk.co.alynn.one.render.RenderRequest;
import uk.co.alynn.one.render.TextureManager;
import uk.co.alynn.one.render.WorldRenderer;
import uk.co.alynn.one.world.Segment;
import uk.co.alynn.one.world.World;
import uk.co.alynn.one.world.WorldUpdater;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OneGame implements ApplicationListener {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Constants constants;
    private World world;
    private TextureManager textureManager;
    private InputHandler inputHandler;
    private boolean flipQueued = false;

    @Override
    public void create() {
        ConstantsLoader ldr = new ConstantsLoader(Gdx.files.internal(
                "data/constants.txt").reader());
        constants = ldr.load();

        textureManager = new TextureManager();

        inputHandler = new InputHandler();
        Gdx.input.setInputProcessor(inputHandler);

        inputHandler.bind("62", new Runnable() {
            @Override
            public void run() {
                flipQueued = true;
            }
        });

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(1, h / w);
        batch = new SpriteBatch();

        List<Segment> segs = new ArrayList<Segment>();

        // ****** CIRCLE
        //int len = 30;
        //for (int i = 0; i < len; ++i) {
        //    segs.add(new Segment(30.0, 90.0 / len));
        //}
        
        // ****** ANY BEZIER CURVE
       	int iterations = 10;
    	//double [] raw = {198.57143,160.93361,282.46108,127.35369,375.20155,165.64481,443.56866,215.98559,539.53351,296.97239,607.52749,408.148,649.07752,525.81327,678.46103,614.17421,685.99116,720.94964,628.60835,799.89974,541.87499,893.57105,432.06845,970.44717,307.72539,1003.8069,236.53765,1019.4626,162.18528,1026.8587,89.638477,1017.4731,48.560539,1016.2444,7.6696351,976.77886,32.811086,935.2938,69.201426,862.29636,135.27584,810.11501,195.70156,757.67071,253.83284,708.79976,316.61348,665.90868,378.06353,621.52068,414.39548,593.26798,442.51098,540.97581,417.62273,496.44847,376.16081,424.88181,291.76661,400.46169,222.5844,364.80847,179.7191,340.50379,126.13323,310.46498,119.94395,256.47023,119.49404,210.87578,162.12378,179.9799,198.57143,160.93361};
    	double [] raw = {0,0,1000,0,1000,1000,0,1000};
    	int inLength = raw.length/2;
    	double [] xraw = new double[inLength];
    	double [] yraw = new double[inLength];
        int allPoints = (int) Math.pow(2, iterations) * (inLength -1) + 1;
        double[] xpoints = new double[allPoints]; // number of points after
                                                  // iteration
        double[] ypoints = new double[allPoints]; // number of points after
                                                  // iteration
        Segment[] sResult = new Segment[allPoints];
    	
    	BezierIteration.rawData(raw, xraw, yraw);


        BezierIteration.discretize(iterations, xraw, yraw, xpoints, ypoints, sResult);
        
        for (int i = 0; i < allPoints; ++i) {
            segs.add(new Segment(sResult[i].getLength(),sResult[i].getAngle()));
        }
        
        
        world = new World(segs);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        WorldRenderer renderer = new WorldRenderer(world, new RenderRequest(
                constants, batch, textureManager));
        renderer.renderWorld();
        batch.end();

        WorldUpdater up = new WorldUpdater(world, constants, 1 / 30.0);
        if (flipQueued) {
            up.doFlip();
            flipQueued = false;
        }
        up.tick();
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
