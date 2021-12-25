package org.debug.smartdeviceiot.server.signalprocess.entity;


import org.debug.smartdeviceiot.server.signalprocess.utils.OfflineDataUtil;

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

}
