//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.debug.smartdeviceiot.server.signalprocess.filtering.iir;

/**
 *Formula:
 *
 *     y[i] = x[i] * b[0]  +  x[i-1] * b[1]  +  x[i-2] * b[2]  +  ...
 *                         -  y[i-1] * a[1]  -  y[i-2] * a[2]  -  ...
 * (x = input, y = output, a and b = filter coefficients, a[0] must be 1)
 * */
public class IirFilter implements SignalFilter {
    private int n1;
    private int n2;
    private double[] a;
    private double[] b;
    private double[] buf1;
    private double[] buf2;
    private int pos1;
    private int pos2;

    public IirFilter(IirFilterCoefficients var1) {
        this.a = var1.a;
        this.b = var1.b;
        if (this.a.length >= 1 && this.b.length >= 1 && this.a[0] == 1.0D) {
            this.n1 = this.b.length - 1;
            this.n2 = this.a.length - 1;
            this.buf1 = new double[this.n1];
            this.buf2 = new double[this.n2];
        } else {
            throw new IllegalArgumentException("Invalid coefficients.");
        }
    }

    //Processes an input signal value and returns the next output signal value.
    //            step(double inputValue)
    public double step(double var1) {
        double var3 = this.b[0] * var1;

        int var5;
        int var6;
        for(var5 = 1; var5 <= this.n1; ++var5) {
            var6 = (this.pos1 + this.n1 - var5) % this.n1;
            var3 += this.b[var5] * this.buf1[var6];
        }

        for(var5 = 1; var5 <= this.n2; ++var5) {
            var6 = (this.pos2 + this.n2 - var5) % this.n2;
            var3 -= this.a[var5] * this.buf2[var6];
        }

        if (this.n1 > 0) {
            this.buf1[this.pos1] = var1;
            this.pos1 = (this.pos1 + 1) % this.n1;
        }

        if (this.n2 > 0) {
            this.buf2[this.pos2] = var3;
            this.pos2 = (this.pos2 + 1) % this.n2;
        }

        return var3;
    }
}
