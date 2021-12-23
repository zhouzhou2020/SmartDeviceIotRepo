//package org.debug.smartdeviceiot.server.DSP.signal;
//
//
//import com.cb612.dsp.controller.model.window.Window;
//
//public class HighPassFilter extends LowPassFilter {
//
//    public HighPassFilter(double sampleRate, int M, double fo, Window window) {
//        super(sampleRate, M, fo, window);
//    }
//
//    @Override
//    public double value(int n) {
//        return super.value(n) * ((n & 0x01) == 1 ? -1.0 : 1.0);
//    }
//}
