package com.example.intelligentpotver2;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class PlantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);

        Plants plants = (Plants) getIntent().getSerializableExtra("plants");

        Toolbar toolbar = (Toolbar) findViewById(R.id.plants_ac_toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.plants_ac_collapsing_toolbar);
        ImageView plantsImgView = (ImageView) findViewById(R.id.plants_img_view);
        TextView plantsContentText = (TextView) findViewById(R.id.plants_content_text);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        collapsingToolbarLayout.setTitle(plants.getPlantsName());
        Glide.with(this).load(plants.getPlantsImgId()).into(plantsImgView);
        plantsContentText.setText(plants.getPlantsContent());
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
        finish();
    }
}
