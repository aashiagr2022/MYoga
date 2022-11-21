package com.netra.myoga;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserDetailsActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener
{

    ImageButton save;
    private GoogleSignInOptions options;
    private GoogleSignInAccount account;
    DBConnection connection = new DBConnection(this);
    EditText gender, age, height, weight ;
    String email = " ";
    String userName = " ";
    String userGender = " ";
    RadioGroup radioGroup_age, radioGroup_gender;
    String userAge, UserDetailStatus;
    RadioButton male,female,age_below_18,age_18_24,age_25_45,age_above_45;

    int userHeight = 0;
    int userWeight = 0;


    @Override
    protected  void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_user_detail);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();

            userName = personName;
            email = personEmail;
        }


        //findView sets the view and has to be done after setContentView to instantiate view
        //account  = getIntent().getParcelableExtra("Account");
        //gender = (EditText) findViewById(R.id.gender);
        //age = (EditText) findViewById(R.id.age);
        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        save = (ImageButton) findViewById(R.id.Save);


        radioGroup_age = (RadioGroup) findViewById(R.id.userage_group);
        radioGroup_age.setOnCheckedChangeListener(this::onCheckedChanged);
        age_below_18 = (RadioButton) findViewById(R.id.radioButton9);
        age_18_24 = (RadioButton) findViewById(R.id.radioButton10);
        age_25_45 = (RadioButton) findViewById(R.id.radioButton11);
        age_above_45 = (RadioButton) findViewById(R.id.radioButton12);

        UserDetailStatus = "Filled";

        radioGroup_gender = (RadioGroup) findViewById(R.id.usergender_group);
        male = (RadioButton) findViewById(R.id.radioButton);
        female = (RadioButton) findViewById(R.id.radioButton8);
        radioGroup_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int radioButtonId1 = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton1 = (RadioButton)radioGroup.findViewById(radioButtonId1);
                userGender = radioButton1.getText().toString();


                if ( ( radioButton1.getText().toString().trim().equals("")) )
                {
                    radioButton1.setError( "Height is required!" );
                }
                Log.i("Purpose", String.valueOf(userGender));

            }
        });


        height.addTextChangedListener(new TextWatcher(){

            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {

            }

            public void beforeTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {

                Log.i("ABefore Change", height.getText().toString());
            }

            public void afterTextChanged(Editable arg0)
            {
                userHeight = Integer.parseInt(height.getText().toString());
            }
        });

        weight.addTextChangedListener(new TextWatcher(){

            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {

            }

            public void beforeTextChanged(CharSequence cs, int arg1, int arg2, int arg3)
            {

                Log.i("ABefore Change", weight.getText().toString());
            }

            public void afterTextChanged(Editable arg0)
            {
                userWeight = Integer.parseInt(weight.getText().toString());
            }


        });






        Log.i("Gender", userGender);
        Log.i("Age", String.valueOf(userAge));
        Log.i("Height", String.valueOf(userHeight));
        Log.i("Weight", String.valueOf(userWeight));
        Log.i("UserDetail", String.valueOf(UserDetailStatus));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //updateDB();

                /*if( height.getText().toString().length() == 0 )
                    height.setError( "Height is required!" );*/

                if(!male.isChecked() && !female.isChecked()){
                    Toast.makeText(UserDetailsActivity.this, "Please select gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!age_below_18.isChecked() && !age_18_24.isChecked() && !age_25_45.isChecked() && !age_above_45.isChecked()){
                    Toast.makeText(UserDetailsActivity.this, "Please select your age", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ( height.getText().toString().trim().equals("")) )
                {
                    // height.setError( "Height is required!" );
                    Toast.makeText(UserDetailsActivity.this, "Please enter your height", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( ( weight.getText().toString().trim().equals("")) )
                {
                    // weight.setError( "Weight is required!" );
                    Toast.makeText(UserDetailsActivity.this, "Plĕase enter your weight", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    updateDB();

                    Log.i("TEST", "Save button clicked");
                    Intent intent = new Intent(UserDetailsActivity.this, UserPurposeActivity.class);
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
        userAge = radioButton.getText().toString();

        Log.i("Purpose", String.valueOf(userAge));

    }



    public void updateDB()
    {

        Log.i("Update DB", userGender);
        Log.i("Update DB", String.valueOf(userAge));
        Log.i("Update DB", String.valueOf(userHeight));
        Log.i("Update DB", String.valueOf(userWeight));
        Log.i("UserDetail", String.valueOf(UserDetailStatus));
        connection.insertUser(userName,email,userGender,userAge,userHeight,userWeight,UserDetailStatus);

        //connection.deleteDb();
        String data = connection.getData().toString();


        Log.i("Table Data:" , data);
       /* ArrayList<String> result = connection.getAllUsers();

        for(String contact : result)
        {
            Log.i("Contacts: ", contact);
        }*/

    }
}
