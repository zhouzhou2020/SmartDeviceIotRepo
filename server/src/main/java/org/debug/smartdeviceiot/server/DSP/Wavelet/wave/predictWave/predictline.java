package org.debug.smartdeviceiot.server.DSP.Wavelet.wave.predictWave;

/**
 Line (with slope) predict wavelets
 The wavelet Lifting Scheme predict wavelets are a proto-wavelet, which does not include an averaging step (referred to as an update step in the Lifting Scheme).
 The line wavelet "predicts" that an even point will like midway between its two neighboring even points. That is, that the odd point will lie on a line between the two adjacent even points. The difference between this "prediction" and the actual odd value replaces the odd element. */

public class predictline extends predictbase{
    /**
     Calculate an extra value for the line wavelet algorithm at the end of the data series. Here we pretend that the last two values in the data series are at the x-axis coordinates 0 and 1, respectively. We then need to calculate the data series (y-axis value) at the x-axis coordinate 2.
     Given two points, x1, y1 and x2, y2, where
     x1 = 0
     x2 = 1

     calculate the point on the line at x3, y3, where
     x3 = 2

     The "two-point equation" for a line given x1, y1 and x2, y2 is
     .          y2 - y1
     (y - y1) = -------- (x - x1)
     .          x2 - x1

     Solving for y
     .    y2 - y1
     y = -------- (x - x1) + y1
     .    x2 - x1
     Since x1 = 0 and x2 = 1
     .    y2 - y1
     y = -------- (x - 0) + y1
     .    1 - 0
     or
     y = (y2 - y1)*x + y1
     We're calculating the value at x3 = 2, so
     y = 2*y2 - 2*y1 + y1
     or
     y = 2*y2 - y1
     */
    private double new_y( double y1, double y2) {
        double y = 2 * y2 - y1;
        return y;
    }
    /**
     Predict phase of line Lifting Scheme wavelet
     The predict step attempts to "predict" the value of an odd element from the even elements. The difference between the prediction and the actual element is stored as a wavelet coefficient.
     The "predict" step takes place after the split step. The split step will move the odd elements (bj) to the second half of the array, leaving the even elements (ai) in the first half
     a0, a1, a1, a3, b0, b1, b2, b2,
     The predict step of the line wavelet "predicts" that the odd element will be on a line between two even elements.
     bj+1,i = bj,i - (aj,i + aj,i+1)/2
     Note that when we get to the end of the data series the odd element is the last element in the data series (remember, wavelet algorithms work on data series with 2n elements). Here we "predict" that the odd element will be on a line that runs through the last two even elements. This can be calculated by assuming that the last two even elements are located at x-axis coordinates 0 and 1, respectively. The odd element will be at 2. The new_y() function is called to do this simple calculation.
     */
    protected void predict( double[] vec, int N, int direction ) {
        int half = N >> 1;
        double predictVal;
        for (int i = 0; i < half; i++) {
            int j = i + half;
            if (i < half - 1) {
                predictVal = (vec[i] + vec[i + 1]) / 2;
            }
            else if (N == 2) {
            predictVal = vec[0];
            }
            else { // calculate the last "odd" prediction
                predictVal = new_y( vec[i-1], vec[i] );
            }
            if (direction == forward) {
                vec[j] = vec[j] - predictVal;
            }
            else if (direction == inverse) {
                vec[j] = vec[j] + predictVal;
            }
            else {
                System.out.println("predictline::predict: bad direction value");
            }
        }
    } // predict
}
