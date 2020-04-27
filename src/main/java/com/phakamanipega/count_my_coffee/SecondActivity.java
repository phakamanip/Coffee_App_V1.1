package com.phakamanipega.count_my_coffee;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;


public class SecondActivity extends AppCompatActivity {

    public Button BACK, GOTOSETTINGS;
    public TextView THISWEEK, THISMONTH, THISYEAR;
    public ListView lister;
    public static final String TAG = "SecondActivity";
    DataBaseHelper mDatabaseHelper;
    DecimalFormat money = new DecimalFormat("0.00 ");
    ListAdapter listAdapter;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        THISWEEK = (TextView) findViewById(R.id.thisweekview);
        THISMONTH = (TextView) findViewById(R.id.thismonthview);
        THISYEAR = (TextView) findViewById(R.id.thisyeaview);
        GOTOSETTINGS = (Button) findViewById(R.id.Clickviewer);
        BACK = (Button) findViewById(R.id.back);

        lister = (ListView) findViewById(R.id.list);
        mDatabaseHelper = new DataBaseHelper(this);
        populateListView();




        //Button to go back to homeScreen
        BACK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMainActivity = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(goToMainActivity);
            }
        });

        GOTOSETTINGS.setOnClickListener(new View.OnClickListener() {
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

            //"2" is column with dates in DataBaseHelper and 1 is column with values
            final String mydataAndDate = data.getString(2) + data.getString(1);
            final String mydataPrice = data.getString(1)   ;
            listData.add(mydataPrice);


            //listData.add(data.getString(2 ) + "  $" + data.getString(1));  }
            listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
            lister.setAdapter(listAdapter);


            //Alows you to click item from listView and delete it
            lister.setOnItemClickListener(new AdapterView.OnItemClickListener() {



                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, final int i, final long l) {

//                    String theonetobeDeleteled = (String )adapterView.getItemAtPosition(i);
//                    mDatabaseHelper.deleteOne(theonetobeDeleteled);
//                    Toast.makeText(SecondActivity.this, "jajajajajajaja",Toast.LENGTH_SHORT).show();








                    AlertDialog.Builder adb = new AlertDialog.Builder(SecondActivity.this);
                    adb.setTitle("Delete?" );
                    adb.setMessage("Are you sure u want to delete $" + listData.get(i ) );
                    final String thisIsNowI = listData.get(i);
                    final int positionToRemove = i;
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new AlertDialog.OnClickListener()
                    {


                            public void onClick(DialogInterface dialog, int which) {
                                String name = adapterView.getItemAtPosition(i).toString();
                                mDatabaseHelper.deleteName( name);
                                Toast.makeText(SecondActivity.this, name +" Was Deleted",Toast.LENGTH_SHORT).show();

                            }


                    });
                    adb.show();



                }
            });


            //=================================================

            Calendar thisDayNow = Calendar.getInstance();
            final ArrayList<String> listDataPrice = new ArrayList<>();
            final Cursor data2 = mDatabaseHelper.getData();

            while (data2.moveToNext()) {
                listDataPrice.add(data2.getString(1));
            }



        /* WEEKLY ITERATOR

        =============================================
        */

            // -1 is to Assign Monday as day 1, not Sunday.
            int currentDayOfWeek =  thisDayNow.get(Calendar.DAY_OF_WEEK  ) -1 ;
            int lastDAYOFWEEK = 6;

             if(currentDayOfWeek <= lastDAYOFWEEK){
                double[] doubleListweekly = new double[listDataPrice.size()];
                double sumOfWeek =0;
                for(int i = 0; i < listDataPrice.size(); i++) {

                    doubleListweekly[i] = Double.parseDouble(listDataPrice.get(i));
                    sumOfWeek += doubleListweekly[i];
                                             THISWEEK.setText("$" + money.format(sumOfWeek));

                }
             }




        /*MONTHLY ITERATOR
        ===========================================
        */

            int currentDayofMonth = thisDayNow.get(Calendar.DAY_OF_MONTH);
            int lastDayOfMonth = 31;


            if (currentDayofMonth <= lastDayOfMonth) {

                double[] doubleListmonthly = new double[listDataPrice.size()];


                TreeMap<Integer, double[]> treeMap1 = new TreeMap<>();

                 for(TreeMap.Entry<Integer, double[]> entry: treeMap1.entrySet() ) {

                     treeMap1.put(currentDayofMonth, doubleListmonthly);

                     treeMap1.subMap(currentDayofMonth, lastDayOfMonth);

                       THISMONTH.setText(treeMap1.toString());

                 }


            }




        /*YEARLY ITERATOR
         ========================================================
        */

            double[] doubleList = new double[listDataPrice.size()];
            double summm = 0;

            for (int i = 0; i < listDataPrice.size(); ++i) {
                doubleList[i] = Double.parseDouble(listDataPrice.get(i));
                summm += doubleList[i];

                String thisone = String.valueOf(summm);
                THISYEAR.setText("$" + money.format(summm ));






            }

        }




    }




}








