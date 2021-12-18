package org.debug.smartdeviceiot.server.DSP.Wavelet.wave.predictWave;


/**
 class predictbase: base class for ur-Lifting Scheme wavelets using split/predict or predict/merge steps.
 Predict wavelets are a prototype for Lifting Scheme wavlets. Like the Lifting Scheme wavelets, predict wavelets can be thought of as a set of steps that perform the in-place wavelet calculation. The building blocks consist of
 A split block which splits an array so that the even elements are in the first half and the odd elements are in the second half.
 A predict block which calculates the difference between an odd element and its predicted value based on the even elements.
 A merge block which merges odd and even values back together (this is used in the inverse transform).
 The split and merge methods are shared by all Lifting Scheme wavelet algorithms. This base class provides the transform and inverse transform methods (forwardTrans and inverseTrans). The predict method is an abstract method that is defined for a particular predict wavelet sub-class. */

public abstract class predictbase {
    /** "enumeration" for forward wavelet transform */
    protected final int forward = 1;
    /** "enumeration" for inverse wavelet transform */
    protected final int inverse = 2;
    /** Move the odd elements to the second half of the N element region in the vec array. */
    protected void split( double[] vec, int N ) {
        int start = 1;
        int end = N - 1;
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
                double tmp = vec[i]; vec[i] = vec[i+1]; vec[i+1] = tmp;
            }
            start = start - 1;
            end = end + 1;
        }
    }

    /** Round a double to four decimal places. 四舍五入到小数点后四位 */
    private double round4( double d ) {
        final double multiplier = 10000.0;
        double rounded;
        rounded = Math.round(d * multiplier)/multiplier;
        return rounded;
    } // round4

    /**
    Print the result of an ordered wavelet transform. the result is printed to show the coefficient (difference) bands, where the bands are in increasing frequency.
    @param //vec[] result of a wavelet transform */
    public void pr_ordered( double[] vec ) {
        if (vec != null) {
            int len = vec.length;
            if (len > 0) {
                System.out.println(round4(vec[0]));
                int num_in_freq = 1;
                int cnt = 0;
                for (int i = 1; i < len; i++) {
                    System.out.print(round4(vec[i]) + " ");
                    cnt++;
                    if (cnt == num_in_freq) {
                        System.out.println();
                        cnt = 0;
                        num_in_freq = num_in_freq * 2;
                    }
                }
            }
        }
    } // pr_ordered

    /** Predict step, to be defined by the subclass @param vec input array @param N size of region to act on (from 0..N-1) @param direction forward or inverse transform */
    protected abstract void predict( double[] vec, int N, int direction );
    /**
    Predict wavelet forward transform
    forwardTrans is passed an array of doubles. The array size must be a power of two. The predict wavelet transform is calculated in-place and the result is returned in the argument array.
    The result of forwardTrans is a set of differences, ordered by powers of two. Element vec[0] will contain the element that started the data set. Element vec[1] will contain the difference between the prediction for vec[1] and the original value at vec[1]. The next set of differences will be at vec[2..3]... the differences at vec[N/2..N-1] will contain the largest set of differences.
*/
    public void forwardTrans( double[] vec ) {
        final int N = vec.length;
        for (int n = N; n > 1; n = n >> 1) {
            split( vec, n );
            predict( vec, n, forward );
        }
    } // forwardTrans
    /**
    Default two step Lifting Scheme inverse wavelet transform
    inverseTrans is passed the result of an ordered set of predict wavelet coefficients. The inverse transform is calculated in-place and the result is returned in the argument array.
            */
    public void inverseTrans( double[] vec ) {
        final int N = vec.length;
        for (int n = 2; n <= N; n = n << 1) {
            predict( vec, n, inverse );
            merge( vec, n );
        }
    } // inverseTrans }
}
