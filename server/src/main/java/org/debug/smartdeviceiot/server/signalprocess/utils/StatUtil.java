package org.debug.smartdeviceiot.server.signalprocess.utils;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.descriptive.rank.Median;

public class StatUtil {

    static double MEAN(double[] input){
        return StatUtils.mean(input);
    }

    static double Kurtosis(double[] input){
        return new Kurtosis().evaluate(input);
    }
    static double Skewness(double[] input){
        return new Skewness().evaluate(input);
    }
    static double Sum(double[] input){
        return StatUtils.sum(input);
    }
    static double Max(double[] input){
        return StatUtils.max(input);

    }
    static double Min(double[] input){
        return StatUtils.min(input);

    }
    static double Variance(double[] input){
        return StatUtils.variance(input);

    }
    static double StandardDeviation(double[] input){
        return new StandardDeviation().evaluate(input);
    }
    //中位数
    static double Median(double[] input){
        return new Median().evaluate(input);
    }

    //众数
    static double[] Mode(double[] input){
        return StatUtils.mode(input);
    }

    //几何平均数
    static double GeometricMean(double[] input){
        return StatUtils.geometricMean(input);
    }

    // 累乘
    static double Product(double[] input){
        return StatUtils.product(input);
    }

    //总体方差
    static double PopulationVariance(double[] input){
        return StatUtils.populationVariance(input);
    }

    //对数和
    static double SumLog(double[] input){
        return StatUtils.sumLog(input);
    }

    //平方和
    static double SumSq(double[] input){
        return StatUtils.sumSq(input);
    }

}
