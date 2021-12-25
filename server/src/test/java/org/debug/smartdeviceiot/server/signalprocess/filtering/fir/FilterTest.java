package org.debug.smartdeviceiot.server.signalprocess.filtering.fir;

import org.debug.smartdeviceiot.server.BaseTest;
import org.debug.smartdeviceiot.server.signalprocess.filtering.fir.type.FilterFunction;
import org.debug.smartdeviceiot.server.signalprocess.filtering.fir.type.HighPassFilterFunction;
import org.debug.smartdeviceiot.server.signalprocess.filtering.fir.window.BlackmanWindow;
import org.debug.smartdeviceiot.server.signalprocess.filtering.fir.window.WindowFunction;
import org.debug.smartdeviceiot.server.signalprocess.templateSignal.RealSignal;
import org.debug.smartdeviceiot.server.signalprocess.templateSignal.Signal;
import org.junit.Test;

import java.util.List;

public class FilterTest extends BaseTest {

    @Test
    public void test(){
        WindowFunction windowFunction=new BlackmanWindow();
        FilterFunction filterType=new HighPassFilterFunction();
        String filterMParameter="";
        String filterFrequency="";
        Filter filter = new Filter(windowFunction,filterType,
                Integer.valueOf(filterMParameter),
                Double.valueOf(filterFrequency));
        double[] values=new double[256];
        double sampleFreq=1024d;
        Signal originSignal = new RealSignal(values,0,sampleFreq);
        List<Signal> apply = filter.apply(originSignal);

        Signal signal = apply.get(0);
        for (int i = 0; i < signal.getValues().length; i++) {
            System.out.println(signal.getValues()[i]);
        }

    }
}
