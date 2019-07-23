package com.example.user.myrecorder1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DBsingleton {
    public static SQLiteDatabase database;
    //function that create the db and return it or if its already have been created return it
    public static SQLiteDatabase getInstance(Context activity){
        if(database==null)
            database = new DBhandler(activity).getWritableDatabase();
        return database;
    }
}
