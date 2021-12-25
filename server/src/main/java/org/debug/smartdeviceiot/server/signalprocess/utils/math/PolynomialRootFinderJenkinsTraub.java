//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.debug.smartdeviceiot.server.signalprocess.utils.math;

public class PolynomialRootFinderJenkinsTraub {
    private PolynomialRootFinderJenkinsTraub() {
    }

    public static Complex[] findRoots(double[] var0) {
        PolynomialRootFinderJenkinsTraub.GlobalEnvironment var1 = new PolynomialRootFinderJenkinsTraub.GlobalEnvironment();
        return var1.rpoly(var0);
    }

    private static void quadsd(int var0, double var1, double var3, double[] var5, double[] var6, double[] var7) {
        double var8 = var5[1];
        var6[1] = var8;
        double var10 = var5[2] - var1 * var8;
        var6[2] = var10;

        for(int var12 = 3; var12 <= var0; ++var12) {
            double var13 = var5[var12] - var1 * var10 - var3 * var8;
            var6[var12] = var13;
            var8 = var10;
            var10 = var13;
        }

        var7[0] = var10;
        var7[1] = var8;
    }

    private static Complex[] quad(double var0, double var2, double var4) {
        if (var0 == 0.0D && var2 == 0.0D) {
            return new Complex[]{new Complex(0.0D), new Complex(0.0D)};
        } else if (var0 == 0.0D) {
            return new Complex[]{new Complex(-var4 / var2), new Complex(0.0D)};
        } else if (var4 == 0.0D) {
            return new Complex[]{new Complex(0.0D), new Complex(-var2 / var0)};
        } else {
            double var6 = var2 / 2.0D;
            double var8;
            double var10;
            double var12;
            if (Math.abs(var6) < Math.abs(var4)) {
                var12 = var4 >= 0.0D ? var0 : -var0;
                var8 = var6 * (var6 / Math.abs(var4)) - var12;
                var10 = Math.sqrt(Math.abs(var8)) * Math.sqrt(Math.abs(var4));
            } else {
                var8 = 1.0D - var0 / var6 * (var4 / var6);
                var10 = Math.sqrt(Math.abs(var8)) * Math.abs(var6);
            }

            if (var8 >= 0.0D) {
                var12 = var6 >= 0.0D ? -var10 : var10;
                double var14 = (-var6 + var12) / var0;
                double var16 = var14 != 0.0D ? var4 / var14 / var0 : 0.0D;
                return new Complex[]{new Complex(var16), new Complex(var14)};
            } else {
                Complex var18 = new Complex(-var6 / var0, Math.abs(var10 / var0));
                return new Complex[]{var18, var18.conj()};
            }
        }
    }

    private static class GlobalEnvironment {
        private static final double eta = 2.22E-16D;
        private static final double base = 2.0D;
        private static final double infin = 3.4028234663852886E38D;
        private static final double smalno = 1.1754943508222875E-38D;
        private static final double are = 2.22E-16D;
        private static final double mre = 2.22E-16D;
        private static final double rotationAngleDeg = 94.0D;
        private static final double rotationAngle = 1.6406094968746698D;
        private static final double cosr = Math.cos(1.6406094968746698D);
        private static final double sinr = Math.sin(1.6406094968746698D);
        private int n;
        private int nn;
        private double[] p;
        private double[] qp;
        private double[] k;
        private double[] qk;
        private double u;
        private double v;
        private double a;
        private double b;
        private double c;
        private double d;
        private double a1;
        private double a2;
        private double a3;
        private double a6;
        private double a7;
        private double e;
        private double f;
        private double g;
        private double h;
        private Complex sz;
        private Complex lz;

        private GlobalEnvironment() {
        }

        private Complex[] rpoly(double[] var1) {
            int var2 = var1.length - 1;
            Complex[] var3 = new Complex[var2];
            double var6 = Math.sqrt(0.5D);
            double var8 = -var6;
            this.n = var2;
            if (var1[0] == 0.0D) {
                throw new IllegalArgumentException("The leading coefficient must not be zero.");
            } else {
                while(this.n > 0 && var1[this.n] == 0.0D) {
                    var3[var2 - this.n] = Complex.ZERO;
                    --this.n;
                }

                this.nn = this.n + 1;
                this.p = new double[this.nn + 1];

                for(int var10 = 1; var10 <= this.nn; ++var10) {
                    this.p[var10] = var1[var10 - 1];
                }

                label223:
                while(this.n >= 1) {
                    if (this.n == 1) {
                        var3[var2 - 1] = new Complex(-this.p[2] / this.p[1]);
                        return var3;
                    }

                    if (this.n == 2) {
                        Complex[] var38 = PolynomialRootFinderJenkinsTraub.quad(this.p[1], this.p[2], this.p[3]);
                        var3[var2 - 2] = var38[0];
                        var3[var2 - 1] = var38[1];
                        return var3;
                    }

                    double var37 = 0.0D;
                    double var12 = 3.4028234663852886E38D;

                    for(int var14 = 1; var14 <= this.nn; ++var14) {
                        double var15 = Math.abs(this.p[var14]);
                        if (var15 > var37) {
                            var37 = var15;
                        }

                        if (var15 != 0.0D && var15 < var12) {
                            var12 = var15;
                        }
                    }

                    double var39 = 5.295019598298592E-23D / var12;
                    if (var39 == 0.0D) {
                        var39 = 1.1754943508222875E-38D;
                    }

                    if (var39 > 1.0D && 3.4028234663852886E38D / var39 >= var37 || var39 <= 1.0D && var37 > 10.0D) {
                        double var16 = Math.log(var39) / Math.log(2.0D) + 0.5D;
                        double var18 = Math.pow(2.0D, var16);
                        if (var18 != 1.0D) {
                            for(int var20 = 1; var20 <= this.nn; ++var20) {
                                this.p[var20] = var18 * this.p[var20];
                            }
                        }
                    }

                    double[] var40 = new double[this.nn + 1];

                    for(int var17 = 1; var17 <= this.nn; ++var17) {
                        var40[var17] = Math.abs(this.p[var17]);
                    }

                    var40[this.nn] = -var40[this.nn];
                    double var41 = Math.exp((Math.log(-var40[this.nn]) - Math.log(var40[1])) / (double)this.n);
                    double var19;
                    if (var40[this.n] != 0.0D) {
                        var19 = -var40[this.nn] / var40[this.n];
                        if (var19 < var41) {
                            var41 = var19;
                        }
                    }

                    while(true) {
                        var19 = var41 * 0.1D;
                        double var21 = var40[1];

                        int var23;
                        for(var23 = 2; var23 <= this.nn; ++var23) {
                            var21 = var21 * var19 + var40[var23];
                        }

                        if (var21 <= 0.0D) {
                            for(var19 = var41; Math.abs(var19 / var41) > 0.005D; var41 -= var19) {
                                var21 = var40[1];
                                double var42 = var21;

                                for(int var25 = 2; var25 <= this.n; ++var25) {
                                    var21 = var21 * var41 + var40[var25];
                                    var42 = var42 * var41 + var21;
                                }

                                var21 = var21 * var41 + var40[this.nn];
                                var19 = var21 / var42;
                            }

                            var21 = var41;
                            var23 = this.n - 1;
                            this.k = new double[this.n + 1];

                            for(int var24 = 2; var24 <= this.n; ++var24) {
                                this.k[var24] = (double)(this.nn - var24) * this.p[var24] / (double)this.n;
                            }

                            this.k[1] = this.p[1];
                            double var43 = this.p[this.nn];
                            double var26 = this.p[this.n];
                            boolean var28 = this.k[this.n] == 0.0D;

                            int var35;
                            for(int var29 = 1; var29 <= 5; ++var29) {
                                double var30 = this.k[this.n];
                                if (var28) {
                                    for(int var46 = 1; var46 <= var23; ++var46) {
                                        int var33 = this.nn - var46;
                                        this.k[var33] = this.k[var33 - 1];
                                    }

                                    this.k[1] = 0.0D;
                                    var28 = this.k[this.n] == 0.0D;
                                } else {
                                    double var32 = -var43 / var30;

                                    for(int var34 = 1; var34 <= var23; ++var34) {
                                        var35 = this.nn - var34;
                                        this.k[var35] = var32 * this.k[var35 - 1] + this.p[var35];
                                    }

                                    this.k[1] = this.p[1];
                                    var28 = Math.abs(this.k[this.n]) <= Math.abs(var26) * 2.22E-16D * 10.0D;
                                }
                            }

                            double[] var44 = new double[this.n + 1];

                            int var45;
                            for(var45 = 1; var45 <= this.n; ++var45) {
                                var44[var45] = this.k[var45];
                            }

                            var45 = 1;

                            do {
                                double var31 = cosr * var6 - sinr * var8;
                                var8 = sinr * var6 + cosr * var8;
                                var6 = var31;
                                double var47 = var21 * var31;
                                this.u = -2.0D * var47;
                                this.v = var21;
                                this.qp = new double[this.nn + 1];
                                this.qk = new double[this.n + 1];
                                var35 = this.fxshfr(20 * var45, var47);
                                int var36;
                                if (var35 > 0) {
                                    var3[var2 - this.n] = this.sz;
                                    if (var35 > 1) {
                                        var3[var2 - this.n + 1] = this.lz;
                                    }

                                    this.nn -= var35;
                                    this.n = this.nn - 1;

                                    for(var36 = 1; var36 <= this.nn; ++var36) {
                                        this.p[var36] = this.qp[var36];
                                    }

                                    this.qp = null;
                                    this.qk = null;
                                    continue label223;
                                }

                                for(var36 = 1; var36 <= this.n; ++var36) {
                                    this.k[var36] = var44[var36];
                                }
                            } while(var45++ <= 20);

                            throw new RuntimeException("No convergence.");
                        }

                        var41 = var19;
                    }
                }

                return var3;
            }
        }

        private int fxshfr(int var1, double var2) {
            double var4 = 0.0D;
            double var6 = 0.0D;
            double var8 = 0.25D;
            double var10 = 0.25D;
            double var12 = var2;
            double var14 = this.v;
            double[] var16 = new double[2];
            PolynomialRootFinderJenkinsTraub.quadsd(this.nn, this.u, this.v, this.p, this.qp, var16);
            this.a = var16[0];
            this.b = var16[1];
            int var17 = this.calcsc();

            for(int var18 = 1; var18 <= var1; ++var18) {
                this.nextk(var17);
                var17 = this.calcsc();
                double[] var19 = this.newest(var17);
                double var20 = var19[0];
                double var22 = var19[1];
                double var26 = 0.0D;
                if (this.k[this.n] != 0.0D) {
                    var26 = -this.p[this.nn] / this.k[this.n];
                }

                double var28 = 1.0D;
                double var30 = 1.0D;
                if (var18 != 1 && var17 != 3) {
                    if (var22 != 0.0D) {
                        var28 = Math.abs((var22 - var14) / var22);
                    }

                    if (var26 != 0.0D) {
                        var30 = Math.abs((var26 - var12) / var26);
                    }

                    double var32 = var28 < var6 ? var28 * var6 : 1.0D;
                    double var34 = var30 < var4 ? var30 * var4 : 1.0D;
                    boolean var36 = var32 < var8;
                    boolean var37 = var34 < var10;
                    if (var37 || var36) {
                        double var38 = this.u;
                        double var40 = this.v;
                        double[] var42 = new double[this.n + 1];

                        for(int var43 = 1; var43 <= this.n; ++var43) {
                            var42[var43] = this.k[var43];
                        }

                        double var50 = var26;
                        boolean var45 = false;
                        boolean var46 = false;
                        int var47 = var37 && (!var36 || var34 < var32) ? 40 : 20;

                        label112:
                        while(true) {
                            while(true) {
                                while(true) {
                                    if (var47 == 70) {
                                        break label112;
                                    }

                                    int var48;
                                    switch(var47) {
                                    case 20:
                                        var48 = this.quadit(var20, var22);
                                        if (var48 > 0) {
                                            return var48;
                                        }

                                        var45 = true;
                                        var8 *= 0.25D;
                                        if (!var46 && var37) {
                                            for(int var49 = 1; var49 <= this.n; ++var49) {
                                                this.k[var49] = var42[var49];
                                            }

                                            var47 = 40;
                                            break;
                                        }

                                        var47 = 50;
                                        break;
                                    case 40:
                                        PolynomialRootFinderJenkinsTraub.GlobalEnvironment.RealitOut var52 = this.realit(var50);
                                        if (var52.nz > 0) {
                                            return var52.nz;
                                        }

                                        var50 = var52.sss;
                                        var46 = true;
                                        var10 *= 0.25D;
                                        if (var52.iflag) {
                                            var47 = 50;
                                        } else {
                                            var20 = -(var50 + var50);
                                            var22 = var50 * var50;
                                            var47 = 20;
                                        }
                                        break;
                                    case 50:
                                        this.u = var38;
                                        this.v = var40;

                                        for(var48 = 1; var48 <= this.n; ++var48) {
                                            this.k[var48] = var42[var48];
                                        }

                                        if (var36 && !var45) {
                                            var47 = 20;
                                            break;
                                        }

                                        double[] var51 = new double[2];
                                        PolynomialRootFinderJenkinsTraub.quadsd(this.nn, this.u, this.v, this.p, this.qp, var51);
                                        this.a = var51[0];
                                        this.b = var51[1];
                                        var17 = this.calcsc();
                                        var47 = 70;
                                        break;
                                    default:
                                        throw new AssertionError();
                                    }
                                }
                            }
                        }
                    }
                }

                var14 = var22;
                var12 = var26;
                var6 = var28;
                var4 = var30;
            }

            return 0;
        }

        private int quadit(double var1, double var3) {
            boolean var5 = false;
            double var6 = 0.0D;
            double var8 = 0.0D;
            this.u = var1;
            this.v = var3;
            int var10 = 0;

            while(true) {
                Complex[] var11 = PolynomialRootFinderJenkinsTraub.quad(1.0D, this.u, this.v);
                this.sz = var11[0];
                this.lz = var11[1];
                if (Math.abs(Math.abs(this.sz.re()) - Math.abs(this.lz.re())) > 0.01D * Math.abs(this.lz.re())) {
                    return 0;
                }

                double[] var12 = new double[2];
                PolynomialRootFinderJenkinsTraub.quadsd(this.nn, this.u, this.v, this.p, this.qp, var12);
                this.a = var12[0];
                this.b = var12[1];
                double var13 = Math.abs(this.a - this.sz.re() * this.b) + Math.abs(this.sz.im() * this.b);
                double var15 = Math.sqrt(Math.abs(this.v));
                double var17 = 2.0D * Math.abs(this.qp[1]);
                double var19 = -this.sz.re() * this.b;

                int var21;
                for(var21 = 2; var21 <= this.n; ++var21) {
                    var17 = var17 * var15 + Math.abs(this.qp[var21]);
                }

                var17 = var17 * var15 + Math.abs(this.a + var19);
                var17 = 1.998E-15D * var17 - 1.554E-15D * (Math.abs(this.a + var19) + Math.abs(this.b) * var15) + 4.44E-16D * Math.abs(var19);
                if (var13 <= 20.0D * var17) {
                    return 2;
                }

                ++var10;
                if (var10 > 20) {
                    return 0;
                }

                int var22;
                if (var10 >= 2 && var8 <= 0.01D && var13 >= var6 && !var5) {
                    if (var8 < 2.22E-16D) {
                        var8 = 2.22E-16D;
                    }

                    var8 = Math.sqrt(var8);
                    this.u -= this.u * var8;
                    this.v += this.v * var8;
                    double[] var28 = new double[2];
                    PolynomialRootFinderJenkinsTraub.quadsd(this.nn, this.u, this.v, this.p, this.qp, var28);
                    this.a = var28[0];
                    this.b = var28[1];

                    for(var22 = 1; var22 <= 5; ++var22) {
                        int var23 = this.calcsc();
                        this.nextk(var23);
                    }

                    var5 = true;
                    var10 = 0;
                }

                var6 = var13;
                var21 = this.calcsc();
                this.nextk(var21);
                var22 = this.calcsc();
                double[] var29 = this.newest(var22);
                double var24 = var29[0];
                double var26 = var29[1];
                if (var26 == 0.0D) {
                    return 0;
                }

                var8 = Math.abs((var26 - this.v) / var26);
                this.u = var24;
                this.v = var26;
            }
        }

        private PolynomialRootFinderJenkinsTraub.GlobalEnvironment.RealitOut realit(double var1) {
            double var3 = 0.0D;
            double var5 = 0.0D;
            double var7 = var1;
            int var9 = 0;

            while(true) {
                double var10 = this.p[1];
                this.qp[1] = var10;

                for(int var12 = 2; var12 <= this.nn; ++var12) {
                    var10 = var10 * var7 + this.p[var12];
                    this.qp[var12] = var10;
                }

                double var21 = Math.abs(var10);
                double var14 = Math.abs(var7);
                double var16 = 0.5D * Math.abs(this.qp[1]);

                for(int var18 = 2; var18 <= this.nn; ++var18) {
                    var16 = var16 * var14 + Math.abs(this.qp[var18]);
                }

                if (var21 <= 20.0D * (4.44E-16D * var16 - 2.22E-16D * var21)) {
                    this.sz = new Complex(var7);
                    return new PolynomialRootFinderJenkinsTraub.GlobalEnvironment.RealitOut(var1, 1, false);
                }

                ++var9;
                if (var9 > 10) {
                    return new PolynomialRootFinderJenkinsTraub.GlobalEnvironment.RealitOut(var1, 0, false);
                }

                if (var9 >= 2 && Math.abs(var5) <= 0.001D * Math.abs(var7 - var5) && var21 > var3) {
                    return new PolynomialRootFinderJenkinsTraub.GlobalEnvironment.RealitOut(var7, 0, true);
                }

                var3 = var21;
                double var22 = this.k[1];
                this.qk[1] = var22;

                int var20;
                for(var20 = 2; var20 <= this.n; ++var20) {
                    var22 = var22 * var7 + this.k[var20];
                    this.qk[var20] = var22;
                }

                if (Math.abs(var22) <= Math.abs(this.k[this.n]) * 10.0D * 2.22E-16D) {
                    this.k[1] = 0.0D;

                    for(var20 = 2; var20 <= this.n; ++var20) {
                        this.k[var20] = this.qk[var20 - 1];
                    }
                } else {
                    var5 = -var10 / var22;
                    this.k[1] = this.qp[1];

                    for(var20 = 2; var20 <= this.n; ++var20) {
                        this.k[var20] = var5 * this.qk[var20 - 1] + this.qp[var20];
                    }
                }

                var22 = this.k[1];

                for(var20 = 2; var20 <= this.n; ++var20) {
                    var22 = var22 * var7 + this.k[var20];
                }

                var5 = 0.0D;
                if (Math.abs(var22) > Math.abs(this.k[this.n]) * 10.0D * 2.22E-16D) {
                    var5 = -var10 / var22;
                }

                var7 += var5;
            }
        }

        private int calcsc() {
            double[] var1 = new double[2];
            PolynomialRootFinderJenkinsTraub.quadsd(this.n, this.u, this.v, this.k, this.qk, var1);
            this.c = var1[0];
            this.d = var1[1];
            if (Math.abs(this.c) > Math.abs(this.k[this.n]) * 100.0D * 2.22E-16D && Math.abs(this.d) > Math.abs(this.k[this.n - 1]) * 100.0D * 2.22E-16D) {
                if (Math.abs(this.d) < Math.abs(this.c)) {
                    this.e = this.a / this.c;
                    this.f = this.d / this.c;
                    this.g = this.u * this.e;
                    this.h = this.v * this.b;
                    this.a3 = this.a * this.e + (this.h / this.c + this.g) * this.b;
                    this.a1 = this.b - this.a * (this.d / this.c);
                    this.a7 = this.a + this.g * this.d + this.h * this.f;
                    return 1;
                } else {
                    this.e = this.a / this.d;
                    this.f = this.c / this.d;
                    this.g = this.u * this.b;
                    this.h = this.v * this.b;
                    this.a3 = (this.a + this.g) * this.e + this.h * (this.b / this.d);
                    this.a1 = this.b * this.f - this.a;
                    this.a7 = (this.f + this.u) * this.a + this.h;
                    return 2;
                }
            } else {
                return 3;
            }
        }

        private void nextk(int var1) {
            if (var1 == 3) {
                this.k[1] = 0.0D;
                this.k[2] = 0.0D;

                for(int var5 = 3; var5 <= this.n; ++var5) {
                    this.k[var5] = this.qk[var5 - 2];
                }

            } else {
                double var2 = var1 == 1 ? this.b : this.a;
                int var4;
                if (Math.abs(this.a1) > Math.abs(var2) * 2.22E-16D * 10.0D) {
                    this.a7 /= this.a1;
                    this.a3 /= this.a1;
                    this.k[1] = this.qp[1];
                    this.k[2] = this.qp[2] - this.a7 * this.qp[1];

                    for(var4 = 3; var4 <= this.n; ++var4) {
                        this.k[var4] = this.a3 * this.qk[var4 - 2] - this.a7 * this.qp[var4 - 1] + this.qp[var4];
                    }
                } else {
                    this.k[1] = 0.0D;
                    this.k[2] = -this.a7 * this.qp[1];

                    for(var4 = 3; var4 <= this.n; ++var4) {
                        this.k[var4] = this.a3 * this.qk[var4 - 2] - this.a7 * this.qp[var4 - 1];
                    }
                }

            }
        }

        private double[] newest(int var1) {
            if (var1 == 3) {
                return new double[]{0.0D, 0.0D};
            } else {
                double var2;
                double var4;
                if (var1 == 2) {
                    var2 = (this.a + this.g) * this.f + this.h;
                    var4 = (this.f + this.u) * this.c + this.v * this.d;
                } else {
                    var2 = this.a + this.u * this.b + this.h * this.f;
                    var4 = this.c + (this.u + this.v * this.f) * this.d;
                }

                double var6 = -this.k[this.n] / this.p[this.nn];
                double var8 = -(this.k[this.n - 1] + var6 * this.p[this.n]) / this.p[this.nn];
                double var10 = this.v * var8 * this.a1;
                double var12 = var6 * this.a7;
                double var14 = var6 * var6 * this.a3;
                double var16 = var10 - var12 - var14;
                double var18 = var4 + var6 * var2 - var16;
                if (var18 == 0.0D) {
                    return new double[]{0.0D, 0.0D};
                } else {
                    double var20 = this.u - (this.u * (var14 + var12) + this.v * (var6 * this.a1 + var8 * this.a7)) / var18;
                    double var22 = this.v * (1.0D + var16 / var18);
                    return new double[]{var20, var22};
                }
            }
        }

        private static class RealitOut {
            double sss;
            int nz;
            boolean iflag;

            RealitOut(double var1, int var3, boolean var4) {
                this.sss = var1;
                this.nz = var3;
                this.iflag = var4;
            }
        }
    }
}
