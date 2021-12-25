package org.debug.smartdeviceiot.server.signalprocess.utils;

public class WindowUtil {

    // 窗函数
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
}
