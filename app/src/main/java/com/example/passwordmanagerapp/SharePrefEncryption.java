package com.example.passwordmanagerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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


//            sharedPreferencesForAppIds = context.getSharedPreferences(appIds, Context.MODE_PRIVATE).edit();


        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }


    }

    public void addData(String app, String account, String pass) {


        Set<String> details = new HashSet<String>();

        AtomicLong id = new AtomicLong();


//        sharedPreferencesForAppIds.putString(id.toString(),app);


        details.add(account);
        details.add(pass);
        editor = sharedPreferencesForAppData.edit();
        editor.putStringSet(app,details);
        editor.apply();
        Log.i("Data: ","Stored");
    }


    public void fetchData(){
//        SharedPreferences sh = sharedPreferencesForAppData;

//        Set<String> retrieved = sh.getStringSet(, new HashSet<String>());
//        ArrayList<String> list = new ArrayList<String>(retrieved);
//        retrieved.add(sharedPreferences.getString(app,null).toString());
        Map<String,?> entries = sharedPreferencesForAppData.getAll();
        Integer count = sharedPreferencesForAppData.getAll().size();
        Set<String> keys = entries.keySet();
        for(String item: keys){
            Log.i("Data retrieved: ", item);
            Log.i("file items amount: ",count.toString());
        }

//        return list;
    }

//    public ArrayList<String> fetchData(String app){
//        SharedPreferences sh = sharedPreferencesForAppData;
//
//        Set<String> retrieved = sh.getStringSet(app, new HashSet<String>());
//        ArrayList<String> list = new ArrayList<String>(retrieved);
////        retrieved.add(sharedPreferences.getString(app,null).toString());
//        for(String item: list){
//            Log.i("Data retrieved: ", item);
//        }
//
//        return list;
//    }
}
