package org.debug.smartdeviceiot.server.DSP.filtering.iir;


import org.debug.smartdeviceiot.server.DSP.TemplateSignal.RealSignal;
import org.debug.smartdeviceiot.server.DSP.TemplateSignal.SignalFileDao;

public class IirFilterTest {

    public static void main(String[] args) {
        iirTest();
    }

    public static void iirTest(){
//        double[] time = new double[150];
//        double[] valueA = new double[150];
//        for (int i = 0; i < 50 * 3; i++) {
//            time[i] = i / 50.0;
//            valueA[i] = Math.sin(2 * Math.PI * 5 * time[i])+Math.sin(2 * Math.PI * 15 * time[i]);
//        }

        SignalFileDao signalFileDao = new SignalFileDao();
        double[] values = signalFileDao.read("D:\\Data\\垂直摆放线圈data.txt").getValues();
        for (int i = 0; i < 20; i++) {
            System.out.println(values[i]);
        }
        /* 第一个表示选择滤波器类型（低通，高通，带通，带阻），
        第二个参数表示滤波器的阶数，
        第三个参数表示下截止频率，
        第四个参数表示上截止频率，在低通和高通滤波器中只需要一个截止频率，所以在低通和高通中上截止频率是没有作用的。
        只有在带通或者带阻滤波器中才需要两个截止频率。
        * */
        IirFilterCoefficients iirFilterCoefficients;
        iirFilterCoefficients = IirFilterDesignExstrom.design(FilterPassType.lowpass, 2,
                600.0 / 40960, 600.0 / 40960);

        for (int i=0;i<iirFilterCoefficients.a.length;i++) {
            System.out.println("A["+i+"]:"+iirFilterCoefficients.a[i]);
        }
        for (int i=0;i<iirFilterCoefficients.b.length;i++) {
            System.out.println("B["+i+"]:"+iirFilterCoefficients.b[i]);
        }

        values = IIRFilter(values, iirFilterCoefficients.a, iirFilterCoefficients.b);
        signalFileDao.write(new RealSignal(values,0.0,40960.0),
                        "D:\\Data\\垂直线圈after.txt");
        System.out.println("--------------滤波后-----------------");
        for (int i = 0; i < 100; i++) {
            System.out.println(values[i]);
        }

    }

    public synchronized static double[] IIRFilter(double[] signal, double[] a, double[] b) {

        double[] in = new double[b.length];
        double[] out = new double[a.length-1];

        double[] outData = new double[signal.length];

        for (int i = 0; i < signal.length; i++) {

            System.arraycopy(in, 0, in, 1, in.length - 1);
            in[0] = signal[i];

            //calculate y based on a and b coefficients
            //and in and out.
            float y = 0;
            for(int j = 0 ; j < b.length ; j++){
                y += b[j] * in[j];

            }

            for(int j = 0;j < a.length-1;j++){
                y -= a[j+1] * out[j];
            }

            //shift the out array
            System.arraycopy(out, 0, out, 1, out.length - 1);
            out[0] = y;
            outData[i] = y;


        }
        return outData;
    }
}
