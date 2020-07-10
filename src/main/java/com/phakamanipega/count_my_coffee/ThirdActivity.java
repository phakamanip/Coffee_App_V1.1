package com.phakamanipega.count_my_coffee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirdActivity extends AppCompatActivity {

    public  Button HomeScreeButton;
    public  TextView Screen, Screen2;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_third );


        SimpleDateFormat sfd = new SimpleDateFormat( "MM/dd  HH:mm" );
        final String date2 = sfd.format( new Date() );

        HomeScreeButton = (Button) findViewById( R.id.done );
        Screen = (TextView) findViewById( R.id.message );
        Screen2 = (TextView) findViewById( R.id.message2 );


        String thankyou = "Thanks for using our Coffee App Version 1. \n" +
                "Keep checking-in here for update.    ";

        String thankyou2 = "Send comments and suggestions on what u want to see added in future updates to      \n" +
                "myusefulapps1@gmail.com\n" + "\n" +
                "Thank you";

        Screen.setText( thankyou );
        Screen2.setText( thankyou2 );


        HomeScreeButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ToastUpdated();
            }

        } );
    }
        public void ToastUpdated(){

            Toast toast = Toast.makeText(ThirdActivity.this, "You're up to date  ", Toast.LENGTH_SHORT);
            toast.setGravity( Gravity.CENTER, 0,0 );
            toast.show();
        }



}