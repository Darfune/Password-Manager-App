package com.example.passwordmanagerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText searchbarEditText;
    private AppItemAdapter appItemAdapter;
    private List<AppItem> listOfItems;
    private AppItemAdapter adapter;
    private FloatingActionButton addItemFloatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addItemFloatingActionButton = (FloatingActionButton) findViewById(R.id.addItemFloatingActionButton);

        listOfItems = new LinkedList<>();
        AppItem item1 = new AppItem("1","Hello", "World");
        listOfItems.add(item1);
        adapter = new AppItemAdapter(this,listOfItems);

        RecyclerView itemRecyclerView = findViewById(R.id.itemRecyclerView);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemRecyclerView.setAdapter(adapter);

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

    public void addItem(View view) {
//        Log.i("In addItem function: ", "test");
//        AppItem item2 = new AppItem("2","Hello 2", "World 2");
//        listOfItems.add(item2);
//        adapter.notifyDataSetChanged();
        startActivity(new Intent(MainActivity.this, AddApp.class));

    }
}