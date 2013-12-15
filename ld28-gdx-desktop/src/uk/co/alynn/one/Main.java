package uk.co.alynn.one;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import java.lang.Math;
import uk.co.alynn.one.world.Segment;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "ld28-gdx";
        cfg.useGL20 = false;
        cfg.width = 480;
        cfg.height = 320;

        new LwjglApplication(new OneGame(), cfg);

        bezierTest();
    }

    private static void bezierTest() {
        // example of bezier iteration
        // should be done for x- and y coordinates

    	double [] raw = {198.57143,160.93361,282.46108,127.35369,375.20155,165.64481,443.56866,215.98559,539.53351,296.97239,607.52749,408.148,649.07752,525.81327,678.46103,614.17421,685.99116,720.94964,628.60835,799.89974,541.87499,893.57105,432.06845,970.44717,307.72539,1003.8069,236.53765,1019.4626,162.18528,1026.8587,89.638477,1017.4731,48.560539,1016.2444,7.6696351,976.77886,32.811086,935.2938,69.201426,862.29636,135.27584,810.11501,195.70156,757.67071,253.83284,708.79976,316.61348,665.90868,378.06353,621.52068,414.39548,593.26798,442.51098,540.97581,417.62273,496.44847,376.16081,424.88181,291.76661,400.46169,222.5844,364.80847,179.7191,340.50379,126.13323,310.46498,119.94395,256.47023,119.49404,210.87578,162.12378,179.9799,198.57143,160.93361};
    	int inLength = raw.length/2;
    	double [] xraw = new double[inLength];
    	double [] yraw = new double[inLength];
    	// parameters
    	int iterations = 2;
    	String test = "length=" + raw.length;
        System.out.println(test);
    	
    	BezierIteration.rawData(raw, xraw, yraw);
        int allPoints = (int) Math.pow(2, iterations) * (inLength -1) + 1;
        double[] xpoints = new double[allPoints]; // number of points after
                                                  // iteration
        double[] ypoints = new double[allPoints]; // number of points after
                                                  // iteration
        Segment[] sResult = new Segment[allPoints];

        BezierIteration.discretize(iterations, xraw, yraw, xpoints, ypoints, sResult);

        // actual calculations

        BezierIteration.coordinatesToSegments(xpoints, ypoints, sResult);

        // test
        test = "Bezier:";
        int counter = allPoints;
        while (counter > 0) {
            test = test + "; " + xpoints[counter - 1] + ", l"
                    + sResult[counter - 1].getLength() + ", a"
                    + sResult[counter - 1].getAngle();
            counter = counter - 1;
        }
        System.out.println(test);
	}
}
