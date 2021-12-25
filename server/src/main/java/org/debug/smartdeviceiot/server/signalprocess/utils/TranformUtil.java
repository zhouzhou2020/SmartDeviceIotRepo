package org.debug.smartdeviceiot.server.signalprocess.utils;

import java.util.Arrays;

public class TranformUtil {

    // 变换公式
    //快速哈特莱(Hartley)变换
    public static void fht(int n, double x[]) {
        int i, j, k, m = 0, l1, l2, l3, l4, n1, n2, n4;
        double a, e, c, s, t, t1, t2;
        for (j = 1, i = 1; i < 16; i++) {
            m = i;
            j = 2 * j;
            if (j == n) break;
        }
        n1 = n - 1;
        for (j = 0, i = 0; i < n1; i++) {
            if (i < j) {
                t = x[j];
                x[j] = x[i];
                x[i] = t;
            }
            k = n / 2;
            while (k < (j + 1)) {
                if (k == 0)//这是自己改动的
                {
                    j = 0;
                    break;
                }
                j = j - k;
                k = k / 2;
            }
            j = j + k;
        }

        for (i = 0; i < n; i += 2) {
            t = x[i];
            x[i] = t + x[i + 1];
            x[i + 1] = t - x[i + 1];
        }

        n2 = 1;
        for (k = 2; k <= m; k++) {
            n4 = n2;
            n2 = n4 + n4;
            n1 = n2 + n2;
            e = 6.28318530719586 / n1;
            for (j = 0; j < n; j += n1) {
                l2 = j + n2;
                l3 = j + n4;
                l4 = l2 + n4;
                if (l4 > n - 1)//自定义提示信息
                {
                    System.out.println("出现异常");
                    continue;
                }
                t = x[j];
                x[j] = t + x[l2];
                x[l2] = t - x[l2];
                t = x[l3];
                x[l3] = t + x[l4];
                x[l4] = t - x[l4];
                a = e;
                for (i = 1; i < n4; i++) {
                    l1 = j + i;
                    l2 = j - i + n2;
                    l3 = l1 + n2;
                    l4 = l2 + n2;

                    c = Math.cos(a);
                    s = Math.sin(a);
                    t1 = x[l3] * c + x[l4] * s;
                    t2 = x[l3] * s - x[l4] * c;
                    a = (i + 1) * e;
                    t = x[l1];
                    x[l1] = t + t1;
                    x[l3] = t - t1;
                    t = x[l2];
                    x[l2] = t + t2;
                    x[l4] = t - t2;
                }
            }
        }
    }

    //fht实现hilbert，hilbert变换其实就是将原信号进行90度相移
    public static double[] hilbert(int n, double signal[]) {
        double[] x = Arrays.copyOf(signal, signal.length);
        int i, n1, n2;
        double t;
        n1 = n / 2;
        n2 = n1 + 1;
        fht(n, x);
        for (i = 1; i < n1; i++) {
            t = x[i];
            x[i] = x[n - i];
            x[n - i] = t;
        }

        for (i = n2; i < n; i++) {
            x[i] = -x[i];
        }
        x[0] = 0.0;
        x[n1] = 0.0;
        //ifht与fht差别只是一个1/n，所以用fht实现ifht
        fht(n, x);
        t = 1.0 / n;
        for (i = 0; i < n; i++) {
            x[i] *= t;
        }
        System.out.println("-----------------hilbert-----------");
        return x;
    }

}
