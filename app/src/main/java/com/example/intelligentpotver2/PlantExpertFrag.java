package com.example.intelligentpotver2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class PlantExpertFrag extends Fragment {

    private List<Plants> plantsList = new ArrayList<>();
    private PlantsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.plant_expert_fragment,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        RecyclerView plantListView = (RecyclerView) getActivity().findViewById(R.id.plantList);
        initPlantsData();
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        plantListView.setLayoutManager(layoutManager);
        adapter = new PlantsAdapter(plantsList);
        plantListView.setAdapter(adapter);
    }

    private void initPlantsData(){
        Plants yueji = new Plants("招财树",R.drawable.zhaocaishu,"招财树性喜高温湿润和阳光照射。");
        plantsList.add(yueji);
        Plants junzilan = new Plants("君子兰",R.drawable.junzilan,"君子兰浇水一点也马虎不得");
        plantsList.add(junzilan);
        Plants duorou = new Plants("虎皮兰",R.drawable.hupilan,"虎皮兰土质需求疏松，需要透气性，所以在为虎皮兰配土的时候。");
        plantsList.add(duorou);
        Plants juhua = new Plants("芦荟",R.drawable.luhui,"栽培芦荟最好使用砂质土壤。");
        plantsList.add(juhua);
        Plants diaolan = new Plants("绿萝",R.drawable.lvluo,"性喜温暖、潮湿环境，要求土壤疏松、肥沃、排水良好。");
        plantsList.add(diaolan);
    }
}
