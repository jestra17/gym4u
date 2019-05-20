package com.example.gym4u;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Announcement extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
//    MyDB db;
    public Button NewPostButton;
    public ImageView picturePost;
    public EditText newPost;
    private static final int galleryPick = 1;
    private Uri ImageUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);
        String date = getDate();
        String time = getTime();
       // db = new MyDB(this);
      //  showPosts();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //NewPostButton = (Button) findViewById(R.id.post_button);
        //picturePost = (ImageView) findViewById(R.id.postImage);
        //picturePost.setVisibility(View.INVISIBLE);
        //newPost = findViewById(R.id.postEditText);

        String id = FirebaseAuth.getInstance().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users/"+id+"/name");
        DatabaseReference refG = database.getReference("Users/"+id+"/gym");
        refG.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String gym = (String) dataSnapshot.getValue();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Announcement.this, Client_Home.class);
            startActivity(intent);
            //super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setting_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void OpenGallery(View view) {
        Intent galleryImage = new Intent();
        galleryImage.setAction(Intent.ACTION_GET_CONTENT);
        galleryImage.setType("image/*");
        startActivityForResult(galleryImage,galleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null){
            ImageUri = data.getData();
            picturePost.setVisibility(View.VISIBLE);
            picturePost.setImageURI(ImageUri);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_annoucements) {
            Intent intent = new Intent(Announcement.this, Announcement.class);
            startActivity(intent);
        } else if (id == R.id.nav_wall) {
            Intent intent = new Intent(Announcement.this, Wall.class);
            startActivity(intent);
        } else if (id == R.id.nav_gym) {
            Intent intent = new Intent(Announcement.this, Urgym.class);
            startActivity(intent);
        } else if (id == R.id.nav_heart) {
            Intent intent = new Intent(Announcement.this, Urheart.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

/*
    public void showPosts(){

        ArrayList postArr = new ArrayList();
        String table = "announcements";
        List<Postdata> posts = db.getEverything(table);
        for(Postdata p : posts){

            postArr.add(p);

        }
        postadapter adapter = new postadapter(this,R.layout.listviewscreen,postArr);
        ListView myListView = findViewById(R.id.List);
        myListView.setAdapter(adapter);


    }

*/
    public static String getDate(){
        Date date = new Date();
        //lower case h = 12 hr time, a = use AM/PM
        String strDateFormat = "MM/dd/yyyy";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    public static String getTime(){
        Date time = new Date();
        //lower case h = 12 hr time, a = use AM/PM
        String strTimeFormat = "h:mm a";
        DateFormat timeFormat = new SimpleDateFormat(strTimeFormat);
        String formattedTime = timeFormat.format(time);
        return formattedTime;

    }
/*
    public void UpdatePost(View view) {
        if(newPost.getText().toString().isEmpty()){
            Toast.makeText(this, "no post content", Toast.LENGTH_LONG).show();
            return;
        }
        else {
           // String post = newPost.getText().toString();
          //   newPost.setText("");
            String date = getDate();
            String time = getTime();
            String name = "Instructor";
            String table = "announcements";
            boolean result = db.betaInsert(table,name,post,date,time);
            if(result == true) {
                //db.view();
                //Toast.makeText(Announcement.this, "DOWNLOAD SUCCESSFUL", Toast.LENGTH_SHORT).show();
                showPosts();

            }else{
                Toast.makeText(Announcement.this, "POST UNSUCCESSFUL", Toast.LENGTH_SHORT).show();
            }

        }
    }



*/



}
