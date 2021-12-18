package org.debug.smartdeviceiot.server.DSP.signal;

public class QuantizedSignalRounded extends QuantizedSignal {

    public QuantizedSignalRounded(final DiscreteSignal sourceSignal, final int numberOfLevels) {
        super(sourceSignal, numberOfLevels);
    }

    @Override
    public double rounding(final double x) {
        return Math.round(x);
    }
}
