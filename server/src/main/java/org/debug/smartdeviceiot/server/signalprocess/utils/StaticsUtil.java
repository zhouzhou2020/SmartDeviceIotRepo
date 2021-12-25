package org.debug.smartdeviceiot.server.signalprocess.utils;

public class StaticsUtil {

    //均值
    static double MEAN(double x[], int start, int end) {
        double y = 0.0;
        for (int i = start; i <= end; i++) {
            y = y + x[i];
        }
        return (y / (end + 1 - start));
    }

    //方差
    static double VARIANCE(double x[], int start, int end) {
        double y = 0.0;
        double mean0 = MEAN(x, start, end);
        for (int i = start; i <= end; i++) {
            y = y + (x[i] - mean0)*(x[i] - mean0);
        }
        return (y / (end - start));
    }

    //标准差
    static double STD(double x[], int start, int end) {
        return Math.sqrt(VARIANCE(x, start, end));
    }

    static double VPP(double x[], int start, int end) {  //极差，峰峰值
        double min = x[start], max = x[start];
        for (int i = start; i <= end; i++) {
            if (x[i] < min) {
                min = x[i];
            }
            else if (x[i] > max) {
                max = x[i];
            }
        }
        return (max - min);
    }

    //均方根
    static double RMS(double x[], int start, int end) {
        double y = 0.0;
        for (int i = start; i <= end; i++) {
            y = y + x[i] * x[i];
        }
        y = y / (end + 1 - start);
        return Math.sqrt(y);
    }

    static double VP(double x[], int start, int end) {    //峰值,是一个绝对值
        double min = x[start], max = x[start];
        for (int i = start; i <= end; i++) {
            if (x[i] < min) {
                min = x[i];
            }
            else if (x[i] > max) {
                max = x[i];
            }
        }
        if (Math.abs(max) >= Math.abs(min)) {
            return Math.abs(max);
        }
        return Math.abs(min);
    }

    //平均幅值
    static double Xmean(double x[], int start, int end) {
        int i;
        double m;
        double[] y=new double[end-start+1];
        for (i = start; i <= end; i++)
        {
            y[i - start] = Math.abs(x[i]);
        }
        m = MEAN(y, start, end);
        return m;
    }

    //方根幅值
    static double Xr(double x[], int start, int end) {
        int i;
        double[] y=new double[end-start+1];
        for (i = start; i <= end; i++)
        {
            y[i - start] = Math.sqrt(Math.abs(x[i]));
        }
        double m = MEAN(y, start, end);
        return m * m;
    }

    //波形因子,无量纲
    static double WaveParam(double x[], int start, int end) {
        return (RMS(x, start, end) / Xmean(x, start, end));
    }

    //峰值因子,无量纲
    static double VPParam(double x[], int start, int end) {
        return VP(x, start, end) / RMS(x, start, end);
    }

//    //峰值系数,无量纲
//    static double VPParam(double x[], int start, int end) {
//        return RMS(x, start, end) / VPP(x, start, end);
//    }

    //脉冲因子,无量纲
    static double PulseParam(double x[], int start, int end) {
        return VP(x, start, end) / Xmean(x, start, end);   //有的公式是除以mean
    }

    //裕度因子,无量纲
    static double YDParam(double x[], int start, int end) {
        return VP(x, start, end) / Xr(x, start, end);
    }

    //偏态，偏度,斜度
    static double SKEW(double x[], int start, int end) {
        double y = 0.0;
        double mean0 = MEAN(x, start, end);
        double s = STD(x, start, end);
        for (int i = start; i <= end; i++) {
            y = y + (x[i] - mean0)*(x[i] - mean0)*(x[i] - mean0);
        }
        return (y / (end + 1 - start) / (s*s*s));
    }

    //峭度
    static double KURTOSIS(double x[], int start, int end) {
        double y = 0.0;
        double mean0 = MEAN(x, start, end);
        double s = VARIANCE(x, start, end);
        for (int i = start; i <= end; i++) {
            y = y + (x[i] - mean0)*(x[i] - mean0)*(x[i] - mean0)*(x[i] - mean0);
        }
        return (y / (end + 1 - start) / (s*s));
    }

    // 最大值及其索引
    public static final int arrMax(double[] a) {
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

}
