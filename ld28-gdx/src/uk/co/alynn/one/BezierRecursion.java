package uk.co.alynn.one;

public class BezierRecursion{

    public static void bezierRecursion(float [] points , int iterations){
    	String test = "iterations " + iterations + " points " + points[0];
        System.out.println(test);

    }
    
    public static void bezierStep(int [] points ){
    // points originally contains coordinates for four bezier points
    // these are replaced with the 7 points describing the replacing two bezier curves
    	points[6] = points[3];					// final result point 6
    	points[5] = (points[6] + points[2])/2;  // final result point 5
    	points[3] = (points[1] + points[2])/2;  // intermediate result
    	points[1] = (points[0] + points[1])/2;  // final result point 1
    	points[2] = (points[1] + points[3])/2;  // final result point 2
    	points[4] = (points[3] + points[5])/2;  // final result point 4
    	points[3] = (points[2] + points[4])/2;  // final result point 3
    }
} 

