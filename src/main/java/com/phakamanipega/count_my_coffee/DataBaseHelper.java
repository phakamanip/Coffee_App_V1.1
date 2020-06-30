package com.phakamanipega.count_my_coffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "myDB.db";
    public static final String TABLE_NAME = "mylist_data2";
    public static final String COL1 = "ITEMWEEK";
    public static final String COL2 = "ITEM1";
    public static final String COL3 = "DATE";
    public static final String COL4 = "ITEMWEEK";


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
      "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT " +
                             " , ITEM1 TEXT " +
                             " , DATE TEXT " +
                             " , ITEMWEEK TEXT" + ")";
        db.execSQL(createTable);

    }

    public boolean addData(int weekDayInt, double price1, String date ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, weekDayInt);
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
        super(context, TABLE_NAME, null, 2);

    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }



    public Cursor retrieveOnWeekDay(int WeekTotal){
        SQLiteDatabase db = this.getWritableDatabase();

        int monday = 1;
        int tuesday = 2;
        int wednesday = 3;
        int thursday = 4;
        int friday = 5;
        int saturday= 6;
        int sunday = 7;

        //String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL4 + " = '" + sunday +" '";


        String query2 = "SELECT * FROM " + TABLE_NAME + " WHERE " + WeekTotal ;


        Cursor data = db.rawQuery(query2, null);
      //  db.execSQL(query2);

        return data;


    }



}












