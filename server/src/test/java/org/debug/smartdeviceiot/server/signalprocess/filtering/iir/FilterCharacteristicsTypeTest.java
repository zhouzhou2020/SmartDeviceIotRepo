package org.debug.smartdeviceiot.server.signalprocess.filtering.iir;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

import java.util.Scanner;

public class FilterCharacteristicsTypeTest extends BaseTest {

    @Test
    public void test(){
        Scanner sc = new Scanner(System.in);
//        String name = (String) "getSelectedType";
        String name = sc.nextLine().trim();
        FilterCharacteristicsType functionType=null;
        switch (name) {
            case "butterworth":
                functionType = FilterCharacteristicsType.butterworth;
                break;
            case "chebyshev": //I型
                functionType = FilterCharacteristicsType.chebyshev;
                break;
            case "bessel":
                functionType = FilterCharacteristicsType.bessel;
                break;
        }
//        return functionType;
    }
}
