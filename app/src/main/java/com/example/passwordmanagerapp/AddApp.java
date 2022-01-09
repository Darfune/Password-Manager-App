package com.example.passwordmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

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
        List<String> data = new ArrayList<>();
        data.add(app);
        Log.i("Collection: ", String.valueOf(data));
        data.add(account);
        Log.i("Collection: ", String.valueOf(data));
        data.add(pass);
        Log.i("Collection: ", String.valueOf(data));
        if(repass.equals(pass)){
            saveData.addData(data);
        }
        else{
            Toast.makeText(this,"The two Passwords don't match\n\t\t\tTry again",Toast.LENGTH_SHORT).show();
            passAutoCompleteTextView.setText("");
            repassAutoCompleteTextView.setText("");
        }

    }

}