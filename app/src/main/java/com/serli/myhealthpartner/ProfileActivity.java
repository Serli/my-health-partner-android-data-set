package com.serli.myhealthpartner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.serli.myhealthpartner.controller.ProfileController;
import com.serli.myhealthpartner.model.ProfileData;

import java.util.Calendar;
import java.util.Date;

public class ProfileActivity extends AppCompatActivity {

    private ProfileController controller;

    private Spinner spinner_gender;
    private String[] gender;
    private EditText editText_height;
    private EditText editText_weight;
    private DatePicker datePicker_birthday;
    ProfileData profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_profile);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        controller = new ProfileController(this);
        profile = new ProfileData();

        gender = getResources().getStringArray(R.array.gender);

        spinner_gender = (Spinner) findViewById(R.id.spinner_gender);
        editText_height = (EditText) findViewById(R.id.editText_height);
        editText_weight = (EditText) findViewById(R.id.editText_weight);
        datePicker_birthday = (DatePicker) findViewById(R.id.datePicker_birthday);


        if (controller.getProfile() != null) {
            profile = controller.getProfile();

            spinner_gender.setSelection(profile.getSex());
            editText_height.setText(String.valueOf(profile.getSize()));
            editText_weight.setText(String.valueOf(profile.getWeight()));

            Date d = profile.getBirthday();
            Calendar calendar_tmp = Calendar.getInstance();
            calendar_tmp.setTime(d);
            datePicker_birthday.updateDate(calendar_tmp.get(Calendar.YEAR), calendar_tmp.get(Calendar.MONTH), calendar_tmp.get(Calendar.DAY_OF_MONTH));
        }


        final Button button_validate = (Button) findViewById(R.id.button_validate);
        button_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profile.setSex(spinner_gender.getSelectedItemPosition());
                profile.setSize(Integer.parseInt(editText_height.getText().toString()));
                profile.setWeight(Integer.parseInt(editText_weight.getText().toString()));

                int day = datePicker_birthday.getDayOfMonth();
                int month = datePicker_birthday.getMonth();
                int year = datePicker_birthday.getYear();

                Calendar calendar_birthday = Calendar.getInstance();
                calendar_birthday.set(year, month, day);

                profile.setBirthday(calendar_birthday.getTime());

                controller.setProfile(profile);
                finish();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(adapter);
    }
}
