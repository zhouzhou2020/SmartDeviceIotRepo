//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.debug.smartdeviceiot.server.signalprocess.Utils.math;


//import Utils.ArrayUtils;

import org.debug.smartdeviceiot.server.signalprocess.Utils.ArrayUtils;

public class PolynomialRootFinderLaguerre {
    private static final double EPSS = 1.0E-14D;

    private PolynomialRootFinderLaguerre() {
    }

    public static Complex[] findRoots(double[] var0) {
        return findRoots(ArrayUtils.toComplex(var0));
    }

    public static Complex[] findRoots(Complex[] var0) {
        int var1 = var0.length - 1;
        Complex[] var2 = new Complex[var1];
        Complex[] var3 = var0;

        int var4;
        label39:
        for(var4 = 0; var4 < var1; ++var4) {
            int var6 = 0;

            do {
                Complex var7 = var6 == 0 ? new Complex(0.0D) : randomStart();
                Complex var5 = laguer(var3, var7);
                if (var5 != null) {
                    var2[var4] = var5;
                    var3 = PolynomialUtils.deflate(var3, var5, 0.0D);
                    continue label39;
                }
            } while(var6++ <= 1000);

            throw new RuntimeException("Root finding aborted in random loop.");
        }

        for(var4 = 0; var4 < var1; ++var4) {
            var2[var4] = laguer(var0, var2[var4]);
            if (var2[var4] == null) {
                throw new RuntimeException("Polish failed.");
            }
        }

        return var2;
    }

    private static Complex laguer(Complex[] var0, Complex var1) {
        int var2 = var0.length - 1;
        Complex var3 = new Complex((double)var2);
        Complex var4 = var1;

        for(int var5 = 0; var5 < 80; ++var5) {
            Complex var6 = var0[0];
            double var7 = var6.abs();
            Complex var9 = Complex.ZERO;
            Complex var10 = Complex.ZERO;
            double var11 = var4.abs();

            for(int var13 = 1; var13 <= var2; ++var13) {
                var10 = var4.mul(var10).add(var9);
                var9 = var4.mul(var9).add(var6);
                var6 = var4.mul(var6).add(var0[var13]);
                var7 = var6.abs() + var11 * var7;
            }

            var7 *= 1.0E-14D;
            if (var6.abs() <= var7) {
                return var4;
            }

            Complex var24 = var9.div(var6);
            Complex var14 = var24.mul(var24);
            Complex var15 = var14.sub(Complex.TWO.mul(var10.div(var6)));
            Complex var16 = var3.sub(Complex.ONE).mul(var3.mul(var15).sub(var14)).sqrt();
            Complex var17 = var24.add(var16);
            Complex var18 = var24.sub(var16);
            double var19 = var17.abs();
            double var21 = var18.abs();
            if (var19 < var21) {
                var17 = var18;
            }

            Complex var23;
            if (var19 <= 0.0D && var21 <= 0.0D) {
                var23 = (new Complex(Math.log(1.0D + var11), (double)(var5 + 1))).exp();
            } else {
                var23 = var3.div(var17);
            }

            var4 = var4.sub(var23);
        }

        return null;
    }

    private static Complex randomStart() {
        return new Complex(Math.random() * 2.0D - 1.0D, Math.random() * 2.0D - 1.0D);
    }
}
