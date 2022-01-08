package com.example.passwordmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AddApp extends AppCompatActivity {

    private AutoCompleteTextView appAutoCompleteTextView;
    private AutoCompleteTextView accountAutoCompleteTextView;
    private AutoCompleteTextView passAutoCompleteTextView;
    private AutoCompleteTextView repassAutoCompleteTextView;
    private Button saveButton;

    private SharePrefEncryption saveData;
    private static final String filename = "SecureDataFile";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    public static final String SWITCH1 = "switch1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_app);


        appAutoCompleteTextView = findViewById(R.id.appAutoCompleteTextView);
        accountAutoCompleteTextView = findViewById(R.id.accountAutoCompleteTextView);
        passAutoCompleteTextView = findViewById(R.id.passAutoCompleteTextView);
        repassAutoCompleteTextView = findViewById(R.id.repassAutoCompleteTextView);
        saveButton = findViewById(R.id.saveButton);

        saveData = new SharePrefEncryption(this,filename);





    }

    public void addItem(View view) {

        String app = appAutoCompleteTextView.getText().toString();
        String account = accountAutoCompleteTextView.getText().toString();
        String pass = passAutoCompleteTextView.getText().toString();
        String repass = repassAutoCompleteTextView.getText().toString();
        if(repass.equals(pass)){
            saveData.addData(app,account,pass);
        }
        else{
            Toast.makeText(this,"The two Passwords don't match\n        Try again",Toast.LENGTH_SHORT).show();
            passAutoCompleteTextView.setText("");
            repassAutoCompleteTextView.setText("");
        }

    }

    public void fetchItem(View view) {

        String app = appAutoCompleteTextView.getText().toString();
        Toast.makeText(this,saveData.fetchData(app),Toast.LENGTH_SHORT).show();

    }
}