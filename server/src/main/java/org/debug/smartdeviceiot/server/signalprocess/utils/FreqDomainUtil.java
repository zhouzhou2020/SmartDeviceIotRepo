package org.debug.smartdeviceiot.server.signalprocess.utils;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

import static org.debug.smartdeviceiot.server.signalprocess.utils.MathUtil.nextPow2;
import static org.debug.smartdeviceiot.server.signalprocess.utils.TranformUtil.hilbert;

public class FreqDomainUtil {

    private static final FastFourierTransformer transformer;

    static {
        transformer = new FastFourierTransformer(DftNormalization.STANDARD);
    }

//频域计算

    public static final Complex[] fft(Complex[] src) {
        if (Integer.bitCount(src.length) != 1) {
            throw new IllegalArgumentException("src.length must be power of 2");
        } else {
            return transformer.transform(src, TransformType.FORWARD);
        }
    }

    public static final Complex[] fft(double[] src) {
        if (Integer.bitCount(src.length) != 1) {
            throw new IllegalArgumentException("src.length must be power of 2");
        } else {
            return transformer.transform(src, TransformType.FORWARD);
        }
    }

    public static final Complex[] ifft(Complex[] src) {
        if (Integer.bitCount(src.length) != 1) {
            throw new IllegalArgumentException("src.length must be power of 2");
        } else {
            return transformer.transform(src, TransformType.INVERSE);
        }
    }

    public static final Complex[] ifft(double[] src) {
        if (Integer.bitCount(src.length) != 1) {
            throw new IllegalArgumentException("src.length must be power of 2");
        } else {
            return transformer.transform(src, TransformType.INVERSE);
        }
    }

    public static final Complex[] rfft(double[] src) {
        if (Integer.bitCount(src.length) != 1) {
            throw new IllegalArgumentException("src.length must be power of 2");
        } else {
            return (Complex[])Arrays.copyOf(transformer.transform(src, TransformType.FORWARD), (src.length >> 1) + 1);
        }
    }

    public static final double[] irfft(Complex[] src) {
        if (Integer.bitCount(src.length - 1) != 1) {
            throw new IllegalArgumentException("src.length must be 2^n + 1");
        } else {
            int fftSize = src.length - 1 << 1;
            Complex[] src0 = (Complex[])Arrays.copyOf(src, fftSize);
            Arrays.fill(src0, src.length, fftSize, Complex.ZERO);
            return Arrays.stream(transformer.transform(src0, TransformType.INVERSE)).mapToDouble((c) -> {
                return c.getReal();
            }).toArray();
        }
    }


    public static Complex[] getSpectrum(double[] wf) {
        final int fftSize = 1 << nextPow2(wf.length);
        final int fftSize2 = (fftSize >> 1) + 1;

        final double[] src = Arrays.stream(Arrays.copyOf(wf, fftSize))
                .map(w -> w / wf.length)
                .toArray();

        return rfft(src);
    }

    public static double[] getWaveformFromSpectrum(Complex[] spectrum) {
        return irfft(spectrum);
    }

    public static double[] getMagSpectrum(double[] wf) {
        return Arrays.stream(getSpectrum(wf))
                .mapToDouble(c -> c.abs())
                .toArray();
    }

    public static double[] getLogSpectrum(double[] wf) {
        return Arrays.stream(getSpectrum(wf))
                .mapToDouble(c -> Math.log10(c.abs()))
                .toArray();
    }

    /**
     * Calculate cepstrum series from waveform
     * @param wf: waveform
     * @return cepstrum
     */
    public static double[] cepstrumCoefficient(double[] wf, int lifter) {
        final int fftSize = 1 << nextPow2(wf.length);
        final int fftSize2 = (fftSize >> 1) + 1;

        final double[] src = Arrays.stream(Arrays.copyOf(wf, fftSize))
                .map(w -> w / wf.length)
                .toArray();

        final Complex[] spectrum = rfft(src);

        double[] specLog = Arrays.stream(spectrum)
                .mapToDouble(c -> Math.log10(c.abs()))
                .toArray();

        Complex[] cepstrum = rfft(Arrays.copyOf(specLog, specLog.length - 1));
        //range() 左闭右开
        return IntStream.range(0, lifter).mapToDouble(i -> cepstrum[i].abs()).toArray();
    }

    public static double[] rceps(double[] input) {
        double[] ans = new double[input.length];
        Complex[] iLog = new FastFourierTransformer(DftNormalization.STANDARD).transform(getLogSpectrum(input), TransformType.INVERSE);
        for (int i = 0; i < iLog.length; i++) {
            ans[i] = iLog[i].getReal();
            System.out.println("No." + i + ":" + ans[i]);
        }
        System.out.println(ans.length);
        return ans;
    }


    //  datar[] datai[] 输入序列的实部和虚部； n--输入数据长度；
    //  m--输出数据长度；  f1，f2：细化频段起止点
    public static void czt(double[] datar,double[] datai,int n,int m,double f1,double f2) {
        double e, t, ar, ai, ph, tr, ti;
        int len = n + m - 1;
        int n1, n2, i, j;
        for (j = 1, i = 1; i < 16; i++) {
            j = 2 * j;
            if (j >= len) {
                len = j;
                break;
            }
        }
//        do
//        {
//            j *= 2;
//        } while (j < len);
//        len = j;
        double[] xr = new double[len];
        double[] xi = new double[len];
        double[] wr = new double[len];
        double[] wr1 = new double[len];
        double[] wi = new double[len];
        double[] wi1 = new double[len];
        ph = 2.0 * Math.PI * (f2 - f1) / (m - 1);
        n1 = (n >= m) ? n : m;
        for (int i1 = 0; i1 < n1; i1++) {
            e = ph * i1 * i1 / 2.0;
            wr[i1] = Math.cos(e);
            wi[i1] = Math.sin(e);
            wr1[i1] = wr[i1];
            wi1[i1] = -wi[i1];
        }
        n2 = len - n + 1;
        for (int i1 = m; i1 < n2; i1++) {
            wr[i1] = 0.0;
            wi[i1] = 0.0;
        }
        for (int i1 = n2; i1 < len; i1++) {
            j = len - i1;
            wr[i1] = wr[j];
            wi[i1] = wi[j];
        }
        Complex[] in1 = new Complex[len];
        for (int i1 = 0; i1 < in1.length; i1++) {
            in1[i1] = new Complex(wr[i1], wi[i1]);
        }
        Complex[] res = new FastFourierTransformer(DftNormalization.STANDARD).
                transform(in1, TransformType.FORWARD);
        ph = -2.0 * Math.PI * f1;
        for (int i1 = 0; i1 < n; i1++) {
            e = ph * i1;
            ar = Math.cos(e);
            ai = Math.sin(e);
            tr = ar * wr1[i1] - ai * wi1[i1];
            ti = ai * wr1[i1] + ar * wi1[i1];
            t = datar[i1] * tr - datai[i1] * ti;
            xi[i1] = datar[i1] * ti + datai[i1] * tr;
            xr[i1] = t;
        }
        for (int i1 = n; i1 < len; i1++) {
            xr[i1] = 0.0;
            xi[i1] = 0.0;
        }
        Complex[] in2 = new Complex[len];
        for (int i2 = 0; i2 < in2.length; i2++) {
            in2[i2] = new Complex(xr[i2], xi[i2]);
        }
        Complex[] res2 = new FastFourierTransformer(DftNormalization.STANDARD).
                transform(in2, TransformType.FORWARD);
        for (int i1 = 0; i1 < len; i1++) {
            tr = res2[i1].getReal() * res[i1].getReal() - res2[i1].getImaginary() * res[i1].getImaginary();
            xi[i1] = res2[i1].getReal() * res[i1].getImaginary() + res2[i1].getImaginary() * res[i1].getReal();
            xr[i1] = tr;
        }
        Complex[] in3 = new Complex[len];
        for (int i3 = 0; i3 < in3.length; i3++) {
            in3[i3] = new Complex(xr[i3], xi[i3]);
        }
        Complex[] res3 = new FastFourierTransformer(DftNormalization.STANDARD).
                transform(in3, TransformType.INVERSE);
        for (int i1 = 0; i1 < m; i1++) {
            tr = res3[i1].getReal() * wr1[i1] - res3[i1].getImaginary() * wi1[i1];
            xi[i1] = res3[i1].getReal() * wi1[i1] + res3[i1].getImaginary() * wr1[i1];
            xr[i1] = tr;
            System.out.println("最终结果" + xr[i1] + "," + xi[i1]);
        }
        //幅值和频率：
        double[] mag = new double[m];
        double[] f = new double[m];
        for (int i1 = 0; i1 < m; i1++) {
            mag[i1] = Math.hypot(xr[i1], xi[i1]);
            f[i1] = f1 + i1 * (f2 - f1) / (m - 1);
        }
    }

    //没有基于apache
    public static double[] envelope(double[] signal) {
        double[] abs = new double[signal.length];
        double[] hb = hilbert(signal.length, signal);
        for (int i = 0; i < signal.length; i++) {
            //    System.out.println(signal[i]+","+hb[i]);
            abs[i] = Math.hypot(signal[i], hb[i]);
        }
        return abs;
    }

// 启动一个线程
    public static final ScheduledExecutorService newSingleDaemonThreadScheduledExecutor() {
        return Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public final Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
    }

}
