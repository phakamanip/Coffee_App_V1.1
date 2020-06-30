package com.phakamanipega.count_my_coffee;

import java.text.SimpleDateFormat;
import java.util.*;
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
    public ListView LISTVIEW;

    DataBaseHelper mDatabaseHelper;
    DecimalFormat money = new DecimalFormat("0.00 ");
    Calendar thisDayNow = Calendar.getInstance();
    ListAdapter Adapter;
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
        LISTVIEW = (ListView) findViewById(R.id.list);
        mDatabaseHelper = new DataBaseHelper(this);
        populateListView();
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
        final ArrayList<String> ListDataForViews = new ArrayList<>();
                while (data.moveToNext()) {
                     String mydataAndDate = "$" + data.getString(1) + data.getString(2) + data.getString(3) ;
                     ListDataForViews.add(mydataAndDate);
                }
              Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListDataForViews);
              LISTVIEW.setAdapter(Adapter);


        final Cursor data2 = mDatabaseHelper.getData();
        final ArrayList<String> listDataForLv = new ArrayList<>();
                    while (data2.moveToNext()) {
                        listDataForLv.add(data2.getString(1));
                    }




     //  Alows you to click item from listView and delete it
        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, final long l) {

                AlertDialog.Builder adb = new AlertDialog.Builder(SecondActivity.this);
                 adb.setTitle("Delete?");
                 adb.setMessage("Are you sure u want to delete $" + ListDataForViews.get(i));
                 adb.setNegativeButton("Cancel", null);
                 adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String listdatastring = ListDataForViews.get(i);
                        mDatabaseHelper.deleteOne(listDataForLv.get(i));
                        Toast.makeText(SecondActivity.this, listdatastring + " Deleted ", Toast.LENGTH_LONG).show();
                        populateListView();
                    }
                });
                adb.show();
            }

        });

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

            double[] doubleListmonthly = new double[listDataForLv.size()];
            double sumOfMonth = 0;
            for (int i = 0; i < listDataForLv.size(); i++) {
                doubleListmonthly[i] = Double.parseDouble(listDataForLv.get(i));
                sumOfMonth += doubleListmonthly[i];
                THISMONTH.setText("$" + money.format(sumOfMonth) + " " + currentDayofMonth);


            }
        }

        if (currentDayofMonth <= April || currentDayofMonth <= June ||
                currentDayofMonth <= September || currentDayofMonth <= November) {
            double[] doubleListmonthly = new double[listDataForLv.size()];

            TreeMap<Integer, double[]> treeMap1 = new TreeMap<>();

            for (TreeMap.Entry<Integer, double[]> entry : treeMap1.entrySet()) {
                treeMap1.put(currentDayofMonth, doubleListmonthly);
                treeMap1.subMap(currentDayofMonth, lastDayOfMonth);
                THISMONTH.setText(treeMap1.toString());

            }
        }
        //=======================================YEARLY ITERATOR================================

        double[] doubleList = new double[listDataForLv.size()];
     double summm = 0;

        for (int i = 0; i < listDataForLv.size(); ++i) {
            doubleList[i] = Double.parseDouble(listDataForLv.get(i));
            summm += doubleList[i];
            THISYEAR.setText("$" + money.format(summm));
        }




    }



    public void populateWeek() {

        //***REMEMBER, FOR THE WEEKLY RESET TIME, U CAN USE THE WHILE LOOP....while its 11:59 on Sunday, reset .

        final Cursor data3 = mDatabaseHelper.getData();
        final ArrayList<Integer> listDataforWeekInt = new ArrayList<>();
        while(data3.moveToNext()){
            listDataforWeekInt.add(data3.getInt(3));
        }

        final Cursor data2 = mDatabaseHelper.getData();
        final ArrayList<String> listDataForLv = new ArrayList<>();
        while (data2.moveToNext()) {
            listDataForLv.add(data2.getString(1));
        }

        int TodaysInt = Integer.parseInt(sdf.format(new Date()));
        int monday = 1;
        int tuesday = 2;
        int wednesday = 3;
        int thursday = 4;
        int fryday = 5;
        int saturday = 6;
        int sunday = 7;

        for (int i =0; i<listDataforWeekInt.size(); i++){
           listDataForLv.get(i);

                if(listDataforWeekInt.get(i) ==1) {
                    double sumOfArray = Double.parseDouble(listDataForLv.get(i));


                    THISWEEK.setText(String.valueOf(TodaysInt) + " and " + listDataForLv +" also " + sumOfArray);
                }

        }




    }





}





























