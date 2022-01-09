package com.example.passwordmanagerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.passwordmanagerapp.Models.AppItem;

public class SelectedItemViewActivity extends AppCompatActivity {

    private static final String TAG = "SelectedItemViewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_item_view);


        if(getIntent().hasExtra("selectedItem")){
            AppItem selectedItem = getIntent().getParcelableExtra("selectedItem");
            Log.i(TAG, "onCreate id: " + selectedItem.getId());
            Log.i(TAG, "onCreate app: " + selectedItem.getAppName());
            Log.i(TAG, "onCreate account: " + selectedItem.getAccount());
            Log.i(TAG, "onCreate password: " + selectedItem.getPassword());
        }


    }
}