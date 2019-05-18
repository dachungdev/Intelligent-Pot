package com.example.intelligentpotver2;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MsgListManager extends BaseAdapter {

    private Context context;
    private List<Message> msgList;
    private int resource;

    public MsgListManager(Context mContext,List<Message> mMsgList,int mResource){
        this.context = mContext;
        this.msgList = mMsgList;
        this.resource = mResource;
    }

    @Override
    public int getCount(){return msgList.size();}

    @Override
    public Object getItem(int i){return msgList.get(i);}

    @Override
    public long getItemId(int i){return i;}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        Message message = msgList.get(i);
        MsgHolder msgHolder = null;

        final int flag = i;
        if (view == null){
            msgHolder = new MsgHolder();

            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource,null);

            msgHolder.msg_title = (TextView) view.findViewById(R.id.msg_title);
            msgHolder.msg_content = (TextView) view.findViewById(R.id.msg_content);
            view.setTag(msgHolder);
        }else {
            msgHolder = (MsgHolder) view.getTag();
        }

        msgHolder.msg_title.setText("一号盆栽：");
        msgHolder.msg_content.setText(message.getMsgDetail());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message msg3 = msgList.get(flag);
                Intent intent = new Intent(context,MsgDetailActivity.class);
                intent.putExtra("MsgItem",msg3);
                context.startActivity(intent);
            }
        });

        return view;
    }

    public void addMsg(Message a){
        if (msgList.size() == 0){
            msgList.add(a);
            this.notifyDataSetChanged();
        }
        else {
            for (int i = 0;i<msgList.size();i++){
                Message c = msgList.get(i);
                if (c.contain(a)){
                    break;
                }
                else {
                    if (i == msgList.size()-1){
                        msgList.add(a);
                        this.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    public void removeMsg(Message a){
        for (int i = 0;i<msgList.size();i++){
            Message c = msgList.get(i);
            if (c.contain(a)){
                msgList.remove(i);
                this.notifyDataSetChanged();
                break;
            }
        }
    }
}

class MsgHolder{
    TextView msg_title,msg_content;
}
