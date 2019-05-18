package com.example.intelligentpotver2;

public class Value {

    private static float emTemp;
    private static float emHumi;
    private static float potHumi;
    private static float ph;
    private static float co2;
    private static String time;

    public static void setValue(float a,float b,float c, float d, float e,String f){
        Value.emTemp = a;
        Value.emHumi = b;
        Value.potHumi = c;
        Value.ph = d;
        Value.co2 = e;
        Value.time = f;
    }

    public static float getEmTemp(){
        return emTemp;
    }

    public static float getEmHumi(){
        return emHumi;
    }

    public static float getPotHumi(){
        return potHumi;
    }

    public static float getPh() {
        return ph;
    }

    public static float getCo2() {
        return co2;
    }

    public static String getTime(){
        return time;
    }
}
