package org.debug.smartdeviceiot.server.signalprocess.freqDomain;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;
import org.apache.commons.math3.util.MathArrays;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class dspUtils {

    private static final FastFourierTransformer transformer;
    private static final double LOG_OF_2;


    public static final int nextPow2(int n) {
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

    public static Complex[] getSpectrum(double[] wf) {
        final int fftSize = 1 << nextPow2(wf.length);
        final int fftSize2 = (fftSize >> 1) + 1;

        final double[] src = Arrays.stream(Arrays.copyOf(wf, fftSize))
                .map(w -> w / wf.length)
                .toArray();

        return rfft(src);
    }

    public static double[] getWaveformFromSpectrum(Complex[] spectrum) {
        return irfft(spectrum);
    }

    public static double[] getMagSpectrum(double[] wf) {
        return Arrays.stream(getSpectrum(wf))
                .mapToDouble(c -> c.abs())
                .toArray();
    }

    public static double[] getLogSpectrum(double[] wf) {
        return Arrays.stream(getSpectrum(wf))
                .mapToDouble(c -> Math.log10(c.abs()))
                .toArray();
    }

    /**
     * Calculate cepstrum series from waveform
     * @param wf: waveform
     * @return cepstrum
     */
    public static double[] cepstrumCoefficient(double[] wf, int lifter) {
        final int fftSize = 1 << nextPow2(wf.length);
        final int fftSize2 = (fftSize >> 1) + 1;

        final double[] src = Arrays.stream(Arrays.copyOf(wf, fftSize))
                .map(w -> w / wf.length)
                .toArray();

        final Complex[] spectrum = rfft(src);

        double[] specLog = Arrays.stream(spectrum)
                .mapToDouble(c -> Math.log10(c.abs()))
                .toArray();

        Complex[] cepstrum = rfft(Arrays.copyOf(specLog, specLog.length - 1));
        //range() 左闭右开
        return IntStream.range(0, lifter).mapToDouble(i -> cepstrum[i].abs()).toArray();
    }

    public static double[] rceps(double[] input) {
        double[] ans = new double[input.length];
        Complex[] iLog = new FastFourierTransformer(DftNormalization.STANDARD).transform(getLogSpectrum(input), TransformType.INVERSE);
        for (int i = 0; i < iLog.length; i++) {
            ans[i] = iLog[i].getReal();
            System.out.println("No." + i + ":" + ans[i]);
        }
        System.out.println(ans.length);
        return ans;
    }

    public static double[] envelope(double[] signal) {
        double[] abs = new double[signal.length];
        double[] hb = hilbert(signal.length, signal);
        for (int i = 0; i < signal.length; i++) {
            //    System.out.println(signal[i]+","+hb[i]);
            abs[i] = Math.hypot(signal[i], hb[i]);
        }
        return abs;
    }


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
        for (int i1 = 0; i1 < x.length; i1++) {
            System.out.println("No." + i1 + ":" + x[i1]);
        }
        return x;
    }

    public static void testStat(double[] input) {
//        double[] input = new double[] { 0.33, 1.33,0.27333, 0.3, 0.501,
//                0.444, 0.44, 0.34496, 0.33,0.3, 0.292, 0.667 };
//        double[] input = new double[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32};
        System.out.println("平均数：" + StatUtils.mean(input));
        System.out.println("峭度：" + new Kurtosis().evaluate(input));
        System.out.println("偏度：" + new Skewness().evaluate(input));
        System.out.println("总和：" + StatUtils.sum(input));
        System.out.println("最大值：" + StatUtils.max(input));
        System.out.println("最小值：" + StatUtils.min(input));
        System.out.println("方差：" + StatUtils.variance(input));
        System.out.println("标准差：" + new StandardDeviation().evaluate(input));
        System.out.println("中位数：" + new Median().evaluate(input));
        System.out.println("众数：" + StatUtils.mode(input));//返回一个数组
        System.out.println("几何平均数：" + StatUtils.geometricMean(input));//n个正数的连乘积的n次算术根
        //   System.out.println("平均概率偏差（平均差）："+StatUtils.meanDifference(input,input2));
        //   System.out.println("一组数据的方差差异性为：" + StatUtils.varianceDifference(values,values2,StatUtils.meanDifference(values, values2)));
        //   System.out.println("两样本数据的和差为：" + StatUtils.sumDifference(values,values2));
        double[] norm = StatUtils.normalize(input);
        System.out.println("第3个数标准化结果：" + norm[3]);
        System.out.println("累乘:" + StatUtils.product(input));
        System.out.println("从小到大排序后位于80%位置的数：" + StatUtils.percentile(input, 80));
        System.out.println("总体方差:" + StatUtils.populationVariance(input));
        System.out.println("一组数据对数求和:" + StatUtils.sumLog(input));
        System.out.println("一组数据平方和：" + StatUtils.sumSq(input));
    }

    public static double[] readInput(String fileName) {
        ArrayList a1 = new ArrayList<Double>();
        InputStream fin = null;
        try {
            fin = new FileInputStream(new File(fileName));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        Scanner s = new Scanner(fin);
        while (s.hasNext())
            a1.add(Double.parseDouble(s.nextLine()));
        int signalLen = a1.size();
        double[] input = new double[signalLen];
        for (int i = 0; i < input.length; i++) {
            // input[i]=1000*(Math.random()-0.5);
            input[i] = (Double) a1.get(i);
        }
        return input;
    }

    public static byte[] fileToByteArray(String filePath) {

        File src = new File(filePath);//获得文件的源头，从哪开始传入(源)
        byte[] dest = null;//最后在内存中形成的字节数组(目的地)
        //选择流
        InputStream is = null;
        ByteArrayOutputStream baos= null;
        try {
            is = new FileInputStream(src);
            baos = new ByteArrayOutputStream();
            byte[] flush = new byte[1024*10];
            int len = -1;
            while((len = is.read(flush)) != -1) {
                baos.write(flush,0,len);
            }
            baos.flush();
            dest = baos.toByteArray();
            return dest;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public static short[] extractShortsArray(byte []bytes) {
        int len = bytes.length;
        short[] shorts = new short[len >> 1 + (len & 1)];
        for (int i = 1; i < len; i += 2) {
            byte h = bytes[i-1];
            int l = bytes[i]& 0xff;
            shorts[i >> 1] = (short) (h << 8 | l);
        }
        return shorts;
    }

    public static void bytes2file(String srcFname, String destifName) {
        FileWriter osw=null;
        try {
            osw = new FileWriter(destifName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //读文件到字节数组
        byte[] datas = fileToByteArray(srcFname);
        System.out.println("---------转short[]----------");
        short[] shorts = extractShortsArray(datas);

        //打印到文件
        try{
            for (int i = 0; i < shorts.length-1; i++) {
                osw.write(String.valueOf(shorts[i])+"\n");
                osw.flush();
            }
            osw.write(String.valueOf(shorts[shorts.length-1]));
            osw.flush();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                osw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("data保存完成");
    }

    public static void byteArrayToFile(byte[] src,String filePath) {
        File dest = new File(filePath);//目的地，新文件
        InputStream is = null;//ByteArrayInputStream的父类
        OutputStream os = null;
        //操作
        try {
            is = new ByteArrayInputStream(src);
            os = new FileOutputStream(dest);

            byte[] flush = new byte[1024*10];
            int len = -1;
            while((len = is.read(flush)) != -1) {
                os.write(flush,0,len);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != os) {//关闭文件流
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

    public static final Complex[] fft(Complex[] src) {
        if (Integer.bitCount(src.length) != 1) {
            throw new IllegalArgumentException("src.length must be power of 2");
        } else {
            return transformer.transform(src, TransformType.FORWARD);
        }
    }

    public static final Complex[] fft(double[] src) {
        if (Integer.bitCount(src.length) != 1) {
            throw new IllegalArgumentException("src.length must be power of 2");
        } else {
            return transformer.transform(src, TransformType.FORWARD);
        }
    }

    public static final Complex[] ifft(Complex[] src) {
        if (Integer.bitCount(src.length) != 1) {
            throw new IllegalArgumentException("src.length must be power of 2");
        } else {
            return transformer.transform(src, TransformType.INVERSE);
        }
    }

    public static final Complex[] ifft(double[] src) {
        if (Integer.bitCount(src.length) != 1) {
            throw new IllegalArgumentException("src.length must be power of 2");
        } else {
            return transformer.transform(src, TransformType.INVERSE);
        }
    }

    public static final Complex[] rfft(double[] src) {
        if (Integer.bitCount(src.length) != 1) {
            throw new IllegalArgumentException("src.length must be power of 2");
        } else {
            return (Complex[])Arrays.copyOf(transformer.transform(src, TransformType.FORWARD), (src.length >> 1) + 1);
        }
    }

    public static final double[] irfft(Complex[] src) {
        if (Integer.bitCount(src.length - 1) != 1) {
            throw new IllegalArgumentException("src.length must be 2^n + 1");
        } else {
            int fftSize = src.length - 1 << 1;
            Complex[] src0 = (Complex[])Arrays.copyOf(src, fftSize);
            Arrays.fill(src0, src.length, fftSize, Complex.ZERO);
            return Arrays.stream(transformer.transform(src0, TransformType.INVERSE)).mapToDouble((c) -> {
                return c.getReal();
            }).toArray();
        }
    }

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

    public static final double nn2hz(double nn) {
        return 440.0D * Math.pow(2.0D, (nn - 69.0D) / 12.0D);
    }

    public static final double hz2nn(double hz) {
        return log2(hz / 440.0D) * 12.0D + 69.0D;
    }

    public static final double log2(double x) {
        return Math.log(x) / LOG_OF_2;
    }



    public static final int argmax(double[] a) {
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

    public static final ScheduledExecutorService newSingleDaemonThreadScheduledExecutor() {
        return Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public final Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });
    }

    static {
        transformer = new FastFourierTransformer(DftNormalization.STANDARD);
        LOG_OF_2 = Math.log(2.0D);
    }


    //  datar[] datai[] 输入序列的实部和虚部； n--输入数据长度；
    //  m--输出数据长度；  f1，f2：细化频段起止点
    public static void Czt(double[] datar,double[] datai,int n,int m,double f1,double f2){
        double e,t,ar,ai,ph,tr,ti;
        int len=n+m-1;
        int n1,n2,i,j;
        for(j=1,i=1;i<16;i++){
            j=2*j;
            if(j>=len)
            {
                len=j;
                break;
            }
        }
//        do
//        {
//            j *= 2;
//        } while (j < len);
//        len = j;
        double[] xr=new double[len];
        double[] xi=new double[len];
        double[] wr=new double[len];
        double[] wr1=new double[len];
        double[] wi=new double[len];
        double[] wi1=new double[len];
        ph=2.0*Math.PI*(f2-f1)/(m-1);
        n1=(n>=m)?n:m;
        for (int i1 = 0; i1 < n1; i1++) {
            e=ph*i1*i1/2.0;
            wr[i1]=Math.cos(e);
            wi[i1]=Math.sin(e);
            wr1[i1]=wr[i1];
            wi1[i1]=-wi[i1];
        }
        n2=len-n+1;
        for (int i1 = m; i1 < n2; i1++) {
            wr[i1]=0.0;
            wi[i1]=0.0;
        }
        for (int i1 = n2; i1 < len; i1++) {
            j=len-i1;
            wr[i1]=wr[j];
            wi[i1]=wi[j];
        }
        Complex[] in1 = new Complex[len];
        for (int i1 = 0; i1 < in1.length; i1++) {
            in1[i1] = new Complex(wr[i1], wi[i1]);
        }
        Complex[] res = new FastFourierTransformer(DftNormalization.STANDARD).
                transform(in1,TransformType.FORWARD);
        ph=-2.0*Math.PI*f1;
        for (int i1 = 0; i1 < n; i1++) {
            e=ph*i1;
            ar=Math.cos(e);
            ai=Math.sin(e);
            tr=ar*wr1[i1]-ai*wi1[i1];
            ti=ai*wr1[i1]+ar*wi1[i1];
            t=datar[i1]*tr-datai[i1]*ti;
            xi[i1]=datar[i1]*ti+datai[i1]*tr;
            xr[i1]=t;
        }
        for (int i1 = n; i1 < len; i1++) {
            xr[i1]=0.0;
            xi[i1]=0.0;
        }
        Complex[] in2 = new Complex[len];
        for (int i2 = 0; i2 < in2.length; i2++) {
            in2[i2] = new Complex(xr[i2], xi[i2]);
        }
        Complex[] res2 = new FastFourierTransformer(DftNormalization.STANDARD).
                transform(in2, TransformType.FORWARD);
        for (int i1 = 0; i1 < len; i1++) {
            tr=res2[i1].getReal()*res[i1].getReal()-res2[i1].getImaginary()*res[i1].getImaginary();
            xi[i1]=res2[i1].getReal()*res[i1].getImaginary()+res2[i1].getImaginary()*res[i1].getReal();
            xr[i1]=tr;
        }
        Complex[] in3 = new Complex[len];
        for (int i3 = 0; i3 < in3.length; i3++) {
            in3[i3] = new Complex(xr[i3], xi[i3]);
        }
        Complex[] res3 = new FastFourierTransformer(DftNormalization.STANDARD).
                transform(in3, TransformType.INVERSE);
        for (int i1 = 0; i1 < m; i1++) {
            tr=res3[i1].getReal()*wr1[i1]-res3[i1].getImaginary()*wi1[i1];
            xi[i1]=res3[i1].getReal()*wi1[i1]+res3[i1].getImaginary()*wr1[i1];
            xr[i1]=tr;
            System.out.println("最终结果"+xr[i1]+","+xi[i1]);
        }
        //幅值和频率：
        double[] mag=new double[m];
        double[] f=new double[m];
        for (int i1 = 0; i1 < m; i1++) {
            mag[i1]=Math.hypot(xr[i1],xi[i1]);
            f[i1]=f1+i1*(f2-f1)/(m-1);
        }
    }
}
