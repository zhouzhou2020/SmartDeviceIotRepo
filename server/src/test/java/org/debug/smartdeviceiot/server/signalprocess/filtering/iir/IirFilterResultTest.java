package org.debug.smartdeviceiot.server.signalprocess.filtering.iir;

import org.debug.smartdeviceiot.server.BaseTest;
import org.debug.smartdeviceiot.server.signalprocess.filtering.iir.*;
import org.debug.smartdeviceiot.server.signalprocess.templateSignal.RealSignal;
import org.debug.smartdeviceiot.server.signalprocess.templateSignal.SignalFileDao;
import org.junit.Test;


public class IirFilterResultTest extends BaseTest {


    @Test
    public void iirTest(){
        double signalSamplingFrequency=4096.0d;
        // generate filter params
        IirFilterCoefficients iirFilterCoefficients = IirFilterDesignFisher.design(FilterPassType.bandpass
                , FilterCharacteristicsType.chebyshev
                , 2
                , -10
                , 500/signalSamplingFrequency
                , 1500/signalSamplingFrequency
        );
        /**
         * 两种生成滤波器参数的方式不一样
         * */
//        IirFilterCoefficients iirFilterCoefficients1 = IirFilterDesignExstrom.design(FilterPassType.bandpass
//                , 2
//                , 500/signalSamplingFrequency
//                , 1500/signalSamplingFrequency
//        );
        for (int i=0;i<iirFilterCoefficients.a.length;i++) {
            System.out.println("A["+i+"]:"+iirFilterCoefficients.a[i]);
        }
        for (int i=0;i<iirFilterCoefficients.b.length;i++) {
            System.out.println("B["+i+"]:"+iirFilterCoefficients.b[i]);
        }
        double[] signalBeforeFilter=new double[2048];
        for(int i=0;i<signalBeforeFilter.length;i++){
            signalBeforeFilter[i]=Math.round(32767);
        }
        // target signal sequence
        double[] signalAfterFilter = IirFilterResult
                .getIirFilterSignal(signalBeforeFilter, iirFilterCoefficients.a, iirFilterCoefficients.b);
        // target signal
        RealSignal targetSignal = new RealSignal(signalAfterFilter, System.currentTimeMillis(), signalSamplingFrequency);

    }

    @Test
    public void iirTest2(){

    }

}
