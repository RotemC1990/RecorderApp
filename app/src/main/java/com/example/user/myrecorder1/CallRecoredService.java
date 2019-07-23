package com.example.user.myrecorder1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;


public class CallRecoredService extends Service {

    MediaRecorder mediaRecorder;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //service that record the calls
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.reset();

        String phoneNumber=intent.getStringExtra("number");
        String time=new Helper().getTime();
        String path=new Helper().getPath();
        String recordFullPath=path+"/"+phoneNumber+"_"+time+".mp4";

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        mediaRecorder.setOutputFile(recordFullPath);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaRecorder.start();
        return START_NOT_STICKY;
    }

    public void onDestroy()
    {
        super.onDestroy();

        mediaRecorder.stop();
        mediaRecorder.reset();
        mediaRecorder.release();
        mediaRecorder =null;
    }
}
