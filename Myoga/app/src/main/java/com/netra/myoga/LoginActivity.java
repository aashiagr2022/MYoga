package com.netra.myoga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends AppCompatActivity  implements
GoogleApiClient.OnConnectionFailedListener{

    SignInButton signIn_button;
    Button stream_button;
    TextView textView;
    private MediaPlayer mediaPlayer;
    private boolean playPause, initialStage=true;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 100;
    GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, options);

        // Check for existing Google Sign In account, if the user is already signed in
// the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);



        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,options)
                .build();

        //stream_button = (Button) findViewById(R.id.Stream_btn);
        signIn_button = (SignInButton) findViewById(R.id.signIn_button);
        signIn_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });

        /*stream_button.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view)
            {
                if(!playPause) {
                    stream_button.setText("Pause");

                    if (initialStage) {
                        new Player().execute("https://www.wavsource.com/snds_2020-10-01_3728627494378403/tv/commercials/dodge_hit_it.wav");
                    } else {
                        if (!mediaPlayer.isPlaying()) {
                            mediaPlayer.start();
                        }
                    }

                    playPause = true;
                }
                else
                {
                    stream_button.setText("Play");

                    if(mediaPlayer.isPlaying())
                    {
                        mediaPlayer.pause();
                    }

                    playPause = false;
                }
            }

        });*/
    }

    protected  void onPause()
    {
        super.onPause();

        if(mediaPlayer != null)
        {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
    {

    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result)
    {
        showUserDetails();
            if(result.isSuccess())
            {
                Log.i("Success SignIn", result.toString());
                showUserDetails();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
            }

    }

    private void showUserDetails()
    {
        Intent intent = new Intent(LoginActivity.this, UserDetailsActivity.class);
        startActivity(intent);
    }


    class Player extends AsyncTask<String, Void, Boolean>{

        protected  Boolean doInBackground(String... strings)
        {
            Boolean prepared = false;

            try
            {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

                    public void onCompletion(MediaPlayer mediaPlayer)
                    {
                        initialStage = true;
                        playPause = false;
                        stream_button.setText("Play");
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.prepare();
                prepared = true;
            }
            catch (Exception e)
            {
                Log.e("Streaming Error", e.getMessage());
                prepared = false;

            }

            return  prepared;
        }

        protected  void onPostExecute(Boolean status)
        {
            super.onPostExecute(status);

            mediaPlayer.start();
            initialStage = false;
        }

        protected void onPreExecute()
        {
            super.onPreExecute();
            //Need buffering indication ??
        }
    }

}

