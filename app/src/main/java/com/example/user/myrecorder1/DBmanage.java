package com.example.user.myrecorder1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class DBmanage {
    //fields
    SQLiteDatabase sqLiteDatabase;
    //constructor
    public DBmanage(Context activity) {
        sqLiteDatabase = DBsingleton.getInstance(activity);
    }

    //function that add the call details to the db
    public void addCallDetails(CallDetails callDetails) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBhandler.SERIAL_NUMBER, callDetails.getSerial());
        contentValues.put(DBhandler.PHONE_NUMBER, callDetails.getNum());
        contentValues.put(DBhandler.TIME, callDetails.getTime());
        contentValues.put(DBhandler.DATE, callDetails.getDate());

        sqLiteDatabase.insert(DBhandler.TABLE_RECORD, null, contentValues);
    }

    //function that return all records details
    public List<CallDetails> getFullList() {
        List<CallDetails> recordList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DBhandler.TABLE_RECORD;

        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CallDetails callDetails = new CallDetails();
                callDetails.setSerial(cursor.getInt(0));
                callDetails.setNum(cursor.getString(1));
                callDetails.setTime(cursor.getString(2));
                callDetails.setDate(cursor.getString(3));

                recordList.add(callDetails);
            } while (cursor.moveToNext());
        }
        return recordList;
    }

}
