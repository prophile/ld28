package uk.co.alynn.one;

import java.lang.Math;
import java.util.List;

import uk.co.alynn.one.world.Segment;

public class BezierIteration{
    public static void discretize(int iterations, double [] xraw, double [] yraw, double [] x, double [] y, Segment [] s){
    	int startRaw = 0;
    	int start = 0;
    	int i = 0;
        int tmpLength = (int) Math.pow(2, iterations) * 3 + 1;
    	double [] xTMP = new double[tmpLength];
    	double [] yTMP = new double[tmpLength];
    	
    	while ( startRaw + 3 < xraw.length ){
    		// goes through input coordinates in steps of four
    		
    		// transfer raw data for this step in temporary array
    		i = 0;
    		while (i<4){
     			xTMP[i] = xraw[startRaw + i];
    			yTMP[i] = yraw[startRaw + i];
    			i = i+1;
    		}
    		
    		BezierIteration.bezierIteration(xTMP, iterations);
            BezierIteration.bezierIteration(yTMP, iterations);
            
            // transfer data into final array
            i = 0;
            while (i<tmpLength){
            	x[start+i] = xTMP[i];
            	y[start+i] = yTMP[i];
            	i = i+1;
            }
            
            // calculate Segments from final coordinate arrays
            BezierIteration.coordinatesToSegments(x, y, s);
            
    		// Address calculations at the end for the next loop, because start address is 0
    		// get next start address in x, y
    		start = start + tmpLength - 1;    		
    		// get next start address in xRaw, yRaw
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
       	if ((x[counter] == x[0]) && (y[counter] == y[0])){
    			// dirty solution
    		    // if the bezier curve is closed and the start and endpoint are the same
    			// distance is zero
    		    // add a small distance in x inbetween them
    				v1[0] = 0.00000000000000001;
    			}
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
    	   			if ((x[counter] == x[elements]) && (y[counter] == y[elements])){
    	    			// dirty solution
    	    		    // if the bezier curve is closed and the start and endpoint are the same
    	    			// distance is zero
    	    		    // add a small x distance inbetween them
    	    				v2[0] = 0.00000000000000001;
    	   			}
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
        cosAngle = (v1[0] * v2[0] + v1[1] * v2[1])/ ((Math.sqrt(v1[0] * v1[0] + v1[1] * v1[1]) * Math.sqrt(v2[0]* v2[0] + v2[1] * v2[1])));
        sAngle = Math.toDegrees(Math.acos(cosAngle));
        if (Double.isNaN(sAngle)){
        	sAngle = 0.000000000000000000001;    //dirty
        }
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
        	counter = counter + 1;
        }
    }
    public static void getBezier(List<Segment> segs){
       	int iterations = 10; //CONST
    	//double [] raw = {198.57143,160.93361,282.46108,127.35369,375.20155,165.64481,443.56866,215.98559,539.53351,296.97239,607.52749,408.148,649.07752,525.81327,678.46103,614.17421,685.99116,720.94964,628.60835,799.89974,541.87499,893.57105,432.06845,970.44717,307.72539,1003.8069,236.53765,1019.4626,162.18528,1026.8587,89.638477,1017.4731,48.560539,1016.2444,7.6696351,976.77886,32.811086,935.2938,69.201426,862.29636,135.27584,810.11501,195.70156,757.67071,253.83284,708.79976,316.61348,665.90868,378.06353,621.52068,414.39548,593.26798,442.51098,540.97581,417.62273,496.44847,376.16081,424.88181,291.76661,400.46169,222.5844,364.80847,179.7191,340.50379,126.13323,310.46498,119.94395,256.47023,119.49404,210.87578,162.12378,179.9799,198.57143,160.93361};
    	double [] raw = {0,0,1000,0,1000,1000,0,1000}; //CONST
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
    }
}
