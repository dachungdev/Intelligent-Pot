package com.example.intelligentpotver2;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
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

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    private Boolean threadFlag = true;

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent){
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
                            Log.d("NetWorkService",String.valueOf(Value.getPotHumi()));
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
            }
        }).start();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("MyIntentService","onDestroy executed");
    }
}
