package org.debug.smartdeviceiot.server.signalprocess.yfl;

import static java.lang.System.exit;

public class FilterResponse {
    /**
     * & 1
     * 数字滤波器的频率响应
     * 计算数字滤波器的频率响应、幅频响应和相频响应
     * b--双精度实型一维数组，长度为(m+1).存放滤波器分子多项式的系数b(i)
     * a--双精度实型一维数组，长度为(n+1).存放滤波器坟墓多项式的系数a(i)
     * m--整形变量。滤波器分子多项式的阶数
     * n--整形变量。滤波器坟墓多项式的阶数
     * x--双精度实型一维数组，长度为len。
     * 当sign = 0时，存放滤波器频率乡音的实部RE[H(w)]
     * 当sign = 1时，存放滤波器幅频响应|H(w)|;
     * 当sign = 2时，存放用分贝表示的滤波器的幅频响应|H(w)|;
     * y--双精度实型一维数组，长度为len。
     * 当sign = 0时，存放滤波器频率响应的虚部Im[H(w)];
     * 当sign = 1、2时，存放滤波器的相频响应fai(w);
     * len--整形变量。频率响应的长度。
     * sign--整形变量。
     * 当sign = 0时，计算滤波器频率响应的实部Re[H(w)]和虚部Im[H(w)]
     * 当sign = 1时，计算滤波器的幅频响应|H(w)|和相频响应fai(w)
     * 当sign = 2时，计算滤波器的幅频响应|H(w)|(用dB表示)和相频响应fai(w)
     */
    void gain(double b[], double a[], int m, int n, double x[], double y[], int len, int sign) {
        int i, k;
        double ar, ai, br, bi, zr, zi, im, re, den, numr, numi, freq, temp;

        for (k = 0; k < len; k++) {
            freq = k * 0.5 / (len - 1);
            zr = Math.cos(-8.0 * Math.atan(1.0) * freq);
            zi = Math.sin(-8.0 * Math.atan(1.0) * freq);
            br = 0.0;
            bi = 0.0;

            for (i = m; i > 0; i--) {
                re = br;
                im = bi;
                br = (re + b[i]) * zr - im * zi;
                bi = (re + b[i]) * zi + im * zr;
            }

            ar = 0.0;
            ai = 0.0;

            for (i = n; i > 0; i--) {
                re = ar;
                im = ai;
                ar = (re + a[i]) * zr - im * zi;
                ai = (re + a[i]) * zi - im * zr;
            }

            br = br + b[0];
            ar = ar + 1.0;

            numr = ar * br + ai * bi;
            numi = ar * bi - ai * br;
            den = ar * ar + ai * ai;
            x[k] = numr / den;
            y[k] = numi / den;

            switch (sign) {
                case 1:
                    temp = Math.sqrt(x[k] * x[k] + y[k] * y[k]);
                    y[k] = Math.atan2(y[k], x[k]);
                    x[k] = temp;
                    break;
                case 2:
                    temp = x[k] * x[k] + y[k] * y[k];
                    y[k] = Math.atan2(y[k], x[k]);
                    x[k] = 10.0 * Math.log10(temp);
            }
        }
    }

    /**
     * & 2
     * 级联型数字滤波器的频率响应
     * 计算级联型数字滤波器的频率响应、相频响应和相频响应。
     * b--双精度实型2维数组，体积为ns * (n + 1)。存放滤波器分子多项式的系数，
     * b[j][i] 表示第j个n阶节的分子多项式的第i个系数。
     * a--双精度实型2维数组，体积为ns * (n + 1)。存放滤波器分母多项式的系数，
     * a[j][i] 表示第j个n阶节的分子多项式的第i个系数。
     * n--整形变量。级联型滤波器每节的阶数
     * ns--整形变量。机脸型滤波器的n阶节数L。
     * x--双精度实型一维数组，长度为len。
     * 当sign = 0时，存放滤波器频率响应的实部Re[H(W)];
     * 当sign = 1时，存放滤波器幅频响应|H(W)|；
     * 当sign = 2时，存放用分贝表示的幅频响应|H(W)|。
     * y--双精度实型一维数组，长度为len。
     * 当sign = 0时，存放滤波器频率响应的虚部Im[H(W)];
     * 当sign = 1、2时，存放滤波器的相频响应fai(W)。
     * len--整形变量。频率响应的长度。
     * sign--整形变量。
     * 当sign = 0时，计算滤波器频率响应的实部Re[H(W)]和虚部Im[H(w)];
     * 当sign = 1时，计算滤波器的幅频响应|H(W)|和相频响应fai(W);
     * 当sign = 2时，计算滤波器的幅频响应|H(W)|(dB)和相频响应fai(W);
     */

    void gainc(double b[], double a[], int n, int ns, double x[], double y[], int len, int sign) {
        int i, j, k, n1;
        double ar, ai, br, bi, zr, zi, im, re, den, numr, numi, freq, temp;
        double hr, hi, tr, ti;
        n1 = n + 1;

        for (k = 0; k < len; k++) {
            freq = k * 0.5 / (len - 1);
            zr = Math.cos(-8.0 * Math.atan(1.0) * freq);
            zi = Math.sin(-8.0 * Math.atan(1.0) * freq);
            x[k] = 1.0;
            y[k] = 0.0;
            for (j = 0; j < ns; j++) {
                br = 0.0;
                bi = 0.0;
                for (i = n; i > 0; i--) {
                    re = br;
                    im = bi;
                    br = (re + b[j * n1 + i]) * zr - im * zi;
                    bi = (re + b[j * n1 + i]) * zi + im * zr;
                }

                ar = 0.0;
                ai = 0.0;

                for (i = n; i > 0; i--) {
                    re = ar;
                    im = ai;
                    ar = (re + a[j * n1 + i]) * zr - im * zi;
                    ai = (re + a[j * n1 + i]) * zi + im * zr;
                }

                br = br + b[j * n1 + 0];
                ar = ar + 1.0;

                numr = ar * br + ai * bi;
                numi = ar * bi - ai * br;
                den = ar * ar + ai * ai;
                hr = numr / den;
                hi = numi / den;
                tr = x[k] * hr - y[k] * hi;
                ti = x[k] * hi + y[k] * hr;
                x[k] = tr;
                y[k] = ti;
            }

            switch (sign) {
                case 1:
                    temp = Math.sqrt(x[k] * x[k] + y[k] * y[k]);
                    if (temp != 0.0) {
                        y[k] = Math.atan2(y[k], x[k]);
                    } else {
                        y[k] = 0.0;
                    }
                    x[k] = temp;
                    break;
                case 2:
                    temp = x[k] * x[k] + y[k] * y[k];
                    if (temp != 0.0) {
                        y[k] = Math.atan2(y[k], x[k]);
                    } else {
                        temp = 1.0e-40;
                        y[k] = 0.0;
                    }
                    x[k] = 10.0 * Math.log10(temp);
            }
        }
    }

    /**
     * & 3
     * 数字滤波器的时域响应
     * 计算数字滤波器的单位冲激响应和单位阶跃响应
     * x--双精度实型一维数组，长度为lx。存放滤波器的输入序列。
     * y--双精度实型一维数组，长度为ly。存放滤波器的输出序列。
     * lx--整形变量。输入序列的长度。
     * ly--整形变量。输出序列的长度。
     * b--双精度实型一维数组，长度为(m+1)。存放滤波器分子多项式的系数b(i)。
     * a--双精度实型一维数组，长度为(n+1)。存放滤波器坟墓多项式的系数a(i)。
     * m--整形变量。滤波器分子多项式的阶数。
     * n--整形变量。滤波器分母多项式的阶数。
     */

    void resp(double x[], double y[], int lx, int ly, double b[], double a[], int m, int n) {
        int k, i, i1;
        double sum;

        for (k = 0; k < ly; k++) {
            sum = 0.0;
            for (i = 0; i <= m; i++) {
                if ((k - i) > 0) {
                    i1 = ((k - i) < lx) ? (k - i) : (lx - 1);
                    sum = sum + b[i] * x[i1];
                }
            }

            for (i = 1; i <= n; i++) {
                if ((k - i) >= 0) {
                    sum = sum - a[i] * y[k - i];
                }
            }
            y[k] = sum;
        }
    }

    /**
     * & 4
     * 直接型IIR数字滤波(一)
     * 用直接型IIR数字滤波器数字滤波
     * b--双精度实型一维数组，长度为(m+1)。存放滤波器分子多项式的系数b(i)
     * a--双精度实型一维数组，长度为(n+1)。存放滤波器坟墓多项式的系数a(i)
     * m--整形变量。滤波器分子多项式的阶数
     * n--整形变量。滤波器分母多项式的阶数
     * x--双精度实型一维数组，长度为len。开始时存放滤波器的输入序列，最后存放
     * 滤波器的输出序列；在分块处理时，它用于表示当前内的滤波器的输入序列和输出
     * 序列
     * len--整形变量。输入序列与输出序列的长度；在分块处理时，它用于表示快的长度
     * px--双精度实型一维数组，长度为(m+1)，在分块处理时，它用于保存前一块滤波时的
     * m+1个输入序列值
     * py--双精度实型一维数组，长度为(n+1)，它用于保存前一块滤波时的n个输出序列值
     */

    void filter(double b[], double a[], int m, int n, double x[], int len, double px[], double py[]) {
        int k, i;
        for (k = 0; k < len; k++) {
            px[0] = x[k];
            x[k] = 0.0;
            for (i = 0; i <= m; i++) {
                x[k] = x[k] + b[i] * px[i];
            }
            for (i = 1; i <= n; i++) {
                x[k] = x[k] - a[i] * py[i];
            }
            if (Math.abs(x[k]) > 1.0e10) {
            }
            for (i = m; i >= 1; i--) {
                px[i] = px[i - 1];
            }
            for (i = n; i >= 2; i--) {
                py[i] = py[i - 1];
            }
            py[1] = x[k];
        }
    }

    /**
     * & 5
     * 直接型IIR数字滤波(二)
     * 用直接型IIR数字滤波器数字滤波
     * b--双精度实型一维数组，长度为(m+1)。存放滤波器分子多项式的系数b(i)
     * a--双精度实型一维数组，长度为(n+1)。存放滤波器坟墓多项式的系数a(i)
     * m--整形变量。滤波器分子多项式的阶数
     * n--整形变量。滤波器分母多项式的阶数
     * x--双精度实型一维数组，长度为len。存放滤波器的输入序列；在分块处理时，它用于
     * 表示当前块内的滤波器的输入序列
     * y--双精度实型一维数组，长度为len。存放滤波器的输出序列；在分块处理时，它用于
     * 表示当前块内的滤波器的输出序列
     * len--整形变量。输入序列与输出序列的长度；在分块处理时，它用于表示快的长度
     * px--双精度实型一维数组，长度为(m+1)，在分块处理时，它用于保存前一块滤波时的
     * m+1个输入序列值
     * py--双精度实型一维数组，长度为(n+1)，它用于保存前一块滤波时的n个输出序列值
     */
    void filtery(double b[], double a[], int m, int n, double x[], double y[], int len, double px[], double py[]) {
        int k, i;
        double sum;
        for (k = 0; k < len; k++) {
            px[0] = x[k];
            sum = 0.0;
            for (i = 0; i <= m; i++) {
                sum = sum + b[i] * px[i];
            }
            for (i = 1; i <= n; i++) {
                sum = sum - a[i] * py[i];
            }
            if (Math.abs(x[k]) > 1.0e10) {
                System.out.println("this is an unstable filter!");
                exit(0);    /*  是不是搞成  return 更好*/
            }
            for (i = m; i >= 1; i--) {
                px[i] = px[i - 1];
            }
            for (i = n; i >= 2; i--) {
                py[i] = py[i - 1];
            }
            py[1] = sum;
            y[k] = y[k] + sum;
        }
    }

    /**
     * & 6
     * 级联型IIR数字滤波
     * 用级联型IIR数字滤波器进行数字滤波
     * b--双精度实型二维数组，体积为ns * (n + 1)。存放滤波器分子多项式的系数
     * b[j][i] 表示第j个n阶节的分子多项式的第i个系数。
     * a--双精度实型二维数组，体积为ns * (n + 1)。存放滤波器分母多项式的系数。
     * a[j][i] 表述第j个n阶节的分母多项式的第i个系数。
     * n--整形变量。滤波器没节的阶数
     * ns--整形变量，滤波器的n阶阶数：L。
     * x--双精度实型一维数组，长度为len。开始时存放滤波器的序列，最后存放
     * 滤波器的输出序列；在分块处理时，它用于表示当前快内的滤波器的输入序列与输出序列
     * len--整形变量。输入序列与输出序列的长度；在分块处理时，用于表示表示块的长度。
     * px--双精度实型二维数组，体积为ns * (n+1)。在分块处理时，它用于保存前一块滤波时
     * 的(n+1)个输入序列
     * py--双精度实型二维数组，体积为ns * (n+1)。在分块处理时，它用于保存前一块滤波时的
     * n个输出序列值
     */

    void filterc(double b[], double a[], int n, int ns, double x[], int len, double px[], double py[]) {
        int i, j, k, n1;
        n1 = n + 1;
        for (j = 0; j < ns; j++) {
            for (k = 0; k < len; k++) {
                px[j * n1 + 0] = x[k];
                x[k] = b[j * n1 + 0] * px[j * n1 + 0];
                for (i = 1; i <= n; i++) {
                    x[k] += b[j * n1 + 0] * px[j * n1 + 0]
                            - a[j * n1 + i] * py[j * n1 + i];
                    if (Math.abs(x[k]) > 1.0e10) {
                        System.out.println("this is an unstable filter!");
                        exit(0);   /*  是不是搞成  return 更好*/
                    }
                    for (i = n; i >= 2; i--) {
                        px[j * n1 + i] = px[j * n1 + i - 1];
                        py[j * n1 + i] = py[j * n1 + i - 1];
                    }
                    px[j * n1 + 1] = px[j * n1 + 0];
                    py[j * n1 + 1] = x[k];
                }
            }
        }
    }

    /**
     * & 7
     * 并联型IIR数字滤波
     * 用级联型IIR数字滤波器进行数字滤波
     * b--双精度实型二维数组，体积为ns * (n + 1)。存放滤波器分子多项式的系数
     * b[j][i] 表示第j个n阶节的分子多项式的第i个系数。
     * a--双精度实型二维数组，体积为ns * (n + 1)。存放滤波器分母多项式的系数。
     * a[j][i] 表述第j个n阶节的分母多项式的第i个系数。
     * n--整形变量。滤波器每节的阶数
     * ns--整形变量，滤波器的n阶阶数：L。
     * x--双精度实型一维数组，长度为len。存放滤波器的输入序列，
     * 在分块处理时，它用于表示当前块内的滤波器的输入序列
     * y--双精度实型一维数组，长度为len。存放滤波器的输出序列；
     * 在分块处理时，它用于表示当前块内的滤波器的输入序列
     * len--整形变量。输入序列与输出序列的长度；在分块处理时，永不表示块的长度。
     * px--双精度实型二维数组，体积为ns * (n+1)。在分块处理时，它用于保存前一块滤波器
     * 的(n+1)个输入序列
     * py--双精度实型二维数组，体积为ns * (n+1)。在分块处理时，它用于保存前一块滤波时的
     * n个输出序列值
     */

    void filterp(double b[], double a[], int n, int ns, double x[], double y[], int len, double px[], double py[]) {
        int i, j, k, n1;
        double sum;
        n1 = n + 1;
        for (k = 0; k < len; k++) {
            y[k] = 0.0;
        }
        for (j = 0; j < ns; j++) {
            for (k = 0; k < len; k++) {
                px[j * n1 + 0] = x[k];
                sum = b[j * n1 + 0] * px[j * n1 + 0];
                for (i = 1; i <= n; i++) {
                    ///////////////////////
                    sum += b[j * n1 + i] * px[j * n1 + i] - a[j * n1 + i] * py[j * n1 + i];
                }
                if (Math.abs(sum) > 1.0e10) {
                    System.out.println("this is an unstable filter!");
                    exit(0);
                }
                for (i = n; i >= 2; i--) {
                    px[j * n1 + i] = px[j * n1 + i - 1];
                    py[j * n1 + i] = py[j * n1 + i - 1];
                }
                px[j * n1 + 1] = px[j * n1 + 0];
                py[j * n1 + 1] = sum;
                y[k] = y[k] + sum;
            }
        }
    }
}
