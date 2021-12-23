package org.debug.smartdeviceiot.server.signalprocess.freqDomain;

import javafx.application.Platform;
import javafx.print.*;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.apache.commons.math3.util.MathArrays;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import java.io.*;
import java.util.Arrays;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FreqDomainUtil {

    private static final FastFourierTransformer transformer;
    private static final double LOG_OF_2;

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

        return IntStream.range(0, lifter).mapToDouble(i -> cepstrum[i].abs()).toArray();
    }

    public static final int nextPow2(int n){
        return n <= 0 ? 0 : highestOneBit(n - 1);
    }

    public static final int highestOneBit(int n) {
        int digit;
        for(digit = 32; digit > 0; --digit) {
            if ((n & -2147483648) == -2147483648) {
                return digit;
            }

            n <<= 1;
        }

        return digit;
    }

    public static final Stream<double[]> sliding(double[] x, int windowSize, int shiftSize) {
        int nFrames = x.length / shiftSize;
        return IntStream.range(0, nFrames).mapToObj((i) -> {
            int from = i * shiftSize;
            return Arrays.copyOfRange(x, from, from + windowSize);
        });
    }

    public static final Stream<double[]> sliding(double[] x, double[] window, int shiftSize) {
        int nFrames = x.length / shiftSize;
        return IntStream.range(0, nFrames).mapToObj((i) -> {
            int from = i * shiftSize;
            double[] frame = Arrays.copyOfRange(x, from, from + window.length);
            return MathArrays.ebeMultiply(frame, window);
        });
    }

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

    public static final double[] rectangle(int size) {
        double[] window = new double[size];

        for(int i = 0; i < size; ++i) {
            window[i] = 1.0D;
        }

        return window;
    }

    public static final double[] hanning(int size) {
        double[] window = new double[size];

        for(int i = 0; i < size; ++i) {
            window[i] = 0.5D - 0.5D * Math.cos(6.283185307179586D * (double)i / (double)size);
        }

        return window;
    }

    public static final double[] hamming(int size) {
        double[] window = new double[size];

        for(int i = 0; i < size; ++i) {
            window[i] = 0.54D - 0.46D * Math.cos(6.283185307179586D * (double)i / (double)size);
        }

        return window;
    }

    public static final double[] gaussian(int size, double sigmas) {
        double[] window = new double[size];
        double center = 0.5D * (double)size;

        for(int i = 0; i < size; ++i) {
            double x = ((double)i - center) / center * sigmas;
            window[i] = Math.exp(-0.5D * x * x);
        }

        return window;
    }

    public static final double[] gaussian(int size) {
        return gaussian(size, 3.0D);
    }

    public static final double[] blackmanharris(int size) {
        double[] window = new double[size];
        double a0 = 0.35875D;
        double a1 = 0.48829D;
        double a2 = 0.14128D;
        double a3 = 0.01168D;

        for(int i = 0; i < size; ++i) {
            double x = (double)i / (double)size;
            window[i] = 0.35875D - 0.48829D * Math.cos(2.0D * x) + 0.14128D * Math.cos(4.0D * x) - 0.01168D * Math.cos(6.0D * x);
        }

        return window;
    }

    public static final double nn2hz(double nn) {
        return 440.0D * Math.pow(2.0D, (nn - 69.0D) / 12.0D);
    }

    public static final double hz2nn(double hz) {
        return log2(hz / 440.0D) * 12.0D + 69.0D;
    }

    public static final double log2(double x) {
        return Math.log(x) / LOG_OF_2;
    }

    public static final int argmax(double[] a) {
        if (a.length == 0) {
            return -1;
        } else {
            int maxIndex = 0;
            double maxValue = a[0];

            for(int i = 1; i < a.length; ++i) {
                if (maxValue < a[i]) {
                    maxIndex = i;
                    maxValue = a[i];
                }
            }

            return maxIndex;
        }
    }

    public static final <T> Map.Entry<Double, T> nearest(NavigableMap<Double, T> nmap, double key) {
        Map.Entry<Double, T> floor = nmap.floorEntry(key);
        Map.Entry<Double, T> ceiling = nmap.ceilingEntry(key);
        if (floor.equals(ceiling)) {
            return floor;
        } else if (ceiling == null) {
            return floor;
        } else if (floor == null) {
            return ceiling;
        } else {
            return key - (Double)floor.getKey() <= (Double)ceiling.getKey() - key ? floor : ceiling;
        }
    }



    public static final double autoTickUnit(double x) {
        if (Double.isFinite(x) && x > 0.0D) {
            double x1 = Math.pow(10.0D, Math.ceil(Math.log10(x) - Math.log10(10.0D)));
            double x2 = Math.pow(10.0D, Math.ceil(Math.log10(x) - Math.log10(20.0D))) * 2.0D;
            double x5 = Math.pow(10.0D, Math.ceil(Math.log10(x) - Math.log10(50.0D))) * 5.0D;
            return Math.min(Math.min(x1, x2), x5);
        } else {
            throw new IllegalArgumentException("x must be positive finite: " + x);
        }
    }


    public static final ScheduledExecutorService newSingleDaemonThreadScheduledExecutor() {
        return Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public final Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
    }

    private static final byte[] readFully(InputStream in, byte[] dst) throws IOException {
        return readFully(in, dst, 0, dst.length);
    }

    private static final byte[] readFully(InputStream in, byte[] dst, int off, int len) throws IOException {
        int readCount = 0;

        int c;
        for(boolean var5 = false; readCount < len; readCount += c) {
            c = in.read(dst, off + readCount, len - readCount);
            if (c < 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("specified length is ").append(len).append(" bytes, ").append("but stream is terminated at ").append(readCount).append(" bytes.");
                throw new EOFException(sb.toString());
            }
        }

        return dst;
    }

    static {
        transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        LOG_OF_2 = Math.log(2.0D);
    }

    /**
     * Get loudness of current frame
     * @return loudness (dB)
     */
    public static int getLoudness(double[] wf) {
        int length = wf.length;

        double sum = 0;
        for (int k = 0; k < length; k++) sum += Math.pow(wf[k], 2);

        return (int)(Math.sqrt(sum / length) * 1000);
    }

    // Get waveform of the frame starting from current position
    public static double[] getFrameWaveform(double[] waveform) {
//        return getFrameWaveform(waveform, (int)signal.currentPos);
        return null;
    }

    public static double[] getFrameWaveform(double[] waveform, int start) {
//        int length = Math.min(signal.frameSize, waveform.length - start);
//        double[] wf = new double[length];
//        System.arraycopy(waveform, start, wf, 0, length);
//        return wf;
        return null;
    }

    /**
     * Normalize a vector
     * @param x: vector
     * @return normalized vector
     */
    public static double[] featureScaling(double[] x) {
        double[] y = new double[x.length];
        double min = x[0]; double max = x[0];
        for (double t: x) {
            if (t < min) min = t;
            if (t > max) max = t;
        }
        for (int i = 0; i < y.length; i++) y[i] = (x[i] - min) / (max - min);
        return y;
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



}
