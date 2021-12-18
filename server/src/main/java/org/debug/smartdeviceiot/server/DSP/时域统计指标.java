package org.debug.smartdeviceiot.server.DSP;


import com.dlut.dsp612.DSP612;

public class Filter {
    /**师兄程序
     * */
    private double MovingFilter2(double[] num, double[] den, double[] xn, double[] yn, double x) {
        double y = 0;
        //y(n)=b0*x(n)+ b1*x(n-1)+b2*x(n-2)-a1*y(n-1)-a2*y(n-2)
        y = num[0] * x + num[1] * xn[0] + num[2] * xn[1] - den[1] * yn[0] - den[2] * yn[1];
        //x(n-2)=x(n-1)
        xn[1] = xn[0];
        //x(n-1)=x(n)
        xn[0] = x;
        //y(n-2)=y(n-1)
        yn[1] = yn[0];
        //y(n-1)=y(n)
        yn[0] = y;
        return y;
    }

    private double MovingFilterN(double[][] num, double[][] den, double[][] xn, double[][] yn, double x) {
        double y = x;
        for (int i = 0; i < num.length; i++) {
            y = MovingFilter2(num[i], den[i], xn[i], yn[i], y);
        }
        return y;
    }
    private final double[][] NumAll = new double[4][];
    private final double[][] DenAll = new double[4][];

    //通频滤波函数：bandpass IIR Butterworth order=8 fs=131,072 fc1=50Hz fc2=10kHz
    public double MovingFilterAll(double[][] xn, double[][] yn, double x) {
        return MovingFilterN(NumAll, DenAll, xn, yn, x);
    }
    private final double[][] NumLow = new double[4][];
    private final double[][] DenLow = new double[4][];

    //低频滤波函数：bandpass IIR Butterworth order=8 fs=131,072 fc1=50Hz fc2=300Hz
    public double MovingFilterLow(double[][] xn, double[][] yn, double x) {
        return MovingFilterN(NumLow, DenLow, xn, yn, x);
    }
    private final double[][] NumMedium = new double[4][];
    private final double[][] DenMedium = new double[4][];

    //中频滤波函数：bandpass IIR Butterworth order=8 fs=131,072 fc1=300Hz fc2=1,800Hz
    public double MovingFilterMedium(double[][] xn, double[][] yn, double x) {
        return MovingFilterN(NumMedium, DenMedium, xn, yn, x);
    }
    private final double[][] NumHigh = new double[4][];
    private final double[][] DenHigh = new double[4][];

    //高频滤波函数：bandpass IIR Butterworth order=8 fs=131,072 fc1=1,800Hz fc2=10,000Hz
    public double MovingFilterHigh(double[][] xn, double[][] yn, double x) {
        return MovingFilterN(NumHigh, DenHigh, xn, yn, x);
    }
    private final double[][] NumBall = new double[4][];
    private final double[][] DenBall = new double[4][];

    //钢球滤波函数：bandpass IIR Butterworth order=8 fs=131,072 fc1=170Hz fc2=400Hz
    public double MovingFilterBall(double[][] xn, double[][] yn, double x) {
        return MovingFilterN(NumBall, DenBall, xn, yn, x);
    }
    public Filter() {
        //通频滤波参数
        NumAll[0] = new double[]{0.217867577416, 0, -0.217867577416};
        NumAll[1] = new double[]{0.217867577416, 0, -0.217867577416};
        NumAll[2] = new double[]{0.1979526300317, 0, -0.1979526300317};
        NumAll[3] = new double[]{0.1979526300317, 0, -0.1979526300317};

        DenAll[0] = new double[]{1, -1.511038067583, 0.7024119319967};
        DenAll[1] = new double[]{1, -1.998174221324, 0.9981799772181};
        DenAll[2] = new double[]{1, -1.995544443234, 0.9955502726316};
        DenAll[3] = new double[]{1, -1.250164119606, 0.4061585531365};

        //低频滤波参数
        NumLow[0] = new double[]{0.005978383678869, 0, -0.005978383678869};
        NumLow[1] = new double[]{0.005978383678869, 0, -0.005978383678869};
        NumLow[2] = new double[]{0.005959178242368, 0, -0.005959178242368};
        NumLow[3] = new double[]{0.005959178242368, 0, -0.005959178242368};

        DenLow[0] = new double[]{1, -1.992036267916, 0.9922312868542};
        DenLow[1] = new double[]{1, -1.998621751283, 0.99862781572};
        DenLow[2] = new double[]{1, -1.982977193202, 0.9830916655433};
        DenLow[3] = new double[]{1, -1.994911251382, 0.994921516679};

        //中频滤波参数
        NumMedium[0] = new double[]{0.03546076865589, 0, -0.03546076865589};
        NumMedium[1] = new double[]{0.03546076865589, 0, -0.03546076865589};
        NumMedium[2] = new double[]{0.03480829366406, 0, -0.03480829366406};
        NumMedium[3] = new double[]{0.03480829366406, 0, -0.03480829366406};

        DenMedium[0] = new double[]{1, -1.947432443746, 0.9543161075885};
        DenMedium[1] = new double[]{1, -1.991576182642, 0.9917937489725};
        DenMedium[2] = new double[]{1, -1.969545901011, 0.9699107201511};
        DenMedium[3] = new double[]{1, -1.898718757707, 0.9026742714988};

        //高频滤波参数
        NumHigh[0] = new double[]{0.1823692333867, 0, -0.1823692333867};
        NumHigh[1] = new double[]{0.1823692333867, 0, -0.1823692333867};
        NumHigh[2] = new double[]{0.1678278260133, 0, -0.1678278260133};
        NumHigh[3] = new double[]{0.1678278260133, 0, -0.1678278260133};

        DenHigh[0] = new double[]{1, -1.590806741448, 0.7812243673468};
        DenHigh[1] = new double[]{1, -1.945071258391, 0.9527538976422};
        DenHigh[2] = new double[]{1, -1.821689166727, 0.8341408941426};
        DenHigh[3] = new double[]{1, -1.472629969185, 0.5721276080584};
        //钢球滤波参数
        NumBall[0] = new double[]{0.005501122620502, 0, -0.005501122620502};
        NumBall[1] = new double[]{0.005501122620502, 0, -0.005501122620502};
        NumBall[2] = new double[]{0.005484851916346, 0, -0.005484851916346};
        NumBall[3] = new double[]{0.005484851916346, 0, -0.005484851916346};

        DenBall[0] = new double[]{1,   -1.993841089346,   0.9941889471931};
        DenBall[1] = new double[]{1,   -1.997323089518,   0.9973929853935};
        DenBall[2] = new double[]{1,   -1.987816445373,   0.9880404943786};
        DenBall[3] = new double[]{1,   -1.991585672233,    0.991693551392};
    }

    public double RMS(double[] x, int start, int end) {  //均方根
        double y = 0;
        for (int i = start; i <= end; i++) {
            y = y + x[i] * x[i];
        }
        y = y / (end + 1 - start);
        return Math.sqrt(y);
    }

    public double VPP(double[] x, int start, int end) {  //极差
        double min = x[start], max = x[start];
        for (int i = start; i <= end; i++) {
            if (x[i] < min) {
                min = x[i];
            } else if (x[i] > max) {
                max = x[i];
            }
        }
        return (max - min);
    }

    public double VP(double[] x, int start, int end) {    //峰值
        double min = x[start], max = x[start];
        for (int i = start; i <= end; i++) {
            if (x[i] < min) {
                min = x[i];
            } else if (x[i] > max) {
                max = x[i];
            }
        }
        if (Math.abs(max) >= Math.abs(min)) {
            return Math.abs(max);
        }
        return Math.abs(min);
    }
    //均值
    public double AVG(double[] x, int start, int end) {
        double y = 0;
        for (int i = start; i <= end; i++) {
            y = y + x[i];
        }
        return ( y/(end + 1 - start) );
    }
    //方差
    public double VARIANCE(double[] x, int start, int end) {
        double y = 0;
        double avg=AVG(x,start,end);
        for (int i = start; i <= end; i++) {
            y = y + (x[i]-avg)*(x[i]-avg);
        }
        return ( y/(end + 1 - start) );
    }
    //偏态，偏度
    public double SKEW(double[] x, int start, int end) {
        double y = 0;
        double avg=AVG(x,start,end);
        double s=Math.sqrt(VARIANCE(x,start,end));
        for (int i = start; i <= end; i++) {
            y = y + (x[i]-avg)*(x[i]-avg)*(x[i]-avg);
        }
        return ( y/(end + 1 - start)/(s*s*s) );
    }
    //峭度
    public double KURTOSIS(double[] x, int start, int end) {
        double y = 0;
        double avg=AVG(x,start,end);
        double s=VARIANCE(x,start,end);
        for (int i = start; i <= end; i++) {
            y = y + (x[i]-avg)*(x[i]-avg)*(x[i]-avg)*(x[i]-avg);
        }
        return ( y/(end + 1 - start)/(s*s) );
    }

    //*****************************FFT***************************
    public double[] HanningPeriodic(double[] x, int n) {
        double[] y = new double[n];
        int i;
        for (i = 0; i < n; i++) {
            y[i] = x[i] * (0.5 * (1 - Math.cos(2 * Math.PI * i / n)));
        }
        return y;
    }

    public double[] HannPeriodic(double[] x, int n) {
        double[] y = new double[n];
        int i;
        for (i = 0; i < n; i++) {
            y[i] = x[i] * (0.5 * (1 - Math.cos(2 * Math.PI * i / n)));
        }
        return y;
    }

    public double[] HannSymmetric(double[] x, int n) {
        double[] y = new double[n];
        int i;
        for (i = 0; i < n; i++) {
            y[i] = x[i] * (0.5 * (1 - Math.cos(2 * Math.PI * i / (n - 1))));
        }
        return y;
    }
    //  Symmetric对称的
    public double[] HanningSymmetric(double[] x, int n) {
        double[] y = new double[n];
        int i;
        for (i = 0; i < n; i++) {
            y[i] = x[i] * (0.5 * (1 - Math.cos(2 * Math.PI * (i+1) / (n + 1))));
        }
        return y;
    }

    private Complex MUL(Complex a, Complex b)//定义复乘
    {
        Complex c = new Complex();
        c.real = a.real * b.real - a.imag * b.imag;
        c.imag = a.real * b.imag + a.imag * b.real;
        return (c);
    }
    public void MYFFT(Complex[] xin, int NP)//输入为复数指针*xin，做N点FFT
    {
        int L = 0; // 级间运算层
        int J = 0; // 级内运算层
        int K = 0, KB = 0; // 蝶形运算层
        int M = 1, Nn = 0;// N=2^M
        int B = 0; // 蝶形运算两输入数据间隔
        /* 以下是为倒序新建的局部变量*/
        int LH = 0, J2 = 0, N1 = 0, I, K2 = 0;
        Complex Wn = new Complex();//定义旋转因子
        Complex Vn = new Complex();//每一级第一个旋转因子虚部为0，实部为1
        Complex T1 = new Complex();//存放旋转因子与X(k+B)的乘积
        /*以下是倒序*/
        LH = NP / 2; // LH=N/2
        J2 = LH;
        N1 = NP - 2;
        for (I = 1; I <= N1; I++) {
            if (I < J2) {
                T1 = xin[I];
                xin[I] = xin[J2];
                xin[J2] = T1;
            }
            K2 = LH;
            while (J2 >= K2) {
                J2 -= K2;
                K2 = K2 / 2;// K2=K2/2
            }
            J2 += K2;
        }
        /* 以下为计算出M */
        Nn = NP;
        while (Nn != 2)// 计算出N的以2为底数的幂M
        {
            M++;
            Nn = Nn / 2;
        }

        /* 蝶形运算 */
        for (L = 1; L <= M; L++) // 级间
        {
            B = (int) (Math.pow(2, (L - 1)));
            Vn.real = 1;
            Vn.imag = 0;
            Wn.real = Math.cos(Math.PI / B);
            Wn.imag = -Math.sin(Math.PI / B);
            for (J = 0; J < B; J++) // 级内
            {
                for (K = J; K < NP; K += 2 * B) // 蝶形因子运算
                {
                    KB = K + B;
                    T1 = MUL(xin[KB], Vn);
                    xin[KB].real = xin[K].real - T1.real;//原址运算，计算结果存放在原来的数组中
                    xin[KB].imag = xin[K].imag - T1.imag;
                    xin[K].real = xin[K].real + T1.real;
                    xin[K].imag = xin[K].imag + T1.imag;
                }
                Vn = MUL(Wn, Vn);// 旋转因子做复乘相当于指数相加，得到的结果
                // 和J*2^（M-L）是一样的，因为在蝶形因子运算
                // 层中M与L都是不变的，唯一变x化的是级内的J
                // 而且J是以1为步长的，如J*W等效于W+W+W...J个W相加
            }
        }
    }

    public Complex[] MYIFFT(Complex[] xin, int N)//输入为复数指针*xin，做N点FFT
    {
        Complex[] y = new Complex[N];

        for(int i = 0; i < N; i++){
            y[i] = new Complex();
        }

        // take conjugate
        for (int i = 0; i < N; i++) {
            y[i].real = xin[i].real;
            y[i].imag = -xin[i].imag;
        }

        // compute forward FFT
        MYFFT(y,N);

        // take conjugate again
        for (int i = 0; i < N; i++) {
            y[i].imag = -y[i].imag/N;
            y[i].real = y[i].real/N;
        }
        // divide by N
        return y;
    }

    public void ModelComplex(Complex[] Sample, int NP, double[] output) { //取模
        int i;
        for (i = 0; i < NP; i++) {
            output[i] = Math.sqrt(Sample[i].real * Sample[i].real + Sample[i].imag * Sample[i].imag)*2/NP;
        }
    }

    public float[][] getLvboData(float[] data) {  //滤波
        if (data == null) {
            return null;
        }
        float lvboData[][] = new float[3][];
        int len = data.length;
        lvboData[0] = new float[len];
        lvboData[1] = new float[len];
        lvboData[2] = new float[len];
        //具体算法
        /**等于没用*/

        return lvboData;
    }

    public static void main(String[] args) {
//        int Fs = 131072;
//        int Sample = Fs * 2;
//        Filter MyFilter = new Filter();
//        double[] x = new double[Sample];
//        double[] y = new double[Sample];
//        double[] ya = new double[Sample];
//        double[] yl = new double[Sample];
//        double[] ym = new double[Sample];
//        double[] yh = new double[Sample];
//        double[] yb = new double[Sample];
//        double xRMS = 0;
//        double yaRMS = 0;
//        double ylRMS = 0;
//        double ymRMS = 0;
//        double yhRMS = 0;
//        double ybRMS = 0;
//        double xVPP = 0;
//        double yaVPP = 0;
//        double ylVPP = 0;
//        double ymVPP = 0;
//        double yhVPP = 0;
//        double ybVPP = 0;
//        double[][] XnAll = new double[4][2];
//        double[][] YnAll = new double[4][2];
//        double[][] XnLow = new double[4][2];
//        double[][] YnLow = new double[4][2];
//        double[][] XnMedium = new double[4][2];
//        double[][] YnMedium = new double[4][2];
//        double[][] XnHigh = new double[4][2];
//        double[][] YnHigh = new double[4][2];
//        double[][] XnBall = new double[4][2];
//        double[][] YnBall = new double[4][2];
//        for (int i = 0; i < Sample; i++) {
//
//            //原始信号
//            x[i] = 2*1.414*Math.sin(2 * 3.1415926 * 261 * i / Fs);  // + Math.sin(2 * 3.1415926 * 200 * i / Fs);
//
//            //通频信号
//            ya[i] = MyFilter.MovingFilterAll(XnAll, YnAll, x[i]);
//
//            //低频信号
//            yl[i] = MyFilter.MovingFilterLow(XnLow, YnLow, x[i]);
//            //中频信号
//
//            ym[i] = MyFilter.MovingFilterMedium(XnMedium, YnMedium, x[i]);
//            //高频信号
//            yh[i] = MyFilter.MovingFilterHigh(XnHigh, YnHigh, x[i]);
//            //钢球信号
//            yb[i] = MyFilter.MovingFilterBall(XnBall, YnBall, x[i]);
//        }
//        int Points = 16384;
//        xRMS = MyFilter.RMS(x, Sample - Points, Sample - 1);
//        yaRMS = MyFilter.RMS(ya, Sample - Points, Sample - 1);
//        ylRMS = MyFilter.RMS(yl, Sample - Points, Sample - 1);
//        ymRMS = MyFilter.RMS(ym, Sample - Points, Sample - 1);
//        yhRMS = MyFilter.RMS(yh, Sample - Points, Sample - 1);
//        ybRMS = MyFilter.RMS(yb, Sample - Points, Sample - 1);
//
//        xVPP = MyFilter.VPP(x, Sample - Points, Sample - 1);
//        yaVPP = MyFilter.VPP(ya, Sample - Points, Sample - 1);
//        ylVPP = MyFilter.VPP(yl, Sample - Points, Sample - 1);
//        ymVPP = MyFilter.VPP(ym, Sample - Points, Sample - 1);
//        yhVPP = MyFilter.VPP(yh, Sample - Points, Sample - 1);
//        ybVPP = MyFilter.VPP(yb, Sample - Points, Sample - 1);
//
//
//        double xAvg=MyFilter.AVG(x, Sample - Points, Sample - 1);
//        double xVar=MyFilter.VARIANCE(x, Sample - Points, Sample - 1);
//        double xSkew=MyFilter.SKEW(x, Sample - Points, Sample - 1);
//        double xK=MyFilter.KURTOSIS(x, Sample - Points, Sample - 1);
//        //**********************FFT*************************
//        Complex[] Data = new Complex[Sample];// 采样输入的实数转化为复数
//
//        y=MyFilter.HanningPeriodic(x,Sample);
//        for (int i = 0; i < Sample; i++) {
//            Data[i] = new Complex();
//        }
//        for (int i = 0; i < Sample; i++) //输入实数信号转换为复数
//        {
//            Data[i].real = y[i];
//            Data[i].imag = 0;
//        }
//        double[] Output = new double[Sample];// 输出的FFT幅值（复数的模）
//
//        MyFilter.MYFFT(Data, Sample);
//        MyFilter.ModelComplex(Data, Sample, Output);

        fftCost();
    //    testStat();
    }

    public static void fftCost(){
        Filter ft = new Filter();
        double[] input = DSP612.readInput("D:\\\\TestIO\\\\inHilb.txt");
        int Len= input.length;
        Complex[] in=new Complex[Len];
        for (int i = 0; i < Len; i++) //输入实数信号转换为复数
        {
            in[i]=new Complex();
            in[i].real = input[i];
         //   in[i].real = 1000*(Math.random()-0.5);
            in[i].imag = 0;
        }
        long start = System.nanoTime();
        ft.MYFFT(in, Len);
        //    ft.ModelComplex(in, signalLen, Out);
        long end = System.nanoTime();
        double cost = (end - start) * 1.0e-9;
        System.out.println("FFT耗时"+cost);
        double[] Out = new double[Len];// 输出的FFT幅值（复数的模）
        for (int i = 0; i < in.length; i++) {
            System.out.println(in[i].real+","+in[i].imag);
        }
//        System.out.println("-----------幅值------------");
//        for (int i = 0; i < Out.length; i++) {
//            System.out.println(Out[i]);
//        }
//        System.out.println("---------反变换--------------");
//        Complex[] myifft = ft.MYIFFT(in, signalLen);
//        for (int i = 0; i < Out.length; i++) {
//            System.out.println(myifft[i].real+","+myifft[i].imag);
//        }
    }

    public static void testStat(){
        Filter filter = new Filter();
        double[] input = new double[] { 0.33, 1.33,0.27333, 0.3, 0.501,
                0.444, 0.44, 0.34496, 0.33,0.3, 0.292, 0.667 };
//        double[] input = new double[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32};
        int len = input.length-1;
        System.out.println("mean:"+filter.AVG(input,0,len));
        System.out.println("峭度:"+filter.KURTOSIS(input,0,len));
        System.out.println("偏度:"+filter.SKEW(input,0,len));
        System.out.println("均方根："+filter.RMS(input,0,len));
        System.out.println("方差："+filter.VARIANCE(input,0,len));
        System.out.println("峰值："+filter.VP(input,0,len));
        System.out.println("极差："+filter.VPP(input,0,len));
    }

    public static double[] zoomFFT(double[] data){

        return data;
    }

    public static double[] CZT(double[] data){

        return data;
    }
}
