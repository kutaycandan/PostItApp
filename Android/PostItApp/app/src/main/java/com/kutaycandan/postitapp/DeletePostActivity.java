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

public class DeletePostActivity extends AppCompatActivity {
    Button btn_DeletePost;
    TextView delete_text;
    Intent startNewActivity;
    TCPConnectionService TCPService;
    boolean isBound = false;
    static String teamname_inDeletePost;
    static String postname_inDeletePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_post_page);
        postname_inDeletePost = getIntent().getStringExtra("postname");
        teamname_inDeletePost = getIntent().getStringExtra("teamname");
        Log.d("DEBUG","postname in delete:"+postname_inDeletePost);
        Log.d("DEBUG","teamname in delete:"+teamname_inDeletePost);
        Intent intent =new Intent(DeletePostActivity.this,TCPConnectionService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);

        btn_DeletePost = (Button) findViewById(R.id.delete_the_post_button);
        delete_text = (TextView) findViewById(R.id.delete_post_textview);
        delete_text.setText("Do you really want to delete "+postname_inDeletePost+
                " post in group "+teamname_inDeletePost+"?");

        btn_DeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_post();

            }
        });



    }

    public void delete_post(){
        TCPService.delete_post(teamname_inDeletePost,postname_inDeletePost);

        finish();
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MyLocalBinder bind= (MyLocalBinder) service;
            TCPService = bind.getService();
            Log.d("DEBUG","get service delete posts");
            isBound=true;

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
            Log.d("DEBUG","drop service ");
        }
    };


}
