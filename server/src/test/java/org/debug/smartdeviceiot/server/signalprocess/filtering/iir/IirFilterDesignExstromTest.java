package org.debug.smartdeviceiot.server.signalprocess.filtering.iir;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

public class IirFilterDesignExstromTest extends BaseTest {

    @Test
    public void iirTest() {
        double signalSamplingFrequency = 4096.0d;
        IirFilterCoefficients iirFilterCoefficients = IirFilterDesignExstrom.design(FilterPassType.bandpass
                , 2
                , 500 / signalSamplingFrequency
                , 1500 / signalSamplingFrequency
        );

        for (int i = 0; i < iirFilterCoefficients.a.length; i++) {
            System.out.println("A[" + i + "]:" + iirFilterCoefficients.a[i]);
        }
        for (int i = 0; i < iirFilterCoefficients.b.length; i++) {
            System.out.println("B[" + i + "]:" + iirFilterCoefficients.b[i]);
        }
    }
}
