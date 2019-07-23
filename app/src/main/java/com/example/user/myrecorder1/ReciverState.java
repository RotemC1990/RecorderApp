package com.example.user.myrecorder1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;



public class ReciverState extends BroadcastReceiver {
    //fields
    static Boolean recordStarted;
    public static String phoneNumber;
    public static String name;

    //when we receive a call or making one
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        Boolean switchCheckOn = sp.getBoolean("switchOn", true);
        if (switchCheckOn) { //if the switch is on record
            try {
                Bundle extras = intent.getExtras();
                String state = extras.getString(TelephonyManager.EXTRA_STATE);
                if (extras != null) {
                    if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {   //receive or taking a call
                        int activeCalls = sp.getInt("numOfCalls", 0);
                        sp.edit().putInt("numOfCalls", ++activeCalls).apply();
                        phoneNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER); // get the phone number
                        if (sp.getInt("numOfCalls", 1) == 1) {  //if there is a active call
                            Intent reivToServ = new Intent(context, CallRecoredService.class); //crate intent for the service
                            reivToServ.putExtra("number", phoneNumber);
                            context.startService(reivToServ);   //start the service
                            int serialNumber = sp.getInt("serialNumData", 1);
                            new DBmanage(context).addCallDetails(new CallDetails(serialNumber, phoneNumber, new Helper().getTime(), new Helper().getDate()));
                            sp.edit().putInt("serialNumData", ++serialNumber).apply();
                            sp.edit().putBoolean("recordStarted", true).apply();
                        }

                    } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) { //ending the call
                        int activeCalls1 = sp.getInt("numOfCalls", 1);
                        sp.edit().putInt("numOfCalls", --activeCalls1).apply();
                        int activeCalls = sp.getInt("numOfCalls", 0);
                        recordStarted = sp.getBoolean("recordStarted", false);
                        if (recordStarted && activeCalls == 0) {    //if we need to end the call record
                            context.stopService(new Intent(context, CallRecoredService.class)); //stop the service
                            sp.edit().putBoolean("recordStarted", false).apply();
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
