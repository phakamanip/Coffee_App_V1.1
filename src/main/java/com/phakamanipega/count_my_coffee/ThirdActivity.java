package com.phakamanipega.count_my_coffee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirdActivity extends AppCompatActivity {

    public  Button HomeScreeButton;
    public  TextView Screen;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        SimpleDateFormat sfd  = new SimpleDateFormat("MM/dd  HH:mm");
        final  String date2 = sfd.format(new Date());

        HomeScreeButton = (Button) findViewById(R.id.done) ;
        Screen = (TextView) findViewById(R.id.message);


        String  thankyou = "Thanks for using our App. \n" +
                "Keep checking here for update.     \n" +
                "Send comments and suggestions on what u want to see added in future updates to      \n" +
                "myCoffeeApp@gmail.com\n"+ "\n"+
                "Thank you";

        Screen.setText(thankyou);




        HomeScreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThirdActivity.this, MainActivity.class));
                finish();
            }

        });
}


}