package org.debug.smartdeviceiot.server.signalprocess.filtering.fir.type;

public class BandPassFilterFunction implements FilterFunction {

    @Override
    public double getValue(int i) {
        return 2.0 * Math.sin(Math.PI * i / 2.0);
    }

    @Override
    public int getK(double samplingFrequency, double frequency) {
        return (int) ((4 * samplingFrequency) / (samplingFrequency - 4 * frequency));
        //return (int) (samplingFrequency / frequency);
    }

}
