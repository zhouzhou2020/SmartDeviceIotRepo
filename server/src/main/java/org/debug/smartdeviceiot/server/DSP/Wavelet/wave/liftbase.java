package org.debug.smartdeviceiot.server.DSP.Wavelet.wave;
import com.dlut.Wavelet.util.*;
/**
 class liftbase: base class for simple Lifting Scheme wavelets using split, predict, update or update, predict, merge steps.
 Simple lifting scheme wavelets consist of three steps, a split/merge step, predict step and an update step:
 The split step divides the elements in an array so that the even elements are in the first half and the odd elements are in the second half.
 The merge step is the inverse of the split step. It takes two regions of an array, an odd region and an even region and merges them into a new region where an even element alternates with an odd element.
 The predict step calculates the difference between an odd element and its predicted value based on the even elements. The difference between the predicted value and the actual value replaces the odd element.
 The predict step operates on the odd elements. The update step operates on the even element, replacing them with a difference between the predict value and the actual odd element. The update step replaces each even element with an average. The result of the update step becomes the input to the next recursive step in the wavelet calculation.
 The split and merge methods are shared by all Lifting Scheme wavelet algorithms. This base class provides the transform and inverse transform methods (forwardTrans and inverseTrans). The predict and update methods are abstract and are defined for a particular Lifting Scheme wavelet sub-class.

 */

public abstract class liftbase {
    /**
     * "enumeration" for forward wavelet transform
     */
    protected final int forward = 1;
    /**
     * "enumeration" for inverse wavelet transform
     */
    protected final int inverse = 2;
    /**
     * Split the vec into even and odd elements, where the even elements are in the first half of the vector and the odd elements are in the second half.
     */
    protected void split(double[] vec, int N) {
        int start = 1;
        int end = N - 1;
        while (start < end) {
            for (int i = start; i < end; i = i + 2) {
                double tmp = vec[i];
                vec[i] = vec[i + 1];
                vec[i + 1] = tmp;
            }
            start = start + 1;
            end = end - 1;
        }
    }

    /**
     * Merge the odd elements from the second half of the N element region in the array with the even elements in the first half of the N element region. The result will be the combination of the odd and even elements in a region of length N.
     */
    protected void merge(double[] vec, int N) {
        int half = N >> 1;
        int start = half - 1;
        int end = half;
        while (start > 0) {
            for (int i = start; i < end; i = i + 2) {
                double tmp = vec[i];
                vec[i] = vec[i + 1];
                vec[i + 1] = tmp;
            }
            start = start - 1;
            end = end + 1;
        }
    }

    /**
     * Predict step, to be defined by the subclass @param vec input array @param N size of region to act on (from 0..N-1) @param direction forward or inverse transform
     */
    protected abstract void predict(double[] vec, int N, int direction);

    /**
     * Update step, to be defined by the subclass @param vec input array @param N size of region to act on (from 0..N-1) @param direction forward or inverse transform
     */
    protected abstract void update(double[] vec, int N, int direction);

    /**
     * Simple wavelet Lifting Scheme forward transform
     * forwardTrans is passed an array of doubles. The array size must be a power of two. Lifting Scheme wavelet transforms are calculated in-place and the result is returned in the argument array.
     * The result of forwardTrans is a set of wavelet coefficients ordered by increasing frequency and an approximate average of the input data set in vec[0]. The coefficient bands follow this element in powers of two (e.g., 1, 2, 4, 8...).
     */
    public void forwardTrans(double[] vec) {
        final int N = vec.length;
        for (int n = N; n > 1; n = n >> 1) {
            split(vec, n);
            predict(vec, n, forward);
            update(vec, n, forward);
        }
    } // forwardTrans

    /**
     * Default two step Lifting Scheme inverse wavelet transform
     * inverseTrans is passed the result of an ordered wavelet transform, consisting of an average and a set of wavelet coefficients. The inverse transform is calculated in-place and the result is returned in the argument array.
     */
    public void inverseTrans(double[] vec) {
        final int N = vec.length;
        for (int n = 2; n <= N; n = n << 1) {
            update(vec, n, inverse);
            predict(vec, n, inverse);
            merge(vec, n);
        }
    } // inverseTrans
}
