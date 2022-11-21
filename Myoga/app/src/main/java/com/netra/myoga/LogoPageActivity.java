package com.netra.myoga;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import java.util.Timer;
import java.util.TimerTask;

public class LogoPageActivity extends AppCompatActivity {

    Timer timer;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions options;
    private GoogleSignInAccount account;


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.logo_page);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
                    public void run() {
                Intent intent = new Intent(LogoPageActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);


    }
}
