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
    public static final String TABLE_NAME = "transactions";
    public static final String COL2 = "Amount";
    public static final String COL3 = "Date";
    public static final String COL4 = "Day_Total";
    public static final String COL5 = "Month_Total";
    public static final String COL6 = "Week_Total";
    //onUpdate, use available functions from SQL


    @Override
    public void onCreate(SQLiteDatabase db) {
    String createTable =
    "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT " +
            " , Amount TEXT " +
            " , Date TEXT " +
            " , Day_Total TEXT " +
            " , Week_Total TEXT "  +
            " , Month_Total TEXT) ";
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

    public boolean deleteOne(String tobeDeleted){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " DELETE FROM transactions " + " WHERE " + COL2 +" = '"  + tobeDeleted + "'";
        Cursor cursor =  db.rawQuery(query,null);

       if (cursor.moveToFirst()){
           return true;
       } else {
           return false; }
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

    //adding amounts of one day
    public Cursor dayOfWeekCalculation(){
        SimpleDateFormat sdf = new SimpleDateFormat( "D" );
        int currentWeekInt = Integer.parseInt( sdf.format( new Date(  ) ) ) ;
       SQLiteDatabase db = this.getWritableDatabase();
       String Query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL4 +" = '" + currentWeekInt +"' ";
       Cursor data = db.rawQuery( Query,null );
       return data;
    }

    //adding amounts of one week
    public Cursor weekOfYearCalculation(){
        SimpleDateFormat sdf = new SimpleDateFormat( "w" );
        int currentWeekOfYear = Integer.parseInt( sdf.format( new Date ()) );
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL6 +" = '" + currentWeekOfYear +"' ";
        Cursor data = db.rawQuery( Query, null );
        return data;
    }


    //adding amounts of one month
    public Cursor monthOfYearCalculation(){
        SimpleDateFormat sdf = new SimpleDateFormat( "L" );
        int monthInt = Integer.parseInt(sdf.format( new Date()));
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL5 + " = '" + monthInt + "' ";
        Cursor data = db.rawQuery( Query, null );
        return data;
    }}

