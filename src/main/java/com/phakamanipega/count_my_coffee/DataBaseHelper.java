package com.phakamanipega.count_my_coffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "myDB.db";
    public static final String TABLE_NAME = "mylist_data2";
    public static final String COL2 = "ITEM1";
    public static final String COL3 = "DATE";
    public static final String COL4 = "ITEMDAY";
    public static final String COL5 = "ITEMMONTH";
    public static final String COL6 = "ITEMWEEK";


    @Override
    public void onCreate(SQLiteDatabase db) {
  String createTable =
    "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT " +
            " , ITEM1 TEXT " +
            " , DATE TEXT " +
            " , ITEMDAY TEXT " +
            " , ITEMWEEK TEXT "  +
            " , ITEMMONTH TEXT) ";
        db.execSQL(createTable);

    }

    public boolean addData(int weekDayInt, int weekInt, int monthInt, double price1, String date ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, weekDayInt);
        contentValues.put(COL6, weekInt );
        contentValues.put(COL5, monthInt );
        contentValues.put(COL2, price1);
        contentValues.put(COL3, date);


        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    //Delete item function
    public boolean deleteOne(String tobeDeleted){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM mylist_data2 " + " WHERE " + COL2 +" = '"  + tobeDeleted + "'";

       Cursor cursor =  db.rawQuery(query,null);

       if (cursor.moveToFirst()){
           return true;
       } else {
           return false;
       }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, 5);

    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    public Cursor dayOfWeekCalculation(){
        SimpleDateFormat sdf = new SimpleDateFormat( "u" );
        int currentWeekInt = Integer.parseInt( sdf.format( new Date(  ) ) ) ;
       SQLiteDatabase db = this.getWritableDatabase();
       String Query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL4 +" = '" + currentWeekInt +"' ";
       Cursor data = db.rawQuery( Query,null );



       return data;
    }

    public Cursor weekOfYearCalculation(){
        SimpleDateFormat sdf = new SimpleDateFormat( "w" );
        int currentWeekOfYear = Integer.parseInt( sdf.format( new Date ()) );
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL6 +" = '" + currentWeekOfYear +"' ";
        Cursor data = db.rawQuery( Query, null );
        return data;
    }

    public Cursor monthOfYearCalculation(){
        SimpleDateFormat sdf = new SimpleDateFormat( "L" );
        int monthInt = Integer.parseInt(sdf.format( new Date()));
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL5 + " = '" + monthInt + "' ";
        Cursor data = db.rawQuery( Query, null );

        return data;
    }







}












