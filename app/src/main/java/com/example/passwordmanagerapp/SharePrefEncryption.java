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
    private SharedPreferences.Editor sharedPreferencesForAppIds;
    private SharedPreferences.Editor editor;
    private static final String appIds = "Apps_list";

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


    public AppItem fetchData(String key) throws JSONException {

            JSONArray jsonArray = new JSONArray(sharedPreferencesForAppData.getString(key, "[]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.i("ArrayList", String.valueOf(i) + " **-** "+ (String) jsonArray.get(i));
            }
            String id = key;
            String appName = (String) jsonArray.get(0);
            String account = (String) jsonArray.get(1);
            String password = (String) jsonArray.get(2);
            AppItem item = new AppItem(id,appName,account,password);

        return item;
    }

    public Map<String,?> getAll() {
        Map<String,?> entries = sharedPreferencesForAppData.getAll();
        return (Map<String, ?>) entries;
    }

}
