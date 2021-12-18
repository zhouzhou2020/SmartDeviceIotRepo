//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.debug.smartdeviceiot.server.DSP.Utils.math;

public class PolynomialUtils {
    private PolynomialUtils() {
    }

    public static Complex evaluate(double[] var0, Complex var1) {
        if (var0.length == 0) {
            throw new IllegalArgumentException();
        } else {
            Complex var2 = new Complex(var0[0]);

            for(int var3 = 1; var3 < var0.length; ++var3) {
                var2 = var2.mul(var1).add(var0[var3]);
            }

            return var2;
        }
    }

    public static Complex evaluate(RationalFraction var0, Complex var1) {
        Complex var2 = evaluate(var0.top, var1);
        Complex var3 = evaluate(var0.bottom, var1);
        return var2.div(var3);
    }

    public static double[] multiply(double[] var0, double[] var1) {
        int var2 = var0.length - 1;
        int var3 = var1.length - 1;
        int var4 = var2 + var3;
        double[] var5 = new double[var4 + 1];

        for(int var6 = 0; var6 <= var4; ++var6) {
            double var7 = 0.0D;
            int var9 = Math.max(0, var6 - var3);
            int var10 = Math.min(var2, var6);

            for(int var11 = var9; var11 <= var10; ++var11) {
                var7 += var0[var2 - var11] * var1[var3 - var6 + var11];
            }

            var5[var4 - var6] = var7;
        }

        return var5;
    }

    public static Complex[] multiply(Complex[] var0, Complex[] var1) {
        int var2 = var0.length - 1;
        int var3 = var1.length - 1;
        int var4 = var2 + var3;
        Complex[] var5 = new Complex[var4 + 1];

        for(int var6 = 0; var6 <= var4; ++var6) {
            Complex var7 = Complex.ZERO;
            int var8 = Math.max(0, var6 - var3);
            int var9 = Math.min(var2, var6);

            for(int var10 = var8; var10 <= var9; ++var10) {
                var7 = var7.add(var0[var2 - var10].mul(var1[var3 - var6 + var10]));
            }

            var5[var4 - var6] = var7;
        }

        return var5;
    }

    public static Complex[] deflate(Complex[] var0, Complex var1, double var2) {
        int var4 = var0.length - 1;
        Complex[] var5 = new Complex[var4];
        var5[0] = var0[0];

        for(int var6 = 1; var6 < var4; ++var6) {
            var5[var6] = var1.mul(var5[var6 - 1]).add(var0[var6]);
        }

        Complex var7 = var1.mul(var5[var4 - 1]).add(var0[var4]);
        if (var2 <= 0.0D || Math.abs(var7.re()) <= var2 && Math.abs(var7.im()) <= var2) {
            return var5;
        } else {
            throw new RuntimeException("Polynom deflatation failed, remainder = " + var7 + ".");
        }
    }

    public static Complex[] expand(Complex[] var0) {
        int var1 = var0.length;
        if (var1 == 0) {
            return new Complex[]{Complex.ONE};
        } else {
            Complex[] var2 = new Complex[]{Complex.ONE, var0[0].neg()};

            for(int var3 = 1; var3 < var1; ++var3) {
                Complex[] var4 = new Complex[]{Complex.ONE, var0[var3].neg()};
                var2 = multiply(var2, var4);
            }

            return var2;
        }
    }

    public static class RationalFraction {
        public double[] top;
        public double[] bottom;

        public RationalFraction() {
        }
    }
}
