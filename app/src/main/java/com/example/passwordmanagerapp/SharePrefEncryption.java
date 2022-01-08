package com.example.passwordmanagerapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


public class SharePrefEncryption {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharePrefEncryption(Context context, String filename) {

        editor = sharedPreferences.edit();

        try {
            MasterKey masterKey = new MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            sharedPreferences = EncryptedSharedPreferences.create(
                    context,
                    filename,
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );




        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void addData(String app, String account, String pass) {


        Set<String> details = new HashSet<String>();

        details.add(account);
        details.add(pass);

        editor.putStringSet(app,details);
        editor.apply();
    }

    public String fetchData(String app){
        SharedPreferences.Editor editor = sharedPreferences.edit();

//        Set<String> retrieved = editorgetStringSet(app,null);
//        retrieved.add(sharedPreferences.getString(app,null).toString());
//        Log.i("Data retrieved: ", retrieved.toString());
        return "hello";
    }
}
