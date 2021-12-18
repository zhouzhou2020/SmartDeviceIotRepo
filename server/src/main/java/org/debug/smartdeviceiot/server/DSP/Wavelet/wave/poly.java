package org.debug.smartdeviceiot.server.DSP.Wavelet.wave;
/**
 Polynomial wavelets
 This wavelet transform uses a polynomial interpolation wavelet (e.g., the function used to calculate the differences). A Haar scaling function (the calculation of the average for the even points) is used.
 This wavelet transform uses a two stage version of the lifting scheme. In the "classic" two stage Lifting Scheme wavelet the predict stage preceeds the update stage. Also, the algorithm is absolutely symetric, with only the operators (usually addition and subtraction) interchanged.
 The problem with the classic Lifting Scheme transform is that it can be difficult to determine how to calculate the smoothing (scaling) function in the update phase once the predict stage has altered the odd values. This version of the wavelet transform calculates the update stage first and then calculates the predict stage from the modified update values. In this case the predict stage uses 4-point polynomial interpolation using even values that result from the update stage.
 In this version of the wavelet transform the update stage is no longer perfectly symetric, since the forward and inverse transform equations differ by more than an addition or subtraction operator. However, this version of the transform produces a better result than the Haar transform extended with a polynomial interpolation stage. */

public class poly extends liftbase{
    final static int numPts = 4;
    private polyinterp fourPt; /** poly class constructor */
    public poly() { fourPt = new polyinterp(); } /**
     Copy four points or N (which ever is less) data points from vec into d These points are the "known" points used in the polynomial interpolation.
     @param /vec[] the input data set on which the wavelet is calculated @param d[] an array into which N data points, starting at start are copied. @param N the number of polynomial interpolation points @param start the index in vec from which copying starts */
    private void fill( double vec[], double d[], int N, int start ) {
        int n = numPts;
        if (n > N) n = N;
        int end = start + n;
        int j = 0;
        for (int i = start; i < end; i++) {
            d[j] = vec[i]; j++;
        }
    } // fill
    /**
    The update stage calculates the forward and inverse Haar scaling functions. The forward Haar scaling function is simply the average of the even and odd elements. The inverse function is found by simple algebraic manipulation, solving for the even element given the average and the odd element.
    In this version of the wavelet transform the update stage preceeds the predict stage in the forward transform. In the inverse transform the predict stage preceeds the update stage, reversing the calculation on the odd elements.*/
    protected void update( double[] vec, int N, int direction ) {
        int half = N >> 1;
        for (int i = 0; i < half; i++) {
            int j = i + half;
            double updateVal = vec[j] / 2.0;
            if (direction == forward) {
                vec[i] = (vec[i] + vec[j])/2;
            }
            else if (direction == inverse) {
                vec[i] = (2 * vec[i]) - vec[j];
            }
            else {
                System.out.println("update: bad direction value");
            }
        }
    }
    /** Predict an odd point from the even points, using 4-point polynomial interpolation.
     The four points used in the polynomial interpolation are the even points. We pretend that these four points are located at the x-coordinates 0,1,2,3. The first odd point interpolated will be located between the first and second even point, at 0.5. The next N-3 points are located at 1.5 (in the middle of the four points). The last two points are located at 2.5 and 3.5. For complete documentation see
     http://www.bearcave.com/misl/misl_tech/wavelets/lifting/index.html
     The difference between the predicted (interpolated) value and the actual odd value replaces the odd value in the forward transform.
     As the recursive steps proceed, N will eventually be 4 and then 2. When N = 4, linear interpolation is used. When N = 2, Haar interpolation is used (the prediction for the odd value is that it is equal to the even value).
     @param vec the input data on which the forward or inverse transform is calculated. @param N the area of vec over which the transform is calculated @param direction forward or inverse transform */
    protected void predict( double[] vec, int N, int direction ) {
        int half = N >> 1;
        double d[] = new double[ numPts ];
        int k = 42;
        for (int i = 0; i < half; i++) {
            double predictVal;
            if (i == 0) {
                if (half == 1) { // e.g., N == 2, and we use Haar interpolation
                    predictVal = vec[0];
                }
                else {
                    fill( vec, d, N, 0 );
                    predictVal = fourPt.interpPoint( 0.5, half, d );
                }
            }
            else if (i == 1) {
                predictVal = fourPt.interpPoint( 1.5, half, d );
            }
            else if (i == half-2) {
                predictVal = fourPt.interpPoint( 2.5, half, d );
            }
            else if (i == half-1) {
                predictVal = fourPt.interpPoint( 3.5, half, d );
            }
            else {
                fill( vec, d, N, i-1);
                predictVal = fourPt.interpPoint( 1.5, half, d );
            }
            int j = i + half;
            if (direction == forward) {
                vec[j] = vec[j] - predictVal;
            }
            else if (direction == inverse) {
                vec[j] = vec[j] + predictVal;
            }
            else {
                System.out.println("poly::predict: bad direction value");
            }
        }
    } // predict
    /**
        Polynomial wavelet lifting scheme transform.This version of the forwardTrans function overrides the function in the liftbase base class. This function introduces an extra polynomial interpolation stage at the end of the transform.*/
    public void forwardTrans( double[] vec ) {
        final int N = vec.length;
        for (int n = N; n > 1; n = n >> 1) {
            split( vec, n );
            update( vec, n, forward );
            predict( vec, n, forward );
        } // for
    } // forwardTrans
    /** Polynomial wavelet lifting Scheme inverse transform.This version of the inverseTrans function overrides the function in the liftbase base class. This function introduces an inverse polynomial interpolation stage at the start of the inverse transform.**/
    public void inverseTrans( double[] vec ) {
        final int N = vec.length;
        for (int n = 2; n <= N; n = n << 1) {
            predict( vec, n, inverse );
            update( vec, n, inverse );
            merge( vec, n );
        }
    } // inverseTrans
}// poly
