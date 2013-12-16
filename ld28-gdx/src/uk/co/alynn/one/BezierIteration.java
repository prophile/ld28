package uk.co.alynn.one;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class BezierIteration {
    public static void discretize(int iterations, double[] xraw, double[] yraw,
            double[] x, double[] y) {
        int startRaw = 0;
        int start = 0;
        int i = 0;
        int tmpLength = (int) Math.pow(2, iterations) * 3 + 1;
        double[] xTMP = new double[tmpLength];
        double[] yTMP = new double[tmpLength];

        while (startRaw + 3 < xraw.length) {
            // goes through input coordinates in steps of four

            // transfer raw data for this step in temporary array
            i = 0;
            while (i < 4) {
                xTMP[i] = xraw[startRaw + i];
                yTMP[i] = yraw[startRaw + i];
                i = i + 1;
            }

            BezierIteration.bezierIteration(xTMP, iterations);
            BezierIteration.bezierIteration(yTMP, iterations);

            // transfer data into final array
            i = 0;
            while (i < tmpLength) {
                x[start + i] = xTMP[i];
                y[start + i] = yTMP[i];
                i = i + 1;
            }

            // Address calculations at the end for the next loop, because start
            // address is 0
            // get next start address in x, y
            start = start + tmpLength - 1;
            // get next start address in xRaw, yRaw
            startRaw = startRaw + 3;
        }

    }

    public static void bezierIteration(double[] points, int iterations) {
        int i = 0;
        while (i < iterations) {
            int step = (int) Math.pow(2, i);
            while (step > 0) {
                step = step - 1;
                BezierIteration.bezierStep(points, step, i);
            }
            i = i + 1;
        }
    }

    public static void bezierStep(double[] points, int st, int it) {
        // st: which step of current iteration, counting down
        // it: which iteration, counting up
        // points originally contains coordinates for four bezier points
        // these are replaced with the 7 points describing the replacing two
        // bezier curves
        int data = st * 3; // where the original data comes from
        int ofs = data * 2; // where the new datapoints go

        points[ofs + 6] = points[data + 3]; // final result point 6
        points[ofs + 5] = (points[ofs + 6] + points[data + 2]) / 2; // final
                                                                    // result
                                                                    // point 5
        points[ofs + 3] = (points[data + 1] + points[data + 2]) / 2; // intermediate
                                                                     // result
        points[ofs + 1] = (points[data + 0] + points[data + 1]) / 2; // final
                                                                     // result
                                                                     // point 1
        points[ofs + 2] = (points[ofs + 1] + points[ofs + 3]) / 2; // final
                                                                   // result
                                                                   // point 2
        points[ofs + 4] = (points[ofs + 3] + points[ofs + 5]) / 2; // final
                                                                   // result
                                                                   // point 4
        points[ofs + 3] = (points[ofs + 2] + points[ofs + 4]) / 2; // final
                                                                   // result
                                                                   // point 3
    }

    public static double vectorsToAngle(double[] v1, double[] v2) {
        // calculate the angle between two vectors
        double sAngle = 0;
        double cosAngle;
        cosAngle = (v1[0] * v2[0] + v1[1] * v2[1])
                / (Math.sqrt(v1[0] * v1[0] + v1[1] * v1[1]) * Math.sqrt(v2[0]
                        * v2[0] + v2[1] * v2[1]));
        sAngle = Math.toDegrees(Math.acos(cosAngle));
        if (Double.isNaN(sAngle)) {
            sAngle = 0.000000000000000000001; // dirty
        }
        return sAngle;
    }

    public static void rawData(double[] raw, double[] x, double[] y) {
        int total = raw.length;
        int counter = 0;
        while (counter < total) {
            // split into x- and y-coordinates
            if (counter % 2 == 0) {
                x[counter / 2] = raw[counter];
            } else {
                y[(counter - 1) / 2] = raw[counter];
            }
            counter = counter + 1;
        }
    }

    public static void getBezier(List<Vector2> segs, Constants constants) {
        // int iterations = 10; //CONST
        int iterations = constants.getInt("iterations", 3,
                "Segments to draw outside of the current segment.");
        // String lala = Constants.("BezierA");

        // ***********test start
        // String test = "iterations" + iterations + lala;
        // System.out.println(test);
        // *******TEST END

        double[] first = { 198.57143, 160.93361, 282.46108, 127.35369, 375.20155,
                165.64481, 443.56866, 215.98559, 539.53351, 296.97239,
                607.52749, 408.148, 649.07752, 525.81327, 678.46103, 614.17421,
                685.99116, 720.94964, 628.60835, 799.89974, 541.87499,
                893.57105, 432.06845, 970.44717, 307.72539, 1003.8069,
                236.53765, 1019.4626, 162.18528, 1026.8587, 89.638477,
                1017.4731, 48.560539, 1016.2444, 7.6696351, 976.77886,
                32.811086, 935.2938, 69.201426, 862.29636, 135.27584,
                810.11501, 195.70156, 757.67071, 253.83284, 708.79976,
                316.61348, 665.90868, 378.06353, 621.52068, 414.39548,
                593.26798, 442.51098, 540.97581, 417.62273, 496.44847,
                376.16081, 424.88181, 291.76661, 400.46169, 222.5844,
                364.80847, 179.7191, 340.50379, 126.13323, 310.46498,
                119.94395, 256.47023, 119.49404, 210.87578, 162.12378,
                179.9799, 198.57143, 160.93361 };
        // double[] square = { 0, 0, 1000, 0, 1000, 1000, 0, 1000 }; // CONST
        // double[] raw = { 194.28571, 198.07647, 340, 329.50504, 391.42857,
        // 558.07647, 237.14286, 455.21933, 82.857143, 352.36218,
        // -51.428571, 243.79075, 65.714286, 218.07647, 182.85714,
        // 192.36218, 194.28571, 198.07647, 194.28571, 198.07647 };
        
        double[] star = {206.07112,189.85766,242.31271,141.10991,331.34717,224.15456,387.89858,201.97949,449.98952,177.63225,458.80706,51.08517,525.27932,56.517523,590.06291,61.811872,573.48965,195.38627,634.3758,218.14193,707.518,245.47818,811.91574,96.524366,860.64997,157.53278,914.79662,225.31678,717.8176,303.82754,739.43166,387.84756,757.11452,456.58589,928.93734,435.02304,917.21851,505.02526,908.26512,558.50821,791.46546,501.52928,759.63471,545.43136,723.18997,595.69722,824.21462,698.08759,771.75655,731.29943,713.17568,768.38769,653.94751,603.65904,589.92908,630.28417,530.48274,655.00778,600.63037,804.02004,537.40115,816.15223,465.70421,829.90918,473.1258,666.06527,404.06102,642.406,348.74048,623.45505,276.47215,727.41969,234.35539,686.85271,189.63777,643.78057,307.10734,558.02168,274.76149,505.02525,244.29694,455.1112,114.87983,518.22392,105.05586,460.57853,94.136023,396.50279,246.90496,413.4004,266.68027,351.48207,284.18559,296.67129,171.74196,236.03305,206.07112,189.85766};
        
        double[] raw = new double[star.length];
        for (int k = 0; k<star.length; k++){
        	raw[k] = star[k];
        }
        int inLength = raw.length / 2;
        double[] xraw = new double[inLength];
        double[] yraw = new double[inLength];
        int allPoints = (int) Math.pow(2, iterations) * (inLength - 1) + 1;
        double[] xpoints = new double[allPoints]; // number of points after
                                                  // iteration
        double[] ypoints = new double[allPoints]; // number of points after
                                                  // iteration

        BezierIteration.rawData(raw, xraw, yraw);

        BezierIteration.discretize(iterations, xraw, yraw, xpoints, ypoints);

        for (int i = 0; i < allPoints; ++i) {
            segs.add(new Vector2((float) xpoints[i], (float) ypoints[i]));
        }
    }
}
