package org.debug.smartdeviceiot.server.signalprocess.filtering.fir;

import org.debug.smartdeviceiot.server.BaseTest;
import org.debug.smartdeviceiot.server.signalprocess.filtering.fir.type.BandPassFilterFunction;
import org.debug.smartdeviceiot.server.signalprocess.filtering.fir.type.FilterFunction;
import org.debug.smartdeviceiot.server.signalprocess.filtering.fir.type.HighPassFilterFunction;
import org.debug.smartdeviceiot.server.signalprocess.filtering.fir.type.LowPassFilterFunction;
import org.junit.Test;

import java.util.Scanner;

public class FilterTypeTest extends BaseTest {

    @Test
    public void test(){
        Scanner sc = new Scanner(System.in);
//        String name = (String) "getSelectedType";
        String name = sc.nextLine().trim();
        FilterFunction filterFunction = null;
        switch (name) {
            case "Low-pass":
                filterFunction = new LowPassFilterFunction();
                break;
            case "Band-pass":
                filterFunction = new BandPassFilterFunction();
                break;
            case "High-pass":
                filterFunction = new HighPassFilterFunction();
                break;
        }

//        return filterFunction;
    }
}
