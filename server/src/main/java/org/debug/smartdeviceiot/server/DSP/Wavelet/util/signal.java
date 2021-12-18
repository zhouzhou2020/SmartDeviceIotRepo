package org.debug.smartdeviceiot.server.DSP.Wavelet.util;

import java.lang.Math; /** This class provides a signal generator for testing Fourier transform code. */
public class signal {
    /**
     * current x
     */
    private float x;
    /**
     * number of samples for a signal phase
     */
    private float rate;
    /**
     * "step" (or increment) between samples
     */
    private float step;
    /**
     * coeficients scaling the result of the sin function (e.g., coef[i]*sin(x)
     */
    private float coef[];
    /**
     * scaling factor for the argument to the sin function (e.g., sin( scale[i]*x )
     */
    private float scale[];
    /**
     * offset factor for the argument to the sin function (e.g., sin(x + offset[i])
     */
    private float offset[];

    public signal(float r, float c[], float s[], float o[] ) {
        x = (float)0.0;
        rate = r;
        if (c == null) { coef = new float[] {1.0f}; }
        else { coef = c; }
        if (s == null) {
            scale = new float[] {1.0f}; }
        else { scale = s; }
        if (o == null) { offset = new float[] {0.0f}; }
        else { offset = o; }
        int maxLen;
        maxLen = Math.max( coef.length, scale.length );
        maxLen = Math.max( offset.length, maxLen );
        if (coef.length < maxLen) {
            float newCoef[] = new float[ maxLen ];
            int i;
            for (i = 0; i < coef.length; i++) {
                newCoef[i] = coef[i]; }
            for (i = coef.length; i < maxLen; i++) {
                newCoef[i] = 1.0f; }
            coef = newCoef;
        }
        if (scale.length < maxLen) {
            float newScale[] = new float[ maxLen ];
            int i;
            for (i = 0; i < scale.length; i++) {
                newScale[i] = scale[i];
            }
            for (i = scale.length; i < maxLen; i++) {
                newScale[i] = 1.0f; }
            scale = newScale;
        }
        if (offset.length < maxLen) {
            float newOffset[] = new float[ maxLen ];
            int i;
            for (i = 0; i < offset.length; i++) {
                newOffset[i] = offset[i]; }
            for (i = offset.length; i < maxLen; i++) {
                newOffset[i] = 0.0f; }
            offset = newOffset;
        } // // Adjust the sample rate for the scaling of sin(x) //
        float maxScale = scale[0];
        for (int i = 1; i < scale.length; i++) {
            maxScale = Math.max( maxScale, scale[i] ); }
        rate = rate * maxScale;
        step = (float)((2.0 * Math.PI)/(double)rate);
    }
        // signal /** Get the next signal valueThis function returns the next signal value and then increments the counter by step.
    public point nextVal() {
        point p = new point( x, 0.0f);
        double scaleX;
        for (int i = 0; i < coef.length; i++) {
            scaleX = scale[i] * x;
            p.y = p.y + coef[i] * (float)Math.sin(scaleX + offset[i]);
        }
        x = x + step;
        return p;
    } // next_t
}
/**
 The constructor is passed:
 @param r The number of samples for one cycle of the generated signal. @param c An array of coefficients for the result of the sin function (for example: for c = {1.5f, 3.0f}, 1.5sin(x) + 3.0sin(x)) @param s An array of scaling factors for the argument to the sin function (for example: for s = {0.5f, 0.25f}, sin(x/2) + sin(x/4)) @param o An array of soffset factors for the argument to the sin function (for example: for o = {(float)Math.PI/4.0f}, sin( x + pi/4 ))
 The signal produced by nextVal() is the sum of a set of sin(x) values. The number of sin(x) values is len = max(c.length, s.length).
 .             len
 .             ---
 signal(x) =   \
 .             /    ci * sin( si * x + oi)
 .             ---
 .             i=0
 Here ci scales the result of the sin(x) function, si scales the argument to sin(x) and oi is an offset to the argument of sin(x).
 Example:

 float sampleRate = 16;
 float coef[] = new float[] {1.0f};
 float scale[] = new float[] {2.0f, 1.0f, 0.5f};
 float offset[] = new float[] { 0.0f, (float)Math.PI/4.0f }

 signal sig = new signal( sampleRate, coef, scale, offset );
 This will result in the signal
 f(x) = sin( 2x ) + sin( x + pi/4 ) + sin(x/2);
 Any or all of the coef, scale and offset arguments may be null. If they are supplied, they do not have to be the same size. The shorter array will be padded with appropriate values by the signal object.
 If coef, scale and offset arguments are null the signal will be sin(x).
 The signal that would be generated from the coef, scale and offset arguments shown above is shown below.

 The loop below is used to generate the points in the graph from the range 0...6Pi.
 float range = 3.0f * (2.0f * (float)Math.PI);

 point p;
 float x;

 do {
 p = sig.nextVal();
 data.addElement( p );
 x = p.x;
 System.out.println(" " + p.x + "  " + p.y );
 }
 */