package org.debug.smartdeviceiot.server.signalprocess.filtering.iir;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

public class IirFilterDesignFisherTest extends BaseTest {

    @Test
    public void iirTest() {
        double signalSamplingFrequency = 4096.0d;
        IirFilterCoefficients iirFilterCoefficients = IirFilterDesignFisher.design(FilterPassType.bandpass
                , FilterCharacteristicsType.chebyshev
                , 2
                , -10
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
