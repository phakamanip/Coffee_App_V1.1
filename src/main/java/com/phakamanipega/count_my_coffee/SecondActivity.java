package com.phakamanipega.count_my_coffee;

import java.text.SimpleDateFormat;
import java.util.*;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.*;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import java.text.DecimalFormat;


public class SecondActivity extends AppCompatActivity {

    //***REMEMBER, FOR THE WEEKLY RESET TIME, U CAN USE THE WHILE LOOK....while its 11:59 on Sunday, reset everything.

    public static final String TAG = "SecondActivity";
    public Button BACKbtn, SETTINGSbtn;
    public TextView THISWEEK, THISMONTH, THISYEAR;
    public ListView lister;

    DataBaseHelper mDatabaseHelper;
    DecimalFormat money = new DecimalFormat("0.00 ");
    Calendar thisDayNow = Calendar.getInstance();
    ListAdapter adapter;
    SimpleDateFormat sdf = new SimpleDateFormat("u");


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        THISWEEK = (TextView) findViewById(R.id.thisweekview);
        THISMONTH = (TextView) findViewById(R.id.thismonthview);
        THISYEAR = (TextView) findViewById(R.id.thisyeaview);
        SETTINGSbtn = (Button) findViewById(R.id.Clickviewer);
        BACKbtn = (Button) findViewById(R.id.back);
        lister = (ListView) findViewById(R.id.list);
        mDatabaseHelper = new DataBaseHelper(this);
        populateListView();
        //deleteWeekly();
        populateWeek();


        BACKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMainActivity = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(goToMainActivity);
            }
        });

        SETTINGSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosetttings = (new Intent(SecondActivity.this, ThirdActivity.class));
                startActivity(gotosetttings);
            }
        });


    }


    public void populateListView() {

        final Cursor data = mDatabaseHelper.getData();
        final ArrayList<String> listData = new ArrayList<>();

        while (data.moveToNext()) {

            //  "1" = column values in Db       2 is column with dates
            final String mydataAndDate = "$" + data.getString(1) + data.getString(2);
            listData.add(mydataAndDate);
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        lister.setAdapter(adapter);

        final ArrayList<String> listDataPrice = new ArrayList<>();

        //  Alows you to click item from listView and delete it
        lister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, final long l) {

                AlertDialog.Builder adb = new AlertDialog.Builder(SecondActivity.this);
                // adb.setTitle("Delete?");
                //adb.setMessage("Are you sure u want to delete $" + listData.get(i));
                //adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String listdatastring = listData.get(i);
                        mDatabaseHelper.deleteOne(listDataPrice.get(i));
                        Toast.makeText(SecondActivity.this, listdatastring + " Deleted ", Toast.LENGTH_LONG).show();
                        populateListView();
                    }
                });
                adb.show();
            }

        });


        final Cursor data2 = mDatabaseHelper.getData();

        while (data2.moveToNext()) {
            listDataPrice.add(data2.getString(1));
        }

        // ===========================================Monthly=============================
        int currentDayofMonth = thisDayNow.get(Calendar.DAY_OF_MONTH);
        int lastDayOfMonth = 31;

        int January = 31;
        int Febuary = 28;
        int March = 31;
        int April = 30;
        int May = 31;
        int June = 30;
        int July = 31;
        int August = 31;
        int September = 30;
        int October = 31;
        int November = 30;
        int December = 31;

        if (currentDayofMonth <= January || currentDayofMonth <= March
                || currentDayofMonth <= May || currentDayofMonth <= July
                || currentDayofMonth <= August || currentDayofMonth <= October
                || currentDayofMonth <= December) {

            double[] doubleListmonthly = new double[listDataPrice.size()];
            double sumOfMonth = 0;
            for (int i = 0; i < listDataPrice.size(); i++) {
                doubleListmonthly[i] = Double.parseDouble(listDataPrice.get(i));
                sumOfMonth += doubleListmonthly[i];
                THISMONTH.setText("$" + money.format(sumOfMonth) + " " + currentDayofMonth);


            }
        }

        if (currentDayofMonth <= April || currentDayofMonth <= June ||
                currentDayofMonth <= September || currentDayofMonth <= November) {
            double[] doubleListmonthly = new double[listDataPrice.size()];

            TreeMap<Integer, double[]> treeMap1 = new TreeMap<>();

            for (TreeMap.Entry<Integer, double[]> entry : treeMap1.entrySet()) {
                treeMap1.put(currentDayofMonth, doubleListmonthly);
                treeMap1.subMap(currentDayofMonth, lastDayOfMonth);
                THISMONTH.setText(treeMap1.toString());

            }
        }
        //=======================================YEARLY ITERATOR================================


        double[] doubleList = new double[listDataPrice.size()];
        double summm = 0;

        for (int i = 0; i < listDataPrice.size(); ++i) {
            doubleList[i] = Double.parseDouble(listDataPrice.get(i));
            summm += doubleList[i];
            THISYEAR.setText("$" + money.format(summm));
        }


    }


    public void deleteWeekly() {

        SimpleDateFormat currentDayOfWeek = new SimpleDateFormat("u");
        String currentDayString = currentDayOfWeek.format(new Date());

        SimpleDateFormat currentTimeOfDay = new SimpleDateFormat("hh.mm");
        String currentTimeString = currentTimeOfDay.format(new Date());
        double CurrentTimeDouble = Double.parseDouble(currentTimeString);
        double ResetTime = 04.12;


        ArrayList<String> wholeWeekArrayListString = new ArrayList<>();

        int MoMdayInt = 1;
        int ChoozedayInt = 2;
        int WeednesdayInt = 3;
        int ThirstdayInt = 4;
        int FriHighdayInt = 5;
        int SaturdayInt = 6;
        int SundayInt = 7;
        int[] intArray = {MoMdayInt, ChoozedayInt, WeednesdayInt, ThirstdayInt, FriHighdayInt, SaturdayInt, SundayInt};
        int CurrentDayOfWeekInt = Integer.parseInt(currentDayString);


        Cursor data3 = mDatabaseHelper.getData();
        while (data3.moveToNext()) {
            wholeWeekArrayListString.add(data3.getString(1));
        }


        double[] doubleListWeekly = new double[wholeWeekArrayListString.size()];
        double sumOfWeek = 0;

        for (int i = CurrentDayOfWeekInt; i <= wholeWeekArrayListString.indexOf(intArray); i++) {
            doubleListWeekly[i] = Double.parseDouble(wholeWeekArrayListString.get(i));
            sumOfWeek += doubleListWeekly[i];


            if (CurrentDayOfWeekInt == 1) {
                THISWEEK.setText("Today is MoMday " + " $" + money.format(sumOfWeek));
            }
            if (CurrentDayOfWeekInt == 2) {
                THISWEEK.setText(" Today is Choozeday " + "$" + money.format(sumOfWeek));
            }
            if (CurrentDayOfWeekInt == 3) {
                THISWEEK.setText("Today is Weednesday " + "$" + money.format(sumOfWeek));
            }
            if (CurrentDayOfWeekInt == 4) {
                THISWEEK.setText("Today is Thirstday " + "$" + money.format(sumOfWeek));
            }
            if (CurrentDayOfWeekInt == 5) {
                THISWEEK.setText("Today is FriHighday " + "$" + money.format(sumOfWeek));
            }
            if (CurrentDayOfWeekInt == 6) {
                THISWEEK.setText("Today is Saturday " + " $" + money.format(sumOfWeek));
            }
            if (CurrentDayOfWeekInt == SundayInt) {
                THISWEEK.setText("Today is Sunday" + "$" + money.format(sumOfWeek));
            }
            if (CurrentDayOfWeekInt == 7 && CurrentTimeDouble == ResetTime) {
                THISWEEK.setText("Today is Sunday and time is resetTime" + "$" + money.format(sumOfWeek));
                // wholeWeekArrayListString.clear();


            }

        }


    }


    public void populateWeek() {

        //***REMEMBER, FOR THE WEEKLY RESET TIME, U CAN USE THE WHILE LOOP....while its 11:59 on Sunday, reset .

        String TodaysPriceTranfaredString = getIntent().getStringExtra("zuma");
        int TodaysInt = Integer.parseInt(sdf.format(new Date()));

        ArrayList<String> PriceStringArrayList = new ArrayList<>();
        PriceStringArrayList.add(TodaysPriceTranfaredString);


        int monday = 1;
        int tuesday = 2;
        int wednesday = 3;
        int thursday = 4;
        int fryday = 5;
        int saturday = 6;
        int sunday = 7;

        int[] Momday = {TodaysPriceTranfaredString.length()};
        int[] ChoozedayInt = {TodaysPriceTranfaredString.length()};
        int[] WeednesdayInt = {TodaysPriceTranfaredString.length()};
        int[] Thirstday = {TodaysPriceTranfaredString.length()};
        int[] SaturdayInt = {TodaysPriceTranfaredString.length()};
        int[] SundayInt = {TodaysPriceTranfaredString.length()};


        if (TodaysInt == saturday || TodaysInt == monday || TodaysInt == tuesday
                || TodaysInt == wednesday || TodaysInt == thursday || TodaysInt == fryday || TodaysInt == sunday) {

            double[] FriHighdayInt = {PriceStringArrayList.size()};
            double sum = 0;

            for (int i = 0; i < PriceStringArrayList.size(); i++) {
                FriHighdayInt[i] = Double.parseDouble(PriceStringArrayList.get(i));
                sum += FriHighdayInt[i];

                HashMap<Integer, Double> dvhm = new HashMap<Integer, Double>();
                dvhm.put(TodaysInt, sum);

                Set<Map.Entry<Integer, Double>> entrySet = dvhm.entrySet();

                for (Map.Entry<Integer, Double> entry : entrySet) {

                    int mykey = entry.getKey();
                    double myvalue = entry.getValue();
                    if (entry.getKey() == 7) {

                        dvhm.values();

                        double[] adding = {entry.getValue()};
                        String addingString = String.valueOf(adding);

                            THISWEEK.setText(" Day:" + TodaysInt + String.valueOf(", sum= " + sum +
                                    "\n Key= " + mykey + " Value= " + myvalue + " DIS= " + addingString
                            ));


                    }

                }
            }


        }


    }




}





























