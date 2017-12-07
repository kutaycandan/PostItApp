package com.kutaycandan.postitapp;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
import android.support.annotation.Nullable;
import android.util.Log;

public class TCPConnectionService extends Service {
    private final IBinder binder = new MyLocalBinder();
    public static TCPClient client=new TCPClient();
    int mStartMode;


    //boolean MAllowRebind;

    public class MyLocalBinder extends Binder{
        TCPConnectionService getService(){
            return TCPConnectionService.this;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return mStartMode;
    }

    public void sign_up(String username,String email,String password){
        final String sign_up_username=username;
        final String sign_up_email = email;
        final String sign_up_password = password;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Login Cagirildi");
                client.sendMessage("sign_up-"+sign_up_username+"-"+sign_up_email+"-"+sign_up_password);
            }
        };
        new Thread(r).start();

    }
    public void log_in(String username,String password){
        final String log_in_username=username;
        final String log_in_password=password;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Login Cagirildi");
                client.sendMessage("log_in-"+log_in_username+"-"+log_in_password);
            }
        };
        new Thread(r).start();
    }
    public void get_groups(int userID){
        final int tmp = userID;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Get Groups Cagirildi");
                client.sendMessage("get_groups-"+tmp);
            }
        };
        new Thread(r).start();
    }

    public void add_group(int userID,String groupname){
        final int tmp = userID;
        final String tmp2 = groupname;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Add Group Cagirildi");
                client.sendMessage("add_group-"+tmp+"-"+tmp2);
            }
        };
        new Thread(r).start();
    }
    public void join_group(int userID,String groupname){
        final int tmp = userID;
        final String tmp2 = groupname;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Join Group Cagirildi");
                client.sendMessage("join_group-"+tmp+"-"+tmp2);
            }
        };
        new Thread(r).start();
    }
    public void is_master(int userID,String groupname){
        final int tmp = userID;
        final String tmp2 = groupname;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","is Master Cagirildi");
                client.sendMessage("check_master-"+tmp+"-"+tmp2);
            }
        };
        new Thread(r).start();
    }
    public void delete_group(int userID,String groupname){
        final int tmp = userID;
        final String tmp2 = groupname;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Delete Group Cagirildi");
                client.sendMessage("delete_group-"+tmp+"-"+tmp2);
            }
        };
        new Thread(r).start();
    }

    public void get_posts(String teamname){

        final String tmp2 = teamname;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Get Posts Cagirildi");
                client.sendMessage("get_posts-"+tmp2);
            }
        };
        new Thread(r).start();
    }
    public void add_post(String teamname, String postname ,String text){
        final String tmp = teamname;
        final String tmp2 = postname;
        final String tmp3 = text ;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Add Post Cagirildi");
                client.sendMessage("add_post-"+tmp+"-"+tmp2+"-"+tmp3);
            }
        };
        new Thread(r).start();
    }
    public void delete_post(String teamname, String postname){
        final String tmp = teamname;
        final String tmp2 = postname;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Delete Post Cagirildi");
                client.sendMessage("delete_post-"+tmp+"-"+tmp2);
            }
        };
        new Thread(r).start();
    }
    public void read_post(String teamname, String postname){
        final String tmp = teamname;
        final String tmp2 = postname;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Read Post Cagirildi");
                client.sendMessage("read_post-"+tmp+"-"+tmp2);
            }
        };
        new Thread(r).start();
    }

    public void update_post(String teamname, String postname ,String text){
        final String tmp = teamname;
        final String tmp2 = postname;
        final String tmp3 = text ;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Update Post Cagirildi");
                client.sendMessage("update_post-"+tmp+"-"+tmp2+"-"+tmp3);
            }
        };
        new Thread(r).start();
    }
    public void request_post_update(String teamname, String postname){
        final String tmp = teamname;
        final String tmp2 = postname;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Request Update Post Cagirildi");
                client.sendMessage("request_post_update-"+tmp+"-"+tmp2);
            }
        };
        new Thread(r).start();
    }
    public void release_post_update(String teamname, String postname){
        final String tmp = teamname;
        final String tmp2 = postname;

        Runnable r = new Runnable() {
            @Override
            public void run() {
                Log.d("DEBUG","Release Update Post Cagirildi");
                client.sendMessage("release_post_update-"+tmp+"-"+tmp2);
            }
        };
        new Thread(r).start();
    }




    public String getMessage(){
        return client.readMessage();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        client.destroyConnection();
        LogInActivity.isServiceUp=false;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                client.openConnection();
            }
        };
        new Thread(r).start();
        LogInActivity.isServiceUp=true;
    }
}
