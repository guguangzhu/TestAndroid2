package com.example.testandroid2.test;

/**
 * 类描述：单元测试
 * 创建人：G.G.Z
 * 创建时间：2017/3/3 14:23
 */
public class Calculator {

    public int add(int one, int another) {
        //为了简单起见，暂不考虑溢出等情况。
        return one + another;
    }

    public double sum(double a, double b){
        return a+b;
    }

    public double substract(double a, double b){
        return a-b;
    }

    public double divide(double a, double b){
        return a/b;
    }

    public double multiply(double a, double b){
        return a*b;
    }
}
