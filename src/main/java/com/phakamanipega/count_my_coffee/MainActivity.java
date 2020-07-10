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


public class MainActivity extends Activity {

    private int mCounter = 0;
    private TextView cups;
    public Button thirdactivity;
    public Button logpage;
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
            //UI BUTTONS  0-9... 00
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


        //Button when pay is pressed
        PAY.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {



             try {

                        SimpleDateFormat sdf = new SimpleDateFormat(" MM.dd ");
                 SimpleDateFormat sdf2 = new SimpleDateFormat("u");
                 SimpleDateFormat sdf3 = new SimpleDateFormat( "L" );
                 SimpleDateFormat sdf4 = new SimpleDateFormat( "w" );
                        DecimalFormat money = new DecimalFormat("0.00 ");
                     Double number1 = Double.parseDouble(num1.getText().toString()) / 100.00;
                     Double number2 = Double.parseDouble(num2.getText().toString());
                     Double sum = number1 + number2;


                 //TextViews

                    num1.setText(" ");
                    num2.setText(valueOf(sum));
                    result.setText(  money.format(sum));
                    mCounter++;
                    cups.setText(Integer.toString(mCounter));

                                if (num1 == null || num2.equals(" ")) {
                                    Toast.makeText(getBaseContext(), "input field is empty", Toast.LENGTH_LONG).show(); }


                          //Assigning variables that will go into sqlite
                           String weekdayString = sdf2.format(new Date());
                                String monthString = sdf3.format(new Date());
                                String weekMonthString = sdf4.format(new Date());
                              int weekDayInt = Integer.parseInt(weekdayString) ;
                              int monthInt = Integer.parseInt( monthString );
                              int weekMonth =Integer.parseInt( weekMonthString );
                           Double weekEntry = Double.parseDouble(num2.getText().toString());
                           Double newEntry = Double.parseDouble(num2.getText().toString());
                           String dateForSql = sdf.format(new Date());

                           TreeMap<Double, Double> treeMap = new TreeMap<>();
                           treeMap.put(Double.valueOf(dateForSql), newEntry);


                    //Adding the values onto SQLite through AddData.class
                   if (num2.length() != 0 || dateForSql != null) {
                        AddData(weekDayInt, weekMonth, monthInt, newEntry, dateForSql);
                        num1.setText(" " );
                        ToastSaved();
                   }





             } catch(Exception e)
           { ToastNoAmountInserted();
           }





        }});



        //Button to open LOG page/Activity
        logpage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Double number1 =  Double.parseDouble(num2.getText().toString());
                        Double sum = number1 ;


                        String sumerizedstring = String.valueOf(sum);

                        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                        intent.putExtra("zuma",sumerizedstring );
                        startActivity(intent);



//                    Intent pacman = new Intent(MainActivity.this, SecondActivity.class);
//
//
//                    startActivity(pacman);
//



                    }});

        //Button to open SETTINGS page
        thirdactivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent SETTINGSPAGE = new Intent(MainActivity.this, ThirdActivity.class);
                startActivity(SETTINGSPAGE);

        }});

    }

    public void ToastWelcome(){
        Toast toast = Toast.makeText(this, "Hello, Welcome back :) " ,Toast.LENGTH_LONG);
        toast.setGravity( Gravity.TOP,-20,380);
        toast.show();


    }

    public void ToastSaved(){

        Toast toast = Toast.makeText(MainActivity.this, "SAVED !!!", Toast.LENGTH_SHORT);
        toast.setGravity( Gravity.BOTTOM, 26,290 );
        toast.show();
    }
    public void ToastNoAmountInserted()
    {Toast toast = Toast.makeText(MainActivity.this, "No Amount Inserted", Toast.LENGTH_SHORT);
        toast.setGravity( Gravity.TOP, -20,380 );
        toast.show();

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


        mDatabaseHelper = new DataBaseHelper(this);
        num1 = (TextView) findViewById(R.id.etNum1);
        num2 = (EditText) findViewById(R.id.etNum2);
        PAY = (Button) findViewById(R.id.btnAdd);
        result = (TextView) findViewById(R.id.tvAnswer);
        cups = (TextView) findViewById(R.id.textView6);
        logpage = (Button) findViewById(R.id.logpage);
        thirdactivity = (Button) findViewById(R.id.settings);

    }

    public void AddData(int weekDayInt, int weekMonth, int monthInt, Double newEntry, String dateForSql){


            boolean insertData = mDatabaseHelper.addData(weekDayInt, weekMonth, monthInt, newEntry , dateForSql );

                if(insertData == true)  {
                        num1.setText(valueOf(result));
                        Toast.makeText(MainActivity.this, "SAVED ", Toast.LENGTH_SHORT).show();}
                else { Toast.makeText(MainActivity.this, "NOT SAVED", Toast.LENGTH_SHORT).show(); }

    }





}

