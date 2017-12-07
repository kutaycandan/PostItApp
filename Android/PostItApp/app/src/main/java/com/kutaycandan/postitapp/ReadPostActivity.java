package com.kutaycandan.postitapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kutaycandan.postitapp.TCPConnectionService.MyLocalBinder;

public class ReadPostActivity extends AppCompatActivity {

    TextView post_text;

    TCPConnectionService TCPService;
    boolean isBound = false;
    static String teamname_inReadPost;
    static String postname_inReadPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.read_post_page);
        postname_inReadPost = getIntent().getStringExtra("postname");
        teamname_inReadPost = getIntent().getStringExtra("teamname");
        Log.d("DEBUG","postname in read:"+postname_inReadPost);
        Log.d("DEBUG","teamname in read:"+teamname_inReadPost);
        Intent intent =new Intent(ReadPostActivity.this,TCPConnectionService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);



    }

    public void read_post(){
        TCPService.read_post(teamname_inReadPost,postname_inReadPost);
        String s=TCPService.getMessage();
        post_text = (TextView) findViewById(R.id.read_post_textview);
        s=s.replace("(n)","\n");
        post_text.setText(s);

    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MyLocalBinder bind= (MyLocalBinder) service;
            TCPService = bind.getService();
            Log.d("DEBUG","get service delete posts");
            isBound=true;
            read_post();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
            Log.d("DEBUG","drop service ");
        }
    };


}
