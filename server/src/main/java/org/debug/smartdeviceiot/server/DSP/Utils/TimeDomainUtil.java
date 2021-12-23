package org.debug.smartdeviceiot.server.DSP.Utils;


//Time domain indicator


public class TimeDomainUtil {

    static double RMS(double[] x, int start, int end) {  //均方根
        double y = 0;
        for (int i = start; i <= end; i++) {
            y = y + x[i] * x[i];
        }
        y = y / (end + 1 - start);
        return Math.sqrt(y);
    }

    static double VPP(double[] x, int start, int end) {  //极差
        double min = x[start], max = x[start];
        for (int i = start; i <= end; i++) {
            if (x[i] < min) {
                min = x[i];
            } else if (x[i] > max) {
                max = x[i];
            }
        }
        return (max - min);
    }

    static double VP(double[] x, int start, int end) {    //峰值
        double min = x[start], max = x[start];
        for (int i = start; i <= end; i++) {
            if (x[i] < min) {
                min = x[i];
            } else if (x[i] > max) {
                max = x[i];
            }
        }
        if (Math.abs(max) >= Math.abs(min)) {
            return Math.abs(max);
        }
        return Math.abs(min);
    }

    //均值
    static double AVG(double[] x, int start, int end) {
        double y = 0;
        for (int i = start; i <= end; i++) {
            y = y + x[i];
        }
        return (y / (end + 1 - start));
    }

    //方差
    static double VARIANCE(double[] x, int start, int end) {
        double y = 0;
        double avg = AVG(x, start, end);
        for (int i = start; i <= end; i++) {
            y = y + (x[i] - avg) * (x[i] - avg);
        }
        return (y / (end + 1 - start));
    }

    //偏态，偏度
    static double SKEW(double[] x, int start, int end) {
        double y = 0;
        double avg = AVG(x, start, end);
        double s = Math.sqrt(VARIANCE(x, start, end));
        for (int i = start; i <= end; i++) {
            y = y + (x[i] - avg) * (x[i] - avg) * (x[i] - avg);
        }
        return (y / (end + 1 - start) / (s * s * s));
    }

    //峭度
    static double KURTOSIS(double[] x, int start, int end) {
        double y = 0;
        double avg = AVG(x, start, end);
        double s = VARIANCE(x, start, end);
        for (int i = start; i <= end; i++) {
            y = y + (x[i] - avg) * (x[i] - avg) * (x[i] - avg) * (x[i] - avg);
        }
        return (y / (end + 1 - start) / (s * s));
    }


    public static void main(String[] args) {
        testStat();
    }

    //double[] input = DSP612.readInput("D:\\\\TestIO\\\\inHilb.txt");

    public static void testStat() {
        double[] input = new double[]{0.33, 1.33, 0.27333, 0.3, 0.501,
                0.444, 0.44, 0.34496, 0.33, 0.3, 0.292, 0.667};
        int len = input.length - 1;
        System.out.println("mean:" + AVG(input, 0, len));
        System.out.println("峭度:" + KURTOSIS(input, 0, len));
        System.out.println("偏度:" + SKEW(input, 0, len));
        System.out.println("均方根：" + RMS(input, 0, len));
        System.out.println("方差：" + VARIANCE(input, 0, len));
        System.out.println("峰值：" + VP(input, 0, len));
        System.out.println("极差：" + VPP(input, 0, len));
    }

}
