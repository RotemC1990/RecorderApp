package com.example.user.myrecorder1;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    //fields
    private final int NUM_OF_PERMISSION =5;
    private Button RecordListPageBtn,SelfRecordPageBtn;
    private Switch switchRecordBtn;
    private TextView RecordInfoTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        //connect the fields to the buttons
        SelfRecordPageBtn=(Button)findViewById(R.id.selfRecBtn);
        RecordListPageBtn=(Button)findViewById(R.id.listPageBtn);
        switchRecordBtn=(Switch)findViewById(R.id.RecordSwitch);
        RecordInfoTxt= (TextView)findViewById(R.id.recInfoTxt) ;
        //make listeners
        SelfRecordPageBtn.setOnClickListener(this);
        RecordListPageBtn.setOnClickListener(this);
        switchRecordBtn.setOnCheckedChangeListener(this);

        if(pref.getBoolean("switchOn",false))//if the record is on
        {
            RecordInfoTxt.setText("The Application is Recording");
            RecordInfoTxt.setTextColor(getResources().getColor(R.color.green));
            switchRecordBtn.setChecked(true);
        }
        else    //if the record is off
        {
            RecordInfoTxt.setText("The Application isn't Recording");
            RecordInfoTxt.setTextColor(getResources().getColor(R.color.red));
            switchRecordBtn.setChecked(false);
        }
        pref.edit().putInt("numOfCalls",0).apply();
        permissionCheck();
    }

    //function that run when clicking the buttons
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.listPageBtn:
                intent = new Intent(this,ListPage.class);
                startActivity(intent);
                break;
            case R.id.selfRecBtn:
                intent = new Intent(this,SelfRecord.class);
                startActivity(intent);
                break;
        }
    }

    //function that run when clicking the switchers
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        SharedPreferences pref1=PreferenceManager.getDefaultSharedPreferences(this);
        if(isChecked) {
            if(permissionCheck()) { //record on
                RecordInfoTxt.setText("The Application is Recording");
                RecordInfoTxt.setTextColor(getResources().getColor(R.color.green));
                pref1.edit().putBoolean("switchOn", true).apply();
            }
            else {//there is no permissions
                RecordInfoTxt.setText("If you allowed the permissions press again");
                RecordInfoTxt.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                compoundButton.setChecked(false);
            }

        }
        else {//record off
            RecordInfoTxt.setText("The Application isn't Recording");
            RecordInfoTxt.setTextColor(getResources().getColor(R.color.red));
            pref1.edit().putBoolean("switchOn", false).apply();
        }
    }

    //function that check if the permissions is allowed
    private boolean permissionCheck()
    {
        int i=0;
        String[] perm={Manifest.permission.READ_PHONE_STATE,Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_CONTACTS};
        List<String> reqPerm=new ArrayList<>();

        for(String permis:perm) {
            int resultPhone = ContextCompat.checkSelfPermission(MainActivity.this,permis);
            if(resultPhone== PackageManager.PERMISSION_GRANTED)
                i++;
            else {
                reqPerm.add(permis);
            }
        }
        if(i==NUM_OF_PERMISSION)
            return true;
        else
            return permissionRequest(reqPerm);
    }

    //function that request the permission from the user
    private boolean permissionRequest(List<String> perm)
    {

        String[] listReq=new String[perm.size()];
        listReq=perm.toArray(listReq);
        for(String permissions:listReq) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,permissions)) {
                Toast.makeText(getApplicationContext(), "Phone Permissions needed for " + permissions, Toast.LENGTH_LONG);
            }
        }
        ActivityCompat.requestPermissions(MainActivity.this, listReq, 1);
        return false;
    }

}
