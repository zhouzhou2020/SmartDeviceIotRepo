package org.debug.smartdeviceiot.server.DSP.Wavelet.matrix;

import org.debug.smartdeviceiot.server.DSP.Wavelet.matrix.linear.linearPr;
import org.debug.smartdeviceiot.server.DSP.Wavelet.matrix.linear.matWavelet;

/**
 Demonstrate how the Haar wavelet transform can be calculated using matrices.
 The even rows of a forward transform matrix calculate the Haar wavelet scaling function. This is a smoothed value, which in the case of the Haar transform is simply the average of two values. The scaling function is sometimes referred to as a low pass filter.
 The odd rows in the forward transform matrix calculate the Haar wavelet function. This represents the change between two elements. In the case of this version of the Haar algorithm, this is the average difference between two adjacent values. The wavelet function is sometimes referred to as a high pass filter.
 */
public class haarDemo {
    // Haar wavelet forward transform matrices //
    static final double fwdMat16[][] = {
            {0.5, 0.5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0.5,-0.5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0.5, 0.5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0.5,-0.5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0.5, 0.5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0.5,-0.5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0.5, 0.5, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0.5,-0.5, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0.5, 0.5, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0.5,-0.5, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.5, 0.5, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.5,-0.5, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.5, 0.5, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.5,-0.5, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.5, 0.5},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.5,-0.5}};
    static final double fwdMat8[][] = {
            {0.5, 0.5, 0, 0, 0, 0, 0, 0},
            {0.5,-0.5, 0, 0, 0, 0, 0, 0},
            {0, 0, 0.5, 0.5, 0, 0, 0, 0},
            {0, 0, 0.5,-0.5, 0, 0, 0, 0},
            {0, 0, 0, 0, 0.5, 0.5, 0, 0},
            {0, 0, 0, 0, 0.5,-0.5, 0, 0},
            {0, 0, 0, 0, 0, 0, 0.5, 0.5},
            {0, 0, 0, 0, 0, 0, 0.5,-0.5}};
    static final double fwdMat4[][] = {
            {0.5, 0.5, 0, 0},
            {0.5,-0.5, 0, 0},
            {0, 0, 0.5, 0.5},
            {0, 0, 0.5,-0.5}};
    static final double fwdMat2[][] = {{0.5, 0.5}, {0.5,-0.5}}; //
    // Haar inverse transform matrices
    static final double invMat16[][] = {
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 1,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1,-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 1,-1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1,-1, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,-1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,-1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,-1}};
    static final double invMat8[][] = {{1, 1, 0, 0, 0, 0, 0, 0}, {1,-1, 0, 0, 0, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0}, {0, 0, 1,-1, 0, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 1,-1, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1}, {0, 0, 0, 0, 0, 0, 1,-1}};
    static final double invMat4[][] = {{1, 1, 0, 0}, {1,-1, 0, 0}, {0, 0, 1, 1}, {0, 0, 1,-1}};
    static final double invMat2[][] = {{1, 1}, {1,-1}};

    public static void main( String argv[] ) {
        double v[] = {32.0, 10.0, 20.0, 38.0, 37.0, 28.0, 38.0, 34.0, 8.0, 24.0, 18.0, 9.0, 23.0,
                24.0, 28.0, 34.0};
        matWavelet w = new matWavelet();
        linearPr p = new linearPr();
        System.out.println("before:");
        p.prVec( v );
        System.out.println();
        w.transStep(fwdMat16, v );
        w.transStep(fwdMat8, v );
        w.transStep(fwdMat4, v );
        w.transStep(fwdMat2, v );
        p.pr_ordered( v );
        System.out.println();
        w.invTransStep( invMat2, v );
        w.invTransStep( invMat4, v );
        w.invTransStep( invMat8, v );
        w.invTransStep( invMat16, v );
        System.out.println("after:");
        p.prVec( v ); } // main }
}
