package org.debug.smartdeviceiot.server.DSP.Wavelet.util;

public class matrix {
    public double dotprod( double[] vecA, double[] vecB ) {
        double prod = 0;
        for (int i = 0; i < vecB.length; i++) {
            prod = prod + (vecA[i] * vecB[i]);
        }
        return prod;
    }

    public double[] matvec( double[][] mat, double[] vec ) {
        int vecLen = vec.length;
        double[] newVec = new double[ vecLen ];
        int m = mat.length;
        for (int i = 0; i < m; i++) {
            newVec[i] = dotprod( mat[i], vec );
        }
        return newVec;
    }
}
