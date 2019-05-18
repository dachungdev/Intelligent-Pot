package com.example.intelligentpotver2;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

public class ButlerFragment extends Fragment{

    private IntentFilter intentFilter;
    private TestReceiver testReceiver;
    private LocalBroadcastManager localBroadcastManager;


    private List<Message> msgList = new ArrayList<>();
    MsgListManager msgListManager;

    TextView normal_msg;
    private NetWorkService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            downloadBinder = (NetWorkService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.butler_fragment_layout,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*
        Button startService = (Button) getActivity().findViewById(R.id.start_service_btn);
        Button stopService = (Button) getActivity().findViewById(R.id.stop_service_btn);
        Button bindService = (Button) getActivity().findViewById(R.id.bind_service);
        Button unbindService = (Button) getActivity().findViewById(R.id.unbind_service);
        */
        normal_msg = (TextView) getActivity().findViewById(R.id.normal_message);

        ListView message = (ListView) getActivity().findViewById(R.id.message_list);
        localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());

        Intent startIntent = new Intent(getActivity(),NetWorkService.class);
        getActivity().startService(startIntent);

        /*
        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        bindService.setOnClickListener(this);
        unbindService.setOnClickListener(this);
        */

        initData();
        defaultPic();
        if (msgList.size()==0){
            normal_msg.setVisibility(View.VISIBLE);
        }
        else {
            normal_msg.setVisibility(View.GONE);
        }
        msgListManager = new MsgListManager(getActivity(),msgList,R.layout.message_item);
        message.setAdapter(msgListManager);

        intentFilter = new IntentFilter();
        intentFilter.addAction("HighEmTemp");
        intentFilter.addAction("HighEmHumi");
        intentFilter.addAction("HighPotHumi");
        intentFilter.addAction("HighPh");
        intentFilter.addAction("HighCo2");
        intentFilter.addAction("NormalEmTemp");
        intentFilter.addAction("NormalEmHumi");
        intentFilter.addAction("NormalPotHumi");
        intentFilter.addAction("NormalPh");
        intentFilter.addAction("NormalCo2");
        testReceiver = new TestReceiver();
        localBroadcastManager.registerReceiver(testReceiver,intentFilter);
    }

    /*
    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.start_service_btn:
                Intent startIntent = new Intent(getActivity(),NetWorkService.class);
                getActivity().startService(startIntent);
                break;
            case R.id.stop_service_btn:
                Intent stopIntent = new Intent(getActivity(),NetWorkService.class);
                getActivity().stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent intent = new Intent("testtest");
                localBroadcastManager.sendBroadcast(intent);
                break;
            case R.id.unbind_service:
                msgList.clear();
                msgListManager.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }
    */

    class TestReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            String wt = intent.getAction();
            if (wt.equals("NormalEmTemp")){
                Message msg2 = new Message(1,"环境温度过高",Value.getEmTemp());
                msgListManager.removeMsg(msg2);
                defaultPic();
            }
            if (wt.equals("NormalEmHumi")){
                Message msg2 = new Message(2,"环境湿度过高",Value.getEmHumi());
                msgListManager.removeMsg(msg2);
                defaultPic();
            }
            if (wt.equals("NormalPotHumi")){
                Message msg2 = new Message(3,"土壤湿度过高",Value.getPotHumi());
                msgListManager.removeMsg(msg2);
                defaultPic();
            }
            if (wt.equals("NormalPh")){
                Message msg2 = new Message(4,"PH值过高",Value.getPh());
                msgListManager.removeMsg(msg2);
                defaultPic();
            }
            if (wt.equals("NormalCo2")){
                Message msg2 = new Message(5,"CO²浓度过高",Value.getCo2());
                msgListManager.removeMsg(msg2);
                defaultPic();
            }
            if (wt.equals("HighEmTemp")){
                Message msg2 = new Message(1,"环境温度过高",Value.getEmTemp());
                msgListManager.addMsg(msg2);
                defaultPic();
            }
            if (wt.equals("HighEmHumi")){
                Message msg2 = new Message(2,"环境湿度过高",Value.getEmHumi());
                msgListManager.addMsg(msg2);
                defaultPic();
            }
            if (wt.equals("HighPotHumi")){
                Message msg2 = new Message(3,"土壤湿度过高",Value.getPotHumi());
                msgListManager.addMsg(msg2);
                defaultPic();
            }
            if (wt.equals("HighPh")){
                Message msg2 = new Message(4,"PH值过高",Value.getPh());
                msgListManager.addMsg(msg2);
                defaultPic();
            }
            if (wt.equals("HighCo2")){
                Message msg2 = new Message(5,"CO²浓度过高",Value.getCo2());
                msgListManager.addMsg(msg2);
                defaultPic();
            }
        }
    }

    public void initData(){
        if (MessageControl.getFlag()!=0){
            if (MessageControl.getEmTempFlag()!=0){
                Message msg2 = new Message(msgList.size()+1,"环境温度过高",Value.getEmTemp());
                msgList.add(msg2);
            }
            if (MessageControl.getEmHumiFlag()!=0){
                Message msg2 = new Message(msgList.size()+1,"环境湿度过高",Value.getEmHumi());
                msgList.add(msg2);
            }
            if (MessageControl.getPotHumiFlag()!=0){
                Message msg2 = new Message(msgList.size()+1,"土壤湿度过高",Value.getPotHumi());
                msgList.add(msg2);
            }
            if (MessageControl.getPhFlag()!=0){
                Message msg2 = new Message(msgList.size()+1,"PH值过高",Value.getPh());
                msgList.add(msg2);
            }
            if (MessageControl.getCo2Flag()!=0){
                Message msg2 = new Message(msgList.size()+1,"CO²浓度过高",Value.getCo2());
                msgList.add(msg2);
            }
        }
    }

    public void defaultPic(){
        if (msgList.size()==0){
            normal_msg.setVisibility(View.VISIBLE);
        }
        else {
            normal_msg.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(testReceiver);
    }
}
