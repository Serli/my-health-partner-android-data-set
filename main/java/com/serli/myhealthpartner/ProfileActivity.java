package com.serli.myhealthpartner;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.serli.myhealthpartner.controller.ProfileController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private ProfileController controller;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private EditText EditText;
    static final String[] gender = new String[]{"--------------- ", "Man", "Women"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView1 = (TextView) findViewById(R.id.textView1);
        textView1.setText("Profile ");

        textView2 = (TextView) findViewById(R.id.textView2);
        textView2.setText("Name");

        textView3 = (TextView) findViewById(R.id.textView3);
        textView3.setText("Sexe");

        textView4 = (TextView) findViewById(R.id.textView4);
        textView4.setText("birthday");

        EditText = (EditText)findViewById(R.id.editText);

        final Button loginButton = (Button) findViewById(R.id.button2);

        //recuperer name
        EditText name = (EditText) findViewById(R.id.editText);
        String Name = name.getText().toString();

        //////spinner
        final Spinner list = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        list.setAdapter(adapter);
    }

    //calculate age
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getAge(View v) {
        //date of birthday
        String dob_var = EditText.getText().toString();
        //current date
        SimpleDateFormat df = new SimpleDateFormat("dd.MMM.yyyy");
        Calendar c = Calendar.getInstance();
        String currentDate = df.format(c.getTime());

        SimpleDateFormat format = new SimpleDateFormat("dd.MMM.yyyy");

        Date d1;
        Date d2;
        int age =0 ;
        try {
            d1 = format.parse(dob_var);
            d2 = format.parse(currentDate);
            long diff = d2.getTime() - d1.getTime();
            long diffYears = diff / (365 * 24 * 60 * 60 * 1000);
            age = (int) Math.round(diffYears);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
