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

public class AddGroupActivity extends AppCompatActivity {
    Button btn_addGroup;
    Intent startNewActivity;
    TCPConnectionService TCPService;
    boolean isBound = false;
    static int userID_inAddGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group_page);
        userID_inAddGroups = getIntent().getIntExtra("userID",0);
        Intent intent =new Intent(AddGroupActivity.this,TCPConnectionService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);


        btn_addGroup = (Button) findViewById(R.id.add_new_group_button);


        btn_addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText groupname = (EditText) findViewById(R.id.new_group_name);
                if(isBound){
                    Log.d("DEBUG","add_group_cagirilcak");
                    Log.d("DEBUG","userID in add group: "+userID_inAddGroups);
                    Log.d("DEBUG","teamname in add group: "+groupname.getText().toString());

                    TCPService.add_group(userID_inAddGroups,groupname.getText().toString());
                    Log.d("DEBUG","add_group_cagirildi");
                    int result=Integer.parseInt(TCPService.getMessage());
                    if(result>0) {
                        Toast.makeText(AddGroupActivity.this,
                                "Successfully added", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                        Toast.makeText(AddGroupActivity.this,
                                "Groupname already exist",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MyLocalBinder bind= (MyLocalBinder) service;
            TCPService = bind.getService();
            Log.d("DEBUG","get service add_groups");
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
            Log.d("DEBUG","drop service ");
        }
    };
}
