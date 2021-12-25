package org.debug.smartdeviceiot.server.signalprocess.jna;

import java.util.Arrays;
import java.util.stream.IntStream;

public class NativeDspUtils {
    public static native double MEAN(double[] data,int fromIndex,int toIndex);
    public static native double VARIANCE(double[] data,int fromIndex,int toIndex);
    public static native double STD(double[] data,int fromIndex,int toIndex);
    public static native double VPP(double[] data,int fromIndex,int toIndex);
    public static native double RMS(double[] data,int fromIndex,int toIndex);
    public static native double VP(double[] data,int fromIndex,int toIndex);
    public static native double Xmean(double[] data,int fromIndex,int toIndex);
    public static native double Xr(double[] data,int fromIndex,int toIndex);
    public static native double WaveParam(double[] data,int fromIndex,int toIndex);
    public static native double PulseParam(double[] data,int fromIndex,int toIndex);
    public static native double YDParam(double[] data,int fromIndex,int toIndex);
    public static native double SKEW(double[] data,int fromIndex,int toIndex);
    public static native double KURTOSIS(double[] data,int fromIndex,int toIndex);

    public static native void DFT(double[] inputR,double[] inputI,double[] outputR,double[] outputI,int size,int notInverse);
    public static native void FFT(double[] dataR,double[] dataI,int size,int notInverse);//2
    public static native void RFFT(double[] data, int size);//2
    public static native void IRFFT(double[] data, int size);//2
    public static native void CZT(double[] dataR,double[] dataI,int inSize,int outSize,double f1,double f2);

    public static native void FHT(double[] data,int size);//2
    public static native void FCT(double[] data,int size);//2
    public static native void IFCT(double[] data,int size);//2
    public static native void FST(double[] data,int size);//2
    public static native void FWT(double[] data,int size,int sign);//2
    public static native void Hilbth(double[] data,int size);//2
    public native void Hilbtf(double[] data,int size);//2

    public static native void convol(double[] x,double[] y,int xSize,int ySize,int outSize);
    public static native void convols(double[] x,double[] y,int xSize,int ySize,int sectionSize);
    public static native void correl(double[] x,double[] y,int xSize,int ySize,int outSize);

    public static native void ft(double[] mag,double[] xr,double[] xi,int Size,int isLog);
    public static native void spectrum(double[] spec, double[] freq, double[] xr, double[] xi, int n, int nfft, double fs, int isLog);
    public static native void rceps(double[] ceps,double[] xr, double[] xi, int n);
    public static native void envelop(double[] enve, double[] x, int n); //这个叫包络线
    public static native void emd(double[] data,int size);
    public static native void emd(String  fname,int size);
    public static native void eemd(double[] data,int size,double Nstd,int NE);
    public static native void eemd(String fname,int size,double Nstd,int NE);

    public static native void pmpse(double[] x,int n,int m,int nfft, int win, double fs,double[] r,double[] freq,double[] sxx,int sdb);
    public static native void cmpse(double[] x, double[] y, int n, int m, int mode, int win, double fs, int lag, int nfft, double[] r, double[] freq, double[] sxy, int sdb);



    public static double correl(double[] x,double[] y){
        try{
            if (x.length!=y.length) {
                throw new Exception("lists need the same length！");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        int n=x.length;
        System.out.println(n);
        double sumX = Arrays.stream(x).sum();
        double sumY = Arrays.stream(y).sum();
        double sumX2 = Arrays.stream(x).map(e -> Math.pow(e, 2)).sum();
        double sumY2 = Arrays.stream(y).map(e -> Math.pow(e, 2)).sum();
        double sumXY = IntStream.range(0, n).mapToDouble(i -> x[i] * y[i]).sum();
        double numerator=sumXY-sumX*sumY/n;
        double denomanator=Math.sqrt((sumX2-Math.pow(sumX,2)/n)*(sumY2-Math.pow(sumY,2)/n));
        if (denomanator==0)
            return 0.0;
        return numerator/denomanator;
    }




    public static void main(String[] args) {
        double[] x=new double[16];
        double[] y=new double[16];
        double[] tx = {-1.0,5.0,2.0,7.0,6.0,9.0,4.0};
        double[] ty = {1.0,2.0,1.0,-3.0,4.0,5.0};
        int i;

        System.arraycopy(tx,0,x,0,7);
        System.arraycopy(ty,0,y,0,6);
        System.out.println("dspUtils().getCorr(x,y) = " + NativeDspUtils.correl(x, y));
    }
}
