package org.debug.smartdeviceiot.server.DSP.Wavelet.wave;

class daubbook {
    final static double sqrt3 = Math.sqrt( 3 );
    final static double sqrt2 = Math.sqrt( 2 );
    /** Split the vec into even and odd elements, where the even elements are in the first half of the vector and the odd elements are in the second half. */
    protected void split( double[] vec, int N ) {
        int start = 1; int end = N - 1;
        while (start < end) {
        for (int i = start; i < end; i = i + 2) {
            double tmp = vec[i];
            vec[i] = vec[i+1];
            vec[i+1] = tmp;
        }
        start = start + 1;
        end = end - 1;
        }
    }

    /** Merge the odd elements from the second half of the N element region in the array with the even elements in the first half of the N element region. The result will be the combination of the odd and even elements in a region of length N. */
    protected void merge( double[] vec, int N ) {
        int half = N >> 1;
        int start = half-1;
        int end = half;
        while (start > 0) {
            for (int i = start; i < end; i = i + 2) {
                double tmp = vec[i];
                vec[i] = vec[i+1];
                vec[i+1] = tmp;
            }
            start = start - 1;
            end = end + 1;
        }
    }

    /** Forward step of the Daubechies D4 transform */
    protected void forwardStep( double[] S, int N ) {
        final int half = N/2; // update 1
        for (int n = 0; n < half; n++)
            S[n] = S[n] + sqrt3 * S[half+n]; // predict
        S[half] = S[half] - (sqrt3/4.0)*S[0] - (((sqrt3-2)/4.0)*S[half-1]);
        for (int n = 1; n < half; n++)
            S[half+n] = S[half+n] - (sqrt3/4.0)*S[n] - (((sqrt3-2)/4.0)*S[n-1]); // update 2
        for (int n = 0; n < half-1; n++)
            S[n] = S[n] - S[half+n+1];
        S[half-1] = S[half-1] - S[half]; // normalize
        for (int n = 0; n < half; n++) {
            S[n] = ((sqrt3-1.0)/sqrt2) * S[n];
            S[n+half] = ((sqrt3+1.0)/sqrt2) * S[n+half];
        }
    } // forwardStep

    /** Inverse step of the Daubechies D4 transform */
    protected void inverseStep( double[] S, int N ) {
        final int half = N/2;
        for (int n = 0; n < half; n++) {
             S[n] = ((sqrt3+1.0)/sqrt2) * S[n];
             S[n+half] = ((sqrt3-1.0)/sqrt2) * S[n+half];
        }
        for (int n = 0; n < half-1; n++)
            S[n] = S[n] + S[half+n+1];
        S[half-1] = S[half-1] + S[half];
        S[half] = S[half] + (sqrt3/4.0)*S[0] + (((sqrt3-2)/4.0)*S[half-1]);
        for (int n = 1; n < half; n++)
            S[half+n] = S[half+n] + (sqrt3/4.0)*S[n] + (((sqrt3-2)/4.0)*S[n-1]);
        for (int n = 0; n < half; n++)
            S[n] = S[n] - sqrt3 * S[half+n];
    } // inverseStep

     /** Forward Daubechies transform, implemented with a single monolithic forward step. */
     public void forwardTrans( double[] vec ) {
         final int N = vec.length;
         for (int n = N; n > 1; n = n >> 1) {
             split(vec, n);
             forwardStep(vec, n);
         }
     }// forwardTrans

    /** Inverse Daubechies transform, implemented with a single monolithic inverse step. */
    public void inverseTrans( double[] vec ) {
        final int N = vec.length;
        for (int n = 2; n <= N; n = n << 1) {
            inverseStep( vec, n );
            merge(vec, n );
        }
    } // inverseTrans
}
