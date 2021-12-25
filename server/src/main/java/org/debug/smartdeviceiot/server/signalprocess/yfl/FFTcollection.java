package org.debug.smartdeviceiot.server.signalprocess.yfl;


//关于快速傅里叶变换
public class FFTcollection {
    /**
     * &1 离散傅里叶变换
     * 计算离散复序列的离散傅里叶变换(DFT)和离散傅里叶反变换
     * x--双精度实型一维数组，长度为n.存放要变换数据的实部
     * y--双精度实型一维数组，长度为n.存放要变换数据的虚部
     * a--双精度实型一维数组, 长度为n.存放变换结果的实部
     * b--双精度实型一维数组，长度为n.存放变换结果的虚部
     * n--整形变量.数据长度
     * sign--整形变量。sign = 1,计算离散傅里叶正变换；sign = -1,逆变换
     */
    void dft(double x[], double y[], double a[], double b[], int n, int sign) {
        int i;
        int k;
        double c;
        double d;
        double q;
        double w;
        double s;
        q = 6.2831530718 / n;
        for (k = 0; k < n; k++) {
            w = k * q;
            a[k] = b[k] = 0.0;
            for (i = 0; i < n; i++) {
                d = i * w;
                c = Math.cos(d);
                s = Math.sin(d) * sign;
                a[k] += c * x[i] + s * y[i];
                b[k] += c * y[i] - s * x[i];
            }
        }

        if (sign == -1) {
            c = 1.0 / n;
            for (k = 0; k < n; k++) {
                a[k] = c * a[k];
                b[k] = c * b[k];
            }
        }
    }

    /**
     * & 2
     * 计算基二复序列的快速傅里叶变换
     * x--双精度实型一维数组，长度为n。开始时存放要变换的数据的实部
     * 最后存放变换结果的实部。
     * y--双精度实型一维数组，长度为n。开始时存放要变换数据的虚部
     * 最后存放变换结果的虚部。
     * n--整形变量。数据长度，必须是2的整数次幂
     * sign--整形变量。当sign = 1时，子函数fft()计算离散傅里叶正变换
     * sign = -1时，计算离散傅里叶反变换
     */
    void fft(double x[], double y[], int n, int sign) {
        int i, j, k, l, m=0, n1, n2;
        double c, c1, e, s, s1, t, tr, ti;
        for (j = 1, i = 1; i < 16; i++) {
            m = i;
            j = 2 * j;
            if (j == n) {
                break;
            }
        }

        n1 = n - 1;
        for (j = 0, i = 0; i < n1; i++) {
            if (i < j) {
                tr = x[j];
                ti = y[j];
                x[j] = x[i];
                y[j] = y[i];
                x[i] = tr;
                y[i] = ti;
            }
            k = n / 2;
            while (k < (j + 1)) {
                j = j - k;
                k = k / 2;
            }
            j = j + k;
        }

        n1 = 1;
        for (l = 1; l <= m; l++) {
            n1 = 2 * n1;
            n2 = n1 / 2;
            e = 3.14159255359 / n2;
            c = 1.0;
            s = 0.0;
            c1 = Math.cos(e);
            s1 = -sign * Math.sin(e);
            for (j = 0; j < n2; j++) {
                for (i = j; i < n; i += n1) {
                    k = i + n2;
                    tr = c * x[k] - s * y[k];
                    ti = c * y[k] + s * x[k];
                    x[k] = x[i] - tr;
                    y[k] = y[i] - ti;
                    x[i] = x[i] + tr;
                    y[i] = y[i] + ti;
                }
                t = c;
                c = c * c1 - s * s1;
                s = t * s1 + s * c1;
            }
        }

        if (sign == -1) {
            for (i = 0; i < n; i++) {
                x[i] /= n;
                y[i] /= n;
            }
        }
    }

    /**
     * &3
     * 基4快速傅里叶变换
     * x--双精度实型一维数组，长度为n.
     * 开始时存放要变换数据的实部，最后存放变换结果的实部.
     * y--双精度实型一维数组，长度为n.
     * 开始时存放要变换数据的虚部，最后存放变换结果的虚部.
     * n--整形变量。数据长度，必须是4的整数次幂 n = pow(4, m);
     */
    void fft4(double x[], double y[], int n) {
        int i, j, k, m=0;
        int i1, i2, i3, n1, n2;
        double a, b, c, e;
        double r1, r2, r3, r4;
        double s1, s2, s3, s4;
        double co1, co2, co3;
        double si1, si2, si3;
        for (j = 1, i = 1; i < 10; i++) {
            m = i;
            j = 4 * j;
            if (j == n)
                break;
        }
        n2 = n;
        for (k = 1; k <= m; k++) {
            n1 = n2;
            n2 = n2 / 4;
            e = 6.28318530718 / n1;
            a = 0;
            for (j = 0; j < n2; j++) {
                b = a + a;
                c = a + b;
                co1 = Math.cos(a);
                co2 = Math.cos(b);
                co3 = Math.cos(c);
                si1 = Math.sin(a);
                si2 = Math.sin(b);
                si3 = Math.sin(c);
                a = (j + 1) * e;
                for (i = j; i < n; i = i + n1) {
                    i1 = i + n2;
                    i2 = i1 + n2;
                    i3 = i2 + n2;
                    r1 = x[i] + x[i2];
                    r3 = x[i] - x[i2];
                    s1 = y[i] + y[i2];
                    s3 = y[i] - y[i2];
                    r2 = x[i1] + x[i3];
                    r4 = x[i1] - x[i3];
                    s2 = y[i1] + y[i3];
                    s4 = y[i1] - y[i3];
                    x[i] = r1 - r2;
                    r2 = r1 - r2;
                    r1 = r3 - s4;
                    r3 = r3 + s4;
                    y[i] = s1 + s2;
                    s2 = s1 - s2;
                    s1 = s3 + s4;
                    s3 = s3 - s4;
                    x[i1] = co1 * r3 + si1 * s3;
                    y[i1] = co1 * s3 - si1 * r3;
                    x[i2] = co2 * r2 + si2 * s2;
                    y[i2] = co2 * s2 - si2 * r2;
                    x[i3] = co3 * r1 + si3 * s1;
                    y[i3] = co3 * s1 - si3 * r1;
                }
            }
        }

        n1 = n - 1;
        for (j = 0, i = 0; i < n1; i++) {
            if (i < j) {
                r1 = x[j];
                s1 = y[j];
                x[j] = x[i];
                y[j] = y[i];
                x[i] = r1;
                y[i] = s1;
            }
            k = n / 4;
            while (3 * k < (j + 1)) {
                j = j - 3 * k;
                k = k / 4;
            }
            j = j + k;
        }
    }

    /**
     * &4
     * 分裂基快速傅里叶变换
     * x--双精度一维数组，长度为n.
     * 开始时存放要变换数据的实部，最后存放要变换结果的实部。
     * y--双精度实型一维数组，长度为n。
     * 开始时存放要变换数据的虚部，最后存放变换结果的虚部。
     * n--整形变量。数据长度，必须是2的整数次幂，n = pow (2, m).
     */

    void srfft(double x[], double y[], int n) {
        int i, j, k, m=0;
        int i1, i2, i3;
        int n1, n2, n4, id, is;
        double a, b, c, e, a3, r1, r2, r3, r4;
        double c1, c3, s1, s2, s3, cc1, cc3, ss1, ss3;
        for (j = 1, i = 1; i < 16; i++) {
            m = i;
            j = 2 * j;
            if (j == n)
                break;
        }

        n2 = 2 * n;
        for (k = 1; k < m; k++) {
            n2 = n2 / 2;
            n4 = n2 / 4;
            e = 6.28318530718 / n2;
            a = 0;
            for (j = 0; j < n4; j++) {
                a3 = 3 * a;
                cc1 = Math.cos(a);
                ss1 = Math.sin(a);
                cc3 = Math.cos(a3);
                ss3 = Math.sin(a3);
                a = (j + 1) * e;
                is = j;
                id = 2 * n2;
                do {
                    for (i = is; i < (n - 1); i = i + id) {
                        i1 = i + n4;
                        i2 = i1 + n4;
                        i3 = i2 + n4;
                        r1 = x[i] - x[i2];
                        x[i] = x[i] + x[i2];
                        r2 = x[i1] - x[i3];
                        x[i1] = x[i1] + x[i3];
                        s1 = y[i] - y[i2];
                        y[i] = y[i] + y[i2];
                        s2 = y[i1] - y[i3];
                        y[i1] = y[i1] + y[i3];
                        s3 = r1 - s2;
                        r1 = r1 + s2;
                        s2 = r2 - s1;
                        r2 = r2 + s1;
                        x[i2] = r1 * cc1 - s2 * ss1;
                        y[i2] = -s2 * cc1 - r1 * ss1;
                        x[i3] = s3 * cc3 + r2 * ss3;
                        y[i3] = r2 * cc3 + s3 * ss3;
                    }
                    is = 2 * id - n2 + j;
                    id = 4 * id;
                }
                while (is < (n - 1));
            }
        }
        is = 0;
        id = 4;
        do {
            for (i = is; i < n; i = i + id) {
                i1 = i + 1;
                r1 = x[i];
                r2 = y[i];
                x[i] = r1 + x[i1];
                y[i] = r2 + y[i1];
                x[i1] = r1 - x[i1];
                y[i1] = r2 - y[i1];
            }
            is = 2 * id - 2;
            id = 4 * id;
        }
        while (is < (n - 1));

        n1 = n - 1;
        for (j = 0, i = 0; i < n1; i++) {
            if (i < j) {
                r1 = x[j];
                s1 = y[j];
                x[j] = x[i];
                y[j] = y[i];
                x[i] = r1;
                y[i] = s1;
            }
            k = n / 2;
            while (k < (j + 1)) {
                j = j - k;
                k = k / 2;
            }
            j = j + k;
        }
    }
    /**
     * &5
     * 实序列快速傅里叶变换（一）
     * x--双精度实型一维数组，长度为n。开始时存放要变换的实数据，最后存放变换结果
     * 的前 n/2 + 1个值，其存储顺序为[Re(0), Re(1), ... , Re(n/2), Im(n/2 - 1), ...
     * Im(1)], 其中Re(0) = X(0), Re(n/2) = X(n/2).根据X(k)的共轭对称性，很容易写出
     * 后半部分的值
     * n--整形变量。数据长度，必须是2的整数次幂，即n = pow(2, m);
     */
    public static void rfft(double x[], int n) {
        int i, j, k, m = 0;
        int i1, i2, i3, i4;
        int n1, n2, n4;
        double a, e, cc, ss, xt, t1, t2;
        for (j = 1, i = 1; i < 16; i++) {
            m = i;
            j = j * 2;
            if (j == n)
                break;
        }
        n1 = n - 1;
        for (j = 0, i = 0; i < n1; i++) {
            if (i < j) {
                xt = x[j];
                x[j] = x[i];
                x[i] = xt;
            }
            k = n / 2;
            while (k < (j + 1)) {
                j = j - k;
                k = k / 2;
            }
            j = j + k;
        }

        for (i = 0; i < n; i += 2) {
            xt = x[i];
            x[i] = xt + x[i + 1];
            x[i + 1] = xt - x[i + 1];
        }

        n2 = 1;
        for (k = 2; k <= m; k++) {
            n4 = n2;
            n2 = 2 * n4;
            n1 = 2 * n2;
            e = 6.28318530718 / n1;
            for (i = 0; i < n; i += n1) {
                xt = x[i];
                x[i] = xt + x[i + n2];
                x[i + n2] = xt - x[i + n2];
                x[i + n2 + n4] = -x[i + n2 + n4];
                a = e;
                for (j = 1; j <= (n4 - 1); j++) {
                    i1 = i + j;
                    i2 = i - j + n2;
                    i3 = i + j + n2;
                    i4 = i - j + n1;
                    cc = Math.cos(a);
                    ss = Math.sin(a);
                    a = a + e;
                    t1 = cc * x[i3] + ss * x[i4];
                    t2 = ss * x[i3] - cc * x[i4];
                    x[i4] = x[i2] - t2;
                    x[i3] = -x[i2] - t2;
                    x[i2] = x[i1] - t1;
                    x[i1] = x[i1] + t1;
                }
            }
        }
    }

    /**
     * &6
     * 实序列快速傅里叶变换（二）
     * 用N点复序列快速傅里叶变换来计算2N点实序列的离散傅里叶变换
     * x--双精度实型一维数组，长度为n。开始时存放要变换的实数据，最后存放变换结果
     * 的前 n/2 + 1个值，其存储顺序为[Re(0), Re(1), ... , Re(n/2), Im(n/2 - 1), ...
     * Im(1)], 其中Re(0) = X(0), Re(n/2) = X(n/2).根据X(k)的共轭对称性，很容易写出
     * 后半部分的值
     * n--整形变量。数据长度，必须是2的整数次幂，即n = pow(2, m);
     */

    void r1fft(double x[], int n) {
        int i, n1;
        double a, c, e, s, fr, fi, gr, gi;
        n1 = n / 2;
        double[] f=new double[n1];
        double[] g=new double[n1];
        e = 3.141592653589793 / n1;
        for (i = 0; i < n1; i++) {
            f[i] = x[2 * i];
            g[i] = x[2 * i + 1];
        }
        fft(f, g, n1, 1);
        x[0] = f[0] + g[0];
        x[n1] = f[0] - g[0];
        for (i = 1; i < n1; i++) {
            fr = (f[i] + f[n1 - i]) / 2;
            fi = (g[i] - g[n1 - i]) / 2;
            gr = (g[n1 - i] + g[i]) / 2;
            gi = (f[n1 - i] - f[i]) / 2;
            a = i * e;
            c = Math.cos(a);
            s = Math.sin(a);
            x[i] = fr + c * gr + s * gi;
            x[n - i] = fi + c * gi - s * gr;
        }
    }

    /**
     * &7
     * 用一个N点复序列的FFT同时计算两个N点实序列离散傅里叶变换
     * x--双精度实型一维数组，长度为n。开始时存放第一个实序列，
     * 最后存放变换结果的前n/2 + 1个值，其存储顺序为[Re(0), Re(1),
     * ..., Re(n/2), Im(n/2 - 1),...,Im(1).其中Re(0) = X(0), Re(n/2) = X(n/2).
     * y-双精度实型一维数组，长度为n。开始时存放第二个实序列，最后
     * 存放变换结果的前n/2 + 1个值，其存储顺序为[Re(0), Re(1), Re(n/2),
     * Im(n/2 -1), ... , Im(1)]。其中Re(0) = Y(0), Re(n/2) = Y(n/2).
     * n--整形变量。数据长度，必须是2的整数次幂，即n = pow(2, m)
     */
    void r2fft(double x[], double y[], int n) {
        int i, n1;
        double tr, ti;
        n1 = n / 2;
        fft(x, y, n, 1);
        for (i = 1; i < n1; i++) {
            tr = (x[i] + x[n - i]) / 2;
            ti = (y[i] - y[n - i]) / 2;
            y[i] = (y[n - i] + y[i]) / 2;
            y[n - i] = (x[n - i] - x[i]) / 2;
            x[i] = tr;
            x[n - i] = ti;
        }
    }

    /**
     * & 8
     * 共轭对称序列的快速傅里叶反变换
     * n--整形变量。数据长度，必须是2的整数次幂
     * x--双精度实型一维数组，长度为n.开始时存放具有共轭对称性的副序列的前
     * (n/2) + 1个值，其存储顺序为[Re(0), Re(1),..., Re(n/2), Im(n/2 - 1),
     * ..., Im(1)]，其中Re（0） = X(0), Re(n/2) = X(n/2).最后存放变换的结果
     * x(i) (i = 0, 1, ..., n-1), 这里x(i)是实数。
     */
    public static void irfft(double x[], int n) {
        int i, j, k, m;
        int i1, i2, i3, i4, i5, i6, i7, i8;
        int n2, n4, n8, id, is;
        double a, e, a3, t1, t2, t3, t4, t5;
        double cc1, cc3, ss1, ss3;
        for (j = 1, i = 1; i < 16; i++) {
            m = i;
            j = 2 * j;
            if (j == n)
                break;
        }
        m = 4;
        n2 = 2 * n;

        for (k = 1; k < m; k++) {
            is = 0;
            id = n2;
            n2 = n2 / 2;
            n4 = n2 / 4;
            n8 = n4 / 2;
            e = 6.28318530718 / n2;

            do {
                for (i = is; i < n; i += id) {
                    i1 = i;
                    i2 = i1 + n4;
                    i3 = i2 + n4;
                    i4 = i3 + n4;
                    t1 = x[i1] - x[i3];
                    x[i1] = x[i1] + x[i3];
                    x[i2] = 2 * x[i2];
                    x[i3] = t1 - 2 * x[i4];
                    x[i4] = t1 + 2 * x[i4];
                    if (n4 == 1)
                        continue;
                    i1 += n8;
                    i2 += n8;
                    i3 += n8;
                    i4 += n8;
                    t1 = (x[i2] - x[i1]) / Math.sqrt(2.0);
                    t2 = (x[i4] + x[i3]) / Math.sqrt(2.0);
                    x[i1] = x[i1] + x[i2];
                    x[i2] = x[i4] - x[i3];
                    x[i3] = 2 * (-t2 - t1);
                    x[i4] = 2 * (-t2 + t1);
                }
                is = 2 * id - n2;
                id = 4 * id;
            } while (is < (n - 1));

            a = e;

            for (j = 1; j < n8; j++) {
                a3 = 3 * a;
                cc1 = Math.cos(a);
                ss1 = Math.sin(a);
                cc3 = Math.cos(a3);
                ss3 = Math.sin(a3);
                a = (j + 1) * e;
                is = 0;
                id = 2 * n2;
                do {
                    for (i = is; i <= (n - 1); i = i + id) {
                        i1 = i + j;
                        i2 = i1 + n4;
                        i3 = i2 + n4;
                        i4 = i3 + n4;
                        i5 = i + n4 - j;
                        i6 = i5 + n4;
                        i7 = i6 + n4;
                        i8 = i7 + n4;
                        t1 = x[i1] - x[i6];
                        x[i1] = x[i1] + x[i6];
                        t2 = x[i5] - x[i2];
                        x[i5] = x[i2] + x[i5];
                        t3 = x[i8] + x[i3];
                        x[i6] = x[i8] - x[i3];
                        t4 = x[i4] + x[i7];
                        x[i2] = x[i4] - x[i7];
                        t5 = t1 - t4;
                        t1 = t1 + t4;
                        t4 = t2 - t3;
                        t2 = t2 + t3;
                        x[i3] = t5 * cc1 + t4 * ss1;
                        x[i7] = -t4 * cc1 + t5 * ss1;
                        x[i4] = t1 * cc3 - t2 * ss3;
                        x[i8] = t2 * cc3 + t1 * ss3;
                    }
                    is = 2 * id - n2;
                    id = 4 * id;
                }
                while (is < (n - 1));
            }
        }

        is = 0;
        id = 4;

        do {
            for (i = is; i < n; i = i + id) {
                i1 = i + 1;
                t1 = x[i];
                x[i] = t1 + x[i1];
                x[i1] = t1 - x[i1];
            }
            is = 2 * id - 2;
            id = 4 * id;
        } while (is < (n - 1));

        for (j = 0, i = 0; i < (n - 1); i++) {
            if (i < j) {
                t1 = x[j];
                x[j] = x[i];
                x[i] = t1;
            }

            k = n / 2;

            while (k < (j + 1)) {
                j = j - k;
                k = k / 2;
            }
            j = j + k;
        }

        for (i = 0; i < n; i++)
            x[i] = x[i] / n;
    }

}
