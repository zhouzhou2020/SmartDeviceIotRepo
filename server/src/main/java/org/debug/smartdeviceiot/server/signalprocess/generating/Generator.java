package org.debug.smartdeviceiot.server.signalprocess.generating;


import org.debug.smartdeviceiot.server.signalprocess.templateSignal.Signal;

public interface Generator {

    Signal generate(final double startTime, final double endTime, final double amplitude, final double samplingFrequency);

    //ComplexSignal generateComplex();

}