package org.debug.smartdeviceiot.server.DSP.Wavelet;


import org.debug.smartdeviceiot.server.DSP.Wavelet.util.point;

import java.util.Vector;
//import com.dlut.Wavelet.util.*;

/**
 <p>
 Window functions for the Discrete Fourier Transform (DFT)
 </p>

 <p>
 The DFT function suffers from the embarrassment of
 "DFT leakage".  When the DFT is calculated on a signal
 and the magnitude at each point is graphed for a
 simple function like sin(x), there should be one
 magnitude point, 1.0, at the sin(x) frequency.
 However, the actual magnitude will be slightly
 less than 1.0 and there will be several small
 sub-magnitudes.  These sub-magnitudes are referred
 to as "DFT leakage".  For more complex signals
 DFT leakage can actually obscure smaller signal
 subfrequencies.
 </p>
 <p>
 In <i>Understanding Digital Signal Processing</i>
 by Richard Lyons, a technique referred to as windowing
 is described to reduce DFT leakage.  Windowing applies
 a function which smoothly tails off the signal at both
 ends of the sample.  Apparently there are a variety of
 windowing function described in the DSP literature.
 Lyons describes three:
 </p>
 <ol>
 <li>
 Triangular window
 </li>
 <li>
 Hanning window
 </li>
 <li>
 Hamming window
 </li>
 </ol>
 <p>
 These three functions are implemented by methods in
 the window class.  Each method is passed a Vector
 object, which contains a set of point objects (the
 point objects contain the <i>x</i> and <i>y</i>
 values for a sample point).  The window function
 is applied to the <i>y</i> values for each point.
 </p>

 */
public class window {

    /**
     <p>
     Apply the "triangle" function to the <i>y</i> values
     in the point objects contained in <tt>vec</i>
     </p>
     <p>
     For N points in the DFT sample
     </p>
     <pre>
     for (int n = 0; n < N; n++) {
     ...if (n >= 0 && n <= N/2)
     ......w(n) = n/(N/2);
     ...else
     ......w(n) = 2 - n/(N/2);
     }
     </pre>
     <p>
     Where w(n) is the scaling factor by which the
     sample vec(n) is multiplied.
     </p>
     */
    public void triangle( Vector vec )
    {
        if (vec != null) {
            int N = vec.size();
            if (N > 0) {
                float scale;
                float N_over_2 = (N/2);
                point p;
                for (int n = 0; n < N; n++) {
                    p = (point)vec.elementAt( n );
                    if (n <= N/2) {
                        scale = ((float)n)/N_over_2;
                    }
                    else {
                        scale = 2.0f - (float)n/N_over_2;
                    }
                    p.y = p.y * scale;
                }
            }
        }
    } // triangle


    /**
     <p>
     Both the Hanning and the Hamming window functions
     (named after two guys with similar names) take the
     form
     </p>
     <pre>
     w(n) = coefA - coefB * cos( 2 * PI * n / (N-1))
     </pre>
     <p>
     The "hanning" and "hamming" window functions not only
     have names with similar functions, but they differ
     only in their exponents.  Presumably there are other
     similar functions.  So cosWinFunc is protected (instead
     of private) so that it can be referenced by a function
     in a class derived from this one.
     </p>
     */
    protected void cosWinFunc(float coefA, float coefB, Vector vec )
    {
        if (vec != null) {
            int len = vec.size();
            if (len > 0) {
                int N_minus_1 = len-1;

                point p;
                float new_y, scale, cosArg;
                for (int n = 0; n < len; n++) {
                    cosArg = (2.0f * (float)Math.PI * n)/N_minus_1;
                    scale = coefA - coefB * (float)Math.cos( cosArg );

                    p = (point)vec.elementAt( n );
                    p.y = p.y * scale;
                } // for
            } // if
        } // if
    } // cosWinFunc


    /**
     Apply the "hanning" function to the <i>y</i> values
     in the point objects contained in <tt>vec</i>
     </p>
     <p>
     For N points in the DFT sample
     </p>
     <pre>
     for (int n = 0; n < N; n++) {
     ...w(n) = 0.5 - 0.5 * cos( 2*PI*n/(N-1))
     }
     </pre>
     <p>
     Where w(n) is the scaling factor by which the
     sample vec(n) is multiplied.
     </p>
     */
    public void hanning( Vector vec ){
        cosWinFunc( 0.5f, 0.5f, vec );
    } // hanning


    /**
     Apply the "hamming" function to the <i>y</i> values
     in the point objects contained in <tt>vec</i>
     </p>
     <p>
     For N points in the DFT sample
     </p>
     <pre>
     for (int n = 0; n < N; n++) {
     ...w(n) = 0.54 - 0.46 * cos( 2*PI*n/(N-1));
     }
     </pre>
     <p>
     Where w(n) is the scaling factor by which the
     sample vec(n) is multiplied.
     </p>
     */
    public void hamming( Vector vec )
    {
        cosWinFunc( 0.54f, 0.46f, vec );
    } // hamming

} // window

