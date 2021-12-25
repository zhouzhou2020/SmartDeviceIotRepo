package org.debug.smartdeviceiot.server.signalprocess.filtering.iir;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

import java.util.Scanner;

public class FilterPassTypeTest extends BaseTest {

    @Test
    public void test(){
        Scanner sc = new Scanner(System.in);
//        String name = (String) "getSelectedType";
        String name = sc.nextLine().trim();
        FilterPassType filterFunction = null;
        switch (name) {
            case "lowpass":
                filterFunction = FilterPassType.lowpass;
                break;
            case "highpass":
                filterFunction = FilterPassType.highpass;
                break;
            case "bandpass":
                filterFunction = FilterPassType.bandpass;
                break;
            case "bandstop":
                filterFunction = FilterPassType.bandstop;
                break;
        }
//        return filterFunction;
    }

}
