package com.kutaycandan.postitapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kutaycandan.postitapp.TCPConnectionService.MyLocalBinder;

import static java.lang.Thread.sleep;

/**
 * Created by kutay on 5.12.2017.
 */

public class GroupsActivity extends AppCompatActivity {
    ImageButton btn_addGroup;
    ImageButton btn_joinGroup;
    Intent startNewActivity;
    TCPConnectionService TCPService;
    boolean isBound = false;
    static int userID_inGroups;
    String groups;
    String referenced_group_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_page);
        userID_inGroups = getIntent().getIntExtra("userID",0);
        Log.d("DEBUG","User ID "+userID_inGroups);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent =new Intent(GroupsActivity.this,TCPConnectionService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
        btn_addGroup = (ImageButton) findViewById(R.id.add_group_button);
        btn_joinGroup = (ImageButton) findViewById(R.id.join_group_button);

        btn_addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAddGroup();
            }
        });
        btn_joinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goJoinGroup();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isBound)
            unbindService(connection);

    }

    public void goAddGroup(){
        startNewActivity = new Intent(this,AddGroupActivity.class);
        startNewActivity.putExtra("userID",userID_inGroups);
        startActivity(startNewActivity);

    }
    public void goJoinGroup(){
        startNewActivity = new Intent(this,JoinGroupActivity.class);
        startNewActivity.putExtra("userID",userID_inGroups);
        startActivity(startNewActivity);
    }
    public void goPosts(){
        startNewActivity = new Intent(this,PostsActivity.class);
        startNewActivity.putExtra("teamname",referenced_group_name);
        startNewActivity.putExtra("userID",userID_inGroups);
        startActivity(startNewActivity);
    }
    public void get_groups(){
        TCPService.get_groups(userID_inGroups);
        groups = TCPService.getMessage();
        if(!groups.equals("")) {
            Log.d("DEBUG", "groups: " + groups);
            String[] groupArr = groups.split("/");


            String[] groupnames = new String[groupArr.length];
            for (int x = 0; x < groupArr.length; x++) {
                groupnames[x] = groupArr[x];
            }
            ListAdapter groupAdapter =
                    new GroupRowAdapter(this, groupnames);
            final ListView groupListView = (ListView) findViewById(R.id.group_listview);
            groupListView.setAdapter(groupAdapter);
            groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    //Toast.makeText(GroupsActivity.this,"List item name : " + String.valueOf(i), Toast.LENGTH_LONG).show();
                    referenced_group_name = (String) groupListView.getItemAtPosition(i);
                    goPosts();

                }
            });
        }
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MyLocalBinder bind= (MyLocalBinder) service;
            TCPService = bind.getService();
            Log.d("DEBUG","get service groups");
            isBound=true;
            get_groups();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
            Log.d("DEBUG","drop service ");
        }
    };
    public void goDelete(){
        startNewActivity = new Intent(this,DeleteGroupActivity.class);
        startNewActivity.putExtra("teamname",referenced_group_name);
        startNewActivity.putExtra("userID",userID_inGroups);
        startActivity(startNewActivity);
    }
    public class GroupRowAdapter extends ArrayAdapter<String> {

        public GroupRowAdapter(Context context, String[] groupNames) {
            super(context,R.layout.custom_group_row,groupNames);

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater groupInflater = LayoutInflater.from(getContext());
            View customView = groupInflater.inflate(R.layout.custom_group_row,parent,false);

            String groupName = getItem(position);
            TextView textView = (TextView) customView.findViewById(R.id.groupname_text);
            ImageButton button = (ImageButton) customView.findViewById(R.id.delete_group_button);
            textView.setText(groupName);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = "Position:"+position+" is deleted";
                    //Toast.makeText(getContext(),name,Toast.LENGTH_LONG).show();
                    referenced_group_name=getItem(position);
                    goDelete();
                }
            });
            return customView;


        }

    }


}
