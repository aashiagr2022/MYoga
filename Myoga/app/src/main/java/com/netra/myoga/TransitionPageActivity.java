package com.netra.myoga;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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

public class TransitionPageActivity extends AppCompatActivity
       // implements GoogleApiClient.OnConnectionFailedListener {
{

    TextView userName, email;
    ImageView userImage;
    Button level1_button;
    Button level2_button;
    Button level3_button;
    Button level4_button;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions options;
    private GoogleSignInAccount account;


    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.transition_page);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        userName = (TextView) findViewById(R.id.userName);
        email = (TextView) findViewById(R.id.Email);
        userImage = (ImageView) findViewById(R.id.userImage);
        level1_button = (Button) findViewById(R.id.level1);
        level2_button = (Button) findViewById(R.id.level2);
        level3_button = (Button) findViewById(R.id.level3);
        //level4_button = (ImageButton) findViewById(R.id.level4);


        options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personGivenName = acct.getGivenName();
            String personFamilyName = acct.getFamilyName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            userName.setText(personGivenName);

            try {
                Glide.with(this).load(acct.getPhotoUrl()).into(userImage);
            }
            catch(NullPointerException nex)
            {
                Toast.makeText(getApplicationContext(), "Error loading image",Toast.LENGTH_LONG).show();
            }

        }


        level1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("TEST", "Level 1 button clicked");
                Intent intent = new Intent(TransitionPageActivity.this, Level1Activity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        level2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("TEST", "Level 2 button clicked");
                Intent intent = new Intent(TransitionPageActivity.this, Level2Activity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

        level3_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("TEST", "Level 3 button clicked");
                Intent intent = new Intent(TransitionPageActivity.this, Level3Activity.class);
                intent.putExtra("Account",account);
                startActivity(intent);
            }
        });

//        level4_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Log.i("TEST", "Level 4 button clicked");
//                Intent intent = new Intent(TransitionPageActivity.this, Level4Activity.class);
//                intent.putExtra("Account",account);
//                startActivity(intent);
//            }
//        });
    }
}

