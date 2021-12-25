package org.debug.smartdeviceiot.server.signalprocess.templateSignal;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

import java.util.Arrays;

public class RealSignalTest extends BaseTest {

    @Test
    public void test1(){
        double[] arr1=new double[256];
        double[] arr2=new double[256];

        for (int i = 0; i < arr1.length; i++) {
            arr1[i]=5*Math.sin(2*Math.PI*10*i*1.0/50);
        }
        for (int i = 0; i < arr2.length; i++) {
            arr2[i]=10*Math.cos(2*Math.PI*10*i*1.0/50);
        }

        Signal signal1=new RealSignal(arr1,0,100);
        Signal signal2=new RealSignal(arr2,0,100);

        Signal add = signal1.add(signal2);
        Signal subtract = signal1.subtract(signal2);
        Signal multiply = signal1.multiply(signal2);
        Signal divide = signal1.divide(signal2);

        Signal negate = signal1.negate();
        Signal inverse = signal1.inverse();
        Signal reverse = signal1.reverse();

        Signal convolution = signal1.convolution(signal2);
        Signal correlation = signal1.correlation(signal2);
        Signal correlationUsingConvolution = signal1.correlationUsingConvolution(signal2);

        double average = signal1.average();
        double averageMagnitude = signal1.averageMagnitude();
        double power = signal1.power();
        double variance = signal1.variance();
        double effectiveValue = signal1.effectiveValue();

        partArrPrint(arr1,10,""+arr1);
        partArrPrint(arr2,10,""+arr2);
        partArrPrint(add.getValues(),10,""+add);
        partArrPrint(subtract.getValues(),10,""+subtract);
        partArrPrint(multiply.getValues(),10,""+multiply);
        partArrPrint(divide.getValues(),10,""+divide);
        partArrPrint(negate.getValues(),10,""+negate);
        partArrPrint(inverse.getValues(),10,""+inverse);
        partArrPrint(reverse.getValues(),10,""+reverse);

        partArrPrint(convolution.getValues(),10,""+convolution);
        partArrPrint(correlation.getValues(),10,""+correlation);
        partArrPrint(correlationUsingConvolution.getValues(),10,""+correlationUsingConvolution);

        System.out.println("average = " + average);
        System.out.println("averageMagnitude = " + averageMagnitude);
        System.out.println("power = " + power);
        System.out.println("variance = " + variance);
        System.out.println("effectiveValue = " + effectiveValue);

    }

    public void partArrPrint(double[] arr,int printNum,String arrName){
        double[] doubles = Arrays.copyOfRange(arr, 0, printNum);
        System.out.println(arrName+" = " + Arrays.toString(doubles));
    }

}
