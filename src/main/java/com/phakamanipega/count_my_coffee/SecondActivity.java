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
import java.util.HashMap;
import java.util.List;


public class SecondActivity extends AppCompatActivity {

    public Button BACK, GOTOSETTINGS;
    public TextView THISWEEK, THISMONTH, THISYEAR;
    public ListView lister;
    public static final String TAG = "SecondActivity";
    DataBaseHelper mDatabaseHelper;
    DecimalFormat money = new DecimalFormat("0.00 ");
    Calendar thisDayNow = Calendar.getInstance();
    ListAdapter adapter;



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

            //                           "2" is column with dates in DataBaseHelper and             1 is column with values
            final String  mydataAndDate ="$"+data.getString(1) + data.getString(2);
            final String mydataPrice = data.getString(1);
            final String mydataDate =  data.getString(2)   ;
            final String[]  mydataPriceArray = { data.getString(1)  } ;

        listData.add(mydataAndDate );


               adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,  listData );
               lister.setAdapter(adapter );

            final ArrayList<String> listDataPrice = new ArrayList<>();



            //  Alows you to click item from listView and delete it
            lister.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(final AdapterView<?> adapterView, View view, final int i, final long l) {

                    AlertDialog.Builder adb = new AlertDialog.Builder(SecondActivity.this);
                    adb.setTitle("Delete?" );

                    adb.setMessage("Are you sure u want to delete $" + listData.get(i ) );
                    adb.setNegativeButton("Cancel", null);
                    adb.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            String listdatastring = listData.get(i);

                            mDatabaseHelper.deleteOne(mydataAndDate);
                            mDatabaseHelper.deleteOne(listDataPrice.get(i));
                            //mDatabaseHelper.deleteOne(listData.get(i));
                            //mDatabaseHelper.deleteOne(mydataPrice);

                            listData.remove(i);
                            mDatabaseHelper.deleteName(mydataDate, String.valueOf(mydataPrice));
                            Toast.makeText(SecondActivity.this, listdatastring + " Deleted ",Toast.LENGTH_LONG).show();
                            populateListView();


                        }
                    }


                    );


                    adb.show();
                }

            });



            final Cursor data2 = mDatabaseHelper.getData();


            while (data2.moveToNext()) {
                listDataPrice.add(data2.getString(1));


            }
            //listItemView with 2 views
            //=================================================
            HashMap<String, String> listviewPriceAndDate = new HashMap<>();
            listviewPriceAndDate.put(data.getString(1),data.getString(2));
            List<HashMap<String, String>> listItems = new ArrayList<>();
            listItems.add(listviewPriceAndDate);


           // ListAdapter zee = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
           // lister.setAdapter(zee);

            //=================================================

           // listDataPrice.get()


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
                                          //   THISWEEK.setText("$" + money.format(sumOfWeek));

                }
             }

         //=======

            int currentDayofMonth = thisDayNow.get(Calendar.DAY_OF_MONTH);
            int lastDayOfMonth = 31;





       // ===========================================
         /*MONTHLY ITERATOR


            int January = 31;
            int Febuary = 28;
            int March = 31;
            int April = 30 ;
            int May = 31;
            int June  = 30;
            int July = 31;
            int August = 31;
            int September  = 30;
            int October = 31;
            int November = 30;
            int December = 31;

            if (currentDayofMonth <= January      || currentDayofMonth <=March
                    || currentDayofMonth <=May    || currentDayofMonth <=July
                    || currentDayofMonth <=August || currentDayofMonth <= October
                    || currentDayofMonth <=December){ }

            if (currentDayofMonth <=April ||currentDayofMonth <=June ||
                    currentDayofMonth <=September || currentDayofMonth <=November ){ }

          //======


            if (currentDayofMonth <= lastDayOfMonth) {
                double[] doubleListmonthly = new double[listDataPrice.size()];

                     TreeMap<Integer, double[]> treeMap1 = new TreeMap<>();

                 for(TreeMap.Entry<Integer, double[]> entry: treeMap1.entrySet() ) {
                     treeMap1.put(currentDayofMonth, doubleListmonthly);
                     treeMap1.subMap(currentDayofMonth, lastDayOfMonth);
                     // THISMONTH.setText(treeMap1.toString());

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



                THISYEAR.setText("$" + money.format(summm ));

               // mDatabaseHelper.deleteOne(String.valueOf(summm));

                THISMONTH.setText(listDataPrice.toString());
                THISWEEK.setText(listData.toString());






            }


        }




    }




}








