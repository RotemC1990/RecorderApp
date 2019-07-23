package com.example.user.myrecorder1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.io.File;
import java.util.List;

import static android.support.v4.content.FileProvider.getUriForFile;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    //fileds
    List<CallDetails> callDetails;
    Context context;
    SharedPreferences sp;
    String checkDate = "";
    //constructor
    public Adapter(List<CallDetails> callDetails, Context context) {
        this.callDetails = callDetails;
        this.context = context;
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    //viewHolder for watching the list of the records
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView num, time, date,name;
        //constructor
        public MyViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date1);
            name = (TextView) itemView.findViewById(R.id.name1);
            num = (TextView) itemView.findViewById(R.id.num);
            time = (TextView) itemView.findViewById(R.id.time1);
        }
        //play the selected record
        public void bind(final String dates, final String num, final String times) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String path = Environment.getExternalStorageDirectory() + "/My Records/" + dates + "/" + num + "_" + times + ".mp4"  ;
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    File f = new File(path);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(getUriForFile(context,"com.example.user.myrecorder1",f), "audio/*");
                    context.startActivity(intent);
                    sp.edit().putBoolean("pauseStateVLC",true).apply();
                }
            });
        }
    }

    //initialize the view by the xml of the record list or the date
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MyViewHolder viewHolder = null;
        LayoutInflater layoutInflator = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case 0:
                View v1 = layoutInflator.inflate(R.layout.record_list, parent, false);
                viewHolder = new MyViewHolder(v1);
                break;
            case 2:
                View v3 = layoutInflator.inflate(R.layout.date_layout, parent, false);
                viewHolder = new MyViewHolder(v3);
                break;
        }
        return viewHolder;
    }

    //initialize and put the record details in the view
    @Override
    public void onBindViewHolder(Adapter.MyViewHolder holder, int position) {

        CallDetails callDetails = this.callDetails.get(position);
        String contentNum=callDetails.getNum(); //number of contact
        String name=new Helper().getContactName(contentNum,context); //name of contact
        String nameUnknown="Unknown";
        holder.bind(callDetails.getDate(), callDetails.getNum(), callDetails.getTime()); //bind the contact details in the holder
        switch (getItemViewType(position)) {
            case 0:
                if(name!=null && !name.equals("")) {    //if the call if from a valid number
                    holder.name.setText(name);
                    holder.name.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }
                else {  //if the call if from a private number
                    holder.name.setText(nameUnknown);
                    holder.name.setTextColor(context.getResources().getColor(R.color.red));
                }
                holder.num.setText(this.callDetails.get(position).getNum());
                holder.time.setText(this.callDetails.get(position).getTime());
                break;
            case 2:
                holder.date.setText(this.callDetails.get(position).getDate());
                if(name!=null && !name.equals("")) {    //if the call if from a valid number
                    holder.name.setText(name);
                    holder.name.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
                }
                else {  //if the call if from a private number
                    holder.name.setText(nameUnknown);
                    holder.name.setTextColor(context.getResources().getColor(R.color.red));
                }
                holder.num.setText(this.callDetails.get(position).getNum());
                holder.time.setText(this.callDetails.get(position).getTime());
                break;
        }
    }
    //return what is the type of the list
    @Override
    public int getItemCount() {
        return callDetails.size();
    }

    public int getItemViewType(int position) {
        CallDetails cd = callDetails.get(position);
        String dt = cd.getDate();

        try {
            //record_list
            if (position!=0 && cd.getDate().equalsIgnoreCase(callDetails.get(position - 1).getDate())) {
                checkDate = dt;
                return 0;
            }
            else {  //date_layout
                checkDate = dt;
                return 2;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 2;
        }
    }
}
