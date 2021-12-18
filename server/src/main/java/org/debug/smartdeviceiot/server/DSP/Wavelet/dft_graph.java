package org.debug.smartdeviceiot.server.DSP.Wavelet;
import java.util.Vector;
import com.dlut.Wavelet.util.*;


/**
 <p>
 The DFT scales cosine or sine waves by the values from the
 signal.  This code attempt to show this graphically by outputting
 the data for sine or cosine plots, where the sine and cosine
 functions are scaled by the points over which the DFT is
 calculated.
 </p>

 <p>
 The data is output in gnuplot format, where a blank
 line seperates.
 </p>

 */
public class dft_graph {
    private final int REAL_PART = 1;
    private final int IMAG_PART = 2;

    private final double twoPi = 2 * Math.PI;
    /** Vector object containing the points over which the DFT is calculated */
    private Vector vec;
    /** The plot is calculated from 0..<i>end</i> */
    private float end;

    /**
     <p>
     The dft_graph constructor is passed a Vector of
     <i>point</i> objects.  This Vector object contains
     the points over which the DFT is calculated.
     </p>
     */
    public dft_graph( Vector v )
    {
        vec = v;
        int len = vec.size();
        point p = (point)(vec.elementAt(len-1));
        end = p.x;
    }


    /**
     <p>
     Calculate the cosine or sine functions, scaled by the
     points over which the DFT is calculated.  A relatively
     small step is used in an attempt to yield a smoother
     graph of the functions.
     </p>

     <p>
     Most Java compilers are too stupid to figure out what
     a loop invariant is, so its coded here by hand.
     </p>

     */
    private void calc( int part )
    {
        int len = vec.size();
        for (int m = 0; m < len; m++) {
            point p = (point)vec.elementAt(m);
            double coef = p.y;
            double loopInv = twoPi * m; // loop invariant
            for (double n = 0; n <= end; n = n + 0.125) {
                double scale = (loopInv * n)/(double)len;

                double y = 0.0;
                if (part == REAL_PART) {
                    y = Math.cos( scale );
                }
                else if (part == IMAG_PART) {
                    y = Math.sin( scale );
                }
                y = coef * y;
                System.out.println(" " + n + "  " + y );
            }
            System.out.println();
        }
    } // calc


    /**
     Output a plot for the real part of the DFT

     */
    public void real_part()
    {
        calc( REAL_PART );
    }


    /**
     Output a plot for the imaginary part of the DFT
     */
    public void imag_part()
    {
        calc( IMAG_PART );
    }

}
