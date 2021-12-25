package org.debug.smartdeviceiot.server.signalprocess.yfl;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

public class GeneratorTest extends BaseTest {

    private Generator gnt = new Generator();

//1.均匀分布的随机数
    @Test
    public void test1() {
        double a, b, x;
        long s;
        a = 0;
        b = 1;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                x = gnt.gauss(a, b);
//                x = uniform(a, b);
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }


}
