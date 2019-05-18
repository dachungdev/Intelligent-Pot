package com.example.intelligentpotver2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
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

public class MyPlantFrag extends Fragment implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {

    Boolean threadFlag = true;

    TextView evmtTempText;
    TextView evmtHumiText;
    TextView potHumiText;
    TextView phText;
    TextView co2Text;

    LinearLayout evmtTempLin;
    LinearLayout evmtHumiLin;
    LinearLayout potHumiLin;
    LinearLayout phLin;
    LinearLayout co2Lin;

    CircleImageView controlImg;
    CircleImageView changeImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.my_plant_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        evmtTempText = (TextView) getActivity().findViewById(R.id.evmtTempText);
        evmtHumiText = (TextView) getActivity().findViewById(R.id.evmtHumiText);
        potHumiText = (TextView) getActivity().findViewById(R.id.potHumiText);
        phText = (TextView) getActivity().findViewById(R.id.phText);
        co2Text = (TextView) getActivity().findViewById(R.id.co2Text);

        evmtTempLin = (LinearLayout) getActivity().findViewById(R.id.evmt_temp_lin);
        evmtHumiLin = (LinearLayout) getActivity().findViewById(R.id.evmt_humi_lin);
        potHumiLin = (LinearLayout) getActivity().findViewById(R.id.pot_humi_lin);
        phLin = (LinearLayout) getActivity().findViewById(R.id.ph_lin);
        co2Lin = (LinearLayout) getActivity().findViewById(R.id.co2_lin);

        controlImg = (CircleImageView) getActivity().findViewById(R.id.my_plant_control_img);
        changeImg = (CircleImageView) getActivity().findViewById(R.id.my_plant_change_img);

        evmtTempLin.setOnClickListener(this);
        evmtHumiLin.setOnClickListener(this);
        potHumiLin.setOnClickListener(this);
        phLin.setOnClickListener(this);
        co2Lin.setOnClickListener(this);

        controlImg.setOnClickListener(this);
        changeImg.setOnClickListener(this);
        getData();
    }

    public void getData(){
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
                    if (getActivity() == null){
                        return;
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setData();
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.evmt_humi_lin:
                Toast.makeText(getActivity(),"evmtHUmi",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),ChartActivity.class);
                intent.putExtra("valueId",1);
                startActivity(intent);
                break;
            case R.id.evmt_temp_lin:
                Toast.makeText(getActivity(),"evmtTemp",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getActivity(),ChartActivity.class);
                intent1.putExtra("valueId",0);
                startActivity(intent1);
                break;
            case R.id.pot_humi_lin:
                Toast.makeText(getActivity(),"potHUmi",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getActivity(),ChartActivity.class);
                intent2.putExtra("valueId",2);
                startActivity(intent2);
                break;
            case R.id.ph_lin:
                Toast.makeText(getActivity(),"phText",Toast.LENGTH_SHORT).show();
                Intent intent3 = new Intent(getActivity(),ChartActivity.class);
                intent3.putExtra("valueId",3);
                startActivity(intent3);
                break;
            case R.id.co2_lin:
                Toast.makeText(getActivity(),"co2Text",Toast.LENGTH_SHORT).show();
                Intent intent4 = new Intent(getActivity(),ChartActivity.class);
                intent4.putExtra("valueId",4);
                startActivity(intent4);
                break;
            case R.id.my_plant_control_img:
                PopupMenu popupMenu = new PopupMenu(getActivity(),view);
                MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.control_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();
                break;
            case R.id.my_plant_change_img:
                PopupMenu popChange = new PopupMenu(getActivity(),view);
                MenuInflater inflaterChange = popChange.getMenuInflater();
                inflaterChange.inflate(R.menu.change_menu,popChange.getMenu());
                popChange.setOnMenuItemClickListener(this);
                popChange.show();
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item){
        switch (item.getItemId()){
            case R.id.add_water:
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_light:
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.one_plant:
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.two_plant:
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                break;
            case R.id.add_plant:
                Toast.makeText(getActivity(),"敬请期待",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }

    public void setData(){
        evmtTempText.setText(String.valueOf(Value.getEmTemp())+"°");
        evmtHumiText.setText(String.valueOf(Value.getEmHumi())+"%");
        potHumiText.setText(String.valueOf(Value.getPotHumi())+"%");
        phText.setText(String.valueOf(Value.getPh()));
        co2Text.setText(String.valueOf(Value.getCo2())+"%");
        /*String url = "http://47.112.17.83:8080/FlowerPotWebApp/SqlServServlet";
        String tag = "GetFlowerDetailData";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
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
                    evmtTempText.setText(json.getString("EvmtTemp")+"°");
                    float ehdata = Float.valueOf(json.getString("EvmtHumi"));
                    evmtHumiText.setText(json.getString("EvmtHumi")+"%");
                    float thdata = Float.valueOf(json.getString("TRHumi"));
                    potHumiText.setText(json.getString("TRHumi")+"%");
                    float phdata = Float.valueOf(json.getString("PHData"));
                    phText.setText(json.getString("PHData"));
                    float co2data = Float.valueOf(json.getString("CO2Data"));
                    co2Text.setText(json.getString("CO2Data")+"%");
                    Value.setValue(etdata,ehdata,thdata,phdata,co2data,showTime);

                } catch (JSONException e) {
                    Toast.makeText(getActivity(), "no internet", Toast.LENGTH_SHORT).show();
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
                //params.put("Num",String.valueOf(num));
                return params;
            }
        };
        request.setTag(tag);
        requestQueue.add(request);*/
    }

    @Override
    public void onStop(){
        super.onStop();
        threadFlag = false;
    }

    @Override
    public void onPause(){
        super.onPause();
        threadFlag = true;
    }
}
