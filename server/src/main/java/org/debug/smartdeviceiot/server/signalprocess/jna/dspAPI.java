package org.debug.smartdeviceiot.server.signalprocess.jna;

public class dspAPI {
    public native double MEAN(double[] data,int fromIndex,int toIndex);
    public native double VARIANCE(double[] data,int fromIndex,int toIndex);
    public native double STD(double[] data,int fromIndex,int toIndex);
    public native double VPP(double[] data,int fromIndex,int toIndex);
    public native double RMS(double[] data,int fromIndex,int toIndex);
    public native double VP(double[] data,int fromIndex,int toIndex);
    public native double Xmean(double[] data,int fromIndex,int toIndex);
    public native double Xr(double[] data,int fromIndex,int toIndex);
    public native double VPParam(double[] data,int fromIndex,int toIndex);
    public native double WaveParam(double[] data,int fromIndex,int toIndex);
    public native double PulseParam(double[] data,int fromIndex,int toIndex);
    public native double YDParam(double[] data,int fromIndex,int toIndex);
    public native double SKEW(double[] data,int fromIndex,int toIndex);
    public native double KURTOSIS(double[] data,int fromIndex,int toIndex);

    public native void DFT(double[] inputR,double[] inputI,double[] outputR,double[] outputI,int size,int notInverse);
    public native void FFT(double[] dataR,double[] dataI,int size,int notInverse);//2
    public native void RFFT(double[] data, int size);//2
    public native void IRFFT(double[] data, int size);//2
    public native void CZT(double[] dataR,double[] dataI,int inSize,int outSize,double f1,double f2);

    public native void FHT(double[] data,int size);//2
    public native void FCT(double[] data,int size);//2
    public native void IFCT(double[] data,int size);//2
    public native void FST(double[] data,int size);//2
    public native void FWT(double[] data,int size,int sign);//2
    public native void Hilbth(double[] data,int size);//2
    public native void Hilbtf(double[] data,int size);//2

    public native void convol(double[] x,double[] y,int xSize,int ySize,int outSize);
    public native void convols(double[] x,double[] y,int xSize,int ySize,int sectionSize);
    public native void correl(double[] x,double[] y,int xSize,int ySize,int outSize);

    public native void ft(double[] mag,double[] xr,double[] xi,int Size,int isLog);
    public native void spectrum(double[] spec, double[] freq, double[] xr, double[] xi, int n, int nfft, double fs, int isLog);
    public native void rceps(double[] ceps,double[] xr, double[] xi, int n);
    public native void envelop(double[] enve, double[] x, int n); //这个叫包络线
    public native void emd(double[] data,int size);
    public native void emd(String  fname,int size);
    public native void eemd(double[] data,int size,double Nstd,int NE);
    public native void eemd(String fname,int size,double Nstd,int NE);

    public native void pmpse(double[] x,int n,int m,int nfft, int win, double fs,double[] r,double[] freq,double[] sxx,int sdb);
    public native void cmpse(double[] x, double[] y, int n, int m, int mode, int win, double fs, int lag, int nfft, double[] r, double[] freq, double[] sxy, int sdb);

    public dspAPI() {
//        System.load("C:\\Users\\LXZ\\source\\repos\\testJna\\x64\\Release\\testJna.dll");
//
        System.loadLibrary("testJna");
    }

}
