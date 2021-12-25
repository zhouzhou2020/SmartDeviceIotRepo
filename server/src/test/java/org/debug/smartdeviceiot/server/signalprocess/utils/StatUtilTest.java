package org.debug.smartdeviceiot.server.signalprocess.utils;

import org.apache.commons.math3.stat.StatUtils;
import org.debug.smartdeviceiot.server.BaseTest;
import org.junit.Test;

import static org.debug.smartdeviceiot.server.signalprocess.utils.StatUtil.*;

public class StatUtilTest extends BaseTest {

    @Test
    public void test() {
        //        double[] input = new double[] { 0.33, 1.33,0.27333, 0.3, 0.501,
//                0.444, 0.44, 0.34496, 0.33,0.3, 0.292, 0.667 };
        double[] input = new double[] {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32};

        System.out.println("平均数：" + MEAN(input));
        System.out.println("峭度：" + Kurtosis(input));
        System.out.println("偏度：" + Skewness(input));
        System.out.println("总和：" + Sum(input));
        System.out.println("最大值：" + Max(input));
        System.out.println("最小值：" + Min(input));
        System.out.println("方差：" + Variance(input));
        System.out.println("标准差：" + StandardDeviation(input));
        System.out.println("中位数：" + Median(input));
        System.out.println("众数：" + Mode(input));//返回一个数组
        System.out.println("几何平均数：" + GeometricMean(input));//n个正数的连乘积的n次算术根
        System.out.println("累乘:" + Product(input));
        System.out.println("总体方差:" + PopulationVariance(input));
        System.out.println("一组数据对数求和:" + SumLog(input));
        System.out.println("一组数据平方和：" + SumSq(input));
        //   System.out.println("平均概率偏差（平均差）："+StatUtils.meanDifference(input,input2));
        //   System.out.println("一组数据的方差差异性为：" + StatUtils.varianceDifference(values,values2,StatUtils.meanDifference(values, values2)));
        //   System.out.println("两样本数据的和差为：" + StatUtils.sumDifference(values,values2));
        double[] norm = StatUtils.normalize(input);
        System.out.println("第3个数标准化结果：" + norm[3]);
        System.out.println("从小到大排序后位于80%位置的数：" + StatUtils.percentile(input, 80));

    }

}
