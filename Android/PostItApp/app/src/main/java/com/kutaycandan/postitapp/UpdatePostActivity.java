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

public class UpdatePostActivity extends AppCompatActivity {
    Button btn_update_post;
    EditText update_edittext;
    Intent startNewActivity;
    TCPConnectionService TCPService;
    boolean isBound = false;
    static String postname_inUpdatePost;
    static String teamname_inUpdatePost;
    String posttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_post_page);
        postname_inUpdatePost = getIntent().getStringExtra("postname");
        teamname_inUpdatePost = getIntent().getStringExtra("teamname");
        Log.d("DEBUG","postname in delete:"+postname_inUpdatePost);
        Log.d("DEBUG","teamname in delete:"+teamname_inUpdatePost);
        Intent intent =new Intent(UpdatePostActivity.this,TCPConnectionService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
        btn_update_post = (Button) findViewById(R.id.submit_update_post_button);
        update_edittext = (EditText) findViewById(R.id.update_post_edittext);
        btn_update_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_post();

            }
        });


    }

    public void update_post(){
        posttext = update_edittext.getText().toString();
        posttext = posttext.replace("\n","(n)");
        TCPService.update_post(teamname_inUpdatePost,postname_inUpdatePost,posttext);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCPService.release_post_update(teamname_inUpdatePost,postname_inUpdatePost);
        finish();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MyLocalBinder bind= (MyLocalBinder) service;
            TCPService = bind.getService();
            Log.d("DEBUG","get service update post ");
            isBound=true;
            getPostText();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
            Log.d("DEBUG","drop service update post ");
        }
    };
    public void getPostText(){
        TCPService.read_post(teamname_inUpdatePost,postname_inUpdatePost);
        String post = TCPService.getMessage();
        post=post.replace("(n)","\n");
        update_edittext.setText(post);

    }

}
