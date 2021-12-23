////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package org.debug.smartdeviceiot.server.DSP.filtering.iir;
//
//
////import Utils.ArrayUtils;
////import Utils.math.Complex;
////import Utils.math.PolynomialRootFinderJenkinsTraub;
////import Utils.math.PolynomialUtils;
//
//import org.debug.smartdeviceiot.server.DSP.Utils.ArrayUtils;
//import org.debug.smartdeviceiot.server.DSP.Utils.math.Complex;
//import org.debug.smartdeviceiot.server.DSP.Utils.math.PolynomialRootFinderJenkinsTraub;
//import org.debug.smartdeviceiot.server.DSP.Utils.math.PolynomialUtils;
//
//public class BesselFilterDesign {
//    private BesselFilterDesign() {
//    }
//
//    //Returns the polynomial coefficients for the Bessel polynomial of the given order.
//    //(int n)
//    public static double[] computePolynomialCoefficients(int var0) {
//        double var1 = 1.0D;
//
//        for(int var3 = 1; var3 <= var0; ++var3) {
//            var1 = var1 * (double)(var0 + var3) / 2.0D;
//        }
//
//        double[] var5 = new double[var0 + 1];
//        var5[0] = var1;
//        var5[var0] = 1.0D;
//
//        for(int var4 = 1; var4 < var0; ++var4) {
//            var5[var4] = var5[var4 - 1] * 2.0D * (double)(var0 - var4 + 1) / (double)(2 * var0 - var4 + 1) / (double)var4;
//        }
//
//        return var5;
//    }
//
//
//    //Evaluates the transfer function of a Bessel filter.
//    //                                    (double[] polyCoeffs, Complex s)
//    public static Complex transferFunction(double[] var0, Complex var1) {
//        PolynomialUtils.RationalFraction var2 = new PolynomialUtils.RationalFraction();
//        var2.top = new double[]{var0[var0.length - 1]};
//        var2.bottom = var0;
//        return PolynomialUtils.evaluate(var2, var1);
//    }
//
//    //Computes the normalized gain of a Bessel filter at a given frequency.
//    //                              (double[] polyCoeffs, double w)
//    public static double computeGain(double[] var0, double var1) {
//        Complex var3 = new Complex(0.0D, var1);
//        Complex var4 = transferFunction(var0, var3);
//        return var4.abs();
//    }
//
//
//    //This method uses appoximation to find the frequency for a given gain.
//    //                                       (double[] polyCoeffs, double gain)
//    public static double findFrequencyForGain(double[] var0, double var1) {
//        if (var1 <= 0.999999D && var1 >= 1.0E-6D) {
//            double var6 = 1.0D;
//            int var5 = 0;
//
//            do {
//                if (computeGain(var0, var6) >= var1) {
//                    double var8 = 1.0D;
//                    var5 = 0;
//
//                    do {
//                        if (computeGain(var0, var8) <= var1) {
//                            var5 = 0;
//
//                            do {
//                                if (var8 - var6 < 1.0E-15D) {
//                                    return var6;
//                                }
//
//                                double var10 = (var8 + var6) / 2.0D;
//                                double var12 = computeGain(var0, var10);
//                                if (var12 > var1) {
//                                    var6 = var10;
//                                } else {
//                                    var8 = var10;
//                                }
//                            } while(var5++ <= 1000);
//
//                            throw new AssertionError("No convergence.");
//                        }
//
//                        var8 *= 2.0D;
//                    } while(var5++ <= 100);
//
//                    throw new AssertionError();
//                }
//
//                var6 /= 2.0D;
//            } while(var5++ <= 100);
//
//            throw new AssertionError();
//        } else {
//            throw new IllegalArgumentException();
//        }
//    }
//
//    //Returns the frequency normalization scaling factor for a Bessel filter.
//    //                                              (double[] polyCoeffs)
//    public static double findFrequencyScalingFactor(double[] var0) {
//        double var1 = 1.0D / Math.sqrt(2.0D);
//        return findFrequencyForGain(var0, var1);
//    }
//
//    //Returns the frequency normalized s-plane poles for a Bessel filter.
//                                    //  (int n)
//    public static Complex[] computePoles(int var0) {
//        double[] var1 = computePolynomialCoefficients(var0);
//        double[] var2 = ArrayUtils.reverse(var1);
//        double var3 = findFrequencyScalingFactor(var2);
//        Complex[] var5 = PolynomialRootFinderJenkinsTraub.findRoots(var2);
//        Complex[] var6 = ArrayUtils.divide(var5, var3);
//        return var6;
//    }
//}
