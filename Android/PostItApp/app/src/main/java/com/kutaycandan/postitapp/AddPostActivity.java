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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.kutaycandan.postitapp.TCPConnectionService.MyLocalBinder;

public class AddPostActivity extends AppCompatActivity {
    Button btn_addPost;
    EditText postname;
    EditText text;
    Intent startNewActivity;
    TCPConnectionService TCPService;
    boolean isBound = false;
    static String teamname_inAddPost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post_page);
        teamname_inAddPost = getIntent().getStringExtra("teamname");
        Intent intent =new Intent(AddPostActivity.this,TCPConnectionService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);


        btn_addPost = (Button) findViewById(R.id.submit_new_post_button);


        btn_addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postname = (EditText) findViewById(R.id.new_postname);
                text = (EditText) findViewById(R.id.new_posttext);
                if(isBound){
                    Log.d("DEBUG","add_post_cagirilcak");

                    String ss =text.getText().toString();
                    ss=ss.replace("\n","(n)");
                    TCPService.add_post(teamname_inAddPost,postname.getText().toString(),
                            ss);
                    Log.d("DEBUG","add_post_cagirildi");
                    int result=Integer.parseInt(TCPService.getMessage());
                    if(result>0) {
                        Toast.makeText(AddPostActivity.this,
                                "Successfully created", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                        Toast.makeText(AddPostActivity.this,
                                "This group has already use this postname",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MyLocalBinder bind= (MyLocalBinder) service;
            TCPService = bind.getService();
            Log.d("DEBUG","get service add_post");
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
            Log.d("DEBUG","drop service ");
        }
    };
}
