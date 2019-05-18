package com.example.intelligentpotver2;

import android.app.ActionBar;
import android.view.MenuItem;
import android.widget.Toolbar;

public class BackBtn {

    private ActionBar mActionBar;

    public BackBtn(ActionBar actionBar){
        this.mActionBar = actionBar;
        if (actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

}
