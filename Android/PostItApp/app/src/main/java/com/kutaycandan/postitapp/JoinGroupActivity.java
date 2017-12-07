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

import static com.kutaycandan.postitapp.LogInActivity.userID_inLogin;

public class JoinGroupActivity extends AppCompatActivity {
    Button btn_joinGroup;
    Intent startNewActivity;
    TCPConnectionService TCPService;
    boolean isBound = false;
    static int userID_inJoinGroups;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_group_page);
        userID_inJoinGroups = getIntent().getIntExtra("userID",0);
        Intent intent =new Intent(JoinGroupActivity.this,TCPConnectionService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);


        btn_joinGroup = (Button) findViewById(R.id.join_new_group_button);


        btn_joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText groupname = (EditText) findViewById(R.id.join_group_name);
                if(isBound){
                    Log.d("DEBUG","join_group_cagirilcak");
                    Log.d("DEBUG","userID in add group: "+userID_inJoinGroups);
                    Log.d("DEBUG","teamname in add group: "+groupname.getText().toString());

                    TCPService.join_group(userID_inJoinGroups,groupname.getText().toString());
                    Log.d("DEBUG","join_group_cagirildi");
                    int result=Integer.parseInt(TCPService.getMessage());
                    if(result==1) {

                        Toast.makeText(JoinGroupActivity.this,
                                "Successfully joined", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else if(result==-2)
                        Toast.makeText(JoinGroupActivity.this,
                                "You are already in the group",Toast.LENGTH_LONG).show();
                    else if(result==-1)
                        Toast.makeText(JoinGroupActivity.this,
                                "Groupname does not exist",Toast.LENGTH_LONG).show();
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
