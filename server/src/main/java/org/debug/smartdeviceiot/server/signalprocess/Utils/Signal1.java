package org.debug.smartdeviceiot.server.signalprocess.Utils;


import java.util.ArrayList;


public class Signal1 {

    private short[] signal;
    private double[] freq;
    private double[] timeSeries;
    private double frameInterval;
    private double sampleRate;
    public int frameSize;
    public int currentPos;
    public ArrayList<double[]> recordedWaveform = new ArrayList<>();
    public boolean recording = false;

    public Signal1(short[] signal, double sampleRate) {
        this.frameSize=signal.length;
        this.signal=new short[frameSize];
        this.freq=new double[frameSize];
        this.timeSeries=new double[frameSize];
        this.signal = signal;
        this.sampleRate = sampleRate;
        setTimeSeries();
        setFreq();
    }

    public void setFreq() {
        for (int i = 0; i < frameSize; i++) {
            this.freq[i]=this.sampleRate*i/frameSize;
        }
    }

    public void setTimeSeries() {
        for (int i = 0; i < frameSize; i++) {
            this.timeSeries[i]=i/this.sampleRate;
        }
    }

    public double[] getTimeSeries() {
        return timeSeries;
    }
    public short[] getSignal() {
        return signal;
    }
    public double[] getFreq() {
        return freq;
    }


    public static void main(String[] args) {
        byte[] bytes = OfflineDataUtil.fileToByteArray("D:\\MyFile\\LaborWork\\智能装备项目开发\\datasets\\LSdata\\启动水平摆放\\" +
                "channel3线圈数据40960short_sTime1607326916819");
        short[] shorts = OfflineDataUtil.extractShortsArray(bytes);
//        for (int i = 0; i < 40; i++) {
//            System.out.println(shorts[i]);
//        }
        Signal1 signal1 = new Signal1(shorts, 40960.0);
        System.out.println("signal1.frameSize:"+signal1.frameSize+"  "+signal1.frameInterval);
        for (int i = 0; i < 500; i++) {
//            System.out.println("signal: "+signal1.signal[i]+" time: "+signal1.timeSeries[i]+" freq: "+signal1.freq[i]);
        }

        int size=signal1.signal.length;
        double[] mag = new double[size];
        double[] xr = new double[size];
        double[] xi = new double[size];
        for (int i = 0; i < xr.length; i++) {
            xr[i]=signal1.signal[i];
        }
//        dspAPI dspAPI = new dspAPI();
        long l = System.currentTimeMillis();
//        dspAPI.FFT(xr,xi,size,1);
//        dspAPI.ft(mag,xr,xi,size,0);
        calcTime(l);



    }

    public static void calcTime(long beginT){
        long endT = System.currentTimeMillis();
        System.out.println(" 此处耗时 = " + (endT - beginT) / 1000.0+"s");
    }



}
