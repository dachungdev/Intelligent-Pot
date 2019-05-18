package com.example.intelligentpotver2;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;


public class MsgDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_detail);
        Toolbar msgDetailToolbar = (Toolbar) findViewById(R.id.msg_detail_activity_toolbar);
        TextView msgDetailValue = (TextView) findViewById(R.id.msg_detail_value);
        TextView msgDetailDescription = (TextView) findViewById(R.id.msg_detail_description);
        TextView msgDetailContent = (TextView) findViewById(R.id.msg_detail_content);
        setSupportActionBar(msgDetailToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setTitle("信息");
        String value = "当前环境温度为：";
        String text1 = "处于";
        String text2 = "的不健康状态。";
        String text3 = "建议您：";

        Message msg3 = (Message) getIntent().getSerializableExtra("MsgItem");
        switch (msg3.getMsgNum()){
            case 1:
                msgDetailValue.setText(value+String.valueOf(Value.getEmTemp())+"°");
                msgDetailDescription.setText(text1+msg3.getMsgDetail()+text2);
                msgDetailContent.setText(text3+getResources().getString(R.string.emTemp));
                break;
            case 2:
                msgDetailValue.setText(value+String.valueOf(Value.getEmHumi())+"%");
                msgDetailDescription.setText(text1+msg3.getMsgDetail()+text2);
                msgDetailContent.setText(text3+getResources().getString(R.string.emHumi));
                break;
            case 3:
                msgDetailValue.setText(value+String.valueOf(Value.getPotHumi())+"%");
                msgDetailDescription.setText(text1+msg3.getMsgDetail()+text2);
                msgDetailContent.setText(text3+getResources().getString(R.string.potHumi));
                break;
            case 4:
                msgDetailValue.setText(value+String.valueOf(Value.getPh()));
                msgDetailDescription.setText(text1+msg3.getMsgDetail()+text2);
                msgDetailContent.setText(text3+getResources().getString(R.string.ph));
                break;
            case 5:
                msgDetailValue.setText(value+String.valueOf(Value.getCo2())+"%");
                msgDetailDescription.setText(text1+msg3.getMsgDetail()+text2);
                msgDetailContent.setText(text3+getResources().getString(R.string.co2));
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
