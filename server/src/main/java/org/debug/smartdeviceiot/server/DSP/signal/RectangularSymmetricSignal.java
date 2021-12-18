package org.debug.smartdeviceiot.server.DSP.signal;

public class RectangularSymmetricSignal extends ContinuousSignal {

    private final double amplitude;
    private final double term;
    private final double fulfillment;

    public RectangularSymmetricSignal(double rangeStart, double rangeLength, double amplitude,
                                      double term, double fulfillment) {
        super(rangeStart, rangeLength);
        this.amplitude = amplitude;
        this.term = term;
        this.fulfillment = fulfillment;
    }

    @Override
    public double value(double t) {
        if (((t - getRangeStart()) / term)
                - Math.floor((t - getRangeStart()) / term) < fulfillment) {
            return amplitude;
        } else {
            return -amplitude;
        }
    }
}
