package org.debug.smartdeviceiot.server.DSP.Wavelet.matrix.linear;

/** Wavelet forward and inverse transform using matrices. */
public class matWavelet {
    /** Move the odd elements to the second half of the N element region in the vec array. */ private void split( double[] vec, int N ) { int start = 1; int end = N - 1; while (start < end) { for (int i = start; i < end; i = i + 2) { double tmp = vec[i]; vec[i] = vec[i+1]; vec[i+1] = tmp; } start = start + 1; end = end - 1; } } /** Merge the odd elements from the second half of the N element region in the array with the even elements in the first half of the N element region. The result will be the combination of the odd and even elements in a region of length N. */ private void merge( double[] vec, int N ) { int half = N >> 1; int start = half-1; int end = half; while (start > 0) { for (int i = start; i < end; i = i + 2) { double tmp = vec[i]; vec[i] = vec[i+1]; vec[i+1] = tmp; } start = start - 1; end = end + 1; } } /**
     Calculate one wavelet transform step on the data set in "v" using a transform matrix m. The result is returned with the wavelet result (wavelet coefficients) in the upper half of the array and the scaling result (smoothed values) in the lower half.
     The ordering is the matrix is scaling, wavelet, scaling, ... wavelet.
     Note that the matrix m must be NxN
     */ public void transStep( double m[][], double v[] ) {
         int N = m.length;
         double scale, wave;
         linearOps ops = new linearOps();
         for (int i = 0; i < N; i = i + 2) {
             scale = ops.dotprod(m[i], v );
             wave = ops.dotprod(m[i+1], v );
             v[i] = scale; v[i+1] = wave;
         }
         split( v, N );
     } // transStep
    /** Calculate one inverse transform step on the input vector v using the inverse transform matrix m.
     * The result is returned in the input vector v. */
    public void invTransStep( double m[][], double v[] ) {
        int N = m.length;
        double v1, v2;
        linearOps ops = new linearOps();
        merge(v, N);
        for (int i = 0; i < N; i = i + 2) {
            v1 = ops.dotprod( m[i], v );
            v2 = ops.dotprod( m[i+1], v );
            v[i] = v1;
            v[i+1] = v2;
        }
    } // invTransStep } // matWavelet
}
