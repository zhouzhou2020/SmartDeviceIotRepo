package org.debug.smartdeviceiot.server.signalprocess.templateSignal;

import org.debug.smartdeviceiot.server.BaseTest;
import org.debug.smartdeviceiot.server.signalprocess.filtering.iir.FilterPassType;
import org.debug.smartdeviceiot.server.signalprocess.filtering.iir.IirFilterCoefficients;
import org.debug.smartdeviceiot.server.signalprocess.filtering.iir.IirFilterDesignExstrom;
import org.debug.smartdeviceiot.server.signalprocess.filtering.iir.IirFilterResult;
import org.junit.Test;

public class SignalFileDaoTest extends BaseTest {

    @Test
    public void test(){
        String signalSrcFile=""; //.txt
        String signalDstFile=""; //.txt

        log.info("---------从文件读取一组信号---------");
        SignalFileDao signalFileDao = new SignalFileDao();
        Signal read = signalFileDao.read(signalSrcFile);
        double[] values = read.getValues();

        log.info("---------信号iir滤波---------");
        IirFilterCoefficients iirFilterCoefficients;
        iirFilterCoefficients = IirFilterDesignExstrom.design(FilterPassType.lowpass, 2,
                600.0 / 40960, 600.0 / 40960);
        for (int i=0;i<iirFilterCoefficients.a.length;i++) {
            System.out.println("A["+i+"]:"+iirFilterCoefficients.a[i]);
        }
        for (int i=0;i<iirFilterCoefficients.b.length;i++) {
            System.out.println("B["+i+"]:"+iirFilterCoefficients.b[i]);
        }
        values = IirFilterResult.getIirFilterSignal(values, iirFilterCoefficients.a, iirFilterCoefficients.b);

        log.info("--------------滤波后信号用文件保存-----------------");
        signalFileDao.write(new RealSignal(values,0.0,40960.0),
                signalDstFile);

    }

    //从本地读文件，并写到本地
    @Test
    public void testReadSignal(String fname){
        SignalFileDao signalFileDao = new SignalFileDao();
        Signal signal = signalFileDao.read("D:\\LSdata\\水平摆放叶轮data.txt");
        double[] values = signal.getValues();
        System.out.println("signal.getSamplingFrequency() = " + signal.getSamplingFrequency());
        System.out.println("signal.getStartTime() = " + signal.getStartTime());
        System.out.println("signal.getType() = " + signal.getType());
        System.out.println("signal.getValues().length = " + signal.getValues().length);
        for (int i = 0; i < 10; i++) {
            System.out.println(i+":"+values[i]);
        }
        signalFileDao.write(signal,"D:\\LSdata\\data.txt");
    }
}
