package uk.co.alynn.one;

import java.lang.Math;

import uk.co.alynn.one.world.Segment;

public class BezierIteration{
    public static void discretize(int iterations, double [] xraw, double [] yraw, double [] x, double [] y, Segment [] s){
    	int startRaw = 0;
    	int start = 0;
    	int i = 0;
        int pointsTMP = (int) Math.pow(2, iterations) * 3 + 1;
    	double [] xTMP = new double[pointsTMP];
    	double [] yTMP = new double[pointsTMP];
    	
    	
    	while ( (startRaw/3)<iterations ){
    		// transfer raw data for this step in temporary array
    		i = 0;
    		while (i<4){
    			xTMP[i] = xraw[startRaw + i];
    			yTMP[i] = yraw[startRaw + i];
    			i = i+1;
    		}
    		
    		// Address calculations at the end for the next loop, because start address is 0
    		// get next start address in x, y
    		start = start + (int) Math.pow(4, iterations) - 1;
            BezierIteration.bezierIteration(xTMP, iterations);
            BezierIteration.bezierIteration(yTMP, iterations);
            
            // transfer data into final array
            // calculate Segments from final x, y arrays
    		
    		// get next start address in xraw, yraw
    		startRaw = startRaw + 3;
    	}
    	
    }
	
    public static void bezierIteration(double [] points , int iterations){
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
    
    public static void bezierStep(double [] points, int st, int it){
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
    
    public static void coordinatesToSegments(double [] x, double [] y, Segment [] s){
    	// calculates segments from coordinates, 
    	// independent of array length
    	// all input arrays have to be the same length
    	// s[0] : segment that connects coordinate 0 and coordinate 1
    	// segment angle: angle between this element and the next
    	// last element: segment that connects last coordinate with coordinate 0
    	int elements = x.length - 1;
    	int counter = elements;
    	double sAngle = 0;
    	double sLength = 1;
    	double [] v1 = new double [2], v2 = new double [2];
    	

    	// calculate last segment
    	sLength = Math.sqrt(Math.pow((x[counter]-x[0]),2) + Math.pow((y[counter]-y[0]),2));
    	v1[0] = x[0] - x[counter];
    	v1[1] = y[0] - y[counter];
    	v2[0] = x[counter] - x[counter-1];
    	v2[1] = y[counter] - y[counter-1];
    	sAngle = BezierIteration.vectorsToAngle(v1, v2);

    	s[counter] = new Segment(sLength, sAngle);

    	// calculate other segments
    	while (counter > 0){
    		counter = counter - 1;
    		// calculate length
    		sLength = Math.sqrt((Math.pow((x[counter]-x[counter + 1]), 2) + Math.pow((y[counter]-y[counter + 1]), 2)));
    		
    		// calculate angle
    		if (counter == 0){
            	v1[0] = x[counter + 1] - x[counter];
            	v1[1] = y[counter + 1] - y[counter];
            	v2[0] = x[counter] - x[elements];   // use the last element
            	v2[1] = y[counter] - y[elements];   // use the last element
            	sAngle = BezierIteration.vectorsToAngle(v1, v2);
    			
    		}else{
    			v1[0] = x[counter + 1] - x[counter];
    			v1[1] = y[counter + 1] - y[counter];
    			v2[0] = x[counter] - x[counter-1];
    			v2[1] = y[counter] - y[counter-1];
    			sAngle = BezierIteration.vectorsToAngle(v1, v2);
    		}
        	s[counter] = new Segment(sLength, sAngle);
    	}
    }

    public static double vectorsToAngle(double[] v1, double[] v2) {
        // calculate the angle between two vectors
        double sAngle = 0;
        double cosAngle;
        cosAngle = (v1[0] * v2[0] + v1[1] * v2[1])
                / (Math.sqrt(v1[0] * v1[0] + v1[1] * v1[1]) + Math.sqrt(v2[0]
                        * v2[0] + v2[1] * v2[1]));
        sAngle = Math.toDegrees(Math.acos(cosAngle));
        return sAngle;
    }
    
    
    public static void rawData(double[] raw, double[] x, double[] y) {
        int total = raw.length;
        int counter = 0;
        while (counter < total){
        	// split into x- and y-coordinates
        	if (counter%2 == 0){
        		x[counter/2] = raw[counter];
        	} else{
        		y[(counter-1)/2] = raw[counter];
        	}
        }
    }
}
