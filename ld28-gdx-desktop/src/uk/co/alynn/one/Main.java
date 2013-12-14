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
		
		new LwjglApplication(new OneGame(), cfg);
		int [] points = new int [14]; //number of points after iteration * 2 (x, y)
		int iterations = 1;
		BezierRecursion.bezierRecursion(points , iterations);
	}
}
