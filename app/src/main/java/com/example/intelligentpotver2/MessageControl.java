package com.example.intelligentpotver2;

public class MessageControl {

    private static int emTempFlag = 0;
    private static int emHumiFlag = 0;
    private static int potHumiFlag = 0;
    private static int phFlag = 0;
    private static int co2Flag = 0;
    private static int flag = 0;

    public static void setEmTempFlag(int a){
        MessageControl.emTempFlag = a;
    }

    public static void setEmHumiFlag(int a){
        MessageControl.emHumiFlag = a;
    }

    public static void setPotHumiFlag(int a){
        MessageControl.potHumiFlag = a;
    }

    public static void setPhFlag(int a){
        MessageControl.phFlag = a;
    }

    public static void setCo2Flag(int a){
        MessageControl.co2Flag = a;
    }

    public static int getEmTempFlag(){
        return emTempFlag;
    }

    public static int getEmHumiFlag(){
        return emHumiFlag;
    }

    public static int getPotHumiFlag(){
        return potHumiFlag;
    }

    public static int getPhFlag(){
        return phFlag;
    }

    public static int getCo2Flag(){
        return co2Flag;
    }

    public static void addflag(){
        MessageControl.flag ++;
    }

    public static void reduceFlag(){
        MessageControl.flag --;
    }

    public static int getFlag(){
        return flag;
    }

}
