package uk.co.alynn.one;

import java.lang.Math;

import uk.co.alynn.one.world.Segment;

public class BezierIteration{

    public static void bezierIteration(float [] points , int iterations){
        int i = 0;
        while (i < iterations){
        	int step = ((int)Math.pow(2,i));
        	while (step > 0){
        		step = step - 1;
        		BezierIteration.bezierStep(points, step, i);
        	}
            i = i + 1;
        }
    }
    
    public static void bezierStep(float [] points, int st, int it){
    // st: which step of current iteration, counting down
    // it: which iteration, counting up
    // points originally contains coordinates for four bezier points
    // these are replaced with the 7 points describing the replacing two bezier curves
        int data = st*3; // where the original data comes from
        int ofs = data*2; // where the new datapoints go

    	points[ofs + 6] = points[data + 3];							// final result point 6
    	points[ofs + 5] = (points[ofs + 6] + points[data + 2])/2;  	// final result point 5
    	points[ofs + 3] = (points[data + 1] + points[data + 2])/2;  // intermediate result
    	points[ofs + 1] = (points[data + 0] + points[data + 1])/2;  // final result point 1
    	points[ofs + 2] = (points[ofs + 1] + points[ofs + 3])/2;  	// final result point 2
    	points[ofs + 4] = (points[ofs + 3] + points[ofs + 5])/2;  	// final result point 4
    	points[ofs + 3] = (points[ofs + 2] + points[ofs + 4])/2;  	// final result point 3
    }
    
    public static void coordinatesToSegments(float [] x, float [] y, Segment [] s){
    	// calculates segments from coordinates
    	// s[0] : segment that connects coordinate 0 and coordinate 1
    	// last element: segment that connects last coordinate with coordinate 0
    	int counter = x.length;

    	// calculate last segment: 
    	while (counter > 0){
    		// calculate segments
    		counter = counter - 1;
    	}
    }

} 

