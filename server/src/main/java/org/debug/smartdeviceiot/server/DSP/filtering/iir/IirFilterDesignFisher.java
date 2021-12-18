//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//  code by Anthony J. Fisher.
//

package org.debug.smartdeviceiot.server.DSP.filtering.iir;


import Utils.ArrayUtils;
import Utils.math.Complex;
import Utils.math.PolynomialUtils;


public class IirFilterDesignFisher {
    private IirFilterDesignFisher() {
    }

    private static Complex[] getPoles(FilterCharacteristicsType var0, int var1, double var2) {
        Complex[] var4;
        switch(var0) {
        case bessel:
            var4 = BesselFilterDesign.computePoles(var1);
            return var4;
        case butterworth:
            var4 = new Complex[var1];

            for(int var16 = 0; var16 < var1; ++var16) {
                double var6 = ((double)var1 / 2.0D + 0.5D + (double)var16) * 3.141592653589793D / (double)var1;
                var4[var16] = Complex.expj(var6);
            }

            return var4;
        case chebyshev:
            if (var2 >= 0.0D) {
                throw new IllegalArgumentException("Chebyshev ripple must be negative.");
            } else {
                var4 = getPoles(FilterCharacteristicsType.butterworth, var1, 0.0D);
                double var5 = Math.pow(10.0D, -var2 / 10.0D);
                double var7 = Math.sqrt(var5 - 1.0D);
                double var9 = asinh(1.0D / var7) / (double)var1;
                if (var9 <= 0.0D) {
                    throw new AssertionError();
                }

                double var11 = Math.sinh(var9);
                double var13 = Math.cosh(var9);

                for(int var15 = 0; var15 < var1; ++var15) {
                    var4[var15] = new Complex(var4[var15].re() * var11, var4[var15].im() * var13);
                }

                return var4;
            }
        default:
            throw new UnsupportedOperationException("Filter characteristics type " + var0 + " not yet implemented.");
        }
    }

    private static PolesAndZeros normalize(Complex[] var0, FilterPassType var1, double var2, double var4, boolean var6) {
        int var7 = var0.length;
        boolean var8 = var1 == FilterPassType.bandpass || var1 == FilterPassType.bandstop;
        if (var2 > 0.0D && var2 < 0.5D) {
            if (var8 && (var4 <= 0.0D || var4 >= 0.5D)) {
                throw new IllegalArgumentException("Invalid fcf2.");
            } else {
                double var9 = Math.tan(3.141592653589793D * var2) / 3.141592653589793D;
                double var11 = var8 ? Math.tan(3.141592653589793D * var4) / 3.141592653589793D : 0.0D;
                double var13 = 6.283185307179586D * (var6 ? var9 : var2);
                double var15 = 6.283185307179586D * (var6 ? var11 : var4);
                double var17;
                double var19;
                PolesAndZeros var21;
                int var22;
                Complex var23;
                Complex var24;
                PolesAndZeros var25;
                switch(var1) {
                case lowpass:
//                    var25 = new IirFilterDesignFisher.PolesAndZeros((SyntheticClass_1)null);
                    var25 = new PolesAndZeros();
                    var25.poles = ArrayUtils.multiply(var0, var13);
                    var25.zeros = new Complex[0];
                    return var25;
                case highpass:
//                    var25 = new IirFilterDesignFisher.PolesAndZeros((SyntheticClass_1)null);
                    var25 = new PolesAndZeros();
                    var25.poles = new Complex[var7];

                    for(int var18 = 0; var18 < var7; ++var18) {
                        var25.poles[var18] = Complex.div(var13, var0[var18]);
                    }

                    var25.zeros = ArrayUtils.zeros(var7);
                    return var25;
                case bandpass:
                    var17 = Math.sqrt(var13 * var15);
                    var19 = var15 - var13;
//                    var21 = new IirFilterDesignFisher.PolesAndZeros((SyntheticClass_1)null);
                    var21 = new PolesAndZeros();
                    var21.poles = new Complex[2 * var7];

                    for(var22 = 0; var22 < var7; ++var22) {
                        var23 = var0[var22].mul(var19 / 2.0D);
                        var24 = Complex.sub(1.0D, Complex.div(var17, var23).sqr()).sqrt();
                        var21.poles[var22] = var23.mul(var24.add(1.0D));
                        var21.poles[var7 + var22] = var23.mul(Complex.sub(1.0D, var24));
                    }

                    var21.zeros = ArrayUtils.zeros(var7);
                    return var21;
                case bandstop:
                    var17 = Math.sqrt(var13 * var15);
                    var19 = var15 - var13;
//                    var21 = new IirFilterDesignFisher.PolesAndZeros((SyntheticClass_1)null);
                    var21 = new PolesAndZeros();
                    var21.poles = new Complex[2 * var7];

                    for(var22 = 0; var22 < var7; ++var22) {
                        var23 = Complex.div(var19 / 2.0D, var0[var22]);
                        var24 = Complex.sub(1.0D, Complex.div(var17, var23).sqr()).sqrt();
                        var21.poles[var22] = var23.mul(var24.add(1.0D));
                        var21.poles[var7 + var22] = var23.mul(Complex.sub(1.0D, var24));
                    }

                    var21.zeros = new Complex[2 * var7];

                    for(var22 = 0; var22 < var7; ++var22) {
                        var21.zeros[var22] = new Complex(0.0D, var17);
                        var21.zeros[var7 + var22] = new Complex(0.0D, -var17);
                    }

                    return var21;
                default:
                    throw new UnsupportedOperationException("Filter pass type " + var1 + " not yet implemented.");
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid fcf1.");
        }
    }


    private static PolesAndZeros MapSPlaneToZPlane(PolesAndZeros var0, SToZMappingMethod var1) {
        PolesAndZeros var2;
        switch(var1) {
        case bilinearTransform:
//            var2 = new IirFilterDesignFisher.PolesAndZeros((SyntheticClass_1)null);
            var2 = new PolesAndZeros();
            var2.poles = doBilinearTransform(var0.poles);
            Complex[] var3 = doBilinearTransform(var0.zeros);
            var2.zeros = extend(var3, var0.poles.length, new Complex(-1.0D));
            return var2;
        case matchedZTransform:
//            var2 = new IirFilterDesignFisher.PolesAndZeros((SyntheticClass_1)null);
            var2 = new PolesAndZeros();
            var2.poles = doMatchedZTransform(var0.poles);
            var2.zeros = doMatchedZTransform(var0.zeros);
            return var2;
        default:
            throw new UnsupportedOperationException("Mapping method " + var1 + " not yet implemented.");
        }
    }

    private static Complex[] doBilinearTransform(Complex[] var0) {
        Complex[] var1 = new Complex[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = doBilinearTransform(var0[var2]);
        }

        return var1;
    }

    private static Complex doBilinearTransform(Complex var0) {
        return var0.add(2.0D).div(Complex.sub(2.0D, var0));
    }

    private static Complex[] extend(Complex[] var0, int var1, Complex var2) {
        if (var0.length >= var1) {
            return var0;
        } else {
            Complex[] var3 = new Complex[var1];

            int var4;
            for(var4 = 0; var4 < var0.length; ++var4) {
                var3[var4] = var0[var4];
            }

            for(var4 = var0.length; var4 < var1; ++var4) {
                var3[var4] = var2;
            }

            return var3;
        }
    }

    private static Complex[] doMatchedZTransform(Complex[] var0) {
        Complex[] var1 = new Complex[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = var0[var2].exp();
        }

        return var1;
    }

    private static PolynomialUtils.RationalFraction computeTransferFunction(PolesAndZeros var0) {
        Complex[] var1 = PolynomialUtils.expand(var0.zeros);
        Complex[] var2 = PolynomialUtils.expand(var0.poles);
        PolynomialUtils.RationalFraction var5 = new PolynomialUtils.RationalFraction();
        var5.top = ArrayUtils.toDouble(var1, 1.0E-10D);
        var5.bottom = ArrayUtils.toDouble(var2, 1.0E-10D);
        return var5;
    }

    private static double computeGain(PolynomialUtils.RationalFraction var0, FilterPassType var1, double var2, double var4) {
        double var6;
        switch(var1) {
        case lowpass:
            return computeGainAt(var0, Complex.ONE);
        case highpass:
            return computeGainAt(var0, new Complex(-1.0D));
        case bandpass:
            var6 = (var2 + var4) / 2.0D;
            Complex var10 = Complex.expj(6.283185307179586D * var6);
            return computeGainAt(var0, var10);
        case bandstop:
            var6 = computeGainAt(var0, Complex.ONE);
            double var8 = computeGainAt(var0, new Complex(-1.0D));
            return Math.sqrt(var6 * var8);
        default:
            throw new RuntimeException("Unsupported filter pass type.");
        }
    }

    private static double computeGainAt(PolynomialUtils.RationalFraction var0, Complex var1) {
        return PolynomialUtils.evaluate(var0, var1).abs();
    }

    private static IirFilterCoefficients computeIirFilterCoefficients(PolynomialUtils.RationalFraction var0) {
        double var1 = var0.bottom[0];
        IirFilterCoefficients var3 = new IirFilterCoefficients();
        var3.a = ArrayUtils.divide(var0.bottom, var1);
        var3.a[0] = 1.0D;
        var3.b = ArrayUtils.divide(var0.top, var1);
        return var3;
    }

    /**design(FilterPassType filterPassType, FilterCharacteristicsType filterCharacteristicsType,
     * int filterOrder, double ripple, double fcf1, double fcf2)
     * ripple - Passband ripple in dB. Must be negative. Only used for Chebyshev filter, ignored for other filters.
     * */
    public static IirFilterCoefficients design(FilterPassType var0, FilterCharacteristicsType var1, int var2, double var3, double var5, double var7) {
        Complex[] var9 = getPoles(var1, var2, var3);
        SToZMappingMethod var10 = var1 == FilterCharacteristicsType.bessel ? SToZMappingMethod.matchedZTransform : SToZMappingMethod.bilinearTransform;
        boolean var11 = var10 == SToZMappingMethod.bilinearTransform;
        PolesAndZeros var12 = normalize(var9, var0, var5, var7, var11);
        PolesAndZeros var13 = MapSPlaneToZPlane(var12, var10);
        PolynomialUtils.RationalFraction var14 = computeTransferFunction(var13);
        double var15 = computeGain(var14, var0, var5, var7);
        IirFilterCoefficients var17 = computeIirFilterCoefficients(var14);
        var17.b = ArrayUtils.divide(var17.b, var15);
        return var17;
    }

    private static double asinh(double var0) {
        return Math.log(var0 + Math.sqrt(1.0D + var0 * var0));
    }

    private static enum SToZMappingMethod {
        bilinearTransform,
        matchedZTransform;

        private SToZMappingMethod() {
        }
    }

    private static class PolesAndZeros {
        public Complex[] poles;
        public Complex[] zeros;

        private PolesAndZeros() {
        }
    }
}
