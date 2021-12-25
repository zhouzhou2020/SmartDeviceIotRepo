package org.debug.smartdeviceiot.server.signalprocess.yfl;

//import static com.dlut.DSPcollection.Transformer.FFTcollection.irfft;
//import static com.dlut.DSPcollection.Transformer.FFTcollection.rfft;

import static org.debug.smartdeviceiot.server.signalprocess.yfl.FFTcollection.irfft;
import static org.debug.smartdeviceiot.server.signalprocess.yfl.FFTcollection.rfft;

public class Convolution {

    /**
     * & 1
     * 用快速傅里叶变换计算两个有限长序列的线性卷积
     * z(n) = XIGMA(i = 0)(M - 1)[x(i) * y(n-i)];
     * L >= M + N - 1;
     * L = pow (2, n), n为正整数;
     * x--双精度实型一维数组，长度为len.开始时存放实序列x(i), 最后存放线性卷积的值
     * y--双精度实型一维数组，长度为n.存放实序列y(i).
     * m--整形变量。序列x(i)的长度。
     * n--整形变量。序列y(i)的长度。
     * len--整形变量。线性卷积的长度。len>= m + n + 1，且len = pow(2, s)
     */

    static void convol(double x[], double y[], int m, int n, int len) {
        int i, len2;
        double t;
        for (i = m; i < len; i++) {
            x[i] = 0.0;
        }

        for (i = n; i < len; i++) {
            y[i] = 0.0;
        }

        rfft(x, len);
        rfft(y, len);

        len2 = len / 2;
        x[0] = x[0] * y[0];
        x[len2] = x[len2] * y[len2];
        for (i = 1; i < len2; i++) {
            t = x[i] * y[i] - x[len - i] * y[len - i];
            x[len - i] = x[i] * y[len - i] + x[len - i] * y[i];
            x[i] = t;
        }
        irfft(x, len);
    }

    /**
     * & 2
     * 长序列的快速卷积
     * 用重叠保留法计算一个长序列和一个短序列的快速卷积。通常用于数字滤波
     * x--双精度一维数组，长度为len。开始时存放长序列x(i), 最后存放线性卷积的值
     * h--双精度一维数组，长度为m。存放短序列h(i).
     * len--整形变量。长序列x(i)的长度。
     * m--整形变量。短序列h(i)的长度。
     * n--整形变量。对长序列x(i)进行分段的长度。一般选取n大于段序列h(i)长度m的两倍以上，
     * 切必须是2的整数次幂，即n = pow(2, s);
     */
    static void convols(double x[], double h[], int len, int m, int n) {
        int i, j, i1, n2, num, nblks;
        double t;
//        double *r, *s;
//        r = (double*)malloc(n * sizeof(double));
//        s = (double*)malloc((n - m + 1) * sizeof(double));
        double[] r = new double[n];
        double[] s = new double[n - m + 1];
        n2 = n / 2;
        num = n - m + 1;
        nblks = (int) (Math.floor((len - n + m) / (double) num) + 1);
        // h[i] 补零
        for (i = m; i < n; i++) {
            h[i] = 0.0;
        }
        // 计算h的fft
        rfft(h, n);

        for (j = 0; j < nblks; j++) {
            if (j == 0) {
                for (i = 0; i < (m - 1); i++) {
                    r[i] = 0.0;
                }
                for (i = (m - 1); i < n; i++) {
                    i1 = i - m + 1;
                    r[i] = x[i1];
                }
            } else {
                for (i = 0; i < n; i++) {
                    i1 = i + j * num - m + 1;
                    r[i] = x[i1];
                }
                for (i = 0; i < num; i++) {
                    i1 = i + (j - 1) * num;
                    x[i1] = s[i];
                }
            }
            rfft(r, n);
            r[0] = r[0] * h[0];
            r[n2] = r[n2] * h[n2];
            for (i = 1; i < n2; i++) {
                t = h[i] * r[i] - h[n - i] * r[n - i];
                r[n - i] = h[i] * r[n - i] + h[n - i] * r[i];
                r[i] = t;
            }
            irfft(r, n);
            for (i = (m - 1); i < n; i++) {
                i1 = i - m + 1;
                s[i1] = r[i];
            }
        }
        for (i = 0; i < num; i++) {
            i1 = i + (j - 1) * num;
            x[i1] = s[i];
        }

        i1 = j * num;
        for (i = i1; i < len; i++) {
            x[i] = 0.0;
        }

//        free(r);
//        free(s);
    }

    /**
     * & 3
     * 特别长序列的快速卷积
     * 用重叠保留发和快速傅里叶变换计算一个特别长序列和一个短序列的线性卷积。
     * 通常用于数组滤波
     * void convold
     */

    /**
     * & 4
     * 快速相关, 用快速傅里叶变换计算两个有限长序列的线性相关
     * x--双精度实型一维数组，长度为m。开始时存放实序列x[i], 最后存放线性相关的值
     * y--双精度实型一维数组，长度为n。开始时存放实序列y[i].
     * m--整形变量。序列x(i)的长度。
     * n--整形变量。序列y(i)的长度。
     * len--整形变量。len >= m + n + 1, 且必须是2的整数次幂，即len = pow (2, s)。
     */
    static void correl(double x[], double y[], int m, int n, int len) {
        int i, len2;
//        double t, *z;
//        z=malloc(len * sizeof(double));
        double t;
        double[] z = new double[len];
//        System.out.println(x.length);
        for (i = m; i < len; i++) {
            x[i] = 0.0;
        }
        for (i = 0; i < (m - 1); i++) {
            z[i] = 0.0;
        }
        for (i = (m - 1); i <= (m + n - 2); i++) {
            z[i] = y[i - m + 1];
        }
        for (i = (m + n - 1); i < len; i++) {
            z[i] = 0.0;
        }
        rfft(x, len);
        rfft(z, len);
        len2 = len / 2;
        x[0] = x[0] * z[0];
        x[len2] = x[len2] * z[len2];
        for (i = 1; i < len2; i++) ;
        {
            t = x[i] * z[i] + x[len - i] * z[len - i];
            x[len - i] = x[i] * z[len - i] - x[len - i] * z[i];
            x[i] = t;
        }
        irfft(x, len);
//        free(z);
    }

}
