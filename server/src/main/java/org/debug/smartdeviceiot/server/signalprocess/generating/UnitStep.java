package org.debug.smartdeviceiot.server.signalprocess.generating;


import org.debug.smartdeviceiot.server.signalprocess.TemplateSignal.RealSignal;
import org.debug.smartdeviceiot.server.signalprocess.TemplateSignal.Signal;

public class UnitStep implements Generator {

    private final double stepTime;

    public UnitStep(double stepTime) {
        this.stepTime = stepTime;
    }

    @Override
    public Signal generate(double startTime, double endTime, double amplitude, double samplingFrequency) {
        int n = (int) ((endTime - startTime) * samplingFrequency);
        double samplingPeriod = 1.0 / samplingFrequency;
        double time = startTime;
        double[] values = new double[n];
        for (int i = 0; i < n && time <= endTime; i++) {
            if (time > stepTime) {
                values[i] = amplitude;
            } else if (Math.abs(time - stepTime) < 0.01 * samplingPeriod) {
                values[i] = amplitude / 2.0;
            } else {
                values[i] = 0;
            }

            time += samplingPeriod;
        }
        return new RealSignal(values, startTime, endTime, amplitude, samplingFrequency, false);
    }

}
