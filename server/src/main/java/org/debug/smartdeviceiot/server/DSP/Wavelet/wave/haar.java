package org.debug.smartdeviceiot.server.DSP.Wavelet.wave;
/**
 Haar (flat line) wavelet.
 As with all Lifting scheme wavelet transform functions, the first stage of a transform step is the split stage. The split step moves the even element to the first half of an N element region and the odd elements to the second half of the N element region.
 The Lifting Scheme version of the Haar transform uses a wavelet function (predict stage) that "predicts" that an odd element will have the same value as it preceeding even element. Stated another way, the odd element is "predicted" to be on a flat (zero slope line) shared with the even point. The difference between this "prediction" and the actual odd value replaces the odd element.
 The wavelet scaling function (a.k.a. smoothing function) used in the update stage calculates the average between an even and an odd element.
 The merge stage at the end of the inverse transform interleaves odd and even elements from the two halves of the array (e.g., ordering them even0, odd0, even1, odd1, ...)
*/

public class haar extends liftbase { /** Haar predict step */
    protected void predict( double[] vec, int N, int direction ) {
        int half = N >> 1;
        int cnt = 0;
        for (int i = 0; i < half; i++) {
            double predictVal = vec[i];
            int j = i + half;
            if (direction == forward) {
                vec[j] = vec[j] - predictVal;
            }
            else if (direction == inverse) {
                vec[j] = vec[j] + predictVal;
            }
            else {
                System.out.println("haar::predict: bad direction value");
            }
        }
    }
    /**Update step of the Haar wavelet transform.
 The wavelet transform calculates a set of detail or difference coefficients in the predict step. These are stored in the upper half of the array. The update step calculates an average from the even-odd element pairs. The averages will replace the even elements in the lower half of the array.
 The Haar wavelet calculation used in the Lifting Scheme is
 dj+1, i = oddj+1, i = oddj, i - evenj, i
 aj+1, i = evenj, i = (evenj, i + oddj, i)/2
 Note that the Lifting Scheme uses an in-place algorithm. The odd elements have been replaced by the detail coefficients in the predict step. With a little algebra we can substitute the coefficient calculation into the average calculation, which gives us
 aj+1, i = evenj, i = evenj, i + (oddj, i/2)
 */
    protected void update( double[] vec, int N, int direction ) {
        int half = N >> 1;
        for (int i = 0; i < half; i++) {
            int j = i + half;
            double updateVal = vec[j] / 2.0;
            if (direction == forward) {
                vec[i] = vec[i] + updateVal;
            }
            else if (direction == inverse) {
                vec[i] = vec[i] - updateVal;
            }
            else { System.out.println("update: bad direction value");
            }
        }
    }
}
