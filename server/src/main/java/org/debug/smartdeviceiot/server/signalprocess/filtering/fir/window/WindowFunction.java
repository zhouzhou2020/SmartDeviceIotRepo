package org.debug.smartdeviceiot.server.signalprocess.filtering.fir.window;

public interface WindowFunction {

    double getValue(final int i, final int M);

}
