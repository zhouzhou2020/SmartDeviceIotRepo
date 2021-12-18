package org.debug.smartdeviceiot.server.DSP.Wavelet.util;

public class print {
    /** Round a double to four decimal places. */
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
    public void pr_vec( double vec[] ) {
        int N = vec.length;
        for (int i = 0; i < N; i++) {
            System.out.print( round4(vec[i]) );
            if (i < N-1)
                System.out.print(", ");
        }
        System.out.println();
    } // pr_vec
    /** Print an m x n matrix */
    public void pr_mat( double mat[][] ) {
        int m = mat.length;
        for (int i = 0; i < m; i++) {
            pr_vec( mat[i] );
        }
    } // pr_mat
}

