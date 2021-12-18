package org.debug.smartdeviceiot.server.DSP.filtering.type;

public interface FilterFunction {

    double getValue(final int i);

    int getK(final double samplingFrequency, final double frequency);

}
