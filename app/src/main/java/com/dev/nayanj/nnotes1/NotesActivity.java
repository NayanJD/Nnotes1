package com.dev.nayanj.nnotes1;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class NotesActivity extends AppCompatActivity {
    private String strValue="";
    EditText simpleEditText;
    int value=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        simpleEditText = (EditText) findViewById(R.id.editText);

        Bundle extras = getIntent().getExtras();
        value=extras.getInt("position");
        Log.i("Position value",":"+value);
        if(value!=-1){
            String curr="";
            String read="";
            int count=0;
            try(BufferedReader buf=new BufferedReader(new InputStreamReader(openFileInput("notes")))){
                while((read=buf.readLine())!=null){
                    while(read.length()!=0){
                        if(count==value){
                            curr=read.substring(0,read.indexOf("//"));
                            simpleEditText.setText(curr, TextView.BufferType.EDITABLE);
                            //Log.i("contents",read);
                        }
                        read=read.substring(read.indexOf("//")+2);
                        count++;
                    }


                }
            }
            catch(IOException e){}
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabsave);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strValue = simpleEditText.getText().toString();

            }
        });
        /*final EditText simpleEditText = (EditText) findViewById(R.id.editText);
        simpleEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strValue = simpleEditText.getText().toString();
            }
        });*/

    }

    @Override
    public void finish(){
        if(strValue==""){
            strValue = simpleEditText.getText().toString();
        }

        Intent data=new Intent();
        setResult(10, data);
        Log.i("value",strValue);

        if(value!=-1){
            String read="";
            String curr="";
            int count=0;
            try (BufferedReader bufread = new BufferedReader(new InputStreamReader(openFileInput("notes")));
                 BufferedWriter bufwrite = new BufferedWriter(new OutputStreamWriter(openFileOutput("tempnotes", Context.MODE_PRIVATE)))) {
                //FileOutputStream fout = openFileOutput("notes", Context.MODE_APPEND);
                Log.i("Success", "File opening worked in NotesActivity");
                while((read=bufread.readLine())!=null){
                    while(read.length()!=0){
                        if(count==value && strValue.length()>0){

                                bufwrite.write(strValue+"//",0,strValue.length()+2);
                                //curr=read.substring(0,read.indexOf("//"));
                                //read=read.substring(read.indexOf("//")+2);
                        }
                        else {
                            curr = read.substring(0, read.indexOf("//"));
                            bufwrite.write(curr+"//",0,curr.length()+2);
                        }
                        read=read.substring(read.indexOf("//")+2);
                        count++;
                        }
                }
                deleteFile("notes");
                String[] files=getApplicationContext().fileList();
                File d=getFilesDir();
                File original=new File(d,"notes");
                File temp=new File(d,"tempnotes");
                temp.renameTo(original);
            } catch (IOException f) {
                Log.i("Error", "File not found during readingggggggggggggggggggggggggggggggggggggggggg");
            }
        }
        else {
            try (BufferedWriter buf = new BufferedWriter(new OutputStreamWriter(openFileOutput("notes", Context.MODE_APPEND)))) {
                //FileOutputStream fout = openFileOutput("notes", Context.MODE_APPEND);
                Log.i("Success", "File opening worked in NotesActivity");
                    if(strValue.length()>0)
                        buf.write(strValue+"//", 0, strValue.length()+2);


            } catch (IOException f) {
                Log.i("Error", "File not found during readingggggggggggggggggggggggggggggggggggggggggg");
            }
        }

        String read="";
        try(BufferedReader buf=new BufferedReader(new InputStreamReader(openFileInput("notes")))){
            while((read=buf.readLine())!=null){
                    //read = buf.readLine();

                    if(read.length()==0)
                        read="1111";
                    Log.i("contents",read);

                }
        }
        catch(IOException e){}
        super.finish();
    }
}
