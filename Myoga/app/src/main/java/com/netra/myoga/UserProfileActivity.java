package com.netra.myoga;

import android.content.Intent;
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
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class UserProfileActivity extends AppCompatActivity
            implements GoogleApiClient.OnConnectionFailedListener {

    Button logoutBtn, nextBtn;
    TextView userName, email;
    ImageView userImage;
    DBConnection connection = new DBConnection(this);


    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions options;
    private GoogleSignInAccount account;



    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_user_profile);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

      //  logoutBtn = (Button) findViewById(R.id.logout_btn);
        nextBtn = (Button) findViewById(R.id.next);
        userName = (TextView) findViewById(R.id.userName);
        email = (TextView) findViewById(R.id.Email);
        userImage = (ImageView) findViewById(R.id.userImage);

        options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, options)
                .build();



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("TEST", "Next button clicked");

                String data = connection.getData().toString();

                /*if(data == null) {
                    Intent intent = new Intent(UserProfileActivity.this, TransitionPageActivity.class);
                    intent.putExtra("Account", account);
                    startActivity(intent);
                }*/
                //if(account.getEmail() == null) {
                    Intent intent = new Intent(UserProfileActivity.this, UserDetailsActivity.class);
                    intent.putExtra("Account", account);
                    startActivity(intent);
                //}
            }
        });


       /* nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("TEST", "Next button clicked");

                Intent intent = new Intent(UserProfileActivity.this, UserDetailsActivity.class);
                intent.putExtra("Account", account);
                startActivity(intent);
            }
        });*/


 }

 protected void onStart()
 {
     super.onStart();
     Log.i("TEST","onStart");
     OptionalPendingResult<GoogleSignInResult> pendingResult = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
     if(pendingResult.isDone())
     {
         Log.i("Pending Result:", "isDone");
         GoogleSignInResult result = pendingResult.get();
         handleSignInResult(result);
     }
     else
     {
         Log.i("Pending Result","Not Done");
         pendingResult.setResultCallback(new ResultCallback<GoogleSignInResult>()
        {
             public void onResult(@NonNull GoogleSignInResult googleSignInResult)
            {
                Log.i("Pending Result","Call handleSignIn");
                handleSignInResult(googleSignInResult);
            }
        });
     }
 }

 private void handleSignInResult(GoogleSignInResult result)
 {
     if(result.isSuccess())
     {
         Log.i("Result", result.toString());
         account = result.getSignInAccount();
         userName.setText(account.getDisplayName());
         Log.i("Name", account.getDisplayName());
         email.setText(account.getEmail());
        // Log.i("Image URL :", account.getPhotoUrl().getPath());


         try {
             Glide.with(this).load(account.getPhotoUrl()).into(userImage);
         }
         catch(NullPointerException nex)
         {
             Toast.makeText(getApplicationContext(), "Error loading image",Toast.LENGTH_LONG).show();
         }
     }
     else
     {
         Log.i("Result Else", result.toString());
         gotoHomePage();
     }
 }

 private void gotoHomePage()
 {
     Intent intent = new Intent(this, LoginActivity.class);
 }

 public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
 {

 }
}
