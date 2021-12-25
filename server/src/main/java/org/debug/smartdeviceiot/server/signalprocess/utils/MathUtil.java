package org.debug.smartdeviceiot.server.signalprocess.utils;

import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.util.MathArrays;

import java.util.Arrays;
import java.util.Map;
import java.util.NavigableMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MathUtil {

    private static final double LOG_OF_2;

    static {
        LOG_OF_2 = Math.log(2.0D);
    }

    // 数学关系计算
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


    public static final double log2(double x) {
        return Math.log(x) / LOG_OF_2;
    }

    public static final double nn2hz(double nn) {
        return 440.0D * Math.pow(2.0D, (nn - 69.0D) / 12.0D);
    }

    public static final double hz2nn(double hz) {
        return log2(hz / 440.0D) * 12.0D + 69.0D;
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


    public static double[] getFrame(double[] waveform, int start) {
//        double SampleRate=40960;  //采样率
//        double frameInterval=1024.0/40960;
//        int frameSize = (int)(SampleRate*frameInterval);  //窗长

        int frameSize=100;
        int length = Math.min(frameSize, waveform.length - start);
//        System.out.println("length = " + length);
        double[] wf = new double[length];
        System.arraycopy(waveform, start, wf, 0, length);
        return wf;
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
}
