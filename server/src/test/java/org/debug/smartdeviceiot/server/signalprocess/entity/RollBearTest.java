package org.debug.smartdeviceiot.server.signalprocess.entity;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

public class RollBearTest extends BaseTest {

    @Test
    public void testBear(){
        RollBear rollBear = new RollBear(10, 12.7, 70.0, 0.0/180.0*Math.PI, 900.0);
        System.out.println("rollBear.BPFI:\t"+rollBear.getBPFI());
        System.out.println("rollBear.BPFO:\t"+rollBear.getBPFO());
        System.out.println("rollBear.BSF:\t"+rollBear.getBSF());
        System.out.println("rollBear.FTF:\t"+rollBear.getFTF());
    }

}
