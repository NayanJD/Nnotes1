package com.dev.nayanj.nnotes1;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] notes;
    private ArrayList<String> note=new ArrayList<>();
    private  boolean present=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        String[] files=getApplicationContext().fileList();

        for(String s:files) {
            Log.i("files", s);
            if(s=="notes")
                present=true;
        }
        try{
        if(!present){
            FileOutputStream fout= openFileOutput("notes", Context.MODE_PRIVATE);
        }}
        catch(FileNotFoundException f){
            Log.i("error","file not found");
        }
        present=false;
        try {
            FileInputStream fin = openFileInput("notes");
            Log.i("Success","File opening worked");
        }
        catch(FileNotFoundException f){
            Log.i("Error","File not found during readingggggggggggggggggggggggggggggggggggggggggg");
        }
        String read="";
        try(BufferedReader buf=new BufferedReader(new InputStreamReader(openFileInput("notes")))){
            while((read=buf.readLine())!=null){
                if(read.equals("//")) {
                    read = buf.readLine();
                    if (read != null) {
                        if(read.length()<30)
                            note.add(read);
                        else
                            note.add(read.substring(0,30)+"...");
                    }
                    else
                        note.add("");
                    buf.readLine();
                    Log.i("content",read);

                }
            }
        }
        catch(IOException e){
            Log.i("error","file cant be open from MainActivity");
        }


        notes=note.toArray(new String[0]);
        mAdapter = new NotesAdapter(notes);
        mRecyclerView.setAdapter(mAdapter);

        //setUpNotesSelectedListener();
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(MainActivity.this, NotesActivity.class);
                        i.putExtra("position",position);
                        startActivityForResult(i,10);
                        Log.i("Success","It worked");
                    }
                })
        );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*View about=(ClipData.Item) findViewById(R.id.action_settings);

        about.setOnClickListener((view)->{
            Intent i = new Intent(MainActivity.this, NotesActivity.class);
        });*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, NotesActivity.class);
                i.putExtra("position",-1);
                startActivityForResult(i,10);
            }
        });

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/

        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
    }

    /*public void setUpNotesSelectedListener(){
        mRecyclerView.setOnClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the detail view passing book as an extra
                //Intent intent = new Intent(MainActivity.this, NotesActivity.class);
                Log.i("Success","It worked");
                //startActivity(intent);
            }
        });
    }*/


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Intent i = new Intent(MainActivity.this, About.class);
            i.putExtra("position",-1);
            startActivityForResult(i,10);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //@SuppressWarnings("StatementWithEmptyBody")
    //@Override
    /*public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //mRecyclerView.clear();
        note.clear();
        String read="";
        String curr="";
        char[] ch=new char[2];
        try(BufferedReader buf=new BufferedReader(new InputStreamReader(openFileInput("notes")))) {
            while ((read=buf.readLine())!=null) {
                while(read.length()!=0){
                    curr=read.substring(0,read.indexOf("//"));
                    read=read.substring(read.indexOf("//")+2);
                    note.add(curr);
                    Log.i("contents in main",curr);
                }


            }
        }
        catch(IOException e){
            Log.i("error","file cant be open from MainActivity");
        }
        //for(String s:note)
         //   Log.i("checking note in main", s);
        //mAdapter.notifyDataSetChanged();
        notes=note.toArray(new String[0]);
        mAdapter = new NotesAdapter(notes);
        mRecyclerView.setAdapter(mAdapter);
    }
}
