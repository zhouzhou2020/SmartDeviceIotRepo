package org.debug.smartdeviceiot.server.signalprocess.filtering.fir.type;

public interface FilterFunction {

    double getValue(final int i);

    int getK(final double samplingFrequency, final double frequency);

}
