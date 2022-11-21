package com.netra.myoga;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class UserPurposeActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    RadioGroup radioGroup_purpose;
    RadioButton button2, button3, button4, button5, button6;
    ImageButton next_button;
    String purpose , userEmail;;
    DBConnection connection = new DBConnection(this);
    TextView email;

    private GoogleSignInAccount account;
    private GoogleSignInOptions options;
    private GoogleSignInAccount accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_purpose);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        radioGroup_purpose = (RadioGroup) findViewById(R.id.userpurpose_group);
        radioGroup_purpose.setOnCheckedChangeListener(this::onCheckedChanged);
        button2 = (RadioButton) findViewById(R.id.radioButton2);
        button3 = (RadioButton) findViewById(R.id.radioButton3);
        button4 = (RadioButton) findViewById(R.id.radioButton4);
        button5 = (RadioButton) findViewById(R.id.radioButton5);
        button6 = (RadioButton) findViewById(R.id.radioButton6);

        next_button = (ImageButton) findViewById(R.id.nextbutton);
        //email = (TextView) findViewById(R.id.Email);
        account  = getIntent().getParcelableExtra("Account");

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

            userEmail = personEmail;
        }


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("TEST", "Save button clicked");

                if(!button2.isChecked() && !button3.isChecked() && !button4.isChecked() && !button5.isChecked() && !button6.isChecked()){
                    Toast.makeText(UserPurposeActivity.this, "Please select your purpose", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Intent intent = new Intent(UserPurposeActivity.this, TransitionPageActivity.class);
                    intent.putExtra("Account", account);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {

        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton)radioGroup.findViewById(radioButtonId);
        purpose = radioButton.getText().toString();

        connection.UpdateUserPurpose(purpose,userEmail);

        Log.i("Purpose", String.valueOf(purpose));
    }
}

