package org.debug.smartdeviceiot.server.signalprocess.entity;

import org.debug.smartdeviceiot.server.BaseTest;
import org.debug.smartdeviceiot.server.signalprocess.utils.OfflineDataUtil;
import org.junit.Test;

public class Signal1Test extends BaseTest {

    @Test
    public void test(){
        byte[] bytes = OfflineDataUtil.fileToByteArray("D:\\MyFile\\LaborWork\\智能装备项目开发\\datasets\\LSdata\\启动水平摆放\\" +
                "channel3线圈数据40960short_sTime1607326916819");
        short[] shorts = OfflineDataUtil.extractShortsArray(bytes);
//        for (int i = 0; i < 40; i++) {
//            System.out.println(shorts[i]);
//        }
        Signal1 signal1 = new Signal1(shorts, 40960.0);
        System.out.println("signal1.frameSize:"+signal1.frameSize);
        int size=signal1.getSignal().length;


    }



}
