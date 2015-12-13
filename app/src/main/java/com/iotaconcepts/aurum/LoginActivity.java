package com.iotaconcepts.aurum;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
{
    Button next;
    EditText et_name, et_age;
    RadioGroup myRadioGroup;
    RadioButton male, female;
    String name_str, age_str, sex_age;
    double lat, lon;
    String lat_s, lon_s;
    GPSTracker gps;
    int a; // acts as a flag for male-female

    SessionManager name_sex_session;
    LocationSessionManager location_session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        next = (Button)findViewById(R.id.bt_login_next);
        et_name = (EditText)findViewById(R.id.et_login_name);
        et_age = (EditText)findViewById(R.id.et_login_age);
        myRadioGroup = (RadioGroup)findViewById(R.id.rg_sex_radioGroup);
        male = (RadioButton)findViewById(R.id.rb_male);
        female = (RadioButton)findViewById(R.id.rb_female);

        a = 0; // 1- male, 2 - female
        name_sex_session = new SessionManager(getApplicationContext());
        location_session = new LocationSessionManager(getApplicationContext());

        //Location Tracker
        gps = new GPSTracker(LoginActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation())
        {
            lat = gps.getLatitude();
            lon = gps.getLongitude();
            lat_s = Double.toString(lat);
            lon_s = Double.toString(lon);
            //Toast.makeText(LoginActivity.this, lat_s + " " + lon_s, Toast.LENGTH_SHORT).show();
        }
        else
        {
         //       gps.showSettingsAlert();
        }

        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                name_str = et_name.getText().toString();
                age_str = et_age.getText().toString();

                if(name_str.equals(""))  // if no name is filled
                {
                    Toast.makeText(LoginActivity.this, "Please enter your name", Toast.LENGTH_LONG).show();
                }
                else                      // name filled
                {
                    if(!male.isChecked() && !female.isChecked()) // if no checkbox checked
                    {
                        Toast.makeText(LoginActivity.this, "Please enter your sex", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        if(male.isChecked())
                        {
                            a = 1;
                        }
                        else if(female.isChecked())
                        {
                            a = 2;
                        }

                        if (age_str.equals(""))  // if no age is entered
                        {
                            Toast.makeText(LoginActivity.this, "Please enter your age", Toast.LENGTH_LONG).show();
                        }
                        else
                        {


                            /****** if Name, sex and age is filled - execute the code below!  ******/

                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);

                            // Setting Dialog Title
                            alertDialog.setTitle("Disclaimer. Legal Notice.");

                            // Setting Dialog Message
                            alertDialog.setMessage("\n" +
                                    "Users kindly understand that this app is meant for educational purpose only. This application is NOT meant for SELF DIAGNOSIS. The results provided by this app are not a legal advice. This app maps various symptoms to their PROBABLE diseases or cause. Actual disease may vary depending on various conditions. This method is not the best means of diagnosis. Please visit a registered doctor in case you require a medical assistance or help.\n\nThe result generated based on the information given by the user is only for informative and indicative purposes. Therefore, the developers make no warranties whatsoever nor is responsible for damages of any kind regarding the use of this app or any decision made by the user.\n");

                            // If user clicks DECLINE
                            alertDialog.setNegativeButton("Decline", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                }
                            });


                            // if User clicks Accept
                            alertDialog.setPositiveButton("Accept", new DialogInterface.OnClickListener()
                            {
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    // sex_age : purpose: storing sex and age data in one string
                                    sex_age = Integer.toString(a);  // if male a=1, else if female a = 2
                                    sex_age = sex_age.concat(age_str);   //concat: sex_age = sex_age + age i.e. sex_age = a + age(of user)

                                    //Toast.makeText(LoginActivity.this, sex_age, Toast.LENGTH_SHORT).show();

                                    // Now store data in phone memory - using session manager

                                    // Name and (sex+age) details are stored in SessionManager Class (object name: name_sex_session)
                                    name_sex_session.createLoginSession(name_str, sex_age);

                                    // Location details are stored in LocationSessionManager Class (object name: location_session)
                                    location_session.createLoginSession(lat_s, lon_s);

                                    //Toast.makeText(LoginActivity.this, lat_s + " " + lon_s, Toast.LENGTH_LONG).show();

                                    // Start our main activity
                                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            });

                            alertDialog.show();



















                        }
                    }
                }
            }
        });
    }
}
