package com.example.intelligentpotver2;

import java.io.Serializable;

public class Plants implements Serializable{

    private String plantsName;
    private int plantsImgId;
    private String plantsContent;

    public Plants(String a,int b,String c){
        this.plantsName = a;
        this.plantsImgId = b;
        this.plantsContent = c;
    }

    public String getPlantsName(){
        return plantsName;
    }

    public int getPlantsImgId(){
        return plantsImgId;
    }

    public String getPlantsContent(){
        return this.plantsContent;
    }
}
