package org.debug.smartdeviceiot.server.DSP.Wavelet.dft;


import org.debug.smartdeviceiot.server.DSP.Wavelet.dft.fourier.dft;
import org.debug.smartdeviceiot.server.DSP.Wavelet.util.complex;
import org.debug.smartdeviceiot.server.DSP.Wavelet.util.point;
import org.debug.smartdeviceiot.server.DSP.Wavelet.util.signal;

import java.util.Vector;
import java.lang.Math.*; /** Test for the Discreat Fourier Transform */

class dfttest {
    public static void main(String args[]) {
        float sampleRate = 16;
        float range = 4.0f * (2.0f * (float) Math.PI);
        float coef[] = new float[]{1.0f};
        float scale[] = new float[]{8.0f, 4.0f, 2.0f};
        // float scale[] = new float[] {2.0f, 1.0f, 0.5f};
        // float scale[] = new float[] {(float)(2 * Math.PI)};
        // float scale[] = new float[] {1.0f};
        signal sig = new signal(sampleRate, coef, scale, null);
        Vector data = new Vector();
        float x;
        do {
            point p = sig.nextVal();
            data.addElement(p);
            x = p.x;
            // System.out.println(" " + p.x + " " + p.y );
        } while (x <= range);
        dft DFT = new dft(data);
        Vector transData = new Vector();
        int N = data.size();
        // The DFT produces unique points only over the first // N/2 points for real values.
        // The other points are // a mirror of the first N/2 values.
        for (int i = 0; i < N; i++) {
            complex cx = DFT.dftPoint(i);
            transData.addElement(cx);
        } // At this point there is a Vector object of complex objects
        // which represent the DFT for the first N/2 points. You can  do a variety of things with this, like print out the // magnitude. // } // main
    }
}