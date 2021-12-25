package org.debug.smartdeviceiot.server.signalprocess.filtering.fir;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.debug.smartdeviceiot.server.BaseTest;
import org.debug.smartdeviceiot.server.signalprocess.filtering.fir.window.*;
import org.junit.Test;

import java.util.Scanner;

public class WindowFunctionTest extends BaseTest {

    @Test
    public void test(){
        System.out.println("---------");
        Scanner sc = new Scanner(System.in);
//        String name = (String) "getSelectedType";
        String name = sc.nextLine().trim();
        WindowFunction windowFunction = null;
        switch (name) {
            case "Rectangular":
                windowFunction = new RectangularWindow();
                break;
            case "Hamming":
                windowFunction = new HammingWindow();
                break;
            case "Hanning":
                windowFunction = new HanningWindow();
                break;
            case "Blackman":
                windowFunction = new BlackmanWindow();
                break;
        }
//        return windowFunction;
    }
}
