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

public class PostsActivity extends AppCompatActivity {
    ImageButton btn_addPosts;
    Intent startNewActivity;
    TCPConnectionService TCPService;
    boolean isBound = false;
    static int userID_inPosts;
    String posts;
    String referenced_post_name;
    static String groupname_inPosts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posts_page);
        userID_inPosts = getIntent().getIntExtra("userID",0);
        groupname_inPosts = getIntent().getStringExtra("teamname");
        Log.d("DEBUG","User ID "+userID_inPosts);
        Log.d("DEBUG","group name is : "+groupname_inPosts);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Intent intent =new Intent(PostsActivity.this,TCPConnectionService.class);
        bindService(intent,connection, Context.BIND_AUTO_CREATE);
        btn_addPosts = (ImageButton) findViewById(R.id.add_post_button);

        btn_addPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goAddPosts();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isBound)
            unbindService(connection);

    }

    public void goAddPosts(){
        startNewActivity = new Intent(this,AddPostActivity.class);
        //startNewActivity.putExtra("userID",userID_inPosts);
        startNewActivity.putExtra("teamname",groupname_inPosts);
        startActivity(startNewActivity);

    }

    public void get_posts(){
        TCPService.get_posts(groupname_inPosts);
        posts = TCPService.getMessage();
        if(!posts.equals("")) {
            Log.d("DEBUG", "posts: " + posts);
            String[] postsArr = posts.split("/");

            ListAdapter postAdapter =
                    new PostRowAdapter(this, postsArr);
            final ListView postListView = (ListView) findViewById(R.id.post_listview);
            postListView.setAdapter(postAdapter);
            postListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    referenced_post_name = (String) postListView.getItemAtPosition(i);
                    goReadPosts();
                }
            });
        }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            MyLocalBinder bind= (MyLocalBinder) service;
            TCPService = bind.getService();
            Log.d("DEBUG","get service posts");
            isBound=true;
            get_posts();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBound=false;
            Log.d("DEBUG","drop service posts");
        }
    };
    public void goDeletePosts(){
        startNewActivity = new Intent(this,DeletePostActivity.class);
        startNewActivity.putExtra("postname",referenced_post_name);
        startNewActivity.putExtra("teamname",groupname_inPosts);
        startActivity(startNewActivity);
    }
    public void goUpdatePosts(){
        TCPService.request_post_update(groupname_inPosts,referenced_post_name);
        int request = Integer.parseInt(TCPService.getMessage());
        if (request==1) {
            startNewActivity = new Intent(this, UpdatePostActivity.class);
            startNewActivity.putExtra("postname", referenced_post_name);
            startNewActivity.putExtra("teamname", groupname_inPosts);
            startActivity(startNewActivity);
        }
        else if(request ==-1){
            Toast.makeText(PostsActivity.this,
                    "Post: "+referenced_post_name+" is now updated by other user. Please try later.",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void goReadPosts(){
        startNewActivity = new Intent(this,ReadPostActivity.class);
        startNewActivity.putExtra("postname",referenced_post_name);
        startNewActivity.putExtra("teamname",groupname_inPosts);
        startActivity(startNewActivity);
    }
    public class PostRowAdapter extends ArrayAdapter<String> {

        public PostRowAdapter(Context context, String[] groupNames) {
            super(context,R.layout.custom_group_row,groupNames);

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater postInflater = LayoutInflater.from(getContext());
            View customView = postInflater.inflate(R.layout.custom_post_row,parent,false);
            ImageButton button_delete = (ImageButton) customView.findViewById(R.id.delete_post_button);
            ImageButton button_update = (ImageButton) customView.findViewById(R.id.update_post_button);
            String postName = getItem(position);
            TextView textView = (TextView) customView.findViewById(R.id.postname_text);

            textView.setText(postName);
            button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    referenced_post_name=getItem(position);
                    goDeletePosts();
                }
            });
            button_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    referenced_post_name=getItem(position);
                    goUpdatePosts();
                }
            });
            return customView;


        }

    }


}
