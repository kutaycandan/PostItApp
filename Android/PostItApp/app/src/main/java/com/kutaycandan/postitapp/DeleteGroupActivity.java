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

public class DeleteGroupActivity extends AppCompatActivity {
    Button btn_DeleteGroup;
    TextView delete_text;
    Intent startNewActivity;
    TCPConnectionService TCPService;
    boolean isBound = false;
    static int userID_inDeleteGroup;
    String teamname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_group_page);
        userID_inDeleteGroup = getIntent().getIntExtra("userID",0);
        teamname = getIntent().getStringExtra("teamname");
        Intent intent =new Intent(DeleteGroupActivity.this,TCPConnectionService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
        btn_DeleteGroup = (Button) findViewById(R.id.delete_the_group_button);
        delete_text = (TextView) findViewById(R.id.delete_group_textview);
        btn_DeleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_group();

            }
        });


    }

    public void delete_group(){
        TCPService.delete_group(userID_inDeleteGroup,teamname);
        finish();
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MyLocalBinder bind= (MyLocalBinder) service;
            TCPService = bind.getService();
            Log.d("DEBUG","get service groups");
            isBound=true;
            isMaster();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
            Log.d("DEBUG","drop service ");
        }
    };
    public void isMaster(){
        TCPService.is_master(userID_inDeleteGroup,teamname);
        int result = Integer.parseInt(TCPService.getMessage());
        if(result==1){
            delete_text.setText("You are master of group: "+teamname+". If you delete this group, everyone will " +
                    "be kicked and every post will be deleted. Do you really want to delete this group?");
        }
        else{
            delete_text.setText("Do you really want to exit on group: "+teamname+"?");
        }

    }

}
