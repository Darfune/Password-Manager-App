package com.example.passwordmanagerapp;

import static android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements AppItemAdapter.OnItemListener{

    private RecyclerView itemRecyclerView;
    private EditText searchbarEditText;
    private AppItemAdapter appItemAdapter;
    private List<AppItem> listOfItems;

    private FloatingActionButton addItemFloatingActionButton;
    private SharePrefEncryption sharedPreferencesForAppData;
    private static final String filename = "SecureDataFile";

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        addItemFloatingActionButton = (FloatingActionButton) findViewById(R.id.addItemFloatingActionButton);

        sharedPreferencesForAppData = new SharePrefEncryption(this,filename);
        listOfItems = new LinkedList<>();
        appItemAdapter = new AppItemAdapter(this,listOfItems, this);

        itemRecyclerView = findViewById(R.id.itemRecyclerView);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemRecyclerView.setAdapter(appItemAdapter);


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
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            showUsersData();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void showUsersData() throws JSONException {
        listOfItems.clear();
        Map<String,?> entries = sharedPreferencesForAppData.getAll();
        Set<String> keys = entries.keySet();
        if(!keys.isEmpty()){
            for(String key: keys){
                AppItem item = sharedPreferencesForAppData.fetchData(key);
                listOfItems.add(item);
            }
            appItemAdapter.notifyDataSetChanged();
        }

    }

    public void addItem(View view) {

        startActivity(new Intent(MainActivity.this, AddApp.class));

    }

    @Override
    public void onNoteClick(int position) {
        AppItem p = listOfItems.get(position);
        Intent intent = new Intent(this,SelectedItemViewActivity.class);
        intent.putExtra("selectedItem", (Parcelable) p);
        startActivity(intent);
    }
}