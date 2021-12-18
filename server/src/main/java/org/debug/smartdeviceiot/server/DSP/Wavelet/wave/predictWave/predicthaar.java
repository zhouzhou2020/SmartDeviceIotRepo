package org.debug.smartdeviceiot.server.DSP.Wavelet.wave.predictWave;

/**
 Haar (flat line) predict wavelets.
 The wavelet Lifting Scheme predict wavelets are a proto-wavelet, which does not include an averaging step (referred to as an update step in the Lifting Scheme).
 The predicthaar class has a Haar wavelet predict step. The Haar predict step "predicts" that an odd element will have the same value as it preceeding even element. Stated another way, the odd element is "predicted" to be on a float (zero slope line) shared with the odd point. The difference between this "prediction" and the actual odd value replaces the odd element.
 Before the predict step is invoked, the split step has run. The split step moves the odd elements to the second half of the N element region. */

public class predicthaar extends predictbase{
    /** Haar predict step */
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
                System.out.println("predicthaar::predict: bad direction value");
            }
        }
    }
} // predicthaar

