package com.example.intelligentpotver2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class NetWorkService extends Service {
    private DownloadBinder mBinder = new DownloadBinder();

    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d("NetWorkService","startDownload executed");
        }

        public int getProgress(){
            Log.d("NetworkService","getProgress executed");
            return 0;
        }
    }

    @Override
    public IBinder onBind(Intent intent){
        return mBinder;
    }

    public NetWorkService() {
    }

    public void onCreate(){
        super.onCreate();
        Log.d("NetWorkService","onCreate executed");
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getContext(),"start service",Toast.LENGTH_SHORT).show();
            }
        }).start();*/
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        final LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(MyApplication.getContext());
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://47.112.17.83:8080/FlowerPotWebApp/SqlServServlet";
                String tag = "GetFlowerDetailData";
                RequestQueue requestQueue = Volley.newRequestQueue(MyApplication.getContext());
                requestQueue.cancelAll(tag);
                final StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = (JSONObject) new JSONObject(response).get("params");
                            JSONArray json1 = jsonObject.getJSONArray("FlowerData");
                            JSONObject json = json1.getJSONObject(0);
                            String time = json.getString("AddTime");
                            String showTime = time.substring(10,19);
                            float etdata= Float.valueOf(json.getString("EvmtTemp"));
                            float ehdata = Float.valueOf(json.getString("EvmtHumi"));
                            float thdata = Float.valueOf(json.getString("TRHumi"));
                            float phdata = Float.valueOf(json.getString("PHData"));
                            float co2data = Float.valueOf(json.getString("CO2Data"));
                            Value.setValue(etdata,ehdata,thdata,phdata,co2data,showTime);
                            Log.d("NetWorkService",showTime);
                        } catch (JSONException e) {
                            Toast.makeText(MyApplication.getContext(), "no internet", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG",error.getMessage(),error);
                    }
                })
                {
                    @Override
                    protected Map<String,String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        return params;
                    }
                };
                request.setTag(tag);
                requestQueue.add(request);

                Log.d("NetWorkService",String.valueOf(Value.getPotHumi()));

                if (Value.getEmTemp()>35&&MessageControl.getEmTempFlag()==0){
                    Intent highEmTempintent = new Intent("HighEmTemp");
                    localBroadcastManager.sendBroadcast(highEmTempintent);
                    MessageControl.setEmTempFlag(1);
                    MessageControl.addflag();
                }
                if (Value.getEmHumi()>100&&MessageControl.getEmHumiFlag()==0){
                    Intent highEmHumiIntent = new Intent("HighEmHumi");
                    localBroadcastManager.sendBroadcast(highEmHumiIntent);
                    MessageControl.setEmHumiFlag(1);
                    MessageControl.addflag();
                }
                if (Value.getPotHumi()>80&&MessageControl.getPotHumiFlag()==0){
                    Intent highPotHumiIntent = new Intent("HighPotHumi");
                    localBroadcastManager.sendBroadcast(highPotHumiIntent);
                    MessageControl.setPotHumiFlag(1);
                    MessageControl.addflag();
                }
                if (Value.getPh()>6&&MessageControl.getPhFlag() == 0){
                    Intent highPotHumiIntent = new Intent("HighPh");
                    localBroadcastManager.sendBroadcast(highPotHumiIntent);
                    MessageControl.setPhFlag(1);
                    MessageControl.addflag();
                }
                if (Value.getCo2()>0.03&&MessageControl.getCo2Flag()==0){
                    Intent highCo2Intent = new Intent("HighCo2");
                    localBroadcastManager.sendBroadcast(highCo2Intent);
                    MessageControl.setCo2Flag(1);
                    MessageControl.addflag();
                }

                if (Value.getEmTemp()<=35&&MessageControl.getEmTempFlag()==1){
                    Intent normalEmTempintent = new Intent("NormalEmTemp");
                    localBroadcastManager.sendBroadcast(normalEmTempintent);
                    MessageControl.setEmTempFlag(0);
                    MessageControl.reduceFlag();
                }
                if (Value.getEmHumi()<=100&&MessageControl.getEmHumiFlag()==1){
                    Intent normalEmHumiIntent = new Intent("NormalEmHumi");
                    localBroadcastManager.sendBroadcast(normalEmHumiIntent);
                    MessageControl.setEmHumiFlag(0);
                    MessageControl.reduceFlag();
                }
                if (Value.getPotHumi()<=80&&MessageControl.getPotHumiFlag()==1){
                    Intent normalPotHumiIntent = new Intent("NormalPotHumi");
                    localBroadcastManager.sendBroadcast(normalPotHumiIntent);
                    MessageControl.setPotHumiFlag(0);
                    MessageControl.reduceFlag();
                }
                if (Value.getPh()<=6&&MessageControl.getPhFlag() == 1){
                    Intent normalPotHumiIntent = new Intent("NormalPh");
                    localBroadcastManager.sendBroadcast(normalPotHumiIntent);
                    MessageControl.setPhFlag(0);
                    MessageControl.reduceFlag();
                }
                if (Value.getCo2()<=0.03&&MessageControl.getCo2Flag()==1){
                    Intent normalCo2Intent = new Intent("NormalCo2");
                    localBroadcastManager.sendBroadcast(normalCo2Intent);
                    MessageControl.setCo2Flag(0);
                    MessageControl.reduceFlag();
                }
                if (MessageControl.getFlag()!=0){
                    Intent flagIntent = new Intent("test3");
                    localBroadcastManager.sendBroadcast(flagIntent);
                }
                Log.d("NetWorkService", String.valueOf(MessageControl.getFlag()));


            }
        }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int twoS = 2 * 1000;
        long triggerAtTime = SystemClock.elapsedRealtime() + twoS;
        Intent i = new Intent(this,NetWorkService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        manager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("NetWorkService","onDestroy executed");
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getContext(),"stop service",Toast.LENGTH_SHORT).show();
            }
        }).start();*/
    }
}
