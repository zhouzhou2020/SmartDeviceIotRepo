package org.debug.smartdeviceiot.server.DSP.Wavelet.util;

import java.lang.Math.*; /**
 A container for magnitude and angle (which complements the complex container)
 This class follows the naming convention in Richard Lyon's stellar book Understanding Digital Signal Processing. A "phasor" is sometimes called a vector. However, the term vector also refers to a one dimensional matrix, so the term "phasor". The phasor consists of a magnitude and an angle. It is complementary to a complex number, since a complex number can be formed from a phasor and a phasor can be formed form a complex number.
 */
public class phasor {
    private float M; // magnitude
    private float A; // Angle of the magnitude vector, in degrees

    public phasor() {
        M = 0.0f;
        A = 0.0f;
    }

    /**
     * Construct a phasor object from a magnitude and an angle. The angle is in degrees
     */
    public phasor(float m, float a) {
        M = m;
        A = a;
    }

    /**
     * Construct a phasor object from a complex object.
     */
    public phasor(complex c) {
        M = c.mag();
        A = c.angle();
    }

    /**
     * set the magnitude
     */
    public void mag(float m) {
        M = m;
    }

    /**
     * return the magnitude
     */
    public float mag() {
        return M;
    }

    /**
     * set the angle of the phasor
     */
    public void angle(float a) {
        A = a;
    }

    /**
     * return the angle of the phasor
     */
    public float angle() {
        return A;
    } /** return the complex form of the phasor*/
    public complex cx() {
        double R; double I;
        double radA = (A * Math.PI)/180;
        R = Math.cos( radA ) * M;
        I = Math.sin( radA ) * M;
        complex c = new complex( (float)R, (float)I );
        return c;
    }
}
/**
    This function uses the trig. identities:
        sin(angle) = I / mag
        mag * sin(angle) = I

        and
        cos(angle) = R / mag
        mag * cos(angle) = R
 Note that the angle stored in the phasor is in degrees. The Java trig functions take angles in radians.
 */