package org.debug.smartdeviceiot.server.signalprocess.Utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



import org.debug.smartdeviceiot.server.signalprocess.Utils.math.Complex;

import java.util.Arrays;
import java.util.Comparator;

public class ArrayUtils {
    private ArrayUtils() {
    }

    public static Complex[] zeros(int var0) {
        Complex[] var1 = new Complex[var0];

        for(int var2 = 0; var2 < var0; ++var2) {
            var1[var2] = Complex.ZERO;
        }

        return var1;
    }

    public static double[] toDouble(Complex[] var0, double var1) {
        double[] var3 = new double[var0.length];

        for(int var4 = 0; var4 < var0.length; ++var4) {
            var3[var4] = var0[var4].toDouble(var1);
        }

        return var3;
    }

    public static double[] toDouble(int[] var0) {
        double[] var1 = new double[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = (double)var0[var2];
        }

        return var1;
    }

    public static double[] toDouble(Double[] var0) {
        double[] var1 = new double[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = var0[var2];
        }

        return var1;
    }

    public static Complex[] toComplex(double[] var0) {
        Complex[] var1 = new Complex[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = new Complex(var0[var2]);
        }

        return var1;
    }

    public static Double[] toObject(double[] var0) {
        Double[] var1 = new Double[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = var0[var2];
        }

        return var1;
    }

    public static double[] multiply(double[] var0, double var1) {
        double[] var3 = new double[var0.length];

        for(int var4 = 0; var4 < var0.length; ++var4) {
            var3[var4] = var0[var4] * var1;
        }

        return var3;
    }

    public static Complex[] multiply(Complex[] var0, double var1) {
        Complex[] var3 = new Complex[var0.length];

        for(int var4 = 0; var4 < var0.length; ++var4) {
            var3[var4] = var0[var4].mul(var1);
        }

        return var3;
    }

    public static double[] divide(double[] var0, double var1) {
        double[] var3 = new double[var0.length];

        for(int var4 = 0; var4 < var0.length; ++var4) {
            var3[var4] = var0[var4] / var1;
        }

        return var3;
    }

    public static Complex[] divide(Complex[] var0, double var1) {
        Complex[] var3 = new Complex[var0.length];

        for(int var4 = 0; var4 < var0.length; ++var4) {
            var3[var4] = var0[var4].div(var1);
        }

        return var3;
    }

    public static double[] reverse(double[] var0) {
        double[] var1 = new double[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = var0[var0.length - 1 - var2];
        }

        return var1;
    }

    public static double[] sortByMagnitude(double[] var0) {
        Double[] var1 = toObject(var0);
        Arrays.sort(var1, new Comparator<Double>() {
            public int compare(Double var1, Double var2) {
                return Double.compare(Math.abs(var1), Math.abs(var2));
            }

            public boolean equals(Object var1) {
                throw new AssertionError();
            }
        });
        double[] var2 = toDouble(var1);
        return var2;
    }

    public static Complex[] sortByImRe(Complex[] var0) {
        Complex[] var1 = copy(var0);
        Arrays.sort(var1, new Comparator<Complex>() {
            public int compare(Complex var1, Complex var2) {
                if (var1.im() < var2.im()) {
                    return -1;
                } else {
                    return var1.im() > var2.im() ? 1 : Double.compare(var1.re(), var2.re());
                }
            }

            public boolean equals(Object var1) {
                throw new AssertionError();
            }
        });
        return var1;
    }

    public static Complex[] sortByAbsImNegImRe(Complex[] var0) {
        Complex[] var1 = copy(var0);
        Arrays.sort(var1, new Comparator<Complex>() {
            public int compare(Complex var1, Complex var2) {
                double var3 = Math.abs(var1.im());
                double var5 = Math.abs(var2.im());
                if (var3 < var5) {
                    return -1;
                } else if (var3 > var5) {
                    return 1;
                } else if (var1.im() > var2.im()) {
                    return -1;
                } else {
                    return var1.im() < var2.im() ? 1 : Double.compare(var1.re(), var2.re());
                }
            }

            public boolean equals(Object var1) {
                throw new AssertionError();
            }
        });
        return var1;
    }

    private static Complex[] copy(Complex[] var0) {
        Complex[] var1 = new Complex[var0.length];

        for(int var2 = 0; var2 < var0.length; ++var2) {
            var1[var2] = var0[var2];
        }

        return var1;
    }

    public static String toString(double[] var0) {
        StringBuilder var1 = new StringBuilder();
        var1.append("[");

        for(int var2 = 0; var2 < var0.length; ++var2) {
            if (var2 > 0) {
                var1.append(" ");
            }

            var1.append(var0[var2]);
        }

        var1.append("]");
        return var1.toString();
    }

    public static String toString(Complex[] var0) {
        StringBuilder var1 = new StringBuilder();
        var1.append("[");

        for(int var2 = 0; var2 < var0.length; ++var2) {
            if (var2 > 0) {
                var1.append(" ");
            }

            var1.append(var0[var2]);
        }

        var1.append("]");
        return var1.toString();
    }
}

