package com.netra.myoga;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class PoseControlFlow extends AppCompatActivity {
    int day;
    int week;

    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        day = getIntent().getIntExtra("Day",2);
        week = getIntent().getIntExtra("Week",2);
        //this is where we will call the chain of exercises
        long startTime = System.nanoTime();
        boolean seqFin = false;
        long totalTime = 140;


        callComplete(totalTime);

        callPose(4);
        callPose(3);
        callPose(2);
        callPose(1);

        long endTime   = System.nanoTime();
        totalTime = (endTime - startTime)/1_000_000_000;



    }

    public void callPose(int poseNum) {
        Log.i("TEST", "Going into Screen for Pose " + poseNum);
        Intent intent = new Intent(PoseControlFlow.this, PoseCount.class);
        intent.putExtra("SequenceNum",poseNum);
        intent.putExtra("Day",day);
        intent.putExtra("Week",week);
        startActivity(intent);
    }

    public void callComplete(long totalTime) {
        Log.i("TEST", "Going to Completion Screen");
        Intent comp = new Intent(PoseControlFlow.this, CompletionActivity.class);
        comp.putExtra("TotalTime",totalTime);
        comp.putExtra("Day",day);
        comp.putExtra("Week",week);
        startActivity(comp);
    }
}
