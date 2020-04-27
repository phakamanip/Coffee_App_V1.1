package com.phakamanipega.count_my_coffee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "myDB.db";
    public static final String TABLE_NAME = "mylist_data2";
    public static final String COL1 = "ID";
    public static final String COL2 = "ITEM1";
    public static final String COL3 = "DATE";


    public boolean addData(double price1, String date ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
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
        String query = " DELETE FROM " + TABLE_NAME + " WHERE " + COL3 +COL2 + " = '"  + tobeDeleted + "'";

       Cursor cursor =  db.rawQuery(query,null);

       if (cursor.moveToFirst()){
           return true;
       } else {
           return false;
       }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
      "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "ITEM1 TEXT, DATE TEXT )";
      db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteName(String namethis) {
        SQLiteDatabase db = this.getWritableDatabase();
       String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL2 + " = '"  + namethis + "'" ;

        //Log.d(TAG, "deleteName: query: " + query);
       // Log.d(TAG, "deleteName: Deleting: " + namethis + " from database.");
       db.execSQL(query);
       db.close();

    }












}