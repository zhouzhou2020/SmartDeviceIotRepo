package org.debug.smartdeviceiot.server.DSP.Wavelet.wave;
/**
 Haar transform extended with a polynomial interpolation step
 This wavelet transform extends the Haar transform with a polynomial wavelet function.
 The polynomial wavelet uses 4-point polynomial interpolation to "predict" an odd point from four even point values.
 This class extends the Haar transform with an interpolation stage which follows the predict and update stages of the Haar transform. The predict value is calculated from the even points, which in this case are the smoothed values calculated by the scaling function (e.g., the averages of the even and odd values).
 The predict value is subtracted from the current odd value, which is the result of the Haar wavelet function (e.g., the difference between the odd value and the even value). This tends to result in large odd values after the interpolation stage, which is a weakness in this algorithm. */

public class haarpoly extends haar {
    final static int numPts = 4;
    private polyinterp fourPt;
    /** haarpoly class constructor */
    public haarpoly() { fourPt = new polyinterp(); }
    /**
 Copy four points or N (which ever is less) data points from vec into d These points are the "known" points used in the polynomial interpolation.
 @param /vec[] the input data set on which the wavelet is calculated @param d[] an array into which N data points, starting at start are copied.
     @param N the number of polynomial interpolation points @param start the index in vec from which copying starts */
    private void fill( double vec[], double d[], int N, int start ) {
        int n = numPts;
        if (n > N) n = N;
        int end = start + n;
        int j = 0;
        for (int i = start; i < end; i++) { d[j] = vec[i]; j++; }
    } // fill
     /**
    Predict an odd point from the even points, using 4-point polynomial interpolation.
    The four points used in the polynomial interpolation are the even points. We pretend that these four points are located at the x-coordinates 0,1,2,3. The first odd point interpolated will be located between the first and second even point, at 0.5. The next N-3 points are located at 1.5 (in the middle of the four points). The last two points are located at 2.5 and 3.5. For complete documentation see

    http://www.bearcave.com/misl/misl_tech/wavelets/lifting/index.html

    The difference between the predicted (interpolated) value and the actual odd value replaces the odd value in the forward transform.
    As the recursive steps proceed, N will eventually be 4 and then 2. When N = 4, linear interpolation is used. When N = 2, Haar interpolation is used (the prediction for the odd value is that it is equal to the even value).
    @param vec the input data on which the forward or inverse transform is calculated. @param N the area of vec over which the transform is calculated @param direction forward or inverse transform */
     protected void interp( double[] vec, int N, int direction ) {
         int half = N >> 1;
         double d[] = new double[numPts];
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
     } // interp

     /**Haar transform extened with polynomial interpolation forward transform.
                      This version of the forwardTrans function overrides the function in the liftbase base class. This function introduces an extra polynomial interpolation stage at the end of the transform.*/
     public void forwardTrans(double[] vec){
            final int N = vec.length;
            for (int n = N; n > 1; n = n >> 1) {
                split(vec, n);
                predict(vec, n, forward);
                update(vec, n, forward);
                interp(vec, n, forward);
            } // for
     } // forwardTrans
    /**
            Haar transform extened with polynomial interpolation inverse transform.
            This version of the inverseTrans function overrides the function in the liftbase base class.This function
            introduces an inverse polynomial interpolation stage at the start of the inverse transform.*/
    public void inverseTrans ( double[] vec ){
        final int N = vec.length;
        for (int n = 2; n <= N; n = n << 1) {
            interp(vec, n, inverse);
            update(vec, n, inverse);
            predict(vec, n, inverse);
            merge(vec, n);
        }
    } // inverseTrans }
}