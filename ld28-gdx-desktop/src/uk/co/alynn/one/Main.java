package uk.co.alynn.one;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "ld28-gdx";
        cfg.useGL20 = false;
        cfg.width = 480;
        cfg.height = 320;
        cfg.resizable = true;

        new LwjglApplication(new OneGame(), cfg);

        // bezierTest();
    }

    /*
     * private static void bezierTest() { // example of bezier iteration //
     * should be done for x- and y coordinates int iterations = 2; int allPoints
     * = (int) Math.pow(2, iterations) * 3 + 1; double[] xpoints = new
     * double[allPoints]; // number of points after // iteration double[]
     * ypoints = new double[allPoints]; // number of points after // iteration
     * Segment[] sResult = new Segment[allPoints]; // initialize to example:
     * square xpoints[0] = 0; xpoints[1] = 1; xpoints[2] = 1; xpoints[3] = 0;
     * ypoints[0] = 0; ypoints[1] = 0; ypoints[2] = 1; ypoints[3] = 1; // actual
     * calculations BezierIteration.bezierIteration(xpoints, iterations);
     * BezierIteration.bezierIteration(ypoints, iterations);
     * BezierIteration.coordinatesToSegments(xpoints, ypoints, sResult); // test
     * String test = "Bezier:"; int counter = allPoints; while (counter > 0) {
     * test = test + "; " + xpoints[counter - 1] + ", l" + sResult[counter -
     * 1].getLength() + ", a" + sResult[counter - 1].getAngle(); counter =
     * counter - 1; } System.out.println(test); }
     */
}
