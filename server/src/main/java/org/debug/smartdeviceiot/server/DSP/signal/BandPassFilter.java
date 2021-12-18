package org.debug.smartdeviceiot.server.DSP.signal;


import com.cb612.dsp.controller.model.window.Window;

public class BandPassFilter extends LowPassFilter {

    public BandPassFilter(double sampleRate, int M, double fo, Window window) {
        super(sampleRate, M, fo, window);
    }

    @Override
    public double value(int n) {
        return super.value(n) * 2.0 * Math.sin(Math.PI * n / 2.0);
    }
}
