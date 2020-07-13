package com.phakamanipega.count_my_coffee;

import java.util.*;
import android.view.View;
import android.widget.*;
import android.content.*;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import java.text.DecimalFormat;


public class DataActivity extends AppCompatActivity {

    public static final String TAG = "SecondActivity";
    public Button HOMEbtn, SETTINGSbtn;
    public TextView TODAYtv, THISWEEKtv, THISMONTHtv, THISYEARtv;
    public ListView LISTVIEW;

    DataBaseHelper mDatabaseHelper;
    DecimalFormat money = new DecimalFormat("0.00 ");
    ListAdapter Adapter;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        UIViews(); //method difined at line 162
        populateListView(); //method defined at line 58


        // Button to go to Calculation page
        HOMEbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMainActivity = new Intent(DataActivity.this, CalculatorActivity.class);
                startActivity(goToMainActivity);
            }
        });

        // Button to go to Settings and updates page
        SETTINGSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotosetttings = (new Intent(DataActivity.this, SettingsActivity.class));
                startActivity(gotosetttings);
            }
        });


    }




    public void populateListView() {

        //Retrieving data from SQLite Columns and showing it onto "LISTVIEW" using adapter Design Pattern ;
        final Cursor data = mDatabaseHelper.getData();
        final ArrayList<String> ListDataForViews = new ArrayList<>();
                while (data.moveToNext()) {
                     String mydataAndDate = "$" + data.getString(1)
                                     +"   "     + data.getString(2)
                                                + data.getString(3) +" "
                                                + data.getString(4) +" "
                                                + data.getString( 5 )
                             ;ListDataForViews.add(mydataAndDate);
                }
          Adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListDataForViews);
          LISTVIEW.setAdapter(Adapter);


//===============================================================================
       //Retrieving data of Column with int of day, summing up the value and showing it on TextView "TODAYtv
        final Cursor data4 = mDatabaseHelper.dayOfWeekCalculation();
        final ArrayList<Double> dayOfWeekArray = new ArrayList<>(  );
        while (data4.moveToNext()){
            dayOfWeekArray.add( data4.getDouble(1) );

            double sumDay =0;
            for(Double d : dayOfWeekArray)
                sumDay += d;
            TODAYtv.setText( " $" + money.format(  sumDay) );
        }
//=================================================================================
        //Retrieving data of Column with int of month, summing up the value and showing it on TextView "THISMONTHtv

        final Cursor data5 = mDatabaseHelper.monthOfYearCalculation();
        final  ArrayList<Double> monthOfYearArray = new ArrayList<>(  );
        while(data5.moveToNext()) {
            monthOfYearArray.add( data5.getDouble( 1 ) );

            double sumMonth = 0;
            for (Double d : monthOfYearArray){
                sumMonth += d;
             THISMONTHtv.setText( " $" + money.format( sumMonth ) );}
         }

 //=================================================================================
        //Retrieving data of Column with int of week, summing up the value and showing it on TextView "THISWEEKtv

        final Cursor data6 = mDatabaseHelper.weekOfYearCalculation();
        final ArrayList<Double> weekOfYearArray = new ArrayList<>(  );
        while(data6.moveToNext()){
             weekOfYearArray.add( data6.getDouble( 1 ) );

             double sumWeek =0;
             for (Double d: weekOfYearArray){
                 sumWeek += d;
              THISWEEKtv.setText(" $" + money.format( sumWeek )); }
        }

//=================================================================================
        //Retrieving data of Column with int of year, summing up the value and showing it on TextView "THISYEARtv
        final Cursor data2 = mDatabaseHelper.getData();
        final ArrayList<String> yearArray = new ArrayList<>();
        while (data2.moveToNext()) {
                  yearArray.add(data2.getString(1));
                double[] doubleList = new double[yearArray.size()];
                double sumYear = 0;
            for (int i = 0; i < yearArray.size(); ++i) {
            doubleList[i] = Double.parseDouble(yearArray.get(i));
            sumYear += doubleList[i];
            THISYEARtv.setText(" $" + money.format(sumYear)); }
    }


//=================================================================================



        //  Delete item on ListView by clicking it;
            LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adapterView, View view, final int i, final long l) {

                AlertDialog.Builder adb = new AlertDialog.Builder(DataActivity.this);
                 adb.setTitle("Delete?");
                 adb.setMessage("Are you sure u want to delete $" + ListDataForViews.get(i));
                 adb.setNegativeButton("Cancel", null);
                 adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String listdatastring = ListDataForViews.get(i);
                        mDatabaseHelper.deleteOne(yearArray.get(i));
                        Toast.makeText(DataActivity.this, listdatastring + " Deleted ", Toast.LENGTH_LONG).show();
                        populateListView();
                    }
                });
                adb.show();
            }

        });



    }


     public void UIViews() {
         //Tying Buttons to XML
         TODAYtv = (TextView) findViewById( R.id.todayview );
         THISWEEKtv = (TextView) findViewById( R.id.thisweekview );
         THISMONTHtv = (TextView) findViewById( R.id.thismonthview );
         THISYEARtv = (TextView) findViewById( R.id.thisyeaview );
         SETTINGSbtn = (Button) findViewById( R.id.Clickviewer );
         HOMEbtn = (Button) findViewById( R.id.back );
         LISTVIEW = (ListView) findViewById( R.id.list );
         mDatabaseHelper = new DataBaseHelper( this );

     }

}





























