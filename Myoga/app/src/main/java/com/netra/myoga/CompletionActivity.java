package com.netra.myoga;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class CompletionActivity extends AppCompatActivity {
    long totalTime;
    int day;
    int week;
    private GoogleSignInAccount account;
    TextView calView, timeView, dayView;
    Button returnButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.completionpage);

        day = getIntent().getIntExtra("Day",1);
        week = getIntent().getIntExtra("Week",1);
        totalTime = getIntent().getLongExtra("TotalTime",0);
        calView = (TextView) findViewById(R.id.caloriesBurned);
        timeView = (TextView) findViewById(R.id.durationOf);
        dayView = (TextView) findViewById(R.id.dayComp);
        returnButton = (Button) findViewById(R.id.backHome);

        String calStr = "60";
        calView.setText(calStr);
        String dayStr = "Day " + day + " Completed!";
        dayView.setText(dayStr);
        String timeStr = "" + totalTime;
        timeView.setText(timeStr);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DBUTTON", "Return to Main Page");
                Intent intent = new Intent(CompletionActivity.this, TransitionPageActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }
}
