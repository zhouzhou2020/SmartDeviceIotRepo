package org.debug.smartdeviceiot.server.DSP.Wavelet.wave.predictWave;

/**
 Polynomial predict wavelets
 The wavelet Lifting Scheme predict wavelets are a proto-wavelet, which does not include an averaging step (referred to as an update step in the Lifting Scheme).
 The polynomial wavelet uses 4-point polynomial interpolation to predict an even point from the odd points. The difference between the "prediction" and the actual value of the odd point replaces the odd point. For complete documentation see */

public class predictpoly extends predictbase {
    /** number of polynomial interpolation ponts */
    final int numPts = 4; /** Table for 4-point interpolation coefficients */
    private double fourPointTable[][]; /** Table for 2-point interpolation coefficients */
    private double twoPointTable[][];
    /**
     The polynomial interpolation algorithm assumes that the known points are located at x-coordinates 0, 1,.. N-1. An interpolated point is calculated at x, using N coefficients. The polynomial coefficients for the point x can be calculated staticly, using the Legrange method.
     @param x the x-coordinate of the interpolated point @param N the number of polynomial points. @param c[] an array for returning the coefficients */
    private void legrange( double x, int N, double c[] ) {
        double num, denom;
        for (int i = 0; i < N; i++) {
            num = 1; denom = 1;
            for (int k = 0; k < N; k++) {
                if (i != k) {
                    num = num * (x - k);
                    denom = denom * (i - k);
                }
            } // for k
            c[i] = num / denom;
        } // for i
    } // legrange
    /**
        For a given N-point polynomial interpolation, fill the coefficient table, for points 0.5 ... (N-0.5).
                */
    private void fillTable( int N, double table[][] ) {
        double x;
        double n = N;
        int i = 0;
        for (x = 0.5; x < n; x = x + 1.0) {
            legrange( x, N, table[i] ); i++;
        }
    } // fillTable
    /**
        predictpoly constructor
        Build the 4-point and 2-point polynomial coefficient tables.
                */
    public predictpoly() { // Fill in the 4-point polynomial interplation table // for the points 0.5, 1.5, 2.5, 3.5
        fourPointTable = new double[numPts][numPts];
        fillTable( numPts, fourPointTable ); // Fill in the 2-point polynomial interpolation table
        // for 0.5 and 1.5
        twoPointTable = new double[2][2];
        fillTable( 2, twoPointTable );
    } // predictpoly constructor
    /** Print an N x N table polynomial coefficient table */
    private void printTable( double table[][], int N) {
        System.out.println(N + "-point interpolation table:");
        double x = 0.5;
        for (int i = 0; i < N; i++) {
            System.out.print(x + ": ");
            for (int j = 0; j < N; j++) {
                System.out.print( table[i][j] );
                if (j < N-1)
                    System.out.print(", ");
            }
            System.out.println(); x = x + 1.0;
        }
    } /** Print the 4-point and 2-point polynomial coefficient tables. */

    public void printTables() {
        printTable( fourPointTable, numPts );
        printTable( twoPointTable, 2 );
    } // printTables

    /**
            For the polynomial interpolation point x-coordinate x, return the associated polynomial interpolation coefficients.
            @param x the x-coordinate for the interpolated pont @param n the number of polynomial interpolation points @param c[] an array to return the polynomial coefficients */
    private void getCoef( double x, int n, double c[]) {
        double table[][] = null;
        int j = (int)x;
        if (j < 0 || j >= n) {
            System.out.println("predictpoly::getCoef: n = " + n + ", bad x value");
        }
        if (n == numPts) {
            table = fourPointTable;
        }
        else if (n == 2) {
            table = twoPointTable;
            c[2] = 0.0;
            c[3] = 0.0;
        }
        else {
            System.out.println("predictpoly::getCoef: bad value for N");
        }
        if (table != null) {
            for (int i = 0; i < n; i++) {
                c[i] = table[j][i];
            }
        }
    } // getCoef

    /**
            Given four points at the x,y coordinates {0,d0}, {1,d1}, {2,d2}, {3,d3} return the y-coordinate value for the polynomial interpolated point at x.
            @param x the x-coordinate for the point to be interpolated @param N the number of interpolation points @param d[] an array containing the y-coordinate values for the known points (which are located at x-coordinates 0..N-1). */
    private double interpPoint( double x, int N, double d[]) {
        double c[] = new double[ numPts ];
        double point = 0;
        int n = numPts;
        if (N < numPts)
            n = N;
        getCoef( x, n, c );
        if (n == numPts) {
            point = c[0]*d[0] + c[1]*d[1] + c[2]*d[2] + c[3]*d[3];
        }
        else if (n == 2) {
            point = c[0]*d[0] + c[1]*d[1];
        }
        return point;
    } // interpPoint
    /**
            Copy N data points from vec into d These points are the "known" points used in the polynomial interpolation.
            @param /vec[] the input data set on which the wavelet is calculated @param d[] an array into which N data points, starting at start are copied. @param N the number of polynomial interpolation points @param start the index in vec from which copying starts */
    private void fill( double vec[], double d[], int N, int start ) {
        int n = numPts;
        if (n > N)
            n = N;
        int end = start + n;
        int j = 0;
        for (int i = start; i < end; i++) {
            d[j] = vec[i]; j++;
        }
    } // fill

    /**
            Predict an odd point from the even points, using 4-point polynomial interpolation.
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
                    predictVal = interpPoint( 0.5, half, d );
                }
            }
            else if (i == 1) {
                predictVal = interpPoint( 1.5, half, d );
            }
            else if (i == half-2) {
                predictVal = interpPoint( 2.5, half, d );
            }
            else if (i == half-1) {
                predictVal = interpPoint( 3.5, half, d );
            }
            else {
                fill( vec, d, N, i-1);
                predictVal = interpPoint( 1.5, half, d );
            }
            int j = i + half;
            if (direction == forward) {
                vec[j] = vec[j] - predictVal;
            }
            else if (direction == inverse) {
                vec[j] = vec[j] + predictVal;
            }
            else {
                System.out.println("predictpoly::predict: bad direction value");
            }
        }
    } // predict }
}// predictpoly

