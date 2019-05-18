package com.example.intelligentpotver2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private MyFkingReceiver myFkingReceiver;


    LinearLayout butlerLin;
    LinearLayout myPlantLin;
    LinearLayout plantExpertLin;
    LinearLayout settingLin;

    ImageView butlerImg;
    ImageView myPlantImg;
    ImageView plantExpertImg;
    ImageView settingImg;

    TextView butlerText;
    TextView myPlantText;
    TextView plantExpertText;
    TextView settingText;

    TextView testText;
    Button testBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        butlerLin = (LinearLayout) findViewById(R.id.butler_lin);
        myPlantLin = (LinearLayout) findViewById(R.id.my_plant_lin);
        plantExpertLin = (LinearLayout) findViewById(R.id.plant_expert_lin);
        settingLin = (LinearLayout) findViewById(R.id.setting_lin);

        butlerImg = (ImageView) findViewById(R.id.butler_img);
        myPlantImg = (ImageView) findViewById(R.id.my_plant_img);
        plantExpertImg = (ImageView) findViewById(R.id.plant_expert_img);
        settingImg = (ImageView) findViewById(R.id.setting_img);

        butlerLin.setOnClickListener(this);
        myPlantLin.setOnClickListener(this);
        plantExpertLin.setOnClickListener(this);
        settingLin.setOnClickListener(this);

        getSupportActionBar().setTitle("花盆助手");

        //永动机，启动！
        Intent startIntent = new Intent(MyApplication.getContext(),NetWorkService.class);
        startService(startIntent);

        replaceFragment(new ButlerFragment());
        butlerImg.setImageResource(R.mipmap.butlercol);
        butlerLin.setActivated(true);

        //startNetService();
        intentFilter = new IntentFilter();
        intentFilter.addAction("test1");
        intentFilter.addAction("test2");
        intentFilter.addAction("test3");
        myFkingReceiver = new MyFkingReceiver();
        localBroadcastManager.registerReceiver(myFkingReceiver,intentFilter);
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.topLayout,fragment);
        transaction.commit();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(myFkingReceiver);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.butler_lin:
                replaceFragment(new ButlerFragment());
                butlerImg.setImageResource(R.mipmap.butlercol);
                myPlantImg.setImageResource(R.mipmap.myplant);
                plantExpertImg.setImageResource(R.mipmap.plantexpert);
                settingImg.setImageResource(R.mipmap.setting);
                butlerLin.setActivated(true);
                break;
            case R.id.my_plant_lin:
                replaceFragment(new MyPlantFrag());
                if (MessageControl.getFlag()==1) {
                    butlerImg.setImageResource(R.mipmap.butlerwarmcol);
                }
                else {butlerImg.setImageResource(R.mipmap.butler);}
                myPlantImg.setImageResource(R.mipmap.myplantcol);
                plantExpertImg.setImageResource(R.mipmap.plantexpert);
                settingImg.setImageResource(R.mipmap.setting);
                butlerLin.setActivated(false);
                break;
            case R.id.plant_expert_lin:
                replaceFragment(new PlantExpertFrag());
                if (MessageControl.getFlag()==1) {
                    butlerImg.setImageResource(R.mipmap.butlerwarmcol);
                }
                else {butlerImg.setImageResource(R.mipmap.butler);}
                myPlantImg.setImageResource(R.mipmap.myplant);
                plantExpertImg.setImageResource(R.mipmap.plantexpertcol);
                settingImg.setImageResource(R.mipmap.setting);
                butlerLin.setActivated(false);
                break;
            case R.id.setting_lin:
                replaceFragment(new SettingFrag());
                if (MessageControl.getFlag()==1) {
                    butlerImg.setImageResource(R.mipmap.butlerwarmcol);
                }
                else {butlerImg.setImageResource(R.mipmap.butler);}
                myPlantImg.setImageResource(R.mipmap.myplant);
                plantExpertImg.setImageResource(R.mipmap.plantexpert);
                settingImg.setImageResource(R.mipmap.settingcol);
                butlerLin.setActivated(false);
                break;
            default:
                break;
        }
    }

    /*
    private void imgSet(ImageView...img){
        for (int i = 0; i < img.length;i++){
            if (i == 0){
                btn[i].setTextColor(Color.RED);
            }
            else {
                btn[i].setTextColor(Color.BLACK);
                if (MessageControl.getFlag()!=0){
                    butlerBtn.setTextColor(Color.GREEN);
                }
            }
        }
    }
    */

    private void startNetService(){
        Intent intent = new Intent("com.example.intelligentpotver2.FK_YOU");
        sendBroadcast(intent);
    }

    class MyFkingReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context,Intent intent){
            String wt = intent.getAction();
            if (wt.equals("test3")){
                if (butlerLin.isActivated()){
                    ;
                }
                else {butlerImg.setImageResource(R.mipmap.butlerwarmcol);}
                //Toast.makeText(MyApplication.getContext(),"from service",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
