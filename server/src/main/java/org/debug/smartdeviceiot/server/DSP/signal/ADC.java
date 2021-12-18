package org.debug.smartdeviceiot.server.DSP.signal;


import com.cb612.dsp.controller.model.signal.*;

public class ADC {

    public DiscreteSignal sampling(ContinuousSignal signal, double sampleRate) {
        return new ContinuousBasedDiscreteSignal(signal.getRangeStart(),
                signal.getRangeLength(), sampleRate, signal);
    }

    public DiscreteSignal roundingQuantization(DiscreteSignal signal, int numberOfLevels) {
        return new QuantizedSignalRounded(signal, numberOfLevels);
    }

    public DiscreteSignal truncatingQuantization(DiscreteSignal signal, int numberOfLevels) {
        return new QuantizedSignalTruncated(signal, numberOfLevels);
    }
}
