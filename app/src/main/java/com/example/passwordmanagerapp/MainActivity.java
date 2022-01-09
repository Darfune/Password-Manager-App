package com.example.passwordmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.passwordmanagerapp.Adapters.AppItemAdapter;
import com.example.passwordmanagerapp.Models.AppItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchbarEditText;
    private AppItemAdapter appItemAdapter;
    private List<AppItem> listOfItems;
    private AppItemAdapter adapter;
    private FloatingActionButton addItemFloatingActionButton;
    private SharePrefEncryption sharedPreferencesForAppData;
    private static final String filename = "SecureDataFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItemFloatingActionButton = (FloatingActionButton) findViewById(R.id.addItemFloatingActionButton);


        sharedPreferencesForAppData = new SharePrefEncryption(this,filename);
        listOfItems = new LinkedList<>();
        adapter = new AppItemAdapter(this,listOfItems);

        RecyclerView itemRecyclerView = findViewById(R.id.itemRecyclerView);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemRecyclerView.setAdapter(adapter);

        try {
            showUsersData();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        searchbarEditText = (EditText) findViewById(R.id.searchbarEditText);

        searchbarEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String letter = s.toString();
                Log.i("Letter entered by user: ", letter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        try {
            showUsersData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showUsersData() throws JSONException {

        sharedPreferencesForAppData.fetchData();



    }

    public void addItem(View view) {

        startActivity(new Intent(MainActivity.this, AddApp.class));

    }
}