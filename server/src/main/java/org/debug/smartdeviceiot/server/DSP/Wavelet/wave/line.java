package org.debug.smartdeviceiot.server.DSP.Wavelet.wave;

public class line extends liftbase{
    private double new_y( double y1, double y2) { double y = 2 * y2 - y1; return y; }
    /**
     Predict phase of line Lifting Scheme wavelet
     The predict step attempts to "predict" the value of an odd element from the even elements. The difference between the prediction and the actual element is stored as a wavelet coefficient.
     The "predict" step takes place after the split step. The split step will move the odd elements (bj) to the second half of the array, leaving the even elements (ai) in the first half
     a0, a1, a2, a3, b0, b1, b2, b2,
     The predict step of the line wavelet "predicts" that the odd element will be on a line between two even elements.
     bj+1,i = bj,i - (aj,i + aj,i+1)/2
     Note that when we get to the end of the data series the odd element is the last element in the data series (remember, wavelet algorithms work on data series with 2n elements). Here we "predict" that the odd element will be on a line that runs through the last two even elements. This can be calculated by assuming that the last two even elements are located at x-axis coordinates 0 and 1, respectively. The odd element will be at 2. The new_y() function is called to do this simple calculation.
     */
    protected void predict( double[] vec, int N, int direction ) {
        int half = N >> 1;
        double predictVal;
        for (int i = 0; i < half; i++) {
            int j = i + half;
            if (i < half-1) {
                predictVal = (vec[i] + vec[i+1])/2;
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
    /**
     The predict phase works on the odd elements in the second half of the array. The update phase works on the even elements in the first half of the array. The update phase attempts to preserve the average. After the update phase is completed the average of the even elements should be approximately the same as the average of the input data set from the previous iteration. The result of the update phase becomes the input for the next iteration.
     In a Haar wavelet the average that replaces the even element is calculated as the average of the even element and its associated odd element (e.g., its odd neighbor before the split). This is not possible in the line wavelet since the odd element has been replaced by the difference between the odd element and the mid-point of its two even neighbors. As a result, the odd element cannot be recovered.
     The value that is added to the even element to preserve the average is calculated by the equation shown below. This equation is given in Wim Sweldens' journal articles and his tutorial (Building Your Own Wavelets at Home) and in Ripples in Mathematics. A somewhat more complete derivation of this equation is provided in Ripples in Mathematics by A. Jensen and A. la Cour-Harbo, Springer, 2001.
     The equation used to calculate the average is shown below for a given iteratin i. Note that the predict phase has already completed, so the odd values belong to iteration i+1.
     eveni+1,j = eveni,j op (oddi+1,k-1 + oddi+1,k)/4
     There is an edge problem here, when i = 0 and k = N/2 (e.g., there is no k-1 element). We assume that the oddi+1,k-1 is the same as oddk. So for the first element this becomes
     (2 * oddk)/4
     or
     oddk/2
     */
    protected void update( double[] vec, int N, int direction ) {
        int half = N >> 1;
        for (int i = 0; i < half; i++) {
            int j = i + half;
            double val;
            if (i == 0) { val = vec[j]/2.0; }
            else { val = (vec[j-1] + vec[j])/4.0; }
            if (direction == forward) { vec[i] = vec[i] + val; }
            else if (direction == inverse) { vec[i] = vec[i] - val; }
            else { System.out.println("update: bad direction value"); }
        } // for
    }  // line
}
/**
 Line (with slope) wavelet
 The wavelet Lifting Scheme "line" wavelet approximates the data set using a line with with slope (in contrast to the Haar wavelet where a line has zero slope is used to approximate the data).
 The predict stage of the line wavelet "predicts" that an odd point will lie midway between its two neighboring even points. That is, that the odd point will lie on a line between the two adjacent even points. The difference between this "prediction" and the actual odd value replaces the odd element.
 The update stage calculates the average of the odd and even element pairs, although the method is indirect, since the predict phase has over written the odd value. */