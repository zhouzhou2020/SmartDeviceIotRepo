package org.debug.smartdeviceiot.server.DSP.filtering.window;

public class RectangularWindow implements WindowFunction {

    @Override
    public double getValue(int i, int M) {
        return 1.0;
    }

}
