package org.debug.smartdeviceiot.server.DSP.filtering.type;

import filtering.type.FilterFunction;

public class LowPassFilterFunction implements FilterFunction {

    @Override
    public double getValue(int i) {
        return 1.0;
    }

    @Override
    public int getK(double samplingFrequency, double frequency) {
        return (int) (samplingFrequency / frequency);
    }
}
