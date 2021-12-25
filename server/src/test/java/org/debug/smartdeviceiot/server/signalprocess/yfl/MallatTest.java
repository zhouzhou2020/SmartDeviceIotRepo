package org.debug.smartdeviceiot.server.signalprocess.yfl;

import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

import static org.debug.smartdeviceiot.server.signalprocess.yfl.Mallat.dwt;

public class MallatTest extends BaseTest {

    @Test
    public void test() {
        int i,j,m,n,wlen;
        int[] sca=new int[4];
        int[] flag=new int[20];
        double f1,f2,fs,pi,freq;
        double[] c = new double[20];
        double[] d = new double[20];
//        double[] g={-0.332670553950,0.806891509311,-0.459877502118,-0.135011020010,0.085441273882,0.035226291882};
//        double[] h={0.0352263,-0.08544127,-0.135011,0.459877502118,0.8068915,0.33267055};
        double[] g={-0.48296,0.83652,-0.22414,-0.12941};
        double[] h={-0.12941,0.22414,0.83652,0.48296};
        m=4;
        n=8;
        double[] x1={2 ,5 ,8, 9 ,7 ,4 ,-1 ,1};
        double[] y1={1,2,1,-3,4,5};
        for (i = 0; i < x1.length; i++) {
            c[i]=x1[i];
        }
        j=n;
        flag[0]=0;
        for ( i = 0; i < m; i++) {
            flag[i+1]=flag[i]+j;
            sca[i]=j;
            j=j/2;
        }
        wlen=4;
        dwt(g,h,wlen,c,d,m,sca);

    }
}
