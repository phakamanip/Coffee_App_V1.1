package com.phakamanipega.count_my_coffee;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.TreeMap;
import static java.lang.String.valueOf;


public class CalculatorActivity extends Activity {

    public Button SETTINGSPAGEbtn;
    public Button DATAVIEWSbtn;
    public TextView num1;
    public EditText num2;
    public Button PAY;
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
    public static final String TAG = "MainActivity";

    DataBaseHelper mDatabaseHelper;




    @Override
    public void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
           requestWindowFeature(Window.FEATURE_NO_TITLE);
           setContentView(R.layout.activity_main);
           ToastWelcome();
           SetupUIViews();
           CalculatorButtons();



        //Button when pay is pressed
        PAY.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
               try {
                          //Assigning patterns to Date formats;
                        SimpleDateFormat MonthDayFormat = new SimpleDateFormat(" MM.dd ");
                        SimpleDateFormat DayOfWeekFormat = new SimpleDateFormat("u");
                        SimpleDateFormat MonthOfYearFormat = new SimpleDateFormat( "L" );
                        SimpleDateFormat WeekOfYearFormat = new SimpleDateFormat( "w" );
                        DecimalFormat DecimalFormat = new DecimalFormat("0.00 ");

                     //Converting SimpleDateFormat to String;
                   String weekdayString = DayOfWeekFormat.format(new Date());
                   String monthString = MonthOfYearFormat.format(new Date());
                   String weekMonthString = WeekOfYearFormat.format(new Date());

                   //Converting String SimpleDateFormat to int;
                   int DayInt = Integer.parseInt(weekdayString) ;
                   int WeekInt =Integer.parseInt( weekMonthString );
                   int MonthInt = Integer.parseInt( monthString );

                                   //The calculation structure or "basic algorithm"(?)
                                 Double number1 = Double.parseDouble(num1.getText().toString()) / 100.00;
                                 Double number2 = Double.parseDouble(num2.getText().toString());
                                 Double sum = number1 + number2;

                                num1.setText(" ");
                                num2.setText(valueOf(sum));
                                result.setText(DecimalFormat.format(sum));
                         if (num1 == null || num2.equals(" ")) {
                 Toast.makeText(getBaseContext(), "Input field is empty", Toast.LENGTH_LONG).show();
                  }

                          Double SumDoubleValue = Double.parseDouble(num2.getText().toString());
                          String SqlDate_MnthDay = MonthDayFormat.format(new Date());

                 if (num2.length() != 0 || SqlDate_MnthDay != null) {

                       //AddData method defined at line 232. It Consolidates everything and inserts it into Columns on SQLite
                        AddData(DayInt, WeekInt, MonthInt, SumDoubleValue, SqlDate_MnthDay);
                        num1.setText(" " ); //Clear TextView after inserting to SQLite
                        ToastSaved(); //Method defined on line 130
                 }


              } catch(Exception e) { ToastNoAmountInserted(); //Method defined on line  138
               }

               }});


         //Button to open Data + ListView Page.
        DATAVIEWSbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(CalculatorActivity.this, DataActivity.class);
                startActivity(intent);
        }});

        //Button to open "Settings and Updates" page.
        SETTINGSPAGEbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent SETTINGSPAGE = new Intent(CalculatorActivity.this, SettingsActivity.class);
                startActivity(SETTINGSPAGE);
        }});

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


    //Buttons views 0,1,2,3....
    public  void CalculatorButtons(){
        zero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "0");
            }
        });one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "1");
            }
        });two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "2");
            }
        });three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "3");
            }
        });four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "4");
            }
        });five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "5");
            }
        });six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "6");
            }
        });seven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "7");
            }
        });eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "8");
            }
        });nine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num1.setText(num1.getText().toString() + "9");
            }
        });clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num1.getText().length() > 0) {
                    CharSequence name = num1.getText().toString();
                    num1.setText(name.subSequence(0, name.length() - 1));
                }
            }
        });

    }



          //Tying Buttons to XML
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


        mDatabaseHelper = new DataBaseHelper(this);
        num1 = (TextView) findViewById(R.id.etNum1);
        num2 = (EditText) findViewById(R.id.etNum2);
        PAY = (Button) findViewById(R.id.btnAdd);
        result = (TextView) findViewById(R.id.tvAnswer);
        DATAVIEWSbtn = (Button) findViewById(R.id.logpage);
        SETTINGSPAGEbtn = (Button) findViewById(R.id.settings);

    }

    public void AddData(int weekDayInt, int weekMonth, int monthInt, Double newEntry, String dateForSql){

                boolean insertData = mDatabaseHelper.addData(weekDayInt, weekMonth, monthInt, newEntry , dateForSql );

                if(insertData == true)  {
                        num1.setText(valueOf(result));
                        Toast.makeText(CalculatorActivity.this, "SAVED ", Toast.LENGTH_SHORT).show();}
                else { Toast.makeText(CalculatorActivity.this, "NOT SAVED", Toast.LENGTH_SHORT).show(); }

    }





}

