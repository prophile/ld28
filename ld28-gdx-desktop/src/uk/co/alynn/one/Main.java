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
		int iterations = 3;
		int counter = (((int)Math.pow(2,iterations))*3) + 1;

		// example of bezier iteration
		// should be done for x- and y coordinates
		String test = "Bezier:";
		float [] xpoints = new float [counter]; //number of points after iteration
		xpoints[0] = 0;
		xpoints[1] = 1;
		xpoints[2] = 2;
		xpoints[3] = 3;

		BezierIteration.bezierIteration(xpoints, iterations);
		System.out.println(test);
		// TESTING
		while (counter > 0){
			test = test + " and " + xpoints[counter - 1];
			counter = counter - 1;
		}
        System.out.println(test);
	}
}
