package com.example.user.myrecorder1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ListPage extends AppCompatActivity {
    RecyclerView recyclerView;
    List<CallDetails> list;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_page);
        DBhandler db=new DBhandler(this);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerV);
        list= new DBmanage(this).getFullList();
        Collections.reverse(list);
        adapter=new Adapter(list,this);
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }


}
