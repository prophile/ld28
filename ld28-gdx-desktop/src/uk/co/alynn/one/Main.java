package uk.co.alynn.one;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import java.lang.Math;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "ld28-gdx";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new OneGame(), cfg);

		// example of bezier iteration
		// should be done for x- and y coordinates

		int iterations = 1;
		

		int allPoints = (((int)Math.pow(2,iterations))*3) + 1;
		float [] xpoints = new float [allPoints]; //number of points after iteration
		xpoints[0] = 0;
		xpoints[1] = 1;
		xpoints[2] = 2;
		xpoints[3] = 3;
		
		BezierIteration.bezierIteration(xpoints, iterations);

		// test bezier iteration
		String test = "Bezier:";
		int counter = allPoints;
		while (counter > 0){
			test = test + "; " + xpoints[counter - 1];
			counter = counter - 1;
		}
        System.out.println(test);
	}
}
