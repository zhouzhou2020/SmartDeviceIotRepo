package org.debug.smartdeviceiot.server.DSP.signal;

public class QuantizedSignalTruncated extends QuantizedSignal {

    public QuantizedSignalTruncated(final DiscreteSignal sourceSignal, final int numberOfLevels) {
        super(sourceSignal, numberOfLevels);
    }

    @Override
    public double rounding(final double x) {
        return Math.floor(x);
    }
}
