package com.example.passwordmanagerapp;

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
    private Boolean authorization = false;

    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private ConstraintLayout mainActivityLayout;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityLayout = findViewById(R.id.mainActivity);



        FingerPrintAuth();

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

    private void FingerPrintAuth() {

        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()){
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Toast.makeText(this,"Device doesn't have fingerprint",Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Toast.makeText(this,"Fingerprint hardware not working",Toast.LENGTH_SHORT).show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                Toast.makeText(this,"No FingerPrint Assigned",Toast.LENGTH_SHORT).show();
                break;
        }

        mainActivityLayout.setVisibility(View.INVISIBLE);
        Executor executor = ContextCompat.getMainExecutor(this);

        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(),"Authentication Error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                mainActivityLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder().setTitle("FingerPrint Authentication")
                .setDescription("Use FingerPrint to Login").setDeviceCredentialAllowed(true).build();

        biometricPrompt.authenticate(promptInfo);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        FingerPrintAuth();
        try {
            showUsersData();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        FingerPrintAuth();
        try {
            showUsersData();

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        Log.i("Item ", "Clicked");

        AppItem p = listOfItems.get(position);
        Intent intent = new Intent(this,SelectedItemViewActivity.class);
        intent.putExtra("selectedItem", (Parcelable) p);
        startActivity(intent);
    }
}