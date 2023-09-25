package com.example.texttransfer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    RecyclerView chatList;
    ChatAdapter adapter;
    private Button postDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*postDataBtn = findViewById(R.id.button1);*/
        chatList = findViewById(R.id.ChatList);
        chatList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter();
        chatList.setAdapter(adapter);
    }

    private void getChatData() {
        String URL = "https://api.binjie.fun/api/generateStream";
        RequestQueue requestQueue = Volley.newRequestQueue(this);/*
        StringEntity params
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, URL, )*/

    }
}