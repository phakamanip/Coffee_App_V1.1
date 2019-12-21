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

    public boolean addData(double price, String date ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, price);
        contentValues.put(COL3, date);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
      "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "ITEM1 TEXT, DATE TEXT)";
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

    public void deleteName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " + COL2 + " = '" + name + "'";

        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting: " + name + " from database.");
        db.execSQL(query);

    }

    public double CurrentMonthIncome(){
        double x =0;
        SQLiteDatabase db = this.getReadableDatabase();
        String getAmountData = "SELECT SUM(inc_amount) AS totalInc FROM " + COL2 +
                " where year(entry_date)= year(date('now')) and MONTH(entry_date) = MONTH(date('now'))";
        Cursor c = db.rawQuery(getAmountData, null);
        if(c.moveToFirst()){
            x = c.getDouble(0);
        }

        return x;

    }




}