package com.phakamanipega.count_my_coffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.String.valueOf;


public class CalculatorActivity extends Activity  {

    public Button SETTINGSPAGEbtn;
    public Button DATAVIEWSbtn;
    public Button PAYbtn;
    public EditText num2;
    public TextView num1;
    public TextView result;
    private Button clear;
    private Button zero;
    private Button one;
    private Button two;
    private Button three;
    private Button four;
    private Button five;
    private Button six;
    private Button seven;
    private Button eight;
    private Button nine;
    public static final String TAG = "CalculatorActivity";

    DataBaseHelper dataBaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // ToastWelcome(); onUpdate, let Welcome toast appear only once when app is first opened
        SetupUIViews();
        CalculatorOnClickButtons();


        //functions performed when transactions are typed in and "pay" is pressed
        PAYbtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
          try {
                          //Assigning patterns for Date formats;
           final SimpleDateFormat MonthDayFormat = new SimpleDateFormat(" MM.dd ");
           final SimpleDateFormat DayOfWeekFormat = new SimpleDateFormat("D");
           final SimpleDateFormat MonthOfYearFormat = new SimpleDateFormat( "L" );
           final SimpleDateFormat WeekOfYearFormat = new SimpleDateFormat( "w" );
           final DecimalFormat decimalFormat = new DecimalFormat("0.00 ");

                     //Converting SimpleDateFormat to String;
           final  String weekdayString = DayOfWeekFormat.format(new Date());
           final  String monthString = MonthOfYearFormat.format(new Date());
           final  String weekMonthString = WeekOfYearFormat.format(new Date());

                   //Converting String SimpleDateFormat to int;
           final int DayInt = Integer.parseInt(weekdayString) ;
           final int WeekInt =Integer.parseInt( weekMonthString );
           final int MonthInt = Integer.parseInt( monthString );

                    //The calculation or "basic algorithm"(?)
            Double number1 = Double.parseDouble(num1.getText().toString()) / 100.00;
           final Double number2 = Double.parseDouble(num2.getText().toString());
           final Double sum = number1 + number2;
            num1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    String userInput = null;
                    if (!b) {
                        userInput = num1.getText().toString();
                        if (TextUtils.isEmpty(userInput)) {
                            userInput = "0.00";
                        } else {
                            float floatValue = Float.parseFloat(userInput);
                            userInput = String.format("%.2f", floatValue);
                        }
                    }
                    num1.setText(userInput);
                }
            });


         // num1.setText(" "); //clear screen
           num2.setText(valueOf(sum));
           result.setText(decimalFormat.format(sum));

           if (num1 == null || num2.equals(" ")) {
               Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
           }

           Double SumDoubleValue = Double.parseDouble(num2.getText().toString());
           String SqlDateMnthDay = MonthDayFormat.format(new Date());

           if (num2.length() != 0 || SqlDateMnthDay != null) {

            AddData(DayInt, WeekInt, MonthInt, SumDoubleValue, SqlDateMnthDay);
            num1.setText(" " );
            ToastSaved(); }
          }
          catch(Exception e) {
              ToastNoAmountInserted();
          }
         }
        });


       DATAVIEWSbtn.setOnClickListener(new View.OnClickListener() {
       @Override
        public void onClick(View v) {
        Intent DataPage = new Intent(CalculatorActivity.this, DataActivity.class);
        startActivity(DataPage);}
        });

       SETTINGSPAGEbtn.setOnClickListener(new View.OnClickListener() {
       @Override
        public void onClick(View v) {
        Intent SettingsPage = new Intent(CalculatorActivity.this, SettingsActivity.class);
        startActivity(SettingsPage);}
        });

    }

    public void ToastWelcome(){
        Toast toast = Toast.makeText(this, "Hello, Welcome back :) " ,Toast.LENGTH_LONG);
        toast.setGravity( Gravity.TOP,-20,380);
        toast.show();
    }

    public void ToastSaved(){
        Toast toast = Toast.makeText(CalculatorActivity.this, "SAVED !!!", Toast.LENGTH_SHORT);
        toast.setGravity( Gravity.BOTTOM, 26,290 );
        toast.show();
    }

    public void ToastNoAmountInserted() {
        Toast toast = Toast.makeText(CalculatorActivity.this, "No Amount Inserted", Toast.LENGTH_SHORT);
        toast.setGravity( Gravity.TOP, -20,380 );
        toast.show();
    }

      //Assigning numbers to buttons
    public  void CalculatorOnClickButtons(){

        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { num1.setText(num1.getText().toString() + "0");}
        });

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {num1.setText(num1.getText().toString() + "1");}
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "2"); }
        });

        three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "3"); }
        });

        four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "4"); }
        });

        five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "5"); }
        });

        six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "6"); }
        });

        seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "7"); }
        });

        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "8"); }
        });

        nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "9"); }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num1.getText().length() > 0) {
                    CharSequence name = num1.getText().toString();
                    num1.setText(name.subSequence(0, name.length() - 1)); }}
        });
   }



    public void AddData(int weekDayInt, int weekMonth, int monthInt, Double newEntry, String dateForSql){
        boolean insertData = dataBaseHelper.addData(weekDayInt, weekMonth, monthInt, newEntry , dateForSql );

       if(insertData == true)  {
        num1.setText(valueOf(result));
        Toast.makeText(CalculatorActivity.this, "SAVED ", Toast.LENGTH_SHORT).show();}
       else {
           Toast.makeText(CalculatorActivity.this, "NOT SAVED", Toast.LENGTH_SHORT).show();
       }

    }

    public void SetupUIViews() {
        zero = (Button) findViewById(R.id.button0);
        one = (Button) findViewById(R.id.button1);
        two = (Button) findViewById(R.id.button2);
        three = (Button) findViewById(R.id.button3);
        four = (Button) findViewById(R.id.button4);
        five = (Button) findViewById(R.id.button5);
        six = (Button) findViewById(R.id.button6);
        seven = (Button) findViewById(R.id.button7);
        eight = (Button) findViewById(R.id.button8);
        nine = (Button) findViewById(R.id.button9);
        clear = (Button) findViewById(R.id.button11);

        dataBaseHelper = new DataBaseHelper(this);
        num1 = (TextView) findViewById(R.id.etNum1);
        num2 = (EditText) findViewById(R.id.etNum2);
        PAYbtn = (Button) findViewById(R.id.btnAdd);
        result = (TextView) findViewById(R.id.tvAnswer);
        DATAVIEWSbtn = (Button) findViewById(R.id.logpage);
        SETTINGSPAGEbtn = (Button) findViewById(R.id.settings);

    }

}

