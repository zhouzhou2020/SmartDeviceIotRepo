package org.debug.smartdeviceiot.server.signalprocess.generating;


import org.debug.smartdeviceiot.server.signalprocess.templateSignal.RealSignal;
import org.debug.smartdeviceiot.server.signalprocess.templateSignal.Signal;

public class TriangleWave implements Generator {

    private final double dutyCycle;
    private final double period;

    public TriangleWave(double dutyCycle, double period) {
        this.dutyCycle = dutyCycle;
        this.period = period;
    }

    @Override
    public Signal generate(final double startTime, final double endTime, final double amplitude, final double samplingFrequency) {
        int n = (int) ((endTime - startTime) * samplingFrequency);
        double samplingPeriod = 1.0 / samplingFrequency;
        double time = startTime;
        double[] values = new double[n];
        for (int i = 0; i < n && time <= endTime; i++) {
            double offset = (int) (time / period) * 1.0 * period;
            if (time - offset >= 0 && time - offset <= dutyCycle * period) {
                values[i] = amplitude / (dutyCycle * period) * (time - offset);
            } else if (time - offset >= dutyCycle * period && time - offset <= period) {
                values[i] = -amplitude / (period * (1.0 - dutyCycle)) * (time - offset) + amplitude / (1.0 - dutyCycle);
            }
            time += samplingPeriod;
        }
        return new RealSignal(values, startTime, endTime, amplitude, samplingFrequency, false);
    }

}
