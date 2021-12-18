package org.debug.smartdeviceiot.server.DSP.filtering.window;

public class HammingWindow implements WindowFunction {

    @Override
    public double getValue(int i, int M) {
        return 0.53836 - 0.46164 * Math.cos(2 * Math.PI * i / M);
    }

}
