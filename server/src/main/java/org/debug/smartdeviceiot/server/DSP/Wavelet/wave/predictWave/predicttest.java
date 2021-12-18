package org.debug.smartdeviceiot.server.DSP.Wavelet.wave.predictWave;

class predicttest {
    static double[] copy( double vec[] ) {
        double newVec[] = null;
        if (vec != null) {
            final int len = vec.length;
            newVec = new double[ len ];
            for (int i = 0; i < len; i++) {
                newVec[i] = vec[i];
            }
        }
        return newVec;
    } // copy

    static boolean equal( double v1[], double v2[] ) {
        boolean rslt = true;
        int len = v1.length;
        for (int i = 0; i < len; i++) {
            if (v1[i] != v2[i]) {
                rslt = false; break;
            }
        } // for
        return rslt;
    } // equal

    static void pr( double vec[] ) {
        int cnt = 0;
        final int len = vec.length;
        for (int i = 0; i < len; i++) {
            System.out.print( vec[i] + " ");
            cnt++;
            if (cnt == 6) {
                cnt = 0;
                System.out.println();
            }
        }
        System.out.println();
    } // pr

    public static void main( String[] args ) {
        double vals1[] = { 32.0, 10.0, 20.0, 38.0, 37.0, 28.0, 38.0, 34.0, 18.0, 24.0, 18.0, 9.0,
                23.0, 24.0, 28.0, 34.0 };
        double vals2[] = { 77.6875, 78.1875, 82.0625, 85.5625, 86.7500, 82.4375, 82.2500, 82.7500,
                81.2500, 79.5625, 80.2813, 79.8750, 77.7500, 74.7500, 78.5000, 79.1875, 78.8125,
                80.3125, 80.1250, 79.3125, 83.7500, 89.8125, 87.7500, 91.1250, 94.4375, 92.7500,
                98.0000, 97.1875, 99.4375, 101.7500, 108.5000, 109.0000, 105.2500, 105.5000, 110.0000,
                107.0000, 107.2500, 103.3125, 102.8750, 102.4375, 102.0000, 101.3125, 97.4375, 100.5000,
                107.7500, 110.2500, 114.3125, 111.2500, 114.8125, 112.6875, 109.4375, 108.0625, 104.5625,
                103.2500, 110.5625, 110.7500, 116.3125, 123.6250, 120.9375, 121.6250, 127.6875, 126.0625,
                126.3750, 124.3750 };
        double save[] = copy( vals2 );
         predicthaar haar = new predicthaar();
         haar.forwardTrans( vals2 );
         haar.inverseTrans( vals2 );
        // predictline line = new predictline();
        // line.forwardTrans( vals2 );
        // line.inverseTrans( vals2 );
        // predictpoly poly = new predictpoly();
        // poly.forwardTrans( vals2 );
        // poly.pr_ordered( vals2 );
        // System.out.println();
        // poly.inverseTrans( vals2 );
         if (equal(save, vals2)) {
            System.out.println("Arrays are the same");
         }
         else {
            System.out.println("Arrays are NOT the same");
            pr( vals2 );
         }
    } // main
}
