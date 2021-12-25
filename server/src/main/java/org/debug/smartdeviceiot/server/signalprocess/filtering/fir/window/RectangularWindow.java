package org.debug.smartdeviceiot.server.signalprocess.filtering.fir.window;

public class RectangularWindow implements WindowFunction {

    @Override
    public double getValue(int i, int M) {
        return 1.0;
    }

}
