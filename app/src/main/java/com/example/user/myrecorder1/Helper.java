package com.example.user.myrecorder1;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.File;
import java.util.Calendar;

public class Helper {

    Calendar calendar =Calendar.getInstance();

    //function that return the date in string
    public String getDate()
    {
        int day= calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH)+1;
        int year = calendar.get(Calendar.YEAR);

        return String.valueOf(day)+"_"+String.valueOf(month)+"_"+String.valueOf(year);
    }

    //function that return the time in string
    public String getTime()
    {
        int seconds = calendar.get(Calendar.SECOND);
        int minutes = calendar.get(Calendar.MINUTE);
        int hours  = calendar.get(Calendar.HOUR);
        int am_pm_getValue=calendar.get(Calendar.AM_PM);
        String amORpm="";
        if(am_pm_getValue==Calendar.AM)
            amORpm="AM";
        else if(am_pm_getValue==Calendar.PM)
            amORpm="PM";

        return String.valueOf(hours)+":"+String.valueOf(minutes)+":"+String.valueOf(seconds)+" "+amORpm;
    }
    //function that return the path for the file
    public String getPath()
    {
        String internalFile=getDate();
        File file=new File(Environment.getExternalStorageDirectory()+"/My Records/");
        File file1=new File(Environment.getExternalStorageDirectory()+"/My Records/"+internalFile+"/");
        if(!file.exists())
        {
            file.mkdir();
        }
        if(!file1.exists())
            file1.mkdir();

        String path=file1.getAbsolutePath();

        return path;
    }
    //function that return the contact name of the caller
    public String getContactName(final String number,Context context)
    {
        Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(number));
        String[] projection=new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};
        String contactName="";
        Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);
        if (cursor != null) {
            if(cursor.moveToFirst()) {
                contactName=cursor.getString(0);
            }
            cursor.close();
        }

        if(contactName!=null && !contactName.equals(""))
            return contactName;
        else
            return "";
    }

}
