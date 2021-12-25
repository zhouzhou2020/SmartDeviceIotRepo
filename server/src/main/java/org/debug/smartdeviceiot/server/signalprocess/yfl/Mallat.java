package org.debug.smartdeviceiot.server.signalprocess.yfl;


public class Mallat {

    /**
     * Mallat算法快速计算：一维离散小波
     *    把信号x(t)表示为一簇函数的加权和，该簇函数由基本小波h(t)经过伸缩和平移形成。
     *     伸缩尺度a，时间平移为b
     *     离散小波的一个特例：a=2时，二进小波
     *     mallat算法对信号进行分解与重构的算法，它本身并没有涉及具体的小波函数。
     *     而Haar小波是一种可以用于信号分解与重构的小波函数，在Mallat算法中可以选择使用haar小波作为小波函数。
     * @param g  尺度系数
     * @param h  小波系数
     * @param wlen  小波长度
     * @param c
     * @param d
     * @param m  小波分解的级数
     * @param sca  小波分解时各级的数据长度，sca[0]是原信号长度
     */
    public static void dwt(double[] g,double[] h,int wlen,double[] c,double[]d,int m,int[] sca){
        int i,j,k,mid;
        int[] flag=new int[20];
        double p,q;
        for (flag[0]=0,i = 0; i < m; i++) {
            flag[i+1]=flag[i]+sca[i];
        }
        System.out.println("Doing decomposition");
        for(j=1;j<=m;j++){
            System.out.println("****");
            for(i=0;i<sca[j];i++){
                p=0;
                q=0;
                for (k = 0; k < wlen; k++) {
                    mid=k+2*i;
                    if(mid>=sca[j-1])
                        mid=mid-sca[j-1];
                    p=p+h[k]*c[flag[j-1]+mid];
                    q=q+g[k]*c[flag[j-1]+mid];
                }
                c[flag[j]+i]=p;
                d[flag[j]+i]=q;
            }
        }
        for (double v : c) {
            System.out.println(v);
        }
        System.out.println("----------d---------");
        for (double v : d) {
            System.out.println(v);
        }
    }

    public static void idwt(double[] g,double[] h,int wlen,double[] c,double[]d,int m,int[] sca){
        int i,j,k,mid;
        int[] flag=new int[20];
        double p,q;
        for (flag[0]=0,i = 0; i < m; i++) {
            flag[i+1]=flag[i]+sca[i];
        }
        System.out.println("Doing reconstruction");
        for(k=m;k>0;k--){
            System.out.println("****");
            for(i=0;i<sca[k];i++){
                p=0;
                q=0;
                for (j = 0; j < wlen/2; j++) {
                    mid=i-j;
                    if(mid<0)
                        mid=sca[k]+(i-j);
                    p+=h[2*j]*c[flag[k]+mid]+g[2*j]*d[flag[k]+mid];
                    q+=g[2*j+1]*d[flag[k]+mid]+h[2*j+1]*c[flag[k]+mid];
                }
                c[flag[k-1]+2*i]=p;
                c[flag[k-1]+2*i+1]=q;
            }
        }
    }

}
