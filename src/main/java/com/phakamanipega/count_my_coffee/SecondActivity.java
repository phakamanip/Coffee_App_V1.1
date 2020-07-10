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

    public static final String TAG = "SecondActivity";
    public Button BACKbtn, SETTINGSbtn;
    public TextView TODAY, THISWEEK, THISMONTH, THISYEAR;
    public ListView LISTVIEW;

    DataBaseHelper mDatabaseHelper;
    DecimalFormat money = new DecimalFormat("0.00 ");
    ListAdapter Adapter;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        TODAY = (TextView) findViewById( R.id.todayview );
        THISWEEK = (TextView) findViewById(R.id.thisweekview);
        THISMONTH = (TextView) findViewById(R.id.thismonthview);
        THISYEAR = (TextView) findViewById(R.id.thisyeaview);
        SETTINGSbtn = (Button) findViewById(R.id.Clickviewer);
        BACKbtn = (Button) findViewById(R.id.back);
        LISTVIEW = (ListView) findViewById(R.id.list);
        mDatabaseHelper = new DataBaseHelper(this);
        populateListView();


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
                     String mydataAndDate = "$" + data.getString(1)
                                     +"   "           + data.getString(2)
                                        //        + data.getString(3) +" "
                                       //         + data.getString(4) +" "
                                        //        + data.getString( 5 )
                             ;
                     ListDataForViews.add(mydataAndDate);
                }
                Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListDataForViews);
              LISTVIEW.setAdapter(Adapter);


        final Cursor data2 = mDatabaseHelper.getData();
        final ArrayList<String> listDataForLv = new ArrayList<>();
                    while (data2.moveToNext()) {
                        listDataForLv.add(data2.getString(1));
                    }

        final Cursor data3 = mDatabaseHelper.getData();
        final ArrayList<Integer> listDataforWeekInt = new ArrayList<>();
        while(data3.moveToNext()){
            listDataforWeekInt.add(data3.getInt(3));
        }

        final Cursor data4 = mDatabaseHelper.weekDayCalculation();
        final ArrayList<Double> listOfNumbers = new ArrayList<>(  );
        while (data4.moveToNext()){
            listOfNumbers.add( data4.getDouble(1) );

            double sumWeek =0;
            for(Double d : listOfNumbers)
                sumWeek += d;
            THISWEEK.setText( " $" + money.format(  sumWeek) );
        }

        final Cursor data5 = mDatabaseHelper.monthCalculation();
        final  ArrayList<Double> month_Double_ArrayList = new ArrayList<>(  );
        while(data5.moveToNext()) {
            month_Double_ArrayList.add( data5.getDouble( 1 ) );

            double sumMonth = 0;
            for (Double d : month_Double_ArrayList)
                sumMonth += d;


            THISMONTH.setText( " $" + money.format( sumMonth )
            );
        }

        final Cursor data6 = mDatabaseHelper.weekmonthCalculation();
        final ArrayList<Double> weekmonthArray = new ArrayList<>(  );
        while(data6.moveToNext()){
            weekmonthArray.add( data6.getDouble( 1 ) );
             double WeekMonth =0;
             for (Double d: weekmonthArray){
                 WeekMonth += d;

                 TODAY.setText(" $" + money.format( WeekMonth ));
             }
        }

        double[] doubleList = new double[listDataForLv.size()];
        double summm = 0;

        for (int i = 0; i < listDataForLv.size(); ++i) {
            doubleList[i] = Double.parseDouble(listDataForLv.get(i));
            summm += doubleList[i];
            THISYEAR.setText(" $" + money.format(summm));

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



        //=======================================YEARLY ITERATOR================================


    }


















}





























