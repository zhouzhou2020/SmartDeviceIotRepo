package org.debug.smartdeviceiot.server.DSP.filtering.window;

public class HanningWindow implements WindowFunction {

    @Override
    public double getValue(int i, int M) {
        return 0.5 - 0.5 * Math.cos(2 * Math.PI * i / M);
    }

}
