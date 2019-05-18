package com.example.intelligentpotver2;

import java.io.Serializable;

public class Message implements Serializable{

    private int msgNum;
    private String msgDetail;
    private float msgValue;

    public Message(int mMsgNum,String mMsgDetail,float mMsgValue){
        this.msgNum = mMsgNum;
        this.msgDetail = mMsgDetail;
        this.msgValue = mMsgValue;
    }

    public int getMsgNum(){
        return msgNum;
    }

    public String getMsgDetail(){
        return msgDetail;
    }

    public float getMsgValue(){return msgValue;}

    public Boolean contain(Message a){
        return msgDetail.equals(a.getMsgDetail());
    }
}
