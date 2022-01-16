package com.example.passwordmanagerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import com.example.passwordmanagerapp.Models.AppItem;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;


public class SharePrefEncryption {


    private SharedPreferences sharedPreferencesForAppData;

    public SharePrefEncryption(Context context, String filename) {

        try {
            MasterKey masterKeyData = new MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferencesForAppData = EncryptedSharedPreferences.create(
                    context,
                    filename,
                    masterKeyData,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }


    }

    public void addData(List<String> data) {

        SharedPreferences.Editor editor;

        JSONArray dataInJsonArray = new JSONArray(data);

        UUID idPartOne = UUID.randomUUID();
        UUID idPartTwo = UUID.randomUUID();
        String id =  String.valueOf(idPartOne) +  String.valueOf(idPartTwo);
        Log.i("New item's id: ", id);
        editor = sharedPreferencesForAppData.edit();
        editor.putString(id, String.valueOf(dataInJsonArray));
        editor.apply();
        Log.i("Data: ","Stored");
    }

    public void addPinCode(String pin){

        SharedPreferences.Editor editor;

        editor = sharedPreferencesForAppData.edit();
        editor.putString("Pin Code", pin);
        editor.apply();

    }

    public void biometricsVerification(Boolean answer){
        SharedPreferences.Editor editor;

        editor = sharedPreferencesForAppData.edit();
        editor.putBoolean("Biometrics", answer);
        editor.apply();
    }

    public AppItem fetchData(String key) throws JSONException {

        JSONArray jsonArray = new JSONArray(sharedPreferencesForAppData.getString(key, "[]"));
        String id = key;
        String appName = (String) jsonArray.get(0);
        String account = (String) jsonArray.get(1);
        String password = (String) jsonArray.get(2);
        AppItem item = new AppItem(id,appName,account,password);

        return item;
    }

    public String fetchPin(){
        return sharedPreferencesForAppData.getString("Pin Code", "");
    }

    public Boolean isBiometricsAllowed(){
        return sharedPreferencesForAppData.getBoolean("Biometrics", false);
    }

    public Map<String,?> getAll() {
        Map<String,?> entries = sharedPreferencesForAppData.getAll();
        return (Map<String, ?>) entries;
    }

}
