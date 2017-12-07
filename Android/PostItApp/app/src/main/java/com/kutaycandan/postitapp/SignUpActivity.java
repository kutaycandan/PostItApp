package com.kutaycandan.postitapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.kutaycandan.postitapp.TCPConnectionService.MyLocalBinder;

/**
 * Created by kutay on 4.12.2017.
 */

public class SignUpActivity extends AppCompatActivity {
    Button btn_register;
    Button btn_link_login;
    EditText username;
    EditText password;
    EditText email;
    Intent startNewActivity;

    TCPConnectionService TCPService;
    boolean isBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        Intent i =new Intent(SignUpActivity.this,TCPConnectionService.class);
        bindService(i,connection, Context.BIND_AUTO_CREATE);

        btn_link_login = (Button) findViewById(R.id.signup_btnLinkToLoginScreen);
        btn_register = (Button) findViewById(R.id.signup_btnRegister);

        btn_link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLogin();
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    username = (EditText) findViewById(R.id.signup_username);
                    email = (EditText) findViewById(R.id.signup_email) ;
                    password = (EditText) findViewById(R.id.signup_password);

                    if(isBound) {
                        TCPService.sign_up(username.getText().toString(),
                                email.getText().toString(), password.getText().toString());
                        int result = Integer.parseInt(TCPService.getMessage());

                        if(result>0){
                            Toast.makeText(SignUpActivity.this,
                                    "User created successfully",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else
                            Toast.makeText(SignUpActivity.this,
                                "username or email is already taken",Toast.LENGTH_LONG).show();
                    }
            }
        });
    }
    public void goLogin(){
        startNewActivity = new Intent(this,LogInActivity.class);
        startActivity(startNewActivity);

    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MyLocalBinder bind= (MyLocalBinder) service;
            TCPService = bind.getService();
            isBound=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
        }
    };

}
