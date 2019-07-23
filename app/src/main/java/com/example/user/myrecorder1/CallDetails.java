package com.example.user.myrecorder1;


public class CallDetails {
    //fileds
    private int serial;
    private String num;
    private String time;
    private String date;
    //constructor
    public CallDetails(){

    }
    //constructor
    public CallDetails(int serial,String num,String time,String date)
    {
        this.serial=serial;
        this.num=num;
        this.time=time;
        this.date=date;
    }

    //getters and setters
    public int getSerial()
    {
        return serial;
    }

    public void setSerial(int serial)
    {
        this.serial=serial;
    }

    public String getNum()
    {
        return num;
    }

    public void setNum(String num)
    {
        this.num=num;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time=time;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date=date;
    }
}
