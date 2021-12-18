package org.debug.smartdeviceiot.server.DSP.Wavelet.wave;

class polyinterp {
    /** number of polynomial interpolation ponts */
    private final static int numPts = 4;
    /** Table for 4-point interpolation coefficients */
    private double fourPointTable[][];
    /**Table for 2-point interpolation coefficients */
    private double twoPointTable[][];
    /**
     The polynomial interpolation algorithm assumes that the known points are located at x-coordinates 0, 1,.. N-1. An interpolated point is calculated at x, using N coefficients. The polynomial coefficients for the point x can be calculated staticly, using the Lagrange method.
     @param x the x-coordinate of the interpolated point @param N the number of polynomial points. @param c[] an array for returning the coefficients */
    private void lagrange( double x, int N, double c[] ) {
        double num, denom;
        for (int i = 0; i < N; i++) {
            num = 1;
            denom = 1;
            for (int k = 0; k < N; k++) {
                if (i != k) {
                    num = num * (x - k);
                    denom = denom * (i - k);
                }
            } // for k c[i] = num / denom; } // for i
        } // lagrange
    }
    /**
     For a given N-point polynomial interpolation, fill the coefficient table, for points 0.5 ... (N-0.5).*/
    private void fillTable( int N, double table[][] ) {
        double x; double n = N; int i = 0;
        for (x = 0.5; x < n; x = x + 1.0) {
            lagrange( x, N, table[i] ); i++; }
    } // fillTable
    /**
     poly constructor
     Build the 4-point and 2-point polynomial coefficient tables.*/
    public polyinterp() { // Fill in the 4-point polynomial interplation table // for the points 0.5, 1.5, 2.5, 3.5
        fourPointTable = new double[numPts][numPts];
        fillTable( numPts, fourPointTable ); // Fill in the 2-point polynomial interpolation table // for 0.5 and 1.5
        twoPointTable = new double[2][2];
        fillTable( 2, twoPointTable );
    } // poly constructor /** Print an N x N table polynomial coefficient table */

    private void printTable( double table[][], int N) {
        System.out.println(N + "-point interpolation table:");
        double x = 0.5;
        for (int i = 0; i < N; i++) {
            System.out.print(x + ": ");
            for (int j = 0; j < N; j++) {
                System.out.print(table[i][j]);
                if (j < N - 1) System.out.print(", ");
            }
            System.out.println();
            x = x + 1.0;
        } /** Print the 4-point and 2-point polynomial coefficient tables. */
    }

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
            System.out.println("poly::getCoef: n = " + n + ", bad x value");
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
            System.out.println("poly::getCoef: bad value for N");
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
    public double interpPoint(double x, int N, double d[]) {
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
}// polyinterp
