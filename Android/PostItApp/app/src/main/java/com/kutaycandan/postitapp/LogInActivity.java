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
import android.widget.Toast;

import com.kutaycandan.postitapp.TCPConnectionService.MyLocalBinder;

public class LogInActivity extends AppCompatActivity {
    Button btn_login;
    Button btn_link_signup;
    EditText username;
    EditText password;
    Intent startNewActivity;
    public static int userID_inLogin;


    public static boolean isServiceUp=false;
    Intent serviceIntent;
    TCPConnectionService TCPService;
    boolean isBound = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        if(!isServiceUp){
            upService();
        }
        btn_link_signup = (Button) findViewById(R.id.login_btnLinkToSignUpScreen);
        btn_login = (Button) findViewById(R.id.login_btn);
        Intent i =new Intent(LogInActivity.this,TCPConnectionService.class);
        bindService(i,connection, Context.BIND_AUTO_CREATE);
        btn_link_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSignUp();

            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = (EditText) findViewById(R.id.login_username);
                password = (EditText) findViewById(R.id.login_password);
                Log.d("DEBUG",username.getText().toString());
                Log.d("DEBUG",password.getText().toString());
                Log.d("DEBUG"," "+isBound);
                if(isBound){
                    TCPService.log_in(username.getText().toString(),password.getText().toString());

                    userID_inLogin=Integer.parseInt(TCPService.getMessage());
                    Log.d("DEBUG","userID"+userID_inLogin);

                    if(userID_inLogin>0)
                        goGroups();
                    else
                        Toast.makeText(LogInActivity.this,
                                "Username or Password is wrong",Toast.LENGTH_LONG).show();
                }


            }
        });
    }
    public void goSignUp(){
        startNewActivity = new Intent(this,SignUpActivity.class);
        startActivity(startNewActivity);
    }
    public void goGroups(){
        startNewActivity = new Intent(this,GroupsActivity.class);
        startNewActivity.putExtra("userID",userID_inLogin);
        startActivity(startNewActivity);
    }
    public void upService(){

        serviceIntent = new Intent(LogInActivity.this,TCPConnectionService.class);
        startService(serviceIntent);

    }
    private ServiceConnection connection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder service) {
                    MyLocalBinder bind= (MyLocalBinder) service;
                    TCPService = bind.getService();
                    Log.d("DEBUG","get service ");
                    isBound=true;
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    isBound=false;
                    Log.d("DEBUG","drop service ");
        }
    };
}
