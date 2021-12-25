package org.debug.smartdeviceiot.server.signalprocess.utils;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

import static org.debug.smartdeviceiot.server.signalprocess.utils.StaticsUtil.*;


public class StaticsUtilTest extends BaseTest {

    @Test
    public void test(){
        double[] y = new double[1001];
        for (int i = 0; i < 1001; i++) {
            y[i]=Math.sin(i*1.0/100.0);
        }

        System.out.println(MEAN(y,0,1000));
        System.out.println(VARIANCE(y,0,1000));
        System.out.println(STD(y,0,1000));
        System.out.println(VPP(y,0,1000));
        System.out.println(RMS(y,0,1000));
        System.out.println(VP(y,0,1000));
        System.out.println(Xmean(y,0,1000));
        System.out.println(Xr(y,0,1000));
        System.out.println(VPParam(y,0,1000));
        System.out.println(WaveParam(y,0,1000));
        System.out.println(PulseParam(y,0,1000));
        System.out.println(YDParam(y,0,1000));
        System.out.println(SKEW(y,0,1000));
        System.out.println(KURTOSIS(y,0,1000));
    }
}
