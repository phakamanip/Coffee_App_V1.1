package com.phakamanipega.count_my_coffee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    public  Button UPDATEBtn, HOMEbtn;
    public  TextView Screen, Screen2;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_settings);
        UIVIEWS();

        String thankyou = "Thanks for using our Coffee App Version 1. \n" + "Keep checking-in here for updates.    ";
        Screen.setText( thankyou );

        String ContactUs_At = "Send comments and suggestions on what u want to see added in future updates to      \n" + "myusefulapps1@gmail.com " ;
        Screen2.setText( ContactUs_At );


        UPDATEBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ToastUpdated();
            }

        });


        HOMEbtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, CalculatorActivity.class   );
                startActivity(intent );}
        });

    }
    public void ToastUpdated(){
            Toast toast = Toast.makeText(SettingsActivity.this, "You're up to date  ", Toast.LENGTH_SHORT);
            toast.setGravity( Gravity.CENTER, 0,0 );
            toast.show();
    }


    public void UIVIEWS() {
        HOMEbtn = (Button) findViewById( R.id.returnHome );
        UPDATEBtn = (Button) findViewById( R.id.done );
        Screen = (TextView) findViewById( R.id.message );
        Screen2 = (TextView) findViewById( R.id.message2 );
    }

}
