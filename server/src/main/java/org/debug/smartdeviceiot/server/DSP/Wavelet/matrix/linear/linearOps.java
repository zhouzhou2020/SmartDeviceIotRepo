package org.debug.smartdeviceiot.server.DSP.Wavelet.matrix.linear;

/**
 Linear algebra (vector, vector/matrix and matrix) operations
 The linear algebra operations are implemented for vectors and matrices of double elements.
 Many of the functions are overloaded. In one case a new array or matrix is allocated for the function result. In the other case the result is stored in an argument.
 This is not an "industrial" quality linear algebra package, since the functions do not do enough checking for array arguments with null values or length mismatch.*/

public class linearOps {
    /** Copy the vector argument v into a new vector and return the vector. */ public double[] vecCopy( double[] v ) { int N = v.length; double rslt[] = new double[N]; for (int i = 0; i < N; i++) { rslt[i] = v[i]; } return rslt; } /**
     Calculate the inner, or dot, product of two vectors. The result of a dot product operation is a double value. To produce a correct result, the two vectors should be the same length. To avoid indexing errors, this function will calculate a result using the minimum vector length.
     */
    public double dotprod( double vecA[], double vecB[] ) {
        int len = Math.min( vecA.length, vecB.length );
        double innerProd = 0.0;
        for (int i = 0; i < len; i++) {
            innerProd = innerProd + vecA[i] * vecB[i];
        }
        return innerProd;
    } // dotprod
    /**
    Vector magnitude: ||v||
    Vector magnitude is the square of a vector, which is the same as the dot product of the vector with itself.
            */
    public double vecmag( double v[] ) {
        return dotprod( v, v );
    } // vecmag
    /**
    Add two vectors, a and b. The result is returned in a new vector. The two vectors should be the same
     length. If this is not the case, a result that corresponds to the smallest vector will be returned.
*/
    public double[] vecadd( double a[], double b[] ) {
        int len = Math.min(a.length, b.length );
        double rslt[] = new double[len];
        for (int i = 0; i < len; i++) {
            rslt[i] = a[i] + b[i];
        }
        return rslt;
    } // vecadd
    /**
    Add two vectors, a and b. The result is returned in argument c.*/
    public void vecadd( double c[], double a[], double b[] ) {
        int len = Math.min(a.length, b.length );
        for (int i = 0; i < len; i++) { c[i] = a[i] + b[i]; } } // vecadd
    /**
    Subtract vector b from vector a. The result is returned in a new vector.*/
    public double[] vecsub( double a[], double b[] ) {
        int len = Math.min(a.length, b.length );
        double rslt[] = new double[len];
        for (int i = 0; i < len; i++) { rslt[i] = a[i] - b[i]; }
        return rslt;
    } // vecsub
    /**
    Subtract vector b from vector a. The result is returned in the argument c.
            */
    public void vecsub(double c[], double a[], double b[] ) {
        int len = Math.min(a.length, b.length );
        for (int i = 0; i < len; i++) { c[i] = a[i] - b[i]; }
    } // vecsub
    /** Multiply a vector, v, by a scalar value, scale. The result is returned in a new vector. */
    public double[] multScaleVec( double v[], double scale ) {
        int len = v.length; double rslt[] = new double[len];
        for (int i = 0; i < len; i++) { rslt[i] = v[i] * scale; }
        return rslt; } // multScaleVec
    /**
    Matrix multiply
    This is the linear algebra text book function to mltiply two matrices, where matA is MxN and matB is NxM, yielding an MxM result. The result is returned in a newly allocated matrix.
*/
    public double[][] matmult( double matA[][], double matB[][] ) {
        // A is an MxN matrix
        int lenA_d1 = matA.length;
        int lenA_d2 = matA[0].length;
        // B is an NxM matrix
        int lenB_d1 = matB.length;
        int lenB_d2 = matB[0].length;
        double rslt[][] = null;
        if (lenA_d1 != lenB_d2 || lenA_d2 != lenB_d1) {
            System.out.println("matrix A must be MxN and matrix B must be NxM");
            System.out.println("matrix A is " + lenA_d1 +" x " + lenA_d2 );
            System.out.println("matrix B is " + lenB_d1 +" x " + lenB_d2 );
        }
        else {
            int M = lenA_d1;
            int N = lenA_d2;
            rslt = new double[M][M];
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < M; j++) {
                    double dotProd = 0.0;
                    for (int k = 0; k < N; k++) {
                        dotProd = dotProd + matA[i][k] * matB[k][j];
                    }
                    rslt[i][j] = dotProd;
                }
            } }
        return rslt;
    } // matmult
    /**
        Matrix multiply
        This is the linear algebra text book function to mltiply two matrices, where matA is MxN and
     matB is NxM, yielding an MxM result. The result is returned in argument matC, whose size must be MxM. */
    public void matmult(double matC[][], double matA[][], double matB[][]) {
        // A is an MxN matrix
        int lenA_d1 = matA.length;
        int lenA_d2 = matA[0].length;
        // B is an NxM matrix
        int lenB_d1 = matB.length;
        int lenB_d2 = matB[0].length;
        // C should be an MxM matrix
        int lenC_d1 = matC.length;
        int lenC_d2 = matC[0].length;
        if (lenA_d1 != lenB_d2 || lenA_d2 != lenB_d1) {
            System.out.println("matrix A must be MxN and matrix B must be NxM");
            System.out.println("matrix A is " + lenA_d1 +" x " + lenA_d2 );
            System.out.println("matrix B is " + lenB_d1 +" x " + lenB_d2 );
        }
        else if (lenA_d1 != lenC_d1 || lenA_d1 != lenC_d2) {
            System.out.println("matrix C should be " + lenA_d1 + " x " + lenA_d1 );
            System.out.println("matrix C is " + lenC_d1 +" x " + lenC_d2 );
        }
        else {
            int M = lenA_d1;
            int N = lenA_d2;
            for (int i = 0; i < M; i++) {
                for (int j = 0; j < M; j++) {
                    double dotProd = 0.0;
                    for (int k = 0; k < N; k++) {
                        dotProd = dotProd + matA[i][k] * matB[k][j];
                    }
                    matC[i][j] = dotProd;
                }
            }
        }
    } // matmult
    /** Transpose an MxN matrix into an NxM matrix */
    double[][] transpose( double m[][] ) {
        int M = m.length;
        int N = m[0].length;
        double t[][] = new double[N][M];
        for (int i = 0; i < M; i++) {
            for (int j = 0; j < N; j++) {
                t[j][i] = m[i][j];
            }
        }
        return t;
    } // transpose

    /** Compare two vectors, a and b. Return true if the vectors are equal. */
    boolean vecCompare( double a[], double b[] ) {
        boolean rslt = true;
        int N = a.length;
        if (b.length != N) { rslt = false; }
        else {
            for (int i = 0; i < N; i++) {
                if (a[i] != b[i]) { rslt = false; break; }
            }
        }
        return rslt;
    } // vecCompare
    /** Compare two matrices, a and b. Return true if they are equal, false otherwise. */
    boolean matCompare( double a[][], double b[][] ) {
        boolean rslt = true;
        int M = a.length;
        int N = a[0].length;
        if (M != b.length || N != a[0].length) { rslt = false; }
        else { for (int i = 0; i < M; i++) {
            if (! vecCompare(a[i], b[i]) ) { rslt = false; break; }
        }
        }
        return rslt;
    } // matCompare } // linearOps
}
