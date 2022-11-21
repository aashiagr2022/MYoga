package com.netra.myoga;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.io.File;


public class PoseCount extends AppCompatActivity {
    Button finPose;
    TextView poseName;
    TextView countdown;
    ImageView yogaGif;
    int seqNum;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().hide();
        setContentView(R.layout.posingscreen);



        finPose = (Button) findViewById(R.id.doneButton);
        poseName = (TextView) findViewById(R.id.workoutType);
        yogaGif = (ImageView) findViewById(R.id.poseGif);
        countdown = (TextView) findViewById(R.id.countDown);
        String startTime = "5";
        countdown.setText(startTime);

        seqNum = getIntent().getIntExtra("SequenceNum",1);
        String poseNum = "pose" + seqNum + ".gif";
        //yogaGif.setImageURI(Uri.parse("android.resource://"+getPackageName()+"/drawable/" + poseNum));

        //yogaGif.setImageResource(R.drawable.pose1);

        //set the correct pose
        switch(seqNum){
            case 1:
                //yogaGif.setImageResource(R.drawable.pose1);
                Glide.with(this).load(R.drawable.pose1).into(yogaGif);
                break;
            case 2:
                //yogaGif.setImageResource(R.drawable.pose2);
                Glide.with(this).load(R.drawable.pose2).into(yogaGif);
                break;
            case 3:
                //yogaGif.setImageResource(R.drawable.pose3);
                Glide.with(this).load(R.drawable.pose3).into(yogaGif);
                break;
            case 4:
                //yogaGif.setImageResource(R.drawable.pose4);
                Glide.with(this).load(R.drawable.pose4).into(yogaGif);
                break;
            default:
                yogaGif.setImageResource(R.drawable.pose1);
                Log.i("WARNING:", "Invalid Pose Number Passed");
        }


        poseName.setText(poseNum);

        //button will be used to skip excercise
        finPose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TEST", "Skipping to next exercise");
                kill_activity();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Pose Counter:", "Started");
        //added a 5 second timer as a "ready" measure prior to 30 second timer

        new CountDownTimer(35000, 1000) {

            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished / 1000 > 30){
                    countdown.setText("" + ((millisUntilFinished / 1000)-30));
                }
                else{
                    countdown.setText("" + (millisUntilFinished / 1000));
                }
            }

            public void onFinish() {
                countdown.setText("0");
                Log.i("Pose Counter:", "Ending Current Pose " + seqNum);
                kill_activity();
            }
        }.start();
    }

    void kill_activity()
    {
        finish();
    }
}
