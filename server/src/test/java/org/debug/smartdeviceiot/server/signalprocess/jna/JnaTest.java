package org.debug.smartdeviceiot.server.signalprocess.jna;

import org.debug.smartdeviceiot.server.BaseTest;
import org.debug.smartdeviceiot.server.signalprocess.templateSignal.Signal;
import org.debug.smartdeviceiot.server.signalprocess.templateSignal.SignalFileDao;
import org.debug.smartdeviceiot.server.signalprocess.utils.OfflineDataUtil;
import org.debug.smartdeviceiot.server.signalprocess.entity.Signal1;
import org.junit.Test;


import java.util.Arrays;



public class JnaTest extends BaseTest {
    public static void main(String[] args){
        System.loadLibrary("testJna");
//        test1();
//        test2();
//        test3();
//        test4();
//        test6();
//        testReadSignal("");
//        System.out.println("utils.nextPow2(500) = " + (1<< nextPow2(500)));
    }

//
//    @Test
//    public void testDll() {
//        DllService.JNAtestJna instanceDll  = (DllService.JNAtestJna) Native.loadLibrary("C:\\Users\\LXZ\\source\\repos\\testJna\\x64\\Release\\testJna.dll", DllService.JNAtestJna.class);
//        //调用dll普通函数
////        int sum =instanceDll.add(10,60);
////        int sum2 =instanceDll.dec(10,20);
//
//        double[] y = new double[1001];
//        for (int i = 0; i < 1001; i++) {
//            y[i]=Math.sin(i*1.0/100.0);
//        }
//
//
//        //调用dll回调函数
//        DllService.JNAtestJna.Cback cc =new DllService.JNAtestJna.CbackIpm();
//        instanceDll.Handle(cc);
//
//        System.out.println(instanceDll.MEAN(y,0,1000));
//    }

    //时域指标
    @Test
    public  void test1(){
        dspAPI tNative = new dspAPI();

        double[] y = new double[1001];
        for (int i = 0; i < 1001; i++) {
            y[i]=Math.sin(i*1.0/100.0);
        }

        System.out.println(tNative.MEAN(y,0,1000));
        System.out.println(tNative.VARIANCE(y,0,1000));
        System.out.println(tNative.STD(y,0,1000));
        System.out.println(tNative.VPP(y,0,1000));
        System.out.println(tNative.RMS(y,0,1000));
        System.out.println(tNative.VP(y,0,1000));
        System.out.println(tNative.Xmean(y,0,1000));
        System.out.println(tNative.Xr(y,0,1000));
        System.out.println(tNative.VPParam(y,0,1000));
        System.out.println(tNative.WaveParam(y,0,1000));
        System.out.println(tNative.PulseParam(y,0,1000));
        System.out.println(tNative.YDParam(y,0,1000));
        System.out.println(tNative.SKEW(y,0,1000));
        System.out.println(tNative.KURTOSIS(y,0,1000));
    }

    //快速变换
    @Test
    public void test2(){
        dspAPI tNative = new dspAPI();
        int arrLen=128;
        double fs=100;

        double[] inputR = new double[arrLen];
        double[] inputI = new double[arrLen];
        double[] outputR = new double[arrLen];
        double[] outputI = new double[arrLen];

        for (int i = 0; i < arrLen; i++) {
            inputR[i]=0.5*Math.sin(2*Math.PI*15.0*i/fs)+2*Math.sin(2*Math.PI*40.0*i/fs);
            inputI[i]=0.0d;
            outputR[i]=0.0d;
            outputI[i]=0.0d;
        }
        tNative.DFT(inputR,inputI,outputR,outputI,arrLen,1);
        System.out.println("---------DFT output--------");
        System.out.println(Arrays.toString(outputR));
        System.out.println(Arrays.toString(outputI));//有精度损失，虚部符号和matlab结果相反

        System.out.println("---------FFT output--------");
        tNative.FFT(inputR,inputI,arrLen,1);
        System.out.println(Arrays.toString(inputR));
        System.out.println(Arrays.toString(inputI));//无精度损失，虚部符号和matlab结果相反

        tNative.FFT(inputR,inputI,arrLen,-1);
        System.out.println("---------IFFT output--------");
        System.out.println(Arrays.toString(inputR));
        System.out.println(Arrays.toString(inputI));//无精度损失，虚部符号和matlab结果相反

        tNative.RFFT(inputR,arrLen);
        System.out.println("---------RFFT output--------");
        System.out.println(Arrays.toString(inputR));

        tNative.IRFFT(inputR,arrLen);
        System.out.println("---------IRFFT output--------");
        System.out.println(Arrays.toString(inputR));

//        tNative.CZT(inputR,inputI,arrLen,10,0,20);  //调用方式不对
        System.out.println("---------CZT output--------");
        System.out.println(Arrays.toString(inputR));

        tNative.Hilbtf(inputR,arrLen);
        System.out.println("---------Hilbt output--------");
        System.out.println(Arrays.toString(inputR));   //虚部序列和matlab符号相反
    }

    //相关&卷积
    @Test
    public void test3(){
        dspAPI dsp= new dspAPI();
        double[] x=new double[16];
        double[] y=new double[16];
        double[] tx = {-1.0,5.0,2.0,7.0,6.0,9.0,4.0};
        double[] ty = {1.0,2.0,1.0,-3.0,4.0,5.0};
        int i;

        System.arraycopy(tx,0,x,0,7);
        System.arraycopy(ty,0,y,0,6);
        dsp.convol(x,y,7,6,16);
        System.out.println("-----------convol output----------");
        System.out.println(Arrays.toString(x));


        dsp.correl(x,y,7,6,16);
        System.out.println("-----------convol output----------");
        System.out.println(Arrays.toString(x));

    }

    //频域分析谱
    @Test
    public void test4(){
        dspAPI dsp= new dspAPI();
        int fs=100;
        int N=128;
        double[] xr=new double[N];
        double[] xi=new double[N];
        double[] mag=new double[N];
        double[] f=new double[N];
        for (int i = 0; i < N; i++) {
            xr[i]=0.5*Math.sin(2.0*Math.PI*15.0*i/fs)+2*Math.sin(2.0*Math.PI*40.0*i/fs);
            xi[i]=0.0;
        }

//        System.out.println("----------------ft---------------");
//        dsp.ft(mag,xr,xi,N,0);
//        for (double v : mag) {
//            System.out.println(v);
//        }

//        System.out.println("----------------rceps---------------");
//        dsp.rceps(mag,xr,xi,N);
//        for (double v : mag) {
//            System.out.println(v);
//        }

        System.out.println("-------------envelope-------------"); //和mlb精度不匹配
        dsp.envelop(mag,xr,N);
        for (double v : mag) {
            System.out.println(v);
        }

//        dsp.spectrum(mag,f,xr,xi,N,128,100,1);
//        dsp.emd("D:\\MATLAB\\R2017b\\ecg.txt",1280);

    }

    //从本地读文件并算频谱
    @Test
    public void test5() {
        byte[] bytes = OfflineDataUtil.fileToByteArray("D:\\MyFile\\LaborWork\\智能装备项目开发\\datasets\\LSdata\\启动水平摆放\\" +
                "channel3线圈数据40960short_sTime1607326916819");
        short[] shorts = OfflineDataUtil.extractShortsArray(bytes);
        for (int i = 0; i < 40; i++) {
            System.out.println(shorts[i]);
        }
        Signal1 signal1 = new Signal1(shorts, 40960.0);
        System.out.println("signal1.frameSize:"+signal1.frameSize);

        int size=signal1.getSignal().length;
        int n=128;
        double fs=100.0;
        //n最大到32768，到65536后结果不匹配
        System.out.println(n);
        double[] mag = new double[n];
        double[] f = new double[n];
        double[] xr = new double[n];
        double[] xi = new double[n];
        for (int i = 0; i < xr.length; i++) {
            xr[i]=signal1.getSignal()[i];
            xi[i]=0.0;
            mag[i]=0.0;
        }
        dspAPI dspAPI = new dspAPI();
        System.out.println("nfft = " + n);
//        double[] doubles = Arrays.copyOfRange(xr,0,128);
//        dspAPI.RFFT(doubles,8);
//        System.out.println("Arrays.toString(doubles) = " + Arrays.toString(doubles));
//        dspAPI.FFT(xr,xi,size,1);
        long l = System.currentTimeMillis();
//        dspAPI.ft(mag,xr,xi,n,0);
//        dspAPI.rceps(mag,xr,xi,n);
        dspAPI.envelop(mag,xr,n);
        dspAPI.emd("C:\\Users\\LXZ\\source\\repos\\dspLib\\dspLib\\ecg.txt",1280);

        for (int i = 0; i < 128; i++) {
//            System.out.println("mag[i] = " + mag[i]);
        }
    }


}
