package com.example.intelligentpotver2;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    List<String> names = new ArrayList<>();
    List<Integer> color = new ArrayList<>();
    LineChart chart;

    LineChartManager etChartManager;

    Boolean threadFlag = true;
    int valueId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        TextView describe = (TextView) findViewById(R.id.describe);
        Toolbar chartActivityToolbar = (Toolbar) findViewById(R.id.chart_activity_toolbar);
        setSupportActionBar(chartActivityToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        chart = (LineChart) findViewById(R.id.chart);

        getSupportActionBar().setTitle("详细信息");

        valueId = getIntent().getIntExtra("valueId",0);
        names.add("环境温度");
        names.add("环境湿度");
        names.add("土壤湿度");
        names.add("PH");
        names.add("CO²浓度");

        color.add(Color.CYAN);
        color.add(Color.GREEN);
        color.add(Color.RED);
        color.add(Color.YELLOW);
        color.add(Color.BLUE);

        switch (valueId){
            case 0:
                etChartManager = setChartManger(0,40,0,4);
                describe.setText(getResources().getString(R.string.emTemp));
                etChartManager.setHightLimitLine(35,"最高温",Color.GREEN);
                break;
            case 1:
                etChartManager = setChartManger(1,100,0,7);
                describe.setText(getResources().getString(R.string.emHumi));
                break;
            case 2:
                etChartManager = setChartManger(2,100,0,5);
                describe.setText(getResources().getString(R.string.potHumi));
                etChartManager.setHightLimitLine(80,"最高湿度",Color.GREEN);
                break;
            case 3:
                etChartManager = setChartManger(3,8,0,8);
                describe.setText(getResources().getString(R.string.ph));
                etChartManager.setHightLimitLine(6,"最高PH",Color.GREEN);
                break;
            case 4:
                etChartManager= new LineChartManager(chart,names.get(4),color.get(4),4);
                etChartManager.setYAxis(0.05f,0,5);
                etChartManager.setDescription(names.get(4));
                describe.setText(getResources().getString(R.string.co2));
                etChartManager.setHightLimitLine(0.03f,"最高浓度",Color.GREEN);
                break;
        }

        getData();
    }

    private void getData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (threadFlag){
                    try {
                        //num = num + 1;
                        Thread.sleep(2000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    //Toast.makeText(MyApplication.getContext(),String.valueOf(num),Toast.LENGTH_SHORT).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (valueId){
                                case 0:
                                    etChartManager.addEntry(Value.getEmTemp(),Value.getTime());
                                    break;
                                case 1:
                                    etChartManager.addEntry(Value.getEmHumi(),Value.getTime());
                                    break;
                                case 2:
                                    etChartManager.addEntry(Value.getPotHumi(),Value.getTime());
                                    break;
                                case 3:
                                    etChartManager.addEntry(Value.getPh(),Value.getTime());
                                    break;
                                case 4:
                                    etChartManager.addEntry(Value.getCo2(),Value.getTime());
                                    break;
                            }
                        }
                    });
                }
            }
        }).start();
    }

    public LineChartManager setChartManger(int a,int b,int c,int d){
        LineChartManager mChartManager = new LineChartManager(chart,names.get(a),color.get(a),a);
        mChartManager.setYAxis(b,c,d);
        mChartManager.setDescription(names.get(a));
        return mChartManager;
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

    @Override
    public void onStop(){
        super.onStop();
        threadFlag = false;
    }
}
