package com.yutongdxTop.LaborDispatching.util;

public class GetRandom {
    public static String getRandom(int min, int max) {
        return String.valueOf((int)(min+Math.random()*(max-1+1)));
    }
}
