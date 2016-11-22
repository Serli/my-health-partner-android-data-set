package com.serli.myhealthpartner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.serli.myhealthpartner.controller.MainController;

/**
 * View of the main activity.
 */
// TODO : Add send and delete acquisition.
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MainController controller;

    private NumberPicker minutePicker;
    private NumberPicker secondPicker;
    private Spinner activitySpinner;
    private Button startStopButton;

    private boolean acquisitionStarted;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AccelerometerService.BROADCAST_START_ACTION)) {
                startStopButton.setText(R.string.button_stop);
                acquisitionStarted = true;
            }
            if (intent.getAction().equals(AccelerometerService.BROADCAST_STOP_ACTION)) {
                startStopButton.setText(R.string.button_start);
                acquisitionStarted = false;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        controller = new MainController(this);

        acquisitionStarted = AccelerometerService.isRunning();

        minutePicker = (NumberPicker) findViewById(R.id.minute_picker);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);

        secondPicker = (NumberPicker) findViewById(R.id.second_picker);
        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);

        activitySpinner = (Spinner) findViewById(R.id.activity_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sport_activity));
        activitySpinner.setAdapter(adapter);

        startStopButton = (Button) findViewById(R.id.start_stop_button);
        startStopButton.setOnClickListener(this);
        if (acquisitionStarted)
            startStopButton.setText(R.string.button_stop);

        IntentFilter filter = new IntentFilter();
        filter.addAction(AccelerometerService.BROADCAST_START_ACTION);
        filter.addAction(AccelerometerService.BROADCAST_STOP_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (acquisitionStarted) {
            controller.stopAcquisition();
        } else {
            int duration = minutePicker.getValue() * 60000 + secondPicker.getValue() * 1000;
            if (duration > 0) {
                controller.startAcquisition(duration, activitySpinner.getSelectedItemPosition());
            }
        }
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
