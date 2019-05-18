package com.example.intelligentpotver2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PlantsAdapter extends RecyclerView.Adapter<PlantsAdapter.ViewHolder>{

    private Context mContext;
    private List<Plants> mPlantsList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView plantsImage;
        TextView plantsName;

        public ViewHolder(View view){
            super(view);
            cardView = (CardView) view;
            plantsImage = (ImageView) view.findViewById(R.id.plants_image);
            plantsName = (TextView) view.findViewById(R.id.plants_name);
        }
    }

    public PlantsAdapter(List<Plants> plantsList){
        mPlantsList = plantsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        if (mContext == null){
            mContext = parent.getContext();
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.plants_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Plants plants = mPlantsList.get(position);
                Intent intent = new Intent(mContext,PlantActivity.class);
                intent.putExtra("plants",plants);
                mContext.startActivity(intent);
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int positon){
        Plants plants = mPlantsList.get(positon);
        holder.plantsName.setText(plants.getPlantsName());
        Glide.with(mContext).load(plants.getPlantsImgId()).into(holder.plantsImage);
    }

    @Override
    public int getItemCount(){
        return mPlantsList.size();
    }

}

