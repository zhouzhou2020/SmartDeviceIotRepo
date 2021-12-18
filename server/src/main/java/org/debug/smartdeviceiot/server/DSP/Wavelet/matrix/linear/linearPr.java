package org.debug.smartdeviceiot.server.DSP.Wavelet.matrix.linear;

/**
 Print vectors and matrices
 A vector can be printed in either vector form or as an ordered wavelet transform.
 */

    public class linearPr {
        /** Round a double to four decimal places. */
        private double round4( double d ) {
            final double multiplier = 10000.0;
            double rounded;
            rounded = Math.round(d * multiplier)/multiplier;
            return rounded;
        } // round4
        /**
        Print a vector as an ordered wavelet transform. An ordered wavelet transform starts which a wavelet scaling value (sometimes called a smoothed value or an average). This is followed by bands of coefficients, where the length of the bands increases as a power of two (e.g., 20, 21, 22, ...)
        @param vec[] result of a wavelet transform */
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
        /** Print a vector */
        public void prVec( double v[] ) {
            for (int i = 0; i < v.length; i++) {
                System.out.print( round4(v[i]) + " " );
            }
            System.out.println();
        } // prVec
        /** Print an MxN matrix */
        public void prMat( double m[][] ) {
            int M=0;
            if (m != null) {
                M = m.length;
            }
            int N = m[0].length;
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    System.out.print( round4(m[i][j]) + " " );
                }
                System.out.println();
            }
        }
    } // prMat }
