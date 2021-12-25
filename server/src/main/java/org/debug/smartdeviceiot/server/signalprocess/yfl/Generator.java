package org.debug.smartdeviceiot.server.signalprocess.yfl;

import java.util.Random;

//常用数字信号的产生
public class Generator {

    private Random random = new Random();


    /**
     * 测试指针转数值的准确性，以及后面方面引用的准确性； 否则直接换一个生成随机数的方法
     */

    // &1.1  均匀分布的随机数: a--区间的下限; b--区间的上限
    //jdk中，Math.random( )为java.lang.Math类中的静态方法,Math类中的random就是调用Random类中的nextDouble实现的
    double uniform(double a, double b) {
        return a + (b - a) * Math.random();
    }

    // &1.2  正态分布的随机数:  mean--正态分布的均值；sigma--正态分布的均方差
    double gauss(double mean, double sigma) {
//        int i;
//        double x, y;
//        for (x = 0, i = 0; i < 12; i++)
//            x += uniform(0, 1);
//        x = x - 6;
//        y = mean + x * sigma;
//        return (y);
        return random.nextGaussian() * sigma + mean;
    }

    // &1.3  指数分布的随机数: beta--指数分布的均值
    double exponent(double beta) {
//        double u, x;
//        u = uniform(0, 1);
//        x = -beta * Math.log(u);
//        return (x);
        return -beta * Math.log(Math.random());
    }

    // &1.4 拉普拉斯分布的随机数: beta--拉普拉斯分布的参数
    double laplace(double beta) {
        double u1, u2;
        double x;
        u1 = uniform(0, 1);
        u2 = uniform(0, 1);
        if (u1 <= 0.5)
            x = -beta * Math.log(1 - u2);
        else
            x = beta * Math.log(u2);
        return (x);
    }

    // &1.5 锐利(Rayleigh)分布的随机数: sigma--瑞利分布的参数
    double rayleigh(double sigma) {
//        double u, x;
//        u = uniform(0, 1, s);
//        x = -2 * log(u);
//        x = sigma * Math.sqrt(x);
//        return (x);
        return sigma * Math.sqrt(-2 * Math.log(Math.random()));
    }

    // &1.6 对数正态分布的随机数：u--对数正态分布的参数；sigma--对数正态分布的参数
    double lonnorm(double u, double sigma) {
//        double x;
//        double y;
//        y = gauss(u, sigma);
//        x = Math.exp(y);
//        return (x);
        return Math.exp(gauss(u, sigma));   //注意书上的sigma参数，没加平方
    }

    // &1.7 柯西(Cauchy)分布的随机数： a--柯西分布的参数； b--柯西分布的参数
    double cauchy(double a, double b) {
//        double u, x;
//        u = uniform(0, 1);
//        x = a - b / Math.tan(Math.PI * u);
//        return (x);
        return a - b / Math.tan(Math.PI * Math.random());
    }

    // &1.8 韦伯(weibull)分布的随机数; a--韦伯分布的参数; b--韦伯分布的参数
    double weibull(double a, double b) {
//        double u, x;
//        u = uniform(0.0, 1.0);
//        u = -log(u);
//        x = b * Math.pow(u, 1.0 / a);
//        return (x);
        return b * Math.pow(-Math.log(Math.random()), 1.0 / a);
    }

    /**
     *
     */
    // &1.9 爱尔朗(Erlang)分布的随机数: m--爱尔朗分布的参数; beta--爱尔朗分布的参数
    double erlang(int m, double beta) {
        int i;
        double u;
        for (u = 0.0, i = 0; i < m; i++)
            u *= uniform(0.0, 1.0);
        return -beta * Math.log(u);
    }

    // &1.10 贝努里(Bernoulli)分布的随机数: p--贝努里分布的参数
    double bn(double p) {
        double u = uniform(0.0, 1.0);
        return (u <= p) ? 1 : 0;
    }

    // &1.11 贝努力-高斯分布的随机数: p--贝努力分布的参数; mean--高斯分布的均值; sigma--高斯分布的方差
    double bg(double p, double mean, double sigma) {
        double u, x = 0.0;
        u = uniform(0.0, 1.0);
        if (u <= p)
            x = gauss(mean, sigma);
        else
            x = 0.0;
        return x;
    }

    // &1.12  二项式分布的随机数: n--二项分布的参数n; p--二项分布的参数p
    int bin(int n, double p) {
        int i;
        double x;
        for (x = 0.0, i = 0; i < n; i++) {
            x += bn(p);
        }
        return (int) x;
    }

    // &1.13 泊松(Poisson)分布的随机数: lambda--泊松分布的均值
    int poisson(double lambda) {
        int i = 0;
        double a = Math.exp(-lambda);
        double b = 1.0;
        double u;
        do {
            u = uniform(0.0, 1.0);
            b *= u;
            i++;
        }
        while (b >= a);
        return i - 1;
    }

    /**
     * &1.14 ARMA(p, q)模型数据的产生
     * a--双精度实型一组数组，长度为(p+1)。ARMA(p, q)模型的自回归系数。
     * b--双精度实型一组数组，长度为(q+1)。ARMA(p, q)模型的自回归系数。
     * p--整形变量，ARMA(p, q)模型的自回归阶数
     * q--整形变量，ARMA(P, q)模型的滑动平均阶数
     * mean--双精度实型变量。 产生白噪声所用的正态分布的均值
     * sigma--双精度实型变量。产生白噪声所用的正太分布的均方差
     * seed--长整形指针变量。 *seed为随机数种子
     * x--双精度一组数组，长度为n。存放ARMA(p, q)模型的数据
     * n--整形变量。ARMA(p, q)模型数据的长度
     */
    void arma(double a[], double b[], int p, int q, double mean,
              double sigma, double x[], int n) {
        int i;
        int k;
        int m;
        double s;
        double[] w = new double[n];
        for (k = 0; k <= n; k++) {
            w[k] = gauss(mean, sigma);
        }
        x[0] = b[0] * w[0];
        for (k = 1; k <= p; k++) {
            s = 0.0;
            for (i = 1; i <= k; i++) {
                s += a[i] * x[k - i];
            }
            s = b[0] * w[k] - s;
            if (q == 0) {
                x[k] = s;
                continue;
            }
            m = (k > q) ? q : k;
            for (i = 1; i <= m; i++) {
                s += b[i] * w[k - i];
            }
            x[k] = s;
        }

        for (k = p + 1; k < n; k++) {
            s = 0.0;
            for (i = 1; i <= p; i++) {
                s += a[i] * x[k - i];
            }
            s = b[0] * w[k] - s;
            if (q == 0) {
                x[k] = s;
                continue;
            }
            for (i = 1; i <= q; i++) {
                s += b[i] * w[k - i];
            }
            x[k] = s;
        }
    }

    /**
     * &1.15
     * 含有高斯白噪声的正弦信号的产生
     * a--双精度一组数组，长度为m。各正弦信号的振幅
     * f--双精度一维数组，长度为m。各正弦信号的频率
     * ph--双精度一维数组，长度为m。各正弦信号的相位
     * m--整形变量，正弦信号的个数
     * fs--双精度实型变量，采样频率(HZ)
     * snr--双精度实型变量，信噪比(dB)
     * seed--长整形变量。随机数种子
     * x--双精度实型一维数组。长度为n。存放所产生的数据
     * n--整形变量，数据长度
     */
    void sinwn(double a[], double f[], double ph[], int m,
               double fs, double snr, double x[], int n) {
        int i;
        int k;
        double z;
        double nsr;
        z = snr / 10.0;
        z = Math.pow(10.0, z);
        z = 1.0 / (2 * z);
        nsr = Math.sqrt(z);
        for (i = 0; i < m; i++) {
            f[i] = 2 * Math.PI * f[i] / fs;
            ph[i] = ph[i] * Math.PI / 180.0;
        }
        for (k = 0; k < n; k++) {
            x[k] = 0.0;
            for (i = 0; i < m; i++) {
                x[k] = x[k] + a[i] * Math.sin(k * f[i] + ph[i]);
            }
            x[k] = x[k] + nsr * gauss(0.0, 1.0);
        }
    }


}
