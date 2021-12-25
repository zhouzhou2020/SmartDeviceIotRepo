package org.debug.smartdeviceiot.server.signalprocess.yfl;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

import static org.debug.smartdeviceiot.server.signalprocess.yfl.Convolution.*;

public class ConvolutionTest extends BaseTest {


    @Test
    public void test1() {
        int m, n, len, len1;
        double[] x = new double[16];
        double[] y = new double[16];
        double[] x1 = {-1.0, 5, 2, 7, 6, 9, 4};
        double[] y1 = {1, 2, 1, -3, 4, 5};
        for (int i = 0; i < x1.length; i++) {
            x[i] = x1[i];
        }
        for (int i = 0; i < y1.length; i++) {
            y[i] = y1[i];
        }
        m = 7;
        n = 6;
        len = 16;
        len1 = m + n - 1;
        convol(x, y, m, n, len);
        for (int i = 0; i < len1; i++) {
            System.out.print(String.format("%.3f", x[i]) + " ");
            if (i % 4 == 3)
                System.out.println();
        }
    }

    @Test
    public void test2() {
        int m, n, len;
        double[] h = new double[64];
        double[] x = new double[1024];
        double[] y = new double[1024];
        double[] e = new double[1024];
        n = 64;
        m = 19;
        len = 1024;
        for (int i = 0; i < m; i++) {
            h[i] = 0.0;
        }
        h[1] = -1;
        for (int i = 0; i < len; i++) {
            x[i] = Math.exp(-0.01 * i); //input sequence
        }
        for (int i = 0; i < (len - 1); i++) {
            y[i + 1] = x[i];
        }
        System.out.println("---------x-----------");
        for (int i = 0; i < 64; i++) {
            System.out.print(String.format("%.7f", x[i]) + " ");
            if (i % 4 == 3)
                System.out.println();
        }
        convols(x, h, len, m, n);
        System.out.println("----------y----------");
        for (int i = 0; i < 64; i++) {
            System.out.print(String.format("%.7f", x[i]) + " ");
            if (i % 4 == 3)
                System.out.println();
        }
        for (int i = 0; i < len; i++) {
            e[i] = Math.abs(y[i] + x[i]);
        }
        System.out.println("---------------e------");
        for (int i = 0; i < len; i++) {
            System.out.print(String.format("%.7f", e[i]) + " ");
            if (i % 4 == 3)
                System.out.println();
        }

    }

    @Test
    public void test3() {
        int m, n, m1, len, len1;
        double[] x = new double[16];
        double[] y = new double[16];
        double[] z = new double[16];
        double[] x1 = {-1, 5, 2, 7, 6, 9, 4};
        double[] y1 = {1, 2, 1, -3, 4, 5};
        for (int i = 0; i < x1.length; i++) {
            x[i] = x1[i];
        }
        for (int i = 0; i < y1.length; i++) {
            y[i] = y1[i];
        }
        m = 7;
        n = 6;
        len = 16;
        len1 = m + n - 1;
        m1 = m - 1;
        for (int j = -m1; j < n; j++) {
            z[j + m1] = 0.0;
            for (int i = 0; i < m; i++) {
                if (((i + j) >= 0) && ((i + j) < n))
                    z[j + m1] = z[j + m1] + x[i] * y[i + j];
            }
        }
        System.out.println("-----直接计算互相关------------");
        for (int i = 0; i < len1; i++) {
            System.out.print(String.format("%.3f", z[i]) + " ");
            if (i % 4 == 3)
                System.out.println();
        }
        correl(x, y, m, n, len);
        System.out.println("互相关");
        for (int i = 0; i < len1; i++) {
            System.out.print(x[i] + " ");
            if (i % 4 == 3)
                System.out.println();
        }
        correl(x,x,m,n,len);
        System.out.println("x自相关");
        for (int i = 0; i < len1; i++) {
            System.out.print(String.format("%.3f",x[i])+" ");
            if(i%4==3)
                System.out.println();
        }
    }

}
